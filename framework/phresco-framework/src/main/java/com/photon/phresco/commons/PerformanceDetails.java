/*
 * ###
 * Phresco Framework
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
package com.photon.phresco.commons;

import java.util.List;

public class PerformanceDetails
{
	
	private String testAgainst;
	private String showSettings;
	private String setting;
	private String testName;
	private List<String> name;
    private List<String> context;
    private List<String> contextType;
    private List<String> contextPostData;
    private List<String> encodingType;
    private List<String> dbPerName;
    private List<String> queryType;
    private List<String> query;
    private int noOfUsers;
	private int rampUpPeriod;
	private int loopCount;
	  
	 public PerformanceDetails()
	 {
		 
	 }
	 
	 public PerformanceDetails(String testAgainst,String showSettings,String setting,String testName , List<String> name, List<String> context, List<String> contextType, List<String> contextPostData, List<String> encodingType,List<String> dbPerName,List<String> queryType,List<String> query,int noOfUsers, int rampUpPeriod, int loopCount)
	 {
		 this.testAgainst = testAgainst;
		 this.showSettings = showSettings;
		 this.setting = setting;
		 this.testName = testName; 
		 this.name = name;
		 this.context = context;
		 this.contextType = contextType;
		 this.contextPostData = contextPostData;
		 this.encodingType = encodingType;
		 this.dbPerName = dbPerName;
		 this.queryType = queryType;
		 this.query = query;
		 this.noOfUsers = noOfUsers;
		 this.rampUpPeriod = rampUpPeriod;
		 this.loopCount = loopCount;
	 }
	 
	 public String getTestAgainst() {
	     return testAgainst;
	 }
	 
	 public void setTestAgainst() {
		 this.testAgainst = testAgainst;
	 }
	 
	 public String getShowSettings() {
		 return showSettings;
	 }
	 
	 public void setShowSettings() {
		 this.showSettings = showSettings;
	 }
	 
	 public String getSetting() {
	     return setting;
	 }
	 
	 public void setSetting() {
		 this.setting = setting;
	 }
	 
	 public String getTestName() {
		 return testName;
	 }
	 
	 public void setTestName(String testName) {
		 this.testName = testName;
	 }
	 
	 public List<String> getName() {
	        return name;
	 }
	 
	 public void setName(List<String> name) {
	        this.name = name;
	 }
	 
	 public List<String> getContext() {
	        return context;
     }
	 
	public void setContext(List<String> context) {
	        this.context = context;
	}
	
	public List<String> getContextType() {
	        return contextType;
	}
	
	public void setContextType(List<String> contextType) {
	        this.contextType = contextType;
	}
	
	public List<String> getContextPostData() {
		  return contextPostData;
	}
	
	public void setContextPostData(List<String> contextPostData) {
		 this.contextPostData = contextPostData;
	}
	
	public List<String> getEncodingType() {
		   return encodingType;
	}
	
	public void setEncodingType(List<String> encodingType) {
		 this.encodingType = encodingType;
	}
	
    public List<String> getDbPerName() {
		return dbPerName;
	}

	public void setDbPerName(List<String> dbPerName) {
		this.dbPerName = dbPerName;
	}

	public List<String> getQueryType() {
		return queryType;
	}

	public void setQueryType(List<String> queryType) {
		this.queryType = queryType;
	}

	public List<String> getQuery() {
		return query;
	}

	public void setQuery(List<String> query) {
		this.query = query;
	}

	public int getNoOfUsers() {
	    return noOfUsers;
    }
   
   public void setNoOfUsers(int noOfUsers) {
	   this.noOfUsers = noOfUsers;
   }
   
   public int getRampUpPeriod() {
	   return rampUpPeriod;
   }
   
   public void setRampUpPeriod(int rampUpPeriod) {
	   this.rampUpPeriod = rampUpPeriod;
   }
   
   public int getLoopCount() {
	   return loopCount;
	   
   }
   public void setLoopCount(int loopCount) {
	   this.loopCount = loopCount;
   }
   
   public String toString() {
       return String.format("testAgainst:%s,showSettings:%s,setting:%s,testName:%s,name:%s,context:%s,contextType:%s,contextPostData:%s,encodingType:%s,noOfUsers:%d,rampUpPeriod:%d,loopCount:%d",testAgainst,showSettings,setting,testName,name,context,contextPostData,encodingType,noOfUsers,rampUpPeriod,loopCount);
   }
}

