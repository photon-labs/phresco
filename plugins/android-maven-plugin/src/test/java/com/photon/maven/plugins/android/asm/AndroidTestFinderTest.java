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
 
package com.photon.maven.plugins.android.asm;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Assert;
import org.junit.Test;

import com.photon.maven.plugins.android.asm.AndroidTestFinder;

import java.io.File;

/**
 * Exercises the {@link com.photon.maven.plugins.android.asm.AndroidTestFinder} class.
 *
 */
public class AndroidTestFinderTest {
    @Test
    public void givenDirectoryWithoutTestsThenNoTests() throws MojoExecutionException {
        final boolean result = AndroidTestFinder.containsAndroidTests(new File("target/test-classes/com/photon/maven/plugins/android/asm/withouttests"));
        Assert.assertFalse("'withouttests' should not contain any tests.", result);
    }
    @Test
    public void givenDirectoryWithTestsThenItContainsTests() throws MojoExecutionException {
        final boolean result = AndroidTestFinder.containsAndroidTests(new File("target/test-classes/com/photon/maven/plugins/android/asm/withtests"));
        Assert.assertTrue("'withtests' should contain tests.", result);
    }

}
