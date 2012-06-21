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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.phresco.pom.android.AndroidProfile;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Activation;
import com.phresco.pom.model.Build;
import com.phresco.pom.model.BuildBase;
import com.phresco.pom.model.Build.Plugins;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.model.Model;
import com.phresco.pom.model.Model.Dependencies;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.model.Model.Profiles;
import com.phresco.pom.model.Model.Properties;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.model.Plugin.Configuration;
import com.phresco.pom.model.PluginExecution;
import com.phresco.pom.model.Profile;
import com.phresco.pom.model.ReportPlugin;
import com.phresco.pom.model.ReportPlugin.ReportSets;
import com.phresco.pom.model.ReportSet;
import com.phresco.pom.model.ReportSet.Reports;
import com.phresco.pom.model.Reporting;


/**
 * 
 * 
 * Example
 *      PomProcessor processor = new PomProcessor(new File("D:\\POM\\pom.xml"));
		processor.addDependency("com.suresh.marimuthu", "artifact" ,"2.3");
		processor.save();
 * 
 * @author suresh_ma
 *
 */

public class PomProcessor {

	/**
	 * 
	 */
	protected Model model;

	/**
	 * 
	 */
	protected File file;

	/**
	 * @param pomFile
	 * @throws JAXBException
	 * @throws IOException 
	 */
	public PomProcessor(File pomFile) throws JAXBException, IOException {
		if(pomFile.exists()){
			JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			model = (Model) ((JAXBElement)jaxbUnmarshaller.unmarshal(pomFile)).getValue();
		} else {
			pomFile.createNewFile();
			model = new Model();
		}
		file = pomFile;
	}

	/**
	 * Adds dependency
	 * 
	 * @param groupId
	 * @param artifactId
	 * @param version
	 * @param scope
	 * @throws JAXBException
	 * @throws PhrescoPomException 
	 */
	public void addDependency(String groupId, String artifactId, String version, String scope) throws JAXBException, PhrescoPomException {
		addDependency(groupId, artifactId, version, scope, null);
	} 
	
	public void addDependency(String groupId, String artifactId, String version, String scope, String type) throws JAXBException, PhrescoPomException {
        if(isDependencyAvailable(groupId, artifactId)){
            changeDependencyVersion(groupId, artifactId, version);
            return;
        }
        Dependency dependency = new Dependency();
        dependency.setArtifactId(artifactId);
        dependency.setGroupId(groupId);
        dependency.setVersion(version);
        if(StringUtils.isNotBlank(scope)){
            dependency.setScope(scope);
        }
        if(StringUtils.isNotBlank(type)){
            dependency.setType(type);
        }
        addDependency(dependency);
    } 
	
	/**
	 * @param groupId
	 * @param artifactId
	 * @return
	 * @throws PhrescoPomException
	 */
	public boolean isDependencyAvailable(String groupId,String artifactId) throws PhrescoPomException{
		if(model.getDependencies()==null){
			return false;
		}
		List<Dependency> list = model.getDependencies().getDependency();
		for(Dependency dependency : list){
			if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param dependency
	 * @throws PhrescoPomException 
	 */
	public void addDependency(Dependency dependency) throws PhrescoPomException {
		String groupId = dependency.getGroupId();
		String artifactId = dependency.getArtifactId();
		String version = dependency.getVersion();
		if(isDependencyAvailable(groupId, artifactId)){
			changeDependencyVersion(groupId, artifactId, version);
			return;
		}
		Dependencies dependencies = model.getDependencies();
		if(dependencies == null) {
			dependencies = new Dependencies();
			model.setDependencies(dependencies);
		}
		List<Dependency> dependencyList = dependencies.getDependency();
		dependencyList.add(dependency);
	}

	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 * @throws PhrescoPomException
	 */
	public void changeDependencyVersion(String groupId, String artifactId,String version) throws PhrescoPomException {
		if(model.getDependencies()==null){
			return;
		}
		List<Dependency> list = model.getDependencies().getDependency();
		for(Dependency dependency : list){
			if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)){
				dependency.setVersion(version);
			} 
		}
	}

	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 * @throws JAXBException
	 * @throws PhrescoPomException 
	 */
	public void addDependency(String groupId, String artifactId, String version) throws JAXBException, PhrescoPomException {
		if(isDependencyAvailable(groupId, artifactId)){
			changeDependencyVersion(groupId, artifactId, version);
		}
		addDependency(groupId, artifactId, version, "");
	}

	/**
	 * @param groupId
	 * @param artifactId
	 */
	public Boolean deleteDependency(String groupId, String artifactId) throws PhrescoPomException{
		boolean isFound = false;
		if(model.getDependencies()== null) {
			return isFound;
		}
		List<Dependency> list = model.getDependencies().getDependency();
		for(Dependency dependency : list){
			if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)){
				model.getDependencies().getDependency().remove(dependency);
				isFound = true;
				break;
			} 
		}
		if(model.getDependencies().getDependency().isEmpty()){
			model.setDependencies(null);
		}
		return isFound;
	}

	/**
	 * 
	 * @param groupId
	 * @param artifactId
	 * @throws PhrescoPomException
	 */
	public void deleteAllDependencies(String groupId,String artifactId) throws PhrescoPomException{
		boolean flag = true;
		while(flag){
			flag = deleteDependency(groupId, artifactId);
		}
	}

	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 * @param name
	 * @param packaging
	 * @param description
	 */
	public void setModel(String groupId,String artifactId,String version,String name,String packaging,String description){

		model.setGroupId(groupId);
		model.setArtifactId(artifactId);
		model.setVersion(version);
		model.setName(name);
		model.setPackaging(packaging);
		model.setDescription(description);
	}
	
	/**
	 * 
	 * @param version
	 * @throws PhrescoPomException
	 */
	public void setModelVersion(String version) throws PhrescoPomException{
		model.setVersion(version);
	}

	/**
	 * @return Model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * @throws PhrescoPomException 
	 * 
	 */
	public void removeAllDependencies() throws PhrescoPomException {

		if(model.getDependencies() == null){
			return;
		}
		List <Dependency> list = model.getDependencies().getDependency();
		model.getDependencies().getDependency().removeAll(list);
		model.setDependencies(null);
	}

	/**
	 * @param groupId
	 * @param artifactId
	 * @param version
	 * @return
	 * @throws PhrescoPomException
	 */
	public Plugin addPlugin(String groupId,String artifactId, String version) throws PhrescoPomException {
		Plugin existingPlugin = getPlugin(groupId, artifactId);
		if(existingPlugin != null) {
			existingPlugin.setVersion(version);
			return existingPlugin;
		}
		Build build = model.getBuild();
		if(build == null){
			build = new Build();
			model.setBuild(build);
		}
		Plugins plugins = build.getPlugins();
		if(plugins == null ) {
			plugins = new Plugins();
			build.setPlugins(plugins);
		}
		Plugin plugin = new Plugin();
		plugin.setArtifactId(artifactId);
		plugin.setGroupId(groupId);
		plugin.setVersion(version);
		plugins.getPlugin().add(plugin);
		return plugin;
	}
	
	/**
	 * @param groupId
	 * @param artifactId
	 * @throws PhrescoPomException
	 */
	public void deletePlugin(String groupId,String artifactId) throws PhrescoPomException {
		Plugin plugin = getPlugin(groupId, artifactId);
		if(plugin != null) {
			model.getBuild().getPlugins().getPlugin().remove(plugin);
		} 
		if(model.getBuild().getPlugins().getPlugin().isEmpty()){
			model.getBuild().setPlugins(null);
		}
	}

	/**
	 * @param pluginGroupId
	 * @param pluginArtifactId
	 * @param configList
	 * @return
	 * @throws PhrescoPomException
	 */
	public Configuration addConfiguration(String pluginGroupId,String pluginArtifactId, List<Element> configList) throws PhrescoPomException {
		return addConfiguration(pluginGroupId, pluginArtifactId, configList, false);
	}
	
	
	/**
	 * @param pluginGroupId
	 * @param pluginArtifactId
	 * @param configList
	 * @param overwrite
	 * @return
	 * @throws PhrescoPomException
	 */
	public Configuration addConfiguration(String pluginGroupId,String pluginArtifactId, List<Element> configList, boolean overwrite) throws PhrescoPomException {
		Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
		Configuration configuration = plugin.getConfiguration();
		
		if (configuration == null) {
			configuration = new Configuration();
			plugin.setConfiguration(configuration);
		}
		if (overwrite) {
			configuration.getAny().addAll(configList);
		} else {			
			plugin.getConfiguration().getAny().clear();
			configuration.getAny().addAll(configList);
			}
		return configuration;
	}

	/**
	 * @param pluginGroupId
	 * @param pluginArtifactId
	 * @param tagName
	 * @throws PhrescoPomException
	 */
	public String getPluginConfigurationValue(String pluginGroupId,String pluginArtifactId,String tagName) throws PhrescoPomException{
		Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
		Configuration configuration = plugin.getConfiguration();
		if(model.getBuild() != null && model.getBuild().getPlugins() != null && configuration !=null) {
			for (Element config : configuration.getAny()) {
				if(tagName.equals(config.getTagName())){
					return config.getTextContent();
				}	
			}
		}
		return "";
	}
	/**
	 * @param pluginGroupId
	 * @param pluginArtifactId
	 * @throws ParserConfigurationException
	 * @throws PhrescoPomException
	 */
	public com.phresco.pom.model.Plugin.Dependencies addPluginDependency(String pluginGroupId,String pluginArtifactId, Dependency dependency) throws ParserConfigurationException, PhrescoPomException{
		Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
		com.phresco.pom.model.Plugin.Dependencies dependencies = plugin.getDependencies();
		if(dependencies == null){
			dependencies = new Plugin.Dependencies();
			plugin.setDependencies(dependencies);
			plugin.getDependencies().getDependency().add(dependency);
		} else {
			return null;
		}
		return dependencies;
	}

	/**
	 * @param groupId
	 * @param artifactId
	 * @return
	 * @throws PhrescoPomException
	 */
	public Plugin getPlugin(String groupId,String artifactId) throws PhrescoPomException{

		if(model.getBuild() != null && model.getBuild().getPlugins() != null) {
			for (Plugin plugin : model.getBuild().getPlugins().getPlugin()) {
				if(groupId.equals(plugin.getGroupId()) && artifactId.equals(plugin.getArtifactId())) {
					return plugin;
				}
			}
		}
		return null;
	}

	public void changePluginVersion(String groupId,String artifactId,String version) throws PhrescoPomException{
		if(model.getBuild() != null && model.getBuild().getPlugins() != null) {
			for (Plugin plugin : model.getBuild().getPlugins().getPlugin()) {
				if(groupId.equals(plugin.getGroupId()) && artifactId.equals(plugin.getArtifactId())) {
					plugin.setVersion(version);
				}
			}
		}
	}

	/**
	 * @param name
	 * @param value
	 * @throws ParserConfigurationException 
	 */
	
	public void setProperty(String name,String value) throws ParserConfigurationException {
		
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element element = doc.createElement(name);
		element.setTextContent(value);
		
		if(model.getProperties()==null){
			Properties properties = new Properties();
			model.setProperties(properties);
		}
		for(Element proElement : model.getProperties().getAny()){
			if(proElement.getTagName().equals(name)){
				proElement.setTextContent(value);
				return;
			}
		}
		model.getProperties().getAny().add(element);
	}

	/**
	 * @param propertyName
	 * @return
	 * @throws PhrescoPomException
	 */
	public String getProperty(String propertyName) throws PhrescoPomException {
		if(model.getProperties()==null) {
			return "";
		}
		List<Element> property = model.getProperties().getAny();
		int size = model.getProperties().getAny().size();
		for(int i=0;i<size;i++) { 
			if(propertyName.equals(property.get(i).getTagName())) {
				return property.get(i).getTextContent();
			}
		}
		return "";
	}

	/**
	 * @param moduleName
	 * @throws PhrescoPomException 
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	public void addModule(String moduleName) throws PhrescoPomException {
		if(getPomModule(moduleName).equals(moduleName)){
			return;
		}
		Modules modules = new Modules();
		if(model.getModules()==null){
			model.setModules(modules);
		} 
		model.getModules().getModule().add(moduleName);
	}
	
	/**
	 * @param moduleName
	 * @return
	 * @throws PhrescoPomException
	 */
	public String getPomModule(String moduleName) throws PhrescoPomException{
		if(model.getModules() != null){
			for(String moduleNames : model.getModules().getModule()) {
				if(moduleName.equals(moduleNames)) {
					return moduleNames;
				}
			}
		}
		return "";
	}

	/**
	 * @return
	 * @throws PhrescoPomException
	 */
	public Modules getPomModule() throws PhrescoPomException {
		return model.getModules();
	}

	/**
	 * @param moduleName
	 * @throws PhrescoPomException
	 */
	public void removeModule(String moduleName) throws PhrescoPomException {

		if(model.getModules() == null){
			return;
		}
		for(String moduleNames : model.getModules().getModule()) {
			if(moduleName.equals(moduleNames)) {
				model.getModules().getModule().remove(moduleNames);
				if(model.getModules().getModule().isEmpty()) {
					model.setModules(null);
				}
				break;
			}
		}
	}
	
	/**
	 * @param sourceDirectoryvalue
	 * @throws PhrescoPomException
	 */
	public void addSourceDirectory(String sourceDirectoryvalue) throws PhrescoPomException{
		Build build = model.getBuild();
		if(build==null){
			 build = new Build();
			 model.setBuild(build);
		}
		build.setSourceDirectory(sourceDirectoryvalue);
		
	}

	/**
	 * @return
	 * @throws PhrescoPomException
	 */
	public String getSourceDirectory() throws PhrescoPomException{
		if(model.getBuild().getSourceDirectory()==null){
			return "src";
		}
		return model.getBuild().getSourceDirectory();
	}

	/**
	 * @param id
	 * @param activation
	 * @param build
	 * @param modules
	 * @param dependency
	 * @throws PhrescoPomException
	 */
	public Profile addProfile(String id,Activation activation,BuildBase build,com.phresco.pom.model.Profile.Modules modules) throws PhrescoPomException {
		Profiles profiles = model.getProfiles();
		if(profiles ==null) {
			profiles = new Profiles();
			model.setProfiles(profiles);
		} 
		
		Profile profile = new Profile();
		for(Profile tmpProfile : model.getProfiles().getProfile()){
			if(tmpProfile.getId().equals(id)){
				profile = tmpProfile;
				break;
			}
		}
		
		profile.setId(id);		
		profile.setActivation(activation);
		profile.setBuild(build);
		profile.setModules(modules);
		model.getProfiles().getProfile().add(profile);
		return profile;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws PhrescoPomException
	 */
	public Profile addProfile(String id) throws PhrescoPomException {
		Profiles profiles = model.getProfiles();
		if(profiles ==null) {
			profiles = new Profiles();
			model.setProfiles(profiles);
		} 
		Profile profile = new Profile();
		for(Profile tmpProfile : model.getProfiles().getProfile()){
			if(tmpProfile.getId().equals(id)){
				profile = tmpProfile;
				break;
			}
		}
		
		profile.setId(id);	
		model.getProfiles().getProfile().add(profile);
		return profile;
	}

	/**
	 * @param id
	 * @return
	 * @throws PhrescoPomException
	 */
	public Profile getProfile(String id) throws PhrescoPomException {
		if(model.getProfiles().getProfile() != null){
			for(Profile profile : model.getProfiles().getProfile()){
				if(id.equals(profile.getId())) {
					return profile;
				}	
			}
		}
		return null;
	}
	
	
	/**
	 * @param reportPlugin
	 */
	
	public void siteReportConfig(ReportPlugin reportPlugin){
		com.phresco.pom.model.Reporting.Plugins plugins = new com.phresco.pom.model.Reporting.Plugins();
		if(model.getReporting()==null){
			model.setReporting(new Reporting());
			model.getReporting().setPlugins(plugins);
		}
		model.getReporting().getPlugins().getPlugin().add(reportPlugin);
	}
	
	/**
	 * @param groupId
	 * @param artifactId
	 */
	public void removeSitePlugin(String groupId,String artifactId) {
		com.phresco.pom.model.Reporting.Plugins plugins = model.getReporting().getPlugins();
		if(plugins.getPlugin()==null){
			return;
		}
		for (ReportPlugin plugin : plugins.getPlugin()) {
			if(groupId.equals(plugin.getGroupId()) && artifactId.equals(plugin.getArtifactId())){
				plugins.getPlugin().remove(plugin);
				if(model.getReporting().getPlugins().getPlugin().isEmpty()){
					model.setReporting(null);
				}
				return;
			}	
		}
	}
	
	/**
	 * @param finalName
	 */
	public void setFinalName(String finalName){
		model.getBuild().setFinalName(finalName);
	}
	
	/**
	 * @throws JAXBException
	 */
	public void save() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
		Marshaller marshal = jaxbContext.createMarshaller();
		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshal.marshal(model, file);
	}
}
