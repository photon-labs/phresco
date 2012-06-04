package com.photon.phresco.model;

import java.io.Serializable;

public class PilotTechModuleVersions implements Serializable  {
	 /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	int id;
    String Version;
    int pilotTechModulesid;

	public PilotTechModuleVersions(int id, String version,
			int pilotTechModulesid) {
		super();
		this.id = id;
		Version = version;
		this.pilotTechModulesid = pilotTechModulesid;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	public PilotTechModuleVersions (){

	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PilotTechModuleVersions [id=" + id + ", Version=" + Version
				+ ", pilotTechModulesid=" + pilotTechModulesid + "]";
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return Version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		Version = version;
	}
	/**
	 * @return the pilotTechModulesid
	 */
	public int getPilotTechModulesid() {
		return pilotTechModulesid;
	}
	/**
	 * @param pilotTechModulesid the pilotTechModulesid to set
	 */
	public void setPilotTechModulesid(int pilotTechModulesid) {
		this.pilotTechModulesid = pilotTechModulesid;
	}
}
