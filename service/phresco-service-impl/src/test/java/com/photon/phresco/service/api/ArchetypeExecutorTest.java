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

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.util.FileUtil;

public class ArchetypeExecutorTest extends AbstractPhrescoTest {

	private String PHP_PROJECT_NAME = "phpproject";
	private String PHTN_PHP_PRJ = "PHTN_PHP_PRJ";
	private File projectFolder = null;

	@Before
	public void setUp() throws PhrescoException {
		super.setUp();
	}

	@After
	public void tearDown() throws PhrescoException {
		super.tearDown();
		if (projectFolder != null) {
			FileUtil.delete(projectFolder);
		}
	}

	@Test
	public final void testExecute() throws PhrescoException {
		//test for phresco-php-archetype
		projectFolder = archetypeExecutor.execute(createPHPProjectInfo());
		String[] fileList = projectFolder.list();
		for (String fileName : fileList) {
			assertEquals(PHTN_PHP_PRJ, fileName);
		}
	}

	private ProjectInfo createPHPProjectInfo() {
		ProjectInfo info = new ProjectInfo();
		info.setName(PHP_PROJECT_NAME);
		info.setDescription("PHP Project created using Phresco");
		info.setCode(PHTN_PHP_PRJ);
		info.setVersion("1.0.0");
		Technology technology = new Technology("tech-php", "PHP");
		info.setTechnology(technology);
		return info;
	}
}
