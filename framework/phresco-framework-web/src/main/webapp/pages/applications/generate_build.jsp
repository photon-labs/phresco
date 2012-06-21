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

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.util.XCodeConstants"%>
<%@ page import="com.photon.phresco.util.AndroidConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.framework.commons.ApplicationsUtil"%>
<%@ page import="com.photon.phresco.framework.commons.PBXNativeTarget"%>
<%@ page import="com.photon.phresco.configuration.Environment"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.model.BuildInfo"%>

<script src="js/reader.js" ></script>
<script src="js/select-envs.js"></script>

<%
	String defaultEnv = "";
	Project project = (Project) request.getAttribute(FrameworkConstants.REQ_PROJECT);
   	String projectCode = (String)project.getProjectInfo().getCode();
   	String from = (String) request.getAttribute(FrameworkConstants.REQ_BUILD_FROM);
   	String technology = (String)request.getAttribute(FrameworkConstants.REQ_TECHNOLOGY);
   	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
   	String importSqlPro  = (String) request.getAttribute(FrameworkConstants.REQ_IMPORT_SQL);
   	String buildNumber = (String) request.getAttribute(FrameworkConstants.REQ_DEPLOY_BUILD_NUMBER);
   	String checkImportSql = "";
   	if (importSqlPro != null && Boolean.parseBoolean(importSqlPro)) {
   	    checkImportSql = "checked";
   	}
   	//xcode targets
   	List<PBXNativeTarget> xcodeConfigs = (List<PBXNativeTarget>) request.getAttribute(FrameworkConstants.REQ_XCODE_CONFIGS);
   	List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
   	List<String> buildInfoEnvs = (List<String>) request.getAttribute(FrameworkConstants.BUILD_INFO_ENVS);
   	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
   	// mac sdks
   	List<String> macSdks = (List<String>) request.getAttribute(FrameworkConstants.REQ_IPHONE_SDKS);
%>

<form action="build" method="post" autocomplete="off" class="build_form" id="generateBuildForm"> 
<div class="popup_Modal" id="generateBuild_Modal">
	<div class="modal-header">
		<h3 id="generateBuildTitle">
		<%if (from.equals("nodeJS_runAgnSrc") || from.equals("runAgnSrc")) {%>
				<s:text name="label.runagainsrc"/>
			<% } else if (from.equals(FrameworkConstants.DEPLOY)) {%>
				<s:text name="label.deploy"/>
			<% } else { %>
				<s:text name="label.generatebuild"/>
			<% } %>
		</h3>
		<a class="close" href="#" id="close">&times;</a>
	</div>

	<!-- For build alone starts-->
	<input name="<%= FrameworkConstants.REQ_DEPLOY_BUILD_NUMBER %>" type="hidden" value="<%= buildNumber %>"/>
	<!-- For build alone ends -->
			
	<div class="modal-body">

		<% if (CollectionUtils.isNotEmpty(projectModules) && from.equals("generateBuild")) {  %>
            <div id="agnBrowser" class="build server">
				<!-- Modules -->
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.modules"/></label>
					<div class="input">
						<select id="projectModule" name="projectModule" class="xlarge" >
						 <%
						       for(String projectModule : projectModules) {
						 %>
								<option value="<%= projectModule%>"> <%= projectModule %></option>
						 <% } %>
						</select>
					</div>
				</div>
            </div>
        <% } %>
        
        <% if (from.equals("generateBuild")) { %>
		        <div class="clearfix">
				    <label for="xlInput" class="xlInput popup-label"><s:text name="label.build.name"/></label>
				    <div class="input">
						<input type="text" class="xlarge" id="buildName" name="buildName" maxlength="20" title="20 Characters only"/>
				    </div>
				</div>
				<div class="clearfix">
				    <label for="xlInput" class="xlInput popup-label"><s:text name="label.build.number"/></label>
				    <div class="input">
						<input type="text" class="xlarge" id="newBuildNumber" name="newBuildNumber" maxlength="20" title="10 Characters only"/>
				    </div>
				</div>
		<% } %>

		<div class="clearfix">
		    <label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.environment"/></label>
		    <div class="input">
		    	<% if (from.equals("generateBuild")) { %>
		    		<div class="generate_build">
			        	<ul id="environments" name="environment" class="xlarge">
				        	<li class="config_tab">
								<s:text name="label.configuration"/>
							</li>
				        	<%
								String selectedStr = "";
								if(environments != null) {
									for (Environment environment : environments) {
										if(environment.isDefaultEnv()) {
											defaultEnv = environment.getName();
											selectedStr = "Disabled Checked";
										} else {
											selectedStr = "";
										}
							%>
										<li class="environment_list">
											<input type="checkbox" id="environments" value="<%= environment.getName() %>" <%= selectedStr %> onClick="selectEnvs()">&nbsp;<%= environment.getName() %>
										</li>
							<% 		
									} 
								}
							%>
			        </ul>
		        </div>
		        <% } else { %>
		        	<select id="environments" name="environment" class="xlarge">
		        		<% 
		        			if (from.equals(FrameworkConstants.DEPLOY) && buildInfoEnvs != null && CollectionUtils.isNotEmpty(buildInfoEnvs)) {
		        				for (String env : buildInfoEnvs) {
		        		%>
		        				<option value="<%= env %>"><%= env %></option>
		        				<% } %>
		        		<% } else { %>
					        	<optgroup label="Configurations" class="optgrplbl">
								<%
									String selectedStr = "";
									if(environments != null) {
										for (Environment environment : environments) {
											if(environment.isDefaultEnv()) {
												defaultEnv = environment.getName();
												selectedStr = "selected";
											} else {
												selectedStr = "";
											}
								%>
										<option value="<%= environment.getName() %>" <%= selectedStr %> onClick="selectEnvs()"><%= environment.getName() %></option>
								<% 		} 
									}
								%>
								</optgroup>
						<% } %>
					</select>
				<% } %>
			</div>
		</div>

		<% 
			if (TechnologyTypes.ANDROIDS.contains(technology)) { 
				String pilotProjectName = project.getProjectInfo().getPilotProjectName();
		%>
			<!-- Android Version -->
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.sdk"/></label>
				<div class="input">
					<select id="androidVersion" name="androidVersion" class="xlarge" >
						<%
							int initialVer =  StringUtils.isEmpty(pilotProjectName) ? 0 : 1;
							for (int i = initialVer; i < AndroidConstants.SUPPORTED_SDKS.length; i++) {
						%>
							<option value="<%= AndroidConstants.SUPPORTED_SDKS[i] %>"><%= AndroidConstants.SUPPORTED_SDKS[i] %></option>
						<% } %>
					</select>
				</div>
			</div>
		<% } %>	
		
		<% if (TechnologyTypes.IPHONES.contains(technology)) { %>
			<!-- SDK -->
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.sdk"/></label>
				<div class="input">
					<select id="sdk" name="sdk" class="xlarge" >
						<%
							if (macSdks != null) {
								for (String sdk : macSdks) {
						%>
							<option value="<%= sdk %>"><%= sdk %></option>
						<% 
								} 
							}
						%>
					</select>
				</div>
			</div>
		
			<!-- TARGET -->
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.target"/></label>
				<div class="input">
					<select id="target" name="target" class="xlarge" >
					<% if (xcodeConfigs != null) { 
							for (PBXNativeTarget xcodeConfig : xcodeConfigs) {
						%>
							<option value="<%= xcodeConfig.getName() %>"><%= xcodeConfig.getName() %></option>
						<% } 
					} %>	
			       </select>
				</div>
			</div>
			
			<!-- Mode -->
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.mode"/></label>
				<div class="input">
					<select id="mode" name="mode" class="xlarge" >
						<option value="<%= XCodeConstants.CONFIGURATION_DEBUG %>"><%= XCodeConstants.CONFIGURATION_DEBUG %></option>
						<option value="<%= XCodeConstants.CONFIGURATION_RELEASE %>"><%= XCodeConstants.CONFIGURATION_RELEASE %></option>
					</select>
				</div>
			</div>
		<% } %>	
		
		<fieldset class="popup-fieldset">
			<!-- Show Settings -->
			<div class="clearfix">
				<div class="xlInput">
					<ul class="inputs-list">
						<li class="popup-li">
							<% if (!from.equals(FrameworkConstants.DEPLOY)) { %>
								<input type="checkbox" id="showSettings" name="showSettings" value="showsettings">
								<span class="textarea_span popup-span"><s:text name="label.show.setting"/></span>
							<% } %>
							<% if (from.equals("generateBuild")) { %>
								<input type="checkbox" id="skipTest" name="skipTest" value="true">
								<span class="textarea_span popup-span"><s:text name="label.skiptest"/></span>
							<% } %>
							<% if (from.equals("generateBuild") || from.equals(FrameworkConstants.DEPLOY)) { %>
								<input type="checkbox" id="showError" name="showError" value="true">
								<span class="textarea_span popup-span"><s:text name="label.show.error"/></span>
								<input type="checkbox" id="hideLog" name="hideLog" value="true">
								<span class="textarea_span popup-span"><s:text name="label.hide.log"/></span>
								<input type="checkbox" id="showDebug" name="showDebug" value="true">
								<span class="textarea_span popup-span"><s:text name="label.show.debug"/></span>
							<% } %>
							<% if (!from.equals("generateBuild") && CollectionUtils.isNotEmpty(project.getProjectInfo().getTechnology().getDatabases())) {%>
								<input type="checkbox" id="importSql" name="importSql" value="true" <%= checkImportSql%> >
								<span class="textarea_span popup-span"><s:text name="label.import.sql"/></span>
							<% } %>
							<% if (from.equals("generateBuild") && TechnologyTypes.ANDROIDS.contains(technology)) { %>
								<input type="checkbox" id="proguard" name="proguard" value="false" disabled="disabled">
								<span class="textarea_span popup-span"><s:text name="label.progurad"/></span>
							<% } %>
						</li>
					</ul>
				</div>	
			</div>
		</fieldset>
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action">
			<div id="errMsg" style="width:72%; text-align: left;"></div>
			<input type="hidden" name="from" value="<%= from %>" id="from">
			<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
			<% if (from.equals("nodeJS_runAgnSrc")) {%>
				<input type="button" id="runAgainstSrc" class="btn primary" value="<s:text name="label.run"/>">
			<% } else if (from.equals("runAgnSrc")) {%>
				<input type="button" id="javaRunAgainstSrc" class="btn primary" value="<s:text name="label.run"/>">
			<% } else if (from.equals(FrameworkConstants.DEPLOY)) {%>	
				<input type="button" id="deploy" class="btn primary" value="<s:text name="label.deploy"/>">
			<% } else { %>
				<input type="button" id="build" class="btn primary" value="<s:text name="label.build"/>">
			<% } %>
		</div>
	</div>
</div>
</form> 

<script type="text/javascript">
    
	if(!isiPad()){
	    /* JQuery scroll bar */
		$(".generate_build").scrollbars();
	}

	if (<%= TechnologyTypes.IPHONES.contains(technology) %> || <%= TechnologyTypes.ANDROIDS.contains(technology) %> ) {
		$("#importSql").attr("disabled", "disabled");
	}
	
	if (<%= TechnologyTypes.SHAREPOINT.equals(technology) %> || <%= TechnologyTypes.DOT_NET.equals(technology) %>) {
        $("#importSql").attr("checked", false); 
        $("#importSql").attr("disabled", "disabled"); 
	}
	
	var url = "";
	var readerSession = "";
	
	$(document).ready(function() {
		$('#close, #cancel').click(function() {
			showParentPage();
		});

		$('#build').click(function() {
			buildValidateSuccess("build", '<%= FrameworkConstants.REQ_BUILD %>');
		});
		
		$('#deploy').click(function() {
			buildValidateSuccess("deploy", '<%= FrameworkConstants.REQ_FROM_TAB_DEPLOY %>');
		});
		
		/** NodeJS run against source **/
		$('#runAgainstSrc').click(function() {
			buildValidateSuccess('NodeJSRunAgainstSource', '<%= FrameworkConstants.REQ_READ_LOG_FILE %>');
		});
		
		/** Java run against source **/
		$('#javaRunAgainstSrc').click(function() {
			buildValidateSuccess('runAgainstSource', '<%= FrameworkConstants.REQ_JAVA_START %>');
		});
		
		$('#importSql').change(function() {
			var isChecked = $('#importSql').is(":checked");
			if (isChecked) {
				$("#errMsg").html('<%= FrameworkConstants.EXEC_SQL_MSG %>');
		    } else {
		    	$("#errMsg").empty();
		    }
		});
		
		$('#hideLog').change(function() {
			var isChecked = $('#hideLog').is(":checked");
			if (isChecked) {
				$("#errMsg").html('<%= FrameworkConstants.HIDE_LOG_MSG %>');
		    } else {
		    	$("#errMsg").empty();
		    }
		});
		
	});
		
	function checkObj(obj) {
		if(obj == "null" || obj == undefined) {
			return "";
		} else {
			return obj;
		}
	}
	
	function buildValidateSuccess(lclURL, lclReaderSession) {
		url = lclURL;
		readerSession = lclReaderSession;
		checkForConfig();
	}
	
	function successEnvValidation(data) {
		if(data.hasError == true) {
			showError(data);
		} else {
			$('.build_cmd_div').css("display", "block");
			$("#build-output").empty();
			showParentPage();
			if(url == "build") {
				$("#warningmsg").show();
				$("#build-output").html("Generating build...");
			} else if(url == "deploy") {
				$("#build-output").html("Deploying project...");
			} else {
				$("#build-output").html("Server is starting...");
				disableControl($("#nodeJS_runAgnSrc"), "btn disabled");
				disableControl($("#runAgnSrc"), "btn disabled");
			}
			performUrlActions(url, readerSession);
		}
	}
	
	function performUrlActions(url, testType) {
		var params = "";
		params = params.concat("&environments=");
		params = params.concat(getSelectedEnvs());
		readerHandlerSubmit(url, '<%= projectCode %>', testType, params, true);
	}
	
	/** This method is to enforce the use of default environment **/
	function selectEnvs() {
		var from = $("#from").val();
		if (from == "generateBuild") {
			$("input[value='<%= defaultEnv %>']").attr("checked", "checked");
			$("input[value='<%= defaultEnv %>']").attr("disabled", "disabled");
		}
	}
</script>