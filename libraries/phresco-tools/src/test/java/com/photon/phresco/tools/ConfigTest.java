package com.photon.phresco.tools;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.tools.config.Configuration;
import com.photon.phresco.tools.config.ConfigurationFactory;

public class ConfigTest {
	
	ConfigurationFactory fac = null;
	
	@Before
    public void setUp() {
		fac = ConfigurationFactory.getInstance(new File("src/test/resources/config.xml"));
	}
	
	@After
    public void tearDown() {
    }
	
	@Test
	public void testEnvironment() {
		List<Configuration> configurations = fac.getConfiguration("Dev", null, null);
		assertEquals(5, configurations.size());
	}
	
	@Test
	public void testDefaultEnvironment() {
		assertEquals("Production", fac.getDefaultEnvName());
		List<Configuration> configurations = fac.getDefaultEnv();
		assertEquals(5, configurations.size());
	}
	
	@Test
	public void testConfigurationByType() {
		List<Configuration> configurations = fac.getConfiguration("Dev", "database", null);
		assertEquals(2, configurations.size());
	}
	
	
}
