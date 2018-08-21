package com.github.unitTest.logparser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.logparser.LogParser;

public class LogParserTest {
	@Test
	public void testGetResultMap() throws Exception {
		LogParser logParser = new LogParser();
		
		Map<Integer, HashMap<String, Object>> result = logParser.getResultMap("unitTest.log");
		if (result.isEmpty()) {
			Assert.fail();
		} 
		
		Assert.assertEquals(3, result.size());
		Assert.assertEquals(4, result.get(1).get("total_kills"));
			
	}
	
	@Test
	public void getMeansOfDeathValueTest() {
		
		Assert.assertEquals("MOD_SHOTGUN", LogParser.getMeansOfDeathValue(1));
		Assert.assertEquals("MOD_RAILGUN", LogParser.getMeansOfDeathValue(10));
		
	}

}
