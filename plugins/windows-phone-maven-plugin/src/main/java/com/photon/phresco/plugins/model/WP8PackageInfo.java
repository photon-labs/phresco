package com.photon.phresco.plugins.model;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.DOMException;

import com.photon.phresco.util.PluginConstants;
import com.photon.phresco.util.Utility;

public class WP8PackageInfo implements PluginConstants {
	private String packageVersion, packageName;
	private File[] manifestFile;
	private File rootDir;
	private Element rootElement;
	private Element identityElement;
	private Document doc;
	
	public WP8PackageInfo(File rootDir) {
		this.rootDir = rootDir;
		this.getAppxManifestFile();
	}
	
	public File getAppxManifestFile() {
		manifestFile = rootDir.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, final String name) { 
				return name.endsWith(".appxmanifest");
			}
		});
		try {
			rootElement = this.getRootElementFromAppxManifestFile();
		} catch (MojoExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return manifestFile[0];
	}
	
	private Element getRootElementFromAppxManifestFile() throws MojoExecutionException {
		Element root = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = (Document) builder.build(manifestFile[0]);
			root = doc.getRootElement();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}
	
	public void incrementPackageVersionNo() {
		try {
			Namespace ns = rootElement.getNamespace();
			elementIdentifier(rootElement, "Identity" ,ns);
			saveFile(manifestFile[0], doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void elementIdentifier(Element rootNode, String elementName, Namespace ns) {
		List child = rootNode.getChildren(elementName, ns);
		for (int i = 0; i < child.size(); i++) {
			Object object = child.get(i);
			Element project = (Element) object;
			List children = project.getAttributes();
			for (Object object2 : children) {
				Attribute PropertyGroup = (Attribute) object2;
				findChild(PropertyGroup, ns);
			}
		}
	}

	private void findChild(Attribute rootNamespace, Namespace ns) {
		String name = rootNamespace.getName();
		if (name.equalsIgnoreCase("Version")) {
			rootNamespace.setValue(incrementPackageVersion());
		} 
	}

	private void saveFile(File projectPath, Document doc)	throws IOException {
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

	/**
	 * @return the packageVersion
	 */
	public String getPackageVersion() {
		Element identity = this.getIdentityElement();
		this.packageVersion = identity.getAttributeValue("Version");
		return packageVersion;
	}

	
	
	/**
	 * Set new revision number when we generate a build.
	 * @param element 
	 * @param string 
	 */
	public String incrementPackageVersion() {
		String versionNo = null;
		try {
			String packageVersion = this.getPackageVersion();
			versionNo = this.getNextPackageVersion(packageVersion);
		} catch (DOMException e) {
			e.printStackTrace();
		} 
		return versionNo;
	}
	
	public String getNextPackageVersion(String packageVersion) {
		String revisionNo = "";
		String _packageVersion = "";
		_packageVersion = packageVersion;
		revisionNo = _packageVersion.substring(_packageVersion.lastIndexOf(".") + 1);
		int intRevisionNo = Integer.parseInt(revisionNo) + 1;
		return _packageVersion = _packageVersion.substring(0, _packageVersion.lastIndexOf(".") + 1) + intRevisionNo;
	}
	
	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		Element identity = this.getIdentityElement();
		this.packageName = identity.getAttributeValue("Name");
		return packageName;
	}
	
	/**
	 * get the Identity element from manifest file
	 * @return identity
	 */
	private Element getIdentityElement() {
		Element identity = null;
		
		List childNodes = rootElement.getChildren();
		for (int i = 0; i < childNodes.size(); i++) {
			if (childNodes.get(i) instanceof Element) {
				String data = ((Element) childNodes.get(i)).getName();
				if (data.equalsIgnoreCase("identity")) {
					identity = ((Element) childNodes.get(i));
					break;
				}
			}
		}
		this.identityElement = identity;
		return identityElement;
	}
}