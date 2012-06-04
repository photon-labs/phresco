/*
 * ###
 * Phresco Service Implemenation
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
package com.photon.phresco.service.data.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.photon.phresco.service.data.api.AdminDBManager;
import com.photon.phresco.service.data.db.AdminDBService;
public class AdminDBManagerImpl implements AdminDBManager {
    private AdminDBService admindbService;
    public AdminDBManagerImpl() {
        admindbService = findBean("phresco-admin-db-service");
    }
    /**
     * Returns the AdminDBService
     * @return
     */
    public static AdminDBService findBean(String beanName) {
        System.out.println("Finding Bean "  + beanName);
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring-hibernate.xml");
        System.out.println("Found Bean");
        return (AdminDBService) context.getBean(beanName);
    }


}
