package com.photon.phresco.service.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.photon.phresco.service.admin.commons.ServiceActions;
import com.photon.phresco.service.admin.commons.ServiceUIConstants;

public class ServiceBaseAction extends ActionSupport implements ServiceActions, ServiceUIConstants {

//	private static final Logger S_LOGGER = Logger.getLogger(ServiceBaseAction.class);
    private static final long serialVersionUID = 1L;
    
    
    public HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    }
    
    public HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }
    
}
