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
import java.util.List;
import java.util.Map;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.data.api.PhrescoDataManager;
import com.photon.phresco.service.model.Documents;
import com.photon.phresco.util.TechnologyTypes;

public class ApptypeGenerator {


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

//    private static final Map<String, Documents> documentMap = new HashMap<String, Documents>();
//
//    static {
//        initDocumentMap();
//    }

//    private static void initDocumentMap() {
//        documentMap.put(TechnologyTypes.PHP, createPHPDoc());
//        documentMap.put(TechnologyTypes.PHP_DRUPAL7, createDrupalDoc());
//        documentMap.put(TechnologyTypes.NODE_JS, createNodejsDoc());
//        documentMap.put(TechnologyTypes.SHAREPOINT, createSharepointDoc());
//    }



    public ApptypeGenerator() {
    }

    public void publish() throws PhrescoException {
    	PhrescoDataManager dataManager = PhrescoServerFactory.getPhrescoDataManager();
    	System.out.println(dataManager);

    	dataManager.addApplicationTypes(generateApptypes());
    }

    public List<ApplicationType> generateApptypes() throws PhrescoException {
    	List<ApplicationType> applicationTypes = new ArrayList<ApplicationType>();

        //Web Technologies
        List<Technology> webTechs = new ArrayList<Technology>();
        webTechs.add(createTechnology(TechnologyTypes.PHP, "PHP"));
        webTechs.add(createTechnology(TechnologyTypes.PHP_DRUPAL7, "Drupal7"));
        webTechs.add(createTechnology(TechnologyTypes.SHAREPOINT, "Sharepoint"));
        webTechs.add(createTechnology(TechnologyTypes.HTML5_WIDGET, "HTML5 MultiWidget"));
        webTechs.add(createTechnology(TechnologyTypes.JAVA, "Java Application"));

        ApplicationType web = createApptype("apptype-webapp", "Web Application", webTechs);
        applicationTypes.add(web);

        //Mobile Technologies
        List<Technology> mobileTechs = new ArrayList<Technology>();
        mobileTechs.add(createTechnology(TechnologyTypes.IPHONE_NATIVE, "iPhone Native"));
        mobileTechs.add(createTechnology(TechnologyTypes.IPHONE_HYBRID, "iPhone Hybrid"));
        mobileTechs.add(createTechnology(TechnologyTypes.ANDROID_NATIVE, "Android Native"));
        mobileTechs.add(createTechnology(TechnologyTypes.ANDROID_HYBRID, "Android Hybrid"));
        ApplicationType mobile = createApptype("apptype-mobile", "Mobile", mobileTechs);
        applicationTypes.add(mobile);

        //HTML5 Technologies
        List<Technology> html5Techs = new ArrayList<Technology>();
        html5Techs.add(createTechnology(TechnologyTypes.NODE_JS_WEBSERVICE, "Node JS Web Service"));
        html5Techs.add(createTechnology(TechnologyTypes.JAVA_WEBSERVICE, "Java Web Service"));
        ApplicationType html5 = createApptype("apptype-web-services", "Web Services", html5Techs);
        applicationTypes.add(html5);

    	return applicationTypes;
    }

//    private ArchetypeInfo getArcheType(String techType) throws PhrescoException {
//        return TechnologyDataGenerator.getArchetypeInfo(techType);
//    }
//
//    private static Documents createPHPDoc() {
//        Documents docs = new Documents();
//        //Arch doc
//        Document archDoc = new Document();
//        archDoc.setContent(phpTechDoc);
//        archDoc.setDocumentType(DocumentType.ARCHITECHTURE.name());
//        docs.getDocument().add(archDoc);
//
//        //design doc
//        Document guildLinesDoc = new Document();
//        guildLinesDoc.setUrl("http://pear.php.net/manual/en/standards.bestpractices.php");
//        guildLinesDoc.setDocumentType(DocumentType.CODING_GUIDELINES.name());
//        docs.getDocument().add(guildLinesDoc);
//
//        return docs;
//    }
//
//    public static Documents createDrupalDoc() {
//        Documents docs = new Documents();
//        //Arch doc
//        Document archDoc = new Document();
//        archDoc.setContent(DrupalTechDoc);
//        archDoc.setDocumentType(DocumentType.ARCHITECHTURE.name());
//        docs.getDocument().add(archDoc);
//
//        //design doc
//        Document guildLinesDoc = new Document();
//        guildLinesDoc.setUrl("http://pear.php.net/manual/en/standards.bestpractices.php");
//        guildLinesDoc.setDocumentType(DocumentType.CODING_GUIDELINES.name());
//        docs.getDocument().add(guildLinesDoc);
//
//        return docs;
//    }
//
//    private static Documents createNodejsDoc() {
//        Documents docs = new Documents();
//        //Arch doc
//        Document archDoc = new Document();
//        archDoc.setContent(nodejsDoc);
//        archDoc.setDocumentType(DocumentType.ARCHITECHTURE.name());
//        docs.getDocument().add(archDoc);
//
//        //design doc
//        Document guildLinesDoc = new Document();
//        guildLinesDoc.setUrl("http://pear.php.net/manual/en/standards.bestpractices.php");
//        guildLinesDoc.setDocumentType(DocumentType.CODING_GUIDELINES.name());
//        docs.getDocument().add(guildLinesDoc);
//
//        return docs;
//    }
//
//    private static Documents createSharepointDoc() {
//        Documents docs = new Documents();
//        //Arch doc
//        Document archDoc = new Document();
//        archDoc.setContent(SharepointDoc);
//        archDoc.setDocumentType(DocumentType.ARCHITECHTURE.name());
//        docs.getDocument().add(archDoc);
//
//        //design doc
//        Document guildLinesDoc = new Document();
//        guildLinesDoc.setUrl("http://pear.php.net/manual/en/standards.bestpractices.php");
//        guildLinesDoc.setDocumentType(DocumentType.CODING_GUIDELINES.name());
//        docs.getDocument().add(guildLinesDoc);
//
//        return docs;
//    }

    private ApplicationType createApptype(String id, String name, List<Technology> webTechs) {
        ApplicationType web = new ApplicationType();
        web.setId(id);
        web.setName(name);
        web.getTechnologies().addAll(webTechs);
        return web;
    }

    private Technology createTechnology(String id, String name) throws PhrescoException {
        Technology technology = new Technology();
        technology.setId(id);
        technology.setName(name);
//        technology.setArchetypeInfo(getArcheType(id));
//        technology.setDocuments(getDocument(id));
        return technology;
    }
//
//    private Documents getDocument(String id) {
//        return documentMap.get(id);
//    }

    public static void main(String[] args) throws PhrescoException {
    	PhrescoServerFactory.initialize();
    	new ApptypeGenerator().publish();
	}

}
