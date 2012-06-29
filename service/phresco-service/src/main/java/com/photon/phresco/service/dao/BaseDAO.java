package com.photon.phresco.service.dao;

public abstract class BaseDAO {
	
	String id;
	
	public BaseDAO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BaseDAO [id=" + id + "]";
	}
	
}