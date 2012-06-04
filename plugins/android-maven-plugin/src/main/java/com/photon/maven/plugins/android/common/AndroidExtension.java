/*
 * ###
 * Android Maven Plugin - android-maven-plugin
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
/*
 * Copyright (C) 2009 Jayway AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.photon.maven.plugins.android.common;

public final class AndroidExtension {
	/** Android application. */
	public static final String APK = "apk";
	
	/** Android library project. */
	public static final String APKLIB = "apklib";
	
	/** Android performance project */
	public static final String APKPERF = "apkperf";
	
	/** @deprecated Use {@link APKLIB} instead. */
	public static String APKSOURCES = "apksources";
	
	
	//No instances
	private AndroidExtension() {}
	
	
	/**
	 * Determine whether or not a {@link MavenProject}'s packaging is an
	 * Android project.
	 * 
	 * @param packaging Project packaging.
	 * @return True if an Android project.
	 */
	public static boolean isAndroidPackaging(String packaging) {
		return APK.equals(packaging)
			|| APKLIB.equals(packaging)
			|| APKSOURCES.equals(packaging);
	}
}
