package com.photon.phresco.model;

public class Documents {
	int id ;
	int documentId;
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
	 * @return the documentId
	 */
	public int getDocumentId() {
		return documentId;
	}
	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public Documents(int id, int documentId) {
		super();
		this.id = id;
		this.documentId = documentId;
	}
	public Documents () {

	}

}
