package com.github.unitTest.logparser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.logparser.LogParser;

public class LogParserTest {
	@Test
	public void testGetResultMap() {
		LogParser logParser = new LogParser();
		
		try {
			Map<Integer, HashMap<String, Object>> result = logParser.getResultMap("unitTest.log");
			if (result.isEmpty()) {
				Assert.fail();
			} 
			
			Assert.assertEquals(3, result.size());
			Assert.assertEquals(4, result.get(1).get("total_kills"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
