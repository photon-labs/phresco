package com.photon.phresco.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.LogInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;

/**
 * Admin config file and Log service"
 */
@Path("admin")
public class AdminService implements ServerConstants {

		private static final Logger S_LOGGER = Logger.getLogger(AdminService.class);
		private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
		@GET
		@Path("/config")
		@Produces({ MediaType.APPLICATION_JSON })
		public List<AdminConfigInfo> getProducts()throws Exception {
			PhrescoServerFactory.initialize();
			RepositoryManager repoMgr = PhrescoServerFactory.getRepositoryManager();
			String config = repoMgr.getArtifactAsString(repoMgr.getAdminConfigFile());
			Gson gson = new Gson();
			Type type = new TypeToken<List<AdminConfigInfo>>(){}.getType();
			List<AdminConfigInfo> adminConfigInfos = gson.fromJson(config, type);
			return adminConfigInfos;
		}

		@POST
		@Path("/log")
		@Consumes(MediaType.APPLICATION_JSON)
		public void submitReport(LogInfo phrescoCrashReport)throws Exception {
	    	if (isDebugEnabled) {
				S_LOGGER.debug("Entering Method AdminService.submitReport" + phrescoCrashReport.toString());
			}
		}
		
		public static void main(String[] args) {
			String key = "phresco.forum.url";
			String value = "http://172.16.18.86:7070/jforum";
			AdminConfigInfo adminConfigInfo = new AdminConfigInfo(key, value);
			List<AdminConfigInfo> list = new ArrayList<AdminConfigInfo>(1);
			list.add(adminConfigInfo);
			Gson gson = new Gson();
			String jsonAdminConfig = gson.toJson(list);
			System.out.println(list);
			File file = new File ("/Users/bharatkumarradha/work/admin_config.json");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(jsonAdminConfig.getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
