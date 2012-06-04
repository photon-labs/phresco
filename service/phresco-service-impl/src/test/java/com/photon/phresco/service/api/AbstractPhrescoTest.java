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

import org.junit.After;
import org.junit.Before;

import com.photon.phresco.exception.PhrescoException;

/**
 * Abstract class for all server tests.
 *
 */
public class AbstractPhrescoTest {

    ArchetypeExecutor archetypeExecutor = null;

    @Before
    public void setUp() throws PhrescoException {
        //the setup method need not static here. ignore the compilation warning.
        PhrescoServerFactory.initialize();
        archetypeExecutor = PhrescoServerFactory.getArchetypeExecutor();

        //To Initialize the cache
        PhrescoServerFactory.getRepositoryManager().getApplicationTypes();
    }

    @After
    public void tearDown() throws PhrescoException {
    }

}
