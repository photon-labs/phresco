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

import java.util.List;

/**
 * Configuration for the integration test runs. This class is only the definition of the parameters that are
 * shadowed in
 * {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo} and used there.
 *
 */
public class Test {
    /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testSkip}
     */
   private String skip;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testInstrumentationPackage}
     */
   private String instrumentationPackage;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testInstrumentationRunner}
     */
   private String instrumentationRunner;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testDebug}
     */
   private Boolean debug;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testCoverage}
     */
   private Boolean coverage;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testLogOnly}
     */
   private Boolean logOnly;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testSize}
     */
   private String testSize;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testCreateReport}
     */
   private Boolean createReport;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testPackages}
     */
   protected List<String> packages;
   /**
     * Mirror of {@link com.photon.maven.plugins.android.AbstractInstrumentationMojo#testClasses}
     */
   protected List<String> classes;

   public String getSkip() {
       return skip;
   }

   public String getInstrumentationPackage() {
       return instrumentationPackage;
   }

   public String getInstrumentationRunner() {
       return instrumentationRunner;
   }

   public Boolean isDebug() {
       return debug;
   }

   public Boolean isCoverage() {
       return coverage;
   }

   public Boolean isLogOnly() {
       return logOnly;
   }

   public String getTestSize() {
       return testSize;
   }

   public Boolean isCreateReport() {
       return createReport;
   }

   public List<String> getPackages() {
	   return packages;
   }

   public List<String> getClasses() {
       return classes;
   }
}
