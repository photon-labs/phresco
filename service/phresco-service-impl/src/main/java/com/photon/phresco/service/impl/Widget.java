package com.photon.phresco.service.impl;

import java.io.Serializable;

public class Widget implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

    public Widget()
    {
    }

    public Widget( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

}
