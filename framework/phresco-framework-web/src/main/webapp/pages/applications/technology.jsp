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
	if(selectedInfo != null) {
		projectCode = selectedInfo.getCode();
		selectedServers =  selectedInfo.getTechnology().getServers();
		selectedDatabases =  selectedInfo.getTechnology().getDatabases();
		selectedWebServices =  selectedInfo.getTechnology().getWebservices();
		isEmailSupportSelected = selectedInfo.getTechnology().isEmailSupported();
		selectedPilotProj = selectedInfo.getPilotProjectName();
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
	    
	    <input type="hidden" id="tempSelectedServer" name="tempSelectedServers" value="">
	    <input type="hidden" id="selectedServer" name="selectedServers" value="">
	</div>
	<!-- Servers are loaded here ends -->

	<!-- Databases are loaded here starts -->
	<div class="clearfix">
		<label for="xlInput" Class="new-xlInput"><s:text name="label.supported.dbs"/></label>
		<div class="input new-input" id="dispDatabase" style="color: #ffffff">
	
	    </div>
	    
	    <label for="xlInput" id="Database" style="cursor: pointer; margin: 3px 0 0 20px; text-align: center; background-color: #cccccc; border-radius: 6px; line-height: 25px; padding: 0px;  color: #000000; width:50px;"><s:text name="label.add"/></label>
	    
	    <input type="hidden" id="tempSelectedDatabase" name="tempSelectedDatabases" value="">
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

<!-- <div class="popup_div" id="tech_popup"> -->

<script>
	if(!isiPad()){
		/* JQuery scroll bar */
		$("#multilist-scroller").scrollbars();
	}
	
	$(document).ready(function() {
		/** To construct the div during the project edit **/
		<%
			String name = null;
			List<String> versions = new ArrayList<String>(2);
			if(selectedServers != null) {
				for(Server server : selectedServers) {
					name = server.getName();
					versions.clear();
					for (String version : server.getVersions()) {
						versions.add(version.trim());
					}
		%>
					constructElements("Server", '<%= name %>', '<%= versions %>', "dispServer");
		<%
					versions.clear();
				}
			}
			if(selectedDatabases != null) {
				for(Database database : selectedDatabases) {
					name = database.getName();
					versions.clear();
					for (String version : database.getVersions()) {
						versions.add(version.trim());
					}
		%>
					constructElements("Database", '<%= name %>', '<%= versions %>', "dispDatabase");
		<%
					versions.clear();
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
	function constructElements(type, name, versions, appendTo) {
		var eleAttr = name.replace(/\s+/g, '');
		var newVersions = versions.slice(0,-1)
		newVersions = newVersions.substring(1, newVersions.length);
		var tempHiddenVal = $("#tempSelected"+type).val();
		$("#tempSelected"+type).val(tempHiddenVal + name + " , ");
		$("#"+appendTo).append('<div id="'+eleAttr+'" style="background-color: #bbbbbb; width: 40%; margin-bottom:2px; height: auto; border-radius: 6px; padding: 5px 0 0 10px; position: relative"><a name="' + type + '" class="deleteThis" href="#" id="' 
						+ eleAttr +'" style="text-decoration: none; margin-right: 10px; color: #000000; margin-left: 95%;" title="'+ name +'" onclick="deleteEle(this);">&times;</a><div id="'+newVersions+'" class="'+eleAttr+'" title="'+type+'" onclick="openAttrPopup(this);" style="cursor: pointer; color: #000000; height: auto; position: relative; width: 90%; line-height: 17px; margin-top: -14px; padding: 0 0 6px 1px;">' 
						+ name + " [ " + newVersions + " ] " + '</div></div>');
		$("#"+type).css("margin-left", "320px");
		updateHiddenFields(type, name, newVersions, "");
	}
	
	/** To delete the currently selected item **/
	var deleteId = "";
	var type = "";
	var name = "";
	var tempSelectedServers = "";
	var tempSelectedDatabases = "";
	
	function deleteEle(obj) {
		deleteId = obj.id;
		type = $(obj).prop("name");
		name = $(obj).prop("title");
		tempSelectedServers = $("#tempSelectedServer").val();
		tempSelectedDatabases = $("#tempSelectedDatabase").val();
		
		var params = "selectedAttrType=";
		params = params.concat(type);
		params = params.concat("&selectedParamName=");
		params = params.concat(name);
		params = params.concat("&projectCode=");
		params = params.concat('<%= projectCode %>');
		
		/* 
			To check for the corressponding configuration of the item to be removed.
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
		if (tempSelectedServers != "" && type == "Server") {
			deleteThis(type, deleteId, tempSelectedServers, name);
		}
		if (tempSelectedDatabases != "" && type == "Database") {
			deleteThis(type, deleteId, tempSelectedDatabases, name);
		}
	}
	
	function deleteThis(type, deleteId, tempSelected, name) {
		$("#"+deleteId).remove();
		var serverDispDivConts = $("#dispServer").text();
		var dbDispDivConts = $("#dispDatabase").text();
		if ($.trim(serverDispDivConts) == "") {
			$("#Server").css("margin-left", "20px");
		}
		if($.trim(dbDispDivConts) == "") {
			$("#Database").css("margin-left", "20px");
		}
		updateTempHiddenFields(type, tempSelected, deleteId);
		updateHiddenFields(type, name, "", "delete");
	}
	
	/** To update the temporary hidden field **/
	function updateTempHiddenFields(type, tempSelected, deleteId) {
		params = "type=";
		params = params.concat(type);
		params = params.concat("&tempSelected=");
		params = params.concat(tempSelected);
		params = params.concat("&deleteId=");
		params = params.concat(deleteId);
		performAction('updateHiddenFields', params, '', true);
	}
	
	/** Data are returned from success event of perform action**/
	function successUpdateTempHiddenFileds(data) {
	    if (data.selectedAttrType == "Server") {
    		$("#tempSelectedServer").val(data.hiddenFieldValue);
    	}
    	else if (data.selectedAttrType == "Database") {
			$("#tempSelectedDatabase").val(data.hiddenFieldValue);
    	}	
	}
	
	/** To open the select popup **/
	function openAttrPopup(obj, type) {
		$(".scroll-content").css("z-index", "");
		$('#popup_div').show();
		$('#popup_div').empty();
		showPopup();
		var from = "";
		var tempSelectedServers = $("#tempSelectedServer").val();
		var tempSelectedDatabases = $("#tempSelectedDatabase").val();
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
		if(from == "delete") { //During delete
			nameSep = selectedValues.split("#SEP#");
			for(var i=0; i < nameSep.length; i++) {
				var name = nameSep[i].split("#VSEP#");
				if (name[0].replace(/\s/g,'') == selectedName.replace(/\s/g,'')) {
					delete nameSep[i];
					//nameSep.splice(i,1);
				} else {
					if(nameSep[i] != "") {
						finalValue = finalValue + nameSep[i] + "#SEP#";
					}
				}
			}
		} else if(from == "edit") { //During edit
			nameSep = selectedValues.split("#SEP#");
			for(var i=0; i < nameSep.length; i++) {
				var name = nameSep[i].split("#VSEP#");
				if (name[0].replace(/\s/g,'') == selectedName.replace(/\s/g,'')) {
					var newVal = selectedName + "#VSEP#" + selectedVersions;
					nameSep[i] = newVal;
				}
				if(nameSep[i] != "") {
				finalValue = finalValue + nameSep[i] + "#SEP#";
			  }
			}
		} else { //During add
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
		} else if (pageUrl == "updateHiddenFields") {
			successUpdateTempHiddenFileds(data);
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
					$("#confirmationText").html("Corressponding " + type + " configurations and SQL files will also be deleted. Do you like to continue ?");
				} else {
					$("#confirmationText").html("Corressponding " + type + " configurations will also be deleted. Do you like to continue ?");					
				}
			    dialog('block');
			    escBlockPopup();
			} else {
				if (type == "Database") {
					$("#confirmationText").html("Corressponding " + type + " SQL files will also be deleted. Do you like to continue ?");
					dialog('block');
				    escBlockPopup();
				} else {
					removeDiv();					
				}
			}
		} else if (pageUrl == "technology") {
			techVersions();    	
		    $("#name").focus();
		}
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
					for(Server pilotProjectServer : pilotProjectServers) {
						name = pilotProjectServer.getName();
						versions.clear();
						for (String version : pilotProjectServer.getVersions()) {
							versions.add(version.trim());
						}
			%>
						constructElements("Server", '<%= name %>', '<%= versions %>', "dispServer");
			<%
					}
				}
			%>
			
			/** For Database **/
			<%
				if (CollectionUtils.isNotEmpty(pilotProjectDbs)) {
					for(Database pilotProjectDb : pilotProjectDbs) {
						name = pilotProjectDb.getName();
						versions.clear();
						for (String version : pilotProjectDb.getVersions()) {
							versions.add(version.trim());
						}
			%>
						constructElements("Database", '<%= name %>', '<%= versions %>', "dispDatabase");
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
			if (alreadyConstructed != "") { //To hide the pilot project configurations
				tempSelectedServers = $("#tempSelectedServer").val();
				tempSelectedDatabases = $("#tempSelectedDatabase").val();
				/** To remove servers **/
				<%
					if (CollectionUtils.isNotEmpty(pilotProjectServers)) {
						for(Server pilotProjectServer : pilotProjectServers) {
							String deleteId = pilotProjectServer.getName().replaceAll("\\s", "");
				%>
							deleteThis("Server", '<%= deleteId %>', tempSelectedServers, '<%= pilotProjectServer.getName() %>');
				<%
						}
					}
				%>
				
				/** To remove databases **/
				<%
					if (CollectionUtils.isNotEmpty(pilotProjectDbs)) {
						for(Database pilotProjectDb : pilotProjectDbs) {
							String deleteId = pilotProjectDb.getName().replaceAll("\\s", "");
				%>
							deleteThis("Database", '<%= deleteId %>', tempSelectedDatabases, '<%= pilotProjectDb.getName() %>');
				<%
						}
					}
				%>
				
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
			}
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