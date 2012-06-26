/*
 * ###
 * Phresco Commons
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
package com.photon.phresco.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.FileUtils;
import org.w3c.dom.Document;

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
				String line = s.next().trim();
				if(line.contains("--")){
					String comment = line.substring(line.indexOf("--"), line.lastIndexOf("--"));
					line = line.replace(comment, "");
					line = line.replace("--", "");
				}
				
				if (line.startsWith(Constants.REM_DELIMETER)) {
					String comment = line.substring(0, line.lastIndexOf("\n"));
					line = line.replace(comment, "");
				}
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
	public void encryptConfigFile(String fileName) throws PhrescoException {
		InputStream inputStream = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			inputStream = new FileInputStream(new File(fileName));
			Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			encryptString encryptstring = new encryptString();
			encryptstring.Crypto("D4:6E:AC:3F:F0:BE");
			String encryptXmlString = encryptstring.encrypt(stw.toString());
			writeXml(encryptXmlString, fileName);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}  finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	private void writeXml(String encrStr, String fileName) throws PhrescoException  {
		DataOutputStream dos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			dos = new DataOutputStream(fos);
			dos.writeBytes(encrStr);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			try {
				if (dos != null) {
					dos.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
}
