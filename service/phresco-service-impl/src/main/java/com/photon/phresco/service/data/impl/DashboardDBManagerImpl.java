package com.photon.phresco.service.data.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.photon.phresco.service.api.DashboardManager;
import com.photon.phresco.service.data.db.DashboardDBService;

public class DashboardDBManagerImpl implements DashboardManager {

    private DashboardDBService dashboardDBService;
    public DashboardDBManagerImpl() {
        dashboardDBService = findBean("phresco-dashboard-service");
    }
    /**
     * Returns the ComponentDBService
     * @return
     */
    public static DashboardDBService findBean(String beanName) {
        System.out.println("Finding Bean "  + beanName);
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring-hibernate.xml");
        System.out.println("Found Bean");
        return (DashboardDBService) context.getBean(beanName);
    }
}
