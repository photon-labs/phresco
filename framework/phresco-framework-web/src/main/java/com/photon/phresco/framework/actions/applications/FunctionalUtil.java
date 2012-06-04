package com.photon.phresco.framework.actions.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.util.Utility;

public class FunctionalUtil {
	private static final Logger S_LOGGER = Logger.getLogger(FunctionalUtil.class);
	private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
    
    public static void adaptTestConfig(Project project, String envName, String browser) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method DrupalFunctionalUtil.adaptTestConfig(Project project, SettingsInfo serverDetails, String browser)");
		}
    	if (debugEnabled) {
			S_LOGGER.debug("adaptTestConfig() ProjectInfo = "+project.getProjectInfo());
		}
        InputStream is = null;
        OutputStream os = null;
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        builder.append(project.getProjectInfo().getCode());
        builder.append(FrameworkUtil.getInstance().getFuncitonalAdaptDir(project.getProjectInfo().getTechnology().getId()));
     
        try {
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            administrator.updateTestConfiguration(project, envName, browser, builder.toString());
        } catch (Exception e) {
            throw new PhrescoException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
