<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ include file="errorReport.jsp" %>

<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.model.Technology" %>
<%@ page import="com.photon.phresco.model.ModuleGroup" %>
<%@ page import="com.photon.phresco.model.WebService"%>
<%@ page import="com.photon.phresco.model.Server"%>
<%@ page import="com.photon.phresco.model.Database"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes"%>

<script type="text/javascript" src="js/confirm-dialog.js" ></script>

<%
	String selectedStr = "";
	String checkedStr = "";
	String projectCode = ""; 
	String appType = (String)request.getAttribute(FrameworkConstants.REQ_APPLICATION_TYPE);
	Technology selectedTechnology = (Technology) request.getAttribute(FrameworkConstants.SESSION_SELECTED_TECHNOLOGY);
    ProjectInfo selectedInfo = (ProjectInfo) session.getAttribute((String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE));
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	String disabled = "disabled";
    if (StringUtils.isEmpty(fromPage)) {
        disabled = "";
    }
    
    if (selectedInfo == null) {
    	selectedInfo = (ProjectInfo) request.getAttribute(FrameworkConstants.REQ_PROJECT_INFO);
    }
    
    List<Server> selectedServers = null;
    List<Database> selectedDatabases = null;
    List<WebService> selectedWebServices = null;
    boolean isEmailSupportSelected = false;
    String selectedPilotProj = null;
    String projectInfoDbNames = "";
	if(selectedInfo != null) {
		projectCode = selectedInfo.getCode();
		selectedServers =  selectedInfo.getTechnology().getServers();
		selectedDatabases =  selectedInfo.getTechnology().getDatabases();
		selectedWebServices =  selectedInfo.getTechnology().getWebservices();
		isEmailSupportSelected = selectedInfo.getTechnology().isEmailSupported();
		selectedPilotProj = selectedInfo.getPilotProjectName();
		if (CollectionUtils.isNotEmpty(selectedDatabases)) {
			for (Database selectedDatabase : selectedDatabases) {
				projectInfoDbNames = projectInfoDbNames + selectedDatabase.getName() + ",";
			}
			projectInfoDbNames = projectInfoDbNames.substring(0, projectInfoDbNames.length() - 1);
		}
	}
	
	Collection<String> pilotProjectNames = (Collection<String>) request.getAttribute(FrameworkConstants.REQ_PILOTS_NAMES);
	ProjectInfo pilotProjectInfo = (ProjectInfo) request.getAttribute(FrameworkConstants.REQ_PILOT_PROJECT_INFO);
	List<Server> pilotProjectServers = null;
	List<Database> pilotProjectDbs = null;
	List<WebService> pilotProjectWebServices = null;
	boolean isPilotEmailSupported = false;
	List<String> pilotTechVersions = null;
	if (pilotProjectInfo != null) {
		pilotProjectServers = pilotProjectInfo.getTechnology().getServers();
		pilotProjectDbs = pilotProjectInfo.getTechnology().getDatabases();
		pilotProjectWebServices = pilotProjectInfo.getTechnology().getWebservices();
		isPilotEmailSupported = pilotProjectInfo.getTechnology().isEmailSupported();
		pilotTechVersions = pilotProjectInfo.getTechnology().getVersions();
	}
	
	List<Server> servers = (List<Server>) request.getAttribute(FrameworkConstants.REQ_TEST_SERVERS);
	List<Database> databases = (List<Database>) request.getAttribute( FrameworkConstants.REQ_DATABASES);
%>

<div class="clearfix">
    <label for="xlInput" class="new-xlInput"><s:text name="label.pilot.project"/></label>

    <!--  Pilot projects are loaded here starts-->
    <div class="input new-input">
		<%
        	if (CollectionUtils.isNotEmpty(pilotProjectNames)) {
				for (String pilotProjectName : pilotProjectNames) {
					if (pilotProjectName.equals(selectedPilotProj) || 
							(StringUtils.isNotEmpty(fromPage) && pilotProjectName.equals(selectedInfo.getPilotProjectName()))){
						selectedStr = "selected";
					}
				}
        	}
       	%>
           <select class="xlarge" id="pilotProjects" name="pilotProject" <%= disabled %> onchange="showPilotProjectConfigs(this);">
			<option value="">None</option>
			<%
				if (CollectionUtils.isNotEmpty(pilotProjectNames)) {
					for (String pilotProjectName : pilotProjectNames) {
			%>
						<option value="<%= pilotProjectName %>" <%= selectedStr %> ><%= pilotProjectName %></option>
			<%		
					}
				} 
			%>
           </select>
    </div>
    <!--  Pilot projects are loaded here ends -->
</div>

<form id="deleteObjects">
	<!-- Servers are loaded here starts -->
	<div class="clearfix">
		<label for="xlInput" class="new-xlInput"><s:text name="label.supported.servers"/></label>
	    
	    <div class="input new-input" id="dispServer" style="color: #ffffff;">
	
	    </div>
	    
	    <label for="xlInput" id="Server" style="cursor: pointer; margin: 3px 0 0 20px; text-align: center; background-color: #cccccc; border-radius: 6px; line-height: 25px; padding: 0px;  color: #000000; width:50px;"><s:text name="label.add"/></label>
	    
	    <input type="hidden" id="selectedServer" name="selectedServers" value="">
	</div>
	<!-- Servers are loaded here ends -->

	<!-- Databases are loaded here starts -->
	<div class="clearfix">
		<label for="xlInput" Class="new-xlInput"><s:text name="label.supported.dbs"/></label>
		<div class="input new-input" id="dispDatabase" style="color: #ffffff">
	
	    </div>
	    
	    <label for="xlInput" id="Database" style="cursor: pointer; margin: 3px 0 0 20px; text-align: center; background-color: #cccccc; border-radius: 6px; line-height: 25px; padding: 0px;  color: #000000; width:50px;"><s:text name="label.add"/></label>
	    
	    <input type="hidden" id="selectedDatabase" name="selectedDatabases" value="">
	</div>
</form>
<!-- Databases are loaded here ends -->

<!-- Web Services are loaded here starts -->
<div class="clearfix">
	<s:label for="webservice" key="label.web.service" theme="simple" cssClass="new-xlInput"/>
	<div class="input new-input">
		<div class="typeFields" id="typefield">
			<div id="multilist-scroller">
				<ul>
					<%
						checkedStr = "";
						List<WebService> webservices = selectedTechnology.getWebservices();
						if(webservices != null) {
							for(WebService webservice : webservices) {
								if(selectedWebServices != null && CollectionUtils.isNotEmpty(selectedWebServices)) {
								
									for(WebService selectedWebService : selectedWebServices) {
										if(selectedWebService.getId() == webservice.getId()){
											checkedStr = "checked";
											break;
										} else {
											checkedStr = "";
										}
									}
								}
					%>
								<li>
									<input type="checkbox" id="webservices" name="webservices" value="<%= webservice.getId() %>" <%= checkedStr %>
										class="check"><%= webservice.getName() + " " + webservice.getVersion() %> 
								</li>
					<%        
							}
						}
					%>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- Web Services are loaded here ends -->

<!-- Email starts -->
<div class="clearfix">
	<s:label for="email" key="label.web.email" theme="simple" cssClass="new-xlInput"/>
		<div class="input new-input">
				<%
// 					boolean isEmailSupported = selectedTechnology.isEmailSupported();
					
					if (isEmailSupportSelected) {
						selectedStr = "checked";
					}
				%>
				<input type="checkbox" style="margin-top: 8px;" name="emailSupported" value="true" <%= selectedStr %>>
		</div>	
</div>
<!-- Email ends -->

<input type="hidden" id="projectInfoDbNames" value="<%= StringUtils.isNotEmpty(projectInfoDbNames) ? projectInfoDbNames : "" %>">

<script>
	if(!isiPad()) {
		/* JQuery scroll bar */
		$("#multilist-scroller").scrollbars();
	}
	
	$(document).ready(function() {
		enableScreen();
		
		/** To construct the div during the project edit **/
		<%
			String name = null;
			List<String> versions = new ArrayList<String>(2);
			if (selectedServers != null) {
				for (Server server : selectedServers) {
					String csvVersion = "";
					name = server.getName();
					for (String version : server.getVersions()) {
						if (csvVersion != "") {
							csvVersion = version + ", " + csvVersion;
						} else {
							csvVersion = version + ", ";
						}
					}
					csvVersion = csvVersion.trim();
					csvVersion = csvVersion.substring(0, csvVersion.length() - 1);
		%>
					constructElements("Server", '<%= name %>', '<%= csvVersion %>', "dispServer");
		<%
				}
			}
			if (selectedDatabases != null) {
				for (Database database : selectedDatabases) {
					String csvVersion = "";
					name = database.getName();
					for (String version : database.getVersions()) {
						if (csvVersion != "") {
							csvVersion = version + ", " + csvVersion;
						} else {
							csvVersion = version + ", ";
						}
					}
					csvVersion = csvVersion.trim();
					csvVersion = csvVersion.substring(0, csvVersion.length() - 1);
		%>
					constructElements("Database", '<%= name %>', '<%= csvVersion %>', "dispDatabase");
		<%
				}
			}
		%>
		
		$("#Server").click(function() {
			openAttrPopup("", "Server");
		});
		
		$("#Database").click(function() {
			openAttrPopup("", "Database");
		});
		
		$('#deleteObjects').submit(function() {
			updateHiddenConfigNames();
			return false;
	    });
	});
	
	/** To retain values during project edit, previous and next **/
	function constructElements(type, name, versions, appendTo, from) {
		var isAlreadyAdded = false;
		var alreadyAddedDetails = "";
		var alreadyAddedVersions = new Array();
		var newVersions;
		var eleAttr = name.replace(/\s+/g, '');
		
		/** To append the version to the existing versions if the pilot server/db and the already selected server/db are same **/
		if ("showPilotProjectConfigs" == from) {
			if (type == "Server") {
				alreadyAddedDetails = $("#selectedServer").val();
			} else if (type == "Database") {
				alreadyAddedDetails = $("#selectedDatabase").val();
			}
			var nameSep = new Array();
			nameSep = alreadyAddedDetails.split("#SEP#");
			for (var i=0; i < nameSep.length; i++) {
				var addedName = nameSep[i].split("#VSEP#");
				if (addedName[0].replace(/\s/g, '') == name.replace(/\s/g, '')) {
					if (addedName[1].indexOf(",") != -1) {
						alreadyAddedVersions = addedName[1].split(",");
					} else {
						alreadyAddedVersions[0] = addedName[1];
					}
					isAlreadyAdded = true;
					break;
				}
			}
			
			var arrayVersions = new Array();
			if (versions.indexOf(",") != -1) {
				arrayVersions = versions.split(",");
			} else {
				arrayVersions[0] = versions;
			}
			newVersions = versions;
			
			if (isAlreadyAdded) {
				deleteThis(type, name, "", name);
				var isVersionAlreadyExists = false;
				for (var i=0; i < alreadyAddedVersions.length; i++) {
					for (var j=0; j < arrayVersions.length; j++) {
						if (arrayVersions[j] == alreadyAddedVersions[i]) {
							isVersionAlreadyExists = true;
							break;
						}
					}
					if (isVersionAlreadyExists) {
						break;
					}
				}
				if (!isVersionAlreadyExists) {
					newVersions = newVersions + ", " + alreadyAddedVersions;				
				} else {
					newVersions = alreadyAddedVersions;
				}
			}
		} else { // This is for adding the new server/db
			newVersions = versions;
		}
		
		$("#"+appendTo).append('<div id="'+eleAttr+'" style="background-color: #bbbbbb; width: 40%; margin-bottom:2px; height: auto; border-radius: 6px; padding: 5px 0 0 10px; position: relative"><a name="' + type + '" class="deleteThis" href="#" id="' 
						+ eleAttr +'" style="text-decoration: none; margin-right: 10px; color: #000000; margin-left: 95%;" title="'+ name +'" onclick="deleteEle(this);">&times;</a><div id="'+newVersions+'" class="'+eleAttr+'" title="'+type+'" onclick="openAttrPopup(this);" style="cursor: pointer; color: #000000; height: auto; position: relative; width: 90%; line-height: 17px; margin-top: -14px; padding: 0 0 6px 1px;">' 
						+ name + " [ " + newVersions + " ] " + '</div></div>');
		$("#"+type).css("margin-left", "320px");
		
		updateHiddenFields(type, name, newVersions, from);			
	}
	
	/** To delete the currently selected item **/
	var deleteId = "";
	var type = "";
	var name = "";
	
	function deleteEle(obj) {
		deleteId = obj.id;
		type = $(obj).prop("name");
		name = $(obj).prop("title");
		
		var params = "selectedAttrType=";
		params = params.concat(type);
		params = params.concat("&selectedParamName=");
		params = params.concat(name);
		params = params.concat("&projectCode=");
		params = params.concat('<%= projectCode %>');
		
		/* 
			To check for the Corresponding configuration of the item to be removed.
			If it has configuration then the confirmation will be asked from the user regarding the deletion of the configuration 
		*/
		if (<%= StringUtils.isNotEmpty(fromPage) %>) {
			performAction('checkForRespectiveConfig', params, '', true);
		} else {
			removeDiv();
		}
	}
	
	function updateHiddenConfigNames() {
		if (type == "Server") {
			if ($("#configServerNames").val() != undefined) {
				var val = $("#configServerNames").val() + name + ",";
				$("#configServerNames").val(val);				
			}
		} else if (type == "Database") {
			if ($("#configDbNames").val() != undefined) {
				var val = $("#configDbNames").val() + name + ",";
				$("#configDbNames").val(val);
			}
		}
		removeDiv();
	}
	
	function removeDiv() {
		if (type == "Server") {
			deleteThis(type, deleteId, "", name);
		}
		if (type == "Database") {
			deleteThis(type, deleteId, "", name);
		}
	}
	
	function deleteThis(type, deleteId, tempSelected, name, from) {
		$("#"+deleteId).remove();
		var pilotVersions = "";
		
		/** This is to remove only the pilot server/db version other than the already selected versions during none project selection **/
		if ("removePilotProjConfig" == from) {
			var alreadyAddedVersions = new Array();
			var arrayPilotVersions = new Array();
			if (type == "Server") { // To check whether the already added server verions contains the pilot server versions
				var alreadySelectedServers = $("#selectedServer").val();
				var pilotServerConfigDet = $("#pilotServerConfigDet").val();
				var nameSep = new Array();
				nameSep = alreadySelectedServers.split("#SEP#");
				for (var i=0; i < nameSep.length; i++) {
					var addedServers = nameSep[i].split("#VSEP#");
					if (addedServers[0].replace(/\s/g, '') == name.replace(/\s/g, '')) {
						if (addedServers[1].indexOf(",") != -1) {
							alreadyAddedVersions = addedServers[1].split(",");
							
						} else {
							alreadyAddedVersions[0] = addedServers[1];
						}
						break;
					}
				}
				
				var pilotNameSep = new Array();
				pilotNameSep = pilotServerConfigDet.split("#SEP#");
				for (var i=0; i < pilotNameSep.length; i++) {
					var pilotServers = pilotNameSep[i].split("#VSEP#");
					if (pilotServers[0].replace(/\s/g, '') == name.replace(/\s/g, '')) {
						if (pilotServers[1].indexOf(",") != -1) {
							arrayPilotVersions = pilotServers[1].split(",");
						} else {
							arrayPilotVersions[0] = pilotServers[1];
						}
						break;
					}
				}
				
				for (var i=0; i < arrayPilotVersions.length; i++) {
					for (var j=0; j < alreadyAddedVersions.length; j++) {
						if (jQuery.trim(alreadyAddedVersions[j]) == jQuery.trim(arrayPilotVersions[i])) {
							delete alreadyAddedVersions[j];
						}
					}
				}
				
				for (var i=0; i < alreadyAddedVersions.length; i++) {
					if (alreadyAddedVersions[i] != undefined && alreadyAddedVersions[i].trim() != "") {
						pilotVersions = pilotVersions + alreadyAddedVersions[i].trim() + ", ";						
					}
				}
				pilotVersions = pilotVersions.trim();
				pilotVersions = pilotVersions.substring(0, pilotVersions.length - 1);
				if (pilotVersions != "" && pilotVersions != undefined) {
					constructElements("Server", name, pilotVersions, "dispServer", from);					
				}
			}
			if (type == "Database") { // To check whether the already added db verions contains the pilot db versions
				var alreadySelectedDbs = $("#selectedDatabase").val();
				var pilotDbConfigDet = $("#pilotDbConfigDet").val();
				var nameSep = new Array();
				nameSep = alreadySelectedDbs.split("#SEP#");
				for (var i=0; i < nameSep.length; i++) {
					var addedDbs = nameSep[i].split("#VSEP#");
					if (addedDbs[0].replace(/\s/g, '') == name.replace(/\s/g, '')) {
						if (addedDbs[1].indexOf(",") != -1) {
							alreadyAddedVersions = addedDbs[1].split(",");
						} else {
							alreadyAddedVersions[0] = addedDbs[1];
						}
						break;
					}
				}
				var pilotNameSep = new Array();
				pilotNameSep = pilotDbConfigDet.split("#SEP#");
				for (var i=0; i < pilotNameSep.length; i++) {
					var pilotDbs = pilotNameSep[i].split("#VSEP#");
					if (pilotDbs[0].replace(/\s/g, '') == name.replace(/\s/g, '')) {
						if (pilotDbs[1].indexOf(",") != -1) {
							arrayPilotVersions = pilotDbs[1].split(",");
						} else {
							arrayPilotVersions[0] = pilotDbs[1];
						}
						break;
					}
				}
				
				for (var i=0; i < arrayPilotVersions.length; i++) {
					for (var j=0; j < alreadyAddedVersions.length; j++) {
						if (jQuery.trim(alreadyAddedVersions[j]) == jQuery.trim(arrayPilotVersions[i])) {
							delete alreadyAddedVersions[j];
						}
					}
				}
				
				for (var i=0; i < alreadyAddedVersions.length; i++) {
					if (alreadyAddedVersions[i] != undefined && alreadyAddedVersions[i].trim() != "") {
						pilotVersions = pilotVersions + alreadyAddedVersions[i].trim() + ", ";						
					}
				}
				pilotVersions = pilotVersions.trim();
				pilotVersions = pilotVersions.substring(0, pilotVersions.length - 1);
				if (pilotVersions != "" && pilotVersions != undefined) {
					constructElements("Database", name, pilotVersions, "dispDatabase", from);					
				}
			}
		}
		var serverDispDivConts = $("#dispServer").text();
		var dbDispDivConts = $("#dispDatabase").text();
		
		if ($.trim(serverDispDivConts) == "") {
			$("#Server").css("margin-left", "20px");
		}
		if($.trim(dbDispDivConts) == "") {
			$("#Database").css("margin-left", "20px");
		}
		
		if ("removePilotProjConfig" == from && alreadyAddedVersions != "") {
			updateHiddenFields(type, name, pilotVersions, "edit"); // To remove only the pilot server/db versions
		} else {
			updateHiddenFields(type, name, "", "delete");
		}
	}
	
	/** To open the select popup **/
	function openAttrPopup(obj, type) {
		$(".scroll-content").css("z-index", "");
		$('#popup_div').show();
		$('#popup_div').empty();
		showPopup();
		var from = "";
		
		/** For edit **/
		var selectedVersions;
		var attrName;
		if (obj != "") {
			selectedVersions = obj.id;
			attrName = $(obj).attr("class");
			from = "edit";
			type = $(obj).attr("title");
		}
		
		var params = "applicationType="
		params = params.concat('<%= appType %>');
		params = params.concat("&type=");
		params = params.concat(type);
		params = params.concat("&techId=");
		params = params.concat('<%= selectedTechnology.getId() %>');
		params = params.concat("&attrName=");
		params = params.concat(attrName);
		params = params.concat("&selectedVersions=");
		params = params.concat(selectedVersions);
		params = params.concat("&from=");
		params = params.concat(from);
		params = params.concat("&fromPage=");
		params = params.concat('<%= fromPage %>');
		popup('openAttrPopup', params, $('#popup_div'));
	}
	
	/** To get the versions onChange of the select box option **/
	function getAllVersions(type, selectedId) {
		if (type == "Database") {
			versionFor = "databaseVersion";
		} else {
			versionFor = "serverVersion";
		}
		var params = "applicationType=";
		params = params.concat('<%= appType %>');
		params = params.concat("&techId=");
		params = params.concat('<%= selectedTechnology.getId() %>');
		params = params.concat("&type=");
		params = params.concat(type);
		params = params.concat("&selectedId=");
		params = params.concat(selectedId);
		performAction('getAllVersions', params, '', true);
	}

	/** To update the master hidden fields **/
	function updateHiddenFields(type, selectedName, selectedVersions, from) {
		var selectedValues = $("#selected"+type).val();
		var nameSep = new Array();
		var finalValue = "";
		
		if (from == "delete") { //During delete
			nameSep = selectedValues.split("#SEP#");
			for (var i=0; i < nameSep.length; i++) {
				var name = nameSep[i].split("#VSEP#");
				if (name[0].replace(/\s/g, '') == selectedName.replace(/\s/g, '')) {
					delete nameSep[i];
					//nameSep.splice(i, 1);
				} else {
					if(nameSep[i] != "") {
						finalValue = finalValue + nameSep[i] + "#SEP#";
					}
				}
			}
		} else if (from == "edit") { //During edit
			nameSep = selectedValues.split("#SEP#");
			for (var i=0; i < nameSep.length; i++) {
				var name = nameSep[i].split("#VSEP#");
				if (name[0].replace(/\s/g, '') == selectedName.replace(/\s/g, '') && selectedVersions != "") {
					var newVal = selectedName + "#VSEP#" + selectedVersions;
					delete nameSep[i];
					nameSep[i] = newVal;
				}
				if(nameSep[i] != "") {
					finalValue = finalValue + nameSep[i] + "#SEP#";
			  	}
			}
		} else if (from == "removePilotProjConfig") { //During add
			nameSep = selectedValues.split("#SEP#");
			for (var i=0; i < nameSep.length; i++) {
				var servers = nameSep[i].split("#VSEP#");
				var availName = servers[0]; 
				var availVersions = servers[1];
				if (availName.replace(/\s/g, '') == selectedName.replace(/\s/g, '')) {
					finalValue = finalValue + availName + "#VSEP#" + selectedVersions + "#SEP#";
				} else if (nameSep[i] != "") {
					finalValue = finalValue + nameSep[i] + "#SEP#";
				}
			}
		} else {
			finalValue = $("#selected"+type).val() + selectedName + "#VSEP#" + selectedVersions + "#SEP#";
		}
		
		$("#selected" + type).val(finalValue);
		var updatedVal = $("#selected" + type).val();
		enableDisableSelectBtn(type, $("#selected"+type).val());
	}

	/** To disable and enable the select button based on the selection **/
	function enableDisableSelectBtn(type, selectedValues) {
		var size = 0;
		var nameSep = new Array();
		if(type == "Server") {
			<% if(servers != null) { %>
				size = '<%= servers.size() %>';
			<% } %>
		} else if(type == "Database") {
			<% if(databases != null) { %>
				size = '<%= databases.size() %>';
			<% } %>
		}
		
		nameSep = selectedValues.split("#SEP#");
		if(nameSep.length > size) {
			$("#"+type).hide();
		} else {
			$("#"+type).show();
		}
	}
	
	function successEvent(pageUrl, data) {
		if (pageUrl == "getAllVersions") {
			if (fillCheckBoxVersion(versionFor, data.versions)) {
				makeVersionsSelected();
			}
		} else if (pageUrl == "techVersions") {
			if (data.techVersions != undefined) {
				$("#technologyVersionDiv").show();
                if (fillVersions("techVersion", data.techVersions)) {
					showPrjtInfoTechVersion();
                }
	        } else {
				$("#technologyVersionDiv").hide();
	        }
		} else if (pageUrl == "checkForRespectiveConfig") {
			if (data.hasConfiguration) {
				if (type == "Database") {
					$("#confirmationText").html("Corresponding " + type + " configurations and SQL files will also be deleted. Do you like to continue ?");
				} else {
					$("#confirmationText").html("Corresponding " + type + " configurations will also be deleted. Do you like to continue ?");					
				}
			    dialog('block');
			    escBlockPopup();
			} else {
				if (type == "Database") {
					if (checkForProjectInfoDb()) {
						$("#confirmationText").html("Corresponding " + type + " SQL files will also be deleted. Do you like to continue ?");
						dialog('block');
					    escBlockPopup();						
					} else {
						removeDiv();
					}
				} else {
					removeDiv();					
				}
			}
		} else if (pageUrl == "technology") {
			techVersions();    	
		}
	}
	
	function checkForProjectInfoDb() {
		var arrayProjectInfoDbNames = new Array();
		var projectInfoDbNames = $("#projectInfoDbNames").val()
		arrayProjectInfoDbNames = projectInfoDbNames.split(",");
		for (var i=0; i < arrayProjectInfoDbNames.length; i++) {
			if (arrayProjectInfoDbNames[i].replace(/\s/g, '') == name.replace(/\s/g, '')) {
				return true;
			}
		}
		
		return false;
	}
	
	/** To show/hide the pilot project configurations when the pilot project is selected **/
	function showPilotProjectConfigs(obj) {
		var currentTechnology = $("#technology").val();
		if ('<%= TechnologyTypes.ANDROID_NATIVE %>' == currentTechnology) {
			removeLowerTechVersions();
		}
		
		var pilotProject = $(obj).val();
		var alreadyConstructed = $("#alreadyConstructed").val();// This alreadyConstructed hidded field is in the application.jsp
		
		if (pilotProject != undefined && pilotProject != "" && alreadyConstructed == "") { //To show the pilot project configurations
			/** For Server **/
			<%
				if (CollectionUtils.isNotEmpty(pilotProjectServers)) {
					String pilotServerConfigDet = "";
					for (Server pilotProjectServer : pilotProjectServers) {
						String csvVersion = "";
						name = pilotProjectServer.getName();
						for (String version : pilotProjectServer.getVersions()) {
							if (csvVersion != "") {
								csvVersion = version + ", " + csvVersion;	
							} else {
								csvVersion = version + ", ";
							}
						}
						csvVersion = csvVersion.trim();
						csvVersion = csvVersion.substring(0, csvVersion.length() - 1);
						pilotServerConfigDet = pilotServerConfigDet + (name + "#VSEP#" + csvVersion + "#SEP#");
			%>
						$("#pilotServerConfigDet").val('<%= pilotServerConfigDet %>');
						constructElements("Server", '<%= name %>', '<%= csvVersion %>', "dispServer", "showPilotProjectConfigs");
			<%
					}
				}
			%>
			
			/** For Database **/
			<%
				if (CollectionUtils.isNotEmpty(pilotProjectDbs)) {
					String pilotDbConfigDet = "";
					for (Database pilotProjectDb : pilotProjectDbs) {
						String csvVersion = "";
						name = pilotProjectDb.getName();
						for (String version : pilotProjectDb.getVersions()) {
							if (csvVersion != "") {
								csvVersion = version + ", " + csvVersion;
							} else {
								csvVersion = version + ", ";
							}
						}
						csvVersion = csvVersion.trim();
						csvVersion = csvVersion.substring(0, csvVersion.length() - 1);
						pilotDbConfigDet = pilotDbConfigDet + (name + "#VSEP#" + csvVersion + "#SEP#");
			%>
						$("#pilotDbConfigDet").val('<%= pilotDbConfigDet %>');
						constructElements("Database", '<%= name %>', '<%= csvVersion %>', "dispDatabase", "showPilotProjectConfigs");
			<%
					}
				}
			%>
			
			/** For Webservices **/
			<%
				if (CollectionUtils.isNotEmpty(pilotProjectWebServices)) {
			%>
					$("input:checkbox[name=webservices]").each(function() {
						<% 
							for (WebService pilotProjectWebService : pilotProjectWebServices) {
						%>
								if ($(this).val() == '<%= pilotProjectWebService.getId() %>') {
									$(this).attr("checked", "checked");					
								}
						<% 
							}
						%>
					});
			<%
				}
			%> 
			
			/** For email supported **/
			<%
				if (isPilotEmailSupported) {
			%>
					$("input:checkbox[name=emailSupported]").attr("checked", "checked");	
			<%
				}
			%>
			
			$("#alreadyConstructed").val("alreadyConstructed");
		} else {
			$("#alreadyConstructed").val("");
			
			/** To remove servers **/
			<%
				if (CollectionUtils.isNotEmpty(pilotProjectServers)) {
					for (Server pilotProjectServer : pilotProjectServers) {
						String deleteId = pilotProjectServer.getName().replaceAll("\\s", "");
			%>
						deleteThis("Server", '<%= deleteId %>', "", '<%= pilotProjectServer.getName() %>', "removePilotProjConfig");
			<%
					}
				}
			%>
			$("#pilotServerConfigDet").val("");
			
			/** To remove databases **/
			<%
				if (CollectionUtils.isNotEmpty(pilotProjectDbs)) {
					for (Database pilotProjectDb : pilotProjectDbs) {
						String deleteId = pilotProjectDb.getName().replaceAll("\\s", "");
			%>
						deleteThis("Database", '<%= deleteId %>', "", '<%= pilotProjectDb.getName() %>', "removePilotProjConfig");
			<%
					}
				}
			%>
			$("#pilotDbConfigDet").val("");
			
			/** To unselect the webservices **/
			<%
				if (CollectionUtils.isNotEmpty(pilotProjectWebServices)) {
			%>
					$("input:checkbox[name=webservices]").each(function() {
						<% 
							for (WebService pilotProjectWebService : pilotProjectWebServices) {
						%>
								if ($(this).val() == '<%= pilotProjectWebService.getId() %>') {
									$(this).removeAttr("checked");
								}
						<% 
							}
						%>
					});
			<%
				}
			%> 
			
			/** To uncheck email supported **/
			<%
				if (isPilotEmailSupported) {
			%>
					$("input:checkbox[name=emailSupported]").removeAttr("checked");	
			<%
				}
			%>

			/** To reload all the versions when none project is selected for android native technology **/
			if ('<%= TechnologyTypes.ANDROID_NATIVE %>' == currentTechnology) {
				techVersions();
			}
		}
	}
	
 	/**
    * To remove the lower technology version based on the pilot project projectInfo
    * when pilot project is selected for android native technology
    **/
	function removeLowerTechVersions() {
		<%
			if (CollectionUtils.isNotEmpty(pilotTechVersions)) {
				for (String pilotTechVersion : pilotTechVersions) {
		%>
					var pilotTechVersion = parseInt('<%= pilotTechVersion %>'.replace(/\./g, ""));
					$("#techVersion option").each(function() {
						var techVersion = parseInt($(this).val().replace(/\./g, ""));
						if (techVersion < pilotTechVersion) {
							$(this).remove();
						}
					});
		<%
				}
			}
		%>
	}
</script>