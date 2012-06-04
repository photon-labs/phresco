package com.photon.phresco.framework.actions;

import com.photon.phresco.framework.api.AbstractActionType;
import com.photon.phresco.commons.FrameworkConstants;

public class StopSeleniumServer extends AbstractActionType {
    
    private static final String NAME = "stopSeleniumServer";
    private String type;
    
    public String getName() {
        return NAME;
    }
    
    public StringBuilder getCommand() {
		return new StringBuilder(FrameworkConstants.MVN_SELENIUM_STOP_COMMAND);
	}
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
