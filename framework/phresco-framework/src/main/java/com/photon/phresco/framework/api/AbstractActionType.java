/*
 * ###
 * Phresco Framework
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
package com.photon.phresco.framework.api;

public abstract class AbstractActionType implements ActionType {
    public String workingDirectory = null;
    public boolean hideLog = false;
    public boolean showError = false;
	public boolean skipTest = false;
    public boolean showDebug = false;
    public String profileId = "";
    
    public String getWorkingDirectory() {
        return workingDirectory;
    }
    
    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
    
    public boolean canShowError() {
        return showError;
    }
    
    public void setShowError(boolean showError) {
        this.showError = showError;
    }
    
    public boolean canHideLog() {
        return hideLog;
    }
    
    public void setHideLog(boolean showLog) {
        this.hideLog = showLog;
    }

	public boolean canSkipTest() {
        return skipTest;
    }
    
    public void setSkipTest(boolean skipTest) {
        this.skipTest = skipTest;
    }
    
    public boolean canShowDebug() {
        return showDebug;
    }
    
    public void setShowDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
}
