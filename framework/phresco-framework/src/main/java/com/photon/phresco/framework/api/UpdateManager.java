package com.photon.phresco.framework.api;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.VersionInfo;

public interface UpdateManager {

	  VersionInfo checkForUpdate(String versionNo) throws PhrescoException;

	  void doUpdate(String newVersion) throws PhrescoException;
	 
	  String getCurrentVersion() throws PhrescoException;

}
