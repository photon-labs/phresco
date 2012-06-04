package com.photon.phresco.model;

public class LibraryDoc {

	  private int id;
	  private int libDocid;
	  private String url;
	  private String content;
	  private String documentType;
	  private String contentType;

	  public LibraryDoc (){

	  }

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	public LibraryDoc(int id, int libDocid, String url, String content,
			String documentType, String contentType) {
		super();
		this.id = id;
		this.libDocid = libDocid;
		this.url = url;
		this.content = content;
		this.documentType = documentType;
		this.contentType = contentType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LibraryDoc [id=" + id + ", libDocid=" + libDocid + ", url="
				+ url + ", content=" + content + ", documentType="
				+ documentType + ", contentType=" + contentType + "]";
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the libDocid
	 */
	public int getLibDocid() {
		return libDocid;
	}
	/**
	 * @param libDocid the libDocid to set
	 */
	public void setLibDocid(int libDocid) {
		this.libDocid = libDocid;
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
