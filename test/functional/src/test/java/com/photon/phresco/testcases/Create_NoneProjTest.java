/*
 * Author by {phresco} QA Automation Team
 */
package com.photon.phresco.testcases;

import java.io.IOException;
import org.junit.Test;
import com.photon.phresco.Screens.AddApplicationScreen;
import com.photon.phresco.Screens.ApplicationsScreen;
import com.photon.phresco.Screens.LoginScreen;
import com.photon.phresco.Screens.PhrescoWelcomePage;
import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.AndroidNativeConstants;
import com.photon.phresco.uiconstants.DotNetConstants;
import com.photon.phresco.uiconstants.Drupal6ConstantsXml;
import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
import com.photon.phresco.uiconstants.JavaWebServConstantsXml;
import com.photon.phresco.uiconstants.JqueryWidgetConstants;
import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
import com.photon.phresco.uiconstants.NodeJSConstantsXml;
import com.photon.phresco.uiconstants.PhpConstantsXml;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.SharepointConstantsXml;
import com.photon.phresco.uiconstants.WordPressConstants;
import com.photon.phresco.uiconstants.YuiConstantsXml;
import com.photon.phresco.uiconstants.iPhoneConstantsXml;


import junit.framework.TestCase;

public class Create_NoneProjTest extends TestCase{
	
	
	private PhrescoUiConstantsXml phrsc;
	private Drupal7ConstantsXml drupal;
	private MobWidgetConstantsXml mobwidg;
	private JqueryWidgetConstants jquerywidg;
	private DotNetConstants DotNetConst;
	private WordPressConstants WordpressConst;
	private iPhoneConstantsXml iPhone;
	private AndroidNativeConstants androidNat;
	private AndroidHybridConstants androidHyb;
	private JavaWebServConstantsXml jws;
	private PhpConstantsXml phpconst;
	private SharepointConstantsXml spconst;
	private NodeJSConstantsXml nodejsconst;
	private Drupal6ConstantsXml drupal6;
	private YuiConstantsXml YuiConst;
	private int SELENIUM_PORT;
	private WelcomeScreen wel;
	private String browserAppends;
	private LoginScreen loginObject;
	String methodName;
	
    
    
    @Test
    public void testCreate_Drupal7None() throws InterruptedException, IOException, Exception{
    	    		
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
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
				addappscrn.createProjDRUPAL7None(drupal,methodName);
    }
    @Test
    public void testCreate_Drupal6None() throws InterruptedException, IOException, Exception{
    	    		
    		   drupal6=new Drupal6ConstantsXml();
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
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
				addappscrn.createProjDRUPAL6(drupal6,methodName);
    }
   @Test
    public void testCreate_MobWidgetNone() throws InterruptedException, IOException, Exception{
			    	    		
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
	    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
					System.out.println("methodName = " + methodName);
	    			loginObject = new LoginScreen(phrsc);
	    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
	    			                   phrescoHome.goToPhrescoHomePage(methodName);
				    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
				addappscrn.createProjHTML5MobileWidgetNone(mobwidg,methodName);
    }
   /*@Test
   public void testCreate_JqueryWidgetNone() throws InterruptedException, IOException, Exception{
			    	    		
	               jquerywidg=new JqueryWidgetConstants();
               
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
				AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);			
				addappscrn.createProjJqueryWidgetNone(jquerywidg,methodName);
   }*/
    @Test
    public void testCreate_iPhoneNativeNone() throws InterruptedException, IOException, Exception{
    	    		
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
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
			                   phrescoHome.goToPhrescoHomePage(methodName);
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjiPhoneNativeNone(iPhone,methodName);
    }
    @Test
    public void testCreate_iPhoneHybdNone() throws InterruptedException, IOException, Exception{
    	   		
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
    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
    			loginObject = new LoginScreen(phrsc);
    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
    			                   phrescoHome.goToPhrescoHomePage(methodName);
			    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjiPhoneHybridNone(iPhone,methodName);
    }
		@Test
		public void testCreate_AndroidNativeNone() throws InterruptedException,
				IOException, Exception {
	
			androidNat = new AndroidNativeConstants();
			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
					+ phrsc.PORT + "/";
			browserAppends = "*" + phrsc.BROWSER;
			assertNotNull("Browser name should not be null", browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
			assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
			wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
					serverURL, phrsc.SPEED, phrsc.CONTEXT);
			assertNotNull(wel);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
			                   phrescoHome.goToPhrescoHomePage(methodName);
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr
					.gotoAddApplicationScreen(methodName);
			addappscrn.createProjAndroidNativeNone(androidNat,methodName);
		}
	
		@Test
		public void testCreate_AndroidHybridNone() throws InterruptedException,
				IOException, Exception {
	
			androidHyb = new AndroidHybridConstants();
			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
					+ phrsc.PORT + "/";
			browserAppends = "*" + phrsc.BROWSER;
			assertNotNull("Browser name should not be null", browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
			assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
			wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
					serverURL, phrsc.SPEED, phrsc.CONTEXT);
			assertNotNull(wel);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
			                   phrescoHome.goToPhrescoHomePage(methodName);
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr
					.gotoAddApplicationScreen(methodName);
			addappscrn.createProjAndroidHybridNone(androidHyb,methodName);
		}
    @Test
    public void testCreate_JWSNone() throws InterruptedException, IOException, Exception{
    	    		
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
    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
    			loginObject = new LoginScreen(phrsc);
    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
    			                   phrescoHome.goToPhrescoHomePage(methodName);
			    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjJavaWebServiceNone(jws,methodName);
    }
    @Test
    public void testCreate_PHPNone() throws InterruptedException, IOException, Exception{
    	   		
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
    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
    			loginObject = new LoginScreen(phrsc);
    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
    			                   phrescoHome.goToPhrescoHomePage(methodName);
			    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjPHPNone(phpconst,methodName);
    }
    @Test
    public void testCreate_ShareNone() throws InterruptedException, IOException, Exception{
    	    		
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
    			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				System.out.println("methodName = " + methodName);
    			loginObject = new LoginScreen(phrsc);
    			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
    			                   phrescoHome.goToPhrescoHomePage(methodName);
			    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjSharepointNone(spconst,methodName);
    }
    @Test
    public void testCreate_NodeJSNone() throws InterruptedException, IOException, Exception{
    	    		
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
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
			                   phrescoHome.goToPhrescoHomePage(methodName);
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjNodeJSNone(nodejsconst,methodName);
    }
    
    @Test
    public void testCreate_YuiWidget_ProjNone() throws InterruptedException, IOException, Exception{
    	    		
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
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
			                   phrescoHome.goToPhrescoHomePage(methodName);
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjHTML5WidgNone(YuiConst,methodName);
    }		
     
public void testCreate_DotNet_Proj() throws InterruptedException, IOException, Exception{
		
	        DotNetConst = new DotNetConstants();
			
			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
					+ phrsc.PORT + "/";
			browserAppends = "*" + phrsc.BROWSER;
			assertNotNull("Browser name should not be null", browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
			assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
			wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
					serverURL, phrsc.SPEED, phrsc.CONTEXT);
			assertNotNull(wel);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
			                   phrescoHome.goToPhrescoHomePage(methodName);
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjDotNetNone(DotNetConst,methodName);
    }
    public void testCreate_WordPress_Proj() throws InterruptedException, IOException, Exception{
		
    	    WordpressConst = new WordPressConstants();
			
			String serverURL = phrsc.PROTOCOL + "://" + phrsc.HOST + ":"
					+ phrsc.PORT + "/";
			browserAppends = "*" + phrsc.BROWSER;
			assertNotNull("Browser name should not be null", browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrsc.PORT);
			assertNotNull("selenium-port number should not be null", SELENIUM_PORT);
			wel = new WelcomeScreen(phrsc.HOST, SELENIUM_PORT, browserAppends,
					serverURL, phrsc.SPEED, phrsc.CONTEXT);
			assertNotNull(wel);
			methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println("methodName = " + methodName);
			loginObject = new LoginScreen(phrsc);
			PhrescoWelcomePage phrescoHome=loginObject.testLoginPage(methodName);
			                   phrescoHome.goToPhrescoHomePage(methodName);
		    ApplicationsScreen applicationScr=phrescoHome.clickOnApplicationsTab(methodName);
			AddApplicationScreen addappscrn = applicationScr.gotoAddApplicationScreen(methodName);
			addappscrn.createProjWordPressNone(WordpressConst,methodName);
    }

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

