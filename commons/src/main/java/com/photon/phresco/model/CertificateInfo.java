package com.photon.phresco.model;

import java.security.cert.X509Certificate;

public class CertificateInfo {
	
	String displayName;
	String subjectDN;
	X509Certificate certificate;
	
	public CertificateInfo() {
		
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSubjectDN() {
		return subjectDN;
	}

	public void setSubjectDN(String subjectDN) {
		this.subjectDN = subjectDN;
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		return "CertificateInfo [displayName=" + displayName + ", subjectDN="
				+ subjectDN + ", certificate=" + certificate + "]";
	}
}