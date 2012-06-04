package com.photon.phresco.framework.actions;

import com.photon.phresco.framework.api.AbstractActionType;

public class StartServer extends AbstractActionType {

	private static final String NAME = "start";
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