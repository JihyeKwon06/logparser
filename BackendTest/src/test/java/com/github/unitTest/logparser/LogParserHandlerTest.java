package com.github.unitTest.logparser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.handler.LogParserHandler;

public class LogParserHandlerTest {
	
	@Test
	public void testSortByValueDesc() {
		LogParserHandler logParserHandler = new LogParserHandler();
		
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put("test1", 1);
		hashMap.put("test2", 5);
		hashMap.put("test3", 2);
		hashMap.put("test4", 3);
		hashMap.put("test5", 4);
		
		List result = logParserHandler.sortByValue(hashMap, "DESC");
		
		Iterator it = result.iterator();
		
		int compareNo1 = 6;
		int compareNo2;
		
		while (it.hasNext()) {
			String key = (String) it.next();
			compareNo2 = hashMap.get(key);
			if (compareNo1 < compareNo2) Assert.fail();
			compareNo1 = compareNo2;
		}
	}
	
	@Test
	public void testSortByValueAsc() {
		LogParserHandler logParserHandler = new LogParserHandler();
		
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put("test1", 1);
		hashMap.put("test2", 5);
		hashMap.put("test3", 2);
		hashMap.put("test4", 3);
		hashMap.put("test5", 4);
		
		List result = logParserHandler.sortByValue(hashMap, "ASC");
		
		Iterator it = result.iterator();
		int compareNo1 = 0;
		int compareNo2;
		
		while (it.hasNext()) {
			String key = (String) it.next();
			compareNo2 = hashMap.get(key);
			if (compareNo1 > compareNo2) Assert.fail();
			compareNo1 = compareNo2;
		}
	}

}
