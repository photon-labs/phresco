package com.photon.phresco.model;

import java.io.Serializable;

public class Libraries implements Serializable {
	 /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int id;
	 String name;
	 /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Libraries [id=" + id + ", name=" + name + "]";
	}
	public Libraries(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Libraries (){
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

}
