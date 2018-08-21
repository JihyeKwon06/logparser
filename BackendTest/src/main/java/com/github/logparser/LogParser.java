package com.github.logparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class LogParser {
	
	public enum meansOfDeath {
		MOD_UNKNOWN,
		MOD_SHOTGUN,
		MOD_GAUNTLET,
		MOD_MACHINEGUN,
		MOD_GRENADE,
		MOD_GRENADE_SPLASH,
		MOD_ROCKET,
		MOD_ROCKET_SPLASH,
		MOD_PLASMA,
		MOD_PLASMA_SPLASH,
		MOD_RAILGUN,
		MOD_LIGHTNING,
		MOD_BFG,
		MOD_BFG_SPLASH,
		MOD_WATER,
		MOD_SLIME,
		MOD_LAVA,
		MOD_CRUSH,
		MOD_TELEFRAG,
		MOD_FALLING,
		MOD_SUICIDE,
		MOD_TARGET_LASER,
		MOD_TRIGGER_HURT,
		MOD_NAIL,
		MOD_CHAINGUN,
		MOD_PROXIMITY_MINE,
		MOD_KAMIKAZE,
		MOD_JUICED,
		MOD_GRAPPLE;
	}
	
	private String filePath = "games.log";

	public Map<Integer, HashMap<String, Object>> getResultMap(String filePath) throws Exception {
		this.filePath = filePath;
		return getResultMap();
	}
	
	public Map<Integer, HashMap<String, Object>> getResultMap() throws Exception {
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String line = null;
		StringTokenizer tokens;
		int noGame = 0;
		
		Map<Integer, HashMap<String, Object>> resultMap = new HashMap<Integer, HashMap<String, Object>>();
		
		int total_kills = 0;
		
		ArrayList<Object> playerInfo = null;			// ArrayList<0: id_player, 1: player_kills>
		HashMap<String, ArrayList<Object>> playerInfos = null;	// Map<no_player, ArrayList<0: id_player, 1: player_kills>>
		HashMap<String, Integer> kills_by_means = null;	// Map<no_meansOfDeath, count>
		
		while ((line = reader.readLine()) != null) {
			
			tokens = new StringTokenizer(line);
			tokens.nextToken();	// play time
			String event = tokens.nextToken(": ");
			String no_player;
			
			switch (event) {
				case "InitGame" :
				case "ShutdownGame" :
					if (event.equals("ShutdownGame") || noGame != 0 && resultMap.get(noGame) == null) {
						
						HashMap<String, Object> gameInfo = new HashMap<String, Object>();
						ArrayList<String> players = new ArrayList<String>();
						HashMap<String, Integer> players_kills = new LinkedHashMap<String, Integer>();
						
						Set<String> enums = playerInfos.keySet();
						for (String key : enums) {
							playerInfo = playerInfos.get(key);
							players.add((String)playerInfo.get(0));
							players_kills.put((String)playerInfo.get(0), (Integer) playerInfo.get(1));
						}
						
						gameInfo.put("total_kills", total_kills);
						gameInfo.put("players", players);
						gameInfo.put("players_kills", players_kills);
						gameInfo.put("kills_by_means", kills_by_means);
						
						resultMap.put(noGame, gameInfo);
					}
					
					if (event.equals("ShutdownGame")) break;
					
					noGame++;
					total_kills = 0;
					playerInfos = new HashMap<String, ArrayList<Object>>();
					kills_by_means = new HashMap<String, Integer>();
					break;
				case "ClientConnect" :
					no_player = tokens.nextToken();
					playerInfo = new ArrayList<Object>();
					playerInfo.add("newPlayer");
					playerInfo.add(0);
					playerInfos.put(no_player, playerInfo);
					break;
				case "ClientUserinfoChanged" :
					no_player = tokens.nextToken();
					tokens.nextToken("\\\"");
					String id_player = tokens.nextToken("\\\"");

					playerInfo = playerInfos.get(no_player);
					playerInfo.set(0, id_player);
					playerInfos.put(no_player, playerInfo);
					break;
				case "ClientDisconnect" :
					no_player = tokens.nextToken();
					playerInfos.remove(no_player);
					break;
				case "Kill" :
					no_player = tokens.nextToken();
					String deceased = tokens.nextToken();
					int no_meansOfDeath = Integer.parseInt(tokens.nextToken());
					
					if (no_player.equals(deceased)) {
						break;
					} else if (no_player.equals("1022")) {
						playerInfo = playerInfos.get(deceased);
						playerInfo.set(1, (Integer)playerInfo.get(1)-1);
						playerInfos.put(deceased, playerInfo);
					} else {
						playerInfo = playerInfos.get(no_player);
						playerInfo.set(1, (Integer)playerInfo.get(1)+1);
						playerInfos.put(no_player, playerInfo);
					}
					
					String meansOfDeath = getMeansOfDeathValue(no_meansOfDeath);
					
					if (kills_by_means.containsKey(meansOfDeath)) {
						kills_by_means.put(meansOfDeath, kills_by_means.get(meansOfDeath)+1);
					} else {
						kills_by_means.put(meansOfDeath, 1);
					}
					total_kills++;
					break;
				default :
					break;
			
			}
		}
		return resultMap;
	}
	
	public static String getMeansOfDeathValue(int idx) {
		meansOfDeath[] arr = meansOfDeath.values();
		return arr[idx].toString(); 
	}

}
