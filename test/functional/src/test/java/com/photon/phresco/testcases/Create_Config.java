
 /* Author by {phresco} QA Automation Team*/
 
package com.photon.phresco.testcases;

import java.io.IOException;
import org.junit.Test;
import com.photon.phresco.Screens.AddApplicationScreen;
import com.photon.phresco.Screens.ApplicationsScreen;
import com.photon.phresco.Screens.ConfigScreen;
import com.photon.phresco.Screens.LoginScreen;
import com.photon.phresco.Screens.PhrescoWelcomePage;
import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.preconditions.CreateDbsql;
import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
import com.photon.phresco.uiconstants.JavaWebServConstantsXml;
import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
import com.photon.phresco.uiconstants.NodeJSConstantsXml;
import com.photon.phresco.uiconstants.PhpConstantsXml;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.SharepointConstantsXml;
import com.photon.phresco.uiconstants.YuiConstantsXml;
import com.photon.phresco.uiconstants.iPhoneConstantsXml;


import junit.framework.TestCase;

public class Create_Config extends TestCase{
	
	
	private PhrescoUiConstantsXml phrsc;
	private Drupal7ConstantsXml drupal;
	private MobWidgetConstantsXml mobwidg;
	private iPhoneConstantsXml iPhone;
	private JavaWebServConstantsXml jws;
	private PhpConstantsXml phpconst;
	private SharepointConstantsXml spconst;
	private NodeJSConstantsXml nodejsconst;
	private YuiConstantsXml YuiConst;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
    
    
    @Test
    public void testCreate_Drupal() throws InterruptedException, IOException, Exception{
    	    		
    		   drupal=new Drupal7ConstantsXml();
    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
    					+ phrsc.PORT + "/";
    			browserAppends = "*" + phrsc.BROWSER;
    			assertNotNull("Browser name should not be null", browserAppends);
    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
    			assertNotNull("selenium-port number should not be null",
    					SELENIUM_PORT);
    			wel = new WelcomeScreen(phrsc.HOST,
    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
    					phrsc.CONTEXT);
    			assertNotNull(wel);
    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
    			loginObject = new LoginScreen(phrsc);
    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
    			                   phrescoHome.goToPhrescoHomePage(methodName);
			    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				//AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
			    CreateDbsql dbsql = new CreateDbsql(methodName);
				ConfigScreen config = new ConfigScreen();
				config.DrupalDatabaseConfig(drupal,methodName);
				config.DrupalServerConfig(drupal,methodName);
				
    }
  /* @Test
    public void testCreate_MobWidget() throws InterruptedException, IOException, Exception{
			    	    		
                mobwidg=new MobWidgetConstantsXml();
	    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
	    					+ phrsc.PORT + "/";
	    			browserAppends = "*" + phrsc.BROWSER;
	    			assertNotNull("Browser name should not be null", browserAppends);
	    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
	    			assertNotNull("selenium-port number should not be null",
	    					SELENIUM_PORT);
	    			wel = new WelcomeScreen(phrsc.HOST,
	    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
	    					phrsc.CONTEXT);
	    			assertNotNull(wel);
				loginObject = new LoginScreen(phrsc);
				PhrescoWelcomePage phrescoHome=loginObject.testLoginPage();
								   phrescoHome.goToPhrescoHomePage();
			    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab();
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();			
				addappscrn.createProjHTML5MobileWidget(mobwidg);
				ConfigScreen config = addappscrn.ConfigScreen();
				config.MobilewidgetServerConfig(mobwidg);
				config.HTML5WidgetWebServiceConfig(YuiConst);
    }*/
    /*@Test
    public void testCreate_iPhone() throws InterruptedException, IOException, Exception{
    	    		
			iPhone = new iPhoneConstantsXml();
			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
					+ phrsc.PORT + "/";
			browserAppends = "*" + phrsc.BROWSER;
			assertNotNull("Browser name should not be null", browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
			assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
			wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
					serverURL, phrsc.SPEED, phrsc.CONTEXT);
			assertNotNull(wel);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome = loginObject.testLoginPage();
							   phrescoHome.goToPhrescoHomePage();
			ApplicationsScreen applicationScr = phrescoHome.clickOnApplicationsTab();
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
			addappscrn.createProjiPhone(iPhone);
    }*/
    /*@Test
    public void testCreate_iPhoneHybd() throws InterruptedException, IOException, Exception{
    	   		
    		iPhone=new iPhoneConstantsXml();
    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
    					+ phrsc.PORT + "/";
    			browserAppends = "*" + phrsc.BROWSER;
    			assertNotNull("Browser name should not be null", browserAppends);
    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
    			assertNotNull("selenium-port number should not be null",
    					SELENIUM_PORT);
    			wel = new WelcomeScreen(phrsc.HOST,
    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
    					phrsc.CONTEXT);
    			assertNotNull(wel);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage();
			                   phrescoHome.goToPhrescoHomePage();
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab();
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
			addappscrn.createProjiPhoneHybrid(iPhone);
			ConfigScreen config = addappscrn.ConfigScreen();
			
			
			
    }*/
   /* @Test
    public void testCreate_JWS() throws InterruptedException, IOException, Exception{
    	    		
    		jws=new JavaWebServConstantsXml();
    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
    					+ phrsc.PORT + "/";
    			browserAppends = "*" + phrsc.BROWSER;
    			assertNotNull("Browser name should not be null", browserAppends);
    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
    			assertNotNull("selenium-port number should not be null",
    					SELENIUM_PORT);
    			wel = new WelcomeScreen(phrsc.HOST,
    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
    					phrsc.CONTEXT);
    			assertNotNull(wel);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage();
							   phrescoHome.goToPhrescoHomePage();
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab();
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
			addappscrn.createProjJavaWebService(jws);
			ConfigScreen config = addappscrn.ConfigScreen();
			config.JavaWebServiceDatabaseConfig(jws);
			config.JavaWebServiceServerConfig(jws);
    }
    @Test
    public void testCreate_PHP() throws InterruptedException, IOException, Exception{
    	   		
    	     phpconst=new PhpConstantsXml();
    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
    					+ phrsc.PORT + "/";
    			browserAppends = "*" + phrsc.BROWSER;
    			assertNotNull("Browser name should not be null", browserAppends);
    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
    			assertNotNull("selenium-port number should not be null",
    					SELENIUM_PORT);
    			wel = new WelcomeScreen(phrsc.HOST,
    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
    					phrsc.CONTEXT);
    			assertNotNull(wel);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage();
							   phrescoHome.goToPhrescoHomePage();
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab();
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
			addappscrn.createProjPHP(phpconst);
			CreateDbsql dbsql = new CreateDbsql(methodName);
			ConfigScreen config = addappscrn.ConfigScreen();
			config.PhpDatabaseConfig(phpconst);
			config.PHPServerConfig(phpconst);
    }
    @Test
    public void testCreate_Share() throws InterruptedException, IOException, Exception{
    	    		
    	      spconst=new SharepointConstantsXml();
    			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
    					+ phrsc.PORT + "/";
    			browserAppends = "*" + phrsc.BROWSER;
    			assertNotNull("Browser name should not be null", browserAppends);
    			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
    			assertNotNull("selenium-port number should not be null",
    					SELENIUM_PORT);
    			wel = new WelcomeScreen(phrsc.HOST,
    					SELENIUM_PORT, browserAppends, serverURL, phrsc.SPEED,
    					phrsc.CONTEXT);
    			assertNotNull(wel);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage();
			                   phrescoHome.goToPhrescoHomePage();
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab();
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
			addappscrn.createProjSharepoint(spconst);
			ConfigScreen config = addappscrn.ConfigScreen();
			config.SharepointServerConfig(spconst);
    }
    @Test
    public void testCreate_NodeJS() throws InterruptedException, IOException, Exception{
    	    		
			nodejsconst = new NodeJSConstantsXml();
			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
					+ phrsc.PORT + "/";
			browserAppends = "*" + phrsc.BROWSER;
			assertNotNull("Browser name should not be null", browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
			assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
			wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
					serverURL, phrsc.SPEED, phrsc.CONTEXT);
			assertNotNull(wel);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome = loginObject.testLoginPage();
							   phrescoHome.goToPhrescoHomePage();
			ApplicationsScreen applicationScr = phrescoHome.clickOnApplicationsTab();
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
			addappscrn.createProjNodeJS(nodejsconst);
			CreateDbsql dbsql = new CreateDbsql(methodName);
			ConfigScreen config = addappscrn.ConfigScreen();
			config.NodeJsDatabaseConfig();
			config.NodeJsServerConfig();
    }
    
    @Test
    public void testCreate_YuiWidget_Proj() throws InterruptedException, IOException, Exception{
    	    		
    	YuiConst = new YuiConstantsXml();
			
			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
					+ phrsc.PORT + "/";
			browserAppends = "*" + phrsc.BROWSER;
			assertNotNull("Browser name should not be null", browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
			assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
			wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
					serverURL, phrsc.SPEED, phrsc.CONTEXT);
			assertNotNull(wel);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome = loginObject.testLoginPage();
							   phrescoHome.goToPhrescoHomePage();
			ApplicationsScreen applicationScr = phrescoHome.clickOnApplicationsTab();
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen();
			addappscrn.createProjHTML5Widg(YuiConst);
			ConfigScreen config = addappscrn.ConfigScreen();
			config.HTML5WidgetServerConfig();
			config.HTML5WidgetWebServiceConfig();
    }		
     
*/
	public void setUp() throws Exception {
		phrsc = new PhrescoUiConstantsXml();
	}

	public void tearDown() {
		clean();
	}

	private void clean() {
		wel.closeBrowser();
	}

}

