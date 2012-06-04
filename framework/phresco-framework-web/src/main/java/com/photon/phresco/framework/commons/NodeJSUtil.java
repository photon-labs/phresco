/*
 * ###
 * Framework Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.framework.commons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.Commandline;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.model.DatabaseInfo;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Utility;

public class NodeJSUtil {

	private static final String NODE_CMD = "node";
	private static final String SOURCE_DIR = "source";
	private static final String OS_NAME = "os.name";
	private static final  String WINDOWS = "Windows";
	private static final String WIN_PROCESS_KILL_CMD = "taskkill";
	private static final String UNIX_PROCESS_KILL_CMD = "Pkill";
	private static final String LOG_FILE_NAME = "temp.log";
	private static final String NODE_SERVER_FILE = "server.js";
	private static final String DB_CONFIG_PATH = "/source/public/resources/db-config.json";
	private static final String SQL_FILE_PATH = "/source/sql/site.sql";
	private static final String SERVER_CONFIG_FILE 	= "/source/public/resources/server-config.json";
	private static final String DATABASE_CONFIG_FILE = "/source/public/resources/db-config.json";
	private Project project;

	public NodeJSUtil(Project project) {
		this.project = project;
	}

	public BufferedReader start(SettingsInfo databaseDetails,
			SettingsInfo serverDetails) throws PhrescoException {
		
		try {
			adaptSourceConfig( databaseDetails,
					 serverDetails);
			createDb();
			File tempLogFile = new File(LOG_FILE_NAME);
			tempLogFile.createNewFile(); 
			Commandline cl = new Commandline(NODE_CMD); 
			String[] args1 = {NODE_SERVER_FILE};
			cl.addArguments(args1); 
			String projLocation = Utility.getProjectHome() + File.separator +
					project.getProjectInfo().getCode() + File.separator + SOURCE_DIR;
			cl.setWorkingDirectory(projLocation); 
			Process proc = cl.execute(); 
			BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			// FileWriter fstream = new FileWriter(LOG_FILE_NAME, true);
			// LogWriterThread logWriterThread = new LogWriterThread(input,
			// fstream, "Input");
			// logWriterThread.start();
			// System.out.println("Testing...");
			return input;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
	}

	private void createDb() throws PhrescoException {
		FileInputStream fis = null;
		BufferedReader reader = null;
		Connection con = null;
		try {
			String projectHome = Utility.getProjectHome();
			File file = new File(projectHome + project.getProjectInfo().getCode() + DB_CONFIG_PATH);
			if (!file.exists()) {
				return;
			}
			
			fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fis));
			String dbJson = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				dbJson += line;
			}

			Gson gson = new Gson();
			DatabaseInfo info = gson.fromJson(dbJson, DatabaseInfo.class);
			String databaseName = info.getDatabasename();
			String host = info.getHost();
			String password = info.getPassword();
			String port = info.getPort();
			String userName = info.getUsername();
			String databasetype = info.getDatabaseType();
			Class.forName(getDbDriver(databasetype));
			String dbUrl = "jdbc:" + databasetype + "://" + host + ":" + port;
			con = DriverManager.getConnection(dbUrl, userName, password);
			Statement st = con.createStatement();
			ArrayList<String> list = new ArrayList<String>();
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs = meta.getCatalogs();
			while (rs.next()) {
				String listofDatabases = rs.getString("TABLE_CAT");
				list.add(listofDatabases);
			}
			
			if (list.contains(databaseName)) {
				System.out.println("database already exists");
				executeSql(dbUrl, databaseName, userName, password);
			} else {
				st.executeUpdate("CREATE DATABASE " + databaseName);
				System.out.println("Database created");
				executeSql(dbUrl, databaseName, userName, password);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			} catch (SQLException e) {
				throw new PhrescoException(e);
			}
		}
	}

	private String getDbDriver(String databasetype) {
		Map<String, String> dbDriverMap = new HashMap<String, String>(8);
		dbDriverMap.put("MySQL", "com.mysql.jdbc.Driver");
		dbDriverMap.put("Oracle", "oracle.jdbc.driver.OracleDriver");
		dbDriverMap.put("HSQL", "org.hsql.jdbcDriver");
		dbDriverMap.put("MS SQL Server", "com.ashna.jturbo.driver.Driver");
		dbDriverMap.put("DB2", "COM.ibm.db2.jdbc.app.DB2Driver");
		return dbDriverMap.get(databasetype);
	}

	private void executeSql(String dbUrl, String databaseName, String userName,
			String password) throws PhrescoException {
		String lines = null;
		StringBuilder builder = new StringBuilder();
		Connection con = null;
		BufferedReader br = null;
		try {
			String projectHome = Utility.getProjectHome();
			FileReader sqlFile = new FileReader(projectHome
					+ project.getProjectInfo().getCode() + SQL_FILE_PATH);
			br = new BufferedReader(sqlFile);
			while ((lines = br.readLine()) != null) {
				builder.append(lines);
			}
			String[] inst = builder.toString().split(";");
			con = DriverManager.getConnection(dbUrl + "/" + databaseName, userName,
					password);
			Statement st = con.createStatement();
			for (int i = 0; i < inst.length; i++) {
				if (!inst[i].trim().equals("")) {
					st.executeUpdate(inst[i]);
				}
			}
		} catch (SQLException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			} catch (SQLException e) {
				throw new PhrescoException(e);
			}
		}
	}

	public BufferedReader getLogs() throws PhrescoException {
		DataInputStream in = null;
		try {
			FileInputStream fstream = new FileInputStream(LOG_FILE_NAME);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
			}
			return br;
		} catch (Exception e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}

	public BufferedReader stop() throws PhrescoException {
		String result = "";
		try {
			File tempLogFile = new File(LOG_FILE_NAME);
			if (tempLogFile.exists()) {
				tempLogFile.delete();
			}
			if (System.getProperty(OS_NAME).startsWith(WINDOWS)) {
				stopNodeJSInWindows();
			} else {
				stopNodeJSInUnix();
			}
			result = "Server Stopped Successfully...";
		} catch (CommandLineException e) {
			result = "Unable to stop the server...";
			throw new PhrescoException(e);
		} catch (IOException e) {
			result = "Unable to stop the server...";
			throw new PhrescoException(e);
		}
		ByteArrayInputStream is = new ByteArrayInputStream(result.getBytes());
		return new BufferedReader(new InputStreamReader(is));
	}
	
	public String getNodeJSServerStatus(HttpSession session) throws PhrescoException {
    	String projectCode = project.getProjectInfo().getCode();
		String status = (String)session.getAttribute(projectCode + "_NodeJSServerStatus");
    	try {
			if (status == null || status.isEmpty()) {
				File serverConfigFile = new File(Utility.getProjectHome() + File.separator +
						project.getProjectInfo().getCode() + File.separator + SERVER_CONFIG_FILE);
				Gson gson = new Gson();
				InputStreamReader serverInStream = new InputStreamReader(new FileInputStream(
						serverConfigFile));
				ServerInfo serverInfo = gson.fromJson(serverInStream, ServerInfo.class);
				int port = Integer.parseInt(serverInfo.getPort());
				boolean tempConnectionAlive = DiagnoseUtil.isConnectionAlive(serverInfo.getProtocol(), serverInfo.getHost(), port);
				session.setAttribute(projectCode + "_NodeJSServerStatus", tempConnectionAlive);
				status = String.valueOf(tempConnectionAlive);
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
    	return status;    	
    }

	private void stopNodeJSInWindows() throws IOException {
		Process p = Runtime.getRuntime().exec(
				"cmd /X /C taskkill /F /IM node.exe");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line = null;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
	}
	
	private void adaptSourceConfig(SettingsInfo databaseDetails,
			SettingsInfo serverDetails) throws PhrescoException {
		InputStreamReader serverInStream = null;
		InputStreamReader databaseInStream = null;
		BufferedWriter serverWriter = null;
		BufferedWriter databaseWriter = null;
		try {
			File serverConfigFile = new File(Utility.getProjectHome() + File.separator +
					project.getProjectInfo().getCode() + File.separator + SERVER_CONFIG_FILE);
			File databaseconfigfile = new File(Utility.getProjectHome() +  File.separator +
					project.getProjectInfo().getCode() + File.separator + DATABASE_CONFIG_FILE);

			Gson gson = new Gson();
			databaseInStream = new InputStreamReader(new FileInputStream(
					databaseconfigfile));
			serverInStream = new InputStreamReader(new FileInputStream(
					serverConfigFile));
			DatabaseInfo databaseInfo = gson.fromJson(databaseInStream,
					DatabaseInfo.class);
			ServerInfo serverInfo = gson.fromJson(serverInStream,
					ServerInfo.class);

			PropertyInfo databaseName = databaseDetails
					.getPropertyInfo(Constants.DB_NAME);
			PropertyInfo username = databaseDetails
					.getPropertyInfo(Constants.DB_USERNAME);
			PropertyInfo password = databaseDetails
					.getPropertyInfo(Constants.DB_PASSWORD);
			PropertyInfo host = databaseDetails
					.getPropertyInfo(Constants.DB_HOST);
			PropertyInfo port = databaseDetails
					.getPropertyInfo(Constants.DB_PORT);
			PropertyInfo databaseType = databaseDetails
					.getPropertyInfo(Constants.DB_TYPE);

			PropertyInfo serverhost = serverDetails
					.getPropertyInfo(Constants.SERVER_HOST);
			PropertyInfo serverport = serverDetails
					.getPropertyInfo(Constants.SERVER_PORT);
			PropertyInfo protocol = serverDetails
					.getPropertyInfo(Constants.SERVER_PROTOCOL);
			PropertyInfo context = serverDetails
					.getPropertyInfo(Constants.SERVER_CONTEXT);
			PropertyInfo serveradminpassword = serverDetails
					.getPropertyInfo(Constants.SERVER_ADMIN_PASSWORD);
			PropertyInfo serveradminusername = serverDetails
					.getPropertyInfo(Constants.SERVER_ADMIN_USERNAME);

			databaseInfo.setDatabasename(databaseName.getValue());
			databaseInfo.setUsername(username.getValue());
			databaseInfo.setPassword(password.getValue());
			databaseInfo.setHost(host.getValue());
			databaseInfo.setPort(port.getValue());
			databaseInfo.setDatabaseType(databaseType.getValue());

			serverInfo.setHost(serverhost.getValue());
			serverInfo.setPort(serverport.getValue());
			serverInfo.setProtocol(protocol.getValue());
			serverInfo.setContext(context.getValue());
			serverInfo.setUsername(serveradminusername.getValue());
			serverInfo.setPassword(serveradminpassword.getValue());

			serverWriter = new BufferedWriter(new FileWriter(Utility.getProjectHome()+ File.separator +
					project.getProjectInfo().getCode() + File.separator 
					+ SERVER_CONFIG_FILE));
			databaseWriter = new BufferedWriter(new FileWriter(Utility.getProjectHome() +  File.separator +
					project.getProjectInfo().getCode() + File.separator 
					+ DATABASE_CONFIG_FILE));

			String serverJson = gson.toJson(serverInfo);
			String databaseJson = gson.toJson(databaseInfo);
			serverWriter.write(serverJson);
			databaseWriter.write(databaseJson);
		} catch (Exception e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (serverInStream != null) {
					serverInStream.close();
				}
				if (databaseInStream != null) {
					databaseInStream.close();
				}
				if (serverWriter != null) {
					serverWriter.close();
				}
				if (databaseWriter != null) {
					databaseWriter.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}


	// TODO: Not working, need to investigate
	private void stopNodeJSInWindows1() throws CommandLineException,
			IOException {
		// Commandline cl = new Commandline(WIN_PROCESS_KILL_CMD);
		// String[] args1 = {"/F", "/IM", "node.exe"};
		// cl.addArguments(args1);
		Commandline cl = new Commandline(WIN_PROCESS_KILL_CMD
				+ " /F /IM node.exe");
		cl.setWorkingDirectory(project.getProjectInfo().getCode()
				+ File.separator + SOURCE_DIR);
		System.out.println("command === " + cl.toString());
		Process execute = cl.execute();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				execute.getErrorStream()));
		String line = null;
		while ((line = in.readLine()) != null) {

			System.out.println(line);
		}

	}

	private void stopNodeJSInUnix() throws CommandLineException, IOException {
		Commandline cl = new Commandline(UNIX_PROCESS_KILL_CMD);
		String[] args1 = { "node" };
		cl.addArguments(args1);
		cl.setWorkingDirectory(project.getProjectInfo().getCode()
				+ File.separator + SOURCE_DIR);
		Process execute = cl.execute();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				execute.getErrorStream()));
		String line = null;
		while ((line = in.readLine()) != null) {
		}
	}

	class LogWriterThread extends Thread {
		BufferedReader in;
		FileWriter fstream;

		public LogWriterThread(BufferedReader in, FileWriter fstream,
				String name) {
			super(name);
			this.in = in;
			this.fstream = fstream;
		}

		public void run() {
			String line = null;
			BufferedWriter out = new BufferedWriter(fstream);
			try {
				while ((line = in.readLine()) != null) {
					out.write(line + "\n");
					out.flush();
				}

				synchronized (this) {
					notifyAll();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
