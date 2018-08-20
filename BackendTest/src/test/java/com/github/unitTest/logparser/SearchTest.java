package com.github.unitTest.logparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.logparser.Search;

public class SearchTest {
	@Test
	public void testSearchById() {
		Map<Integer, HashMap<String, Object>> testMap = 
				new HashMap<Integer, HashMap<String, Object>>();
		
		HashMap<String, Object> gameInfo1 = new HashMap<String, Object>();
		HashMap<String, Object> gameInfo2 = new HashMap<String, Object>();
		HashMap<String, Object> gameInfo3 = new HashMap<String, Object>();
		ArrayList<String> players1 = new ArrayList<String>();
		ArrayList<String> players2 = new ArrayList<String>();
		ArrayList<String> players3 = new ArrayList<String>();
		
		// game_1
		players1.add("playerA");
		players1.add("playerB");
		players1.add("playerC");
		gameInfo1.put("players", players1);
		testMap.put(1, gameInfo1);

		// game_2
		players2.add("playerA");
		players2.add("playerB");
		players2.add("playerD");
		gameInfo2.put("players", players2);
		testMap.put(2, gameInfo2);
		
		// game_3
		players3.add("playerA");
		players3.add("playerC");
		players3.add("playerE");
		gameInfo3.put("players", players3);
		testMap.put(3, gameInfo3);
		
		Search search = new Search();
		
		Map<Integer, HashMap<String, Object>> resultMap = search.searchById("playerA", testMap);
		Assert.assertEquals(3, resultMap.size());

		resultMap = search.searchById("playerB", testMap);
		Assert.assertEquals(2, resultMap.size());
		
		resultMap = search.searchById("playerC", testMap);
		Assert.assertEquals(2, resultMap.size());

		resultMap = search.searchById("playerE", testMap);
		Assert.assertEquals(1, resultMap.size());

		resultMap = search.searchById("playerF", testMap);
		Assert.assertEquals(0, resultMap.size());
		
		
	}
}
