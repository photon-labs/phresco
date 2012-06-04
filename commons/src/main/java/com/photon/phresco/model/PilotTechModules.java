package com.photon.phresco.model;

import java.io.Serializable;

public class PilotTechModules implements Serializable  {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int id;
    String mgid;
    String name;
    int PilotTechid;

    //List<Module> versions
    public PilotTechModules (){

    }
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	public PilotTechModules(int id, String mgid, String name, int pilotTechid) {
		super();
		this.id = id;
		this.mgid = mgid;
		this.name = name;
		PilotTechid = pilotTechid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PilotTechModules [id=" + id + ", mgid=" + mgid + ", name="
				+ name + ", PilotTechid=" + PilotTechid + "]";
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the mgid
	 */
	public String getMgid() {
		return mgid;
	}
	/**
	 * @param mgid the mgid to set
	 */
	public void setMgid(String mgid) {
		this.mgid = mgid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the pilotTechid
	 */
	public int getPilotTechid() {
		return PilotTechid;
	}
	/**
	 * @param pilotTechid the pilotTechid to set
	 */
	public void setPilotTechid(int pilotTechid) {
		PilotTechid = pilotTechid;
	}
}
