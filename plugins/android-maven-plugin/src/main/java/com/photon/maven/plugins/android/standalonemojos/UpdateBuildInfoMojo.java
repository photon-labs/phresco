/*
 * ###
 * Android Maven Plugin - android-maven-plugin
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
/*
 * Copyright (C) 2009 Jayway AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.photon.maven.plugins.android.standalonemojos;

import static com.photon.maven.plugins.android.common.AndroidExtension.APK;
import static com.photon.maven.plugins.android.common.AndroidExtension.APKLIB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.archiver.zip.ZipArchiver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.maven.plugins.android.AbstractAndroidMojo;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.pom.AndroidTestPOMUpdater;
import com.photon.phresco.model.BuildInfo;
import com.photon.phresco.util.PluginUtils;
import com.photon.phresco.util.TechnologyTypes;

/**
 * Creates the apk file. By default signs it with debug keystore.<br/>
 * Change that by setting configuration parameter
 * <code>&lt;sign&gt;&lt;debug&gt;false&lt;/debug&gt;&lt;/sign&gt;</code>.
 *
 * @goal updatebuildinfo
 * @phase package
 * @requiresDependencyResolution apk
 */
public class UpdateBuildInfoMojo extends AbstractAndroidMojo {

	

	/*
	 * <p>
	 * Additional source directories that contain resources to be packaged into
	 * the apk.
	 * </p>
	 * <p>
	 * These are not source directories, that contain java classes to be
	 * compiled. It corresponds to the -df option of the apkbuilder program. It
	 * allows you to specify directories, that contain additional resources to
	 * be packaged into the apk.
	 * </p>
	 * So an example inside the plugin configuration could be:
	 *
	 * <pre>
	 * &lt;configuration&gt;
	 * 	  ...
	 *    &lt;sourceDirectories&gt;
	 *      &lt;sourceDirectory&gt;${project.basedir}/additionals&lt;/sourceDirectory&gt;
	 *   &lt;/sourceDirectories&gt;
	 *   ...
	 * &lt;/configuration&gt;
	 * </pre>
	 *
	 * @parameter expression="${android.sourceDirectories}" default-value=""
	 */
//	private File[] sourceDirectories;

	
	/**
	 * Build location
	 *
	 * @parameter expression="/do_not_checkin/build"
	 */
	private String buildDirectory;

	private File buildDir;
	
	private File buildInfoFile;
	private List<BuildInfo> buildInfoList;
	private int nextBuildNo;
	private Date currentDate;
	private String apkFileName;
	private String deliverable;


	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		File outputFile = null, destFile = null;
		String techId = null;
		try {
			
			getLog().info("Base Dir === "  + baseDir.getAbsolutePath());
			File root = null;
			Project currentProject;
			buildInfoList = new ArrayList<BuildInfo>(); // initialization
			
			buildDir = new File(baseDir.getPath() + buildDirectory);
			if (!buildDir.exists()) {
				buildDir.mkdir();
				getLog().info("Build directory created..." + buildDir.getPath());
			}
			
			ProjectAdministrator projAdmin = PhrescoFrameworkFactory.getProjectAdministrator();
			if (baseDir.getPath().endsWith("unit") || baseDir.getPath().endsWith("functional") || baseDir.getPath().endsWith("performance")) {
				root = baseDir.getParentFile().getParentFile();
				currentProject = projAdmin.getProjectByWorkspace(root);
			} else {
				currentProject = projAdmin.getProjectByWorkspace(baseDir);	
			}
			techId = currentProject.getProjectInfo().getTechnology().getId();
			
			buildInfoFile = new File(buildDir.getPath() + "/build.info");

			nextBuildNo = generateNextBuildNo();

			currentDate = Calendar.getInstance().getTime();
		} catch (IOException e) {
			if (techId.equals(TechnologyTypes.ANDROID_NATIVE) || techId.equals(TechnologyTypes.ANDROID_HYBRID)) {
				throw new MojoFailureException("APK could not initialize " + e.getLocalizedMessage());
			} else if (techId.equals(TechnologyTypes.ANDROID_LIBRARY)) {
				throw new MojoFailureException("APKLib could not initialize " + e.getLocalizedMessage());
			} 
			
		} catch (PhrescoException e) {
			throw new MojoFailureException(e.getLocalizedMessage());
		}
		
		
		if (techId.equals(TechnologyTypes.ANDROID_NATIVE) || techId.equals(TechnologyTypes.ANDROID_HYBRID)) {
			// 	Initialize apk build configuration
			outputFile = new File(project.getBuild().getDirectory(), project.getBuild().getFinalName() + '.' + APK);
		} else if (techId.equals(TechnologyTypes.ANDROID_LIBRARY)) {
			// 	Initialize apklib build configuration
			outputFile = new File(project.getBuild().getDirectory(), project.getBuild().getFinalName() + '.' + APKLIB);
		} 

		
		if (outputFile.exists()) {

			try {
				String buildName = project.getBuild().getFinalName() + '_' + getTimeStampForBuildName(currentDate);
				
				if (techId.equals(TechnologyTypes.ANDROID_NATIVE) || techId.equals(TechnologyTypes.ANDROID_HYBRID)) {
					getLog().info("APK created.. Copying to Build directory.....");
					destFile = new File(buildDir, buildName + '.' + APK);
				} else if (techId.equals(TechnologyTypes.ANDROID_LIBRARY)) {
					getLog().info("APKLib created.. Copying to Build directory.....");
					destFile = new File(buildDir, buildName + '.' + APKLIB);
				} 
				
				FileUtils.copyFile(outputFile, destFile);
				getLog().info("copied to..." + destFile.getName());
				apkFileName = destFile.getName();

				getLog().info("Creating deliverables.....");
				ZipArchiver zipArchiver = new ZipArchiver();
				File inputFile = new File(apkFileName);
				zipArchiver.addFile(destFile, destFile.getName());
				File deliverableZip = new File(buildDir, buildName + ".zip");
				zipArchiver.setDestFile(deliverableZip);
				zipArchiver.createArchive();

				deliverable = deliverableZip.getName();
				getLog().info("Deliverables available at " + deliverableZip.getName());
				writeBuildInfo(true);
				
				if (techId.equals(TechnologyTypes.ANDROID_NATIVE) || techId.equals(TechnologyTypes.ANDROID_HYBRID)) {
					Boolean status = AndroidTestPOMUpdater.updatePOM(new File(baseDir.getPath().toString()));
					if (Boolean.TRUE.equals(status)) {
						getLog().info("  Test project pom updated successfully ");
					}
				}
			} catch (IOException e) {
				throw new MojoExecutionException("Error in writing output...");
			} catch (PhrescoException e) {
				throw new MojoExecutionException("Error in writing output...");
			} 

		}
	}

	private void writeBuildInfo(boolean isBuildSuccess) throws MojoExecutionException {
		try {
			PluginUtils pu = new PluginUtils();
			BuildInfo buildInfo = new BuildInfo();
			List<String> envList = pu.csvToList(environmentName);
			buildInfo.setBuildNo(nextBuildNo);
			buildInfo.setTimeStamp(getTimeStampForDisplay(currentDate));
			if (isBuildSuccess) {
				buildInfo.setBuildStatus("SUCCESS");
			} else {
				buildInfo.setBuildStatus("FAILURE");
			}
			buildInfo.setBuildName(apkFileName);
			buildInfo.setDeliverables(deliverable);
			buildInfo.setEnvironments(envList);
			buildInfoList.add(buildInfo);
			Gson gson = new Gson();
			FileWriter writer = new FileWriter(buildInfoFile);
			gson.toJson(buildInfoList, writer);

			writer.close();
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private String getTimeStampForDisplay(Date currentDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
		String timeStamp = formatter.format(currentDate.getTime());
		return timeStamp;
	}

	private String getTimeStampForBuildName(Date currentDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss");
		String timeStamp = formatter.format(currentDate.getTime());
		return timeStamp;
	}
	
	private int generateNextBuildNo() throws IOException {
		int nextBuildNo = 1;
		if (!buildInfoFile.exists()) {
			return nextBuildNo;
		}

		BufferedReader read = new BufferedReader(new FileReader(buildInfoFile));
		String content = read.readLine();


		Gson gson = new Gson();
		java.lang.reflect.Type listType = new TypeToken<List<BuildInfo>>() {
		}.getType();
		buildInfoList = (List<BuildInfo>) gson.fromJson(content, listType);
		if (buildInfoList == null || buildInfoList.size() == 0) {
			return nextBuildNo;
		}

		int buildArray[] = new int[buildInfoList.size()];
		int count = 0;
		for (BuildInfo buildInfo : buildInfoList) {
			buildArray[count] = buildInfo.getBuildNo();
			count++;
		}

		Arrays.sort(buildArray); // sort to the array to find the max build no

		nextBuildNo = buildArray[buildArray.length - 1] + 1; // increment 1 to the max in the build list
		return nextBuildNo;
	}

}
