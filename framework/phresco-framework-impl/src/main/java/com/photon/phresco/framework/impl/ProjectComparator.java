package com.photon.phresco.framework.impl;

import java.util.Comparator;

import com.photon.phresco.framework.api.Project;

public class ProjectComparator implements Comparator<Project> {

    @Override
    public int compare(Project project1, Project project2) {
        return project1.getProjectInfo().getName().compareToIgnoreCase(project2.getProjectInfo().getName());
    }
}
