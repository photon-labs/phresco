package com.photon.phresco.service.api;

import java.util.List;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ArchetypeInfo;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;

public interface DbManager {
    
    /**
     * Returns archetype information details based on techId
     * @param techId
     * @return
     * @throws PhrescoException
     */
    ArchetypeInfo getArchetypeInfo(String techId) throws PhrescoException;
    
    /**
     * Returns the project info based on techId and projectName
     * @param techId
     * @param projectName
     * @return
     * @throws PhrescoException
     */
    ProjectInfo getProjectInfo(String techId, String projectName) throws PhrescoException;
    
    /**
     * Returns the documents for the given technology type
     * @param techId
     * @return
     * @throws PhrescoException
     */
    Technology getTechnologyDoc(String techId) throws PhrescoException;
    
}
