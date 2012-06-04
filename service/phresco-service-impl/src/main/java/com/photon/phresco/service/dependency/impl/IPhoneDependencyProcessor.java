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

import com.photon.phresco.service.api.RepositoryManager;

public class IPhoneDependencyProcessor extends AbstractJsLibDependencyProcessor {

	public IPhoneDependencyProcessor(RepositoryManager repoManager) {
		super(repoManager);
	}


	@Override
	protected String getModulePathKey() {
		return "iphone.modules.path";
	}


	@Override
	protected String getJsLibPathKey() {
		return "iphone.jslib.path";
	}
}
