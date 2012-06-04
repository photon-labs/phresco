package com.photon.phresco.service.data.db;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.Libraries;
import com.photon.phresco.model.LibraryDoc;
import com.photon.phresco.model.LibrayDocs;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.PilotTechModuleVersions;
import com.photon.phresco.model.PilotTechModules;
import com.photon.phresco.model.PilotTechnology;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.PropertyInfo;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.SettingsApplies;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.SettingsPropPossibleValues;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;


public class ComponentDBService {

    private static final String QUERY_FIND_APPLICATION_TYPES = "service.getApplicationTypes";

    private HibernateTemplate template;

    public void setSessionFactory(SessionFactory sessionFactory) {
        template = new HibernateTemplate(sessionFactory);
    }

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



    public void addDownloadInfo(List<DownloadInfo> downloadInfo){
        template.saveOrUpdateAll(downloadInfo);
    }


    public void addProjectinfo(ProjectInfo projectInfo){
        template.saveOrUpdate(projectInfo);
    }



    public void addModules(List<com.photon.phresco.model.Modules> modules) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(modules);
    }

    public void addTechnologies(List<Technology> technologies) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(technologies);
    }



    public void addDocumentations(List<Documentation> documentations) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(documentations);
    }


    public void addDocument(List<com.photon.phresco.model.Document> documentList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(documentList);
    }
    public void addDocuments(List<com.photon.phresco.model.Documents> documentsList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(documentsList);

    }
    public void addModule(List<com.photon.phresco.model.Module> moduleDataList) {
        // TODO Auto-generated method stub
        template.saveOrUpdateAll(moduleDataList);
    }
    public void addLibrary(List<com.photon.phresco.model.Library> libraryList) {
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
