/*
 * ###
 * Phresco Service
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
package com.photon.phresco.service.api;

import java.lang.reflect.Constructor;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.data.api.PhrescoDataManager;
import com.photon.phresco.service.model.ServerConfiguration;

public class PhrescoServerFactory {

    private static final String DOCUMENT_GENERATOR_IMPL_CLASS = "com.photon.phresco.service.impl.DocumentGeneratorImpl";
    private static final String ARCHETYPE_EXECUTOR_IMPL_CLASS = "com.photon.phresco.service.impl.ArchetypeExecutorImpl";
    private static final String REPOSITORY_MANAGER_IMPL_CLASS = "com.photon.phresco.service.impl.RepositoryManagerImpl";
    private static final String LDAP_MANAGER_IMPL_CLASS 	  = "com.photon.phresco.service.impl.LDAPManagerImpl";
    private static final String PHRESCO_DATA_MANAGER_IMPL_CLASS = "com.photon.phresco.service.impl.PhrescoDataManagerImpl";
    private static final String PHRESCO_ADMIN_MANAGER_IMPL_CLASS = "com.photon.phresco.service.impl.AdminManagerImpl";
    private static final String PHRESCO_COMPONENT_MANAGER_IMPL_CLASS = "com.photon.phresco.service.data.impl.ComponentManagerImpl";


    private static final String SERVER_CONFIG_FILE = "server.config";

    private static RepositoryManager repositoryManager 	= null;
    private static LDAPManager ldapManager 				= null;
    private static ArchetypeExecutor executor 			= null;
    private static DocumentGenerator generator 			= null;
    private static ServerConfiguration serverConfig     = null;

	private static PhrescoDataManager dataManager = null;
	private static AdminManager adminManager = null;
	private static ComponentManager compenentManager = null;

    public static synchronized void initialize() throws PhrescoException {
        if (serverConfig == null) {
            serverConfig = new ServerConfiguration(SERVER_CONFIG_FILE);

            repositoryManager = (RepositoryManager) constructClass(REPOSITORY_MANAGER_IMPL_CLASS, serverConfig);
            ldapManager = (LDAPManager) constructClass(LDAP_MANAGER_IMPL_CLASS, serverConfig);
            executor = (ArchetypeExecutor) constructClass(ARCHETYPE_EXECUTOR_IMPL_CLASS, serverConfig);
            generator = (DocumentGenerator) constructClass(DOCUMENT_GENERATOR_IMPL_CLASS);
//            dataManager = (PhrescoDataManager) constructClass(PHRESCO_DATA_MANAGER_IMPL_CLASS);
          //  adminManager = (AdminManager) constructClass(PHRESCO_ADMIN_MANAGER_IMPL_CLASS);
         //   compenentManager = (ComponentManager)constructClass(PHRESCO_COMPONENT_MANAGER_IMPL_CLASS);
        }
    }

    private static Object constructClass(String className, ServerConfiguration serverConfig) throws PhrescoException {
        try {
            @SuppressWarnings("rawtypes")
			Class clazz = Class.forName(className);
            @SuppressWarnings({ "unchecked", "rawtypes" })
			Constructor constructor = clazz.getDeclaredConstructor(ServerConfiguration.class);
            return constructor.newInstance(serverConfig);
        } catch (Exception e) {
            throw new PhrescoException(e);
        }
    }

    private static Object constructClass(String className) throws PhrescoException {
        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new PhrescoException(e);
        }
    }

    public static RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    public static PhrescoDataManager getPhrescoDataManager() {
        return dataManager;
    }

    public static AdminManager getAdminManager() {
        return adminManager;
    }
    public static ComponentManager getComponentManager() {
        return compenentManager;
    }

    public static LDAPManager getLDAPManager() {
        return ldapManager;
    }

    public static ArchetypeExecutor getArchetypeExecutor() {
        return executor;
    }

    public static DocumentGenerator getDocumentGenerator() {
        return generator;
    }

    public static ArchetypeExecutor getNewArchetypeExecutor() throws PhrescoException {
        return (ArchetypeExecutor) constructClass(ARCHETYPE_EXECUTOR_IMPL_CLASS, serverConfig);
    }

    public static DocumentGenerator getNewDocumentGenerator() throws PhrescoException {
        return (DocumentGenerator) constructClass(DOCUMENT_GENERATOR_IMPL_CLASS);
    }
    
    public static ServerConfiguration getServerConfig() {
    	return serverConfig;
    }
}
