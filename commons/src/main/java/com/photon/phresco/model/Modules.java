package com.photon.phresco.model;

public class Modules {
	 int id;
	 String moduleId;
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
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}
	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public Modules(int id, String moduleId) {
		super();
		this.id = id;
		this.moduleId = moduleId;
	}
	public Modules () {

	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Modules [id=" + id + ", moduleId=" + moduleId + "]";
	}
}
