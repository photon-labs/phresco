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

public class NodejsPilotProject implements IPilotProject {

	private static final String NODE_JS_BUILD_FILE = "nodejs-webservice\\eshop\\build.xml";
	private RepositoryManager repManager = null;

	/* new NodejsProject instantiated */
	public NodejsPilotProject(RepositoryManager repoManager) {
		this.repManager = repoManager;
	}

	/* command line execution */
	public void buildPilotProject() throws PhrescoException {
		try {
			Commandline cl = new Commandline("ant -buildfile " + PilotProjectManager.PILOT_BASE + NODE_JS_BUILD_FILE);
			cl.execute();
		} catch (CommandLineException e) {
			throw new PhrescoException(e);
		}

	}

	/* publish NodejsProject to desired path */
	public void publishPilotProject() throws PhrescoException {
		ArtifactInfo info = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.NODE_JS_WEBSERVICE, "", "zip", "0.1");
		File artifact = new File(PilotProjectManager.PILOT_BASE + "\\nodejs-webservice\\eshop\\build\\eshop.zip");
		repManager.addArtifact(info, artifact);
	}

	/* NodejsProject json data creation */
	public void publishMetaData() throws PhrescoException {
		ProjectInfo info = new ProjectInfo();
		info.setName("eshop");
		info.setCode("PHTN_NODEJS_eshop-WebService");
		info.setDescription("Electronic Shopping application for Node JS Web services");
		info.setApplication("apptype-webservices");

		Technology tech = new Technology();
		tech.setId(TechnologyTypes.NODE_JS_WEBSERVICE);
		tech.setId("tech-nodejs-webservice");
		tech.setName("NodeJS Web Service");
//		List<TupleBean> modules = new ArrayList<TupleBean>();
//		modules.add(new TupleBean("mod_express_2.4.3", "Express", "2.4.3"));
//		modules.add(new TupleBean("mod_sequelize_1.2.1", "Sequelize", "1.2.1"));
//		modules.add(new TupleBean("mod_underscore_1.2.2", "underscore", "1.2.2"));
//		modules.add(new TupleBean("mod_underscore.string_1.2.0", "underscore.string", "1.2.0"));
//		modules.add(new TupleBean("mod_lingo_0.0.4", "lingo", "0.0.4"));
//		modules.add(new TupleBean("mod_moment_1.1.2", "moment", "1.1.2"));
//		modules.add(new TupleBean("mod_validator_0.3.5", "validator", "0.3.5"));
//		tech.setModules(modules);

		tech.setFrameworks(null);
		info.setTechnology(tech);
		info.setPilotProjectUrls(new String[] { "/pilots/tech-nodejs-webservice/0.1/tech-nodejs-webservice-0.1.zip" });
		Gson gson = new Gson();
		String json = gson.toJson(info);
		try {
			FileWriter write = new FileWriter("nodeJson.pilots");
			write.write(json);
			write.close();
			ArtifactInfo aInfo = new ArtifactInfo(PilotProjectManager.PILOT_GROUP, TechnologyTypes.NODE_JS_WEBSERVICE, "", "pilots", "0.1");
			File artifact = new File("nodeJson.pilots");
			repManager.addArtifact(aInfo, artifact);

		} catch (IOException e) {
			throw new PhrescoException(e);
		}

	}

}
