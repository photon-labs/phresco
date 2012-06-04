package com.photon.phresco.plugins.xcode.parser;

import java.util.List;

public class PBXNativeTarget {

    private String name;
    private String productName;
    private String appName;
    private String type;
    List<XCBuildConfiguration> buildConfigurations;

    public PBXNativeTarget(String name, String productName, String appName, String type, List<XCBuildConfiguration> buildConfigurations) {
        this.name = name;
        this.productName = productName;
        this.appName = appName;
        this.type = type;
        this.buildConfigurations = buildConfigurations;
    }

    public String getName() {
        return name;
    }

    public String getProductName() {
        return productName;
    }

    public String getAppName() {
        return appName;
    }

    public String getType() {
        return type;
    }

    public List<XCBuildConfiguration> getBuildConfigurations() {
        return buildConfigurations;
    }
}