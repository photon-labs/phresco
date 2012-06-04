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
package com.photon.phresco.service.pilots;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;
import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.util.TechnologyTypes;

public class IphonePilotProject implements IPilotProject {
	private static final String IPHONE_BUILD_FILE = "iphone\\Phresco\\build.xml";

	private RepositoryManager repManager = null;

	/* new Iphone pilot project Instantiated */
	public IphonePilotProject(RepositoryManager repoManager) {
		this.repManager = repoManager;
	}

	/* command line execution */
	public void buildPilotProject() throws PhrescoException {
		try {
			Commandline cl = new Commandline("ant -buildfile " + PilotProjectManager.PILOT_BASE + IPHONE_BUILD_FILE);
			cl.execute();
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		}

	}

	/* publish IphonePilotProject to desired path */
	public void publishPilotProject() throws PhrescoException {
		ArtifactInfo info = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.IPHONE_NATIVE, "", "zip", "0.1");
		File artifact = new File(PilotProjectManager.PILOT_BASE + "\\iphone\\Phresco\\build\\iphone.zip");
		repManager.addArtifact(info, artifact);
	}

	/* IphonePilotProject json data creation */
	public void publishMetaData() throws PhrescoException {
		ProjectInfo info = new ProjectInfo();
		info.setName("eshop");
		info.setCode("PHTN_iPhone_eshop");
		info.setDescription("Electronic Shopping application for iPhone");
		info.setApplication("apptype-mobile");

		Technology tech = new Technology();
		tech.setId("tech-iphone-native");
		tech.setName("iPhone Native");

		tech.setFrameworks(null);

		info.setTechnology(tech);
		info.setPilotProjectUrls(new String[] { "/pilots/tech-iphone-native/0.1/tech-iphone-native-0.1.pilots" });
		Gson gson = new Gson();

		String json = gson.toJson(info);

		try {
			FileWriter write = new FileWriter("iphoneJson.pilots");
			write.write(json);
			write.close();
			ArtifactInfo aInfo = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.IPHONE_NATIVE, "", "pilots", "0.1");
			File artifact = new File("iphoneJson.pilots");
			repManager.addArtifact(aInfo, artifact);

		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}

}
