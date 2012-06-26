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
package com.photon.phresco.service.model;

import java.util.List;

public class Library {

	   private int libid;
	   private String id;
	   private String name;
	   private String contentType;
	   private  String contentURL;
	   private String technologyRef;
	   private String dependentLibs;
	   private  String documentID;
	   private   String version;
	   private	List<String> technolgoyRefList;
	   private  List<String> dependentLibList;
	   private int librariesId;
	   private boolean required;
	   private String artifactId;
	   private String groupId;
	   private String type;
       /**

	 * @return the librariesId
	 */
	public int getLibrariesId() {
		return librariesId;
	}
	/**
	 * @param librariesId the librariesId to set
	 */
	public void setLibrariesId(int librariesId) {
		this.librariesId = librariesId;
	}
		/**
		 * @return the libid
		 */
		public int getLibid() {
			return libid;
		}
		/**
		 * @param libid the libid to set
		 */
		public void setLibid(int libid) {
			this.libid = libid;
		}
		public Library (){

	    }
	    /* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Library [id=" + id + ", name=" + name + ", contentType="
					+ contentType + ", contentURL=" + contentURL
					+ ", technologyRef=" + technologyRef + ", dependentLibs="
					+ dependentLibs + ", documentID=" + documentID
					+ ", version=" + version + ", technolgoyRefList="
					+ technolgoyRefList + ", dependentLibList="
					+ dependentLibList + "]";
		}
		public Library(String id, String name, String contentType,
				String contentURL, String technologyRef, String dependentLibs,
				String documentID, String version,
				List<String> technolgoyRefList, List<String> dependentLibList) {
			super();
			this.id = id;
			this.name = name;
			this.contentType = contentType;
			this.contentURL = contentURL;
			this.technologyRef = technologyRef;
			this.dependentLibs = dependentLibs;
			this.documentID = documentID;
			this.version = version;
			this.technolgoyRefList = technolgoyRefList;
			this.dependentLibList = dependentLibList;
		}
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the contentType
		 */
		public String getContentType() {
			return contentType;
		}
		/**
		 * @param contentType the contentType to set
		 */
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		/**
		 * @return the contentURL
		 */
		public String getContentURL() {
			return contentURL;
		}
		/**
		 * @param contentURL the contentURL to set
		 */
		public void setContentURL(String contentURL) {
			this.contentURL = contentURL;
		}
		/**
		 * @return the technologyRef
		 */
		public String getTechnologyRef() {
			return technologyRef;
		}
		/**
		 * @param technologyRef the technologyRef to set
		 */
		public void setTechnologyRef(String technologyRef) {
			this.technologyRef = technologyRef;
		}
		/**
		 * @return the dependentLibs
		 */
		public String getDependentLibs() {
			return dependentLibs;
		}
		/**
		 * @param dependentLibs the dependentLibs to set
		 */
		public void setDependentLibs(String dependentLibs) {
			this.dependentLibs = dependentLibs;
		}
		/**
		 * @return the documentID
		 */
		public String getDocumentID() {
			return documentID;
		}
		/**
		 * @param documentID the documentID to set
		 */
		public void setDocumentID(String documentID) {
			this.documentID = documentID;
		}
		/**
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}
		/**
		 * @param version the version to set
		 */
		public void setVersion(String version) {
			this.version = version;
		}
		/**
		 * @return the technolgoyRefList
		 */
		public List<String> getTechnolgoyRefList() {
			return technolgoyRefList;
		}
		/**
		 * @param technolgoyRefList the technolgoyRefList to set
		 */
		public void setTechnolgoyRefList(List<String> technolgoyRefList) {
			this.technolgoyRefList = technolgoyRefList;
		}
		/**
		 * @return the dependentLibList
		 */
		public List<String> getDependentLibList() {
			return dependentLibList;
		}
		/**
		 * @param dependentLibList the dependentLibList to set
		 */
		public void setDependentLibList(List<String> dependentLibList) {
			this.dependentLibList = dependentLibList;
		}
		public boolean isRequired() {
			return required;
		}
		public void setRequired(boolean required) {
			this.required = required;
		}
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
}
