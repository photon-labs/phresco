package com.photon.phresco.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VideoInfo {
	
	String id;
	String name;
	String description;
	String imageurl;
	String categories;
	String helpText;
	List<VideoType> videoList;

	public VideoInfo() {
	}

	public VideoInfo(String name, String description, String imageurl,String categories,String helpText,
			List<VideoType> videoList) {
		this.name = name;
		this.description = description;
		this.imageurl = imageurl;
		this.description = categories;
		this.helpText = helpText;
		this.videoList = videoList;
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

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public List<VideoType> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<VideoType> videoList) {
		this.videoList = videoList;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
