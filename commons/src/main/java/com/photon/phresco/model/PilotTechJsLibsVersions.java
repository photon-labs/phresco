package com.photon.phresco.model;

import java.io.Serializable;

public class PilotTechJsLibsVersions implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	public PilotTechJsLibsVersions(int id, String version, int pilotTechJsLibid) {
		super();
		this.id = id;
		Version = version;
		this.pilotTechJsLibid = pilotTechJsLibid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PilotTechJsLibsVersions [id=" + id + ", Version=" + Version
				+ ", pilotTechJsLibid=" + pilotTechJsLibid + "]";
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the pilotTechJsLibid
	 */
	public int getPilotTechJsLibid() {
		return pilotTechJsLibid;
	}
	/**
	 * @param pilotTechJsLibid the pilotTechJsLibid to set
	 */
	public void setPilotTechJsLibid(int pilotTechJsLibid) {
		this.pilotTechJsLibid = pilotTechJsLibid;
	}
	int id;
    String Version;
    int pilotTechJsLibid;
}
