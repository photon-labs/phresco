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
 
package com.photon.maven.plugins.android;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.photon.maven.plugins.android.AbstractEmulatorMojo;

public class AbstractEmulatorMojoTest {

	private AbstractEmulatorMojo mockMojo;

	@Before
	public void setUp() throws Exception {
		mockMojo = new mockAbstractEmulatorMojo();
	}
	
	@Ignore
//	@Test
	public final void testStartAndStopAndroidEmulator() throws MojoExecutionException {
		mockMojo.startAndroidEmulator();
		mockMojo.stopAndroidEmulator();
	}

	private class mockAbstractEmulatorMojo extends AbstractEmulatorMojo {

		public void execute() throws MojoExecutionException,
		MojoFailureException {
			// TODO Auto-generated method stub

		}

	}

}
