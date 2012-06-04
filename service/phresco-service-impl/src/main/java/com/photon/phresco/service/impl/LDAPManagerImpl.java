/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.service.api.LDAPManager;
import com.photon.phresco.service.model.LDAPConfiguration;
import com.photon.phresco.service.model.ServerConfiguration;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.Credentials;

public class LDAPManagerImpl implements LDAPManager {
	
	private static final Logger S_LOGGER = Logger.getLogger(LDAPManagerImpl.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	private Properties serverProps;
	private LDAPConfiguration ldapConfig;

	public LDAPManagerImpl(ServerConfiguration serverConfig) throws PhrescoException {
		serverProps = serverConfig.getServerProperties();
		ldapConfig = new LDAPConfiguration(serverProps);
	}

	@Override
	public UserInfo authenticate(Credentials credentials) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method LDAPManagerImpl.authenticate(Credentials credentials)");
		}
		String userName = credentials.getUsername();
		String passwordEncoded = credentials.getPassword();
		byte[] decodedBytes = Base64.decodeBase64(passwordEncoded);
		String password = new String(decodedBytes);
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, ldapConfig.getLdapContextFactory());
		env.put(Context.PROVIDER_URL, ldapConfig.getLdapUrl());
		env.put(Context.SECURITY_PRINCIPAL, getUserPrincipal(userName));
		env.put(Context.SECURITY_CREDENTIALS, password);

		DirContext dc = null;
		try {
			dc = new InitialDirContext(env);
			if (isDebugEnabled) {
				S_LOGGER.debug("authenticate() Login Success for " + userName);
			}
			return getUserInfo(credentials, dc);
		} catch (Exception e) {
			e.printStackTrace();
			if (isDebugEnabled) {
				S_LOGGER.debug("authenticate() Login Failed for " + userName);
			}
			return new UserInfo();
		} finally {
			try {
				if (dc != null) {
					dc.close();
				}
			} catch (NamingException e) {
				throw new PhrescoException(e);
			}
		}
	}

	private String getUserPrincipal(String userName) {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method LDAPManagerImpl.getUserPrincipal(String userName)");
		}
		StringBuffer userPrincipal = new StringBuffer();
		userPrincipal.append(ldapConfig.getLdapLoginAttribute());
		userPrincipal.append(ServerConstants.STR_EQUALS);
		userPrincipal.append(userName.trim());
		userPrincipal.append(ServerConstants.STR_COMMA);
		userPrincipal.append(ldapConfig.getLdapBaseDn());
		return userPrincipal.toString();
	}

	private UserInfo getUserInfo(Credentials credentials, DirContext ctx) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method LDAPManagerImpl.getUserInfo(String userName, DirContext ctx)");
		}
		UserInfo userInfo = new UserInfo();
		try {
			String userName = credentials.getUsername();
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String[] attrIDs = { "*" };
			constraints.setReturningAttributes(attrIDs);
			NamingEnumeration<SearchResult> ne = ctx.search(ldapConfig.getLdapBaseDn(), ldapConfig
					.getLdapLoginAttribute()
					+ ServerConstants.STR_EQUALS + userName, constraints);
			if (ne.hasMore()) {
				Attributes attrs = ne.next().getAttributes();

				userInfo.setUserName(userName);
				userInfo.setCredentials(credentials);
				userInfo.setDisplayName(getDisplayName(attrs));
				userInfo.setMail(getMailId(attrs));
				userInfo.setPhrescoEnabled(isPhrescoEnabled(attrs)); 
				userInfo.setCustomerNames(getCustomerNames(attrs));
				
			}
			
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		return userInfo;
	}

	private List<String> getCustomerNames(Attributes attrs) throws NamingException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method LDAPManagerImpl.getCustomerName(Attributes attrs");
		}
		List<String> customerNames = new ArrayList<String>();
		Attribute attribute=attrs.get(ldapConfig.getCustomerNameAttribute());
		if (attribute != null) {
			NamingEnumeration<?> all = attribute.getAll();
			while (all.hasMoreElements()) {
				customerNames.add((String)all.nextElement());
			}
		}
		
		return customerNames;
	}

	private boolean isPhrescoEnabled(Attributes attrs) throws NamingException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method LDAPManagerImpl.isPhrescoEnabled(Attributes attrs");
		}
		String phrescoEnabled = "false";
		Attribute attribute=attrs.get(ldapConfig.getPhrescoEnabledAttribute());
		if (attribute != null) {
			phrescoEnabled=(String)attribute.get();
		} else {
            phrescoEnabled = "true";
        }
		return Boolean.parseBoolean(phrescoEnabled);
	}

	private String getDisplayName(Attributes attrs) throws NamingException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method LDAPManagerImpl.getDisplayName(Attributes attrs)");
		}
		String displayName = null;
		Attribute attribute = attrs.get(ldapConfig.getDisplayNameAttribute());
		if (attribute != null) {
			displayName = (String) attribute.get();
		}
		return displayName;
	}

	private String getMailId(Attributes attrs) throws NamingException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method LDAPManagerImpl.getMailId(Attributes attrs");
		}
		String mailId = null;
		Attribute attribute = attrs.get(ldapConfig.getMailIdAttribute());
		if (attribute != null) {
			mailId = (String) attribute.get();
		}
		return mailId;
	}
	
}