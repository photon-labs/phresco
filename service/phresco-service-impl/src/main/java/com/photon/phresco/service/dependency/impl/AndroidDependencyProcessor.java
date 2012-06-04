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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.pom.AndroidTestPOMUpdater;
import com.photon.phresco.service.pom.POMUpdater;
import com.photon.phresco.util.TechnologyTypes;
import com.phresco.pom.util.PomProcessor;

/**
 * Android Dependency processor
 * 
 * @author arunachalam_l
 * 
 */
public class AndroidDependencyProcessor extends AbstractJsLibDependencyProcessor {

	private static final String UNIT_TEST_POM_XML = "/test/unit/pom.xml";
    private static final String PERFORMANCE_TEST_POM_XML = "/test/performance/pom.xml";
    private static final String FUNCTIONAL_TEST_POM_XML = "/test/functional/pom.xml";
    private static final Logger S_LOGGER = Logger.getLogger(AndroidDependencyProcessor.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	private String ANDROID_VERSION = "android.version";
	private String POM_FILE = "pom.xml";
	private String PROPERTIES = "properties";

	/**
	 * @param dependencyManager
	 */
	public AndroidDependencyProcessor(RepositoryManager repoManager) {
		super(repoManager);
	}

	@Override
	public void process(ProjectInfo info, File path) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method AndroidDependencyProcessor.process(ProjectInfo info, File path)");
			S_LOGGER.debug("process() ProjectCode=" + info.getCode());
			S_LOGGER.debug("process() Path=" + path.getPath());
		}
		Technology technology = info.getTechnology();
		// copy pilot projects
		if (StringUtils.isNotBlank(info.getPilotProjectName())) {
			List<ProjectInfo> pilotProjects = getRepositoryManager().getPilotProjects(technology.getId());
			if (CollectionUtils.isEmpty(pilotProjects)) {
				return;
			}
			for (ProjectInfo projectInfo : pilotProjects) {
				String urls[] = projectInfo.getPilotProjectUrls();
				if (urls != null) {
					for (String url : urls) {
						DependencyUtils.extractFiles(url, path);
					}
				}
			}
		}
		updateAndroidVersion(path, info);
		POMUpdater.updatePOM(path, technology);
		AndroidTestPOMUpdater.updatePOM(path);
		if (technology.getId().equals(TechnologyTypes.ANDROID_HYBRID)) {
			extractJsLibraries(path, info.getTechnology().getJsLibraries());
		}
	}

	@Override
	protected String getModulePathKey() {
		return null;
	}

	public void updateAndroidVersion(File path, ProjectInfo info) throws PhrescoException {
		File pomPath = new File(path ,POM_FILE);
		if(!pomPath.exists()) {
			return;
		}
		PomProcessor processor;
		List<File> fileList = new ArrayList<File>();
		fileList.add(new File(path, POM_FILE));
		fileList.add(new File(path, FUNCTIONAL_TEST_POM_XML));
		fileList.add(new File(path, PERFORMANCE_TEST_POM_XML));
		fileList.add(new File(path, UNIT_TEST_POM_XML));
		for (File pomFile : fileList) {
		    if(pomFile.exists()) {
    		    try {
    	            processor = new PomProcessor(pomFile);
    	            List<String> name = info.getTechnology().getVersions();
    	            for (String string : name) {
    	                String selectedVersion = string;
    	                processor.setProperty(ANDROID_VERSION, selectedVersion);
    	            }
    	            processor.save();
    	        } catch (Exception e) {
    	            throw new PhrescoException(e);
    	        }
            }
		}
	}

	@Override
	protected String getJsLibPathKey() {
		return "android.jslib.path";
	}

}