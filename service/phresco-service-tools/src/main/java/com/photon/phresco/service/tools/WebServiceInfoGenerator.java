package com.photon.phresco.service.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.TechnologyTypes;
import com.sun.jersey.api.client.ClientResponse;

public class WebServiceInfoGenerator implements ServiceConstants {
    
    public ServiceContext context = null;
    public ServiceManager serviceManager = null;
    
    public WebServiceInfoGenerator() throws PhrescoException {
        context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest/api");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
        serviceManager = ServiceClientFactory.getServiceManager(context);
    }
    
    private List<WebService> createWebServices() {
        List<WebService> databases = new ArrayList<WebService>();
        databases.add(new WebService("REST/JSON", "1.0", "REST JSON web services", createTechs()));
        databases.add(new WebService("REST/XML", "1.0", "REST XML web services",createTechs()));
        databases.add(new WebService("SOAP", "1.1", "SOAP 1.1", createTechs()));
        databases.add(new WebService("SOAP", "1.2", "SOAP 1.2",createTechs()));
        return databases;
    }

    private List<String> createTechs() {
        String[] techs = new String[]{TechnologyTypes.ANDROID_NATIVE,TechnologyTypes.ANDROID_HYBRID,
                TechnologyTypes.HTML5_MOBILE_WIDGET,TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET,
                TechnologyTypes.HTML5_WIDGET,TechnologyTypes.IPHONE_HYBRID,TechnologyTypes.IPHONE_NATIVE};
        return Arrays.asList(techs);
    }
    
    private void generate() throws PhrescoException {
       List<WebService> webServices = createWebServices();
       RestClient<WebService> applicationTypeClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICES);
       ClientResponse response = applicationTypeClient.create(webServices);
       System.out.println("Response Is" + response.getStatus());
    }
    
    public static void main(String[] args) throws PhrescoException {
        WebServiceInfoGenerator generator = new WebServiceInfoGenerator();
        generator.generate();
    }
}
