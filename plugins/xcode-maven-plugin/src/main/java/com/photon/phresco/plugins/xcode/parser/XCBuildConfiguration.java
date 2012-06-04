package com.photon.phresco.plugins.xcode.parser;

import java.io.File;

public class XCBuildConfiguration {

    private String name;
    private File infoPlist;

    public XCBuildConfiguration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public File getInfoPlist() {
        return infoPlist;
    }

    public void setInfoPlist(File infoPlist) {
        this.infoPlist = infoPlist;
    }
}