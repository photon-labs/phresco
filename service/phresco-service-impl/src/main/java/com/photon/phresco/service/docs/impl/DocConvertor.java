/*
 * ###
 * Phresco Service Implemenation
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
/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.docs.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.photon.phresco.exception.PhrescoException;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.document.DocumentKind;

/**
 * Doc convertor can be used to convert rtf, doc, HTML files to PDF
 *
 */
public final class DocConvertor {
	private static final Logger S_LOGGER = Logger.getLogger(DocConvertor.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();

	private DocConvertor(){

	}

	/**
	 * @param fileUrl
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static PdfInput convertToPdf(String fileUrl) throws PhrescoException{
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DocConvertor.convertToPdf(String fileUrl)");
		}
		try {
			if (isDebugEnabled) {
				S_LOGGER.debug("convertToPdf() fileUrl="+fileUrl);
			}
			if(fileUrl.endsWith(".pdf")){
				return convertPdf(fileUrl);
			} else if (fileUrl.endsWith(".rtf")){
				return convertRtfToPdf(fileUrl);
			} else if(fileUrl.endsWith(".docx")){
				return convertDocxToPDF(fileUrl);
			} else if(fileUrl.endsWith(".htm") || fileUrl.endsWith(".html")){
				return convertHtmlToPdf(fileUrl);
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		} catch (DocumentException e) {
			throw new PhrescoException(e);
		} catch (XDocConverterException e) {
			throw new PhrescoException(e);
		}
		return null;

	}

	/**
	 * @param fileUrl
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DocumentException
	 */
	private static PdfInput convertPdf(String fileUrl) throws IOException, DocumentException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method DocConvertor.convertPdf(String fileUrl)");
		}
//		PdfReader reader = new PdfReader(new FileInputStream(fileUrl));
//		int numberOfPages = reader.getNumberOfPages();
//		Document doc = new Document();
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		PdfCopy copy = new PdfCopy(doc,os);
//		doc.open();
//
//		//page number in PDF starts at 1
//		for (int i = 1; i <= numberOfPages; i++) {
//			copy.addPage(copy.getImportedPage(reader, i));
//		}
//
//		doc.close();
//		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
//		os.close();
		PdfInput input = new PdfInput();
		input.setInputStream(new FileInputStream(fileUrl));
		return input;
	}

	private static PdfInput convertRtfToPdf(String fileUrl) throws FileNotFoundException {
//		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileUrl));
//		RtfParser rtfParser = new RtfParser();

		//TODO: convert the rtf to pdf and return pdf
		return null;
	}

	private static PdfInput convertDocxToPDF(String fileUrl) throws IOException, XDocConverterException {
		if (isDebugEnabled) {
			S_LOGGER.debug("DocConvertor.convertDocxToPDF(String fileUrl)");
		}
		Options options = Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF);
		IConverter converter = ConverterRegistry.getRegistry().getConverter(options);
		if (isDebugEnabled) {
			S_LOGGER.debug("convertDocxToPDF fileUrl="+fileUrl);
		}
		InputStream in = new FileInputStream(new File(fileUrl));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		converter.convert(in, os, options);
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		os.close();
		in.close();
		PdfInput input = new PdfInput();
		input.setInputStream(is);
		return input;
	}

	private static PdfInput convertHtmlToPdf(String fileUrl) {
		// TODO Auto-generated method stub
		return null;
	}
}
