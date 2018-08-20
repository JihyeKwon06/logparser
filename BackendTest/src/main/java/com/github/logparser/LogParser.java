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
		
		
		while ((line = reader.readLine()) != null) {
			
			tokens = new StringTokenizer(line);
			String playTime = tokens.nextToken();
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
						
						resultMap.put(noGame, gameInfo);
					}
					
					if (event.equals("ShutdownGame")) break;
					
					noGame++;
					total_kills = 0;
					playerInfos = new HashMap<String, ArrayList<Object>>();
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
					
					total_kills++;
					break;
				default :
					break;
			
			}
		}
		return resultMap;
	}
}
