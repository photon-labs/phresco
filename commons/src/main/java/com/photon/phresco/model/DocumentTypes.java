//package com.photon.phresco.service.model;
package	com.photon.phresco.model;


public class DocumentTypes {
	String id;
	String key;
	String value;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	public DocumentTypes(String id, String key, String value) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
	}
	public DocumentTypes() {
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
