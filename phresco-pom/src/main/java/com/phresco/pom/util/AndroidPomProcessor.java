/*
 * ###
 * phresco-pom
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
package com.phresco.pom.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.phresco.pom.android.AndroidProfile;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Activation;
import com.phresco.pom.model.BuildBase;
import com.phresco.pom.model.BuildBase.Plugins;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.model.Plugin.Configuration;
import com.phresco.pom.model.Plugin.Executions;
import com.phresco.pom.model.Plugin.Goals;
import com.phresco.pom.model.PluginExecution;
import com.phresco.pom.model.Profile;

/**
 * @author suresh_ma
 * 
 */

public class AndroidPomProcessor extends PomProcessor {

	/**
	 * @param pomFile
	 * @throws JAXBException
	 * @throws IOException
	 */
	public AndroidPomProcessor(File pomFile) throws JAXBException, IOException {
		super(pomFile);
	}

	/**
	 * 
	 * @param profileId
	 * @param defaultGoal
	 * @param plugin
	 * @param androidProfile
	 * @param execution
	 * @param goalElement
	 * @param additionalConfig
	 * @param finalName
	 * @throws JAXBException
	 * @throws PhrescoPomException
	 * @throws ParserConfigurationException
	 */
	public void setProfile(String profileId,String defaultGoal, Plugin plugin,
			AndroidProfile androidProfile, PluginExecution execution,
			Element goalElement, List<Element> additionalConfig) throws JAXBException,
			PhrescoPomException, ParserConfigurationException {
		
		BuildBase base = new BuildBase();
		Plugins plugins = new Plugins();
		Executions executions = new Executions();
		Goals goals = new Goals();
		
		removeProfile(profileId);
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element element = doc.createElement(PomConstants.KEYSTORE);
		element.setTextContent(androidProfile.getKeystore());
		additionalConfig.add(element);
		element = doc.createElement(PomConstants.STOREPASS);
		element.setTextContent(androidProfile.getStorepass());
		additionalConfig.add(element);
		element = doc.createElement(PomConstants.KEYPASS);
		element.setTextContent(androidProfile.getKeypass());
		additionalConfig.add(element);
		element = doc.createElement(PomConstants.ALIAS);
		element.setTextContent(androidProfile.getAlias());
		additionalConfig.add(element);
		
		if (androidProfile.getKeystore() != null
				&& androidProfile.getKeypass() != null
				&& androidProfile.getStorepass() != null) {
			
			base.setDefaultGoal(defaultGoal);
			base.setFinalName(PomConstants.FINAL_NAME);

			plugin.setExecutions(executions);
			plugin.setGoals(goals);
			plugin.getGoals().getAny().add(goalElement);
			if(plugin.getGoals().getAny().isEmpty()) {
				plugin.setGoals(null);
			}
			execution.getConfiguration().getAny().addAll(additionalConfig);
			plugin.getExecutions().getExecution().add(execution);
//			plugin.setConfiguration(configuration);
//			plugin.getConfiguration().getAny().addAll(additionalConfig);
			plugins.getPlugin().add(plugin);
			base.setPlugins(plugins);
			
			addProfile(profileId, base, null);
			save();
			
		} else {
			throw new PhrescoPomException(POMErrorCode.KEYSTORE_NOT_FOUND);
		}
	}

	/**
	 * @param profileId
	 */
	private void removeProfile(String profileId) {
		if(model.getProfiles()!= null) {
			for(Profile profile : model.getProfiles().getProfile()) {
				if(profileId.equals(profile.getId())) {
					model.getProfiles().getProfile().remove(profile);
					return;
				}	 
			}
		}
	}

	/**
	 * @param id
	 * @param plugin
	 * @param configElement
	 * @throws PhrescoPomException
	 * @throws JAXBException
	 */
	public void addProfilePlugin(String id, Plugin plugin,
			List<Element> configElement) throws PhrescoPomException,
			JAXBException {
		Configuration configuration = new Configuration();
		if (getProfile(id) != null && getProfile(id).getId().equals(id)) {
			plugin.setConfiguration(configuration);
			plugin.getConfiguration().getAny().addAll(configElement);
			getProfile(id).getBuild().getPlugins().getPlugin().add(plugin);
			save();
		} else {
			throw new PhrescoPomException(POMErrorCode.PROFILE_ID_NOT_FOUND);
		}
	}

	/**
	 * @return
	 */
	public boolean hasSigning() {
		if(model.getProfiles() !=null && model.getProfiles().getProfile() != null){
			for(Profile profile : model.getProfiles().getProfile()){
				List<Plugin> plugin = profile.getBuild().getPlugins().getPlugin();
				for (Plugin plugin2 : plugin) {
					List<PluginExecution> execution = plugin2.getExecutions().getExecution();
					if(getSigningProfilePlugin(profile, execution)!=""){
						return true;
					}
				}
			}
		} return false;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws PhrescoPomException
	 */
	public AndroidProfile getProfileElement(String id) throws PhrescoPomException{
		Profile profile = getProfile(id);
		AndroidProfile androidProfile = new AndroidProfile();
		List<Plugin> plugin = profile.getBuild().getPlugins().getPlugin();
		for (Plugin plugin2 : plugin) {
			List<PluginExecution> execution = plugin2.getExecutions().getExecution();
			if(execution != null) {
				for (PluginExecution pluginExecution : execution) {
					if(pluginExecution.getConfiguration() != null){
						List<Element> any = pluginExecution.getConfiguration().getAny();
						processProfiles(androidProfile, any);
					}
				}
			}
		}
		return androidProfile;
	}

	/**
	 * @param androidProfile
	 * @param any
	 */
	private void processProfiles(AndroidProfile androidProfile, List<Element> any) {
		for (Element element : any) {
			String tagName = element.getTagName();
			if(tagName.equals(PomConstants.KEYSTORE)) {
				androidProfile.setKeystore(element.getTextContent());
			} else if(tagName.equals(PomConstants.STOREPASS)) {
				androidProfile.setStorepass(element.getTextContent());
			} else if(tagName.equals(PomConstants.KEYPASS)) {
				androidProfile.setKeypass(element.getTextContent());
			} else if(tagName.equals(PomConstants.ALIAS)) {
				androidProfile.setAlias(element.getTextContent());
			}
		}
	}
	
	/**
	 * @return
	 */
	public String getSigningProfile() {
		if(model.getProfiles() != null && model.getProfiles().getProfile() != null) {
			for(Profile profile : model.getProfiles().getProfile()){
				List<Plugin> plugin = profile.getBuild().getPlugins().getPlugin();
				for (Plugin plugin2 : plugin) {
					List<PluginExecution> execution = plugin2.getExecutions().getExecution();
					if(getSigningProfilePlugin(profile, execution).equals(profile.getId())) {
					return getSigningProfilePlugin(profile, execution);
				}
				}
			}
		}
		return "";
	}

	/**
	 * @param profile
	 * @param execution
	 * @return
	 */
	private String getSigningProfilePlugin(Profile profile,	List<PluginExecution> execution) {
		if(model.getProfiles() != null && model.getProfiles().getProfile() != null) {
			for (PluginExecution pluginExecution : execution) {
				if(pluginExecution.getConfiguration() != null){
				List<Element> any = pluginExecution.getConfiguration().getAny();
					for (Element element : any) {
						if(element.getTagName().equals(PomConstants.KEYSTORE)) {
							return profile.getId();
						}
					}
				}
			} 
		} return "";
	}
}