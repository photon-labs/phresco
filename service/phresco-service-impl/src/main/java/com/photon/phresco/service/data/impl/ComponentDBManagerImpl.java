package com.photon.phresco.service.data.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.photon.phresco.service.data.api.ComponentDBManager;
import com.photon.phresco.service.data.db.ComponentDBService;

public class ComponentDBManagerImpl implements ComponentDBManager {

    private ComponentDBService componentDBService;
    public ComponentDBManagerImpl() {
        componentDBService = findBean("phresco-component-db-service");
    }
    /**
     * Returns the ComponentDBService
     * @return
     */
    public static ComponentDBService findBean(String beanName) {
        System.out.println("Finding Bean "  + beanName);
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring-hibernate.xml");
        System.out.println("Found Bean");
        return (ComponentDBService) context.getBean(beanName);
    }


}
