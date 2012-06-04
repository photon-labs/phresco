package com.photon.phresco.service.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.plexus.util.StringUtils;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.I18NString;
import com.photon.phresco.model.L10NString;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.Settings;
import com.photon.phresco.model.SettingsApplies;
import com.photon.phresco.model.SettingsPropPossibleValues;
import com.photon.phresco.model.SettingsPropertyTemplate;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.service.data.api.PhrescoDataManager;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.util.Constants;

public class ConfigDataGenerator implements Constants{

	private static final String SETTINGS_JSON_FILE = "settings.json";
	private static final String SETTINGS_EXCEL_FILE = "SettingsTemplate.xls";

	private static final String SETTINGS_TEMPLATE = "Settings Template";
	private HSSFWorkbook workBook = null;
	private File outputFile = null;
	private RepositoryManager repoMan = null;
	public static PhrescoDataManager dataManager = null;


	public ConfigDataGenerator(File inputDir, File outDir) throws PhrescoException {
		super();
		this.workBook = getWorkBook(new File(inputDir, SETTINGS_EXCEL_FILE));
		this.outputFile = new File(outDir, SETTINGS_JSON_FILE);

		PhrescoServerFactory.initialize();
		this.repoMan = PhrescoServerFactory.getRepositoryManager();
		PhrescoServerFactory.initialize();
		this.dataManager = PhrescoServerFactory.getPhrescoDataManager();
	}

	private HSSFWorkbook getWorkBook(File inputFile) throws PhrescoException {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(inputFile);
			return new HSSFWorkbook(fs);
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

	public void publish(boolean overwrite) throws PhrescoException {
		List<SettingsTemplate> settings = new ArrayList<SettingsTemplate>();

		HSSFSheet sheet = workBook.getSheet(SETTINGS_TEMPLATE);
		Iterator<Row> rowiIterator = sheet.iterator();
		//skipping first row
		for (int i = 0; i < 1; i++) {
			rowiIterator.next();
		}

		SettingsTemplate template = null;
		//com.photon.phresco.model.Settings settingsData = new com.photon.phresco.model.Settings();
		List <com.photon.phresco.model.Settings> settingsDataList = new ArrayList <com.photon.phresco.model.Settings> ();
		List <PropertyTemplate> propertyTemplateList = new ArrayList<PropertyTemplate> ();
		PropertyTemplate propTemplate = null;
		//SettingsPropertyTemplate propTemplate = null;
		//reading the next lines
		int settingsid = 1;
		while (rowiIterator.hasNext()) {
			Row row = rowiIterator.next();
			com.photon.phresco.model.Settings settingsData = new com.photon.phresco.model.Settings();
			//if S.No is valid then create a new SettingsTempate
			Cell cell = row.getCell(0);
			if (cell != null && Cell.CELL_TYPE_BLANK != cell.getCellType()) {
				template = new SettingsTemplate();
				template.setType(row.getCell(1).getStringCellValue());

				//Set applies to
				String appliesTo = row.getCell(2).getStringCellValue();
				String appliesToArr[] = StringUtils.split(appliesTo, ",");
				List<String> applyTo = Arrays.asList(appliesToArr);
				//System.out.println ("applies to-->"+applyTo);
				template.setAppliesTo(applyTo);
				settingsData.setType(template.getType());

				//Adding data persistense - Settings
				System.out.println ("setting type--"+settingsData.getType());
				settingsData.setId(settingsid++);
				settingsDataList.add(settingsData);
				settings.add(template);
				//ENDS

			}

			//create a property template and add to settings template
			propTemplate = createPropertyTemplate(row);

			if (propTemplate != null) {
				template.getProperties().add(propTemplate);
			}

		} //Closing the Excel Row iteration
		if (propTemplate != null) {
			propertyTemplateList = template.getProperties();
		}
		//List <PropertyTemplate> propertyTemplateList = new ArrayList<PropertyTemplate> ();
		//Adding data persistence - SettingsTemplate , PropertyTemplate
		System.out.println ("property temp list-->"+propertyTemplateList.size());
		for (PropertyTemplate propTemp:propertyTemplateList){
			//System.out.println ("property Temp -Key- >"+ propTemp.getKey() +"-name- >"+propTemp.getName()+"- Type- >"+propTemp.getType()+"- Description - >"+propTemp.getDescription()+"- Possible Value- >"+propTemp.getPossibleValues()+"-Name - >"+propTemp.getName()+"-project specific-->"+"- project Specific -- >"+propTemp.isProjectSpecific()+"-is Required- >"+propTemp.isRequired());

		}
		//System.out.println ("setting list size--"+settingsDataList.size());
		/*for (int i=0;i<4;i++){
		dataManager.addSettings(settingsDataList);
		System.out.println ("datamanager is calling--->  " + i);
		}*/
		//ENDS
		writesJson(settings, outputFile);
		persistProperTemplateData (settings,settingsDataList);
		//uploadToRepository(settings, overwrite);
	}

	private void persistProperTemplateData(List<SettingsTemplate> settings, List<Settings> settingsDataList) throws PhrescoException {
		// TODO Auto-generated method stub
		Settings [] settingsDataArray = new Settings [settingsDataList.size()];
		int index = 0;

		for (Settings settingtemp:settingsDataList){
			settingsDataArray [index] = settingtemp;
			index++;
		}
		List <PropertyTemplate> propTemplateDataList  = null;
		List<PropertyTemplate> propTemplateList = new ArrayList<PropertyTemplate> ();
		List <List> listOfPropTempObjects = new ArrayList <List> ();
		int settingsListTracker=0;
		for (SettingsTemplate settingsTemplateTemp:settings){
			//System.out.println (" size of the property list in each settings objects-->  "+settingsTemplateTemp.getProperties().size());
			//List of properties <PropTempList> settingsTemplateTemp.getProperties()
			propTemplateList = settingsTemplateTemp.getProperties();
			propTemplateDataList = new ArrayList <PropertyTemplate> ();
			List<String> appliesToStr = new ArrayList<String> ();
			appliesToStr = settingsTemplateTemp.getAppliesTo();
			int propertyTracker = 1;
			//System.out.println ("applies to String-- >"+appliesToStr);
				for (PropertyTemplate propTemp:propTemplateList){
					//System.out.println ("PropertyTemplate objects"+settingsDataArray[settingsListTracker].getId()+"---proptemplate object  > "+ propTemp.getType());
					//propTemp.getSettingsId((settingsDataArray[settingsListTracker].getId());
					propTemp.setSettingsId(settingsDataArray[settingsListTracker].getId());
					//System.out.println ("property Temp -Key- >"+settingsListTracker+"--"+ propTemp.getKey() +"-name- >"+propTemp.getName()+"- Type- >"+propTemp.getType()+"- Description - >"+propTemp.getDescription()+"- Possible Value- >"+propTemp.getPossibleValues()+"-Name - >"+propTemp.getName()+"-project specific-->"+"- project Specific -- >"+propTemp.isProjectSpecific()+"-is Required- >"+propTemp.getIsRequired());

					propTemplateDataList.add(propTemp);
					storeSettingsPropertyPossibleValues (propTemp,settingsDataArray,settingsListTracker,propertyTracker);
					propertyTracker++;

					}



				//adding phresco data persistance - PropertyTemplate (working fine)
				//dataManager.addPropertyTemplate (propTemplateDataList);
				storeSettingsAppliesto (appliesToStr,settingsDataArray,settingsListTracker);
				settingsListTracker++;


		}//END OF SETTINGS OBJECT ITERATION

	}
	private void storeSettingsPropertyPossibleValues(PropertyTemplate propTemp,
			Settings[] settingsDataArray, int settingsListTracker, int propertyTracker)throws PhrescoException {
		// TODO Auto-generated method stub
		List <String> possibleValues = propTemp.getPossibleValues();
		List<SettingsPropPossibleValues> possibleValueList = new ArrayList<SettingsPropPossibleValues> ();

		//System.out.println ("possible Values List Size->"+possibleValues.size());
		if (possibleValues!=null){
			for (String possibleValue: possibleValues){
				//System.out.println ("possible Values->"+possibleValue);
				SettingsPropPossibleValues possibleValuesTemp = new SettingsPropPossibleValues ();
				possibleValuesTemp.setPossibleValues(possibleValue);
				possibleValuesTemp.setPropid(propertyTracker);
				possibleValuesTemp.setSettingsid(settingsDataArray[settingsListTracker].getId());
				//System.out.println ("possible Values model object->"+possibleValuesTemp);
				possibleValueList.add(possibleValuesTemp);
			}
		}else {
			//System.out.println (" No  possible Values->");
		}

		//dataManager.addSettingsPropPossibleValues (possibleValueList);
	}

	/**
	 *Storing Setting applied technologies
	 */
	private void storeSettingsAppliesto(List<String> appliesToStr,
			Settings[] settingsDataArray, int settingsListTracker) throws PhrescoException {
		// TODO Auto-generated method stub
		int settingsAppliesTracker = 1;
		List <SettingsApplies> settingsAppliesList = new ArrayList <SettingsApplies> ();
		for (String appliesString:appliesToStr){
			SettingsApplies settingsApplies = new SettingsApplies ();
			//System.out.println ("appplies Str in function-->  "+settingsDataArray[settingsListTracker].getId()+"--"+appliesString);
			settingsApplies.setDescription(appliesString);
			settingsApplies.setSettingsid(settingsDataArray[settingsListTracker].getId());
			settingsAppliesList.add(settingsApplies);
		}

		//dataManager.addSettingsApplies (settingsAppliesList);

	}

	private PropertyTemplate createPropertyTemplate(Row row) {
		PropertyTemplate propertyTemplate = new PropertyTemplate();

		//Read the name
		Cell nameCell = row.getCell(3);
		if (nameCell == null) {
			return null;
		}

		String nameStr = getValue(nameCell);
		I18NString nameI18nStr = createI18NString(nameStr);
		propertyTemplate.setName(nameI18nStr);

		Cell propertyCell = row.getCell(4);
		String propertyName = getValue(propertyCell);
		propertyTemplate.setKey(propertyName);

		Cell typeCell = row.getCell(5);
		String typeString = getValue(typeCell);
		propertyTemplate.setType(typeString);

		Cell possibleValuesCell = row.getCell(6);
		if (possibleValuesCell != null && Cell.CELL_TYPE_BLANK != possibleValuesCell.getCellType()) {
			String possibleValuesStr = getValue(possibleValuesCell);
			String possibleValArr[] = StringUtils.split(possibleValuesStr, ",");

			List<String> possibleValues = Arrays.asList(possibleValArr);
			propertyTemplate.setPossibleValues(possibleValues);
		}


		Cell isReqCell = row.getCell(7);
		String isReqStr = getValue(isReqCell);
		if ("Yes".equals(isReqStr)) {
        	propertyTemplate.setRequired(true);
        } else {
        	propertyTemplate.setRequired(false);
        }

		Cell projSpecificCell = row.getCell(8);
		String projSpecificStr = getValue(projSpecificCell);
		if ("Yes".equals(projSpecificStr)) {
        	propertyTemplate.setProjectSpecific(true);
        } else {
        	propertyTemplate.setProjectSpecific(false);
        }

		Cell descCell = row.getCell(9);
		String descStr = getValue(descCell);
		I18NString descII8nStr = createI18NString(descStr);
		propertyTemplate.setDescription(descII8nStr);

 		return propertyTemplate;
	}

	private static String getValue(Cell cell) {
		if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
			return cell.getStringCellValue();
		}

		if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			return String.valueOf(cell.getNumericCellValue());
		}

		return null;
	}

	private static I18NString createI18NString(String desc) {
        I18NString displayName;
        L10NString value;
        displayName = new I18NString();
        value = new L10NString("en-US", desc);
        displayName.add(value);
        return displayName;
    }

	private void writesJson(List<SettingsTemplate> settingsTemplate, File file) throws PhrescoException {
		try {
			Gson gson = new Gson();
			String value = gson.toJson(settingsTemplate);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(value);
			writer.close();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private void uploadToRepository(List<SettingsTemplate> settingsTemplate , boolean overWrite) throws PhrescoException {
		if (overWrite) {
			ArtifactInfo info = new ArtifactInfo("config", "settings", "", "json", "0.1");
			repoMan.addArtifact(info, outputFile);
		} else {
			File dirPath = null;
			for (SettingsTemplate settingTemplte : settingsTemplate) {
				repoMan.addSettings(settingTemplte, dirPath);
			}
		}
	}

	public static void main(String[] args) throws PhrescoException, IOException {
		//File inputFile = new File("D:\\");
		File inputFile = new File("D:\\work\\projects\\phresco\\source\\trunk\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files");

		//File outFile = new File("D:\\");
		File outFile = new File("D:\\work\\projects\\phresco\\source\\trunk\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files");


		ConfigDataGenerator configGen = new ConfigDataGenerator(inputFile, outFile);
		boolean overwrite = true;
		configGen.publish(overwrite);
	}

}
