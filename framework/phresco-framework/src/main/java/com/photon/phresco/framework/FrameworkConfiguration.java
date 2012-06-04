package com.photon.phresco.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;

public class FrameworkConfiguration implements FrameworkConstants {

    private Properties frameworkConfig;

    public FrameworkConfiguration(String fileName) throws PhrescoException {
        initFrameworkConfig(fileName);
    }

    private void initFrameworkConfig(String fileName) throws PhrescoException {
        InputStream stream = null;
        stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        frameworkConfig = new Properties();
        try {
            frameworkConfig.load(stream);

        } catch (IOException e) {
            throw new PhrescoException(e);
        }
    }

    public String getServerPath() {
        return frameworkConfig.getProperty(PHRESCO_SERVER_URL);
    }

    public String getCodePrefix() {
        return frameworkConfig.getProperty(PHRESCO_CODE_PREFIX);
    }

    public String getFuctionalTestSuitePath(){
        return frameworkConfig.getProperty(PHRESCO_FUNCTIONAL_TESTSUITE_PATH);
    }

    public String getReportsPath(){
        return frameworkConfig.getProperty(PHRESCO_REPORTS_PATH);
    }

    public boolean isCacheEnabled(){
        String isCacheStr = frameworkConfig.getProperty(PHRESCO_CACHE_ENABLED);
        return Boolean.parseBoolean(isCacheStr);
    }

    public String getSonarReportPath() {
        return frameworkConfig.getProperty(PHRESCO_SONAR_REPORT_PATH);
    }
    
    public String getSonarUrl() {
        return frameworkConfig.getProperty(PHRESCO_SONAR_URL);
    }

    public String getCIServerPath() {
        return frameworkConfig.getProperty(PHRESCO_CI_JENKINS_URL);
    }
}
