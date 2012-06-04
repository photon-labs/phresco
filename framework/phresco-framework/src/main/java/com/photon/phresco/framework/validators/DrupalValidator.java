package com.photon.phresco.framework.validators;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.ValidationResult;
import com.photon.phresco.framework.api.Validator;

public class DrupalValidator implements Validator {
	private String DRUPAL_CMD="php -version";
	public List<ValidationResult> validate(String ProjectCode) throws PhrescoException {
		List<ValidationResult> results = new ArrayList<ValidationResult>(16);
		try {
			Commandline cl = new Commandline(DRUPAL_CMD);
			Process p = cl.execute();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			if (stdInput.readLine() == null) {
				results.add(new ValidationResult(ValidationResult.Status.ERROR, "Drupal not installed "));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<String> getAppliesTo() throws PhrescoException {
		return Collections.EMPTY_LIST;
	}
}
