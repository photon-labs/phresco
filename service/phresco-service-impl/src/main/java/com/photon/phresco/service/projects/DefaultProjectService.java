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
package com.photon.phresco.service.projects;

import java.io.File;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.DependencyProcessor;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.ProjectService;
import com.photon.phresco.service.config.impl.ConfiguratorFactory;
import com.photon.phresco.service.dependency.impl.DependencyProcessorFactory;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class DefaultProjectService implements ProjectService, Constants {
	private static final Logger S_LOGGER = Logger.getLogger(DefaultProjectService.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	public synchronized File createProject(ProjectInfo projectInfo) throws PhrescoException {
		// TODO:This code should be moved into server initialization
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DefaultProjectService.createProject(ProjectInfo projectInfo)");
		}
		PhrescoServerFactory.initialize();

		// find the archetype from the appType
		if (isDebugEnabled) {
			S_LOGGER.debug("createProject() ProjectInfo =" + projectInfo.getCode());
		}
		File filePath = PhrescoServerFactory.getNewArchetypeExecutor().execute(projectInfo);
		File[] listFiles = filePath.listFiles();
		File projectPath = null;
		if (listFiles.length > 0) {
			projectPath = listFiles[0];
		}
		// PhrescoServerFactory.getDependencyProcessor().process(info,
		// projectPath);
		DependencyProcessor dependencyProcessor = DependencyProcessorFactory.getDependencyProcessor(projectInfo);
		if (dependencyProcessor != null) {
			dependencyProcessor.process(projectInfo, projectPath);
		}

		PhrescoServerFactory.getNewDocumentGenerator().generate(projectInfo, projectPath);

		// Configure created application
		if (isDebugEnabled) {
			S_LOGGER.info("Configure created application :" + projectInfo.getName());
		}
		ConfiguratorFactory.getConfigurator(projectInfo).configure(projectInfo, projectPath);
		return projectPath;
	}

	public File updateProject(ProjectInfo projectInfo) throws PhrescoException {
		File projectPath = new File(Utility.getPhrescoTemp(), UUID.randomUUID().toString()) ;
		projectPath.mkdirs();
		DependencyProcessor dependencyProcessor = DependencyProcessorFactory.getDependencyProcessor(projectInfo);
		if (dependencyProcessor != null) {
			dependencyProcessor.process(projectInfo, projectPath);
		}
		if (isDebugEnabled) {
			S_LOGGER.info("successfully updated application :" + projectInfo.getName());
		}
		ConfiguratorFactory.getConfigurator(projectInfo).configure(projectInfo, projectPath);
		return projectPath;
	}

	public File updateDocumentProject(ProjectInfo projectInfo) throws PhrescoException {
		File tempPath = new File(Utility.getPhrescoTemp(), UUID.randomUUID().toString()+File.separator + projectInfo.getCode());
		try {
			PhrescoServerFactory.getNewDocumentGenerator().generate(projectInfo, tempPath);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return tempPath;
	}
	
	public void deleteProject(ProjectInfo projectInfo, File projectPath) throws PhrescoException {

	}

}
