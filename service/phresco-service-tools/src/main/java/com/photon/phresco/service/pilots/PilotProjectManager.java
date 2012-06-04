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

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;

/*the class PilotProjectManager*/
public class PilotProjectManager {

    private RepositoryManager repositoryManager;

    public static final String PILOT_BASE = "D:\\work\\projects\\phresco\\source\\phresco-projects\\trunk";

    public static final String PILOT_GROUP = "pilots";

    /* new PilotProjectManager instantiated */
    public PilotProjectManager() {
        super();
        try {
            PhrescoServerFactory.initialize();
        } catch (PhrescoException e) {
        }
        this.repositoryManager = PhrescoServerFactory.getRepositoryManager();
    }

    /* gets and returns repository manager */
    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    /* Authenticates to deploy artifacts to repository */
    public static void main(String[] args) {
        PilotProjectManager manager = new PilotProjectManager();
        IPilotProject[] pilots = new IPilotProject[] {
                new PhpPilotProject(manager.getRepositoryManager()),
                new DrupalPilotProject(manager.getRepositoryManager()),
                new SharepointPilotProject(manager.getRepositoryManager()),
                new AndroidPilotProject(manager.getRepositoryManager()),
                new IphonePilotProject(manager.getRepositoryManager()),
                new NodejsPilotProject(manager.getRepositoryManager()), };

        try {
            for (IPilotProject project : pilots) {
//                project.buildPilotProject();
//                project.publishPilotProject();
                project.publishMetaData();
            }
        } catch (PhrescoException e) {
            e.printStackTrace();
        }
    }

}
