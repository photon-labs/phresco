package com.photon.phresco.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class SettingsPropertyTemplate implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String key;
	private I18NString name;
	private String type;
	private boolean projectSpecific;
	private boolean isRequired;
	private List<String> possibleValues;
	private I18NString description;
	private int settingsId;
	private String iName;
	private String iDesc;
	/**
	 * @return the i18Name
	 */
	public String getIName() {
		return iName;
	}

	/**
	 * @param i18Name the i18Name to set
	 */
	public void setIName(String i18Name) {
		this.iName = i18Name;
	}

	/**
	 * @return the i18Desc
	 */
	public String getIDesc() {
		return iDesc;
	}

	/**
	 * @return the iName
	 */
	public String getiName() {
		return iName;
	}

	/**
	 * @param iName the iName to set
	 */
	public void setiName(String iName) {
		this.iName = iName;
	}

	/**
	 * @return the iDesc
	 */
	public String getiDesc() {
		return iDesc;
	}

	/**
	 * @param iDesc the iDesc to set
	 */
	public void setiDesc(String iDesc) {
		this.iDesc = iDesc;
	}

	/**
	 * @param i18Desc the i18Desc to set
	 */
	public void setIDesc(String i18Desc) {
		this.iDesc = i18Desc;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the settingsId
	 */
	public int getSettingsId() {
		return settingsId;
	}

	/**
	 * @param settingsId the settingsId to set
	 */
	public void setSettingsId(int settingsId) {
		this.settingsId = settingsId;
	}

	public SettingsPropertyTemplate () {
		super();
	}

	public SettingsPropertyTemplate(String key, String type) {
		super();
		this.key = key;
		this.type = type;
	}

	public SettingsPropertyTemplate(String key, String type, boolean projectSpecific,
			boolean isRequired) {
		super();
		this.key = key;
		this.type = type;
		this.projectSpecific = projectSpecific;
		this.isRequired = isRequired;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public I18NString getName() {
		return name;
	}

	public void setName(I18NString name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isProjectSpecific() {
		return projectSpecific;
	}

	public void setProjectSpecific(boolean projectSpecific) {
		this.projectSpecific = projectSpecific;
	}

	public List<String> getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(List<String> possibleValues) {
		this.possibleValues = possibleValues;
	}

	public I18NString getDescription() {
		return description;
	}

	public void setDescription(I18NString label) {
		this.description = label;
	}

	public boolean getIsRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Override
	public String toString() {
		return "PropertyTemplate [key=" + key + ", name=" + name + ", type="
				+ type + ", projectSpecific=" + projectSpecific
				+ ", isRequired=" + isRequired + ", possibleValues="
				+ possibleValues + ", description=" + description + "]";
	}

}
