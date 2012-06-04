package com.photon.phresco.framework.impl;

import java.util.Comparator;

import com.photon.phresco.model.SettingsInfo;

public class SettingsInfoComparator implements Comparator<SettingsInfo> {

    @Override
    public int compare(SettingsInfo info1, SettingsInfo info2) {
        return info1.getName().compareTo(info2.getName());
    }
}
