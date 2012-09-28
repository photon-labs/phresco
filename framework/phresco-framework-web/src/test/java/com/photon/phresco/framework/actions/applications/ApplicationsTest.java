package com.photon.phresco.framework.actions.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
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

	private String jpmcUrl = "https://github.com/vivekraja-v/PHR_Phpblog.git";
//	private String jpmcUrl = "https://github.com/PhotonMomo/JPMC_POC.git";
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
	public void copyFiles() {
		try {
			String outFileNamePDF = "/Users/kaleeswaran/workspace/temp/9969c122-1883-4ee8-bbed-21ed62472a1b/PHR_iphone_kalees_test_detail_Sep 28 2012 00.42.pdf";
			String outFinalFileNamePDF = "/Users/kaleeswaran/workspace/temp/kalees/kalees.pdf";
			FileUtils.copyFile(new File(outFileNamePDF), new File(outFinalFileNamePDF));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
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
	public void clonePrivateRepo() {
		try {
			
//			org.eclipse.egit.github.core.Repository repoPrivate = new Repository();
//			repoPrivate.setGitUrl(jpmcUrl);
			
//			repoPrivate.setCloneUrl(jpmcUrl);
//			String description = repoPrivate.getHomepage();
//			System.out.println(repoPrivate.isPrivate());
//			
//			GitHubClient client = new GitHubClient();
//			client.setCredentials("arunachalam-l", "phresco123");
//			org.eclipse.egit.github.core.client.GitHubRequest req = new GitHubRequest();
			
			
//			client.setOAuth2Token(token)
//
//			RepositoryService service = new RepositoryService();
//			service.getClient().setCredentials("arunachalam-l", "phresco123");
//			
//			
//			for (Repository repo : service.getRepositories()) {
//			  System.out.println(repo.getName() + " Watchers: " + repo.getCloneUrl());
//			  List<RepositoryBranch> branches = service.getBranches(repo);
//			  for (RepositoryBranch repositoryBranch : branches) {
//				  System.out.println(repositoryBranch.getName());
//			  }
//			}
			
			
//			CheckoutPullRequestHandler cpr = new CheckoutPullRequestHandler();
			
//			String uuid = UUID.randomUUID().toString();
//			File gitImportTemp = new File(Utility.getPhrescoTemp(), uuid);
//			if(gitImportTemp.exists()) {
//				System.out.println("Empty git directory need to be removed before importing from git ");
//				FileUtils.deleteDirectory(gitImportTemp);
//			}
//			System.out.println("gitImportTemp " + gitImportTemp);
//			
//			importFromPrivateGit(jpmcUrl, gitImportTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean importFromPrivateGit(String url, File directory) throws PhrescoException {
		try {
			
		    return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
	}
	
//	@Test
	public void test() {
		try {
			String uuid = UUID.randomUUID().toString();
			File gitImportTemp = new File(Utility.getPhrescoTemp(), uuid);
			if(gitImportTemp.exists()) {
				System.out.println("Empty git directory need to be removed before importing from git ");
				FileUtils.deleteDirectory(gitImportTemp);
			}
			System.out.println("gitImportTemp " + gitImportTemp);
			importFromGit(jpmcUrl, gitImportTemp);
			System.out.println("Checking out a branch!!!!!!");
			pullCheck(gitImportTemp.toString());
//			checkoutKalees(gitImportTemp);
//			System.out.println("pull operation started!!!!!!!!");
//			pullDir(gitImportTemp);
//			ProjectInfo projectInfo = getProjectInfo(gitImportTemp);
//			System.out.println(projectInfo.getCode());
//			importToWorkspace(gitImportTemp, Utility.getProjectHome() , projectInfo.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void pullDir() {
		try {
//			File dir = new File("/Users/kaleeswaran/workspace/temp/c9b0c443-cf3e-4a97-b9cb-d4009dfabc64");
//			Git git = Git.open(dir); //checkout is the folder with .git
//			PullCommand pull = git.pull(); //succeeds
//			UsernamePasswordCredentialsProvider userCredential = null;
//			userCredential = new UsernamePasswordCredentialsProvider("kaleeswaran-s", "phresco123");
//			pull.setCredentialsProvider(userCredential);
//			pull.call();
			
//			FileRepositoryBuilder builder = new FileRepositoryBuilder();
//			Repository repository = builder.setGitDir(new File("/Users/kaleeswaran/workspace/temp/94855fd1-2965-4c33-96d9-e230c286b7e1/.git")).readEnvironment().findGitDir().build();
//			Git git = new Git(repository);
//			PullCommand pc = git.pull();
//			pc.call();
			
			FileRepositoryBuilder builder = new FileRepositoryBuilder();
			Repository repository = builder.setGitDir(new File("/Users/kaleeswaran/workspace/temp/7192ca42-978a-4621-9505-9b64002ae7b3/.git")).readEnvironment().findGitDir().build();
			Git git = new Git(repository);
//			CheckoutCommand checkout = git.checkout();
//			Ref call = checkout.setName("refs/remotes/origin/kalees").call();
			
			PullCommand pull = git.pull();
			pull.getRepository().close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void checkoutKalees(File dir) {
		System.out.println("executing!!!!!");
		try {
//			File dir = new File("/Users/kaleeswaran/workspace/temp/79442f23-4212-4b75-9396-71ba5efa1d37");
			Git open = Git.open(dir);
			CheckoutCommand command = open.checkout().setCreateBranch(true).setName("refs/remotes/origin/kalees");
			Ref call = command.call();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void pullCheck(String dir) {
		System.out.println("executing!!!!! " + dir);
		try {
//			FileRepositoryBuilder builder = new FileRepositoryBuilder();
//			Repository repository = builder.setGitDir(new File(dir + "/.git")).readEnvironment().findGitDir().build();
//			Git git = new Git(repository);
//			git.checkout().setCreateBranch(true).setName("refs/remotes/origin/kalees");
//			PullCommand pull = git.pull();
//			pull.call();
//			pull.getRepository().close();
//			String dir = "/Users/kaleeswaran/workspace/temp/cd82ebec-fba6-489d-bde1-6aedd022895c";
			Git git = Git.open(new File(dir)); //checkout is the folder with .git
//			git.branchCreate().setForce(true).setName("kalees").setStartPoint("origin/kalees").call();
			CheckoutCommand checkout = git.checkout();
			Ref call = checkout.setName("kalees").call();
			git.pull().call(); //succeeds
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//	@Test
	public void stanalonePullCheck() {
		try {
//		    UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider("xxxx", "xxxx");
//		    localPath = "E:\\murugan\\Test\\GIT_LOCALDEPY";
//		        Git git = new Git(localRepo);
//		        PullCommand pcmd = git.pull();
//		        pcmd.setCredentialsProvider(user);
//		        pcmd.call();
		        
//			FileRepositoryBuilder builder = new FileRepositoryBuilder();
//			Repository repository = builder.setGitDir(new File(dir + "/.git")).readEnvironment().findGitDir().build();
//			Git git = new Git(repository);
//			git.checkout().setCreateBranch(true).setName("refs/remotes/origin/kalees");
//			PullCommand pull = git.pull();
//			pull.call();
//			pull.getRepository().close();
//			git.branchCreate().setForce(true).setName("kalees").setStartPoint("origin/kalees").call();
			String dir = "/Users/kaleeswaran/workspace/temp/05745a89-c5f0-4f2c-b727-ea0e9e4b4f1f";
			Git git = Git.open(new File(dir)); //checkout is the folder with .git
//			System.out.println(git.getRepository().getFullBranch());
			CheckoutCommand checkout = git.checkout();
			Ref call = checkout.setName("refs/remotes/origin/kalees").call();
			
//			git.branchCreate().setForce(true).setName("kalees").setStartPoint("origin/kalees").call();
			
//			checkout.getRepository().close();
//			git.clean().call();
			git.pull().call(); //succeeds
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void importToWorkspace(File gitImportTemp, String phrescoHomeDirectory, String projectCode) throws Exception {
		System.out.println("Entering Method  Applications.importToWorkspace()");
		File workspaceProjectDir = new File(phrescoHomeDirectory + projectCode);
		System.out.println("workspaceProjectDir "+ workspaceProjectDir);
		if (workspaceProjectDir.exists()) {
			System.out.println("workspaceProjectDir exists"+ workspaceProjectDir);
			throw new org.apache.commons.io.FileExistsException();
		}
		System.out.println("gitImportTemp ====> " + gitImportTemp);
		System.out.println("workspaceProjectDir ====> " + workspaceProjectDir);
		FileUtils.copyDirectory(gitImportTemp, workspaceProjectDir);
		try {
			FileUtils.deleteDirectory(gitImportTemp);
			System.out.println("deleted!!!!!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("pack file is not deleted "  + e.getLocalizedMessage());
		}
	}

	public boolean importFromGit(String url,File directory) throws PhrescoException {
		try {
			String login = "arunachalam-l";
			String password = "phresco123";
			List<String> gitBranches = new ArrayList<String>(3);
//			gitBranches.add("refs/remotes/origin/master");
//			gitBranches.add("refs/remotes/origin/kalees");
			
		    UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(login, password);
			Git repo1 = Git.cloneRepository().setURI(url).setDirectory(directory).setCloneAllBranches(true).call();
			
		    for (Ref b : repo1.branchList().call()) {
		    	repo1.checkout().setName(b.getName()).call();
		    	System.out.println("(standard): cloned branch " + b.getName());
		    }
		    repo1.checkout().setName("master").call();
		    repo1.getRepository().close();
		    System.out.println("Clonning completed!!!!!!");
		    return true;
		} catch (Exception e) {
			e.printStackTrace();
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
