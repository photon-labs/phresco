package com.photon.phresco.service.client.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.RestResourceURIs;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.LogInfo;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.Utility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class ServiceManagerImpl implements ServiceManager, ServiceClientConstant {

    private static final String APPTYPES_FILE = "apptypes.json";
    private static final String SETTINGS_FILE = "settings.json";
    private static final String VIDEOS_FILE = "videos.json";
    
    // REST paths
    private String REST_APPS_PATH = "/apps";
    private String REST_APPS_UPDATE_PATH = "/apps/update";
    private String REST_SETTINGS_PATH = "/settings";
    private String REST_ENVE_PATH = "/settings/env";
    private String REST_VIDEOS_PATH = "/homepagevideos";
    private String REST_DOWNLOADS_PATH = "/downloads";
    private String REST_APPS_UPDATEDOC_PATH = "/apps/updatedocs";
    private String REST_LOGIN_PATH = "/login";
    private String REST_ADMIN_CONFIG_PATH = "/admin/config";
    private String REST_LOG_PATH = "/admin/log";
    private String REST_CREDENTIAL_PATH = "/repo/ci/credentialsxml";
    private String REST_CI_MAIL_PLUGIN = "/repo/ci/emailext";
    private String REST_CI_MAILER_HOME = "/repo/ci/mailxml";
    private String REST_CI_MAVEN_HOME = "/repo/ci/mavenhomexml";
    private String REST_CI_JDK_HOME = "/repo/ci/javahomexml";
    private String REST_CI_CONFIG_PATH = "/repo/ci/config";
    private String REST_CI_SVN_PATH = "/repo/ci/svn";
   
    /* OS Types */
    String OS_NAME= "os.name";
    String OS_ARCH = "os.arch";
    String WINDOWS = "Windows";
    String SERVER = "Server";
    String WINDOWS7 = "Windows 7";
    String MAC = "Mac";
    String LINUX = "Linux";
    String WINDOWS_CHECK = "win";
    String MAC_CHECK = "mac";
    String LINUX_CHECK = "nux";
    String OS_BIT64 = "64";
    String OS_BIT86 = "86";

    private static final Logger S_LOGGER = Logger.getLogger(ServiceManagerImpl.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();
    private Map<String, Technology> technologies = null;

    private Gson gson = null;
    private ServiceManagerCache cache = ServiceManagerCache.getInstance();
    
    private String serverPath = null;
    UserInfo userInfo = null;

	public ServiceManagerImpl(String serverPath) throws PhrescoException {
    	super();
    	this.serverPath = serverPath;
        gson = new Gson();
        initCache();
    }

    public ServiceManagerImpl(ServiceContext context) throws PhrescoException {
    	super();
    	init(context);
        /*gson = new Gson();
        initCache();*/
    }
    
    public <E> RestClient<E> getRestClient(String contextPath) {
		StringBuilder builder = new StringBuilder();
		builder.append(serverPath);
		builder.append("/");
		builder.append(contextPath);
		return new RestClient<E>(builder.toString());	
	}

    public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	private void init(ServiceContext context) {
		this.serverPath = (String) context.get(SERVICE_URL);
    	String password = (String) context.get(SERVICE_PASSWORD);
		String username = (String) context.get(SERVICE_USERNAME);
		doLogin(username, password);
	}
	
    private void doLogin(String username, String password) {
    	Credentials credentials = new Credentials(username, password); 
    	Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_LOGIN_PATH);
        resource.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, credentials);
        GenericType<UserInfo> genericType = new GenericType<UserInfo>() {};
        userInfo = response.getEntity(genericType);
    }
	
	
	

    private void initCache() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.initCache()");
		}
        List<ApplicationType> apptypes = null;
        List<VideoInfo> videoInfos = null;
        List<SettingsTemplate> settingsTemplate = null;
        List<Environment> enveInfo = null;

//        if (PhrescoFrameworkFactory.getFrameworkConfig().isCacheEnabled()) {
//            apptypes = loadAppTypesFromFile();
//            videoInfos = loadVideosFromFile();
//            settingsTemplate = loadSettingsTemplateFromFile();
//        }

        //Load AppTypes into Cache
        if (apptypes == null) {
            apptypes = getApplicationTypesFromServer();
        }

        //Load Videos into Cache
        if (videoInfos == null) {
            videoInfos = loadVideosFromService();
        }

        //Load Settings into Cache
        if (settingsTemplate == null) {
            settingsTemplate = loadSettingsTemplateFromService();
        }
        
        if (enveInfo == null) {
            enveInfo = getEnvInfoFromService();
        }
       

//        if (PhrescoFrameworkFactory.getFrameworkConfig().isCacheEnabled()) {
//            writeToFile(VIDEOS_FILE, videoInfos);
//            writeToFile(APPTYPES_FILE, apptypes);
//            writeToFile(SETTINGS_FILE, settingsTemplate);
//        }

        cache.add(APPTYPES_FILE, apptypes);
        cache.add(VIDEOS_FILE, videoInfos);
        cache.add(SETTINGS_FILE, settingsTemplate);
    }

    private List<SettingsTemplate> loadSettingsTemplateFromService() throws PhrescoException {
        if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.loadSettingsTemplateFromService()");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_SETTINGS_PATH);
        Builder builder = resource.accept(MediaType.APPLICATION_JSON_TYPE);

        GenericType<List<SettingsTemplate>> genericType = new GenericType<List<SettingsTemplate>>() {};
        return builder.get(genericType);
    }
    
    public List<Environment> getEnvInfoFromService() throws PhrescoException {
        if (debugEnabled) {
            S_LOGGER.debug("Entering Method ServiceManagerImpl.getEnvFromService()");
        }
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_ENVE_PATH);
        Builder builder = resource.accept(MediaType.APPLICATION_JSON_TYPE);

        GenericType<List<Environment>> genericType = new GenericType<List<Environment>>() {};
        return builder.get(genericType);
    }

    private List<VideoInfo> loadVideosFromService() throws PhrescoException {
        if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.loadVideosFromService()");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_VIDEOS_PATH);
        Builder builder = resource.accept(MediaType.APPLICATION_JSON_TYPE);
        GenericType<List<VideoInfo>> genericType = new GenericType<List<VideoInfo>>() {};
        return builder.get(genericType);
    }
    
    public List<DownloadInfo> getDownloadsFromService() throws PhrescoException {
        if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.loadVideosFromService()");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_DOWNLOADS_PATH + "/" + findOS() );
        Builder builder = resource.accept(MediaType.APPLICATION_JSON_TYPE);
        GenericType<List<DownloadInfo>> genericType = new GenericType<List<DownloadInfo>>() {};
        return builder.get(genericType);
    }
    
    private String findOS(){
    	String osName = System.getProperty(OS_NAME);
    	String osBit = System.getProperty(OS_ARCH);
    	if (osName.contains(WINDOWS)) {
			osName = WINDOWS;
		} else if (osName.contains(LINUX)) {
			osName = LINUX;
		} else if (osName.contains(MAC)) {
			osName = MAC;
		}else if (osName.contains(SERVER)) {
			osName = SERVER;
		}else if (osName.contains(WINDOWS7)) {
			osName = WINDOWS7.replace(" ", "");
		}
			
    	if (osBit.contains(OS_BIT64)) {
    		osBit = OS_BIT64;
    	} else {
    		osBit = OS_BIT86;
    	}
    	return osName.concat(osBit);
    }
    
    private void writeToFile(String fileName, List jsonObjects) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.writeToFile(String fileName, List jsonObjects)");
		}
        String settingsInfoJson = gson.toJson(jsonObjects);
        FileWriter writer = null;

        try {
        	if (debugEnabled) {
    			S_LOGGER.debug("writeToFile() file Name = "+fileName);
    		}
        	File file = new File(Utility.getPhrescoTemp(), fileName);
            writer = new FileWriter(file);
            writer.write(settingsInfoJson);
            writer.flush();
        } catch (IOException e) {
            throw new PhrescoException(e);
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new PhrescoException(e);
                }
            }
        }
    }

    private List<ApplicationType> getApplicationTypesFromServer() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getApplicationTypesFromServer()");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_APPS_PATH);
        Builder builder = resource.accept(MediaType.APPLICATION_JSON_TYPE);
        GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>() {};
        return builder.get(genericType);
    }

    private List<ApplicationType> loadAppTypesFromFile() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.loadAppTypesFromFile()");
		}
        File file = new File(Utility.getPhrescoTemp(), APPTYPES_FILE);
        if (!file.exists()) {
            return null;
        }

        FileReader reader = null;
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            Type type = new TypeToken<List<ApplicationType>>(){}.getType();
            return gson.fromJson(bufferedReader, type);
        } catch (FileNotFoundException e) {
            throw new PhrescoException(e);
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new PhrescoException(e);
                }
        }
    }

    private List<VideoInfo> loadVideosFromFile() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.loadVideosFromFile()");
		}
        File file = new File(Utility.getPhrescoTemp(), VIDEOS_FILE);
        if (!file.exists()) {
            return null;
        }

        FileReader reader = null;
        try {
            reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            Type type = new TypeToken<List<VideoInfo>>(){}.getType();
            return gson.fromJson(bufferedReader, type);
        } catch (FileNotFoundException e) {
          throw new PhrescoException(e);
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new PhrescoException(e);
                }
        }
    }

    private List<SettingsTemplate> loadSettingsTemplateFromFile() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.loadSettingsTemplateFromFile()");
		}
    	File file = new File(Utility.getPhrescoTemp(), SETTINGS_FILE);
        if (!file.exists()) {
            return null;
        }

        FileReader reader = null;
        try {
        	if (debugEnabled) {
    			S_LOGGER.debug("loadSettingsTemplateFromFile() file path = "+file.getPath());
    		}
        	reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            Type type = new TypeToken<List<SettingsTemplate>>(){}.getType();
            return gson.fromJson(bufferedReader, type);
        } catch (FileNotFoundException e) {
          throw new PhrescoException(e);
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new PhrescoException(e);
                }
        }
    }

    @SuppressWarnings("unchecked")
    public List<ApplicationType> getApplicationTypes() throws PhrescoException {
        return (List<ApplicationType>) cache.get(APPTYPES_FILE);
    }

    public ApplicationType getApplicationType(String name) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getApplicationType(String name)");
		}
    	if (debugEnabled) {
			S_LOGGER.debug("getApplicationType() Name = "+name);
		}
        Collection<ApplicationType> applicationTypes = getApplicationTypes();
        for (ApplicationType applicationType : applicationTypes) {
            if (applicationType.getName().equals(name)) {
                return applicationType;
            }
        }

        return null;
    }

    public Map<String, Technology> getAllTechnologies() throws PhrescoException {
        if(technologies == null) {
           technologies = new HashMap<String, Technology>(32);
           fillMapTechnologies();
        }

        return technologies;
    }

    private void fillMapTechnologies() throws PhrescoException {
        List<ApplicationType> applicationTypes = getApplicationTypes();
        for (ApplicationType applicationType : applicationTypes) {
            Collection<Technology> appTechnologies = applicationType.getTechnologies();
            for (Technology technology : appTechnologies) {
                technologies.put(technology.getId(), technology);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<SettingsTemplate> getSettingsTemplates() throws PhrescoException {
        return (List<SettingsTemplate>) cache.get(SETTINGS_FILE);
    }

    @SuppressWarnings("unchecked")
    public List<VideoInfo> getVideoInfos() throws PhrescoException {
        return (List<VideoInfo>) cache.get(VIDEOS_FILE);
    }

    public ClientResponse createProject(ProjectInfo info) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.createProject(ProjectInfo info)");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_APPS_PATH);
        resource.accept(MediaType.APPLICATION_OCTET_STREAM);
        if (debugEnabled) {
			S_LOGGER.debug("createProject() ProjectName = "+ info.getName());
		}
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, info);
        return response;
    }

    public ClientResponse updateProject(ProjectInfo info) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.updateProject(ProjectInfo info)");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_APPS_UPDATE_PATH);
        resource.accept(MediaType.APPLICATION_OCTET_STREAM);
        if (debugEnabled) {
			S_LOGGER.debug("updateProject() ProjectName = "+ info.getName());
		}
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, info);
        return response;
    }
    
    public ClientResponse updateDocumentProject(ProjectInfo info) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.updateProject(ProjectInfo info)");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_APPS_UPDATEDOC_PATH);
        resource.accept(MediaType.APPLICATION_OCTET_STREAM);
        if (debugEnabled) {
			S_LOGGER.debug("updateDocumentProject() ProjectName = "+ info.getName());
		}
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, info);
        return response;
    }
    
    public UserInfo doLogin(Credentials credentials) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.doLogin(Credentials credentials)");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_LOGIN_PATH);
        resource.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, credentials);
        GenericType<UserInfo> genericType = new GenericType<UserInfo>() {};
        UserInfo userInfo = response.getEntity(genericType);
        return userInfo;
    }
    
    public List<AdminConfigInfo> getAdminConfig() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getAdminConfig()");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_ADMIN_CONFIG_PATH);
        resource.accept(MediaType.APPLICATION_JSON_TYPE);
        ClientResponse clientResponse = resource.type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        GenericType<List<AdminConfigInfo>> genericType = new GenericType<List<AdminConfigInfo>>() {};
        return clientResponse.getEntity(genericType);
    }
    
    public String getCiConfigPath() throws PhrescoException {
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_CI_CONFIG_PATH);
        return resource.accept(MediaType.TEXT_PLAIN).get(String.class);
    }

    public String getCiSvnPath() throws PhrescoException {
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_CI_SVN_PATH);
        return resource.accept(MediaType.TEXT_PLAIN).get(String.class);
    }

	public ClientResponse sendReport(LogInfo loginfo) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.sendReport(LogInfo loginfo)");
		}
        Client client = ClientHelper.createClient();
        WebResource resource = client.resource(serverPath + REST_LOG_PATH);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, loginfo);
        return response;
	}
	
    public InputStream getCredentialXml() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getCredentialXml");
		}
    	
		Client client = ClientHelper.createClient();
		WebResource resource = client.resource(serverPath + REST_CREDENTIAL_PATH);
		resource.accept(MediaType.APPLICATION_XML);
		ClientResponse response = resource.type(MediaType.APPLICATION_XML).get(ClientResponse.class);
		InputStream is = response.getEntityInputStream();
        return is;
    }
    
    public InputStream getJdkHomeXml() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getJdkHomeXml");
		}
    	
		Client client = ClientHelper.createClient();
		WebResource resource = client.resource(serverPath + REST_CI_JDK_HOME);
		resource.accept(MediaType.APPLICATION_XML);
		ClientResponse response = resource.type(MediaType.APPLICATION_XML).get(ClientResponse.class);
		InputStream is = response.getEntityInputStream();
        return is;
    }
    
    public InputStream getMavenHomeXml() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getCredentialXml");
		}
    	
		Client client = ClientHelper.createClient();
		WebResource resource = client.resource(serverPath + REST_CI_MAVEN_HOME);
		resource.accept(MediaType.APPLICATION_XML);
		ClientResponse response = resource.type(MediaType.APPLICATION_XML).get(ClientResponse.class);
		InputStream is = response.getEntityInputStream();
        return is;
    }

    public InputStream getMailerXml() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getMailerXml");
		}
    	
		Client client = ClientHelper.createClient();
		WebResource resource = client.resource(serverPath + REST_CI_MAILER_HOME);
		resource.accept(MediaType.APPLICATION_XML);
		ClientResponse response = resource.type(MediaType.APPLICATION_XML).get(ClientResponse.class);
		InputStream is = response.getEntityInputStream();
        return is;
    }
    
    public ClientResponse getEmailExtPlugin() throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getEmailExtPlugin");
		}
    	
		Client client = ClientHelper.createClient();
		WebResource resource = client.resource(serverPath + REST_CI_MAIL_PLUGIN);
		resource.accept(MediaType.APPLICATION_OCTET_STREAM);
		ClientResponse response = resource.get(ClientResponse.class);
        return response;
    }

	/**
	 * Returns the modules for a technology specified by the techId
	 */
	public Collection<ModuleGroup> getModules(String techId) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getModules");
		}

    	return getModules(techId, ModuleGroup.MODULE_TYPE_MODULE);
	}

	/**
	 * Returns the modules for a technology specified by the techId
	 */
	public Collection<ModuleGroup> getJSLibraries(String techId) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getJSLibraries");
		}
        
    	return getModules(techId, ModuleGroup.MODULE_TYPE_JSLIB);
	}
	
	private Collection<ModuleGroup> getModules(String techId, String type) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getModules(techId, type)");
		}

		Client client = Client.create();
		WebResource resource = client.resource(serverPath + RestResourceURIs.REST_API_MODULES);
        Builder builder = resource.accept(MediaType.APPLICATION_JSON_TYPE);
        resource.queryParam(RestResourceURIs.REST_API_QUERY_PARAM_TECH_ID, techId);
        resource.queryParam(RestResourceURIs.REST_API_QUERY_PARAM_MODULE_TYPE, type);
        GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>() {};
        return builder.get(genericType);
	}
	
	public Technology getTechnology(String techId) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ServiceManagerImpl.getTechnology(techId)");
		}

		Client client = Client.create();
		
		StringBuilder path = new StringBuilder();
		path.append(serverPath);
		path.append(RestResourceURIs.REST_API_TECHNOLOGIES);
		path.append("/");
		path.append(techId);
		
		WebResource resource = client.resource(path.toString());
        Builder builder = resource.accept(MediaType.APPLICATION_JSON_TYPE);
        GenericType<Technology> genericType = new GenericType<Technology>() {};
        return builder.get(genericType);
	}

	public Collection<Server> getServers(String techId) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Database> getDatabases(String techId) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<WebService> getWebservices(String techId) throws PhrescoException {
		// TODO Auto-generated method stub
		return null;
	}
}

class ServiceManagerCache {

    private static final ServiceManagerCache CACHE_ = new ServiceManagerCache();

    private static final HashMap<String, Object> CACHEMAP = new HashMap<String, Object>();

    private ServiceManagerCache() {
    }

    public static final ServiceManagerCache getInstance() {
        return CACHE_;
    }

    public void add(String key, Object value) {
        CACHEMAP.put(key, value);
    }

    public Object get(String key) {
        return CACHEMAP.get(key);
    }

    public void remove(String key) {
        CACHEMAP.remove(key);
    }
}