package com.photon.phresco.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GlobalURL {
	
	private String id;
	private String name;
	private String description;
	private String url;
	
	public GlobalURL(){
		
	}
	
	public GlobalURL(String id,String name,String description,String url){
		super();
		this.id=id;
		this.name=name;
		this.description=description;
		this.url=url;
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "GlobalURL [id=" + id + ", name=" + name + ", description="
				+ description + ", url=" + url + "]";
	}

}
