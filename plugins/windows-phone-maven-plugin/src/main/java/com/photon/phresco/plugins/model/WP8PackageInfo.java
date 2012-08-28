package com.photon.phresco.plugins.model;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.plugin.MojoExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.photon.phresco.util.PluginConstants;

public class WP8PackageInfo implements PluginConstants {
	private String packageVersion, packageName;
	private File[] manifestFile;
	private File rootDir;
	private Element rootElement;
	private Element identityElement;
	
	public WP8PackageInfo(File rootDir) {
		this.rootDir = rootDir;
		this.getAppxManifestFile();
	}
	
	private void getAppxManifestFile() {
		manifestFile = rootDir.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, final String name) { 
				return name.endsWith(".appxmanifest");
			}
		});
		try {
			this.rootElement = this.getRootElementFromAppxManifestFile();
		} catch (MojoExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Element getRootElementFromAppxManifestFile() throws MojoExecutionException {
		Element root;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
	
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = null;
	
			builder = factory.newDocumentBuilder();
	
			Document doc = null;
			doc = builder.parse(new InputSource(rootDir.getPath() + WINDOWS_STR_BACKSLASH + manifestFile[0].getName()));
	
			root = doc.getDocumentElement();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			throw new MojoExecutionException(e.getMessage(), e);
		}
		return root;
	}

	/**
	 * @return the packageVersion
	 */
	public String getPackageVersion() {
		Element identity = this.getIdentityElement();
		this.packageVersion = identity.getAttribute("Version");
		return packageVersion;
	}
	
	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		Element identity = this.getIdentityElement();
		this.packageName = identity.getAttribute("Name");
		return packageName;
	}
	
	
	/**
	 * get the Identity element from manifest file
	 * @return identity
	 */
	private Element getIdentityElement() {
		Element identity = null;
		
		NodeList childNodes = rootElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i) instanceof Element) {
				String data = ((Element) childNodes.item(i)).getNodeName();
				if (data.equalsIgnoreCase("identity")) {
					identity = ((Element) childNodes.item(i));
					break;
				}
			}
		}
		this.identityElement = identity;
		return identityElement;
	}
}