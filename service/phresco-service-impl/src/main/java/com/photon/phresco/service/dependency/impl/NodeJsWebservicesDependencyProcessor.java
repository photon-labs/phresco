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

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.RepositoryManager;

public class NodeJsWebservicesDependencyProcessor extends DefaultDependencyProcessor {
	private static final Logger S_LOGGER = Logger.getLogger(NodeJsWebservicesDependencyProcessor.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	public NodeJsWebservicesDependencyProcessor(RepositoryManager repoManager) {
		super(repoManager);
	}

	@Override
	protected String getModulePathKey() {
		return "nodejsws.modules.path";
	}

	@Override
	public void process(ProjectInfo info, File path) throws PhrescoException {
		super.process(info, path);
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method NodeJsWebservicesDependencyProcessor.process(ProjectInfo info, File path)");
			S_LOGGER.debug("process() Path=" + path.getPath());
		}
		createSqlFolder(info, path);
	}

}
