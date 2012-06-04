package com.photon.phresco.framework.actions;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.framework.api.AbstractActionType;

public class Test extends AbstractActionType {
    
    private static final String NAME = "test";
    private String type;
    
    public String getName() {
        return NAME;
    }
    
    public StringBuilder getCommand() {
    	return new StringBuilder(FrameworkConstants.MVN_TEST_COMMAND);
	}
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
