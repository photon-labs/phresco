package com.photon.phresco.service.data.db;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class DashboardDBService {


    private static final String QUERY_FIND_APPLICATION_TYPES = "service.getApplicationTypes";
    private HibernateTemplate template;
    public void setSessionFactory(SessionFactory sessionFactory) {
        template = new HibernateTemplate(sessionFactory);
    }
}
