package com.photon.phresco.model;

import java.io.Serializable;

public class Documentation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8501416224917860870L;
	
	public enum DocumentationType {
		DESCRIPTION, HELP_TEXT, TOOL_TIP, DOCUMENT
	}
	
	String id;
	String url;
	String content;
	DocumentationType type;
	
	public Documentation() {
	}
	
	public Documentation(String id,String url,String content){
		this.id = id;
		this.url = url;
		this.content = content;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public DocumentationType getType() {
		return type;
	}

	public void setType(DocumentationType type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Documentation [url=" + url + ", content=" + content + ", type="
				+ type + "]";
	}

}
