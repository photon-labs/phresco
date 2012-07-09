/**
 * 
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
