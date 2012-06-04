package com.photon.phresco.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DownloadInfo implements Serializable {

	private static final long serialVersionUID = -9197395975210628328L;

	String id;
	String name;
	String version;
	String downloadURL;
	String type;
	String fileName;
	String fileSize;
	String[] appliesTo;
	String[] platform;

	public DownloadInfo() {
	}

	public DownloadInfo(String id, String name, String version,
			String downloadURL, String type, String[] appliesTo,
			String[] platform) {
		super();
		this.id = id;
		this.name = name;
		this.version = version;
		this.downloadURL = downloadURL;
		this.type = type;
		this.appliesTo = appliesTo;
		this.platform = platform;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getAppliesTo() {
		return appliesTo;
	}

	public void setAppliesTo(String[] appliesTo) {
		this.appliesTo = appliesTo;
	}

	public String[] getPlatform() {
		return platform;
	}

	public void setPlatform(String[] platform) {
		this.platform = platform;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "DownloadInfo [id=" + id + ", name=" + name + ", version="
				+ version + ", downloadURL=" + downloadURL + ", type=" + type
				+ ", appliesTo=" + Arrays.toString(appliesTo) + ", platform="
				+ Arrays.toString(platform) + "]";
	}

}
