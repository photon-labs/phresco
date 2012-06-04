package com.photon.phresco.framework.api;

public abstract class AbstractActionType implements ActionType {
    public String workingDirectory = null;
    public boolean hideLog = false;
    public boolean showError = false;
    
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
}
