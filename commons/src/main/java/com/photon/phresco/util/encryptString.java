/*
 * ###
 * Phresco Commons
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
package com.photon.phresco.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class encryptString {
	// iv length should be 16 bytes
	private String iv = "fedcba9876543210";
	private String key = null;
	private Cipher cipher = null;
	private SecretKeySpec keySpec = null;
	private IvParameterSpec ivSpec = null;

	/**
	 * Constructor
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public void Crypto(String key) throws Exception {
		this.key = key;

		// Make sure the key length should be 16
		int len = this.key.length();
		if (len < 16) {
			int addSpaces = 16 - len;
			for (int i = 0; i < addSpaces; i++) {
				this.key = this.key + " ";
			}
		} else {
			this.key = this.key.substring(0, 16);
		}
		this.keySpec = new SecretKeySpec(this.key.getBytes(), "AES");
		this.ivSpec = new IvParameterSpec(iv.getBytes());
		this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
	}

	/**
	 * Bytes to Hexa conversion
	 * 
	 * @param data
	 * @return
	 */
	public String bytesToHex(byte[] data) {
		if (data == null) {
			return null;
		} else {
			int len = data.length;
			String str = "";
			for (int i = 0; i < len; i++) {
				if ((data[i] & 0xFF) < 16)
					str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
				else
					str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
			}
			return str;
		}
	}

	/**
	 * Encrpt the given string
	 * 
	 * @param plainData
	 * @throws Exception
	 */
	public String encrypt(String plainData) throws Exception {

		// Make sure the plainData length should be multiple with 16
		int len = plainData.length();
		int q = len / 16;
		int addSpaces = ((q + 1) * 16) - len;
		for (int i = 0; i < addSpaces; i++) {
			plainData = plainData + " ";
		}

		this.cipher.init(Cipher.ENCRYPT_MODE, this.keySpec, this.ivSpec);
		byte[] encrypted = cipher.doFinal(plainData.getBytes());

		return bytesToHex(encrypted);
	}
}