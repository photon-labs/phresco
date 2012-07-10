/*
 * ###
 * Phresco Service
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
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

package com.photon.phresco.service.converters;

import java.util.HashMap;

import com.photon.phresco.commons.model.Element;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.service.api.Converter;
import com.photon.phresco.service.dao.ApplicationTypeDAO;
import com.photon.phresco.service.dao.BaseDAO;
import com.photon.phresco.service.dao.UserDAO;

/**
 * @author kumar_s
 *
 */
public class ConvertersFactory {
	
	public static final HashMap<Class<? extends BaseDAO>, Converter<? extends BaseDAO, ? extends Element>> convertersMap = 
		new HashMap<Class<? extends BaseDAO>, Converter<? extends BaseDAO, ? extends Element>>(32);  
	
	static {
		initConverters();
	}

	private static void initConverters() {
		convertersMap.put(UserDAO.class, new UserConverter());
		convertersMap.put(ApplicationTypeDAO.class, new ApplicationTypeConverter());
	}
	
//	public static final Converter<BaseDAO, Element> getConverter(Class<? extends BaseDAO> clazz) {
//		return (Converter<BaseDAO, Element>) convertersMap.get(clazz);
//	}


	public static final Converter<? extends BaseDAO, ? extends Element> getConverter(Class<? extends BaseDAO> clazz) {
		return convertersMap.get(clazz);
	}
	
	public static void main(String[] args) {
		Converter<UserDAO, User> converter = (Converter<UserDAO, User>) ConvertersFactory.getConverter(UserDAO.class);
		System.out.println("converter.getClass().getName() :> " + converter.getClass().getName());
	}
	
}
