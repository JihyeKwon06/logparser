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
		
		if (args.length>0) {
			resultMap = parser.getResultMap(args[0]);
		} else {
			resultMap = parser.getResultMap();
		}
		
		resultMap = parser.getResultMap("unitTest.log");
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the order : ");
		String input = scanner.nextLine();
		String inputUpperCase = input.toUpperCase();
		
		if (inputUpperCase.contains("SEARCH")) {
			String id = input.substring(inputUpperCase.indexOf("SEARCH")+"SEARCH".length()+1);
			resultMap = Search.searchById(id, resultMap);
		}
		
		Set<Integer> args1 = resultMap.keySet();
		for (int i : args1) {
			System.out.println("game_"+ i +":");

			// Result
			HashMap<String, Object> result = resultMap.get(i);
			System.out.println(result);
			
			// Ranking
			HashMap<String, Integer> players_kills = (HashMap<String, Integer>) result.get("players_kills");
			System.out.println("[ Ranking ]	");
			
			Iterator it = sortByValue(players_kills, "DESC").iterator();
			int ranking = 1;
			
			while (it.hasNext()) {
				String key = (String) it.next();
				System.out.println(ranking +" : " + key + "("+players_kills.get(key)+")");
				ranking++;
			}
			System.out.println();
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
}
