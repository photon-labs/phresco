package com.photon.phresco.model;

public class LibraryTech {

	 private int id;
     private int libid;
     private String libTechDesc;
     public LibraryTech () {

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
	/**
	 * @return the libid
	 */
	public int getLibid() {
		return libid;
	}
	/**
	 * @param libid the libid to set
	 */
	public void setLibid(int libid) {
		this.libid = libid;
	}
	/**
	 * @return the libTechDesc
	 */
	public String getLibTechDesc() {
		return libTechDesc;
	}
	/**
	 * @param libTechDesc the libTechDesc to set
	 */
	public void setLibTechDesc(String libTechDesc) {
		this.libTechDesc = libTechDesc;
	}
	public LibraryTech(int id, int libid, String libTechDesc) {
		super();
		this.id = id;
		this.libid = libid;
		this.libTechDesc = libTechDesc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LibraryTech [id=" + id + ", libid=" + libid + ", libTechDesc="
				+ libTechDesc + "]";
	}

}
