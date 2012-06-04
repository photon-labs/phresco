package com.photon.phresco.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.FileUtils;

import com.photon.phresco.configuration.ConfigReader;
import com.photon.phresco.configuration.ConfigWriter;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.SettingsInfo;

public class PluginUtils {
	private Map<String, String> dbDriverMap = new HashMap<String, String>(8);

	public void executeUtil(String environmentType, String basedir, File sourceConfigXML) {
		try {
			File phrescoConfigXML = new File(Utility.getProjectHome() + basedir + File.separator + 
					PluginConstants.DOT_PHRESCO_FOLDER + File.separator + PluginConstants.CONFIG_FILE);
			File settingsXML = new File(Utility.getProjectHome() + PluginConstants.SETTINGS_FILE);
			
			ConfigReader reader = new ConfigReader(phrescoConfigXML);
			ConfigWriter writer = new ConfigWriter(reader, true);
			writer.saveXml(sourceConfigXML, environmentType);
			if (settingsXML.exists()) {
				ConfigReader srcReaderToAppend = new ConfigReader(sourceConfigXML);
				
				ConfigReader globalReader = new ConfigReader(settingsXML);
				ConfigWriter globalWriter = new ConfigWriter(globalReader, true);
				globalWriter.saveXml(srcReaderToAppend, environmentType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getDbDriver(String dbtype) {
		return dbDriverMap.get(dbtype);
	}
	
	private void initDriverMap() {
		dbDriverMap.put("mysql", "com.mysql.jdbc.Driver");
		dbDriverMap.put("oracle", "oracle.jdbc.OracleDriver");
		dbDriverMap.put("hsql", "org.hsql.jdbcDriver");
		dbDriverMap.put("mssql", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dbDriverMap.put("db2", "com.ibm.db2.jcc.DB2Driver");
		dbDriverMap.put("mongodb", "com.mongodb.jdbc.MongoDriver");
	}
	
	public void executeSql(SettingsInfo info,File basedir,String filePath,String fileName) throws PhrescoException {
	    initDriverMap();
        String host = info.getPropertyInfo(Constants.DB_HOST).getValue();
        String port = info.getPropertyInfo(Constants.DB_PORT).getValue();
        String userName = info.getPropertyInfo(Constants.DB_USERNAME).getValue();
        String password = info.getPropertyInfo(Constants.DB_PASSWORD).getValue();
        String databaseName = info.getPropertyInfo(Constants.DB_NAME).getValue();
        String databaseType = info.getPropertyInfo(Constants.DB_TYPE).getValue();
        String version = info.getPropertyInfo(Constants.DB_VERSION).getValue();
        String connectionProtocol = findConnectionProtocol(databaseType, host, port, databaseName);
		Connection con = null;
		FileInputStream file = null;
		Statement st = null;
		try {
		    Class.forName(getDbDriver(databaseType)).newInstance();
			file = new FileInputStream(basedir.getPath() + filePath + databaseType.toLowerCase() + 
					File.separator + version + fileName);
			Scanner s = new Scanner(file);
			s.useDelimiter("(;(\r)?\n)|(--\n)");
			con = DriverManager.getConnection(connectionProtocol, userName, password);
			con.setAutoCommit(false);
			st = con.createStatement();
			while (s.hasNext()) {
				String line = s.next();
				if (line.startsWith("/*!") && line.endsWith("*/")) {
					int i = line.indexOf(' ');
					line = line.substring(i + 1, line.length() - " */".length());
				}

				if (line.trim().length() > 0) {
					st.execute(line);
				}
			}
		} catch (SQLException e) {
			throw new PhrescoException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (Exception e) {
		    throw new PhrescoException(e);
        } finally {
			try {
				if (con != null) {
				    con.commit();
					con.close();
				}
				if (file != null) {
					file.close();
				}
			} catch (Exception e) {
				throw new PhrescoException(e);
			}
		}
	}

	private String findConnectionProtocol(String databaseType, String host, String port, String databaseName) {
	    String connectionProtocol = null;
	    if(databaseType.equals("mysql") || databaseType.equals("hsql") || databaseType.equals("db2")) {
	        connectionProtocol = "jdbc:" + databaseType.toLowerCase() + "://" + host + ":" + port + "/" + databaseName;
        } 
	    if (databaseType.equals("oracle")) {
	        connectionProtocol = "jdbc:" + "oracle:thin:@" + host + ":" + port + "/" + databaseName;
	    }
        if(databaseType.equals("mssql")) {
            connectionProtocol = "jdbc:" + "sqlserver" + "://" + host + ":" + port + ";" + "DatabaseName=" + databaseName;
        }
        
        return connectionProtocol;
    }

    public void updateSqlQuery(SettingsInfo info, String serverHost, String context, String serverport)
			throws PhrescoException {
		Connection conn = null;
		String updateQuery;
		String updateHomeQuery;
		try {
			String host = info.getPropertyInfo(Constants.DB_HOST).getValue();
			String port = info.getPropertyInfo(Constants.DB_PORT).getValue();
			String username = info.getPropertyInfo(Constants.DB_USERNAME).getValue();
			String password = info.getPropertyInfo(Constants.DB_PASSWORD).getValue();
			String database = info.getPropertyInfo(Constants.DB_NAME).getValue();
			String databasetype = info.getPropertyInfo(Constants.DB_TYPE).getValue();
			String dbUrl = "jdbc:" + databasetype + "://" + host + ":" + port;
			String url = dbUrl + PluginConstants.FORWARD_SLASH + database;
			conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			String wordPressUrl = "";
			if (serverport.equals(PluginConstants.APACHE_DEFAULT_PORT)) {
				wordPressUrl = serverHost;
			} else {
				wordPressUrl = serverHost + ":" + serverport;
			}
			updateQuery = PluginConstants.WORDPRESS_UPDATE_TABLE + "http://" + wordPressUrl
					+ PluginConstants.FORWARD_SLASH + context + PluginConstants.WORDPRESS_UPDATE_WHERE;
			updateHomeQuery = PluginConstants.WORDPRESS_UPDATE_TABLE + "http://" + wordPressUrl
					+ PluginConstants.FORWARD_SLASH + context + PluginConstants.WORDPRESS_UPDATE_HOME_WHERE;
			stmt.executeUpdate(updateQuery);
			stmt.executeUpdate(updateHomeQuery);
		} catch (Exception e) {
			System.out.println("Update Failed");
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new PhrescoException(e);
			}
		}
	}

	public List<String> csvToList(String environmentName) {
		List<String> envs = new ArrayList<String>();
		if (StringUtils.isNotEmpty(environmentName)) {
			String[] temp = environmentName.split(",");
			for (int i = 0; i < temp.length; i++) {
				envs.add(temp[i]);
			}
		}
		return envs;
	}
	
	public void encode(File configFile) throws PhrescoException {
		try {
			String fileToString = FileUtils.fileRead(configFile);
			String content = Base64.encodeBase64String(fileToString.getBytes());
			FileUtils.fileWrite(configFile, content);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
}
