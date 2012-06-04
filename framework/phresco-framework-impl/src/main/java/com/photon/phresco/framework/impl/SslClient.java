/*
 * ###
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Suspendable;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

/**
 * SslClient.java
 *
 * @author Edward Chou
 */
/**
 * @author suresh_ma
 *
 */
public class SslClient {
	
	public static String path;
    
    private static final String truststore_path = "D:/work/projects/phresco/trunk/framework/trunk/phresco-framework-runner/delivery/bin/phresco.jks";
    private static final String truststore_password = "photon";
    private static final String keystore_path = "D:/work/projects/phresco/trunk/framework/trunk/phresco-framework-runner/delivery/bin/phresco.jks";
    private static final String keystore_password = "photon";
    private static final String url = "https://localhost:8443";
    public static String public_key;
    public static String public_key1;
    public static void main(String[] args) {
        
        try {
            TrustManager mytm[] = null;
            KeyManager mykm[] = null;

            try {
                
                mytm = new TrustManager[]{new MyX509TrustManager(truststore_path, truststore_password.toCharArray())};
                mykm = new KeyManager[]{new MyX509KeyManager(keystore_path, keystore_password.toCharArray())};
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

            SSLContext context = null;

            try {
                context = SSLContext.getInstance("TLS");
                context.init(mykm, mytm, null);
                
            } catch (NoSuchAlgorithmException nae) {
                System.out.println(nae);
            } catch (KeyManagementException kme) {
                System.out.println(kme);
            }
            
            HTTPSProperties prop = new HTTPSProperties(
            
            new HostnameVerifier () {

                public boolean verify(String hostname, SSLSession session) {
                    System.out.println("verifying: " + hostname);
                   
                    return true;
                }
                
            }
            
            , context);
            
            UriBuilder uriBuilder = UriBuilder.fromUri(url);
            
            ClientRequest.Builder requestBuilder = ClientRequest.create();
            
            requestBuilder.type("application/xml");
            requestBuilder.accept("application/xml");
            requestBuilder.entity("Hello World SSL");
            
            ClientRequest clientRequest = requestBuilder.build(uriBuilder.build(), "POST");
            
            clientRequest.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, prop);

            Client client = ClientHelper.createClient();
            ClientResponse response = client.handle(clientRequest);
            
            
            String result = response.getEntity(String.class);
            
            
            System.out.println("response = " + result);
            
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }     
    }
    
    /**
     * Taken from http://java.sun.com/javase/6/docs/technotes/guides/security/jsse/JSSERefGuide.html
     *
     */
    static class MyX509TrustManager implements X509TrustManager {

         /*
          * The default PKIX X509TrustManager9.  We'll delegate
          * decisions to it, and fall back to the logic in this class if the
          * default X509TrustManager doesn't trust it.
          */
         X509TrustManager pkixTrustManager;

         MyX509TrustManager(String trustStore, char[] password) throws Exception {
             this(new File(trustStore), password);
         }

         MyX509TrustManager(File trustStore, char[] password) throws Exception {
             // create a "default" JSSE X509TrustManager.

             KeyStore ks = KeyStore.getInstance("JKS");

             
             
             ks.load(new FileInputStream(trustStore), password);

             TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
             tmf.init(ks);
             
             //System.out.println(ks.getCertificate("phresco").getPublicKey());
             System.out.println(ks.getCertificate("key"));
             public_key = ks.getCertificate("key").getPublicKey().toString();
             
             
                          
             //System.out.println(ks.containsAlias("phresco"));

             TrustManager tms [] = tmf.getTrustManagers();
             

             /*
              * Iterate over the returned trustmanagers, look
              * for an instance of X509TrustManager.  If found,
              * use that as our "default" trust manager.
              */
             for (int i = 0; i < tms.length; i++) {
                 if (tms[i] instanceof X509TrustManager) {
                     pkixTrustManager = (X509TrustManager) tms[i];
                     return;
                 }
             }

             /*
              * Find some other way to initialize, or else we have to fail the
              * constructor.
              */
             throw new Exception("Couldn't initialize");
         }

         /*
          * Delegate to the default trust manager.
          */
         public void checkClientTrusted(X509Certificate[] chain, String authType)
                     throws CertificateException {
             try {
                 pkixTrustManager.checkClientTrusted(chain, authType);
             } catch (CertificateException excep) {
                 // do any special handling here, or rethrow exception.
             }
         }

         /*
          * Delegate to the default trust manager.
          */
         public void checkServerTrusted(X509Certificate[] chain, String authType)
                     throws CertificateException {
             try {
                 pkixTrustManager.checkServerTrusted(chain, authType);
             } catch (CertificateException excep) {
                 /*
                  * Possibly pop up a dialog box asking whether to trust the
                  * cert chain.
                  */
             }
         }

         /*
          * Merely pass this through.
          */
         public X509Certificate[] getAcceptedIssuers() {
             return pkixTrustManager.getAcceptedIssuers();
         }
    }

    /**
     * Inspired from http://java.sun.com/javase/6/docs/technotes/guides/security/jsse/JSSERefGuide.html
     *
     */
    static class MyX509KeyManager implements X509KeyManager {

         /*
          * The default PKIX X509KeyManager.  We'll delegate
          * decisions to it, and fall back to the logic in this class if the
          * default X509KeyManager doesn't trust it.
          */
         X509KeyManager pkixKeyManager;

         MyX509KeyManager(String keyStore, char[] password) throws Exception {
             this(new File(keyStore), password);
         }

         MyX509KeyManager(File keyStore, char[] password) throws Exception {
             // create a "default" JSSE X509KeyManager.

             KeyStore ks = KeyStore.getInstance("JKS");
             ks.load(new FileInputStream(keyStore), password);

             public_key1 = ks.getCertificate("key").getPublicKey().toString();
             
             System.out.println(public_key1);
             if(public_key.equals(public_key1)){
            	 System.out.println("true");
             }else{
            	 System.out.println("false");
             }
             System.out.println("--------------------------------------------------------------------");
            // System.out.println(ks.getCertificate("phresco"));
             KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509", "SunJSSE");
             kmf.init(ks, password);
             

             KeyManager kms[] = kmf.getKeyManagers();

             /*
              * Iterate over the returned keymanagers, look
              * for an instance of X509KeyManager.  If found,
              * use that as our "default" key manager.
              */
             for (int i = 0; i < kms.length; i++) {
                 if (kms[i] instanceof X509KeyManager) {
                     pkixKeyManager = (X509KeyManager) kms[i];
                     return;
                 }
             }

             /*
              * Find some other way to initialize, or else we have to fail the
              * constructor.
              */
             throw new Exception("Couldn't initialize");
         }

        public PrivateKey getPrivateKey(String arg0) {
            return pkixKeyManager.getPrivateKey(arg0);
        }

        public X509Certificate[] getCertificateChain(String arg0) {
            return pkixKeyManager.getCertificateChain(arg0);
        }

        public String[] getClientAliases(String arg0, Principal[] arg1) {
            return pkixKeyManager.getClientAliases(arg0, arg1);
        }

        public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
            return pkixKeyManager.chooseClientAlias(arg0, arg1, arg2);
        }

        public String[] getServerAliases(String arg0, Principal[] arg1) {
            return pkixKeyManager.getServerAliases(arg0, arg1);
        }

        public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
            return pkixKeyManager.chooseServerAlias(arg0, arg1, arg2);
        }
    }
}