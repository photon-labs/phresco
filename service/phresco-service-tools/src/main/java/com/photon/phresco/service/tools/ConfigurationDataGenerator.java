/*
 * ###
 * Phresco Service Tools
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
/*******************************************************************************
  * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.I18NString;
import com.photon.phresco.model.L10NString;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.TechnologyTypes;

public class ConfigurationDataGenerator implements Constants {

    public List<SettingsTemplate> createSettingsAsJSON()
            throws PhrescoException {
        List<SettingsTemplate> settings = new ArrayList<SettingsTemplate>();
        settings.add(createServerTemplate());
        settings.add(createDatabaseTemplate());
        settings.add(createEmailTemplate());
        settings.add(createWebServiceTemplate());

        

        return settings;
    }

    private SettingsTemplate createServerTemplate() {
        List<PropertyTemplate> props = new ArrayList<PropertyTemplate>(8);

        List<String> possibleValues = new ArrayList<String>(8);
        possibleValues.add("http");
        possibleValues.add("https");
        props.add(createPropertyTemplate(SERVER_PROTOCOL, createI18NString("Protocol"), "String", false,true,
                possibleValues, createI18NString("Protocol to access the application")));

        props.add(createPropertyTemplate(SERVER_HOST, createI18NString("Host"), "String", false,true,
                null, createI18NString("Host Name or IPAddress of the server")));
        props.add(createPropertyTemplate(SERVER_PORT, createI18NString("Port"), "Number", false,true, null,
                createI18NString("Port number of the server")));
        props.add(createPropertyTemplate(SERVER_ADMIN_USERNAME, createI18NString("Admin Username"), "String", false,false, null,
                createI18NString("User name to access the server")));
        props.add(createPropertyTemplate(SERVER_ADMIN_PASSWORD, createI18NString("Admin Password"), "Password", false,false, null,
                createI18NString("Password to access the server")));
        props.add(createPropertyTemplate(SERVER_DEPLOY_DIR, createI18NString("Deploy Directory"), "String", false,true, null,
                createI18NString("Deployment directory of the server on the instance")));
        List<String> servers = new ArrayList<String>();
        servers.add("Wamp");
        servers.add("Tomcat");
        servers.add("MicroApache");
        servers.add("XAMPP");

        props.add(createPropertyTemplate(SERVER_TYPE, createI18NString("Type"), "String", false,true, servers,
                createI18NString("Type of the server")));

        props.add(createPropertyTemplate(SERVER_CONTEXT, createI18NString("Context"), "String", false, false,null,
                createI18NString("Server context of the application")));

        List<String> appsTo = new ArrayList<String>(8);
        appsTo.add(TechnologyTypes.PHP);
        appsTo.add(TechnologyTypes.PHP_DRUPAL7);
        appsTo.add(TechnologyTypes.SHAREPOINT);
        appsTo.add(TechnologyTypes.HTML5_WIDGET);
        appsTo.add(TechnologyTypes.JAVA_WEBSERVICE);
        appsTo.add(TechnologyTypes.NODE_JS_WEBSERVICE);
        return new SettingsTemplate(SETTINGS_TEMPLATE_SERVER, props, appsTo);
    }

    private I18NString createI18NString(String desc) {
        I18NString displayName;
        L10NString value;
        displayName = new I18NString();
        value = new L10NString("en-US", desc);
        displayName.add(value);
        return displayName;
    }

    private PropertyTemplate createPropertyTemplate(String key, I18NString name, String type,
            boolean isProjSpecific, boolean isRequired , List<String> possibleValues, I18NString description) {
        PropertyTemplate propTemplate = new PropertyTemplate(key, type, isProjSpecific ,isRequired);
        propTemplate.setPossibleValues(possibleValues);
        propTemplate.setName(name);
        propTemplate.setDescription(description);
        return propTemplate;
    }

    private SettingsTemplate createDatabaseTemplate() {
        List<PropertyTemplate> props = new ArrayList<PropertyTemplate>(8);

        props.add(createPropertyTemplate(DB_HOST, createI18NString("Host"), "String", false,true,
                null, createI18NString("Name or IPAddress of the database server")));
        props.add(createPropertyTemplate(DB_PORT, createI18NString("Port"), "Number", false,true, null,
                createI18NString("Port number of the database server")));
        props.add(createPropertyTemplate(DB_USERNAME, createI18NString("Username"), "String", false,true, null,
                createI18NString("User name to access the database")));
        props.add(createPropertyTemplate(DB_PASSWORD, createI18NString("Password"), "String", false,false, null,
                createI18NString("Password to access the database")));
        props.add(createPropertyTemplate(DB_NAME, createI18NString("DB Name"), "String", true,true, null,
                createI18NString("Name of the database")));
        props.add(createPropertyTemplate(DB_TYPE, createI18NString("Type"), "String", false,true, null,
                createI18NString("Type of the database")));
        props.add(createPropertyTemplate(DB_DRIVER, createI18NString("DB Driver"), "String", false,true, null,
                createI18NString("Java Driver to connect to the database")));
        props.add(createPropertyTemplate(DB_TABLE_PREFIX, createI18NString("Table Prefix"), "String", false,false, null,
                createI18NString("Prefix for the tables")));

        List<String> appsTo = new ArrayList<String>(16);
        appsTo.add(TechnologyTypes.PHP);
        appsTo.add(TechnologyTypes.PHP_DRUPAL7);
        appsTo.add(TechnologyTypes.SHAREPOINT);
        appsTo.add(TechnologyTypes.HTML5_WIDGET);
        appsTo.add(TechnologyTypes.HTML5_MOBILE_WIDGET);
        appsTo.add(TechnologyTypes.JAVA_WEBSERVICE);
        appsTo.add(TechnologyTypes.NODE_JS_WEBSERVICE);
        System.out.println ("dbtemplate-->"+appsTo);
        return new SettingsTemplate(SETTINGS_TEMPLATE_DB, props, appsTo);
    }

    private SettingsTemplate createEmailTemplate() {
        List<PropertyTemplate> props = new ArrayList<PropertyTemplate>(8);
        props.add(createPropertyTemplate(EMAIL_HOST, createI18NString("Host"), "String", false,true, null,
                createI18NString("Name or IPAddress of the email server")));
        props.add(createPropertyTemplate(EMAIL_PORT, createI18NString("Port"), "String", false,true, null,
                createI18NString("Name or IPAddress of the email server")));
        props.add(createPropertyTemplate(EMAIL_USER, createI18NString("Username"), "String", false,true, null,
                createI18NString("email address to be configured")));
        props.add(createPropertyTemplate(EMAIL_PASSWORD, createI18NString("Password"), "Password", false,true, null,
                createI18NString("Password for the email address")));

        List<String> appsTo = new ArrayList<String>(8);
        appsTo.add(TechnologyTypes.PHP);
        appsTo.add(TechnologyTypes.PHP_DRUPAL7);
        appsTo.add(TechnologyTypes.SHAREPOINT);
        System.out.println ("Email template-->"+appsTo);
        return new SettingsTemplate(SETTINGS_TEMPLATE_EMAIL, props, appsTo);
    }


    private SettingsTemplate createWebServiceTemplate() {
        List<PropertyTemplate> props = new ArrayList<PropertyTemplate>(8);

        List<String> possibleValues = new ArrayList<String>(8);
        possibleValues.add("http");
        possibleValues.add("https");

        props.add(createPropertyTemplate(WEB_SERVICE_PROTOCOL, createI18NString("Protocol"), "String", false,true,
                possibleValues, createI18NString("Protocol to access the service")));
        props.add(createPropertyTemplate(WEB_SERVICE_HOST, createI18NString("Host"), "String", false,true,
                null, createI18NString("Name or IPAddress of the service")));
        props.add(createPropertyTemplate(WEB_SERVICE_PORT, createI18NString("Port"), "Number", false,true, null,
                createI18NString("Port number of the service")));
        props.add(createPropertyTemplate(WEB_SERVICE_USERNAME, createI18NString("Username"), "String", false,true, null,
                createI18NString("User name to access the service")));
        props.add(createPropertyTemplate(WEB_SERVICE_PASSWORD, createI18NString("Password"), "Password", false,true, null,
                createI18NString("Password to access the service")));
        props.add(createPropertyTemplate(WEB_SERVICE_CONTEXT, createI18NString("Context"), "String", false,true, null,
                createI18NString("Context of the service")));

        List<String> appsTo = new ArrayList<String>(8);
        appsTo.add(TechnologyTypes.PHP);
        appsTo.add(TechnologyTypes.PHP_DRUPAL7);
        appsTo.add(TechnologyTypes.SHAREPOINT);
        appsTo.add(TechnologyTypes.HTML5);
        appsTo.add(TechnologyTypes.HTML5_WIDGET);
        appsTo.add(TechnologyTypes.ANDROID_NATIVE);
        appsTo.add(TechnologyTypes.ANDROID_HYBRID);
        appsTo.add(TechnologyTypes.IPHONE_NATIVE);
        appsTo.add(TechnologyTypes.IPHONE_HYBRID);
        System.out.println ("web service template-->"+appsTo);
        return new SettingsTemplate(SETTINGS_TEMPLATE_WEBSERVICE, props, appsTo);
    }

    public void writeToFile(File file, List<SettingsTemplate> templates) {
        Gson gson = new Gson();
        String json = gson.toJson(templates);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateConfiguration(File file) throws PhrescoException {
        ConfigurationDataGenerator conf = new ConfigurationDataGenerator();
        List<SettingsTemplate> template = conf.createSettingsAsJSON();
        conf.writeToFile(file, template);
    }

    public static void main(String args[]) throws PhrescoException {
        ConfigurationDataGenerator generator = new ConfigurationDataGenerator();
        generator.publish();
    }

    public void writeFile(File file) throws PhrescoException{
    	ConfigurationDataGenerator conf = new ConfigurationDataGenerator();
        List<SettingsTemplate> template = conf.createSettingsAsJSON();
        conf.writeToFile(file, template);
    }

    public void publish() throws PhrescoException{
    	File file = new File("D:/settings.json");
    	writeFile(file);
    	ConfigurationDataGenerator generator = new ConfigurationDataGenerator();
    	 PhrescoServerFactory.initialize();
 		RepositoryManager repositoryManager = PhrescoServerFactory.getRepositoryManager();
 		ArtifactInfo info = new ArtifactInfo("config.","settings", "", "json", "1.0.0");
 		repositoryManager.addArtifact(info, file);
    }
}
