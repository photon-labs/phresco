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
<%@page import="java.util.HashMap"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>

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
   	List<String> macSdks = (List<String>) session.getAttribute(FrameworkConstants.REQ_IPHONE_SDKS);
   	
   	Map<String, String> jsMap = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_MINIFY_MAP);
   	String fileLoc = (String) request.getAttribute("fileLocation");
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
						<input type="text" placeholder="<s:text name="build.name"/>" class="xlarge javastd" id="userBuildName" name="userBuildName" maxlength="20" title="20 Characters only"/>
				    </div>
				</div>
				<div class="clearfix">
				    <label for="xlInput" class="xlInput popup-label"><s:text name="label.build.number"/></label>
				    <div class="input">
						<input type="text" placeholder="<s:text name="build.number"/>" class="xlarge javastd" id="userBuildNumber" name="userBuildNumber" maxlength="6" title="6 Characters only"/>
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
						<input type="text" class="xlarge javastd" id="mainClassName" name="mainClassName" 
							value="<%= StringUtils.isNotEmpty(mainClassValue) ? mainClassValue : "" %>" 
							maxlength="40" title="40 Characters only"/>
				    </div>
				</div>	
			<% } %>	
			
			<% if (TechnologyTypes.BLACKBERRY_HYBRID.contains(technology)) { %>
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label "><s:text name="label.keypassword"/></label>
				    <div class="input">
						<input type="password" placeholder="<s:text name="Enter the Password"/>" class="xlarge javastd" 
							name="keypass" maxlength="20" title="20 Characters only"/>
				    </div>
				</div>								
			<% } %>	
			
		
			<% if (TechnologyTypes.WIN_METRO.contains(technology)) { %>
					<div class="clearfix">
						<label for="xlInput" class="xlInput popup-label "><s:text name="label.configuration"/></label>
					    <div class="input">
							<select name="configuration" class="xlarge">
								<option value="Release">Release</option>
								<option value="Debug">Debug</option>
							</select>
					    </div>
					</div>
					<div class="clearfix">
						<label for="xlInput" class="xlInput popup-label"><s:text name="label.platform"/></label>
					    <div class="input">
							<select name="platform" class="xlarge">
								<option value="Any CPU">Any CPU</option>
								<option value="ARM">ARM</option>
								<option value="X86">X86</option>
								<option value="X64">X64</option>
							</select>
					    </div>
					</div>	
			<% } %>	
			
			<% if (TechnologyTypes.WIN_PHONE.contains(technology)) { %>
					
					<div class="clearfix">
						<label for="xlInput" class="xlInput popup-label "><s:text name="label.configuration"/></label>
					    <div class="input">
							<select name="configuration" class="xlarge">
								<option value="Release">Release</option>
								<option value="Debug">Debug</option>
							</select>
					    </div>
					</div>
			<% } %>	
			
		<% } %>
		

		<div class="clearfix">
		    <label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.environment"/></label>
		    <div class="input">
		    	<% if (from.equals("generateBuild") && !TechnologyTypes.MOBILES.contains(technology)) { %>
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
							<% } %> <%
 	if (FrameworkConstants.REQ_GENERATE_BUILD.equals(from)
 			&& (TechnologyTypes.JAVA_STANDALONE.equals(technology)
 					|| TechnologyTypes.JAVA_WEBSERVICE
 							.equals(technology)
 					|| TechnologyTypes.JAVA.equals(technology)
 					|| TechnologyTypes.HTML5.equals(technology)
 					|| TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET
 							.equals(technology)
 					|| TechnologyTypes.HTML5_MOBILE_WIDGET
 							.equals(technology)
 					|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET
 							.equals(technology) || TechnologyTypes.HTML5_WIDGET
 					.equals(technology))) {
 %>
								<input type="checkbox" id="skipTest" name="skipTest" value="true">
								<span class="textarea_span popup-span"><s:text name="label.skip.unit.test"/></span>
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
		
		<!-- sql execution starts  -->
		<fieldset class="popup-fieldset fieldset_center_align" id="sqlExecutionContain" style="display: none;">
			<legend class="fieldSetLegend"><s:text name="label.sql.execute"/></legend>
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label" style="width: 210px;"><s:text name="label.databases"/></label>
				<div class="input" style="text-align: left; margin-left: 231px;">
					<select id="databases" name="database" class="xlarge" >
			       	</select>
				</div>
			</div>
	        <table>
	            <tbody>
					<tr>
		                <td style="border-bottom: none;">
	                        <select id="avaliableSourceScript" multiple="multiple" style="height: 150px; width:200px;">
	
	                        </select>                     
		                </td>
		                <td style="border-bottom: none; padding-left: 15px; padding-right: 15px;">
		                	<ul style="list-style : none;">
		                		<li style="padding-bottom: 5px; margin-top:10px;"><input type="button" class="btn primary" id="btnAddAll" value=">>" style="width:30px;"></li>
		                		<li style="padding-bottom: 5px;"><input type="button" class="btn primary" id="btnAdd" value=">" style="width:30px;"></li>
								<li style="padding-bottom: 5px;"><input type="button" class="btn primary" id="btnRemove" value="<" style="width:30px;"></li>
								<li style="padding-bottom: 5px;"><input type="button" class="btn primary" id="btnRemoveAll" value="<<" style="width:30px;"></li>
		                	</ul>
		                </td>
		                <td style="border-bottom: none; padding-left: 31px;">
	                        <select id="selectedSourceScript" name="selectedSourceScript" multiple="multiple" style="height: 150px; width:200px;">
							</select>
		                </td>
		                <td style="border-bottom: none;padding-right: 25px">
	                		<img src="images/icons/top_arrow.png" title="Move up" id="up" style="cursor: pointer;"><br>
	                		<img src="images/icons/btm_arrow.png" title="Move down" id="down" style="cursor: pointer; margin-top: 16px;" >
		                </td>
	            	</tr>
	        	</tbody>
			</table>
			<input id="DbWithSqlFiles" value="" type="hidden">
			<%-- <div style="text-align: left;padding-left: 2%; padding-bottom: 5px; font-weight: bold;">
				<input type="checkbox" id="rollBack" name="rollBack" /> &nbsp;<span class="textarea_span popup-span"><s:text name="label.rollback"/></span>
			</div> --%>
		</fieldset>
		<!-- sql execution ends  -->
		
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
															<input type="checkbox" id="proguard" name="proguard" value="false">
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

<!-- minifier setting starts -->
		<% if (from.equals("generateBuild") && (TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(technology) || 
				TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET.equals(technology) || 
				TechnologyTypes.HTML5_MOBILE_WIDGET.equals(technology) ||
				TechnologyTypes.HTML5_WIDGET.equals(technology))) { %>
			<div class="theme_accordion_container clearfix" style="float: none;">
			    <section class="accordion_panel_wid">
			        <div class="accordion_panel_inner adv-settings-accoridan-inner">
			            <section class="lft_menus_container adv-settings-width">
			                <span class="siteaccordion" id="siteaccordion_active"><span><s:text name="build.minifer"/></span></span>
			                <div class="mfbox siteinnertooltiptxt">
			                    <div class="scrollpanel adv_setting_accordian_bottom">
			                        <section class="scrollpanel_inner">
			                        	<div class="minifyDiv">
											<fieldset class="popup-fieldset fieldset_center_align minify_popup">
												<% 
													if (jsMap != null && !jsMap.isEmpty()) {
														Set keys = jsMap.keySet();
														Iterator iter = jsMap.keySet().iterator();
													    while (iter.hasNext()) {
														    String key = (String) iter.next();
														    String val = (String) jsMap.get(key);
												%>
															<div class = "browseJs">
																<div class="clearfix">
																	<label for="xlInput" class="xlInput popup-label minifyLbl"><s:text name="build.js.minification"/></label>
																	<div class="input">
																		<input type="button" id="<%= key %>" class="btn primary chooseJS" value="<s:text name="build.minify.browse"/>" onclick="browseFiles(this);">
																		<label for="xlInput" class="xlInput popup-label compNameLbl"><s:text name="build.compress.name"/></label>
																		<input type="text" name="jsFileName" class="<%= key %>" id="compNameText" disabled value="<%= key %>"/>
																	</div>
																	<a><img title="" src="images/icons/add_icon.png" id="addJSComp" class="minifyAddIcon" onclick="addJsCompTag();"></a>
																	<a><img class="del imagealign hide" src="images/icons/minus_icon.png" onclick="removeTag(this);"></a>
																</div>
																<input type="hidden" tempName="<%= key %>" name="<%= key %>" value="<%= val %>" id="selectedJs">
															</div>
												<%
														}
													} else {
												%>
												<div class = "browseJs">
														<div class="clearfix">
															<label for="xlInput" class="xlInput popup-label minifyLbl"><s:text name="build.js.minification"/></label>
															<div class="input">
																<input type="button" id="getJsFiles1" class="btn primary chooseJS" value="<s:text name="build.minify.browse"/>" onclick="browseFiles(this);">
																<label for="xlInput" class="xlInput popup-label compNameLbl"><s:text name="build.compress.name"/></label>
																<input type="text" name="jsFileName" class="getJsFiles1" id="compNameText" disabled/>
															</div>
															<a><img title="" src="images/icons/add_icon.png" id="addJSComp" class="minifyAddIcon" onclick="addJsCompTag();"></a>
															<a><img class="del imagealign hide" src="images/icons/minus_icon.png" onclick="removeTag(this);"></a>
														</div>
														<input type="hidden" tempName="getJsFiles1" name="getJsFiles1" value="" id="selectedJs">
													</div>
											<% } %>		
											</fieldset>
										</div>
										<input type="hidden" name="fileLocation" value="<%= StringUtils.isNotEmpty(fileLoc) ? fileLoc : "" %>"/>
			                        </section>
			                    </div>
			                </div>
			            </section>  
			        </div>
			    </section>
			</div> 
		<% } %>
	</div>
	
<!--  minifier setting  end -->
	
	<div class="modal-footer">
		<div class="action popup-action">
			<img class="popupLoadingIcon"> 
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

<% if (TechnologyTypes.MOBILES.contains(technology)) { %>
		<input type="hidden" id="mobile" value="mobile">
<% } %>
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
			
			/* enable text box only if any file selected for minification */
			$('input[name="jsFileName"]').each(function () {
				if($(this).val() !== "") {
					$(this).attr("disabled", false);
				}
			});

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
		
		$('#mainClassName').bind('input propertychange', function (e) { 	//mainClassName validation for JavaStandAlone Projects
			var mainClassName = $(this).val();
			mainClassName = checkForClassName(mainClassName);
        	$(this).val(mainClassName);
		});
		
		$('#jarName').bind('input propertychange', function (e) { 	//jarName validation for JavaStandAlone Projects
			var jarName = $(this).val();
			jarName = checkForJarName(jarName);
        	$(this).val(jarName);
		});
		
		$('#deploy').click(function() {
			var isChecked = $('#importSql').is(":checked");
			if ($('#importSql').is(":checked") && $('#selectedSourceScript option').length == 0) {
				$("#errMsg").html('<%= FrameworkConstants.SELECT_DB %>');
				return false;
			}
			buildValidateSuccess("deploy", '<%= FrameworkConstants.REQ_FROM_TAB_DEPLOY %>');
		});
		
		/** NodeJS run against source **/
		$('#runAgainstSrc').click(function() {
			var isChecked = $('#importSql').is(":checked");
			if ($('#importSql').is(":checked") && $('#selectedSourceScript option').length == 0) {
				$("#errMsg").html('<%= FrameworkConstants.SELECT_DB %>');
				return false;
			}
			buildValidateSuccess('NodeJSRunAgainstSource', '<%= FrameworkConstants.REQ_READ_LOG_FILE %>');
		});
		
		/** Java run against source **/
		$('#javaRunAgainstSrc').click(function() {
			var isChecked = $('#importSql').is(":checked");
			if ($('#importSql').is(":checked") && $('#selectedSourceScript option').length == 0) {
				$("#errMsg").html('<%= FrameworkConstants.SELECT_DB %>');
				return false;
			}
			buildValidateSuccess('runAgainstSource', '<%= FrameworkConstants.REQ_JAVA_START %>');
		});
		
		$('#importSql').click(function() {
			var isChecked = $('#importSql').is(":checked");
			if (isChecked) {
<%-- 				$("#errMsg").html('<%= FrameworkConstants.EXEC_SQL_MSG %>'); --%>
				// getting database list based on environment and and execute sqk checkbox
		    } else {
		    	$('#DbWithSqlFiles').val("");
		    	$("#errMsg").empty();
		    }
			// show hide sql execution fieldset
			executeSqlShowHide();
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
		
		//execute sql codes
		$('#btnAdd').click(function(e) {
			addDbWithVersions();
			$('#avaliableSourceScript > option:selected').appendTo('#selectedSourceScript');
		});
			
		$('#btnAddAll').click(function(e) {
			var sqlFiles = "";
			$('#avaliableSourceScript option').each(function(i, available) {
				sqlFiles = $(available).val();
				$('#DbWithSqlFiles').val($('#DbWithSqlFiles').val() + $('#databases').val()+ "#VSEP#" + sqlFiles + "#NAME#" + $(available).text() + "#SEP#");
			});		
			$('#avaliableSourceScript > option').appendTo('#selectedSourceScript');
		});

		$('#btnRemove').click(function() {
			updateDbWithVersionsForRemove();
   			$('#selectedSourceScript > option:selected').appendTo('#avaliableSourceScript');
		});

		$('#btnRemoveAll').click(function() {
			updateDbWithVersionsForRemoveAll();
   			$('#selectedSourceScript > option').appendTo('#avaliableSourceScript');
		});
		
		//To move up the values
		$('#up').bind('click', function() {
			$('#selectedSourceScript option:selected').each( function() {
				var newPos = $('#selectedSourceScript  option').index(this) - 1;
				if (newPos > -1) {
					$('#selectedSourceScript  option').eq(newPos).before("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
					$(this).remove();
				}
			});
		});
		
		//To move down the values
		$('#down').bind('click', function() {
			var countOptions = $('#selectedSourceScript option').size();
			$('#selectedSourceScript option:selected').each( function() {
				var newPos = $('#selectedSourceScript  option').index(this) + 1;
				if (newPos < countOptions) {
					$('#selectedSourceScript  option').eq(newPos).after("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
					$(this).remove();
				}
			});
		});
		
		$('#environments').change(function() {
			if ($("#from").val() != "generateBuild") {
				
				$('#DbWithSqlFiles').val("");
				executeSqlShowHide();
			}
		});
		
		//execute Sql script
		executeSqlShowHide();
		showHideMinusIcon();
	});
	
	function addDbWithVersions() {
		//creating new data list
		var sqlFiles = "";
		$("#avaliableSourceScript :selected").each(function(i, available) {
			sqlFiles = $(available).val();
			$('#DbWithSqlFiles').val($('#DbWithSqlFiles').val() + $('#databases').val()+ "#VSEP#" + sqlFiles + "#NAME#" + $(available).text() + "#SEP#");
		});
	}
	
	function hideDbWithVersions() {
		// getting existing data list
		var nameSep = new Array();
		nameSep = $('#DbWithSqlFiles').val().split("#SEP#");
		for (var i=0; i < nameSep.length - 1; i++) {
			var addedDbs = nameSep[i].split("#VSEP#");
			var addedSqlName = addedDbs[1].split("#NAME#");
			if($('#databases').val() == addedDbs[0]) {
				$("#avaliableSourceScript option[value='" + addedSqlName[0] + "']").remove();
			}
		}
		// show corresponding DB sql files
		showSelectedDBWithVersions();
	}
	
	function showSelectedDBWithVersions() {
		$('#selectedSourceScript').empty();
		var nameSep = new Array();
		nameSep = $('#DbWithSqlFiles').val().split("#SEP#");
		for (var i=0; i < nameSep.length - 1; i++) {
			var addedDbs = nameSep[i].split("#VSEP#");
			var addedSqlName = addedDbs[1].split("#NAME#");
			if($('#databases').val() == addedDbs[0]) {
				$('#selectedSourceScript').append($("<option></option>").attr("value",addedSqlName[0]).text(addedSqlName[1])); 
			}
		}
		
		//hiding loading icon..
		hideLoadingIcon();
	}
	
	function updateDbWithVersionsForRemove() {
		var toBeUpdatedDbwithVersions = "";
		$("#selectedSourceScript option:selected").each(function(i, alreadySelected) {
			var nameSep = new Array();
			nameSep = $('#DbWithSqlFiles').val().split("#SEP#");
			for (var i=0; i < nameSep.length - 1; i++) {
				var addedDbs = nameSep[i].split("#VSEP#");
				var addedSqlName = addedDbs[1].split("#NAME#");
				if(($('#databases').val() == addedDbs[0]) && $(alreadySelected).val() != addedSqlName[0]) {
					toBeUpdatedDbwithVersions = toBeUpdatedDbwithVersions + nameSep[i] + "#SEP#";
				} else if(($('#databases').val() != addedDbs[0]) && $(alreadySelected).val() != addedSqlName[0]) {
					toBeUpdatedDbwithVersions = toBeUpdatedDbwithVersions + nameSep[i] + "#SEP#";
				}
			}
			$('#DbWithSqlFiles').val(toBeUpdatedDbwithVersions);
		});
	}
	
	function updateDbWithVersionsForRemoveAll() {
		var toBeUpdatedDbwithVersions = "";
		$("#selectedSourceScript option").each(function(i, alreadySelected) {
			var nameSep = new Array();
			nameSep = $('#DbWithSqlFiles').val().split("#SEP#");
			for (var i=0; i < nameSep.length - 1; i++) {
				var addedDbs = nameSep[i].split("#VSEP#");
				var addedSqlName = addedDbs[1].split("#NAME#");
				if(($('#databases').val() != addedDbs[0]) && $(alreadySelected).val() != addedSqlName[0]) {
					toBeUpdatedDbwithVersions = toBeUpdatedDbwithVersions + nameSep[i] + "#SEP#";
				} else if(($('#databases').val() == addedDbs[0]) && $(alreadySelected).val() == addedSqlName[0]) {
					toBeUpdatedDbwithVersions = toBeUpdatedDbwithVersions + nameSep[i] + "#SEP#";
				}
			}
			$('#DbWithSqlFiles').val(toBeUpdatedDbwithVersions);
		});
	}
	
	function executeSqlShowHide() {
		if($('#importSql').is(":checked")) {
			loadingIconShow();
			$('#sqlExecutionContain').show();
		} else {
			$('#sqlExecutionContain').hide();
		}
		// after fieldset completed, we have to load db and sql files
		getDatabases();
	}
	
	function getDatabases() {
		if(!isBlank($("#environments").val())) { 
			var params = 'environments=';
		    params = params.concat($("#environments").val());
		    params = params.concat("&projectCode=");
		    params = params.concat('<%= projectCode %>');
			performAction("getSqlDatabases", params, '', true);
		}
	}
	
	$("#databases").change(function() {
		loadingIconShow();
		getSQLFiles();
	});
	
	function getSQLFiles() {
		if(!isBlank($("#databases").val())) {
			var params = 'selectedDb=';
		    params = params.concat($("#databases").val());
		    params =  params.concat('&environments=');
		    params = params.concat($("#environments").val());
		    params = params.concat("&projectCode=");
		    params = params.concat('<%= projectCode %>');
			performAction("fetchSQLFiles", params, '', true);
		}
	}

	function loadingIconShow() {
		$('.popupLoadingIcon').show();
		getCurrentCSS();
	}
	
	function hideLoadingIcon() {
		$('.popupLoadingIcon').hide();
	}
	
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
		params = params.concat("&DbWithSqlFiles=");
		params = params.concat($('#DbWithSqlFiles').val());
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
	
	var counter = 2;
	function addJsCompTag(){
		var browseId = "getJsFiles"+counter;
		
		var newMinDiv = $(document.createElement('div')).attr("id", 'browseJS' + counter);
		newMinDiv.html("<div class='clearfix'><label for='xlInput' class='xlInput popup-label' style='width:100px;'><s:text name='build.js.minification'/></label>" +
		"<div class='input'><input type='button' id='"+ browseId +"' class='btn primary chooseJS' value='<s:text name='build.minify.browse'/>' onclick='browseFiles(this);' style = 'float:left; margin-left:-30px;'>" + 
		"<label for='xlInput' class='xlInput popup-label' style='padding-right:6px;'><s:text name='build.compress.name'/></label>" + 
		"<input type='text' class='"+browseId+"' name='jsFileName' id='compNameText' value ='' disabled/></div>" +
		"<a><img title='' src='images/icons/add_icon.png' id='addJSComp' class='minifyAddIcon' onclick='addJsCompTag();'></a>" + 
		"<a><img class = 'del imagealign hide' src='images/icons/minus_icon.png' onclick='removeTag(this);'></a><input type='hidden' tempName='"+browseId+"' class='' name='"+browseId+"' value='' id='selectedJs'></div>"); 
		newMinDiv.appendTo(".minify_popup");
		counter++;
		removeTag();
		showHideMinusIcon();
	}
	
	function showHideMinusIcon() {
		var noOfRows = $('input[id="selectedJs"]').size();
		if (noOfRows > 1) {
			$(".del").show();
		} else if (noOfRows === 1) {
			$(".del").hide();
		}
	}
	
	function removeTag(currentTag) {
		var noOfRows = $('input[id="selectedJs"]').size();
		if(noOfRows > 1 && currentTag !== undefined) {
			$(currentTag).parent().parent().parent().remove();
			noOfRows--;
		} 
		if (noOfRows === 1) {
			$(".del").hide();
		}
	}
	
	var textBoxClass = "";
	function browseFiles(obj) {
		textBoxClass = $(obj).attr("id");
		var jsName = $('input[class="'+ textBoxClass +'"]').val();
		var jsFiles = $('input[name="'+ jsName +'"]').val();
		
		$('#browseLocation').remove();
		$('#generateBuildForm').hide();
		var params = "techonolgy=";
		var Technology = '<%= technology %>';
		params = params.concat(Technology);
		params = params.concat("&fileType=js");
		params = params.concat("&fileorfolder=All");
		params = params.concat("&selectedJsName=");
		params = params.concat(jsName);
		params = params.concat("&selectedJsFiles=");
		params = params.concat(jsFiles);
		popup('jsFileBrowse', params, $('#popup_div'), '', true);
	}
	
	function updateHiddenField(jsName, jsFiles, fileLocation) {
		$("."+textBoxClass).val(jsName);
		$('input[tempName="'+ textBoxClass +'"]').attr("name", jsName);
		$('input[name="'+ jsName +'"]').val(jsFiles);
		$('input[name="fileLocation"]').val(fileLocation);
	}
</script>