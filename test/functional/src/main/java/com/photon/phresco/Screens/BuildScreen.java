	package com.photon.phresco.Screens;
	
	import java.io.IOException;
	
	import org.apache.commons.logging.Log;
	import org.apache.commons.logging.LogFactory;
	
	import com.photon.phresco.selenium.util.GetCurrentDir;
	import com.photon.phresco.selenium.util.ScreenException;
	import com.photon.phresco.uiconstants.Drupal7ConstantsXml;
	import com.photon.phresco.uiconstants.JavaWebServConstantsXml;
	import com.photon.phresco.uiconstants.MobWidgetConstantsXml;
	import com.photon.phresco.uiconstants.PhpConstantsXml;
	import com.photon.phresco.uiconstants.PhrescoUiConstants;
	import com.photon.phresco.uiconstants.PhrescoUiConstantsXml;
	import com.photon.phresco.uiconstants.SharepointConstantsXml;
	import com.photon.phresco.uiconstants.iPhoneConstantsXml;
	
	public class BuildScreen extends PhotonAbstractScreen {
		private PhrescoUiConstantsXml phrsc;
		private Log log = LogFactory.getLog(getClass());
	
		public BuildScreen(PhrescoUiConstantsXml phrescoConst) {
			this.phrsc = phrescoConst;
		}
	
		public void successFailureLoop() throws InterruptedException, IOException,
				Exception {
			if (waitForTextPresentConsole("BUILD SUCCESS")) {
				System.out.println("*****OPERATION SUCCEEDED*****");
			} else if (waitForTextPresentConsole("BUILD FAILURE")) {
				log.info("@sucessFailureLoop: failure");
				selenium.captureEntirePageScreenshot(
						GetCurrentDir.getCurrentDirectory() + "\\DeployFailure.png",
						"background=#CCFFDD");
				throw new ScreenException("*****OPERATION FAILED*****");
			}
	
		}
	
		public void NodeJs_Build() throws InterruptedException, IOException,
				Exception {
			click(phrsc.NODEJS_PROJECT_CREATION_ID);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.GENERATEBUILD);
			select(phrsc.SERVER, phrsc.NODEJS_CONFIG_CHOOSE_SERVER_NAME);
			select(phrsc.DATABASE, phrsc.NODEJS_CONFIG_CHOOSE_DB_NAME);
			click(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.NODEJS_GENERATE_BUILD_DOWNLOAD);
	
		}
	
		public void NodeJs_RunAgainstSrc() throws InterruptedException,
				IOException, Exception {
			click(phrsc.NODEJS_PROJECT_CREATION_ID);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.NODEJS_BUILD_RUNAGAINSTSRC_BTN);
			click(phrsc.SERVER);
			select(phrsc.SERVER, phrsc.NODEJS_CONFIG_CHOOSE_SERVER_NAME);
			select(phrsc.DATABASE, phrsc.NODEJS_CONFIG_CHOOSE_DB_NAME);
			click(phrsc.NODEJS_BUILD_RUN_BTN);
			waitForElementPresent(phrsc.NODEJS_BUILD_STOP_BTN);
	
		}
	
		public void HTML5Widget_Build() throws InterruptedException, IOException,
				Exception {
			click(phrsc.HTML5_WIDGET_PROJECT_CREATION_ID);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.GENERATEBUILD);
			select(phrsc.SERVER, phrsc.HTML5_WIDGET_CONFIG_SERVER_NAME_CLICK);
			select(phrsc.WEBSERVICE,
					phrsc.HTML5_WIDGET_CONFIG_WEBSERVICE_NAME_CLICK);
			click(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
	
		}
	
		public void HTML5Widget_Deploy() throws InterruptedException, IOException,
				Exception {
			click(phrsc.HTML5_WIDGET_PROJECT_CREATION_ID);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.DEPLOY);
	
		}
	
		public void PhpBuild(PhpConstantsXml phpconst) throws InterruptedException,
				IOException, Exception {
	
			waitForElementPresent(phpconst.PHPPROJECT);
			click(phpconst.PHPPROJECT);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.GENERATEBUILD);
			select(phrsc.SERVER, phpconst.PHP_SERVERCONFIG);
			click(phpconst.SERVERCONFIG_CLICK);
	
			select(phrsc.DATABASE, phpconst.PHP_DBCONFIG);
			click(phpconst.DBCONFIG_CLICK);
			click(phrsc.ENVIRONMENT_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
	
		}
	
		public void SharepointBuild(SharepointConstantsXml spConst)
				throws InterruptedException, IOException, Exception {
	
			waitForElementPresent(spConst.CREATEDPROJECT_SHAREPOINT);
			click(spConst.CREATEDPROJECT_SHAREPOINT);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.GENERATEBUILD);
			click(phrsc.GENERATEBUILD);
			select(phrsc.SERVER, spConst.MYSHAREPOINTSERVER);
			waitForElementPresent(phrsc.ENVIRONMENT_BUILD);
			click(phrsc.ENVIRONMENT_BUILD);
			successFailureLoop();
	
		}
	
		public void SharepointDeploy(SharepointConstantsXml spCons)
				throws Exception {
	
			waitForElementPresent(spCons.CREATEDPROJECT_SHAREPOINT);
			click(spCons.CREATEDPROJECT_SHAREPOINT);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
			click(phrsc.DEPLOY);
			successFailureLoop();
		}
	
		public void JavaWebServiceBuild(JavaWebServConstantsXml jws)
				throws InterruptedException, IOException, Exception {
	
			click(jws.CREATEDPROJECT_JAVAWEBSERVICE);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.GENERATEBUILD);
			select(phrsc.SERVER, jws.MYJAVAWERSERVICESERVER);
			select(phrsc.DATABASE, jws.MYJAVAWERSERVICEDB);
			click(phrsc.ENVIRONMENT_BUILD);
			successFailureLoop();
	
		}
	
		public void JavaWebServiceDeploy(JavaWebServConstantsXml jws)
				throws Exception {
	
			click(jws.CREATEDPROJECT_JAVAWEBSERVICE);
			click(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
			click(phrsc.DEPLOY);
			successFailureLoop();
		}
	
		public void JavaWebService_RunAgainstSrc(JavaWebServConstantsXml jws)
				throws InterruptedException, IOException, Exception {
			click(jws.CREATEDPROJECT_JAVAWEBSERVICE);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(jws.JAVAWEBSERVICE_BUILD_RUNAGAINSTSRC_BTN);
			click(phrsc.SERVER);
			select(phrsc.SERVER, jws.MYJAVAWERSERVICESERVER);
			select(phrsc.DATABASE, jws.MYJAVAWERSERVICEDB);
			click(jws.JAVAWEBSERVICE_BUILD_RUN_BTN);
			waitForElementPresent(jws.JAVAWEBSERVICE_BUILD_STOP_BTN);
	
		}
	
		public void DrupalBuild(Drupal7ConstantsXml drupalConst)
				throws InterruptedException, IOException, Exception {
	
			click(drupalConst.DRUPALPROJ);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.GENERATEBUILD);
			// click(phrsc.SERVER);
			select(phrsc.SERVER, drupalConst.DRUPAL_GENERATEBUILD_DRUPALSERVER);
			click(drupalConst.DRUPAL_GENERATEBUILD_DRUPALSERVER_CLICK);
			// click(phrsc.DATABASE);
			select(phrsc.DATABASE, drupalConst.DRUPAL_GENERATEBUILD_DRUPALDATABASE);
			click(drupalConst.DRUPAL_GENERATEBUILD_DRUPALDATABASE_CLICK);
			click(phrsc.ENVIRONMENT_BUILD);
			successFailureLoop();
		}
	
		public void drupaldeploy(Drupal7ConstantsXml drupalConst) throws Exception {
	
			click(drupalConst.DRUPALPROJ);
			click(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
			click(phrsc.DEPLOY);
			successFailureLoop();
		}
	
		public void iphonenativeBuild(iPhoneConstantsXml iPhoneConst)
				throws Exception {
			click(iPhoneConst.CREATEDPROJECT_iPHONENATIVE);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.GENERATEBUILD);
			// click(phrsc.SERVER);
			select(phrsc.WEBSERVICE,
					iPhoneConst.iPHONENATIVE_GENERATE_BUILD_WEBSERVICE);
			click(iPhoneConst.iPHONENATIVE_GENERATE_BUILD_WEBSERVICE_CLICK);
			select(iPhoneConst.GENERATE_BUILD_SDK,
					iPhoneConst.iPHONENATIVE_GENERATE_BUILD_SIMULATOR);
			click(iPhoneConst.iPHONENATIVE_GENERATE_BUILD_SIMULATOR_CLICK);
			// click(phrsc.DATABASE);
			click(phrsc.ENVIRONMENT_BUILD);
			successFailureLoop();
		}
	
		public void iphonenativedeploy(iPhoneConstantsXml iPhoneConst)
				throws Exception {
			click(iPhoneConst.CREATEDPROJECT_iPHONENATIVE);
			click(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
			click(phrsc.DEPLOY);
			click(iPhoneConst.iPHONENATIVE_GENERATE_BUILD_DEPLOY_SIMULATOR_CLICK);
			successFailureLoop();
	
		}
	
		public void deployPHP(PhpConstantsXml phpconst) throws Exception {
	
			waitForElementPresent(phpconst.PHPPROJECT);
			click(phpconst.PHPPROJECT);
			waitForElementPresent(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
			click(phrsc.DEPLOY);
			successFailureLoop();
		}
	
		public void MobilewidgetBuild(MobWidgetConstantsXml mobileWidg)
				throws Exception {
			click(mobileWidg.CREATEDPROJECT_HTML5MOBILEWIDGET);
			click(phrsc.EDITAPPLICATION_BUILD);
			click(phrsc.GENERATEBUILD);
			// click(phrsc.SERVER);
			select(phrsc.SERVER,
					mobileWidg.MOBILEWIDGET_GENERATEBUILD_MOBILEWIDGET_SERVER);
			// click(phrsc.MOBILEWIDGET_GENERATEBUILD_MOBILEWIDGET_SERVER_CLICK);
			select(phrsc.WEBSERVICE,
					mobileWidg.MOBILEWIDGET_GENERATEBUILD_MOBILEWIDGET_WEBSERVICE);
			click(mobileWidg.MOBILEWIDGET_GENERATEBUILD_MOBILEWIDGET_SERVER);
			// click(phrsc.DATABASE);
			click(phrsc.ENVIRONMENT_BUILD);
			successFailureLoop();
		}
	
		public void Mobilewidgetdeploy() throws Exception {
			click(phrsc.CREATEDPROJECT_HTML5MOBILEWIDGET);
			click(phrsc.EDITAPPLICATION_BUILD);
			waitForElementPresent(phrsc.DEPLOY);
			click(phrsc.DEPLOY);
	
			successFailureLoop();
		}
	
		public void Validation() throws Exception {
	
		}
	
	}
