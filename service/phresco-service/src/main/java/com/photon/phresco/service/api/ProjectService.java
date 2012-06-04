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
package com.photon.phresco.service.api;

import java.io.File;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;

/**
 * A Facade class should organize the project creation using several Managers and Processors.
 * 
 * @author arunachalam_l
 *
 */
public interface ProjectService {
	
	/**
	 * @param projectInfo
	 * @param projectPath
	 */
	File createProject(ProjectInfo projectInfo)  throws PhrescoException;
	
	/**
	 * @param projectInfo
	 * @param projectPath
	 */
	File updateProject(ProjectInfo projectInfo)  throws PhrescoException;
	
	/**
	 * @param projectInfo
	 * @param projectPath
	 * @return 
	 */
	File updateDocumentProject(ProjectInfo projectInfo)  throws PhrescoException;
	
	/**
	 * @param projectInfo
	 * @param projectPath
	 */
	void deleteProject(ProjectInfo projectInfo, File projectPath)  throws PhrescoException;

}
