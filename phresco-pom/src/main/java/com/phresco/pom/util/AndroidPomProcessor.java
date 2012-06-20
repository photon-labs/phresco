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
import org.w3c.dom.Node;

import com.phresco.pom.android.AndroidProfile;
import com.phresco.pom.android.PomConstants;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Activation;
import com.phresco.pom.model.BuildBase;
import com.phresco.pom.model.BuildBase.Plugins;
import com.phresco.pom.model.Model.Profiles;
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
	 * @param activationbyDefault
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
	public void addAndroidProfile(String profileId,Boolean activationbyDefault,String defaultGoal, Plugin plugin,
			AndroidProfile androidProfile, PluginExecution execution,
			Element goalElement, List<Element> additionalConfig) throws JAXBException,
			PhrescoPomException, ParserConfigurationException {
		
		if(model.getProfiles()!= null) {
			for(Profile profile : model.getProfiles().getProfile()){
				if(profileId.equals(profile.getId())) {
					model.setProfiles(null);
				}	
			}
		}
			
		BuildBase base = new BuildBase();
		Plugins plugins = new Plugins();
		Executions executions = new Executions();
		Goals goals = new Goals();
//		Configuration configuration = new Configuration();
		Activation activation = new Activation();
		
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

			activation.setActiveByDefault(activationbyDefault);
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
			
			addProfile(profileId, activation, base, null);
			save();
			
		} else {
			throw new PhrescoPomException(POMErrorCode.KEYSTORE_NOT_FOUND);
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

	public boolean hasSigning() {
		Profiles profiles = model.getProfiles();
		if(profiles!=null) {
			for(Profile profile : model.getProfiles().getProfile()){
				if(profile.getId().equals("sign")) {
					return true;
				}
			}
		}return false;
	}
	
	public AndroidProfile getProfileElement(String id) throws PhrescoPomException{
		Profile profile = getProfile(id);
		AndroidProfile androidProfile = new AndroidProfile();
		List<Plugin> plugin = profile.getBuild().getPlugins().getPlugin();
		for (Plugin plugin2 : plugin) {
			List<PluginExecution> execution = plugin2.getExecutions().getExecution();
			for (PluginExecution pluginExecution : execution) {
				List<Element> any = pluginExecution.getConfiguration().getAny();
				processProfiles(androidProfile, any);
			}
		}
		return androidProfile;
	}

	private void processProfiles(AndroidProfile androidProfile, List<Element> any) {
		for (Element element : any) {
			String tagName = element.getTagName();
			if(tagName.equals("keystore")) {
				androidProfile.setKeystore(element.getTextContent());
			} else if(tagName.equals("storepass")) {
				androidProfile.setStorepass(element.getTextContent());
			} else if(tagName.equals("keypass")) {
				androidProfile.setKeypass(element.getTextContent());
			} else if(tagName.equals("alias")) {
				androidProfile.setAlias(element.getTextContent());
			}
		}
	}
}