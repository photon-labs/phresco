/*
 * ###
 * Phresco Commons
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
package com.photon.phresco.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;

public class ProjectUtils implements Constants {
	public static void writeProjectInfo(ProjectInfo info, File phrescoFolder) throws PhrescoException {
		BufferedWriter out = null;
		FileWriter fstream = null;
		try {
			// create .project file inside the .phresco folder
			File projectFile = new File(phrescoFolder.getPath() + File.separator + PROJECT_INFO_FILE);
			if (!projectFile.exists()) {
				projectFile.createNewFile();
			}
			// make the .phresco folder as hidden for windows
			// for linux its enough to create the folder with '.' to make it as
			// hidden
			if (System.getProperty(OSNAME).startsWith(WINDOWS)) {
				Runtime.getRuntime().exec(
						"attrib +h " + STR_DOUBLE_QUOTES + phrescoFolder.getPath() + STR_DOUBLE_QUOTES);
			}

			// write the project info as json string into the .project file
			Gson gson = new Gson();
			String infoJSON = gson.toJson(info);
			fstream = new FileWriter(projectFile.getPath());
			out = new BufferedWriter(fstream);
			out.write(infoJSON);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fstream != null) {
					fstream.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	public static void updateProjectInfo(ProjectInfo info, File phrescoFolder) throws PhrescoException {
		// TODO Only  the use modified information should come from UI. So no filtering should be removed.
		BufferedWriter out = null;
		FileWriter fstream = null;
		BufferedReader reader = null;
		try {
			Gson gson = new Gson();
			reader = new BufferedReader(new FileReader(phrescoFolder));
			ProjectInfo projectInfos = gson.fromJson(reader, ProjectInfo.class);
			
			List<ModuleGroup> ProjectInfomodules = projectInfos.getTechnology().getModules();
			List<ModuleGroup> projectInfojsLibraries = projectInfos.getTechnology().getJsLibraries();
			
			List<ModuleGroup> selectedInfomodules = info.getTechnology().getModules();
			List<ModuleGroup> selectedInfojsLibraries = info.getTechnology().getJsLibraries();
			
			if(ProjectInfomodules != null && !ProjectInfomodules.isEmpty() && selectedInfomodules != null) {
				selectedInfomodules.addAll(ProjectInfomodules);	
				info.getTechnology().setModules(selectedInfomodules);
			}else if (ProjectInfomodules != null) {
				info.getTechnology().setModules(ProjectInfomodules);
			}
			if(projectInfojsLibraries != null && !projectInfojsLibraries.isEmpty() && selectedInfojsLibraries != null) {
			    selectedInfojsLibraries.addAll(projectInfojsLibraries); 
			    info.getTechnology().setJsLibraries(selectedInfojsLibraries);
            }else if (projectInfojsLibraries != null) {
				info.getTechnology().setJsLibraries(projectInfojsLibraries);
			}
			String infoJSON = gson.toJson(info);
			fstream = new FileWriter(phrescoFolder.getPath());
			out = new BufferedWriter(fstream);
			out.write(infoJSON);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			Utility.closeStream(reader);
			try {
				if (out != null) {
					out.close();
				}
				if (fstream != null) {
					fstream.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	
	
}
