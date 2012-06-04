/*
 * ###
 * phresco-pom
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
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
