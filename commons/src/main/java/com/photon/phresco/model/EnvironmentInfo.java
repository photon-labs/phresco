package com.photon.phresco.model;

import java.util.List;

public class EnvironmentInfo {
    private List<SettingsInfo> settingsInfos;
    
    public EnvironmentInfo() {
        super();
    }
    
    public EnvironmentInfo(List<SettingsInfo> settingsInfos) {
        this.settingsInfos = settingsInfos;
    }

    public List<SettingsInfo> getSettingsInfos() {
        return settingsInfos;
    }

    public void setSettingsInfos(List<SettingsInfo> settingsInfos) {
        this.settingsInfos = settingsInfos;
    }
}
