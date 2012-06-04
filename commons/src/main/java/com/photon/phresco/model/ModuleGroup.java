package com.photon.phresco.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.photon.phresco.model.Documentation.DocumentationType;
import com.photon.phresco.util.SizeConstants;

public class ModuleGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//String id of the module. this will be groupId:artifactId:
	String id;
	String groupId;
	String artifactId;
	String type;
	String name;
	String vendor;
	boolean core;	
	boolean required;
	List<Documentation> docs;
	List<Module> versions = new ArrayList<Module>(SizeConstants.SIZE_VERSIONS_MAP);

	public ModuleGroup() {
	}
	
	public ModuleGroup(String id, String name,String groupId,String artifactId,String type,String vendor,boolean core,boolean required,List<Documentation> docs,List<Module> modules) {
		this.id = id;
		this.name = name;
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.type = type;
		this.vendor = vendor;
		this.core = core;
		this.required = required;
		this.docs = docs;
		this.versions = modules;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public boolean isCore() {
		return core;
	}

	public void setCore(boolean core) {
		this.core = core;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<Documentation> getDocs() {
		return docs;
	}

	public void setDocs(List<Documentation> docs) {
		this.docs = docs;
	}

	public List<Module> getVersions() {
		return versions;
	}
	
	public Module getVersion(String version) {
		List<Module> moduleVersions = getVersions();
		for (Module moduleVersion : moduleVersions) {
			if (moduleVersion.getVersion().equals(version)) {
				return moduleVersion;
			}
		}
		return null;
	}

	public void setVersions(List<Module> versions) {
		this.versions = versions;
	}
	
	public Documentation getDoc(DocumentationType type) {
		List<Documentation> docs2 = getDocs();
		if (docs2 == null || docs2.isEmpty()) {
			return null;
		}
		
		for (Documentation documentation : docs2) {
			if (type.equals(documentation.getType())) {
				return documentation;
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModuleGroup [id=" + id + ", name=" + name + "]";
	}
	
}
