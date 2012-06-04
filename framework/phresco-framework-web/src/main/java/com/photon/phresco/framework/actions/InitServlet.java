package com.photon.phresco.framework.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;

public class InitServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
    	try {
    		PhrescoFrameworkFactory.getServiceManager();
    	} catch (PhrescoException e) {
			throw new ServletException(e);
		}
    }
}