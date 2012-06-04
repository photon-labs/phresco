package com.photon.phresco.model;

public class Document {

	int id ;
	String url;
	String content;
	String documentType;
	String contentType;
	int documentsid;

	public Document(int id, String url, String content, String documentType,
			String contentType, int documentsid) {
		super();
		this.id = id;
		this.url = url;
		this.content = content;
		this.documentType = documentType;
		this.contentType = contentType;
		this.documentsid = documentsid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Document [id=" + id + ", url=" + url + ", content=" + content
				+ ", documentType=" + documentType + ", contentType="
				+ contentType + ", documentsid=" + documentsid + "]";
	}
	/**
	 * @return the documentsid
	 */
	public int getDocumentsid() {
		return documentsid;
	}
	/**
	 * @param documentsid the documentsid to set
	 */
	public void setDocumentsid(int documentsid) {
		this.documentsid = documentsid;
	}
	public Document(int id, String url, String content, String documentType,
			String contentType) {
		super();
		this.id = id;
		this.url = url;
		this.content = content;
		this.documentType = documentType;
		this.contentType = contentType;
	}
	public Document () {

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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
