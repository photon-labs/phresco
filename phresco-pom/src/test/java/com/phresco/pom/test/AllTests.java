package com.phresco.pom.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PomProcessorAddDependencyTest.class,
	PomProcessorAddModulesTest.class,
	PomProcessorAddPluginTest.class,
	PomProcessorAddPropertiesTest.class,
	PomProcessorChangeDependencyVersionTest.class,
	PomProcessorChangeDependencyVersionTest.class,
	PomProcessorDeleteDependencyTest.class,
	PomProcessorGetModelTest.class,
	PomProcessorGetPluginTest.class,
	PomProcessorRemoveModuleTest.class,
	RemoveAllDependencyTest.class, 
	GetModulesTest.class,
	PomProcessorGetPropertyTest.class,
	PomProcessorChangePluginVersionTest.class,
	PomProcessorAddProfileTest.class}
	 )
public class AllTests {

}
