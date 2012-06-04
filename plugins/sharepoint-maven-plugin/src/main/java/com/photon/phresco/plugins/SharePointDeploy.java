package com.photon.phresco.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.PluginConstants;

/**
 * Goal which deploys the Java WebApp to a server
 * 
 * @goal deploy
 * 
 */
public class SharePointDeploy extends AbstractMojo implements PluginConstants{

	/**
	 * The Maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * @parameter expression="${project.basedir}" required="true"
	 * @readonly
	 */
	protected File baseDir;

	/**
	 * Build file name to deploy
	 * 
	 * @parameter expression="${buildName}" required="true"
	 */
	protected String buildName;
	
	/**
	 * @parameter expression="${environmentName}" required="true"
	 */
	protected String environmentName;

	private File buildFile;
	private File tempDir;
	private File buildDir;
	private File temp;
	private File build;

	public void execute() throws MojoExecutionException {
		init();
		extractBuild();
		deploy();
	}

	private void init() throws MojoExecutionException {
		try {
			
			if (StringUtils.isEmpty(buildName) || StringUtils.isEmpty(environmentName)) {
				callUsage();
			}
			buildDir = new File(baseDir.getPath() + BUILD_DIRECTORY);
			build = new File(baseDir.getPath() + "\\source" + "\\");
			buildFile = new File(buildDir.getPath() + File.separator + buildName);
			tempDir = new File(buildDir.getPath() + TEMP_DIR);
			tempDir.mkdirs();
			temp = new File(tempDir.getPath() + "\\" + baseDir.getName() + ".wsp");
		} catch (Exception e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void callUsage() throws MojoExecutionException {
		getLog().error("Invalid usage.");
		getLog().info("Usage of Deploy Goal");
		getLog().info(
				"mvn sharepoint:deploy -DbuildName=\"Name of the build\""
						+ " -DenvironmentName=\"Multivalued evnironment names\"");
		throw new MojoExecutionException("Invalid Usage. Please see the Usage of Deploy Goal");
	}

	private void extractBuild() throws MojoExecutionException {
		try {
			ArchiveUtil.extractArchive(buildFile.getPath(), tempDir.getPath(),
					ArchiveType.ZIP);
			FileUtils.copyFileToDirectory(temp, build);
			FileUtils.deleteDirectory(tempDir);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	private void deploy() throws MojoExecutionException {
		try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();

			List<SettingsInfo> settingsInfos = administrator.getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER,
					baseDir.getName(), environmentName);
			for (SettingsInfo serverDetails : settingsInfos) {
				String deployDirectory = serverDetails.getPropertyInfo(Constants.SERVER_DEPLOY_DIR).getValue();
				String serverContext = serverDetails.getPropertyInfo(Constants.SERVER_CONTEXT).getValue();
				String protocol = serverDetails.getPropertyInfo(Constants.SERVER_PROTOCOL).getValue();
				String host = serverDetails.getPropertyInfo(Constants.SERVER_HOST).getValue();
				String port = serverDetails.getPropertyInfo(Constants.SERVER_PORT).getValue();
				String projectCode = baseDir.getName();
				restore(protocol, deployDirectory, serverContext, host, port);
				addSolution(projectCode, deployDirectory);
				deploysolution(protocol, deployDirectory, serverContext, host, port, projectCode);
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (PhrescoException e) {
			throw new MojoExecutionException(e.getErrorMessage(), e);
		}
	}

	private void restore(String protocol, String deployDirectory, String serverContext, String host, String port)
			throws CommandLineException, IOException, MojoExecutionException {
		File file = new File(build.getPath() + "\\phresco-pilot.dat");
		if (!file.exists()) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(SHAREPOINT_STSADM);
		sb.append(STR_SPACE);
		sb.append(SHAREPOINT_STR_O);
		sb.append(SHAREPOINT_RESTORE);
		sb.append(STR_SPACE);
		sb.append(SHAREPOINT_STR_URL);
		sb.append(STR_SPACE);
		sb.append(protocol);
		sb.append(SHAREPOINT_STR_COLON);
		sb.append(SHAREPOINT_STR_DOUBLESLASH);
		sb.append(host);
		sb.append(SHAREPOINT_STR_COLON);
		sb.append(port);
		sb.append(SHAREPOINT_STR_BACKSLASH);
		sb.append(serverContext);
		sb.append(STR_SPACE);
		sb.append(SHAREPOINT_STR_HYPEN);
		sb.append(SHAREPOINT_STR_OVERWRITE);
		sb.append(STR_SPACE);
		sb.append(SHAREPOINT_STR_HYPEN);
		sb.append(SHAREPOINT_STR_FILENAME);
		sb.append(STR_SPACE);
		sb.append(SHAREPOINT_STR_DOUBLEQUOTES + build.getPath() + "\\phresco-pilot.dat" + SHAREPOINT_STR_DOUBLEQUOTES);
		Commandline cl = new Commandline(sb.toString());
		cl.setWorkingDirectory(deployDirectory);
		Process process = cl.execute();
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;
		while ((line = in.readLine()) != null) {
		}
	}

	private void addSolution(String ProjectCode, String deployDirectory)
			throws MojoExecutionException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(SHAREPOINT_STSADM);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_STR_O);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_ADDSOLUTION);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_STR_HYPEN);
			sb.append(SHAREPOINT_STR_FILENAME);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_STR_DOUBLEQUOTES + baseDir.getPath() + "\\source" + "\\"
					+ ProjectCode + ".wsp" + SHAREPOINT_STR_DOUBLEQUOTES);
			File file = new File(baseDir.getPath() + "\\source" + "\\"
					+ ProjectCode + ".wsp");
			if (file.exists()) {
				Commandline cl = new Commandline(sb.toString());
				cl.setWorkingDirectory(deployDirectory);
				Process process = cl.execute();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
				}
			} else {
				getLog().error("File Not found Exception");
			}
		} catch (CommandLineException e) {

			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}

	}

	private void deploysolution(String protocol, String deploydirectory, String serverContext,
			String host, String port, String projectCode) throws MojoExecutionException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(SHAREPOINT_STSADM);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_STR_O);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_DEPLOYSOLUTION);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_STR_HYPEN);
			sb.append(SHAREPOINT_STR_NAME);
			sb.append(STR_SPACE);
			sb.append(projectCode + ".wsp");
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_STR_HYPEN);
			sb.append(SHAREPOINT_STR_URL);
			sb.append(STR_SPACE);
			sb.append(protocol);
			sb.append(SHAREPOINT_STR_COLON);
			sb.append(SHAREPOINT_STR_DOUBLESLASH);
			sb.append(host);
			sb.append(SHAREPOINT_STR_COLON);
			sb.append(port);
			sb.append(SHAREPOINT_STR_BACKSLASH);
			sb.append(serverContext);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_STR_HYPEN);
			sb.append(SHAREPOINT_STR_IMMEDIATE);
			sb.append(STR_SPACE);
			sb.append(SHAREPOINT_STR_HYPEN);
			sb.append(SHAREPOINT_STR_ALLOWACDEP);
			Commandline cl = new Commandline(sb.toString());
			cl.setWorkingDirectory(deploydirectory);
			Process process = cl.execute();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
		} catch (CommandLineException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}

	}

}
