package com.photon.phresco.framework.api;

public interface UpgradeManager {

    String getServerVersion();

    String getFrameworkVersion();

    void isUpgradeRequired();


}
