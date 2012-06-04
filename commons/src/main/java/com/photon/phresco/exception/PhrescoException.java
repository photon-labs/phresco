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