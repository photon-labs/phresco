package com.photon.phresco.framework.impl;

import java.util.Comparator;

import com.photon.phresco.model.ModuleGroup;

public class ModuleComparator implements Comparator<ModuleGroup> {

    @Override
    public int compare(ModuleGroup o1, ModuleGroup o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
