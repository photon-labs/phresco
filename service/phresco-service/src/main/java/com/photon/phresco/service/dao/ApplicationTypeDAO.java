package com.photon.phresco.service.dao;


public class ApplicationTypeDAO extends BaseDAO {
	
	private String name;
	private String displayName;
	private String description;
	
	public ApplicationTypeDAO() {
		super();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

    @Override
    public String toString() {
        return "ApplicationTypeDAO [name=" + name + ", displayName="
                + displayName + ", description=" + description + "]";
    }
	
}
