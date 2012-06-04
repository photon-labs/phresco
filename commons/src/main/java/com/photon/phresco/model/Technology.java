package com.photon.phresco.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("restriction")
@XmlRootElement
public class Technology implements Cloneable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String appTypeId;

    private String id;
    
    //Name of the Technology. [PHP, PHP with Drupal]
    private String name;

    //List of applicable frameworks
    private List<ModuleGroup> frameworks;

    //List of applicable JavaScript libraries.
    private List<ModuleGroup> jsLibraries;

    //List of modules applicable supported for the technology
    //[PHP -> login, wiki; Java -> Struts - login; Android -> Image Library]
    private List<ModuleGroup> modules;

	private List<Server> servers;

	private List<Database> databases;
	
	private List<WebService> webservices;
	
	private boolean emailSupported;
	
	private List<String> versions;

    public Technology() {
        super();
    }

    public Technology(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppTypeId() {
		return appTypeId;
	}

	public void setAppTypeId(String appTypeId) {
		this.appTypeId = appTypeId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModuleGroup> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<ModuleGroup> frameworks) {
        this.frameworks = frameworks;
    }

    public List<ModuleGroup> getJsLibraries() {
        return jsLibraries;
    }

    public void setJsLibraries(List<ModuleGroup> jsLibraries) {
        this.jsLibraries = jsLibraries;
    }

    public List<ModuleGroup> getModules() {
        return modules;
    }

    public void setModules(List<ModuleGroup> modules) {
        this.modules = modules;
    }

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public List<Database> getDatabases() {
		return databases;
	}

	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}

	public List<WebService> getWebservices() {
		return webservices;
	}

	public void setWebservices(List<WebService> webservices) {
		this.webservices = webservices;
	}

	public boolean isEmailSupported() {
		return emailSupported;
	}

	public void setEmailSupported(boolean emailSupported) {
		this.emailSupported = emailSupported;
	}
	
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	public List<String> getVersions() {
		return versions;
	}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(1024);
        builder.append("Technology [getId()=");
        builder.append(getId());
        builder.append(", getName()=");
        builder.append(getName());
        builder.append(", getAppTypeId()=");
        builder.append(getAppTypeId());
        builder.append(", getVersions()=");
        builder.append(getVersions());
        builder.append(", getFrameworks()=");
        builder.append(getFrameworks());
        builder.append(", getJsLibraries()=");
        builder.append(getJsLibraries());
        builder.append(", getModules()=");
        builder.append(getModules());
        builder.append(", getServers()=");
        builder.append(getServers());
        builder.append(", getDatabases()=");
        builder.append(getDatabases());
        builder.append(", getWebservices()=");
        builder.append(getWebservices());
        builder.append("]");
        return builder.toString();
    }
   
    @SuppressWarnings("unchecked")
	public Technology clone() {
    	Technology tech = new Technology();

    	tech.setId(getId());
    	tech.setName(getName());
    	tech.setAppTypeId(getAppTypeId());
    	
		if (databases != null && !databases.isEmpty()) {
			tech.setDatabases((List<Database>) ((ArrayList<Database>) databases).clone());
		}
		if (frameworks != null && !frameworks.isEmpty()) {
			tech.setFrameworks((List<ModuleGroup>) ((ArrayList<ModuleGroup>) frameworks).clone());
		}
		if (jsLibraries != null && !jsLibraries.isEmpty()) {
			tech.setJsLibraries((List<ModuleGroup>) ((ArrayList<ModuleGroup>) jsLibraries).clone());
		}
		if (modules != null && !modules.isEmpty()) {
			tech.setModules((List<ModuleGroup>) ((ArrayList<ModuleGroup>) modules).clone());
		}
		if (servers != null && !servers.isEmpty()) {
			tech.setServers((List<Server>) ((ArrayList<Server>) servers).clone());
		}
		if (webservices != null && !webservices.isEmpty()) {
			tech.setWebservices((List<WebService>) ((ArrayList<WebService>) this.webservices).clone());
		}
    	return tech;
    }

}