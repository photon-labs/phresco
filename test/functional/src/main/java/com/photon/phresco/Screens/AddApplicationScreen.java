package com.photon.phresco.Screens;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.photon.phresco.selenium.util.ScreenException;
import com.photon.phresco.uiconstants.AndroidHybridConstants;
import com.photon.phresco.uiconstants.AndroidNativeConstants;
import com.photon.phresco.uiconstants.DotNetConstants;
import com.photon.phresco.uiconstants.Drupal6ConstantsXml;
import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
import com.photon.phresco.uiconstants.JavaWebServConstantsXml;
import com.photon.phresco.uiconstants.JqueryWidgetConstants;
import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
import com.photon.phresco.uiconstants.NodeJSConstantsXml;
import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
import com.photon.phresco.uiconstants.PhpConstantsXml;
import com.photon.phresco.uiconstants.SharepointConstantsXml;
import com.photon.phresco.uiconstants.WordPressConstants;
import com.photon.phresco.uiconstants.YuiConstantsXml;
import com.photon.phresco.uiconstants.iPhoneConstantsXml;

public class AddApplicationScreen extends WebDriverAbstractBaseScreen {
	private PhrescoUiConstantsXml phrsc;
	private Log log = LogFactory.getLog(getClass());
	public WebDriverBaseScreen element;

	public AddApplicationScreen(PhrescoUiConstantsXml phrescoConst) throws Exception {
		this.phrsc = phrescoConst;
		
	}
	

		public void createProjDRUPAL7(Drupal7ConstantsXml drupalConst,String methodName) throws Exception {
			
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(drupalConst.APPINFO_DRUPAL_NAME_VALUE);
			
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(drupalConst.APPINFO_DRUPAL_DESCRIPTION_VALUE);
			
			element=getXpathWebElement(phrsc.TECH_WEBAPP);
			waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			
			element=getXpathWebElement(drupalConst.DRUPAL7);
			waitForElementPresent(drupalConst.DRUPAL7,methodName);
			element.click();
			
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			element.click();
			
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}

		   
			public void createProjHTML5MobileWidget(MobWidgetConstantsXml mobwidg,String methodName) throws Exception {
				
				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(mobwidg.APPINFO_HTML5MobileWidget_NAME_VALUE);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(mobwidg.APPINFO_HTML5MobileWidget_DESCRIPTION_VALUE);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				element=getXpathWebElement(mobwidg.HTML5MobileWidget);
				waitForElementPresent(mobwidg.HTML5MobileWidget,methodName);
				element.click();
				element=getXpathWebElement(phrsc.PILOTPROJ);
				waitForElementPresent(phrsc.PILOTPROJ,methodName);
				element.click();
				waitForElementPresent(phrsc.ESHOP,methodName);
				element=getXpathWebElement(phrsc.ESHOP);
				element.click();
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element.click();
				//Thread.sleep(10000);
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				//Thread.sleep(10000);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
				//waitForElementPresent(mobwidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
				
			}
			
			public void createProjHTML5Widg(YuiConstantsXml YuiConst, String methodName) throws Exception {

				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(YuiConst.HTML5_WIDGET_PROJET_NAME);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(YuiConst.HTML5_WIDGET_PROJECT_DESCRIPTION);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				element=getXpathWebElement(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET);
				waitForElementPresent(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET,methodName);
				element.click();
				element=getXpathWebElement(phrsc.PILOTPROJ);
				waitForElementPresent(phrsc.PILOTPROJ,methodName);
				element.click();
				waitForElementPresent(phrsc.ESHOP,methodName);
				element=getXpathWebElement(phrsc.ESHOP);
				element.click();
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element.click();
				//Thread.sleep(10000);
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				//Thread.sleep(10000);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
				

			}
			
			public void createProjJqueryWidget(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
				
				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(jquerywidg.JQUERY_WIDGET_PROJET_NAME);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(jquerywidg.JQUERY_WIDGET_PROJECT_DESCRIPTION);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				waitForElementPresent(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET,methodName);
				element=getXpathWebElement(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET);
				element.click();
				element=getXpathWebElement(phrsc.PILOTPROJ);
				waitForElementPresent(phrsc.PILOTPROJ,methodName);
				element.click();
				waitForElementPresent(phrsc.ESHOP,methodName);
				element=getXpathWebElement(phrsc.ESHOP);
				element.click();
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element.click();
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			}
		  public void createProjiPhoneNative(iPhoneConstantsXml iPhone,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(iPhone.APPINFO_iPHONENATIVE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(iPhone.APPINFO_iPHONENATIVE_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(iPhone.MOBILEAPP_iPHONENATIVE_CLICK);
			waitForElementPresent(iPhone.MOBILEAPP_iPHONENATIVE_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}
		  
	 public void createProjiPhoneHybrid(iPhoneConstantsXml iPhone,String methodName) throws Exception {
	        
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(iPhone.APPINFO_iPHONEHYBRID_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(iPhone.APPINFO_iPHONEHYBRID_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK);
			waitForElementPresent(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(iPhone.MOBILEAPP_SHOPPINGCART,methodName);
			element=getXpathWebElement(iPhone.MOBILEAPP_SHOPPINGCART);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			//Thread.sleep(10000);
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);    
	        
	    }
	 
	 public void createProjAndroidNative(AndroidNativeConstants androidNat,String methodName) throws Exception {
		    
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(androidNat.APPINFO_ANDROIDNATIVE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(androidNat.APPINFO_ANDROIDNATIVE_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK);
			waitForElementPresent(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
	 public void createProjAndroidHybrid(AndroidHybridConstants androidHyb,String methodName) throws Exception {
		    
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(androidHyb.APPINFO_ANDROIDHYBRID_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(androidHyb.APPINFO_ANDROIDHYBRID_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK);
			waitForElementPresent(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.ESHOP,methodName);
			element=getXpathWebElement(phrsc.ESHOP);
			waitForElementPresent(phrsc.ESHOP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
	 
	 
	 	public void createProjJavaWebService(JavaWebServConstantsXml jws,String methodName) throws Exception {
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		    element.click();
		    element.type(jws.APPINFO_JAVAWEBSERVICE_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		    element.type(jws.APPINFO_JAVAWEBSERVICE_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBSERVICE);
		    waitForElementPresent(phrsc.TECH_WEBSERVICE,methodName);
		    element.click();
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK);
			waitForElementPresent(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(jws.JWSESHOPPROJ,methodName);
			element=getXpathWebElement(jws.JWSESHOPPROJ);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		
	}
		public void createProjPHP(PhpConstantsXml phpconst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		    element.click();
		    element.type(phpconst.APPINFO_PHP_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		    element.type(phpconst.APPINFO_PHP_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBAPP);
		    waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(phpconst.PHP_CLICK);
			waitForElementPresent(phpconst.PHP_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phpconst.APPINFO_PHP_PHPBLOG,methodName);
			element=getXpathWebElement(phpconst.APPINFO_PHP_PHPBLOG);
			waitForElementPresent(phpconst.APPINFO_PHP_PHPBLOG,methodName);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
		   
		public void createProjSharepoint(SharepointConstantsXml spconst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		    element.click();
		    element.type(spconst.APPINFO_SHAREPOINT_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		    element.type(spconst.APPINFO_SHAREPOINT_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBAPP);
		    waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(spconst.SHAREPOINT);
			waitForElementPresent(spconst.SHAREPOINT,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(spconst.SHAREPOINT_RESOURCEMNGMT,methodName);
			element=getXpathWebElement(spconst.SHAREPOINT_RESOURCEMNGMT);
			waitForElementPresent(spconst.SHAREPOINT_RESOURCEMNGMT,methodName);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		}


		public void createProjNodeJS(NodeJSConstantsXml nodejsconst,String methodName) throws Exception {
			
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		    element.click();
		    element.type(nodejsconst.NODEJS_PROJET_NAME);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		    element.type(nodejsconst.NODEJS_PROJECT_DESCRIPTION);
		    element=getXpathWebElement(phrsc.TECH_WEBSERVICE);
		    waitForElementPresent(phrsc.TECH_WEBSERVICE,methodName);
		    element.click();
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(nodejsconst.NODEJS_SELECT_TECHNOLOGY);
			waitForElementPresent(nodejsconst.NODEJS_SELECT_TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			element=getXpathWebElement(nodejsconst.NODEJS_PILOTPROJ_ESHOPWS);
			waitForElementPresent(nodejsconst.NODEJS_PILOTPROJ_ESHOPWS,methodName);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}



 /***********************************NONE_PROJECTS*********************************************/

public void createProjDRUPAL7None(Drupal7ConstantsXml drupalConst,String methodName) throws Exception {
			
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(drupalConst.APPINFO_DRUPALNONE_NAME_VALUE);
			
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(drupalConst.APPINFO_DRUPAL_DESCRIPTION_VALUE);
			
			element=getXpathWebElement(phrsc.TECH_WEBAPP);
			waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			
			element=getXpathWebElement(drupalConst.DRUPAL7);
			element.click();
			
			element=getXpathWebElement(phrsc.PILOTPROJ);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}
			public void createProjDRUPAL6(Drupal6ConstantsXml drupal6Const,String methodName) throws Exception {
				
				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(drupal6Const.APPINFO_DRUPAL6_NAME_VALUE);
				
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(drupal6Const.APPINFO_DRUPAL6_DESCRIPTION_VALUE);
				
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				
				element=getXpathWebElement(drupal6Const.DRUPAL6);
				element.click();
				
				element=getXpathWebElement(phrsc.PILOTPROJ);
				element.click();
				waitForElementPresent(phrsc.NONE,methodName);
				element=getXpathWebElement(phrsc.NONE);
				element.click();
				
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element.click();
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				element.click();
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
				
			}
		   
			public void createProjHTML5MobileWidgetNone(MobWidgetConstantsXml mobwidg,String methodName) throws Exception {
				
				element=getXpathWebElement(phrsc.APPINFO_NAME);
				element.click();
				element.type(mobwidg.APPINFO_MOBILEWIDGET_NONE_NAME_VALUE);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				element.type(mobwidg.APPINFO_HTML5MobileWidget_DESCRIPTION_VALUE);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				element.click();
				element=getXpathWebElement(mobwidg.HTML5MobileWidget);
				element.click();
				element=getXpathWebElement(phrsc.PILOTPROJ);
				element.click();
				waitForElementPresent(phrsc.NONE,methodName);
				element=getXpathWebElement(phrsc.NONE);
				element.click();
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				element.click();
				//Thread.sleep(10000);
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				//Thread.sleep(10000);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
				//waitForElementPresent(mobwidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
				
			}
			
			public void createProjHTML5WidgNone(YuiConstantsXml YuiConst,String methodName) throws Exception {

				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(YuiConst.HTML5_WIDGET_PROJECT_NONE_NAME);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(YuiConst.HTML5_WIDGET_PROJECT_DESCRIPTION);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				element=getXpathWebElement(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET);
				waitForElementPresent(YuiConst.APPINFO_TECHNOLOGY_HTML5_WIDGET,methodName);
				element.click();
				element=getXpathWebElement(phrsc.PILOTPROJ);
				waitForElementPresent(phrsc.PILOTPROJ,methodName);
				element.click();
				waitForElementPresent(phrsc.NONE,methodName);
				element=getXpathWebElement(phrsc.NONE);
				element.click();
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element.click();
				//Thread.sleep(10000);
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				//Thread.sleep(10000);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
				

			}
			
			public void createProjJqueryWidgetNone(JqueryWidgetConstants jquerywidg,String methodName) throws Exception {
				
				element=getXpathWebElement(phrsc.APPINFO_NAME);
				waitForElementPresent(phrsc.APPINFO_NAME,methodName);
				element.click();
				element.type(jquerywidg.JQUERY_WIDGET_NONEPROJ_NAME);
				element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
				waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
				element.type(jquerywidg.JQUERY_WIDGET_PROJECT_DESCRIPTION);
				element=getXpathWebElement(phrsc.TECH_WEBAPP);
				waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
				element.click();
				element=getXpathWebElement(phrsc.TECHNOLOGY);
				waitForElementPresent(phrsc.TECHNOLOGY,methodName);
				element.click();
				waitForElementPresent(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET,methodName);
				element=getXpathWebElement(jquerywidg.APPINFO_TECHNOLOGY_JQUERY_WIDGET);
				element.click();
				element=getXpathWebElement(phrsc.PILOTPROJ);
				waitForElementPresent(phrsc.PILOTPROJ,methodName);
				element.click();
				waitForElementPresent(phrsc.NONE,methodName);
				element=getXpathWebElement(phrsc.NONE);
				element.click();
				element=getXpathWebElement(phrsc.APPINFO_NEXT);
				waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
				element.click();
				waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
				element=getXpathWebElement(phrsc.APPINFO_FINISH);
				element.click();
				
				waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			}
		  public void createProjiPhoneNativeNone(iPhoneConstantsXml iPhone,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(iPhone.APPINFO_iPHONENATIVE_NONEPROJ_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(iPhone.APPINFO_iPHONENATIVE_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(iPhone.MOBILEAPP_iPHONENATIVE_CLICK);
			waitForElementPresent(iPhone.MOBILEAPP_iPHONENATIVE_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
            waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}
		  
	 public void createProjiPhoneHybridNone(iPhoneConstantsXml iPhone,String methodName) throws Exception {
	        
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
			element.click();
			element.type(iPhone.APPINFO_iPHONEHYBRID_NONE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			element.type(iPhone.APPINFO_iPHONEHYBRID_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK);
			waitForElementPresent(iPhone.MOBILEAPP_iPHONEHYBRID_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);    
	        
	    }
	 
	 public void createProjAndroidNativeNone(AndroidNativeConstants androidNat,String methodName) throws Exception {
		    
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(androidNat.APPINFO_ANDROIDNATIVE_NONE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(androidNat.APPINFO_ANDROIDNATIVE_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK);
			waitForElementPresent(androidNat.MOBILEAPP_ANDROIDNATIVE_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
	 public void createProjAndroidHybridNone(AndroidHybridConstants androidHyb,String methodName) throws Exception {
		    
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
			element.click();
			element.type(androidHyb.APPINFO_ANDROIDHYBRID_NONE_NAME_VALUE);
			element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
			waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
			element.type(androidHyb.APPINFO_ANDROIDHYBRID_DESCRIPTION_VALUE);
			waitForElementPresent(phrsc.TECH_MOBILEAPP,methodName);
			element=getXpathWebElement(phrsc.TECH_MOBILEAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK);
			waitForElementPresent(androidHyb.MOBILEAPP_ANDROIDHYBRID_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
	 
	 
	 	public void createProjJavaWebServiceNone(JavaWebServConstantsXml jws,String methodName) throws Exception {
		    element=getXpathWebElement(phrsc.APPINFO_NAME);
		    waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		    element.click();
		    element.type(jws.APPINFO_JAVAWEBSERVICE_NONE_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		    element.type(jws.APPINFO_JAVAWEBSERVICE_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBSERVICE);
		    waitForElementPresent(phrsc.TECH_WEBSERVICE,methodName);
		    element.click();
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK);
			waitForElementPresent(jws.WEBSERVICES_JAVAWEBSERVICE_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			waitForElementPresent(phrsc.APPINFO_NEXT,methodName);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		
	}
		public void createProjPHPNone(PhpConstantsXml phpconst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
			waitForElementPresent(phrsc.APPINFO_NAME,methodName);
		    element.click();
		    element.type(phpconst.APPINFO_PHP_NONEPROJ_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    waitForElementPresent(phrsc.APPINFO_DESCRIPTION,methodName);
		    element.type(phpconst.APPINFO_PHP_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBAPP);
		    waitForElementPresent(phrsc.TECH_WEBAPP,methodName);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(phpconst.PHP_CLICK);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			element.click();
			//Thread.sleep(10000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			//Thread.sleep(10000);
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);

		}
		   
		public void createProjSharepointNone(SharepointConstantsXml spconst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
		    element.click();
		    element.type(spconst.APPINFO_SHAREPOINT_NONE_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    element.type(spconst.APPINFO_SHAREPOINT_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECH_WEBAPP);
			element.click();
			element=getXpathWebElement(phrsc.TECHNOLOGY);
			element.click();
			element=getXpathWebElement(spconst.SHAREPOINT);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			element.click();
			//Thread.sleep(10000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			//Thread.sleep(10000);
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
		}


		public void createProjNodeJSNone(NodeJSConstantsXml nodejsconst,String methodName) throws Exception {
			
			element=getXpathWebElement(phrsc.APPINFO_NAME);
		    element.click();
		    element.type(nodejsconst.NODEJS_PROJET_NONE_NAME);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    element.type(nodejsconst.NODEJS_PROJECT_DESCRIPTION);
		    element=getXpathWebElement(phrsc.TECH_WEBSERVICE);
		    element.click();
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(nodejsconst.NODEJS_SELECT_TECHNOLOGY);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			element.click();
			//Thread.sleep(10000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			//Thread.sleep(10000);
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}


		public void createProjDotNetNone(DotNetConstants dotNetConst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
		    element.click();
		    element.type(dotNetConst.APPINFO_DOTNET_NONEPROJ_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    element.type(dotNetConst.APPINFO_DOTNET_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(dotNetConst.WEBAPP_DOTNET_CLICK);
			waitForElementPresent(dotNetConst.WEBAPP_DOTNET_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			element.click();
			//Thread.sleep(10000);
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			//Thread.sleep(10000);
			
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}


		public void createProjWordPressNone(WordPressConstants wordpressConst,String methodName) throws Exception {
			element=getXpathWebElement(phrsc.APPINFO_NAME);
		    element.click();
		    element.type(wordpressConst.APPINFO_WORDPRESS_NONEPROJ_NAME_VALUE);
		    element=getXpathWebElement(phrsc.APPINFO_DESCRIPTION);
		    element.type(wordpressConst.APPINFO_WORDPRESS_DESCRIPTION_VALUE);
		    element=getXpathWebElement(phrsc.TECHNOLOGY);
			waitForElementPresent(phrsc.TECHNOLOGY,methodName);
			element.click();
			element=getXpathWebElement(wordpressConst.WEBAPP_WORDPRESS_CLICK);
			waitForElementPresent(wordpressConst.WEBAPP_WORDPRESS_CLICK,methodName);
			element.click();
			element=getXpathWebElement(phrsc.PILOTPROJ);
			waitForElementPresent(phrsc.PILOTPROJ,methodName);
			element.click();
			waitForElementPresent(phrsc.NONE,methodName);
			element=getXpathWebElement(phrsc.NONE);
			element.click();
			element=getXpathWebElement(phrsc.APPINFO_NEXT);
			element.click();
			waitForElementPresent(phrsc.APPINFO_FINISH,methodName);
			element=getXpathWebElement(phrsc.APPINFO_FINISH);
			element.click();
			waitForElementPresent(phrsc.PROJCREATIONSUCCESSMSG,methodName);
			
		}

		
    	public ConfigScreen ConfigScreen() throws Exception {
    		element=getXpathWebElement(phrsc.APPLICATIONS_TAB);
    		element.click();
			//waitForElementPresent(phrsc.ADD_APPLICATION_BUTTON);
			return new ConfigScreen();
		}
		
		

		
		/*public BuildScreen gotoBuildScreen() throws Exception {
		      waitForElementPresent(phrsc.ADD_APPLICATION_BUTTON);
			return new BuildScreen(phrsc);
			}*/

		

		}

