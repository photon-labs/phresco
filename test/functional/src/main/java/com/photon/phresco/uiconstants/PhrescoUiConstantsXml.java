package com.photon.phresco.uiconstants;

import java.lang.reflect.Field;

public class PhrescoUiConstantsXml {
	public ReadXMLFile readXml;
	public String HOST = "hostName";
	public String CONTEXT = "contextName";
	public String PROTOCOL = "protocol";
	public String BROWSER = "browserName";
	public String SPEED = "speed";
	public String PORT = "serverPort";
	public String VIDEO_TITLE="videoTitle";
	public String SETTINGS_TAB="settingsTab";
	public String HELP_TAB="helpTab";
	// ***************HOMEPAGE******************
		public String HOME = "hometab";
	// ***************HOMEPAGE******************
	// ***************WELCOMEPAGE***************
		public String WELCOME_TO_PHRESCO="welcomeText";
		public String GOTOHOME_PAGE="gotoHomePage";
		public String CHECKBOX = "checkBoxDonotShow";

	// ***************WELCOMEPAGE***************
	// ***************LOGINPAGE*****************
	public String USER_NAME_XPATH = "userNameXpath";
	public String PASSWORD_XPATH = "passwordXpath";
	public String USER_ID = "userName";
	public String PASSWORD = "password";
	public String REMEMBER_ME_CHECK="rememberCheck";
	public String LOGIN_BUTTON = "loginButton";
	public String LOGOUT = "signOutLink";
	// ***************LOGINPAGE*****************
	// **************APPLICATION_APPINFO************
		
	    public String APPLICATIONS_TEXT="applicationsText";
	    public String APPLICATIONS_TAB="applicationTab";
	    public String ADD_APPLICATION_BUTTON="addApplicationButtonXpath";
		public String DISCOVER = "discoverButton";
		public String APPINFO_NEXT = "appinfoNextButton";
		public String APPINFO_CANCEL = "appinfoCancelButton";
		public String APPINFO_NAME = "appInfoNameXpath";
		public String APPINFO_CODE = "appinfoCodeXpath";
		public String APPINFO_DESCRIPTION = "appinfoDescriptionXpath";
		public String TECH_MOBILEAPP = "appinfoRadioMobileappXpath";
		public String TECH_WEBAPP = "appinfoRadioWebappXpath";
		public String TECH_WEBSERVICE = "appinfoRadioWebserviceXpath";
		public String TECHNOLOGY = "appinfoTechnologyXpath";
		public String PILOTPROJ = "pilotProj";
		public String ESHOP="appInfoPilotProjEshop";
		public String NONE ="appInfoPilotProjNone";
		public String APPINFO_FRAMEWORK = "appinfoFrameworkId";
		public String APPINFO_DATABASE = "appinfoDatabaseId";
		public String APPINFO_EDITOR = "appinfoEditorId";
		public String APPINFO_PLATFORM = "appinfoPlatformId";
		public String APPINFO_LIBRARIES = "appinfoLibrariesId";
		public String APPINFO_JSLIBRARIES = "appinfoJSlibrariesId";
		public String APPINFO_CREATEPROJECT = "appinfoCreateProjectButton";
		public String APPINFO_FINISH = "appinfoFinishButton";
		public String PROJCREATIONSUCCESSMSG = "ProjCreationSuccessMsg";
		public String APPINFO_SELECTALL= "appInfoSelectAll";
		public String APPINFO_DELETE = "appInfoDeleteButton";
		public String APPINFO_CONFIRM_OK = "appInfoConfirmationOk";
		public String APPINFO_CONFIRM_CANCEL ="appInfoConfirmationCancel";
		public String APPINFO_DELETION_SUCCESSMSG = "deletionSuccessMsg";
		public String APPINFO_NOPROJSAVAILABLE_MSG ="noProjsAvailableMsg";

	// **************APPLICATION_APPINFO************
	// *********************BUILD*************************

		public String SERVER = "buildtabServer";
		public String DATABASE = "buildtabDatabase";
		public String EDITAPPLICATION_BUILD = "buildtabBuild";
		public String GENERATEBUILD = "buildtabGenerateBuild";
		public String ENVIRONMENT_BUILD = "buildtabGenBuildpopupBuild";
		public String DEPLOY = "buildtabDeployIcon";
		public String SHOWSETTINGS = "buildtabShowSettings";
		public String WEBSERVICE ="builtabWebService";
	// *********************BUILD*************************
	// *******************CONFIGURATIONS******************

	public String CONFIGURATIONS = "configtab";
	public String CONFIGURATIONS_ADD = "configtabAddButtonlink";
	public String CONFIGURATIONS_NAME = "ConfigNameId";
	public String CONFIGURATIONS_SAVE = "ConfigSaveButtonId";
	public String CONFIGURATIONS_DESCRIPTION = "ConfigDescriptionId";
	public String CONFIGURATIONS_TYPE = "ConfigTypeId";
	public String CONFIGURATIONS_TYPE_SERVER_VALUE = "ConfigTypeServerValue";
	public String CONFIGURATIONS_TYPE_SERVER_CLICK = "ConfigTypeServerValueClick";
	public String CONFIGURATIONS_TYPE_EMAIL = "ConfigTypeEmailValue";
	public String CONFIGURATIONS_TYPE_EMAIL_CLICK = "ConfigTypeEmailValueClick";
	public String CONFIGURATIONS_TYPE_DATABASE = "ConfigTypeDatabase";
	public String CONFIGURATIONS_TYPE_DATABASE_CLICK = "ConfigTypeDatabaseClick";
	public String CONFIGURATIONS_DATABASE_USERNAME_CLICK = "ConfigTypedbUsernameClick";
	public String CONFIGURATIONS_TYPE_DATABASE_NAME_CLICK = "ConfigTypeDBnameClick";
	public String CONFIGURATIONS_HOST = "ConfigHostName";
	public String CONFIGURATIONS_PORT = "ConfigPortNumber";
	public String CONFIGURATIONS_SERVER_DEPLOYDIR = "ConfigServerDeployDir";
	public String CONFIGURATIONS_SERVER_TYPE = "ConfigServerType";
	public String CONFIGURATIONS_SERVER_TYPE_WAMP = "ConfigServerTypeWamp";
	public String CONFIGURATIONS_SERVER_CONTEXT = "ConfigServerContext";

	// ******************CONFIGURATIONS*******************
	// *************FEATURES******************
		public String DRUPAL7_SELECTMODULE_BLOCK = "drupal7ModBlock";
		public String DRUPAL7_SELECTMODULE_TAXONOMYMANAGER = "drupal7ModTaxonomyManager";
		public String DRUPAL7_SELECTMODULE_FIELD = "drupal7ModField";
		public String DRUPAL7_SELECTMODULE_FIELDSQLSTORAGE = "drupal7ModFieldSQLStorage";
		public String DRUPAL7_SELECTMODULE_FILE = "drupal7ModFile";
		public String DRUPAL7_SELECTMODULE_FILTER = "drupal7ModFilter";
		public String DRUPAL7_SELECTMODULE_NODE = "drupal7ModNode";
		public String DRUPAL7_SELECTMODULE_OPTIONS = "drupal7ModOptions";
		public String DRUPAL7_SELECTMODULE_SYSTEM = "drupal7ModSystem";
		public String DRUPAL7_SELECTMODULE_TEXT = "drupal7ModText";
		public String DRUPAL7_SELECTMODULE_USER = "drupal7ModUser";
		public String DRUPAL7_SELECTMODULE_AGGREGATOR = "drupal7ModAggregator";
		public String DRUPAL7_SELECTMODULE_BLOG = "drupal7ModBlog";
		public String DRUPAL7_SELECTMODULE_BOOK = "drupal7ModBook";
		public String DRUPAL7_SELECTMODULE_COLOR = "drupal7ModColor";
		public String DRUPAL7_SELECTMODULE_COMMENT = "drupal7ModComment";
		public String PHP_SELECTMODULE_LOGIN = "phpModLogin";
		public String PHP_SELECTMODULE_SHOUTBOX = "phpModShoutBox";
		public String PHP_SELECTMODULE_WEATHER = "phpModWeather";
		public String PHP_SELECTMODULE_PAGINATION = "phpModPagination";
		public String PHP_SELECTMODULE_LISTFILES = "phpModListFiles";
		public String PHP_SELECTMODULE_AUTOCOMPLETESEARCH = "phpModAutoCompleteSearch";
		public String PHP_SELECTMODULE_CONTACTFORM = "phpModContactForm";
		public String PHP_SELECTMODULE_MULTIPLECHECKBOX = "phpModMultipleCheckBox";
		public String PHP_SELECTMODULE_REPORTGENERATOR = "phpModReportGenerator";
		public String PHP_SELECTMODULE_COMMENTINGSYSTEM = "phpModCommentingSystem";
		public String ANDROID_SELECTMODULE_DROIDFU = "androidModDroidfu";
		public String ANDROID_SELECTMODULE_ANDROIDANNOTATIONS = "androidModAnnotations";
		public String iPHONE_SELECTMODULE_SCANNERKIT="iphoneModScannerKit";
		public String JAVAWEBSERVICE_SELECTMODULE_HIBERNATJPA = "javaWebserviceModHibernatjpa";
		public String SHAREPOINT_SELECTMODULE_ERRORREPORTING = "sharepointModErrorReporting";
		public String SHAREPOINT_SELECTMODULE_LOG4NET = "sharepointModLog4Net";
		public String SHAREPOINT_SELECTMODULE_MEDIAPLAYER = "sharepointModMediaPlayer";
		

		// *************FEATURES******************
	public PhrescoUiConstantsXml() {
		try {
			readXml = new ReadXMLFile();
			Field[] arrayOfField1 = super.getClass().getFields();
			Field[] arrayOfField2 = arrayOfField1;
			int i = arrayOfField2.length;
			for (int j = 0; j < i; ++j) {
				Field localField = arrayOfField2[j];
				Object localObject = localField.get(this);
				if (localObject instanceof String)
					localField
							.set(this, readXml.getValue((String) localObject));

			}
		} catch (Exception localException) {
			throw new RuntimeException("Loading "
					+ super.getClass().getSimpleName() + " failed",
					localException);
		}
	}
}
