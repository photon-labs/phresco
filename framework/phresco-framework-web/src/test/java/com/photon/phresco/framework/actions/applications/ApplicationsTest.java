package com.photon.phresco.framework.actions.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.lib.Ref;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.util.Utility;
import com.phresco.pom.model.Scm;
import com.phresco.pom.util.PomProcessor;

public class ApplicationsTest {

	private String url = "https://github.com/vivekraja-v/PHR_Phpblog.git";
	private String tempDirectory = Utility.getPhrescoTemp();
	private String phrescoHomeDirectory = Utility.getPhrescoHome();
    private static final String PROJECT_INFO = "project.info";
    private static final String SVN_CHECKOUT_TEMP = "svn-checkout-temp";
    private static final String PHRESCO = "/.phresco";
    
	@Before
	public void setUp() throws Exception {
		System.out.println("Set up!!!!");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tear down!!!!");
	}

	@Test
	public void addScm() {
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			PomProcessor processor = frameworkUtil.getPomProcessor("PHR_Phpblog");
			if (processor.getSCM() == null) {
				processor.setSCM("", "kalees", "", "");
				processor.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pomProcessorScmAddTest() {
		try {
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
			builder.append("PHR_sonarAllReport");
			builder.append(File.separatorChar);
			builder.append("pom.xml");
			File pomPath = new File(builder.toString());
			PomProcessor processor = new PomProcessor(pomPath);
			
			Scm scm = processor.getSCM();
			System.out.println("Getting values ======>");
			if(scm != null) {
				System.out.println(scm.getConnection());
				System.out.println(scm.getDeveloperConnection());
				System.out.println(scm.getUrl());
			} else {
				System.out.println("Scm null found");
				System.out.println("Going to add add scm tags");
				processor.setSCM("Connection", "developerConnection", "Url", "Scm tag");
				processor.save();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test() {
		try {
			File gitImportTemp = new File(tempDirectory, "gitImportTemp");
			if(gitImportTemp.exists()) {
				gitImportTemp.delete();
			}
			importFromGit(url, gitImportTemp);
			ProjectInfo projectInfo = getProjectInfo(gitImportTemp);
			System.out.println(projectInfo.getCode());
			importToWorkspace(gitImportTemp, phrescoHomeDirectory + File.separator + "projects", projectInfo.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void importToWorkspace(File gitImportTemp, String phrescoHomeDirectory2, String projectCode) throws PhrescoException {
		try {
			File workspaceProjectDir = new File(phrescoHomeDirectory2 + File.separator + projectCode);
			System.out.println("workspaceProjectDir ========> " + workspaceProjectDir);
			if (workspaceProjectDir.exists()) {
				throw new PhrescoException("Import Fails", "Project with the code " + projectCode + " already present");
			}
			System.out.println("gitImportTemp ====> " + gitImportTemp);
			System.out.println("workspaceProjectDir ====> " + workspaceProjectDir);
			FileUtils.moveDirectory(gitImportTemp, workspaceProjectDir);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e); //org.apache.commons.io.FileExistsException: Destination '/Users/kaleeswaran/projects/PHR_Phpblog' already exists
		}
	}

	public boolean importFromGit(String url,File directory) throws PhrescoException {
		try {
		    Git repo1 = Git.cloneRepository().setURI(url).setDirectory(directory).call();
		    for (Ref b : repo1.branchList().setListMode(ListMode.ALL).call()) {
		    	System.out.println("(standard): cloned branch " + b.getName());
		    }
		    return true;
		} catch (Exception e) {
			throw new PhrescoException(e);//invalid remote repo :::  org.eclipse.jgit.api.errors.InvalidRemoteException: Invalid remote: origin
			//[ErrorMessage] = org.eclipse.jgit.api.errors.JGitInternalException: Destination path "gitImportTemp" already exists and is not an empty directory
		}
		
		//			} catch(org.eclipse.jgit.api.errors.InvalidRemoteException e) { //Invalid remote: origin
		//		e.printStackTrace();
		//		svnImportMsg = getText(INVALID_URL);
		//	} catch(org.eclipse.jgit.errors.RemoteRepositoryException e) { //Invalid remote: origin
		//		e.printStackTrace();
		//		svnImportMsg = getText(INVALID_URL);
	}
	
	public ProjectInfo getProjectInfo(File directory) throws Exception {
		BufferedReader reader = null;
		try {
		    File dotProjectFile = new File(directory, PHRESCO + File.separator + PROJECT_INFO);
		    System.out.println("dotProjectFile ====> " + dotProjectFile);
		    if (!dotProjectFile.exists()) {
		        throw new PhrescoException("Phresco Project definition not found");
		    }
		
		    reader = new BufferedReader(new FileReader(dotProjectFile));
		    return new Gson().fromJson(reader, ProjectInfo.class);
		} finally {
		    Utility.closeStream(reader);
		}
	}

}
