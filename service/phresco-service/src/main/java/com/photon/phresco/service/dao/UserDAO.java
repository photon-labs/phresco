/*
 * ###
 * Phresco Service
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

package com.photon.phresco.service.dao;

import java.util.List;

public class UserDAO extends BaseDAO {

	String userId;
	String email;
	String firstName;
	String lastName;
	int status;

	List<String> roleIds;
	List<String> customerIds;

	public UserDAO() {
		super();
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<String> getRoleIds() {
		return roleIds;
	}
	
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	
	public List<String> getCustomerIds() {
		return customerIds;
	}
	
	public void setCustomerIds(List<String> customerIds) {
		this.customerIds = customerIds;
	}
	
	@Override
	public String toString() {
		return "UserDAO [id=" + id + ", userId=" + userId + ", email=" + email
				+ ", roleIds=" + roleIds + ", customerIds=" + customerIds + "]";
	}
}