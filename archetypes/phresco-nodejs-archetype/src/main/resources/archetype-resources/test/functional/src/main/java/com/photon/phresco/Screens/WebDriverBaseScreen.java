package com.photon.phresco.Screens;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Function;
import com.photon.phresco.selenium.util.ScreenException;

public class WebDriverBaseScreen extends WebDriverAbstractBaseScreen {
	
	private Log log = LogFactory.getLog(getClass());
	protected WebDriverBaseScreen() throws ScreenException{
		if(element==null){
			throw new ScreenException("********The element value should not be null********");
		}
	}
	
	public void click()throws ScreenException{
		log.info("Entering:********click operation start********");
	try{
		element.click();
	}catch(Throwable t){
		t.printStackTrace();
	}
		log.info("Entering:********click operation end********");
		
	}
	public void clear()throws ScreenException{
		log.info("Entering:********clear operation start********");
		try{
		element.clear();
		}catch(Throwable t){
			t.printStackTrace();
		}
		log.info("Entering:********clear operation end********");
		
	}
	public void type(String text)throws ScreenException{
		log.info("Entering:********enterText operation start********");
		try{
			clear();
			element.sendKeys(text);
			
			
		}catch(Throwable t){
			t.printStackTrace();
		}
		log.info("Entering:********enterText operation end********");
	}
	public void submit()throws ScreenException{
		log.info("Entering:********submit operation start********");
		try{
			element.submit();
		}catch(Throwable t){
			t.printStackTrace();
		}
		log.info("Entering:********submit operation end********");
		
	}
	
	
	public void selectListItem(String xpath,String valueToSelect){
		
		WebElement selectElement = driver.findElement(By.xpath(xpath));

		// Then instantiate the Select class with that WebElement
		Select select = new Select(selectElement);

		// Get a list of the options
		List<WebElement> options = select.getOptions();

		// For each option in the list, verify if it's the one you want and then click it
		for (WebElement we : options) {
		if (we.getText().equals(valueToSelect)) {
		we.click();
		//break;
		}
		}

		}
	
	public void accept() throws InterruptedException{
		
	//	String parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
	      WebDriver popup = null;
	      //String windowname=driver.getWindowHandle();
	      	Set<String> windowname=driver.getWindowHandles();
	      	Iterator<String> window=windowname.iterator();
	      
	      while(window.hasNext()) { 
	        String windowHandle = window.next();	      
	        popup = driver.switchTo().window(windowHandle); 
//	        driver.switchTo().defaultContent().switchTo().frame("windowHandle");

	        System.out.println("------------------POP-UP WINDOW NAME---->"+popup.getTitle().toString());
	        if (popup.getTitle().toString().equalsIgnoreCase("")) {
	        	  popup.findElement(By.name("OK")).click();
	        	  Thread.sleep(5000);
	          break;
	        }
	      }
	}
	
	/*public void accept2() throws InterruptedException{
		String name=driver.getWindowHandle();
		
		WebDriver windowname=driver.switchTo().window("Opening barcode-labels");
		System.out.println("--------------Window name--------->"+windowname.getTitle());
		windowname.findElement(By.name("Cancel"));
		Thread.sleep(5000);
	}*/

		
	
	
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

