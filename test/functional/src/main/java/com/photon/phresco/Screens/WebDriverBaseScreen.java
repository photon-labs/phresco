package com.photon.phresco.Screens;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
