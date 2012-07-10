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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.TechnologyTypes;
import com.sun.jersey.api.client.ClientResponse;

public class ApptypeGenerator  implements ServiceConstants {


	/*
	 *
	 */

    private final static String phpTechDoc = "PHP is a general-purpose server-side scripting language originally designed for web development to produce dynamic web pages. For this purpose, PHP code is embedded into the HTML source document and interpreted by a web server with a PHP processor module, which generates the web page document. It also has evolved to include a command-line interface capability and can be used in standalone graphical applications." +
            "PHP can be deployed on most web servers and as a standalone interpreter, on almost every operating system and platform free of charge.";

    private final static String DrupalTechDoc = "Drupal is a general-purpose server-side scripting language originally designed for web development to produce dynamic web pages. For this purpose, PHP code is embedded into the HTML source document and interpreted by a web server with a PHP processor module, which generates the web page document. It also has evolved to include a command-line interface capability and can be used in standalone graphical applications." +
    "PHP can be deployed on most web servers and as a standalone interpreter, on almost every operating system and platform free of charge.";

    private final static String nodejsDoc = "nodejs is a general-purpose server-side scripting language originally designed for web development to produce dynamic web pages. For this purpose, PHP code is embedded into the HTML source document and interpreted by a web server with a PHP processor module, which generates the web page document. It also has evolved to include a command-line interface capability and can be used in standalone graphical applications." +
    "PHP can be deployed on most web servers and as a standalone interpreter, on almost every operating system and platform free of charge.";

    private final static String SharepointDoc = "Sharepoint is a general-purpose server-side scripting language originally designed for web development to produce dynamic web pages. For this purpose, PHP code is embedded into the HTML source document and interpreted by a web server with a PHP processor module, which generates the web page document. It also has evolved to include a command-line interface capability and can be used in standalone graphical applications." +
    "PHP can be deployed on most web servers and as a standalone interpreter, on almost every operating system and platform free of charge.";
    
    public ServiceContext context = null;
    public ServiceManager serviceManager = null;
    
    public ApptypeGenerator() throws PhrescoException {
        // TODO Auto-generated constructor stub
        context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest/api");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
        serviceManager = ServiceClientFactory.getServiceManager(context);
    }

    public void generateApptypes() throws PhrescoException {
    	List<ApplicationType> applicationTypes = new ArrayList<ApplicationType>();

        ApplicationType web = createApptype("apptype-webapp", "Web Application");
        applicationTypes.add(web);

        ApplicationType mobile = createApptype("apptype-mobile", "Mobile Applications");
        applicationTypes.add(mobile);

        ApplicationType html5 = createApptype("apptype-web-services", "Web Services");
        applicationTypes.add(html5);
        
        RestClient<ApplicationType> applicationTypeClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
        ClientResponse response = applicationTypeClient.create(applicationTypes);
        System.out.println(response.getStatus());
    }

    public void createWebAppTechs() throws PhrescoException {
        List<Technology> techs = new ArrayList<Technology>();
        techs.add(createTechnology(TechnologyTypes.PHP, "PHP", new String[]{"5.4.x", "5.3.x", "5.2.x", "5.1.x", "5.0.x"}));
        techs.add(createTechnology(TechnologyTypes.PHP_DRUPAL6, "Drupal6", new String[]{"6.3", "6.25", "6.19"}));
        techs.add(createTechnology(TechnologyTypes.PHP_DRUPAL7, "Drupal7", new String[]{"7.8"}));
        techs.add(createTechnology(TechnologyTypes.SHAREPOINT, "Sharepoint", new String[]{"3.5", "3.0", "2.0"}));
        techs.add(createTechnology(TechnologyTypes.HTML5_WIDGET, "HTML5 Multichannel YUI Widget", new String[]{"1.6", "1.5"}));
        techs.add(createTechnology(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, "HTML5 Multichannel JQuery Widget", new String[]{"1.6", "1.5"}));
        techs.add(createTechnology("tech-html5-jquery-mobile-widget", "HTML5 JQuery Mobile Widget", new String[]{"1.6", "1.5"}));
        techs.add(createTechnology(TechnologyTypes.HTML5_MOBILE_WIDGET, "HTML5 YUI Mobile Widget", new String[]{"1.6", "1.5"}));
        techs.add(createTechnology(TechnologyTypes.DOT_NET, "ASP.NET", new String[]{"3.5", "3.0", "2.0"}));
        techs.add(createTechnology(TechnologyTypes.WORDPRESS, "WordPress", new String[]{"3.3.1"}));
        techs.add(createTechnology(TechnologyTypes.JAVA_STANDALONE, "Java Standalone", new String[]{"1.6", "1.5"}));
        
        serviceManager = ServiceClientFactory.getServiceManager(context);
        RestClient<Technology> techClient = serviceManager.getRestClient("/component/technologies");
        techClient.queryString("techId", "apptype-webapp");
        ClientResponse response = techClient.create(techs);
        System.out.println("response " + response.getStatus());
    }
    
    public void createMobAppTechs() throws PhrescoException {
        List<Technology> techs = new ArrayList<Technology>();
        techs.add(createTechnology(TechnologyTypes.ANDROID_NATIVE, "Android Native", new String[]{"4.0.3", "2.3.3", "2.2"}));
        techs.add(createTechnology(TechnologyTypes.ANDROID_HYBRID, "Android Hybrid", new String[]{"4.0.3", "2.3.3", "2.2"}));
        techs.add(createTechnology(TechnologyTypes.IPHONE_NATIVE, "iPhone Native", new String[]{}));
        techs.add(createTechnology(TechnologyTypes.IPHONE_HYBRID, "iPhone Hybrid", new String[]{}));
        serviceManager = ServiceClientFactory.getServiceManager(context);
        RestClient<Technology> techClient = serviceManager.getRestClient("/component/technologies");
        techClient.queryString("techId", "apptype-mobile");
        ClientResponse response = techClient.create(techs);
        System.out.println("response " + response.getStatus());
        
    }
    
    public void createWebServiceAppTechs() throws PhrescoException {
        List<Technology> techs = new ArrayList<Technology>();
        techs.add(createTechnology(TechnologyTypes.JAVA_WEBSERVICE, "Java Web Service", new String[]{"1.6", "1.5"}));
        techs.add(createTechnology(TechnologyTypes.NODE_JS_WEBSERVICE, "Node JS Web Service", new String[]{"6.14","6.11", "6.8","6.7", "6.1"}));
        serviceManager = ServiceClientFactory.getServiceManager(context);
        RestClient<Technology> techClient = serviceManager.getRestClient("/component/technologies");
        techClient.queryString("techId", "apptype-web-services");
        ClientResponse response = techClient.create(techs);
        System.out.println("response " + response.getStatus());
        
    }
    
    public void publish() throws PhrescoException {
        generateApptypes();
        createWebAppTechs();
        createMobAppTechs();
        createWebServiceAppTechs();
    }
    
    private ApplicationType createApptype(String id, String name) {
        ApplicationType web = new ApplicationType();
        web.setId(id);
        web.setName(name);
        web.setSystem(true);
        return web;
    }

    private Technology createTechnology(String id, String name, String[] versions) throws PhrescoException {
        Technology technology = new Technology();
        technology.setId(id);
        technology.setName(name);
        technology.setSystem(true);
        technology.setVersions(Arrays.asList(versions));
        return technology;
    }

    public static void main(String[] args) throws PhrescoException {
        ApptypeGenerator generator = new ApptypeGenerator();
        generator.publish();
	}

}
