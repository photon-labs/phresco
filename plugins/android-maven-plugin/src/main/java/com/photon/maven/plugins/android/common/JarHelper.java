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

import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.util.IOUtil;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/** Helper class to deal with jar files.
 */
public class JarHelper {

      public interface UnjarListener {

         boolean include(JarEntry jarEntry);
    }

    /** Unjars the specified jar file into the the specified directory
     *
     * @param jarFile
     * @param outputDirectory
     * @param unjarListener
     * @throws IOException
     */
    public static void unjar(JarFile jarFile, File outputDirectory,UnjarListener unjarListener)
            throws IOException {
        for (Enumeration en = jarFile.entries(); en.hasMoreElements(); ) {
            JarEntry entry = (JarEntry) en.nextElement();
            File entryFile = new File(outputDirectory, entry.getName());
            if (unjarListener.include(entry)) {
                if (!entryFile.getParentFile().exists() && !entryFile.getParentFile().mkdirs()) {
				    throw new IOException("Error creating output directory: " +entryFile.getParentFile());
				}

                // If the entry is an actual file, unzip that too
                if (!entry.isDirectory()) {
                    final InputStream in = jarFile.getInputStream(entry);
                    try {
                        final OutputStream out = new FileOutputStream(entryFile);
                        try {
                            IOUtil.copy(in, out);
                        } finally {
                            IOUtils.closeQuietly(out);
                        }
                    } finally {
                        IOUtils.closeQuietly(in);
                    }
                }
            }
        }
    }

}
