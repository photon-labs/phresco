/*
 * Author by {phresco} QA Automation Team
 */
package photon.phresco.nodejs.testcases;



import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.server.SeleniumServer;
import org.testng.annotations.BeforeTest;


import com.photon.phresco.Screens.WelcomeScreen;
import com.photon.phresco.uiconstants.PhrescoUiConstants;

import com.thoughtworks.selenium.Selenium;

public class AllTest extends TestCase {
	

	private SeleniumServer serv;
	private PhrescoUiConstants phrsc;
	private int SELENIUM_PORT;
	private String browserAppends;
	//private LoginScreen loginObject;
	private Log log = LogFactory.getLog(getClass());
	private String contextName;
	WelcomeScreen wel;
	String serverURL;

	@Test
	public void testWel() throws InterruptedException, IOException, Exception {
		try{
			assertNotNull("Browser name should not be null",browserAppends);
			SELENIUM_PORT = Integer.parseInt(phrsc.SERVER_PORT);
			System.out.println("selenium port value"+SELENIUM_PORT);
			assertNotNull("selenium-port number should not be null",SELENIUM_PORT);
			wel=new WelcomeScreen(phrsc.SELENIUM_HOST, SELENIUM_PORT,
					browserAppends, serverURL, phrsc.SPEED,
					contextName);
			assertNotNull(wel);
			wel.nodejsNone();
		} catch(Exception t){
			t.printStackTrace();
    		System.out.println("ScreenCaptured");
    		//selenium.captureEntirePageScreenshot("\\WelPageFails.png", "background=#CCFFDD");	
			wel.ScreenCapturer();

    	}
	}

	public void setUp() throws Exception {

		phrsc = new PhrescoUiConstants();
		serverURL = phrsc.PROTOCOL + "://"
				+ phrsc.HOST + ":"
				+ phrsc.PORT + "/";
		
		browserAppends = "*" + phrsc.BROWSER;
		contextName = phrsc.CONTEXT;
	}

	public void tearDown() {
		clean();
	}

	private void clean() {
		
		wel.closeBrowser();
		
	}

}