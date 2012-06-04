package com.photon.phresco.plugins.xcode.parser;

import java.util.List;

public class PBXProject {

    private List<PBXNativeTarget> targets;

    public PBXProject(List<PBXNativeTarget> targets) {
        this.targets = targets;
    }

    public List<PBXNativeTarget> getTargets() {
        return targets;
    }
}