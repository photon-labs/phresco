package com.photon.phresco.exception;

import org.apache.commons.lang.StringUtils;

public class PhrescoException extends PhrescoExceptionAbstract {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String message = null;
    
    public PhrescoException() {
    }

    public PhrescoException(Throwable t) {
        super(t);
    }

    public PhrescoException(int pErrorNumber) {
        super(pErrorNumber);
    }

    public PhrescoException(int pErrorNumber, String pErrorMessage) {
        super(pErrorNumber, pErrorMessage);
    }

    public PhrescoException(String pErrorMessage) {
        super(pErrorMessage);
    }
    
    public PhrescoException(String title, String msg){
        super(title);
        this.message = msg;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[PhrescoException]: ");
        if (super.getErrorNumber() > 0) {
            buffer.append("[ErrorNumber] = ");
            buffer.append(super.getErrorNumber());
        }
        if (StringUtils.isEmpty(message)) {
            buffer.append(" [ErrorMessage] = ");
            buffer.append(super.getErrorMessage());
        } else {
            buffer.append(message);
        }
        return buffer.toString();

    }
    
}