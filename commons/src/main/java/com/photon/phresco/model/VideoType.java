package com.photon.phresco.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VideoType {
	String id;
	String type;
	String url;
	String codecs;

	public VideoType() {
	}

	public VideoType(String type, String url, String codecs) {
		setType(type);
		setUrl(url);
		setCodecs(codecs);

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCodecs() {
		return codecs;
	}

	public void setCodecs(String codecs) {
		this.codecs = codecs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
