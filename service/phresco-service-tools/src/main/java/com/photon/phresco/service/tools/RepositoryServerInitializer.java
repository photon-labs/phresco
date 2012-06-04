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
package com.photon.phresco.service.tools;

import java.io.File;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;

public class RepositoryServerInitializer {

    RepositoryManager repManager = null;

    RepositoryServerInitializer() throws PhrescoException {
    	PhrescoServerFactory.initialize(); 
    }

    public void setup(File inputExcelDir, File outputDir,File binariesDir) throws PhrescoException {
    	boolean overwrite = true;
    	System.out.println("Adding application types");
//    	ApptypeGenerator apptypeGenerator = new ApptypeGenerator();
//    	apptypeGenerator.publish();
//
//    	//Add the settings template
//    	System.out.println("Generating config data");
//    	ConfigDataGenerator configGen = new ConfigDataGenerator(inputExcelDir, outputDir);
//    	configGen.publish(overwrite);
//    	
//    	//Add the admin config
//        System.out.println("Generating Admin Config");
//        AdminConfigGenerator adminGen = new AdminConfigGenerator(inputExcelDir, outputDir);
//        adminGen.publish(overwrite);
        
    	//Publish technology related modules
    	System.out.println("Generating technology dependencies");
    	TechnologyDataGenerator dataGen = new TechnologyDataGenerator(inputExcelDir , outputDir, binariesDir);
    	dataGen.publish();
    	
    	//Publish JsLibraries
    	System.out.println("Generating JsLibraries");
    	JsLibraryGenerator jsgen = new JsLibraryGenerator(inputExcelDir, outputDir, binariesDir);
    	jsgen.publish();
    	
    	System.out.println("Adding videos");
    	// Add Videos
    	VideoInfoGenerator videoGen = new VideoInfoGenerator(inputExcelDir, outputDir,binariesDir);
    	videoGen.publish();
    	
    	//Add the DownloadInfos
        System.out.println("Generating download Infos");
        SoftwareDownloadGenerator downGen = new SoftwareDownloadGenerator(inputExcelDir, outputDir,binariesDir);
        downGen.publish();
    	
    }

    public static void main(String[] args) throws PhrescoException {
    	
    	File inputExcelDir = new File(args[0], "files");
    	File outputDir = new File(args[0], "output");
    	File binariesFileDir = new File(args[1]);

//    	File inputExcelDir = new File("D:\\work\\phresco\\agra\\service\\trunk\\phresco-service-runner\\delivery\\tools\\", "files");
//    	File outputDir = new File("D:\\work\\phresco\\agra\\service\\trunk\\phresco-service-runner\\delivery\\tools\\", "output");
//    	File binariesFileDir = new File("D:\\work\\phresco\\Phresco-binaries\\");
    	
    	System.out.println("Execution starts..........");
    	RepositoryServerInitializer server = new RepositoryServerInitializer();
    	server.setup(inputExcelDir, outputDir,binariesFileDir);
    	
    }

}
