package com.photon.phresco.service.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Database;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.TechnologyTypes;

public class DatabaseInfoGenerator implements ServiceConstants {
    
    public ServiceContext context = null;
    public ServiceManager serviceManager = null;
    
    public DatabaseInfoGenerator() throws PhrescoException {
        context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest/api");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
        serviceManager = ServiceClientFactory.getServiceManager(context);
    }
    
    private List<Database> createDatabases() {
        List<Database> databases = new ArrayList<Database>();
        
        List<String> versions = new ArrayList<String>(2);
        versions.add("5.5.1");
        versions.add("5.5");
        versions.add("5.1");
        versions.add("5.0");
        versions.add("4.1");
        versions.add("4.0");
        databases.add(new Database("MySQL", versions, "My SQL DB",createTech()));
        
        versions = new ArrayList<String>(2);
        versions.add("11gR2");
        versions.add("11gR1");
        versions.add("10gR2");
        versions.add("10gR1");
        versions.add("9iR2");
        versions.add("9iR1");
        versions.add("8iR3");
        versions.add("8iR2");
        versions.add("8iR1");
        versions.add("8i");
        databases.add(new Database("Oracle", versions, "Oracle DB",createTech()));

        versions = new ArrayList<String>(2);
        versions.add("2.0.4");
        versions.add("1.8.5 ");
        databases.add(new Database("MongoDB", versions, "Mongo DB", createTech()));
        
        versions = new ArrayList<String>(2);
        versions.add("10");
        versions.add("9.7");
        versions.add("9.5");
        versions.add("9");
        databases.add(new Database("DB2", versions, "DB2 DB", createTech()));
        
        versions = new ArrayList<String>(2);
        versions.add("2012");
        versions.add("2008 R2");
        versions.add("2008");
        versions.add("2005");
        databases.add(new Database("MSSQL", versions, "MSSQL DB", createTech()));
        
        return databases;
    }

    private List<String> createTech() {
        String[] techs = new String[]{TechnologyTypes.PHP, TechnologyTypes.PHP_DRUPAL6, 
                TechnologyTypes.PHP_DRUPAL7, TechnologyTypes.JAVA_WEBSERVICE,
                TechnologyTypes.HTML5_MOBILE_WIDGET, TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, 
                TechnologyTypes.HTML5_WIDGET, TechnologyTypes.WORDPRESS, TechnologyTypes.NODE_JS_WEBSERVICE};
        return Arrays.asList(techs);
    }
    
    private void generate() throws PhrescoException {
        List<Database> databases = createDatabases();
        RestClient<Database> restClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_DATABASES);
        restClient.create(databases);
    }
    
    public static void main(String[] args) throws PhrescoException {
        DatabaseInfoGenerator generator = new DatabaseInfoGenerator();
        generator.generate();
    }
}
