package com.photon.phresco.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class PhrescoExceptionAbstract extends Exception implements
		AIException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int errorNumber;

	public PhrescoExceptionAbstract() {
	}

	public PhrescoExceptionAbstract(Throwable t) {
		super(t);
	}

	public PhrescoExceptionAbstract(int pErrorNumber) {
		super("Unknown Error");
		this.errorNumber = pErrorNumber;
	}

	public PhrescoExceptionAbstract(String pErrorMessage) {
		super(pErrorMessage);
		this.errorNumber = 0;
	}

	public PhrescoExceptionAbstract(int pErrorNumber, String pErrorMessage) {
		super(pErrorMessage);
		this.errorNumber = pErrorNumber;
	}

	public int getErrorNumber() {
		return this.errorNumber;
	}

	public String getErrorMessage() {
		return super.getMessage();
	}

	public String getStackError() {
		return null;
	}

	public abstract String toString();

	public String printErrorStack() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		printWriter.write("\n************* Stack Trace ***********\n");
		super.printStackTrace(printWriter);
		printWriter.write("\n*************************************\n");
		StringBuffer error = stringWriter.getBuffer();
		return error.toString();
	}
}