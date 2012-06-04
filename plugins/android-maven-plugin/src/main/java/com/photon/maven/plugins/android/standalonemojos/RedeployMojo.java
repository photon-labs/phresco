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


package com.photon.maven.plugins.android.standalonemojos;

import com.photon.maven.plugins.android.AbstractAndroidMojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;

/**
 * Redeploys the built apk file, or another specified apk, to a connected device.
 * This simply tries to undeploy the APK and re-deploy it.
 * @goal redeploy
 * @requiresProject false
 * @requiresDependencyResolution runtime
 */
public class RedeployMojo extends AbstractAndroidMojo {

    /**
     * Optionally used to specify a different apk file to deploy to a connected emulator or usb device, instead of the
     * built apk from this project.
     *
     * @parameter expression="${android.file}"
     */
    private File file;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (file == null) {
            if (! SUPPORTED_PACKAGING_TYPES.contains(project.getPackaging())) {
                getLog().info("Skipping redeploy on " + project.getPackaging());
                return;
            }
            String packageToUndeploy = extractPackageNameFromAndroidManifest(androidManifestFile);
            undeployApk(packageToUndeploy);
            deployBuiltApk();
        } else {
            undeployApk(file);
            deployApk(file);
        }
    }

}
