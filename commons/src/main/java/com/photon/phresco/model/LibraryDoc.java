/*
 * ###
 * Phresco Commons
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
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
