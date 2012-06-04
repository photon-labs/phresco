package com.photon.phresco.framework.actions;

import com.photon.phresco.framework.api.AbstractActionType;

public class Deploy extends AbstractActionType {
    private static final String NAME = "deploy";
    private String type;
    
    public String getName() {
        return NAME;
    }
    
    public StringBuilder getCommand() {
		return null;
	}
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
