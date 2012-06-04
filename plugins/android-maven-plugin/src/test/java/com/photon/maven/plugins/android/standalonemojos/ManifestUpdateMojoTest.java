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
 
package com.photon.maven.plugins.android.standalonemojos;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.photon.maven.plugins.android.AbstractAndroidMojoTestCase;

public class ManifestUpdateMojoTest extends AbstractAndroidMojoTestCase<ManifestUpdateMojo> {
    @Override
    public String getPluginGoalName() {
        return "manifest-update";
    }

    @Test
    public void testBasicAndroidProjectVersion() throws Exception {
//        ManifestUpdateMojo mojo = createMojo("manifest-tests/basic-android-project");
//        mojo.execute();
//        File dir = getProjectDir(mojo);
//        File manifestFile = new File(dir, "AndroidManifest.xml");
//        assertExpectedAndroidManifest(manifestFile, dir);
    }

	@Test
    public void testBasicAndroidProjectManifest() throws Exception {
//        ManifestUpdateMojo mojo = createMojo("manifest-tests/basic-android-project-manifest");
//        mojo.execute();
//        File dir = getProjectDir(mojo);
//        File manifestFile = new File(dir, "AndroidManifest.xml");
//        assertExpectedAndroidManifest(manifestFile, dir);
    }

    @Test
    public void testBasicJarProject() throws Exception {
//        ManifestUpdateMojo mojo = createMojo("manifest-tests/basic-jar-project");
//        mojo.execute();
//        File dir = getProjectDir(mojo);
//        File manifestFile = new File(dir, "AndroidManifest.xml");
//        Assert.assertFalse("Should not have an AndroidManifest for a jar project", manifestFile.exists());
    }

    @Test
    public void testVersionlessAndroidProject() throws Exception {
//        ManifestUpdateMojo mojo = createMojo("manifest-tests/versionless-android-project");
//        mojo.execute();
//        File dir = getProjectDir(mojo);
//        File manifestFile = new File(dir, "androidManifest.xml"); // intentionally small lowercase 'a'
//        assertExpectedAndroidManifest(manifestFile, dir);
    }

    @Test
    public void testManyVersionsAndroidProject() throws Exception {
//        ManifestUpdateMojo mojo = createMojo("manifest-tests/manyversions-android-project");
//        for (int i = 0; i < 50; i++) { // Simulate 50 runs of the mojo
//            mojo.execute();
//        }
//        File dir = getProjectDir(mojo);
//        File manifestFile = new File(dir, "AndroidManifest.xml");
//        assertExpectedAndroidManifest(manifestFile, dir);
    }

	@Test
    public void testVersionCodeUpdateAndIncrementFail() throws Exception {
//        ManifestUpdateMojo mojo = createMojo("manifest-tests/bad-android-project1");
//        try {
//			mojo.execute();
//        } catch (MojoFailureException e) {
//	        Assert.assertTrue(e.getMessage().startsWith("versionCodeAutoIncrement, versionCodeUpdateFromVersion and versionCode"));
//	        return;
//        }
//		Assert.assertTrue("bad-android-project1 did not throw MojoFailureException", false);
    }

	public void testVersionCodeAndVersionCodeUpdateFail() throws Exception {
//	    ManifestUpdateMojo mojo = createMojo("manifest-tests/bad-android-project2");
//	    try {
//			mojo.execute();
//	    } catch (MojoFailureException e) {
//		    Assert.assertTrue(e.getMessage().startsWith("versionCodeAutoIncrement, versionCodeUpdateFromVersion and versionCode"));
//		    return;
//	    }
//		Assert.assertTrue("bad-android-project2 did not throw MojoFailureException", false);
	}

	public void testVersionCodeAndVersionIncrementFail() throws Exception {
//	    ManifestUpdateMojo mojo = createMojo("manifest-tests/bad-android-project3");
//	    try {
//			mojo.execute();
//	    } catch (MojoFailureException e) {
//		    Assert.assertTrue(e.getMessage().startsWith("versionCodeAutoIncrement, versionCodeUpdateFromVersion and versionCode"));
//		    return;
//	    }
//		Assert.assertTrue("bad-android-project3 did not throw MojoFailureException", false);
	}

    private void assertExpectedAndroidManifest(File manifestFile, File testdir) throws IOException {
//        File expectFile = new File(testdir, "AndroidManifest-expected.xml");
//        // different white space causes issues when between going Windows and *nix via git and wrongly configured
//        // autocrlf .. since we dont need to worry about whitespace.. we strip it out
//        String actual = StringUtils.deleteWhitespace(FileUtils.readFileToString(manifestFile));
//        String expected = StringUtils.deleteWhitespace(FileUtils.readFileToString(expectFile));
//        Assert.assertEquals(expected, actual);
    }
}
