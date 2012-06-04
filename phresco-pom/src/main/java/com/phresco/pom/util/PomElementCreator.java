package com.phresco.pom.util;

import com.phresco.pom.model.Activation;
import com.phresco.pom.model.BuildBase;

public class PomElementCreator {
	
	private PomElementCreator() {
		
	}
	
	/**
	 * @param defaultgoal
	 * @param directory
	 * @return
	 */
	public static BuildBase createBuildBase(String defaultgoal,String directory){
		BuildBase build = new BuildBase();
		build.setDefaultGoal(defaultgoal);
		build.setDirectory(directory);
		return build;
	}
	
	/**
	 * @param value
	 * @param jdk
	 * @return
	 */
	public static Activation createActivation(Boolean value,String jdk){
		Activation activation = new Activation();
		activation.setActiveByDefault(value);
		activation.setJdk(jdk);
		return activation;
	}
	
	/**
	 * @param module
	 * @return
	 */
	private static com.phresco.pom.model.Profile.Modules createProfileModule(String module) {
		com.phresco.pom.model.Profile.Modules modules = new com.phresco.pom.model.Profile.Modules();
		modules.getModule().add(module);
		return modules;
	}
}
