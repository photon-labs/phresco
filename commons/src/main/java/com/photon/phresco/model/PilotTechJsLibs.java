package com.photon.phresco.model;

import java.io.Serializable;

public class PilotTechJsLibs implements Serializable {
	   /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int id;
	   String jslibid;
       String name;
       int pilotTechid;
       PilotTechJsLibs () {

       }
       /**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	public PilotTechJsLibs(int id, String jslibid, String name, int pilotTechid) {
		super();
		this.id = id;
		this.jslibid = jslibid;
		this.name = name;
		this.pilotTechid = pilotTechid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PilotTechJsLibs [id=" + id + ", jslibid=" + jslibid + ", name="
				+ name + ", pilotTechid=" + pilotTechid + "]";
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the jslibid
	 */
	public String getJslibid() {
		return jslibid;
	}
	/**
	 * @param jslibid the jslibid to set
	 */
	public void setJslibid(String jslibid) {
		this.jslibid = jslibid;
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
		return pilotTechid;
	}
	/**
	 * @param pilotTechid the pilotTechid to set
	 */
	public void setPilotTechid(int pilotTechid) {
		this.pilotTechid = pilotTechid;
	}

       //List<Module> versions
}
