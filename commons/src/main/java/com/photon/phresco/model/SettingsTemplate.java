package com.photon.phresco.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.photon.phresco.util.SizeConstants;

@SuppressWarnings("restriction")
@XmlRootElement
public class SettingsTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Database, Server, Email
    private String type;
    //List of properties available for the template
    private List<PropertyTemplate> properties = new ArrayList<PropertyTemplate>(SizeConstants.SIZE_PROPERTIES_MAP);
    //List of technology ids
    private List<String> appliesTo;

    public SettingsTemplate() {
        super();
    }

    public SettingsTemplate(String type, List<PropertyTemplate> properties,
            List<String> appliesTo) {
        this.type = type;
        this.properties = properties;
        this.appliesTo = appliesTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PropertyTemplate> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyTemplate> properties) {
        this.properties = properties;
    }

    public List<String> getAppliesTo() {
        return appliesTo;
    }

    public void setAppliesTo(List<String> appliesTo) {
        this.appliesTo = appliesTo;
    }

    @Override
    public String toString() {
        return "SettingsTemplate [type=" + type + ", properties=" + properties
                + ", appliesTo=" + appliesTo + "]";
    }

}
