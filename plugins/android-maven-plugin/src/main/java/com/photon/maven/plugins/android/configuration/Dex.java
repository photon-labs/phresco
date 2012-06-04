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
 * Configuration for the dex  test execution. This class is only the definition of the parameters that are
 * shadowed in
 * {@link com.photon.maven.plugins.android.phase08preparepackage.DexMojo} and used there.
 *
 */
public class Dex {
    /**
      * Mirror of {@link com.photon.maven.plugins.android.phase08preparepackage.DexMojo#dexJvmArguments}
      */
    private String[] jvmArguments;
    /**
      * Mirror of {@link com.photon.maven.plugins.android.phase08preparepackage.DexMojo#dexCoreLibrary}
      */
    private Boolean coreLibrary;
    /**
      * Mirror of {@link com.photon.maven.plugins.android.phase08preparepackage.DexMojo#dexNoLocals}
      */
    private Boolean noLocals;
    /**
      * Mirror of {@link com.photon.maven.plugins.android.phase08preparepackage.DexMojo#dexOptimize}
      */
    private Boolean optimize;

    public String[] getJvmArguments() {
        return jvmArguments;
    }

    public Boolean isCoreLibrary() {
        return coreLibrary;
    }

    public Boolean isNoLocals() {
        return noLocals;
    }

    public Boolean isOptimize() {
        return optimize;
    }
}
