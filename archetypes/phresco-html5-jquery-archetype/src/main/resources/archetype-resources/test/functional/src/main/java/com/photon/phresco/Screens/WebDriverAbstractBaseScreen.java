package com.photon.phresco.Screens;

import java.io.File;
import java.io.IOException;

import javax.activity.InvalidActivityException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import com.photon.phresco.selenium.util.GetCurrentDir;
import com.photon.phresco.selenium.util.ScreenActionFailedException;
import com.photon.phresco.selenium.util.ScreenException;


/**
 * Contains all selenium webdriver related methods
 */

public class WebDriverAbstractBaseScreen extends BaseScreen {

	public static WebElement element;
	private Log log = LogFactory.getLog(getClass());
	private final static int TIMEOUT = 25000;
	private By by;
	private WebDriverWait wait;

	/**
	 * This constructor will check the driver object value.
	 * 
	 * @throws ScreenException
	 */
	protected WebDriverAbstractBaseScreen() throws ScreenException {
		log.info("Entering:********WebDriverAbstractBaseScreen Constructor********");
		if (driver == null) {
			throw new ScreenException(
					"********The driver object should not be null********");
		}
	}
	/**
	 * 
	 * @param hostName
	 * @param portNumber
	 * @param browserName
	 * @param url
	 * @param speed
	 * @param context
	 * @throws ScreenException
	 * @throws ScreenActionFailedException
	 */
	public WebDriverAbstractBaseScreen(String hostName, int portNumber,
			String browserName, String url, String speed, String context)
			throws ScreenException, ScreenActionFailedException {
		log.info("Entering:********WebDriverAbstractBaseScreen parameter Constructor********");
		initialize(hostName, portNumber, browserName, url, speed, context);
	}

	/**
	 * 
	 * @param xpath
	 *            This methods gets the xpath expression give it back the
	 *            WebElement object.
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getXpathWebElement(String xpath)
			throws ScreenException {
		log.info("Entering:********getXpathWebElement********");
		try {
			this.by=getXpathByValue(xpath);
			// element = driver.findElement(this.by);
			} catch (Throwable t) {
			log.info("Entering:*********Exception in getXpathWebElement()******");
			t.printStackTrace();

		}
		return getElementValue(this.by);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getIdWebElement(String id)
			throws ScreenException {
		log.info("Entering:****getIdWebElement**********");
		try {
			driver.findElement(getBySelector(id));
			// element = driver.findElement(this.by);

		} catch (Throwable t) {
			log.info("Entering:*********Exception in getIdWebElement()******");
			t.printStackTrace();

		}
		return getElementValue(this.by);
	}

	/**
	 * 
	 * @param selector
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getcssWebElement(String selector)
			throws ScreenException {
		log.info("Entering:****getIdWebElement**********");
		try {
			this.by = getBySelector(selector);
			// element = driver.findElement(this.by);

		} catch (Throwable t) {
			log.info("Entering:*********Exception in getCSSWebElement()******");

			t.printStackTrace();

		}
		return getElementValue(this.by);
	}

	/**
	 * 
	 * @param value
	 *            It reads the Expression value and returns the by object.
	 * @return
	 * @throws InvalidActivityException
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getFindElement(String value)
			throws ScreenException {

		log.info("getBySelector():-------Entered---------");
		if (value.startsWith("//")) {
			log.info("--------Getting the Xpath Expression ---------");
			this.by = By.xpath(value);
		} else if (value.startsWith("link")) {
			log.info("--------Getting the Link Expression ---------");
			this.by = By.linkText(value);
		} else if (value.startsWith("css")) {
			log.info("--------Getting the CSS Expression ---------");
			this.by = By.cssSelector(value);
		} else {
			log.info("--------Getting the Id Expression ---------");
			this.by = By.id(value);
		}
		return getElementValue(this.by);

	}
	/**
	 * 
	 * @param by
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getElementValue(By by) throws ScreenException {
		if (by != null) {
			log.info("getFindElement:---Getting the element of-------" + by);
			element = driver.findElement(by);
		} else {
			throw new ScreenException("Value should not be null" + by);
		}
		return new WebDriverBaseScreen();

	}
	/**
	 * 
	 * @param value
	 * @return
	 * @throws ScreenException
	 */
	public By getBySelector(String value) throws ScreenException {

		try {
			log.info("getBySelector():-------Entered---------");
			if (value.startsWith("//")) {
				this.by = By.xpath(value);
				log.info("--------Getting the ### Xpath ### Expression ---------"+this.by);
			} else if (value.startsWith("link")) {
				this.by = By.linkText(value);
				log.info("--------Getting the ### Link ### Expression ---------"+this.by);
			} else if (value.startsWith("css")) {
				this.by = By.cssSelector(value);
				log.info("--------Getting the ### CSS ### Expression ---------"+this.by);
			} else {
				this.by = By.id(value);
				log.info("--------Getting the ### id ### Expression ---------"+this.by);
			}

		} catch (Exception e) {
			log.info("Exception:-------getBySelector()---------" + value);
		}
		
		return this.by;

	}

	/**
	 * 
	 * @param locator
	 *            It gets the expression value and by default wait for two
	 *            minutes.
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForElementPresent(String locator)
			throws InterruptedException {
		try {
			log.info("Entering:*********waitForElementPresent()******");
			this.by = getBySelector(locator);
			this.wait = new WebDriverWait(driver, TIMEOUT);
			log.info("Waiting:*************until the element is visible  ***********");

		} catch (Exception sc) {
			log.info("-----waitForElementPresent timeup------");
			sc.printStackTrace();
		}
		return this.wait.until(presenceOfElementLocated(this.by));
	}
	/**
	 * 
	 * @param locator
	 * @return
	 */
	private Function<WebDriver, WebElement> presenceOfElementLocated(
			final By locator) {
		log.info("Entering:*********presenceOfElementLocated()******Start");
		return new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				log.info("##Entering:*********presenceOfElementLocated()******End");	
				System.out.println("====================");
				if(driver.findElement(locator)!=null){
					log.info("presenceOfElementLocated():---Element found--------");					
				}else {
					log.info("presenceOfElementLocated():---Element not found--------");
					throw new ElementNotVisibleException("The element is not find with in the time");
				}
			
				return driver.findElement(locator);

			}

		};

	}
	/**
	 * 
	 * @param linkText
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getLinkWebElement(String linkText)
			throws ScreenException {
		log.info("Entering:****getIdWebElement**********");
		try {
			this.by = getBySelector(linkText);
		} catch (Throwable t) {
			log.info("Entering:*********Exception in getLinkWebElement()******");
			t.printStackTrace();
		}
		return getElementValue(this.by);
	}
	/**
	 * 
	 * @param value
	 * @return
	 * @throws ScreenException
	 */
	public WebDriverBaseScreen getWebElement(String value)
			throws ScreenException {
		log.info("Entering:****getIdWebElement**********");
		try {
			this.by = getBySelector(value);
			// element = driver.findElement(this.by);
		} catch (Throwable t) {
			log.info("Entering:*********Exception in getIdWebElement()******");
			t.printStackTrace();

		}
		return getElementValue(this.by);
	}
	 /**
	  * 
	  * @param Xpath
	  * @return
	  * @throws ScreenException
	  */
	public By getXpathByValue(String Xpath) throws ScreenException {
		if (Xpath != null) {
			this.by = By.xpath(Xpath);
		} else {
			throw new ScreenException("Enter:---getXpathByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ScreenException
	 */
	public By getIdByValue(String id) throws ScreenException {
		if (id != null) {
			this.by = By.id(id);
			
		} else {
			throw new ScreenException("Enter:---getIdByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param css
	 * @return
	 * @throws ScreenException
	 */
	public By getCSSByValue(String css) throws ScreenException {
		if (css != null) {
			this.by = By.id(css);
			
		} else {
			throw new ScreenException("Enter:---getCSSByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param link
	 * @return
	 * @throws ScreenException
	 */
	public By getLinkByValue(String link) throws ScreenException {
		if (link != null) {
			this.by = By.id(link);

		} else {
			throw new ScreenException("Enter:---getLinkByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param className
	 * @return
	 * @throws ScreenException
	 */
	public By getClassNameByValue(String className) throws ScreenException {
		if (className != null) {
			this.by = By.id(className);

		} else {
			throw new ScreenException("Enter:---getClassNameByValue()-----");
		}
		return this.by;
	}
	/**
	 * 
	 * @param name
	 * @return
	 * @throws ScreenException
	 */
	public By getNameByValue(String name) throws ScreenException {
		if (name != null) {
			this.by = By.id(name);

		} else {
			throw new ScreenException("Enter:---getClassNameByValue()-----");
		}
		return this.by;
	}
	public By getTagByValue(String name) throws ScreenException {
		if (name != null) {
			this.by = By.tagName(name);

		} else {
			throw new ScreenException("Enter:---getClassNameByValue()-----");
		}
		return this.by;
	}
	
	public void ScreenCapturer() throws IOException, Exception{
		try{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(scrFile, new File(GetCurrentDir.getCurrentDirectory()+"\\" + super.getClass().getSimpleName()+ ".png"));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Screen was not captured");
		}
		
	}
	
	public boolean isTextPresent(String name) throws ScreenException{
//		String sourceCode=driver.getPageSource();
		System.out.println("-------textvalue"+name);
		WebElement ele=driver.findElement(getTagByValue("body"));
		String name1=ele.getText();
		String[] name2=name1.split(",");
		
		for(String name3:name2){
			name3.trim();			
			System.out.println("pagesource code---------->"+name3);
			if(name3.endsWith(name)){
				System.out.println("-------------BREAK-------------");
				break;
			}
			
		}
		return true;
	}
	
		
		
		
		
		
	}


