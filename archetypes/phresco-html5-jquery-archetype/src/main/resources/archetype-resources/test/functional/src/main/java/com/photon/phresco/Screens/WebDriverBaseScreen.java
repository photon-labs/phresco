/*
 * ###
 * PHR_jquery-widget-hw
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
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
