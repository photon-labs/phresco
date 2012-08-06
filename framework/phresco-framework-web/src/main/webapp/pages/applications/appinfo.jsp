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

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.collections.MapUtils" %>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.model.ApplicationType" %>

<%
    String codePrefix = (String) request.getAttribute(FrameworkConstants.REQ_CODE_PREFIX);
    String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
    String disabled = "disabled";
    if (StringUtils.isEmpty(fromPage)){
        fromPage = "";
        disabled = "";
    }
    String projectCode = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
    if (StringUtils.isEmpty(projectCode)) {
    	projectCode = "";
    } 
    Map<String, String> selectedFeatures = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_TEMP_SELECTEDMODULES);
    Map<String, String> selectedJsLibs = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_TEMP_SELECTED_JSLIBS);
    String selectedPilotProj = (String) request.getAttribute(FrameworkConstants.REQ_TEMP_SELECTED_PILOT_PROJ);
    ProjectInfo selectedInfo = (ProjectInfo) session.getAttribute(projectCode);
    
    String configServerNames = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_SERVER_NAMES);
    String configDbNames = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_DB_NAMES);
    
    String externalCode = "";
    String projectVersion = "";
    String groupId = "";
    String artifactId = "";
    if (selectedInfo != null) {
    	if (selectedInfo.getProjectCode() != null) {
    		externalCode = selectedInfo.getProjectCode();
    	}
    	if (selectedInfo.getVersion() != null) {
    		projectVersion = selectedInfo.getVersion();
    	}
    	if (selectedInfo.getGroupId() != null) {
    		groupId = selectedInfo.getGroupId();
    	}
    	if (selectedInfo.getArtifactId() != null) {
    		artifactId = selectedInfo.getArtifactId();
    	}
    }
    
    String customerId = (String) request.getAttribute("customerId");
%>

<!--  Form Starts -->
<form action="next" method="post" autocomplete="off" class="app_add_form" id="formAppInfo" autofocus="autofocus">
    <div class="appInfoScrollDiv">           
		<!--  Name Starts -->
		<div class="clearfix <%=request.getAttribute(FrameworkConstants.REQ_NAME)!= null ? "error" : "" %>" id="nameErrDiv">
		    <label for="xlInput" class="xlInput new-xlInput"><span class="red">*</span> <s:text name="label.name"/></label>
		    <div class="input new-input">
		        <input class="xlarge" id="name" name="name" maxlength="30" title="30 Characters only"
		            type="text"  value ="<%= selectedInfo == null ? "" : selectedInfo.getName() %>" 
		            autofocus="autofocus" placeholder="<s:text name="label.name.placeholder"/>" <%= disabled %> />
		        <span class="help-inline" id="nameErrMsg"></span>
		    </div>
		</div>
		<!--  Name Ends -->
	
		<!--  Code Starts -->
		<div class="clearfix">
		    <label for="xlInput" class="xlInput new-xlInput"><s:text name="label.code"/></label>
		    <div class="input new-input">
		        <input type="hidden" id="code" name="code" value="<%= selectedInfo == null ? codePrefix : selectedInfo.getCode() %>" />
		        <%-- <input class="xlarge" id="internalCode" name="internalCode"
		            type="text"  value ="<%= selectedInfo == null ? codePrefix : selectedInfo.getCode() %>" disabled /> --%>
				<input class="xlarge" id="externalCode" name="externalCode"
		            type="text" maxlength="12" value ="<%= externalCode %>" title="12 Characters only" 
		            placeholder="<s:text name="label.code.placeholder"/>"/>
		    </div>
		</div>
		<!--  Code Ends -->
	                    
		<!--  Description Starts -->
		<div class="clearfix">
		    <s:label for="description" key="label.description" theme="simple" cssClass="new-xlInput"/>
		    <div class="input new-input">
		        <textarea class="appinfo-desc xxlarge" maxlength="150" title="150 Characters only" class="xxlarge" id="textarea" 
		        	name="description" placeholder="<s:text name="label.description.placeholder"/>"><%= selectedInfo == null 
		        	? "" : selectedInfo.getDescription() %></textarea>
		    </div>
		</div>
		<!--  Description Ends -->
		
		<!--  Version Starts -->
		<div class="clearfix">
		    <s:label for="version" key="label.project.version" theme="simple" cssClass="new-xlInput"/>
		    <div class="input new-input">
				<input class="xlarge" id="projectVersion" name="projectVersion" maxlength="20" title="20 Characters only"
					type="text"  value ="<%= StringUtils.isEmpty(fromPage) ? "1.0.0" : projectVersion %>"/>
		    </div>
		</div>
		<!--  Version Ends -->
		
		<!--  GroupId Starts -->
		<%-- <div class="clearfix">
		    <s:label for="groupId" key="label.group.id" theme="simple" cssClass="new-xlInput"/>
		    <div class="input new-input">
				<input class="xlarge" id="groupId" name="groupId" type="text"  value ="<%= groupId %>"/>
		    </div>
		</div> --%>
		<!--  GroupId Ends -->
		
		<!--  ArtifactId Starts -->
		<%-- <div class="clearfix">
		    <s:label for="artifactId" key="label.artifact.id" theme="simple" cssClass="new-xlInput"/>
		    <div class="input new-input">
				<input class="xlarge" id="artifactId" name="artifactId" type="text"  value ="<%= artifactId %>"/>
		    </div>
		</div> --%>
		<!--  ArtifactId -->
	                    
		<!--  Application Type Starts-->
		<div class="clearfix">
		    <div class="input new-input">
		        <ul class="inputs-list">
		            <li> 
		            <%
		                List<ApplicationType> appTypes = (List<ApplicationType>) request.getAttribute(FrameworkConstants.SESSION_APPLICATION_TYPES);
		                String checkedStr = "";
		                if (CollectionUtils.isNotEmpty(appTypes)) {
			                for(ApplicationType applicationType : appTypes) {
			                    String name = applicationType.getName();
			                    String id =applicationType.getId();
			                    if(selectedInfo != null) {
			                        checkedStr = name.equals(selectedInfo.getApplication()) ? "checked" : "";
			                    }
		            %>
				                <input type="radio" name="application" id="<%= id %>" value="<%= id %>" <%= checkedStr %> <%= disabled %>/> 
				                <span class="textarea_span"><%= name %></span>
		            <% 
		            		}
		                }
		            %>
		            </li>
		        </ul>
		    </div>
		</div>
		<!--  Application Type Ends-->
	                    
		<!--  Dependencies are loaded -->
		<div class="Create_project_inner" id="AjaxContainer"></div>
	</div>
	
    <!--  Submit and Cancel buttons Starts -->
    <div class="actions">
        <input id="next" type="submit" value="<s:text name="label.next"/>" class="primary btn createProject_btn">
        <input type="button" id="cancel" value="<s:text name="label.cancel"/>" class="primary btn">
    </div>
    <!--  Submit and Cancel buttons Ends -->
    
    <!-- Hidden Fields -->
    <input type="hidden" id="configServerNames" name="configServerNames" value="<%= configServerNames == null ? "" : configServerNames %>">
	<input type="hidden" id="configDbNames" name="configDbNames" value="<%= configDbNames == null ? "" : configDbNames %>">
	<input type="hidden" id="selectedPilotProj" name="selectedPilotProj" value="<%= selectedPilotProj %>">
    <input type="hidden" name="fromTab" value="appInfo">
    <% if (MapUtils.isNotEmpty(selectedFeatures)) { %>
   		<input type="hidden" id="selectedFeatures" name="selectedFeatures" value="<%= selectedFeatures %>">
   	<% } 	
   	   if (MapUtils.isNotEmpty(selectedJsLibs)) { %>
   		<input type="hidden" id="selectedJsLibs" name="selectedJsLibs" value="<%= selectedJsLibs %>">
   	<% } %>
   	<input type="hidden" name="customerId" value="<%= customerId %>">
   	<input type="hidden" name="fromPage" value="<%= fromPage %>">
   	
</form> 
<!--  Form Ends -->
    
<script type="text/javascript">
	/* To check whether the divice is ipad or not */
	if (!isiPad()) {
		$(".appInfoScrollDiv").scrollbars();
	}
	
    $(document).ready(function() {
		$("#name").focus();
    	escPopup();
        checkDefault();
        changeStyle("appinfo");
        $("input[name='application']").click(function() {
            changeApplication();
        });
        
        // To restrict the user in typing the special charaters
        $('#name').bind('input propertychange', function (e) {
        	var projNname = $(this).val();
        	projNname = checkForSplChr(projNname);
        	$(this).val(projNname);
        	codeGenerate(projNname);
        });
        
		$('form').submit(function() {
			disableScreen();
			showLoadingIcon($("#loadingIconDiv"));
			performAction('features', $('#formAppInfo'), $("#tabDiv"));
			return false;
		});
		
		$('#cancel').click(function() {
	    	showLoadingIcon($("#tabDiv")); // Loading Icon
			performAction('applications', $('#formAppInfo'), $('#container'));
		});
		
		$("#externalCode").bind('input propertychange',function(e) { 
	    	var name = $(this).val();
	    	name = checkForCode(name);
	    	$(this).val(name);
	    });
	    
	    $('#projectVersion').bind('input propertychange', function (e) { 	
	    	var version = $(this).val();
	    	version = checkForVersion(version);
	    	$(this).val(version);
	    });
	    
		window.setTimeout(function () { document.getElementById('name').focus(); }, 250);
	});

	//This function is to handle the change event for application radio
	function changeApplication() {
		performAction('applicationType', $('#formAppInfo'), $('#AjaxContainer'));
	}

	function codeGenerate(projNname) {
		var name = projNname;
		var photonPrefix = "<%= codePrefix %>";
        photonPrefix = photonPrefix + name;
        photonPrefix = photonPrefix.replace(/\s/g, '');
        $("#internalCode").val(photonPrefix);
        $("#code").val(photonPrefix);
    }
    
    function checkDefault() {
        var $radios = $("input[name='application']");
        if ($radios.is(':checked') === false) {
            $radios.filter("[value='apptype-webapp']").attr('checked', true);
        }
        changeApplication();
    }
    
    function validationError(data) {
    	enableScreen();
		$(".clearfix").removeClass("error");
    	$(".help-inline").text("");
    	if (data.nameError != undefined) {
    		$("#nameErrMsg").text(data.nameError);
        	$("#nameErrDiv").addClass("error");
        	$("#name").focus();
    	} 
    	if (data.nameError == "Invalid Name") {
    		$("#name").val("");
    		$("#name").focus();
    	}
    }
    
    function hideServerAndDatabase(technology) {
		if (technology == "tech-java-standalone") {
			$("#server").hide();
		} else {
			$("#server").show();
		}
	}
    
    function successEvent(pageUrl, data) {
		if (pageUrl == "getAllVersions") {
			if (fillCheckBoxVersion(versionFor, data.versions)) {
				makeVersionsSelected();
			}
		} else if (pageUrl == "techVersions") {
			if (data.techVersions != undefined && data.techVersions != "") {
				$("#technologyVersionDiv").show();
                if (fillVersions("techVersion", data.techVersions)) {
					showPrjtInfoTechVersion();
                }
	        } else {
				$("#technologyVersionDiv").hide();
	        }
			var technology = $("#technology").val();
			hideServerAndDatabase(technology);
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
</script>