package com.photon.phresco.framework.impl;

import java.util.Comparator;

import com.photon.phresco.configuration.Environment;

public class EnvironmentComparator implements Comparator<Environment> {

    @Override
    public int compare(Environment info1, Environment info2) {
        return info1.getName().compareTo(info2.getName());
    }
}
