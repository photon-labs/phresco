package com.photon.phresco.commons;

public class CIJobStatus {
    int code;
    String message;
    
    public CIJobStatus() {
        super();
    }
    
    public CIJobStatus(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
