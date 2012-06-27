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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.plexus.util.StringUtils;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.util.ServiceConstants;

public class VideoInfoGenerator implements ServiceConstants {

	private static final String VIDEO_INFO_JSON_FILE = "videoInfo.json";
	private static final String VIDEO_EXCEL_FILE = "Videos.xls";

	private static final String VIDEOS = "Videos";
	private static final String DELIMITER = ",";

	HSSFWorkbook workBook = null;
	private File outFile = null;
	private File videoFile = null;
	private RepositoryManager repoMan = null;
	Map<String, String> codecMap = new HashMap<String, String>();
	
	private void initMap() {
	    codecMap.put("webm", "webm");
	    codecMap.put("mp4", "MPEG");
	    codecMap.put("ogg", "ogg , vorbis");
	    codecMap.put("ogv", "ogv , vorbis");
	}
	
	public VideoInfoGenerator(File inputDir, File outDir, File videoDir) throws PhrescoException {
		super();
		this.workBook = getWorkBook(new File(inputDir, VIDEO_EXCEL_FILE));
		this.outFile = new File(outDir, VIDEO_INFO_JSON_FILE);
		this.videoFile = videoDir;
		PhrescoServerFactory.initialize();
		this.repoMan = PhrescoServerFactory.getRepositoryManager();
		initMap();
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


	public void publish() throws PhrescoException {
		List<VideoInfo> infos = new ArrayList<VideoInfo>();
		HSSFSheet sheet = workBook.getSheet(VIDEOS);
		Iterator<Row> rowiIterator = sheet.iterator();
		for (int i = 0; i < 1; i++) {
			rowiIterator.next();
		}

		VideoInfo info = null;
		while (rowiIterator.hasNext()) {
			Row row = rowiIterator.next();
			info = createVideo(row);
			infos.add(info);
		}

		writesJson(infos, outFile);
		uploadToRepository(infos);
		uploadVideoToRepository(infos,videoFile);
	}

	private void uploadVideoToRepository(List<VideoInfo> infos,File videoDir) throws PhrescoException {
	    System.out.println("Video Files Uploading.............");
	    for (VideoInfo videoInfo : infos) {
			List<VideoType> videoList = videoInfo.getVideoList();
			for (VideoType videoType : videoList) {
			    String videoUrl = videoType.getUrl();
				File videoFile = new File(videoDir, videoUrl);
				if(videoFile.exists()) {
    				ArtifactInfo info = new ArtifactInfo("videos.homepage", findArtifactName(videoUrl), "" ,videoType.getType() , "1.0");
    				repoMan.addArtifact(info, videoFile);
				}
			}
		}
	    
	    System.out.println("Video Images Uploading.............");
		for (VideoInfo videoInfo : infos) {
			String imageUrl = videoInfo.getImageurl();
            File imageFile = new File(videoDir, imageUrl);
            if(imageFile.exists()) {
                ArtifactInfo info = new ArtifactInfo("videos.homepage", findArtifactName(imageUrl), "", "png", "1.0");
                repoMan.addArtifact(info, imageFile);
            }
		}

	}

	private VideoInfo createVideo(Row row) {
		VideoInfo info = new VideoInfo();
		Cell name = row.getCell(1);
		String Name = getValue(name);
		Cell description = row.getCell(2);
		String Description = getValue(description);
		Cell imageurl = row.getCell(3);
		String Imageurl = getValue(imageurl);
		Cell categories = row.getCell(4);
		String Categories = getValue(categories);
		Cell type = row.getCell(5);
		String videoType = getValue(type);
		Cell url = row.getCell(6);
		String videoUrl = getValue(url);
		String[] videoTypes = StringUtils.split(videoType,DELIMITER);
		List<VideoType> videotypes = new ArrayList<VideoType>(8);
		
		for (String vidType : videoTypes) {
		    VideoType vtype = new VideoType();
		    vtype.setType(vidType);
		    vtype.setUrl(videoUrl + "." + vidType);
		    vtype.setCodecs(codecMap.get(vidType));
		    videotypes.add(vtype);
        }

		info.setName(Name);
		info.setDescription(Description);
		info.setImageurl(Imageurl);
		info.setCategories(Categories);
		info.setVideoList(videotypes);
		return info;
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

	private void writesJson(List<VideoInfo> VideoInfo, File file) throws PhrescoException {
		try {
			Gson gson = new Gson();
			String value = gson.toJson(VideoInfo);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(value);
			writer.close();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private void uploadToRepository(List<VideoInfo> infos) throws PhrescoException {
			ArtifactInfo info = new ArtifactInfo("videos.homepage", "videoinfo", "", "json", "1.0");
			repoMan.addArtifact(info, outFile);
	}
	
	private static String findArtifactName(String fileName){
	    String name = fileName.substring(0,fileName.lastIndexOf("-"));
	    int index = name.lastIndexOf("/");
	    String context = name.substring(index + 1);
        return context;
	}
	
	public static void main(String[] args) throws PhrescoException {
		
		File inputFile = new File("D:\\work\\phresco\\agra\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files");
		File outFile = new File("D:\\work\\phresco\\agra\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files");

		File videoDir = new File("D:\\work\\phresco\\Phresco-binaries\\");
		VideoInfoGenerator videoGen = new VideoInfoGenerator(inputFile, outFile,videoDir);
		videoGen.publish();
	}
	
}
