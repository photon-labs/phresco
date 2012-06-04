/*
 * ###
 * Phresco Service Tools
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
package com.photon.phresco.service.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.data.api.PhrescoDataManager;
import com.photon.phresco.service.model.ArtifactInfo;

public class AdminConfigGenerator {

	private static final String ADMIN_CONFIG_JSON_FILE = "adminconfig.json";
	private static final String ADMIN_CONFIG_EXCEL_FILE = "AdminConfig.xls";

	private static final int NO_OF_ROWS_TO_SKIP = 1;
	private static final String PROPERTY = "Property";
	private RepositoryManager repManager;
	private HSSFWorkbook workBook;
	private File outFile;
	private PhrescoDataManager dataManager = null;

	public AdminConfigGenerator(File inputDir, File outDir) throws PhrescoException {
		this.workBook = getWorkBook(new File(inputDir, ADMIN_CONFIG_EXCEL_FILE));
		this.outFile = new File(outDir, ADMIN_CONFIG_JSON_FILE);
		PhrescoServerFactory.initialize();
		this.repManager = PhrescoServerFactory.getRepositoryManager();
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
		List<AdminConfigInfo> adminConfigInfos = new ArrayList<AdminConfigInfo>();
		HSSFSheet sheet = workBook.getSheet(PROPERTY);
		Iterator<Row> rowIterator = sheet.rowIterator();
		for (int i = 0; i <NO_OF_ROWS_TO_SKIP; i++) {
			rowIterator.next();
		}

		AdminConfigInfo adminConfigInfo = new AdminConfigInfo();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			adminConfigInfo = createAdminConfig(row);
			System.out.println ("admin config info-->"+adminConfigInfo.getId()+"---"+adminConfigInfo.getKey()+"---"+adminConfigInfo.getValue()+"--"+adminConfigInfo.getDescription());
			adminConfigInfos.add(adminConfigInfo);
			//dataManager.addAdminConfigInfo(adminConfigInfos);
		}
		dataManager.addAdminConfigInfo(adminConfigInfos);
		//System.out.println ("admin config info-->"+adminConfigInfo.getKey()+"---"+adminConfigInfo.getValue());
		//adminConfigInfos.add(adminConfigInfo);
		//Data Persistence
		//dataManager.addAdminConfigInfo(adminConfigInfos);
		//dataManager.addAdminConfigInfo(adminConfigInfo);
		//addAdminConfigInfo
		writesJson(adminConfigInfos, outFile);
		//uploadToRepository(configInfos, overwrite);
	}

	private void uploadToRepository(List<AdminConfigInfo> infos, boolean append) throws PhrescoException {
		if (append) {
			for (AdminConfigInfo adminConfigInfo : infos) {
				repManager.addAdminConfig(adminConfigInfo);
			}
		} else {
			ArtifactInfo info = new ArtifactInfo("config.auto", "admin", "", "json", "1.0");
			repManager.addArtifact(info, outFile);
		}
	}

	private void writesJson(List<AdminConfigInfo> adminConfigInfo, File file) throws PhrescoException {
		try {
			Gson gson = new Gson();
			String value = gson.toJson(adminConfigInfo);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(value);
			writer.close();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private AdminConfigInfo createAdminConfig(Row row) {
		AdminConfigInfo configInfo = new AdminConfigInfo();
		//Cell ids = row.getCell(1);
		//String id = getValue(ids);

		Cell ids = row.getCell(0);
		String id = getValue(ids);
		Cell keys = row.getCell(1);
		String key = getValue(keys);
		Cell values = row.getCell(2);
		String value = getValue(values);
		Cell descriptions = row.getCell(3);
		String description = getValue(descriptions);
		configInfo.setId(id);
		configInfo.setKey(key);
		configInfo.setValue(value);
		configInfo.setDescription(description);
		return configInfo;
	}

	private String getValue(Cell cell) {
		if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
			System.out.println ("hello-->"+cell.getStringCellValue());
			return cell.getStringCellValue();
		}

		if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			System.out.println ("hello2");
			return String.valueOf(cell.getNumericCellValue());
		}
		return null;
	}

	public static void main(String[] args) throws PhrescoException, IOException {
		File inputFile = new File("D:\\work\\projects\\phresco\\source\\trunk\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files");
				//"D:\\work\\projects\\phresco\\service\\trunk\\"+ "phresco-service-runner\\delivery\\tools\\files\\AdminConfig.xls");
		File outFile = new File("D:\\work\\projects\\phresco\\source\\trunk\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files" );
				//"D:\\work\\projects\\phresco\\service\\trunk\\"	+ "phresco-service-runner\\delivery\\tools\\files\\adminconfig.json");
		AdminConfigGenerator configGenerator = new AdminConfigGenerator(inputFile, outFile);
		boolean overwrite = false;
		configGenerator.publish(overwrite);
	}

}