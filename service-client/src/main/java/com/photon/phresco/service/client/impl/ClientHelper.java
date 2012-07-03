/*
 * ###
 * Phresco Service Client
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
package com.photon.phresco.service.client.impl;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

public class ClientHelper {

	public static ClientConfig configureClient() {
		
		TrustManager[ ] certs = new TrustManager[ ] {
		    new X509TrustManager() {
		
		    	public X509Certificate[] getAcceptedIssuers() {
		    		return null;
		    	}
		    	
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					
				}
				
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
		    }
	    };
		
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(null, certs, new SecureRandom());
		} catch (java.security.GeneralSecurityException ex) {
		}
		
		HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
		ClientConfig config = new DefaultClientConfig();
		try {
			config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
					new HostnameVerifier() {
						
						public boolean verify(String hostname, SSLSession session) {
							System.out.println(hostname);
							System.out.println(session);
							return true;
						}
					}, ctx ));
		} catch(Exception e) {
			
		}
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		return config;
	}

	public static Client createClient() {
		return Client.create(ClientHelper.configureClient());
	}
}