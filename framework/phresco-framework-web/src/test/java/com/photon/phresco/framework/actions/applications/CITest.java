package com.photon.phresco.framework.actions.applications;

import static org.junit.Assert.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.CIJob;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.DiagnoseUtil;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.model.CIBuild;
import com.photon.phresco.util.Utility;

public class CITest  implements FrameworkConstants {

	private ProjectAdministrator administrator = null;
	private CI reportGen = new CI();
	boolean buildInProgress = false;
	
	//@Before
	public void setUp() throws Exception {
		administrator = PhrescoFrameworkFactory.getProjectAdministrator();
	}

	//@After
	public void tearDown() throws Exception {
	    if (administrator != null) {
	        administrator = null;
	    }
	}

	//@Test
	public void ci() {
    	try {
    		boolean jenkinsAlive = false;
    		//UI didnt trigger anybuild from here
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            Project project = (Project) administrator.getProject("PHR_aaaaaaaGitHub");
            jenkinsAlive = DiagnoseUtil.isConnectionAlive(HTTP_PROTOCOL, LOCALHOST, Integer.parseInt(reportGen.getPortNo(Utility.getJenkinsHome())));
            System.out.println("get jobs called !!!!!!!!! in CI");
            List<CIJob> existingJobs = administrator.getJobs(project);
            System.out.println("Return jobs got in CI!!!!!!!!!");
            Map<String, List<CIBuild>> ciJobsAndBuilds = new HashMap<String, List<CIBuild>>();
			if(existingJobs != null) {
				for (CIJob ciJob : existingJobs) {
		    		boolean buildJenkinsAlive = false;
		    		boolean isJobCreatingBuild = false;
		    		int noOfJobsIsInProgress = 0;
		    		List<CIBuild> builds = null;
					buildInProgress =false;
					buildJenkinsAlive = DiagnoseUtil.isConnectionAlive(HTTP_PROTOCOL, ciJob.getJenkinsUrl(), Integer.parseInt(ciJob.getJenkinsPort()));
					isJobCreatingBuild = administrator.isJobCreatingBuild(ciJob);
					if(buildJenkinsAlive == true) {
						builds = administrator.getBuilds(ciJob);
					}
					System.out.println("Job name =====> " + ciJob.getName());
					System.out.println("builds size =====> " + builds.size());
					ciJobsAndBuilds.put(ciJob.getName(), builds);
				}
			}
			System.out.println("Successssssss");
		} catch (Exception e) {
			e.printStackTrace();
        }
	}
	
	//@Test
	public void getUrlIssue() {
		try {
//			String url1 = "http://172.16.28.40:3579/ci/computer/api/xml?xpath=/computerSet/busyExecutors/text()";
			String url2 = "http://172.16.28.40:3579/ci/job/VWR Android/api/json";
			String updatedJsonUrl = URLEncoder.encode(url2, "UTF-8");
//			HttpGet httpget1 = new HttpGet(url1);
			HttpGet httpget2 = new HttpGet(updatedJsonUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
