package com.photon.phresco.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;

public class InitializeService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			PhrescoServerFactory.initialize();
			PhrescoServerFactory.getRepositoryManager().getApplicationTypes();
//			List<ApplicationType> applicationTypes = PhrescoServerFactory.getDBManager().getApplicationTypes();
//			System.out.println("applicationTypes " + applicationTypes);
		} catch (PhrescoException e) {
			throw new ServletException(e);
		}
		new VideoDownloader().start();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
	}
	
}
