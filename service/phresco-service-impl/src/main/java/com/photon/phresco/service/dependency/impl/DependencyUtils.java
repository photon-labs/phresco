/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.dependency.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.Utility;

/**
 * @author arunachalam_l
 *
 */
public final class DependencyUtils {
	private static final Logger S_LOGGER = Logger.getLogger(DependencyUtils.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	private DependencyUtils(){
		//restrict instantiation 
	}


	/**
	 * Finds out the {@link ArchiveType} for the given extension.
	 * @param extension
	 * @return
	 */
	public static ArchiveType getArchiveType(String extension) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DependencyUtils.getArchiveType(String extension)");
		}
		ArchiveType archiveType = ArchiveType.ZIP;
		if(extension.equals(".tar.gz")) {
			archiveType = ArchiveType.TARGZ;
		} else if (extension.endsWith(".tar")){
			archiveType = ArchiveType.TAR;
		}
		return archiveType;
	}



	/**
	 * Returns the file extension of the content.
	 * @param contentURL
	 * @return
	 */
	public static String getExtension(String contentURL) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DependencyUtils.getExtension(String contentURL)");
		}
		String extension = ".zip";
		if(contentURL.endsWith("tar.gz")){
			extension = ".tar.gz";
		} else if(contentURL.endsWith("tar")){
			extension = ".tar";
		}
		return extension;
	}
	
	/**
	 * Extracts the given compressed file (of type tar, targz, and zip) into given location.
	 * See also
	 * {@link ArchiveType} and {@link ArchiveUtil}
	 * @param contentURL
	 * @param path
	 * @throws PhrescoException
	 */
	public static void extractFiles(String contentURL, File path) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DependencyUtils.extractFiles(String contentURL, File path)");
			S_LOGGER.debug("extractFiles() Filepath="+path.getPath());
		}
		extractFiles(contentURL, null, path);
	}
	
	/**
	 * Extracts the given compressed file (of type tar, targz, and zip) into given location.
	 * See also
	 * {@link ArchiveType} and {@link ArchiveUtil}
	 * @param contentURL
	 * @param path
	 * @throws PhrescoException
	 */
	public static void extractFiles(String contentURL, String folderName, File path) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DependencyUtils.extractFiles(String contentURL, String folderName, File path)");
		}
		assert !StringUtils.isEmpty(contentURL);
		
		String extension = getExtension(contentURL);
		File archive = new File(Utility.getPhrescoTemp(), UUID.randomUUID().toString()+extension);
		FileOutputStream fos = null;
		OutputStream out = null;
		try {
			InputStream inputStream = PhrescoServerFactory.getRepositoryManager().getArtifactAsStream(contentURL);
			fos = new FileOutputStream(archive);
			out = new BufferedOutputStream(fos);
			
			IOUtils.copy(inputStream, out);
			
			out.flush();
			out.close();
			fos.close();
			ArchiveType archiveType = getArchiveType(extension);	
			if (isDebugEnabled) {
				S_LOGGER.debug("extractFiles() path="+path.getPath());
			}
			ArchiveUtil.extractArchive(archive.toString(), path.getAbsolutePath(),folderName, archiveType);
			archive.delete();
		} 
		catch (FileNotFoundException e) {
			return;
		}
		
		catch (IOException e) {
			throw new PhrescoException(e);
		} catch (PhrescoException pe ){
			if(pe.getCause() instanceof FileNotFoundException) {
				return;
			}
			throw pe;
		}finally {
			Utility.closeStream(fos);
			Utility.closeStream(out);
		}
	}
	
	public static void copyFilesTo(File[] files, File destPath) throws IOException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DependencyUtils.copyFilesTo(File[] files, File destPath)");
			S_LOGGER.error(" copyFilesTo() Destination Filepath =" + destPath.getPath());
		}
		if(files == null || destPath == null) {
			return; //nothing to copy
		}
		for (File file : files) {
			if(file.isDirectory()) {
				FileUtils.copyDirectory(file, destPath);
			} else {
				FileUtils.copyFileToDirectory(file, destPath);
			}
		}

	}
}
