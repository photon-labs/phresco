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
