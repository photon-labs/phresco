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

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.collections.MapUtils" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.model.ApplicationType" %>

<!--  Heading Starts -->
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
%>

<!--  Form Starts -->
<form action="next" method="post" autocomplete="off" class="app_add_form" autofocus="autofocus">
    <div class="appInfoScrollDiv">           
		<!--  Name Starts -->
		<div class="clearfix <%=request.getAttribute(FrameworkConstants.REQ_NAME)!= null ? "error" : "" %>" id="nameErrDiv">
		    <label for="xlInput" class="xlInput new-xlInput"><span class="red">*</span> <s:text name="label.name"/></label>
		    <div class="input new-input">
		        <input class="xlarge" id="name" name="name" maxlength="30" title="30 Characters only"
		            type="text"  value ="<%= selectedInfo == null ? "" : selectedInfo.getName() %>" 
		            autofocus="autofocus" placeholder="Name of the project" <%= disabled %> />
		        <span class="help-inline" id="nameErrMsg">
		           
		        </span>
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
		            type="text" maxlength="12" value ="<%= externalCode %>" title="12 Characters only" placeholder="Project code"/>
		    </div>
		</div>
		<!--  Code Ends -->
	                    
		<!--  Description Starts -->
		<div class="clearfix">
		    <s:label for="description" key="label.description" theme="simple" cssClass="new-xlInput"/>
		    <div class="input new-input">
		        <textarea class="appinfo-desc xxlarge" maxlength="150" title="150 Characters only" class="xxlarge" id="textarea" 
		        name="description" placeholder="Description of the project"><%= selectedInfo == null 
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
			                for(ApplicationType applicationType : appTypes) {
			                    String name = applicationType.getName();
			                    String displayName = applicationType.getDisplayName();
			                    
			                    if(selectedInfo != null) {
			                        checkedStr = name.equals(selectedInfo.getApplication()) ? "checked" : "";
			                    }
			            %>
			                <input type="radio" name="application" id="<%= name %>" value="<%= name %>" <%= checkedStr %> <%= disabled %>/> 
			                <span class="textarea_span"><%= displayName %></span>
			            <% } %>
		            </li>
		        </ul>
		    </div>
		</div>
		<!--  Application Type Ends-->
	                    
		<!--  Dependecies are loaded -->
		<div class="Create_project_inner" id="AjaxContainer"></div>
	</div>
    <!--  Submit and Cancel buttons Starts -->
    <div class="actions">
    	<input type="hidden" id="configServerNames" name="configServerNames" value="<%= configServerNames == null ? "" : configServerNames %>">
    	<input type="hidden" id="configDbNames" name="configDbNames" value="<%= configDbNames == null ? "" : configDbNames %>">
    
    	<% if (MapUtils.isNotEmpty(selectedFeatures)) { %>
    		<input type="hidden" id="selectedFeatures" name="selectedFeatures" value="<%= selectedFeatures %>">
    	<% } 	
    	   if (MapUtils.isNotEmpty(selectedJsLibs)) { %>
    		<input type="hidden" id="selectedJsLibs" name="selectedJsLibs" value="<%= selectedJsLibs %>">
    	<% } %>
    	<input type="hidden" id="selectedPilotProj" name="selectedPilotProj" value="<%= selectedPilotProj %>">
    	<input type="hidden" name="fromTab" value="appInfo">
        <input id="next" type="submit" value="<s:text name="label.next"/>" class="primary btn createProject_btn">
        <input type="button" id="cancel" value="<s:text name="label.cancel"/>" class="primary btn">
    </div>
    <!--  Submit and Cancel buttons Ends -->
</form> 
<!--  Form Ends -->
    
<script type="text/javascript">
	/* To check whether the divice is ipad or not */
	if(!isiPad()){
	    /* JQuery scroll bar */
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
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			params = params.concat("fromPage=");
			params = params.concat('<%= fromPage %>');
			performAction('features', params, $("#tabDiv"));
			return false;
		});
		
		$('#cancel').click(function() {
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			params = params.concat("fromPage=");
			params = params.concat("edit");
	    	showLoadingIcon($("#tabDiv")); // Loading Icon
			performAction('applications', params, $('#container'));
		});
		
		window.setTimeout(function () { document.getElementById('name').focus(); }, 250);
	});

	//This function is to handle the change event for application radio
	function changeApplication() {
		var params = "applicationType=";
		params = params.concat($("input[name='application']:checked").val());
		params = params.concat("&" + '<%= FrameworkConstants.REQ_FROM_PAGE %>' + "=");
		params = params.concat('<%= fromPage %>');
		popup('applicationType', params, $('#AjaxContainer'));
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
    
    $("#externalCode").bind('input propertychange',function(e){ 
    	var name = $(this).val();
    	name = checkForCode(name);
    	$(this).val(name);
     });
    
    $('#projectVersion').bind('input propertychange', function (e) { 	
    	var version = $(this).val();
    	version = checkForVersion(version);
    	$(this).val(version);
     });
  
</script>