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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.Documentation.DocumentationType;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.EntityType;

/**
 * Document Util to process PDF documents
 *
 */
public final class DocumentUtil {
	private static final Logger S_LOGGER = Logger.getLogger(DocumentUtil.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	private static String coreModule = "COREMODULE";
    private static String externalModule = "EXTERNALMODULE";
    private DocumentUtil(){

    }


    /**
     * Adds title section.
     * @param info the project info object
     * @return PDF input stream
     * @throws DocumentException
     */
    public static InputStream getTitleSection(ProjectInfo info) throws DocumentException{
    	if (isDebugEnabled) {
    		S_LOGGER.debug(" Entering Method DocumentUtil.getTitleSection(ProjectInfo info)");
		}
    	if (isDebugEnabled) {
    		S_LOGGER.debug("getTitleSection() projectCode="+info.getCode());
		}
        //create output stream
        com.itextpdf.text.Document docu = new com.itextpdf.text.Document();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter.getInstance(docu, os);
        docu.open();

        //add standard title section with supplied info object
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setFont(DocConstants.TITLE_FONT);
        addBlankLines(paragraph, 10);
        paragraph.add(info.getName());
        addBlankLines(paragraph, 4);
        docu.add(paragraph);
        
        paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        addBlankLines(paragraph, 10);
        String techName = info.getTechnology().getName();
    	if(info.getTechnology().getVersions() != null) {
    		paragraph.add(techName + " - " + info.getTechnology().getVersions().get(0));
    	} else {
    		paragraph.add(techName);
    	}
        docu.add(paragraph);
        
        paragraph = new Paragraph();
        addBlankLines(paragraph, 10);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(DocumentMessages.getString("Documents.version.name")+getVersion(info)); //$NON-NLS-1$
        addBlankLines(paragraph, 7);
        docu.add(paragraph);
        paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        paragraph.setFont(DocConstants.DESC_FONT);
        paragraph.setFirstLineIndent(8);
        paragraph.add(info.getDescription());
        docu.add(paragraph);

        docu.close();

        //Create an inputstream to return.
        return new ByteArrayInputStream(os.toByteArray());

    }


    /**
     * @param info
     * @return
     */
    private static String getVersion(ProjectInfo info) {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method DocumentUtil.getVersion(ProjectInfo info)");
		}
    	if (isDebugEnabled) {
    		S_LOGGER.debug("getVersion() ProjectCode="+ info.getCode());
		}
    	String version = info.getVersion();
        if(org.apache.commons.lang.StringUtils.isEmpty(version)){
            return DocumentMessages.getString("Documents.default.version"); //$NON-NLS-1$
        }
        return version;
    }

    /**
     * Creates and returns PDF input stream for the supplied string.
     * @param string to be printed in the PDF
     * @return PDF input stream.
     * @throws DocumentException
     */
    public static InputStream getStringAsPDF(String string) throws DocumentException{
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method DocumentUtil.getStringAsPDF(String string)");
		}
    	com.itextpdf.text.Document docu = new com.itextpdf.text.Document();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter.getInstance(docu, os);
        docu.open();
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setFirstLineIndent(180);
        paragraph.add("\n"); //$NON-NLS-1$
        paragraph.add(string);
        paragraph.add("\n\n"); //$NON-NLS-1$
        docu.add(paragraph);

        docu.close();

        //Create an inputstream to return.
        return new ByteArrayInputStream(os.toByteArray());

    }

    /**
     * Process tuple beans to generate Documnets for a speific entity type.
     * @param dependencyManager dependency manager
     * @param modules list of tuple beans
     * @param type Entity type
     * @return PDF input stream.
     * @throws PhrescoException
     * @throws DocumentException
     * @throws IOException
     */
    public static InputStream getDocumentStream(RepositoryManager repoManager,List<ModuleGroup> modules, EntityType type, String moduleType) throws PhrescoException, DocumentException, IOException {
        if (isDebugEnabled) {
            S_LOGGER.debug("Entering Method DocumentUtil.getDocumentStream(RepositoryManager repoManager,List<TupleBean> modules, EntityType type)");
        }
        if(modules!= null && !modules.isEmpty()){
            com.itextpdf.text.Document docu = new com.itextpdf.text.Document();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(docu, os);
            docu.open();
            if(moduleType.equals("Modules")) {
            	 List<ModuleGroup> coreModules = new ArrayList<ModuleGroup>();
                 List<ModuleGroup> externalModules = new ArrayList<ModuleGroup>();
                 for (ModuleGroup moduleGroup : modules) {
                 	if (moduleGroup.isCore()) {
                      	 coreModules.add(moduleGroup);
                 	}
                 	if (!moduleGroup.isCore()) {
                      	 externalModules.add(moduleGroup);
                 	}
                 }	
             		if (coreModules != null && CollectionUtils.isNotEmpty(coreModules) && moduleType.equals("Modules")) {
             			updateDoc(coreModules, docu, writer, coreModule);
             		}
             		if (externalModules != null && CollectionUtils.isNotEmpty(externalModules) && moduleType.equals("Modules")) {
             			updateDoc(externalModules, docu, writer, externalModule);
             		}
            }
            	if (moduleType.equals("JsLibraries")) {
            		updateDoc(modules, docu, writer, moduleType);
            	}
            docu.close();

            return new ByteArrayInputStream(os.toByteArray());
        }
        return null;
    }

    private static void updateDoc(List<ModuleGroup> modules, com.itextpdf.text.Document docu, PdfWriter writer, String moduleName)	throws DocumentException, PhrescoException, IOException {
		Paragraph para = new Paragraph();
		para.setAlignment(Element.ALIGN_CENTER);
        para.setFont(DocConstants.BODY_FONT);
        para.setFont(DocConstants.CATEGORY_FONT);
        para.add(moduleName);
        addBlankLines(para, 2);
        docu.add(para); 
        
		for (ModuleGroup tupleBean : modules) {
		    para = new Paragraph();
		    para.setFont(DocConstants.CATEGORY_FONT);
		    para.add(tupleBean.getName());
		    docu.add(para);
		    Documentation document = tupleBean.getDoc(DocumentationType.DESCRIPTION);
		    if (document != null) {
		        if(!StringUtils.isEmpty(document.getUrl())){
		            PdfInput convertToPdf = DocConvertor.convertToPdf(document.getUrl());
		            if(convertToPdf != null) {
		                DocumentUtil.addPages(convertToPdf.getInputStream(), writer, docu);
		            }
		        } else {
		            para = new Paragraph();
		            para.setFont(DocConstants.BODY_FONT);
		            para.add(document.getContent());
		            addBlankLines(para, 2);
		            docu.add(para);
		        }
		    }
		}
	}

    
    
    /**
     * Adds blank lines into the supplied paragraph.
     * @param p the Paragraph object
     * @param noOfLines no of blank lines.
     */
    private static void addBlankLines(Paragraph p, int noOfLines){
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method DocumentUtil.addBlankLines(Paragraph p, int noOfLines)");
		}
    	if (isDebugEnabled) {
    		S_LOGGER.debug("addBlankLines() No of Lines="+noOfLines);
		}
    	StringBuffer sb =new StringBuffer();
        for (int i = 0; i < noOfLines; i++) {
            sb.append("\n"); //$NON-NLS-1$
        }
        p.add(sb.toString());
    }

    /**
     * @param titleSection
     * @param pdfCopy
     * @throws IOException
     * @throws BadPdfFormatException
     */
    public static void addPages(InputStream titleSection, PdfCopy pdfCopy) throws IOException, BadPdfFormatException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method DocumentUtil.addPages(InputStream titleSection, PdfCopy pdfCopy)");
		}
    	PdfReader reader = new PdfReader(titleSection);
        reader.consolidateNamedDestinations();
        int pages = reader.getNumberOfPages();
        for (int i = 1; i <= pages; i++) {
            PdfImportedPage importedPage = pdfCopy.getImportedPage(reader, i);
            pdfCopy.addPage(importedPage);
        }
        pdfCopy.freeReader(reader);
    }


    /**
     * @param titleSection
     * @param writer
     * @param docu
     * @throws IOException
     * @throws DocumentException
     */
    public static void addPages(InputStream titleSection, PdfWriter writer, com.itextpdf.text.Document docu) throws IOException, DocumentException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method DocumentUtil.addPages(InputStream titleSection, PdfWriter writer, com.itextpdf.text.Document docu)");
		}
    	PdfReader reader = new PdfReader(titleSection);
        reader.consolidateNamedDestinations();
        PdfContentByte cb = writer.getDirectContent();

        int pages = reader.getNumberOfPages();
        for (int i = 1; i <= pages; i++) {
            PdfImportedPage importedPage = writer.getImportedPage(reader, i);
            cb.addTemplate(importedPage, 0,0);
            docu.newPage();
        }
    }


    /**
     * @param dependencyManager
     * @param tuples
     * @param type
     * @param pdfCopy
     * @return
     * @throws PhrescoException
     * @throws DocumentException
     * @throws IOException
     * @throws BadPdfFormatException
     */
    public static InputStream addPages(RepositoryManager repoManager, List<ModuleGroup> tuples, EntityType type,  PdfCopy pdfCopy, String moduleType)
            throws PhrescoException, DocumentException, IOException {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method DocumentUtil.addPages(RepositoryManager repoManager,List<TupleBean> tuples, EntityType type, PdfCopy pdfCopy)");
		}
    	InputStream addDocumentInfo = getDocumentStream(repoManager, tuples, type, moduleType);
        if(addDocumentInfo !=null) {
            addPages(addDocumentInfo, pdfCopy);
        }
        return addDocumentInfo;
    }

    /**
     * @param folder
     * @return
     */
    public static String getIndexHtml(File folder) {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method DocumentUtil.getIndexHtml(File folder)");
		}
    	if (isDebugEnabled) {
    		S_LOGGER.debug("getIndexHtml() folder="+folder.getPath());
		}
    	StringBuffer sb = new StringBuffer();
        sb.append("<html>"); //$NON-NLS-1$
        sb.append("<body>"); //$NON-NLS-1$
        sb.append("<h1>");  //$NON-NLS-1$
        sb.append(DocumentMessages.getString("Documents.document.index.title")); //$NON-NLS-1$
        sb.append("</h1>");  //$NON-NLS-1$
        listFiles(folder, sb);
        sb.append("</body>"); //$NON-NLS-1$
        sb.append("</html>"); //$NON-NLS-1$
        return sb.toString();
    }

    private static void listFiles(File file,StringBuffer sb) {
    	if (isDebugEnabled) {
    		S_LOGGER.debug("Entering Method DocumentUtil.listFiles(File file,StringBuffer sb)");
		}
    	String[] list = file.list();
        sb.append("<ul>"); //$NON-NLS-1$
        for (String fileOrFolder : list) {
            File newFile = new File(file.toString()+File.separator+fileOrFolder);
            if(newFile.isHidden()) continue;
            /*if(newFile.isDirectory()){
                sb.append("<li>"); //$NON-NLS-1$
                sb.append("<a href=./"); //$NON-NLS-1$
                sb.append(newFile.getName());
                sb.append("\">"); //$NON-NLS-1$
                sb.append(newFile.getPath());
                sb.append("</a>"); //$NON-NLS-1$
                sb.append("</li>"); //$NON-NLS-1$
                listFiles(newFile, sb);
            } else {*/
                sb.append("<li>"); //$NON-NLS-1$
                sb.append("<a href="+"\""+"./"); //$NON-NLS-1$
                sb.append(newFile.getName());
                sb.append("\">"); //$NON-NLS-1$
                sb.append(newFile.getName());
                sb.append("</a>"); //$NON-NLS-1$
                sb.append("</li>"); //$NON-NLS-1$
//            }
        }
        sb.append("</ul>"); //$NON-NLS-1$
    }
}
