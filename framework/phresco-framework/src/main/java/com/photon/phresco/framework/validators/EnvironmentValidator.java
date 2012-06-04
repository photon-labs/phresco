package com.photon.phresco.framework.validators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.DirectoryWalkListener;
import org.codehaus.plexus.util.DirectoryWalker;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.ValidationResult;
import com.photon.phresco.framework.api.Validator;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.util.Utility;

public class EnvironmentValidator implements Validator, DirectoryWalkListener {
public List<String> invalidProjects = new ArrayList<String>();
	public List<ValidationResult> validate(String ProjectCode) throws PhrescoException {
		List<ValidationResult> results = new ArrayList<ValidationResult>(16);
		String nodeJSHome = System.getenv("SONAR_HOME");
		if (StringUtils.isEmpty(nodeJSHome)) {
			results.add(new ValidationResult(ValidationResult.Status.ERROR, "Environment variable SONAR_HOME is not set"));
		} else {
			File nodeJsHomeDir = new File(nodeJSHome);
			if (!nodeJsHomeDir.exists()) {
				results.add(new ValidationResult(ValidationResult.Status.ERROR, "SONAR_HOME " + nodeJSHome + " is incorrect"));
			}

			//TODO:Execute a system command for node ... and try to see if it works
		}
		List<String> invalidproj = validateProjectCode();
		if (!invalidproj.isEmpty()) {
			results.add(new ValidationResult(ValidationResult.Status.ERROR, "projects  " + invalidproj + " is incorrect"));
		}
		return results;
	}
	
	public List<String> validateProjectCode() {
		DirectoryWalker walker = new DirectoryWalker();
		walker.setBaseDir(new File(Utility.getProjectHome()));
		walker.addDirectoryWalkListener(this);
		walker.scan();

		return invalidProjects;
	}

	@Override
	public void directoryWalkStarting(File paramFile) {
	}
	
	@Override
	public void directoryWalkStep(int percentage, File file) {
		
		BufferedReader reader = null;
		Gson gson = new Gson();
		try {
			if (file.getName().equals("project.info")) {
				reader = new BufferedReader(new FileReader(file));
				String content = reader.readLine();
				ProjectInfo projInfo = gson.fromJson(content,
						ProjectInfo.class);
				String code = projInfo.getCode();
				if (!code.equals(file.getParentFile().getParentFile().getName())) {
					invalidProjects.add(code);					
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void directoryWalkFinished() {
	}

	@Override
	public void debug(String message) {
	}
	
	public List<String> getInvalidProjects() {
		return invalidProjects;
	}
	public List<String> getAppliesTo() throws PhrescoException {
		return Collections.EMPTY_LIST;
	}
}
