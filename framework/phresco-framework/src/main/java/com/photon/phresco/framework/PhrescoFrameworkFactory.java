package com.photon.phresco.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.api.ProjectRuntimeManager;
import com.photon.phresco.framework.api.ServiceManager;
import com.photon.phresco.framework.api.UpdateManager;
import com.photon.phresco.framework.api.Validator;
import com.photon.phresco.framework.validators.AndroidValidator;
import com.photon.phresco.framework.validators.ArchetypeValidator;
import com.photon.phresco.framework.validators.DrupalValidator;
import com.photon.phresco.framework.validators.EnvironmentValidator;
import com.photon.phresco.framework.validators.NodejsValidator;
import com.photon.phresco.framework.validators.PhpValidator;
import com.photon.phresco.util.TechnologyTypes;

public class PhrescoFrameworkFactory {

    private PhrescoFrameworkFactory(){
        //restrict initialization
    }

    //TODO:Can we read this from a property file?
    private static final String PROJECT_ADMINISTRATOR_IMPL = "com.photon.phresco.framework.impl.ProjectAdministratorImpl";
    private static final String PROJECT_RUNTIME_MANAGER_IMPL = "com.photon.phresco.framework.impl.ProjectRuntimeManagerImpl";
    private static final String SERVICE_MANAGER_IMPL = "com.photon.phresco.framework.impl.ServiceManagerImpl";
    private static final String CI_MANAGER_IMPL = "com.photon.phresco.framework.impl.CIManagerImpl";
    private static final String UPDATE_MANAGER_IMPL = "com.photon.phresco.framework.impl.UpdateManagerImpl";

    private static FrameworkConfiguration frameworkConfig = null;
    private static ProjectAdministrator administrator = null;
    private static ProjectRuntimeManager runtimeManager = null;
    private static ServiceManager serviceManager = null;
    private static CIManager ciManager = null;
    private static UpdateManager updateManager = null;

    private static final String FRAMEWORK_CONFIG = "framework.config";
    private static final Map<String, List<Validator>> VALIDATORSMAP = new HashMap<String, List<Validator>>(32);

    public static FrameworkConfiguration getFrameworkConfig() throws PhrescoException {
        if(frameworkConfig == null) {
            frameworkConfig = new FrameworkConfiguration(FRAMEWORK_CONFIG);
        }

        return frameworkConfig;
    }

    public static  ProjectAdministrator getProjectAdministrator() throws PhrescoException {
        if (administrator == null) {
            administrator = (ProjectAdministrator) constructClass(PROJECT_ADMINISTRATOR_IMPL);
        }

        return administrator;
    }

    private static Object constructClass(String className) throws PhrescoException {
        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new PhrescoException(e);
        } catch (InstantiationException e) {
            throw new PhrescoException(e);
        } catch (IllegalAccessException e) {
            throw new PhrescoException(e);
        }
    }

    public static ProjectRuntimeManager getProjectRuntimeManager() throws PhrescoException {
        if (runtimeManager == null) {
            runtimeManager = (ProjectRuntimeManager) constructClass(PROJECT_RUNTIME_MANAGER_IMPL);
        }

        return runtimeManager;

    }

    public static ServiceManager getServiceManager() throws PhrescoException {
        if (serviceManager == null) {
            serviceManager = (ServiceManager) constructClass(SERVICE_MANAGER_IMPL);
        }

        return serviceManager;
    }

    public static CIManager getCIManager() throws PhrescoException {
        if (ciManager == null) {
            ciManager = (CIManager) constructClass(CI_MANAGER_IMPL);
        }

        return ciManager;
    }

    public static UpdateManager getUpdateManager() throws PhrescoException {
        if (updateManager == null) {
        	updateManager = (UpdateManager) constructClass(UPDATE_MANAGER_IMPL);
        }

        return updateManager;
    }

    public static List<Validator> getValidators(String techId) throws PhrescoException {
    	if (VALIDATORSMAP.isEmpty()) {
    		initValidators();
    	}

    	//TODO:
//    	if (action == null) {
//    		return allValidators;
//    	}

    	//TODO:Use the incoming tech id
    	return VALIDATORSMAP.get(techId);
    }

    //TODO:This initialization should happen dynamically from the service
    private static  void initValidators() throws PhrescoException  {
		EnvironmentValidator envValidator = new EnvironmentValidator();

		List<Validator> allValidators = new ArrayList<Validator>(8);
		allValidators.add(envValidator);
		
		VALIDATORSMAP.put(TechnologyTypes.ALL_TECHS, allValidators);
		
		//Validators for PHP Technology
		PhpValidator phpValidator = new PhpValidator();
		ArchetypeValidator archetypeValidator = new ArchetypeValidator();
		List<Validator> phpValidators = new ArrayList<Validator>(8);
		phpValidators.add(phpValidator);
		phpValidators.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.PHP, phpValidators);
		
		//Validators for DRUPAL Technology
		DrupalValidator drupalValidator = new DrupalValidator();
		List<Validator> drupalValidators = new ArrayList<Validator>(8);
		List<Validator> drupal6Validators = new ArrayList<Validator>(8);
		drupalValidators.add(drupalValidator);
		drupalValidators.add(archetypeValidator);
		drupal6Validators.add(envValidator);
		VALIDATORSMAP.put(TechnologyTypes.PHP_DRUPAL7, drupalValidators);
		VALIDATORSMAP.put(TechnologyTypes.PHP_DRUPAL6, drupalValidators);
		
		//Validators for Android Native and Android Hybrid Technology
		AndroidValidator androidValidator = new  AndroidValidator(); 
		List<Validator> androidValidators = new ArrayList<Validator>(8);
		androidValidators.add(androidValidator);
		androidValidators.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.ANDROID_NATIVE, androidValidators);
		VALIDATORSMAP.put(TechnologyTypes.ANDROID_HYBRID, androidValidators);
		
		//Validators for NodejsValidator Technology
		NodejsValidator nodeJSValidator = new  NodejsValidator(); 
		List<Validator> nodeJSValidators = new ArrayList<Validator>(8);
		nodeJSValidators.add(nodeJSValidator);
		nodeJSValidators.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.NODE_JS_WEBSERVICE, nodeJSValidators);

		//Validators for SHAREPOINT Technology
		List<Validator> sharPointValidators = new ArrayList<Validator>(8);
		sharPointValidators.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.SHAREPOINT, sharPointValidators);
		
		//Validators for DOTNETTechnology
		List<Validator> dotNetValidators = new ArrayList<Validator>(8);
		dotNetValidators.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.DOT_NET, dotNetValidators);
		
		//Validators for JAVA WEB SERVICE Technology
		List<Validator> javaWebService = new ArrayList<Validator>(8);
		javaWebService.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.JAVA_WEBSERVICE, javaWebService);
		
		//Validators for JAVA  Technology
		List<Validator> java = new ArrayList<Validator>(8);
		java.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.JAVA, java);
		
		//Validators for JAVA STAND ALONE  Technology
		List<Validator> javaStandAlone = new ArrayList<Validator>(8);
		javaStandAlone.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.JAVA_STANDALONE, javaStandAlone);
		
		//Validators for HTML5 Technology
		List<Validator> html5 = new ArrayList<Validator>(8);
		html5.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.HTML5, html5);
		
		//Validators for HTML5 Technology
		List<Validator> html5MobileWidget = new ArrayList<Validator>(8);
		html5MobileWidget.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.HTML5_MOBILE_WIDGET, html5MobileWidget);
		
		//Validators for HTML5_MULTICHANNEL_JQUERY_WIDGET Technology
		List<Validator> html5MultichannelWiddget = new ArrayList<Validator>(8);
		html5MultichannelWiddget.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, html5MultichannelWiddget);

		//Validators for HTML5_WIDGET Technology
		List<Validator> html5Widdget = new ArrayList<Validator>(8);
		html5Widdget.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.HTML5_WIDGET, html5Widdget);
		
		//Validators for IphoneHybrid Technology
		List<Validator> IphoneHybrid = new ArrayList<Validator>(8);
		IphoneHybrid.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.IPHONE_HYBRID, IphoneHybrid);
		
		//Validators for IphoneNative Technology
		List<Validator> IphoneNative = new ArrayList<Validator>(8);
		IphoneNative.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.IPHONE_NATIVE, IphoneNative);
		
		//Validators for WordPress Technology
		List<Validator> wordPressValidators = new ArrayList<Validator>(8);
		wordPressValidators.add(archetypeValidator);
		VALIDATORSMAP.put(TechnologyTypes.WORDPRESS, wordPressValidators);
    }    

}