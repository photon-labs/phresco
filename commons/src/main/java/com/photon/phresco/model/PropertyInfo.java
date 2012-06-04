package com.photon.phresco.model;

public class PropertyInfo {
	
	private String id;
    private String key;
    private String value;
    
    public PropertyInfo() {
        super();
    }
   
    public PropertyInfo(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
    public String toString() {
        return "PropertyInfo [key=" + key + ", value=" + value + "]";
    }
}
