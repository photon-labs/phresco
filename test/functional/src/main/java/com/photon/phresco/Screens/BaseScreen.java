package com.photon.phresco.Screens;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.gargoylesoftware.htmlunit.javascript.host.Element;
import com.photon.phresco.selenium.util.Constants;
import com.photon.phresco.selenium.util.ScreenActionFailedException;
import com.photon.phresco.selenium.util.ScreenException;
import com.thoughtworks.selenium.Selenium;

public class BaseScreen {


	public static Selenium selenium;
	public static WebDriver driver;
	private static ChromeDriverService chromeService;
	private static Log log = LogFactory.getLog("BaseScreen");

	public BaseScreen() {

	}

	public BaseScreen(String host, int port,String browser, String url,String speed, String context)
			throws AWTException, IOException, ScreenActionFailedException {

		initialize(host, port, browser, url, speed, context);
	}

	public static void initialize(String host, int port, String browser,
			String url, String speed, String context)
			throws com.photon.phresco.selenium.util.ScreenActionFailedException {
	
		try {
			instantiateBrowser(browser, url, context, speed);
		} catch (ScreenException se) {
			se.printStackTrace();
			
		}

	}

	public static void instantiateBrowser(String browserName, String url,
			String context, String speed) throws ScreenException {

		if (browserName.equalsIgnoreCase(Constants.BROWSER_CHROME)) {	
			try {
							
				chromeService = new ChromeDriverService.Builder()
						.usingChromeDriverExecutable(
								new File(getChromeLocation()))
						.usingAnyFreePort().build();				
				log.info("-------------***LAUNCHING GOOGLECHROME***--------------");
				chromeService.start();
				ChromeOptions chromeOption = new ChromeOptions();
				chromeOption.addArguments("start-maximized");
				driver = new ChromeDriver(chromeService, chromeOption);
//				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.navigate().to(url + context);
				
				/*selenium = new WebDriverBackedSelenium(driver, url);
				selenium.open(context);
				selenium.windowMaximize();*/

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (browserName.equalsIgnoreCase(Constants.BROWSER_IE)) {
			log.info("---------------***LAUNCHING INTERNET EXPLORE***-----------");
			driver = new InternetExplorerDriver();
			driver.navigate().to(url + context);
			// driver.get(url);
		/*	selenium = new WebDriverBackedSelenium(driver, url);
			selenium.open(context);*/

		} else if (browserName.equalsIgnoreCase(Constants.BROWSER_FIREFOX)) {
			log.info("-------------***LAUNCHING FIREFOX***--------------");
			driver = new FirefoxDriver();
			windowMaximizeFirefox();
			driver.navigate().to(url + context);	
	
		}/*else if(browserName.equalsIgnoreCase(Constants.ANDROID_HYBRID)){
			driver= new AndroidDriver();
//			driver.navigate().to(url + context);
			System.out.println("**********AndroidDriver*********");
			System.out.println("-------------"+url+context);
			driver.get(url+context);
		}*/
		
		else {
			throw new ScreenException(
					"------Only FireFox,InternetExplore and Chrome works-----------");
		}

	}

	public static void windowMaximizeFirefox() {
		driver.manage().window().setPosition(new Point(0, 0));
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		Dimension dim = new Dimension((int) screenSize.getWidth(),
				(int) screenSize.getHeight());
		driver.manage().window().setSize(dim);
	}

	public void closeBrowser() {
		log.info("-------------***BROWSER CLOSING***--------------");		
		if (driver != null) {	
			driver.quit();
			
		if(chromeService!=null){
			chromeService.stop();
			
			}
		} 
		
	}
	
	public static String  getChromeLocation(){	
		log.info("getChromeLocation:*****CHROME TARGET LOCATION FOUND***");
		String directory = System.getProperty("user.dir");
		String targetDirectory = getChromeFile();		
		String location = directory + targetDirectory;	
		return location;
	}
	
	
	public static String getChromeFile(){
	     if(System.getProperty("os.name").startsWith(Constants.WINDOWS_OS)){
			log.info("*******WINDOWS MACHINE FOUND*************");
			return Constants.WINDOWS_DIRECTORY + "/chromedriver.exe" ;			
		}else if(System.getProperty("os.name").startsWith(Constants.LINUX_OS)){
			log.info("*******LINUX MACHINE FOUND*************");
			return Constants.LINUX_DIRECTORY_64+"/chromedriver";
		}else if(System.getProperty("os.name").startsWith(Constants.MAC_OS)){
			log.info("*******MAC MACHINE FOUND*************");
			return Constants.MAC_DIRECTORY+"/chromedriver";
		}else{
			throw new NullPointerException("******PLATFORM NOT FOUND********");
		}
		
	}
	
	
}
