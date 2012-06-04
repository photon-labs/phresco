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

package com.photon.maven.plugins.android.configuration;

/**
 * Configuration for the zipalign command. This class is only the definition of the parameters that are shadowed in
 * {@link com.photon.maven.plugins.android.AbstractZipalignMojo} and used there.
 *
 */
public class Zipalign {
    /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractZipalignMojo#zipalignSkip}
     */
    private Boolean skip;
    /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractZipalignMojo#zipalignVerbose}
     */
    private Boolean verbose;
    /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractZipalignMojo#zipalignInputApk}
     */
    private String inputApk;
    /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractZipalignMojo#zipalignOutputApk}
     */
    private String outputApk;


    public Boolean isSkip() {
        return skip;
    }

    public Boolean isVerbose() {
        return verbose;
    }

    public String getInputApk() {
        return inputApk;
    }

    public String getOutputApk() {
        return outputApk;
    }
}
