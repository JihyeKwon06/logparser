package com.github.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.github.logparser.LogParser;
import com.github.logparser.Search;

public class LogParserHandler {
	public static void main(String[] args) throws Exception {
		
		LogParser parser = new LogParser();
		Map<Integer, HashMap<String, Object>> resultMap;
		HashMap<String, Integer> players_kills_total = new HashMap<String, Integer>();
		
		if (args.length>0) {
			resultMap = parser.getResultMap(args[0]);
		} else {
			resultMap = parser.getResultMap();
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("[ Just enter to see information of all the game or write 'search ID' for search ]");
		System.out.print("Enter the order : ");
		String input = scanner.nextLine();
		String inputUpperCase = input.toUpperCase();
		
		if (inputUpperCase.contains("SEARCH")) {
			String id = input.substring(inputUpperCase.indexOf("SEARCH")+"SEARCH".length()+1);
			resultMap = Search.searchById(id, resultMap);
		}
		
		Set<Integer> args1 = resultMap.keySet();
		for (int i : args1) {
			System.out.print("game_"+ i +":");
			
			// Result
			HashMap<String, Object> result = resultMap.get(i);
			
			// GameResult
			//System.out.println(result);
			printMap(result);
			
			HashMap<String, Integer> players_kills = (HashMap<String, Integer>)result.get("players_kills");
			
			// Ranking of the game
			/*System.out.println("[ Ranking ]	");
			
			Iterator it = sortByValue(players_kills, "DESC").iterator();
			int ranking = 1;
			
			while (it.hasNext()) {
				String key = (String) it.next();
				System.out.println(ranking +" : " + key + "("+players_kills.get(key)+")");
				ranking++;
			}
			System.out.println();*/

			// Set Ranking of all the games
			Set<String> enums = players_kills.keySet();
			for (String key : enums) {
				players_kills_total.put(key, (players_kills_total.containsKey(key) ? players_kills_total.get(key) : 0) +players_kills.get(key));
			}
			
		}
		
		// Print Ranking of all the games
		System.out.println(System.lineSeparator() + "[ Ranking of all the games]");
		
		Iterator it = sortByValue(players_kills_total, "DESC").iterator();
		int ranking = 1;
		
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println(ranking +" : " + key + "("+players_kills_total.get(key)+")");
			ranking++;
		}
		
	}
	
	public static List sortByValue(final HashMap hashMap, final String order) {
		
		List list = new ArrayList();
		list.addAll(hashMap.keySet());
		
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				Object v1 = hashMap.get(o1);
				Object v2 = hashMap.get(o2);
				
				if (order.equals("ASC")) {
					return ((Comparable) v1).compareTo(v2);
				} else if (order.equals("DESC")) {
					return ((Comparable) v2).compareTo(v1);
				} else {
					return -1;
				}
			}
		});
		return list;
	}
	
	public static void printMapTap(Map map, int tapCnt) {
		System.out.println(" {");
		Set<Object> enums = map.keySet();
		String keyMsg = "";
		for (int tap = 0; tap <= tapCnt; tap++) {
			keyMsg = keyMsg + "	 ";
		}

		for (Object key : enums) {
			System.out.print(keyMsg+ key + ": ");
			Object value = map.get(key);
			if (value instanceof  Map) {
				printMapTap((Map)value, tapCnt+1);
			} else {
				System.out.println(value);
			}
		}
		System.out.println(keyMsg.substring(1)+"}");
	}
	
	public static void printMap(Map map) {
		printMapTap(map, 0);
	}
}
