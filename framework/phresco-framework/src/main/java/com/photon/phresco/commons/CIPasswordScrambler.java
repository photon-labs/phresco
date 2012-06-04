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
