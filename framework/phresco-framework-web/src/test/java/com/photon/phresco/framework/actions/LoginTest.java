package com.photon.phresco.framework.actions;

import static org.junit.Assert.fail;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.api.ProjectRuntimeManager;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.util.Credentials;

public class LoginTest implements FrameworkConstants{

	private ProjectAdministrator administrator = null;
	private ProjectRuntimeManager runtimeManager = null;
	
	@Before
	public void setUp() throws Exception {
		administrator = PhrescoFrameworkFactory.getProjectAdministrator();
		runtimeManager = PhrescoFrameworkFactory.getProjectRuntimeManager();
	}

	@After
	public void tearDown() throws Exception {
	    if (administrator != null) {
	        administrator = null;
	    }
	    
	    if (runtimeManager != null) {
	    	runtimeManager = null;
	    }
	}
	
	
	//@Test
	public void test() {
        try {
        	UserInfo userInfo = null;
            String username = "kaleeswaran_s";
			String password = "Suresh@123";
			Credentials credentials = new Credentials(username, password);
            userInfo = administrator.doLogin(credentials);
            
//            if (!userInfo.isLoginValidation()) {
//            	System.out.println("Login failure1!!!!!!");
//            }
            if (!userInfo.isPhrescoEnabled()) {
            	System.out.println("Login failure2!!!!!!");
            	
            } else {
            	System.out.println("Login SUCCESS!!!!!!" + userInfo.getDisplayName());
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("Login failure3!!!!!!");
        }
	}

//	@Test
	public void copyFileAndFolder() {
		try {
			File srcDir = new File("/Users/kaleeswaran/workspace/projects/PHR_Walgreens_latest/make/2012-09-17-1");
			File destDir = new File("/Users/kaleeswaran/workspace/projects/PHR_Walgreens_latest/do_not_checkin/static-analysis-report");
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
