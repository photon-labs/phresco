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