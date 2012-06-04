package com.photon.phresco.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class L10NString implements Serializable {

    private String lang;
    private String value;

    public L10NString() {
        super();
    }

    public L10NString(String lang, String value) {
        super();
        this.lang = lang;
        this.value = value;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "L10NString [lang=" + lang + ", value=" + value + "]";
    }

}
