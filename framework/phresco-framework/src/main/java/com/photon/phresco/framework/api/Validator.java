package com.photon.phresco.framework.api;

import java.util.List;

import com.photon.phresco.exception.PhrescoException;

public interface Validator {

	/**
	 * Runs the validation and returns the results
	 * @return
	 * @throws PhrescoException
	 */
	List<ValidationResult> validate(String projectCode) throws PhrescoException;

	/**
	 * Returns the technologies it applies
	 * @return Returns the list of technologies applies to, if it is empty then it applies to all
	 * @throws PhrescoException
	 */
	List<String> getAppliesTo() throws PhrescoException;

}
