package com.phresco.pom.android;

import com.phresco.pom.model.Activation;
import com.phresco.pom.model.Build.Plugins;
import com.phresco.pom.model.Plugin.Configuration;

public class AndroidProfile {

	private String profileName = "release";
	private String keystore;
	private String storepass;
	private String keypass;
	private String alias;
	private boolean verbose = true;
	private Configuration config;
	private Plugins plugins;
	private Activation activation;


	public Activation getActivation() {
		return activation;
	}
	public void setActivation(Activation activation) {
		this.activation = activation;
	}
	public Plugins getPlugins() {
		return plugins;
	}
	public void setPlugins(Plugins plugins) {
		this.plugins = plugins;
	}
	public Configuration getConfig() {
		return config;
	}
	public void setConfig(Configuration config) {
		this.config = config;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getKeystore() {
		return keystore;
	}
	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}
	public String getStorepass() {
		return storepass;
	}
	public void setStorepass(String storepass) {
		this.storepass = storepass;
	}
	public String getKeypass() {
		return keypass;
	}
	public void setKeypass(String keypass) {
		this.keypass = keypass;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public boolean isVerbose() {
		return verbose;
	}
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	public boolean isVerify() {
		return verify;
	}
	public void setVerify(boolean verify) {
		this.verify = verify;
	}
	private boolean verify = true;

}
