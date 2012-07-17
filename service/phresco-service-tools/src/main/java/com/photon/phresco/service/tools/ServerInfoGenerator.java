package com.photon.phresco.service.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Server;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.TechnologyTypes;
import com.sun.jersey.api.client.ClientResponse;

public class ServerInfoGenerator implements ServiceConstants {
    
    public ServiceContext context = null;
    public ServiceManager serviceManager = null;
    
    public ServerInfoGenerator() throws PhrescoException {
        context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest/api");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
        serviceManager = ServiceClientFactory.getServiceManager(context);
    }
    
    private List<Server> createServer() {
        List<Server> servers = new ArrayList<Server>();
        Server server;
        List<String> versions = new ArrayList<String>(2);
        versions.add("7.0.x");
        versions.add("6.0.x");
        versions.add("5.5.x");
        server = new Server("Apache Tomcat", versions, "Apache Tomcat Server");
        server.setTechnologies(createTomcatTech());
        servers.add(server);
        versions = new ArrayList<String>(2);
        versions.add("7.1.x");
        versions.add("7.0.x");
        versions.add("6.1.x");
        versions.add("6.0.x");
        versions.add("5.1.x");
        versions.add("5.0.x");
        versions.add("4.2.x");
        versions.add("4.0.x");
        server = new Server("JBoss", versions, "JBoss application server");
        server.setTechnologies(createTomcatTech());
        servers.add(server);

        versions = new ArrayList<String>(2);
        versions.add("7.5");
        versions.add("7.0");
        versions.add("6.0");
        versions.add("5.1");
        versions.add("5.0");
        server = new Server("IIS", versions, "IIS Server");
        server.setTechnologies(createTomcatTech());
        servers.add(server);

        versions = new ArrayList<String>(2);
        versions.add("12c(12.1.1)");
        versions.add("11gR1(10.3.6)");
        versions.add("11g(10.3.1)");
        versions.add("10.3");
        versions.add("10.0");
        versions.add("9.2");
        versions.add("9.1");
        server = new Server("WebLogic", versions, "Web Logic");
        server.setTechnologies(createTomcatTech());
        servers.add(server);
        
        versions = new ArrayList<String>(2);
        versions.add("2.3");
        versions.add("2.2");
        versions.add("2.0");
        versions.add("1.3");
        server = new Server("Apache", versions, "Apache");
        server.setTechnologies(createTomcatTech());
        servers.add(server);
        
        versions = new ArrayList<String>(2);
        versions.add("0.6.x");
        versions.add("0.7.x");
        server = new Server("NodeJS", versions, "NodeJS");
        server.setTechnologies(createNodejsTech());
        servers.add(server);
        
        versions = new ArrayList<String>(2);
        versions.add("8.x");
        versions.add("7.x");
        versions.add("6.x");
        versions.add("5.x");
        versions.add("4.x");
        server = new Server("Jetty", versions, "Jetty");
        server.setTechnologies(createTomcatTech());
        servers.add(server);
        return servers;
    }

    private static List<String> createNodejsTech() {
        String[] techs = new String[]{TechnologyTypes.NODE_JS_WEBSERVICE};
        return Arrays.asList(techs);
    }

    private static List<String> createTomcatTech() {
        String[] techs = new String[]{TechnologyTypes.JAVA_WEBSERVICE, TechnologyTypes.HTML5_MOBILE_WIDGET,
                TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET,TechnologyTypes.HTML5_WIDGET};
        return Arrays.asList(techs);
    }
    
    private void generate() throws PhrescoException {
        List<Server> servers = createServer();
        RestClient<Server> restClient = serviceManager.getRestClient(REST_API_COMPONENT + REST_API_SERVERS);
        ClientResponse response = restClient.create(servers);
        System.out.println("Response Is" + response.getStatus());
    }
    
    public static void main(String[] args) throws PhrescoException {
        ServerInfoGenerator generator = new ServerInfoGenerator();
        generator.generate();
    }
}
