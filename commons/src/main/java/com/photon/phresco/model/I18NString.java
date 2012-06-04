/*
 * ###
 * Phresco Commons
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
