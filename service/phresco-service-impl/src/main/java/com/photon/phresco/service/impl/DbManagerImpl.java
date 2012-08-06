package com.photon.phresco.service.impl;

import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ArchetypeInfo;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.DbManager;
import com.photon.phresco.service.api.DbService;
import com.photon.phresco.util.ServiceConstants;

public class DbManagerImpl extends DbService implements DbManager, ServiceConstants {

    @Override
    public ArchetypeInfo getArchetypeInfo(String techId)
            throws PhrescoException {
        Technology technology = mongoOperation.findOne(TECHNOLOGIES_COLLECTION_NAME,
                new Query(Criteria.whereId().is(techId)), Technology.class);
        return technology.getArchetypeInfo();
    }

    @Override
    public ProjectInfo getProjectInfo(String techId, String projectName)
            throws PhrescoException {
        ProjectInfo projectInfo = mongoOperation.findOne(PILOTS_COLLECTION_NAME, 
                new Query(Criteria.where(REST_QUERY_TECHID).is(techId).and(PROJECT_NAME).is(projectName)), ProjectInfo.class);
        return projectInfo;
    }

    @Override
    public Technology getTechnologyDoc(String techId)
            throws PhrescoException {
        Technology technology = mongoOperation.findOne(TECHNOLOGIES_COLLECTION_NAME, 
                new Query(Criteria.whereId().is(techId)), Technology.class);
        return technology;
    }

}
