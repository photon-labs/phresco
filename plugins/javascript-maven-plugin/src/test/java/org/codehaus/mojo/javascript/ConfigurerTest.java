/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

package org.codehaus.mojo.javascript;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

/**
 * Tests for the Configurer class
 * 
 * @author bjones
 */
public class ConfigurerTest {
	
    /**
     * Test that buildSpecifiedPlugins traverses the project heirachy building list
     * of plugins from the original model.
     */
    @Test
	public void run_buildSpecifiedPlugins() {
		// Setup test mocking
		MavenProject project1 = mock(MavenProject.class);
        MavenProject project2 = mock(MavenProject.class);
        MavenProject project3 = mock(MavenProject.class);
		List<Plugin> pluginsBuild = new ArrayList<Plugin>();
        List<Plugin> plugins1 = new ArrayList<Plugin>();
        List<Plugin> plugins2 = new ArrayList<Plugin>();
        List<Plugin> plugins3 = new ArrayList<Plugin>();
		Plugin plugin1 = new Plugin();
        Plugin plugin2 = new Plugin();
        Plugin plugin3 = new Plugin();
		pluginsBuild.add(plugin1);
        pluginsBuild.add(plugin2);
        pluginsBuild.add(plugin3);
        plugins1.add(plugin1);
        plugins2.add(plugin2);
        plugins3.add(plugin3);
		when(project1.getBuildPlugins()).thenReturn(pluginsBuild);
        when(project2.getBuildPlugins()).thenReturn(pluginsBuild);
        when(project3.getBuildPlugins()).thenReturn(pluginsBuild);
		Model model1 = mock(Model.class);
        Model model2 = mock(Model.class);
        Model model3 = mock(Model.class);
		when(project1.getOriginalModel()).thenReturn(model1);
        when(project2.getOriginalModel()).thenReturn(model2);
        when(project3.getOriginalModel()).thenReturn(model3);
		Build build1 = mock(Build.class);
        Build build2 = mock(Build.class);
        Build build3 = mock(Build.class);
		when(model1.getBuild()).thenReturn( build1 );
        when(model2.getBuild()).thenReturn( build2 );
        when(model3.getBuild()).thenReturn( build3 );
		when(build1.getPlugins()).thenReturn(plugins1);
        when(build2.getPlugins()).thenReturn(plugins2);
        when(build3.getPlugins()).thenReturn(plugins3);
		when(project1.getParent()).thenReturn( project2 );
		when(project2.getParent()).thenReturn( project3 );
		when(project3.getParent()).thenReturn(null);
		
		// Setup object under test and run test methods
		Configurer configurer = new Configurer();
		Set<Plugin> specifiedPlugins = configurer.buildSpecifiedPlugins( project1 );
		
		// Expect that we see dependencies from all levels of the project structure
		assertTrue(specifiedPlugins.contains( plugin1 ));
		assertTrue(specifiedPlugins.contains( plugin2 ));
		assertTrue(specifiedPlugins.contains( plugin3 ));
	}
	
}
