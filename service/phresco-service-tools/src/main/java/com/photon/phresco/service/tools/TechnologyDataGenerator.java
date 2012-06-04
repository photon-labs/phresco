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
package com.photon.phresco.service.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.plexus.util.StringUtils;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.DocumentTypes;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.jaxb.ArchetypeInfo;
import com.photon.phresco.service.jaxb.Document;
import com.photon.phresco.service.jaxb.Documents;
import com.photon.phresco.service.jaxb.Library;
import com.photon.phresco.service.jaxb.Module;
import com.photon.phresco.service.jaxb.Modules;
import com.photon.phresco.service.jaxb.ObjectFactory;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.service.model.DocumentType;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.TechnologyTypes;


public class TechnologyDataGenerator implements ServerConstants {

	private static final Logger S_LOGGER = Logger
			.getLogger(TechnologyDataGenerator.class);
	private File inputRootDir;
	private File outputRootDir;
	private File moduleBinariesDir;
	private static final String SHEET_NAME_MODULE = "Modules";
	private static final int NO_OF_ROWS_TO_SKIP = 1;
	private static final String DELIMITER = ",";
	private static final String ID = "mod_";
	static final Map<String, String> INPUT_EXCEL_MAP = new HashMap<String, String>(16);
	static final Map<String, File> CONTENTS_HOME_MAP = new HashMap<String, File>(16);
	static final Map<String, String> SNO_AND_NAME_MAP = new HashMap<String, String>(16);
	static final Map<String, String[]> NAME_AND_DEP_MAP = new HashMap<String, String[]>(16);

	RepositoryManager repManager = null;
	File outFile = null;
	List <DocumentTypes>  documentTypes  = null;

	static {
		initInputExcelMap();
	}
	
    public TechnologyDataGenerator(File inputRootDir, File outputRootDir, File binariesDir) throws PhrescoException {
        this.inputRootDir = inputRootDir;
        this.outputRootDir = outputRootDir;
        this.moduleBinariesDir = new File(binariesDir, "/technologies/");
        PhrescoServerFactory.initialize();
        this.repManager = PhrescoServerFactory.getRepositoryManager();
        initContentsHomeMap();
    }

	
	private static void initInputExcelMap() {
		INPUT_EXCEL_MAP.put(TechnologyTypes.PHP,"PHTN_PHRESCO_PHP.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.JAVA_WEBSERVICE,"PHTN_PHRESCO_Java-WebService.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.PHP_DRUPAL7,"PHTN_PHRESCO_Drupal7.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.SHAREPOINT,"PHTN_PHRESCO_Sharepoint.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.ANDROID_NATIVE,"PHTN_PHRESCO_Andriod-Native.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.IPHONE_NATIVE,"PHTN_PHRESCO_iPhone-Native.xls");
	    INPUT_EXCEL_MAP.put(TechnologyTypes.NODE_JS_WEBSERVICE,"PHTN_PHRESCO_NodeJS-WebService.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.ANDROID_HYBRID,"PHTN_PHRESCO_Andriod-Hybrid.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.WORDPRESS, "PHTN_PHRESCO_Wordpress.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.PHP_DRUPAL6, "PHTN_PHRESCO_Drupal6.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.PHP_DRUPAL6, "PHTN_PHRESCO_Drupal6.3.xls");
	}

	private  void initContentsHomeMap() {

		CONTENTS_HOME_MAP.put(TechnologyTypes.PHP,new File(moduleBinariesDir, "php"));
		CONTENTS_HOME_MAP.put(TechnologyTypes.PHP_DRUPAL7, new File(moduleBinariesDir, "drupal"));
		CONTENTS_HOME_MAP.put(TechnologyTypes.SHAREPOINT, new File(moduleBinariesDir, "sharepoint"));
		CONTENTS_HOME_MAP.put(TechnologyTypes.ANDROID_NATIVE, new File(moduleBinariesDir, "android-native"));
		CONTENTS_HOME_MAP.put(TechnologyTypes.IPHONE_NATIVE, new File(moduleBinariesDir, "iphone-native"));
		CONTENTS_HOME_MAP.put(TechnologyTypes.NODE_JS_WEBSERVICE, new File(moduleBinariesDir, "nodejs-webservice"));
		CONTENTS_HOME_MAP.put(TechnologyTypes.ANDROID_HYBRID, new File(moduleBinariesDir, "android-hybrid"));
		CONTENTS_HOME_MAP.put(TechnologyTypes.WORDPRESS, new File(moduleBinariesDir, "wordpress"));
		CONTENTS_HOME_MAP.put(TechnologyTypes.PHP_DRUPAL6, new File(moduleBinariesDir, "drupal6"));
	}


	public void publish() throws PhrescoException {
		System.out.println("Uploading Files......");
		generateAll();
		System.out.println("Uploaded successfully.....");
	}

	private  File getExcelFile(String techType) throws PhrescoException {
		String fileName = INPUT_EXCEL_MAP.get(techType);
		if (fileName == null) {
			throw new PhrescoException("No file defined for " + techType);
		}

		return new File(this.inputRootDir, fileName);
	}

	public void generateModules(HSSFWorkbook workBook, String tech)
			throws PhrescoException {
		System.out.println("Generating modules for " + tech);
		Modules modules = handleDependencies(createModules(tech, workBook));
		writeTo(modules, tech);
		System.out.println("Uploading Modules XML File For" + tech);
		addModuleXml(tech);
		System.out.println("Modules generated successfully for " + tech);

		List <com.photon.phresco.service.jaxb.Module>moduleList = modules.getModule();
		List <com.photon.phresco.model.Modules> modulesDataList = new ArrayList <com.photon.phresco.model.Modules> ();
		for (com.photon.phresco.service.jaxb.Module tempModule:moduleList) {
			com.photon.phresco.model.Modules modulesData = new com.photon.phresco.model.Modules ();
			modulesData.setModuleId(tempModule.getId());
			modulesDataList.add(modulesData);
		}
	}

	private Modules createModules(String tech, HSSFWorkbook  workBook)
			throws PhrescoException {
		HSSFSheet sheet = workBook.getSheet(SHEET_NAME_MODULE);
		Modules modules = new Modules();
		Iterator<Row> rowsIter = sheet.rowIterator();
		// Skipping first row
		for (int i = 0; i < NO_OF_ROWS_TO_SKIP; i++) {
			rowsIter.next();
		}
		while (rowsIter.hasNext()) {
			Row row = rowsIter.next();
			Module module = createModule(tech, row);
			if (module != null) {
				modules.getModule().add(module);
			}
		}
		return modules;
	}

	/**
	 * Handle dependencies.
	 *
	 * @param modules
	 *            the modules
	 * @return the modules
	 */
	private Modules handleDependencies(Modules modules) {
		Modules modifiedModules = new Modules();
		List<Module> moduleList = modules.getModule();
		for (Module module : moduleList) {
			List<String> depModNameList = new ArrayList<String>();
			String modName = module.getId();
			String[] depList = NAME_AND_DEP_MAP.get(modName);
			if (depList == null) {
				modifiedModules.getModule().add(module);
				continue;
			}
			for (String dep : depList) {
				int depNo = (int) (Double.valueOf(dep).doubleValue());
				String depModName = SNO_AND_NAME_MAP.get(String.valueOf(depNo));
				depModNameList.add(depModName);
			}

			if (!depModNameList.isEmpty()) {
				module.getDependentModules().addAll(depModNameList);
			}

			modifiedModules.getModule().add(module);
		}
		return modifiedModules;
	}

	private Module createModule(String tech, Row row) throws PhrescoException {
		if (S_LOGGER.isDebugEnabled()) {
			S_LOGGER.debug("row " + row.getRowNum());
		}
		Cell serialNo = row.getCell(0);
		if (serialNo == null || Cell.CELL_TYPE_BLANK == serialNo.getCellType()) {
			return null;
		}

		String name = row.getCell(1).getStringCellValue();
		String version = "1.0";
		Cell versionCell = row.getCell(2);
		if (versionCell == null) {
			System.out.println("Version is null for " + name);
		} else {
			version = getValue(versionCell);
			if (version == null) {
				System.out.println("Version is null for " + name);
				version = "1.0";
			}
		}

		String identifier = ID
				+ row.getCell(1).getStringCellValue().toLowerCase()
						.replace(STR_BLANK_SPACE, STR_UNDER_SCORE)
				+ STR_UNDER_SCORE + version;
		String no = String.valueOf(identifier);
		Cell requireds = row.getCell(3);
		String strRequired = getValue(requireds);
		Boolean required = convertBoolean(strRequired);

		Cell cores = row.getCell(4);
		Boolean core = convertBoolean(getValue(cores));
		Module module = createModule(no, name, version, required, core);

		int sNoInt = (int) serialNo.getNumericCellValue();
		SNO_AND_NAME_MAP.put(String.valueOf(sNoInt), identifier);

		Cell depCell = row.getCell(5);
		if (depCell != null && Cell.CELL_TYPE_BLANK != depCell.getCellType()) {
			String depModules = getValue(depCell);
			String[] depModuleIds = StringUtils.split(depModules,DELIMITER);
			NAME_AND_DEP_MAP.put(identifier, depModuleIds);
		}
		Documents documents = new Documents();
		Cell helptext = row.getCell(9);
		if (helptext != null && Cell.CELL_TYPE_BLANK != helptext.getCellType()) {
			Document doc = new Document();
			if (S_LOGGER.isDebugEnabled()) {
				S_LOGGER.debug("no " + no);
				S_LOGGER.debug("helptext.getCellType() "
						+ helptext.getCellType());
			}
			String docContent = helptext.getStringCellValue();
			String documentType = DocumentType.HELP_TEXT.name();
			doc.setContent(docContent);
			doc.setDocumentType(documentType);
			documents.getDocument().add(doc);
		}
		
		Cell description = row.getCell(12);
        if (description != null && Cell.CELL_TYPE_BLANK != description.getCellType()) {
            Document doc = new Document();
            String docContent = description.getStringCellValue();
            String documentType = DocumentType.DESCRIPTION.name();
            doc.setContent(docContent);
            doc.setDocumentType(documentType);
            documents.getDocument().add(doc);
            
        }
        module.setDocuments(documents);
        
		Cell filenameCell = row.getCell(13);
		if (filenameCell != null
				&& Cell.CELL_TYPE_BLANK != filenameCell.getCellType()) {
			if (S_LOGGER.isDebugEnabled()) {
				S_LOGGER.debug("no " + no);
				S_LOGGER.debug("filename.getCellType() "
						+ filenameCell.getCellType());
			}

			String filePath = filenameCell.getStringCellValue().trim();

			String fileExt = "zip";
			if (filePath.endsWith(".tar.gz")) {
				fileExt = "tar.gz";
			} else if (filePath.endsWith(".tar")) {
				fileExt = "tar";
			} else if (filePath.endsWith(".zip")) {
				fileExt = "zip";
			} else if (filePath.endsWith(".jar")) {
				fileExt = "jar";
			}
			
			String createContentURL = createContentURL(tech, module, "."
					+ fileExt);
			module.setContentURL(createContentURL);
			System.out.println("Uploading Module : " + module.getContentURL());
			publishModule(tech, module, filePath, fileExt);
		}
		/*Cell imagenameCell = row.getCell(14);
        if (imagenameCell != null
                && Cell.CELL_TYPE_BLANK != imagenameCell.getCellType()) {
            
            String imagePath = imagenameCell.getStringCellValue().trim();
            String imageUrl = createImageContentUrl(tech,module,".");
            module.setImageURL(imageUrl);
            publishImageFile(tech, module, imagePath);
            }*/
        
		Cell groupNameCell = row.getCell(14);
		if (groupNameCell != null
				&& Cell.CELL_TYPE_BLANK != groupNameCell.getCellType()) {
			if (S_LOGGER.isDebugEnabled()) {
				S_LOGGER.debug("no " + no);
				S_LOGGER.debug("groupname.getCellType() "
						+ groupNameCell.getCellType());
			}
			String groupId = groupNameCell.getStringCellValue();
			module.setGroupId(groupId);
		}

		Cell artifactCell = row.getCell(14);
		if (artifactCell != null
				&& Cell.CELL_TYPE_BLANK != artifactCell.getCellType()) {
			if (S_LOGGER.isDebugEnabled()) {
				S_LOGGER.debug("no " + no);
				S_LOGGER.debug("artifact name.getCellType() "
						+ artifactCell.getCellType());
			}
			String artifactId = artifactCell.getStringCellValue();
			module.setArtifactId(artifactId);
		}
		return module;
	}

	private void publishImageFile(String tech, Module module, String imagePath) throws PhrescoException {
	    File root = CONTENTS_HOME_MAP.get(tech);
	    String groupId = "modules." + tech + ".files";
        File imageFile = new File(root, imagePath);
        System.out.println("Image path is" + imageFile.getPath());
        if (!imageFile.exists()) {
            System.out.println("Module not exist : " + module.getName() + " filePath: " + imagePath);
            System.out.println("artifact.toString() " + imageFile.toString());
            return;
        }
        ArtifactInfo info = new ArtifactInfo(groupId, module.getId(), "", "png", module.getVersion());
        repManager.addArtifact(info, imageFile);
        System.out.println("Module : " + module.getName() + " filePath: "   + imagePath + " added succesfully");
    }

    private String createImageContentUrl(String tech, Module module,
            String string) {
        return "/modules/" + tech + "/files/" + module.getId() + "/"+ module.getVersion() + "/" + module.getId() + "-"
        + module.getVersion() + ".png";
    }

    private Boolean convertBoolean(String strRequired) {
		if ("Yes".equalsIgnoreCase(strRequired)) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	private String createContentURL(String tech, Module module, String ext) {
		return "/modules/" + tech + "/files/" + module.getId() + "/"
				+ module.getVersion() + "/" + module.getId() + "-"
				+ module.getVersion() + ext;
	}

	private void publishModule(String tech, Module module, String fileURL,
			String ext) throws PhrescoException {
		File root = CONTENTS_HOME_MAP.get(tech);
		String groupId = "modules." + tech + ".files";
		File artifact = new File(root, fileURL);
		File pomFile = createPomFile(module, ext, groupId);
		if (!artifact.exists()) {
			System.out.println("Module not exist : " + module.getName()	+ " filePath: " + fileURL);
			System.out.println("artifact.toString() " + artifact.toString());
			return;
		}

		ArtifactInfo info = new ArtifactInfo(groupId, module.getId(), "", ext, module.getVersion());
		info.setPomFile(pomFile);
		repManager.addArtifact(info, artifact);
		pomFile.delete();
		System.out.println("Module : " + module.getName() + " filePath: "	+ fileURL + " added succesfully");
	}


	private String getValue(Cell cell) {
		if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
			return cell.getStringCellValue();
		}

		if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			return String.valueOf(cell.getNumericCellValue());
		}

		return null;
	}

	private Module createModule(String id, String mod, String version,
			Boolean required, Boolean core) {
		Module module = new Module();
		module.setId(id);
		module.setName(mod);
		module.setVersion(version);
		module.setRequired(required);
		module.setCore(core);
		return module;
	}

	private void writeTo(Modules modules, String tech) throws PhrescoException {
		try {
			File outFile = new File(outputRootDir, "modules/" + tech + ".xml");
			System.out.println("Writing Modules to " + outFile.toString());

			JAXBContext jaxbContext = JAXBContext
					.newInstance("com.photon.phresco.service.jaxb");
			Marshaller marshaller = jaxbContext.createMarshaller();
			JAXBElement<Modules> jaxbApptype = new ObjectFactory()
					.createModules(modules);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(jaxbApptype, outFile);
		} catch (PropertyException e) {
			throw new PhrescoException(e);
		} catch (JAXBException e) {
			throw new PhrescoException(e);
		}
	}

	public final void generateAll() throws PhrescoException {
		for (String tech : INPUT_EXCEL_MAP.keySet()) {
			generate(tech);
		}
	}

	public void generate(String tech) throws PhrescoException {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(getExcelFile(tech));
			System.out.println("Processing excel file for "
					+ getExcelFile(tech));

			HSSFWorkbook workBook = new HSSFWorkbook(fs);
			generateModules(workBook, tech);

		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void addModuleXml(String tech) throws PhrescoException {
        ArtifactInfo info = new ArtifactInfo("modules",tech,"", "xml", "0.2");
        File artifact = new File(outputRootDir,  "/modules/" + tech + ".xml");
        repManager.addArtifact(info, artifact);
    }
	
	private File createPomFile(Module module, String ext, String groupId2) throws PhrescoException {
		System.out.println("Creating Pom File For " + module.getName());
		File file = new File(outputRootDir, "pom.xml");
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		try {
			DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
	
			org.w3c.dom.Document newDoc = domBuilder.newDocument();
			Element rootElement = newDoc.createElement("project");
			newDoc.appendChild(rootElement);
	
			Element modelVersion = newDoc.createElement("modelVersion");
			modelVersion.setTextContent("4.0.0");
			rootElement.appendChild(modelVersion);
	
			Element groupId = newDoc.createElement("groupId");
			groupId.setTextContent(groupId2);
			rootElement.appendChild(groupId);
	
			Element artifactId = newDoc.createElement("artifactId");
			artifactId.setTextContent(module.getId());
			rootElement.appendChild(artifactId);
	
			Element version = newDoc.createElement("version");
			version.setTextContent(module.getVersion());
			rootElement.appendChild(version);
	
			Element packaging = newDoc.createElement("packaging");
			packaging.setTextContent(ext);
			rootElement.appendChild(packaging);
	
			Element description = newDoc.createElement("description");
			description.setTextContent("created by phresco");
			rootElement.appendChild(description);
			
			TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            
			StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(newDoc);
            trans.transform(source, result);
            String xmlString = sw.toString();
            FileWriter writer = new FileWriter(file);
            writer.write(xmlString);
            writer.close();
            
		} catch (Exception e) {
			throw new PhrescoException();
		}
		return file;
	}
	
	public static void main(String[] args) throws PhrescoException {
	    File toolsHome = new File("D:\\work\\phresco\\agra\\service\\trunk\\phresco-service-runner\\delivery\\tools\\");
	    File binariesDir = new File("D:\\work\\phresco\\Phresco-binaries\\");
        File inputRootDir = new File(toolsHome, "files");
        File outputRootDir = new File(toolsHome, "repo");
        TechnologyDataGenerator dataGen = new TechnologyDataGenerator(inputRootDir, outputRootDir, binariesDir);
        dataGen.publish();
    }
}
