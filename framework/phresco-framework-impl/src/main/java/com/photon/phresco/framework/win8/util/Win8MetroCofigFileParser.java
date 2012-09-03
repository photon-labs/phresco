package com.photon.phresco.framework.win8.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.Utility;

/**
 * @author saravanan_na
 *
 */
public class Win8MetroCofigFileParser implements FrameworkConstants {

	public static void xmlParser(ProjectInfo info, File path) throws PhrescoException {
		try {
			path = new File(path + File.separator + SOURCE_DIR + File.separator + HELLOWORLD + File.separator + HELLOWORD_PROJECT_FILE);
			File sourcePath = new File(getProjectHome(info) + File.separator + SOURCE_DIR);
			if (!sourcePath.exists()) {
				return;
			}
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(path);
			Element rootNode = doc.getRootElement();
			Namespace ns = rootNode.getNamespace();
			elementIdentifier(rootNode, PROPERTYGROUP ,info, ns);
			elementIdentifier(rootNode, ITEMGROUP,info, ns);
			copyLibFolder(info, sourcePath);
			saveFile(path, doc);
			changeHellworld(info);
			visitAllDirsAndFiles(sourcePath,info);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private static void elementIdentifier(Element rootNode, String elementName,	ProjectInfo info, Namespace ns) {
		List child = rootNode.getChildren(elementName, ns);
		for (int i = 0; i < child.size(); i++) {
			Object object = child.get(i);
			Element project = (Element) object;
			List children = project.getChildren();
			for (Object object2 : children) {
				Element PropertyGroup = (Element) object2;
				findChild(PropertyGroup,info, ns);
			}
		}
	}

	private static void findChild(Element rootNamespace, ProjectInfo info, Namespace ns) {
		String name = rootNamespace.getName();
		if (name.equalsIgnoreCase(ROOTNAMESPACE)) {
			rootNamespace.setText(info.getName());
		} else if (name.equalsIgnoreCase(ASSEMBLY_NAME)) {
			rootNamespace.setText(info.getName());
		} else if (name.equalsIgnoreCase(CERTIFACTE_KEY)) {
			rootNamespace.setText(info.getName()+ TEMPORARY_KEY);
		} else if (name.equalsIgnoreCase(NONE)) {
			Attribute attribute = rootNamespace.getAttribute(INCLUDE);
			attribute.setValue(info.getName()+ TEMPORARY_KEY);
		} 
	}

	private static void saveFile(File projectPath, Document doc)	throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(projectPath);
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, writer);
		} finally {
			Utility.closeStream(writer);
		}
	}
	
	private static void changeHellworld(ProjectInfo info) throws PhrescoException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		File path = new File(getProjectHome(info) + File.separator +  SOURCE_DIR + File.separator +  HELLOWORD_SOLUTIONFILE);
		File tempFile = new File(getProjectHome(info) + File.separator + SOURCE_DIR + File.separator + TEMP_FOLDER);
		File newFile = new File(getProjectHome(info) +File.separator +  SOURCE_DIR + File.separator + HELLOWORD_SOLUTIONFILE);
		try {
			br = new BufferedReader(new FileReader(path));
			bw = new BufferedWriter(new FileWriter(tempFile));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains(HELLOWORLD)) {
					line = line.replace(HELLOWORLD, info.getName());
				}
				bw.write(line + NEWLINE);
			}
		} catch (Exception e) {
			return;
		} finally {
			Utility.closeReader(br);
			Utility.closeWriter(bw);
		}
		FileUtil.delete(path);
		tempFile.renameTo(newFile);
	}
	
	public static void visitAllDirsAndFiles(File dir, ProjectInfo info) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				File replacePath = replacePath(children[i], dir, info);
				visitAllDirsAndFiles(replacePath, info);
			}
		}
	}

	private static File replacePath(String string, File dir, ProjectInfo info) {
		File newFile = new File("");
		File oldFile = new File("");
		if (string.contains(HELLOWORLD) || string.startsWith(HELLOWORLD)|| string.startsWith(info.getName()) || string.contains(info.getName())) {
			oldFile = new File(dir + File.separator + string);
			String substring = info.getName();
			if (string.contains(HELLOWORLD)) {
				substring = string.substring(0, 10).replace(HELLOWORLD,	info.getName());
				String substring2 = string.substring(10, string.length());
				newFile = new File(dir + File.separator	+ substring.concat(substring2));
			} else {
				newFile = new File(dir + File.separator + substring);
			}
			oldFile.renameTo(newFile);
		}
		return newFile;
	}

	private static void copyLibFolder(ProjectInfo info , File path) throws IOException {
		File srcDir = new File(path + File.separator + info.getName());
		if (srcDir.exists()) {
			File destDir = new File(path + File.separator + HELLOWORLD);
			FileUtils.copyDirectory(srcDir, destDir);
			FileUtils.deleteDirectory(srcDir);
		}
	}
	
	private static File getProjectHome(ProjectInfo info){
		File projectHome = new File(Utility.getProjectHome() + File.separator + info.getCode());
		return projectHome;
	}
	
}