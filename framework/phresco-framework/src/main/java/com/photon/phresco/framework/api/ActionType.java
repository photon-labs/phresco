package com.photon.phresco.framework.api;

import java.util.HashMap;
import java.util.Map;

import com.photon.phresco.framework.actions.AndroidPerfCommand;
import com.photon.phresco.framework.actions.Build;
import com.photon.phresco.framework.actions.Deploy;
import com.photon.phresco.framework.actions.IphoneCodeValidate;
import com.photon.phresco.framework.actions.IphoneIpa;
import com.photon.phresco.framework.actions.IPhoneFunctionalCommand;
import com.photon.phresco.framework.actions.IphoneBuildAndUnitTest;
import com.photon.phresco.framework.actions.JenkinsSetup;
import com.photon.phresco.framework.actions.JenkinsStart;
import com.photon.phresco.framework.actions.JenkinsStop;
import com.photon.phresco.framework.actions.MobileCommand;
import com.photon.phresco.framework.actions.SharepointNUnitTest;
import com.photon.phresco.framework.actions.Sonar;
import com.photon.phresco.framework.actions.StartServer;
import com.photon.phresco.framework.actions.StopSeleniumServer;
import com.photon.phresco.framework.actions.StopServer;
import com.photon.phresco.framework.actions.Test;

public interface ActionType {
    
 Map<String, String> PLUGINMAP = new HashMap<String, String>();

    ActionType BUILD = new Build();
    ActionType DEPLOY = new Deploy();
    ActionType TEST = new Test();
    ActionType SHAREPOINT_NUNIT_TEST = new SharepointNUnitTest();
    ActionType STOP_SELENIUM_SERVER = new StopSeleniumServer();
    ActionType SONAR = new Sonar();
    ActionType JENKINS_SETUP = new JenkinsSetup();
    ActionType JENKINS_START = new JenkinsStart();
    ActionType JENKINS_STOP = new JenkinsStop();
    ActionType START_SERVER = new StartServer();
    ActionType STOP_SERVER = new StopServer();
    ActionType INSTALL = new MobileCommand();
    ActionType MOBILE_COMMON_COMMAND = new MobileCommand();
    ActionType ANDROID_PERF_COMMAND = new AndroidPerfCommand();
    ActionType IPHONE_DOWNLOADIPA_COMMAND = new IphoneIpa();
    ActionType IPHONE_FUNCTIONAL_COMMAND = new IPhoneFunctionalCommand();
    ActionType IPHONE_BUILD_UNIT_TEST = new IphoneBuildAndUnitTest();
    ActionType IPHONE_CODE_VALIDATE = new IphoneCodeValidate();
    
    StringBuilder getCommand();
    
    String getName();
    
    String getType();
    
    void setType(String type);
    
    String getWorkingDirectory();
    
    void setWorkingDirectory(String workingDirectory);
    
    boolean canHideLog();
    
    void setHideLog(boolean hideLog);
    
    boolean canShowError();
    
    void setShowError(boolean showError);
}
