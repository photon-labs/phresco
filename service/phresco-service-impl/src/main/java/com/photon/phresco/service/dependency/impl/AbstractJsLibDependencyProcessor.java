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
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.jaxb.Library;

public abstract class AbstractJsLibDependencyProcessor extends AbstractDependencyProcessor {
	private static final Logger S_LOGGER = Logger.getLogger(AbstractJsLibDependencyProcessor.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	public AbstractJsLibDependencyProcessor(RepositoryManager repoManager) {
		super(repoManager);
	}
	@Override
	public void process(ProjectInfo info, File path) throws PhrescoException {
		super.process(info, path);
		extractJsLibraries(path, info.getTechnology().getJsLibraries());
	}

	@Override
	protected String getModulePathKey() {
		return null;
	}

	protected void extractJsLibraries(File path, List<ModuleGroup> jsLibraries) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method AbstractJsLibDependencyProcessor.extractJsLibraries(File path, List<TupleBean> jsLibraries)");
			S_LOGGER.debug("extractJsLibraries() Filepath="+path.getPath());
		}

		// should be implemented by clients who requires js libraries.
		if (CollectionUtils.isEmpty(jsLibraries)) {
			return;
		}
		File libPath = path;
		String jsLibPathKey = getJsLibPathKey();
		if(!StringUtils.isEmpty(jsLibPathKey)) {
			String modulesPathString=DependencyProcessorMessages.getString(jsLibPathKey);
			libPath = new File(path, modulesPathString);
		}

		for (ModuleGroup tupleBean : jsLibraries) {
			Library library = getRepositoryManager().getJsLibrary(tupleBean.getId());

			if (library == null) {
				S_LOGGER.warn("JS library not found " + tupleBean.getId() + "  --> Path" + path.getPath());
			}

			String contentURL = library.getContentURL();
			if(!StringUtils.isEmpty(contentURL)){
				DependencyUtils.extractFiles(contentURL, libPath);
			}
		}
	}

	protected abstract String getJsLibPathKey();
}
