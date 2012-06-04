/*******************************************************************************
 * Copyright (c)  2012 Photon infotech.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 *  Contributors:
 *  	  Photon infotech - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.plugins.xcode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Run the xcodebuild command line program
 * @goal xcodebuild-clean
 * @phase clean
 */
public class XcodeBuildClean extends AbstractMojo {
	
	/**
	 * Location of the xcodebuild executable.
	 * @parameter expression="${xcodebuild}" default-value="/usr/bin/xcodebuild"
	 */
	private File xcodeCommandLine;
	
	/**
	 * Project Name
	 *
	 * @parameter
	 * @required
	 */
	private String xcodeProject;
	
	/**
	 * @parameter expression="${basedir}"
	 */
	private String basedir;
	
	/**
	 * Execute the xcode command line utility.
	 */
	public void execute() throws MojoExecutionException {
		if(! xcodeCommandLine.exists()){
			throw new MojoExecutionException("Invalid path, invalid xcodebuild file: " + xcodeCommandLine.getAbsolutePath());
		}
		try {
			ProcessBuilder pb = new ProcessBuilder(xcodeCommandLine.getAbsolutePath());
			pb.command().add("clean");
			if(xcodeProject != null){
				pb.command().add("-project " + xcodeProject);
			}
			pb.directory(new File(basedir,"source"));
			getLog().info("Executing command : "+pb.command());
			Process child = pb.start();
			pb.redirectErrorStream(true);
			// Consume subprocess output and write to stdout for debugging
			InputStream is = new BufferedInputStream(child.getInputStream());
			int singleByte = 0;
			while ((singleByte = is.read()) != -1) {
				//output.write(buffer, 0, bytesRead);
				System.out.write(singleByte);
			}

			child.waitFor();
			getLog().info("Exit Value: " + child.exitValue());
	    } catch (IOException e) {
			getLog().error("An IOException occured.");
			throw new MojoExecutionException("An IOException occured", e);
	    } catch (InterruptedException e){
			getLog().error("The clean process was been interrupted.");
			throw new MojoExecutionException("The clean process was been interrupted", e);
		}
	}
}
