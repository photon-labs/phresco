package com.photon.phresco.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DownloadInfos {

	List<DownloadInfo> downloadinfo;

	public List<DownloadInfo> getInfos() {
		return downloadinfo;
	}

	public void setInfos(List<DownloadInfo> infos) {
		this.downloadinfo = infos;
	}
}
