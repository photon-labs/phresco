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
   	String finalName = (String) request.getAttribute(FrameworkConstants.FINAL_NAME);
   	String mainClassValue = (String) request.getAttribute(FrameworkConstants.MAIN_CLASS_VALUE);
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
<div class="popup_Modal topFouty" id="generateBuild_Modal">
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
						<input type="text" placeholder="specify Build name" class="xlarge javastd" id="userBuildName" name="userBuildName" maxlength="20" title="20 Characters only"/>
				    </div>
				</div>
				<div class="clearfix">
				    <label for="xlInput" class="xlInput popup-label"><s:text name="label.build.number"/></label>
				    <div class="input">
						<input type="text" placeholder="specify Build number" class="xlarge javastd" id="userBuildNumber" name="userBuildNumber" maxlength="20" title="10 Characters only"/>
				    </div>
				</div>
				
				<% if (TechnologyTypes.JAVA_STANDALONE.contains(technology)) { %>
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label "><s:text name="label.jar.name"/></label>
				    <div class="input">
						<input type="text" class="xlarge javastd" id="jarName" name="jarName" value="<%= StringUtils.isNotEmpty(finalName) ? finalName : "" %>" maxlength="40" title="40 Characters only"/>
				    </div>
				</div>
				
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.main.class.name"/></label>
				    <div class="input">
						<input type="text" class="xlarge javastd" id="mainClassName" name="mainClassName" value="<%= StringUtils.isNotEmpty(mainClassValue) ? mainClassValue : "" %>" maxlength="40" title="40 Characters only"/>
				    </div>
				</div>	
		<% } %>	
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
		
		<fieldset class="popup-fieldset fieldset_center_align">
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
							<%-- <% if (from.equals("generateBuild") && TechnologyTypes.ANDROIDS.contains(technology)) { %>
								<input type="checkbox" id="proguard" name="proguard" value="false" disabled="disabled">
								<span class="textarea_span popup-span"><s:text name="label.progurad"/></span>
							<% } %> --%>
						</li>
					</ul>
				</div>	
			</div>
		</fieldset>
<!-- 		advanced settingd -->
		<% if (from.equals("generateBuild") && TechnologyTypes.ANDROIDS.contains(technology)) { %>
		<div class="theme_accordion_container clearfix" style="float: none;">
		    <section class="accordion_panel_wid">
		        <div class="accordion_panel_inner adv-settings-accoridan-inner">
		            <section class="lft_menus_container adv-settings-width">
		                <span class="siteaccordion" id="siteaccordion_active"><span><s:text name="build.advanced.settings"/></span></span>
		                <div class="mfbox siteinnertooltiptxt">
		                    <div class="scrollpanel adv_setting_accordian_bottom">
		                        <section class="scrollpanel_inner">
									<fieldset class="popup-fieldset fieldset_center_align">
										<!-- Show Settings -->
										<div class="clearfix">
											<div class="xlInput">
												<ul class="inputs-list">
													<li class="popup-li">
															<input type="checkbox" id="proguard" name="proguard" value="false"  disabled="disabled">
															<span class="textarea_span popup-span"><s:text name="label.progurad"/></span>
															
															<input type="checkbox" id="signing" name="signing" value="false">
															<span class="textarea_span popup-span"><a href="#" class="popup-span" id="androidSigning" ><s:text name="label.signing"/></a></span>
													</li>
												</ul>
											</div>	
										</div>
									</fieldset>
		                        </section>
		                    </div>
		                </div>
		            </section>  
		        </div>
		    </section>
		</div>
		<input id="profileAvailable" name="profileAvailable" type="hidden" value=""/>
		<% } %>
<!-- 		advanced settings end -->
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action">
			<div id="errMsg" class="generate_build_err_msg adv-settings-error-msg"></div>
			<div style="float: right;">
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
</div>
</form> 

<script type="text/javascript">
    
	<%
		if (TechnologyTypes.ANDROIDS.contains(technology) && (Boolean) request.getAttribute(FrameworkConstants.REQ_ANDROID_HAS_SIGNING)) {
	%>
		$('#profileAvailable').val("true");
	<%
		}
	%>

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
		// accodion for advanced issue
		accordion();
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});

		$('#build').click(function() {
			if ($('input[type=checkbox][name=signing]').is(':checked') && isBlank($('#profileAvailable').val())) {
				$("#errMsg").html('<%= FrameworkConstants.PROFILE_CREATE_MSG %>');
				return false;
			}
			buildValidateSuccess("build", '<%= FrameworkConstants.REQ_BUILD %>');
		});
		
		$('#userBuildNumber').bind('input propertychange', function (e) { 	//userBuildNumber validation
			var userBuildNumber = $(this).val();
			userBuildNumber = checkForNumber(userBuildNumber);
        	$(this).val(userBuildNumber);
		});
		
		$('#userBuildName').bind('input propertychange', function (e) { 	//userBuildName validation
			var userBuildName = $(this).val();
			userBuildName = checkForSplChr(userBuildName);
        	$(this).val(userBuildName);
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
		
		$('#androidSigning').click(function() {
// 			if ($(this).is(':checked')) {
				// remove existing duplicate div
				$('#advancedSettingsBuildForm').remove();
				showAdvSettingsConfigure();
// 			} else {
// 				removeAdvSettings();
// 			}
		})
		
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
	
	function showAdvSettingsConfigure() {
		showPopup();
		popup('advancedBuildSettings', '', $('#popup_div'), '', true);
	}
	
	function removeAdvSettings() {
// 		alert("Remove settings configure");
	}
</script>