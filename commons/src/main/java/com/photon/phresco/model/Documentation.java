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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
