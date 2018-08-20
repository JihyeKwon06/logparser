package com.github.logparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Search {
	
	@SuppressWarnings("unchecked")
	public static Map<Integer, HashMap<String, Object>> searchById(String id,
			Map<Integer, HashMap<String, Object>> resultMap) {
		
		Map<Integer, HashMap<String, Object>> searchMap = 
				new HashMap<Integer, HashMap<String, Object>>();
		
		Set<Integer> args1 = resultMap.keySet();
		for (int i : args1) {
			HashMap<String, Object> result = resultMap.get(i);
			ArrayList<String> players = (ArrayList<String>) result.get("players");
			for (String id_player : players) {
				if (id.equals(id_player)) {
					searchMap.put(i, result);
					break;
				}
			}
		}
	
		return searchMap;
	}
	
	
	public static Map<Integer, HashMap<String, Object>> searchById(String[] ids, 
			Map<Integer, HashMap<String, Object>> resultMap,
			String way) {
		
		Map<Integer, HashMap<String, Object>> searchMap = new HashMap<Integer, HashMap<String, Object>>();
		
		Set<Integer> args1 = resultMap.keySet();
		for (int i : args1) {
			HashMap<String, Object> result = resultMap.get(i);
			
			@SuppressWarnings("unchecked")
			ArrayList<String> players = (ArrayList<String>) result.get("players");
			if (way.equals("or")) {
				for (String id : ids) {
					for (String id_player : players) {
						if (id.equals(id_player)) {
							searchMap.put(i, result);
							break;
						}
					}
				}
			} else if (way.equals("and")) {
				int cntIds = ids.length;
				int cntCorrected = 0;
				for (String id : ids) {
					for (String id_player : players) {
						if (id.equals(id_player)) {
							cntCorrected++;
						}
					}
				}
				if (cntIds == cntCorrected) searchMap.put(i, result); 
			}
			
		}
			
		return searchMap;
		
	}
}
