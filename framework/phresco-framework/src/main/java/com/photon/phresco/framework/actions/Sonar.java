package com.photon.phresco.framework.actions;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.framework.api.AbstractActionType;

public class Sonar extends AbstractActionType {
    
    private static final String NAME = "sonar";
    private String type;
    
    public String getName() {
        return NAME;
    }
    
    public StringBuilder getCommand() {
    	return new StringBuilder(FrameworkConstants.MVN_SONAR_COMMAND);
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
