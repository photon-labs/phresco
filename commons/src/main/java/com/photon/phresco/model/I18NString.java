package com.photon.phresco.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.photon.phresco.util.SizeConstants;

@XmlRootElement
public class I18NString implements Serializable {

    private Map<String, L10NString> valuesMap = new HashMap<String, L10NString>(SizeConstants.SIZE_VALUES_MAP);

    public Map<String, L10NString> getValuesMap() {
        return valuesMap;
    }

    public void setValuesMap(Map<String, L10NString> valuesMap) {
        this.valuesMap = valuesMap;
    }

    public L10NString get(String lang) {
        return valuesMap.get(lang);
    }

    public void add(L10NString value) {
        valuesMap.put(value.getLang(), value);
    }

    @Override
    public String toString() {
        return "I18NString [values=" + valuesMap + "]";
    }

}
