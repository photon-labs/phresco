package com.photon.phresco.service.admin.actions.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.photon.phresco.service.admin.actions.ServiceBaseAction;

public class CustomerList extends ServiceBaseAction { 
	
	private static final long serialVersionUID = 6801037145464060759L;
	private static final Logger S_LOGGER = Logger.getLogger(CustomerList.class);
	
	private String name = null;
	private String nameError = null;
	private String email = null;
	private String mailError = null;
	private String address = null;
	private String addressError = null;
	private String zipcode = null;
	private String zipError = null;
	private String number = null;
	private String numError = null;
	private String fax = null;
	private String faxError = null;
	private String country = null;
	private String conError = null;
	private String licence = null;
	private String licenError = null;
	private boolean errorFound = false;

	public String list() {
		S_LOGGER.debug("Entering Method  CustomerList.list()");
		
		return ADMIN_CUSTOMER_LIST;	
	}
	
	public String add() {
		S_LOGGER.debug("Entering Method  CustomerList.add()");
		
		return ADMIN_CUSTOMER_ADD;
	}
	
	public String save() {
		S_LOGGER.debug("Entering Method  CustomerList.save()");
		
		if (validateForm()) {
			setErrorFound(true);
			return SUCCESS;
		}
		return  ADMIN_CUSTOMER_LIST;
	}
	
	private boolean validateForm() {
		boolean isError = false;
		if (StringUtils.isEmpty(name)) {
			setNameError(getText(KEY_I18N_ERR_NAME_EMPTY));
			isError = true;
		} 
		
		if (StringUtils.isEmpty(email)) {
			setMailError(getText(KEY_I18N_ERR_EMAIL_EMPTY));
			isError = true;
		} 
		
		if (StringUtils.isEmpty(address)) {
			setAddressError(getText(KEY_I18N_ERR_ADDRS_EMPTY));
			isError = true;
		} 
		
		if (StringUtils.isEmpty(zipcode)) {
			setZipError(getText(KEY_I18N_ERR_ZIPCODE_EMPTY));
			isError = true;
		} 
		
		if (StringUtils.isEmpty(number)) {
			setNumError(getText(KEY_I18N_ERR_CONTNUM_EMPTY));
			isError = true;
		} 
		
		if (StringUtils.isEmpty(fax)) {
			setFaxError(getText(KEY_I18N_ERR_FAXNUM_EMPTY));
			isError = true;
		} 
		
		if (StringUtils.isEmpty(country)) {
			setConError(getText(KEY_I18N_ERR_COUN_EMPTY));
			isError = true;
		} 
		
		if (StringUtils.isEmpty(licence)) {
			setLicenError(getText(KEY_I18N_ERR_LICEN_EMPTY));
			isError = true;
		} 
		return isError;
	}
	
	public String cancel() {
		S_LOGGER.debug("Entering Method  CustomerList.cancel()");
		
		return ADMIN_CUSTOMER_CANCEL;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameError() {
		return nameError;
	}
	
	public void setNameError(String nameError) {
		this.nameError = nameError;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMailError() {
		return mailError;
	}

	public void setMailError(String mailError) {
		this.mailError = mailError;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressError() {
		return addressError;
	}

	public void setAddressError(String addressError) {
		this.addressError = addressError;
	}
	
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getZipError() {
		return zipError;
	}

	public void setZipError(String zipError) {
		this.zipError = zipError;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumError() {
		return numError;
	}

	public void setNumError(String numError) {
		this.numError = numError;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFaxError() {
		return faxError;
	}

	public void setFaxError(String faxError) {
		this.faxError = faxError;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getConError() {
		return conError;
	}

	public void setConError(String conError) {
		this.conError = conError;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getLicenError() {
		return licenError;
	}

	public void setLicenError(String licenError) {
		this.licenError = licenError;
	}
	
	public boolean isErrorFound() {
		return errorFound;
	}
	
	public void setErrorFound(boolean errorFound) {
		this.errorFound = errorFound;
	}
	
}

