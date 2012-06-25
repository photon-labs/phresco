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
package com.photon.phresco.service.data.db;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.photon.phresco.commons.model.Customer;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.Settings;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;
import com.photon.phresco.service.jaxb.Document;
import com.photon.phresco.service.jaxb.Library;
import com.photon.phresco.service.model.DocumentTypes;
import com.photon.phresco.service.model.Documents;
import com.photon.phresco.service.model.Libraries;
import com.photon.phresco.service.model.LibraryDoc;
import com.photon.phresco.service.model.LibrayDocs;
import com.photon.phresco.service.model.PilotTechModuleVersions;
import com.photon.phresco.service.model.PilotTechModules;
import com.photon.phresco.service.model.PilotTechnology;
import com.photon.phresco.service.model.SettingsApplies;
import com.photon.phresco.service.model.SettingsPropPossibleValues;


public class AdminDBService {

    private static final String QUERY_FIND_APPLICATION_TYPES = "service.getApplicationTypes";
    private static final String QUERY_FIND_MODULE_GROUPS = "service.getModuleGroup";
    private static final String QUERY_FIND_TECHNOLOGY = "service.getTechnology";
    private static final String QUERY_FIND_MODULE = "service.getModule";
    private static final String QUERY_FIND_SETTINGS_INFO = "service.getSettingsInfo";
    private static final String QUERY_FIND_ADMINCONFIG_INFO = "service.getAdminConfigInfo";
    private static final String QUERY_FIND_VIDEO_INFO = "service.getVideoInfo";
    private static final String QUERY_FIND_VIDEO_TYPE = "service.getVideoType";
    private static final String QUERY_FIND_DOWNLOAD_INFO = "service.getDownloadInfo";
    private static final String QUERY_FIND_PILOTS = "service.getPilotInfo";
    private static final String QUERY_FIND_TECHNOLOGY_APPID = "service.getTechnologyappid";
    private static final String QUERY_FIND_DOCUMENTATION = "service.getDocumentation";
    private static final String QUERY_FIND_PROJECTINFO   ="service.getProjectInfo";

    private static final String QUERY_ADMIN_CUSTOMERS_FIND_CUSTOMERS = "admin.customers.findCustomers";

    private static final String QUERY_FIND_DOCUMENT_TYPE   ="service.getDocumentTypes";
    private static final String QUERY_FIND_DOCUMENT_TYPE_KEY   ="service.getDocumentTypeKey";
    //private static final String QUERY_FIND_DOCUMENT_TYPE_KEY  = service.getDocument
    private HibernateTemplate template;

    public void setSessionFactory(SessionFactory sessionFactory) {
        template = new HibernateTemplate(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<DocumentTypes> getDocumentType() {
        // TODO Auto-generated method stub
        return  template.findByNamedQuery(QUERY_FIND_DOCUMENT_TYPE);
    }

    @SuppressWarnings("unchecked")
    public List<DocumentTypes> getDocumentTypes(String docType) {
        // TODO Auto-generated method stub
        return template.findByNamedQuery(QUERY_FIND_DOCUMENT_TYPE_KEY,docType);
    }

    /*public DocumentTypes[] getDocumentType(String docType) {
        // TODO Auto-generated method stub
        return template.findByNamedQuery(QUERY_FIND_DOCUMENT_TYPE_KEY,docType);
    }*/

    @SuppressWarnings("unchecked")
    public List<ApplicationType> getApplicationTypes() {
        return template.findByNamedQuery(QUERY_FIND_APPLICATION_TYPES);

    }

    public void addApplicationTypes(List<ApplicationType> applicationTypes) {
        template.saveOrUpdateAll(applicationTypes);
        for (ApplicationType applicationType : applicationTypes) {
            List<Technology> technologies = applicationType.getTechnologies();
            if (technologies != null && !technologies.isEmpty()) {
                for (Technology technology : technologies) {
                    technology.setAppTypeId(applicationType.getId());
                }
                template.saveOrUpdateAll(technologies);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<ModuleGroup> getModuleGroup(){
        return template.findByNamedQuery(QUERY_FIND_MODULE_GROUPS);
    }

    public void addModuleGroup(List<ModuleGroup> moduleGroup) {
        template.saveOrUpdateAll(moduleGroup);
        /*for (ModuleGroup modGroup : moduleGroup) {
            List<Documentation> documentsList = modGroup.getDocs();
            if(documentsList != null && !documentsList.isEmpty()){
                for (Documentation documentation : documentsList) {
                    documentation.setId(modGroup.getId());
                }
            }
            template.saveOrUpdateAll(documentsList);
        }
        for (ModuleGroup modGroup1 : moduleGroup) {
            List<Module> modulesList = modGroup1.getVersions();
            if(modulesList != null && !modulesList.isEmpty()){
                for (Module module : modulesList) {
                    module.setId(modGroup1.getId());
                }
            }
            template.saveOrUpdateAll(modulesList);
        }*/
    }

    @SuppressWarnings("unchecked")
    public List<Technology> getTechnology(){
        return template.findByNamedQuery(QUERY_FIND_TECHNOLOGY);
    }

    @SuppressWarnings("unchecked")
    public List<Technology> getTechnologies(String appId){
        return template.findByNamedQuery(QUERY_FIND_TECHNOLOGY_APPID,appId);
    }

    @SuppressWarnings("unchecked")
    public List<Module> getModule(){
        return template.findByNamedQuery(QUERY_FIND_MODULE);
    }

    @SuppressWarnings("unchecked")
    public List<SettingsInfo> getSettingsInfo(){
        return template.findByNamedQuery(QUERY_FIND_SETTINGS_INFO);
    }

    public void addSettingsInfo(List<SettingsInfo> settingsInfo){
        template.saveOrUpdateAll(settingsInfo);
        for (SettingsInfo settings : settingsInfo) {
            List<PropertyInfo> propertyInfos = settings.getPropertyInfos();
            if (propertyInfos != null && !propertyInfos.isEmpty()) {
                for (PropertyInfo propertyInfo : propertyInfos) {
                //  propertyInfo.setId(settings.getSettingId());
                    //propertyInfo.setId(settings.getSettingId());

                }
            }
            //template.saveOrUpdateAll(propertyInfos);
        }
    }

    @SuppressWarnings("unchecked")
    public List<AdminConfigInfo> getAdminConfigInfo(){
        return template.findByNamedQuery(QUERY_FIND_ADMINCONFIG_INFO);
    }

    public void addAdminConfigInfo(List<AdminConfigInfo> adminConfigInfo) throws PhrescoException{
        template.saveOrUpdateAll(adminConfigInfo);
    }

    @SuppressWarnings("unchecked")
    public List<VideoInfo> getVideoInfo(){
        return template.findByNamedQuery(QUERY_FIND_VIDEO_INFO);
    }

    @SuppressWarnings("unchecked")
    public List<VideoType> getVideoType(){
        return template.findByNamedQuery(QUERY_FIND_VIDEO_TYPE);
    }

    public void addVideoInfo(List<VideoInfo> videos){
        template.saveOrUpdateAll(videos);
        for (VideoInfo videoInfo : videos) {
            List<VideoType> videoTypes = videoInfo.getVideoList();
            if(videoTypes != null && !videoTypes.isEmpty()){
                for (VideoType videoType : videoTypes) {
                    videoType.setId(videoInfo.getId());
                }
                template.saveOrUpdateAll(videoTypes);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<DownloadInfo> getDownloadInfo(){
        return template.findByNamedQuery(QUERY_FIND_DOWNLOAD_INFO);
    }

    public void addDownloadInfo(List<DownloadInfo> downloadInfo){
        template.saveOrUpdateAll(downloadInfo);
    }

    public List<DownloadInfo> getDownloadInfo(String techId , String platform){
        List<DownloadInfo> downloadInfos = getDownloadInfo();
        List<DownloadInfo> downloads = new ArrayList<DownloadInfo>();
        for (DownloadInfo downloadInfo : downloadInfos) {
            String[] appliesTo = downloadInfo.getAppliesTo();
            for (String applies : appliesTo) {
                if(applies.equals(techId) && platform.equals(downloadInfo.getPlatform())){
                    downloads.add(downloadInfo);
                }
            }
        }
        return downloads;
    }

    public void addProjectinfo(ProjectInfo projectInfo){
        template.saveOrUpdate(projectInfo);
    }

    @SuppressWarnings("unchecked")
    public List<ModuleGroup> getPilots(){
        return template.findByNamedQuery(QUERY_FIND_PILOTS);
    }

    public void addModules(List<com.photon.phresco.service.model.Modules> modules) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(modules);
    }

    public void addTechnologies(List<Technology> technologies) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(technologies);
    }

    public List<Documentation> getDocumentations() {
        // TODO Auto-generated method stub
        return template.findByNamedQuery(QUERY_FIND_DOCUMENTATION);
    }

    public void addDocumentations(List<Documentation> documentations) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(documentations);
    }

    public List<ProjectInfo> getProjectInfo() {
        // TODO Auto-generated method stub
        return template.findByNamedQuery(QUERY_FIND_PROJECTINFO);
    }
    public void addDocument(List<com.photon.phresco.service.model.Document> documentList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(documentList);
    }
    public void addDocuments(List<com.photon.phresco.service.model.Documents> documentsList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(documentsList);

    }
    public void addModule(List<com.photon.phresco.model.Module> moduleDataList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(moduleDataList);
    }
    public void addLibrary(List<com.photon.phresco.service.model.Library> libraryList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(libraryList);

    }
    public void addLibraries(List<Libraries> librariesList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(librariesList);
    }
    public void addSettings(List<com.photon.phresco.model.Settings> settingsDataList) {
        // TODO Auto-generated method stub
        //System.out.println ("Settings List size in DBSERVICE -->"+settingsDataList.size ());
        /*for (Settings settingsTemp:settingsDataList) {
            System.out.println ("Settings List size in DBSERVICE -->"+settingsTemp.getId()+"--"+settingsTemp.getType());
        }*/
        template.saveOrUpdateAll(settingsDataList);
        /*for (Settings settingsTemp:settingsDataList) {
            template.saveOrUpdate(settingsTemp);

        }*/
    }
    public void addPropertyTemplate(List<PropertyTemplate> propTemplateDataList) {
        // TODO Auto-generated method stub
        //template.merge(propTemplateDataList);
        template.saveOrUpdateAll(propTemplateDataList);

        template.clear();
    }
    public void addSettingsApplies(List<SettingsApplies> settingsAppliesList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(settingsAppliesList);
    }
    public void addSettingsPropPossibleValues(
            List<SettingsPropPossibleValues> possibleValueList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(possibleValueList);
    }
    public void addPilotTechModules(List<PilotTechModules> pilotTechModuleList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(pilotTechModuleList);
    }
    public void addPilotTechModuleVersionsList(List<PilotTechModuleVersions> pilotTechModuleVersionsList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(pilotTechModuleVersionsList);
    }
    public void addPilotTechnology(List<PilotTechnology> pilotTechnologyList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(pilotTechnologyList);
    }
    public void addProjectInfo(List<ProjectInfo> pilotProjectInfo) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(pilotProjectInfo);
    }
    public void addLibraryDoc(List<LibraryDoc> libDocList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(libDocList);
    }
    public void addLibraryDocs(List<LibrayDocs> libDocList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(libDocList);
    }


    /*public void addModules(List<Module> modules) {
        // TODO Auto-generated method stub

    }*/
 }
