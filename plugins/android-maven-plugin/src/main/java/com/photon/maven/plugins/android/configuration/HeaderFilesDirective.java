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
 */
public class HeaderFilesDirective {

    /** Base directory from where to include/exclude files from.
     *
     */
    private String directory;

    /**
     * A list of &lt;include> elements specifying the files (usually C/C++ header files) that should be included in the
     * header archive. When not specified, the default includes will be <code><br/>
     * &lt;includes><br/>
     * &nbsp;&lt;include>**&#47;*.h&lt;/include><br/>
     * &lt;/includes><br/>
     * </code>
     *
     * @parameter
     */
    private String[] includes;

    /**
     * A list of &lt;include> elements specifying the files (usually C/C++ header files) that should be excluded from
     * the header archive.
     *
     * @parameter
     */
    private String[] excludes;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    public String[] getIncludes() {
        return includes;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }
}
