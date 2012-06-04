/*
 * ###
 * Phresco Framework
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
package com.photon.phresco.commons;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.photon.phresco.exception.PhrescoException;
import com.trilead.ssh2.crypto.Base64;

public class CIPasswordScrambler {
    public static String mask(String password) throws PhrescoException {
        if (StringUtils.isEmpty(password)) {
            return StringUtils.EMPTY;
        }
        
        try {
            return new String(Base64.encode(password.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new PhrescoException(e);
        }
    }
    
    public static String unmask(String secret) throws PhrescoException {
        if (StringUtils.isEmpty(secret)) {
            return StringUtils.EMPTY;
        }
        
        try {
            return new String(Base64.decode(secret.toCharArray()), "UTF-8");
        } catch (IOException e) {
            throw new PhrescoException(e);
        }
    }
    
    public static void main(String a[]) {
    	try {
			System.out.println(unmask("U2F2ZUFuaW1hbHM1"));
		} catch (PhrescoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
