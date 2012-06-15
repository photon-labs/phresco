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
<%@ include file="description_dialog.jsp" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.collections.MapUtils" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ApplicationType" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.model.Technology" %>
<%@ page import="com.photon.phresco.model.ModuleGroup" %>
<%@ page import="com.photon.phresco.model.Module" %>
<%@page import="com.photon.phresco.model.Documentation.DocumentationType"%>

<style>
	.twipsy {
		margin-top: -5px;
		width: 30%; 
	}
	
	.twipsy.bootstrap-right {
		left: 0;
		margin-top: 6px;
		top: 0;
	}
	
	.twipsy-inner {
		margin-left: 5px;
		margin-top: -14px;
		padding: 3px 8px;
		width: 65%;
	}
	
	 .twipsy-arrow {
		position: relative;
		left: 0;
		margin-top: 6px;
		top: 0;
	} 
	
	#coremodule_accordion_container {
	    height: 90%;
	    width: 99.3%;
	}
	
	.external_features_wrapper {
		border-radius : 6px; 
		float: left; 
		width: 49.2%;
		height: 87%;
	}
	
	.jsCustomPanel {
		float: right; 
		width: 50%; 
		height: 86%;
	}
	
	.js_wrapper {
		float: right; 
		width: 49.5%; 
		height: 85%;
		border-radius : 6px; 
	}
	
	.jsLib_accordion_container {
		height: 90%;
		width: 99.3%;
	}
	
	.custom_features_wrapper {
	    float: left;
	    height: 85%;
	    width: 49.5%;
		border-radius : 6px; 
	}
	
	.custommodule_accordion_container {
		height: 69%;
    	width: 99.3%;
	}
</style>

<script type="text/javascript" src="js/loading.js"></script>
<script type="text/javascript" src="js/home-header.js"></script>
<!-- Feature js -->
<script type="text/javascript" src="js/feature.js"></script>

<%
	String appType = "";
	String disabled = "disabled";

	List<ModuleGroup> leftModules = (List<ModuleGroup>) request.getAttribute(FrameworkConstants.REQ_FEATURES_LEFT_MODULES);
    List<ModuleGroup> rightModules = (List<ModuleGroup>) request.getAttribute(FrameworkConstants.REQ_FEATURES_RIGHT_MODULES);
    
    String leftModuleHdr = (String)request.getAttribute(FrameworkConstants.REQ_FEATURES_FIRST_MDL_CAT);
    String rightModuleHdr = (String)request.getAttribute(FrameworkConstants.REQ_FEATURES_SECOND_MDL_CAT);
    
    Map<String, String> pilotModules = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_PILOTS_IDS);
    Map<String, String> alreadySelectedModules = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_ALREADY_SELECTED_MODULES);

    Map<String, String> pilotJsLibs = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_PILOT_JSLIBS);
    Map<String, String> alreadySelectedJsLibs = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_ALREADY_SELECTED_JSLIBS);
    
    Map<String, String> selectedModules = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_TEMP_SELECTEDMODULES);
    Map<String, String> selectedJsLibs = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_TEMP_SELECTED_JSLIBS);

    String moduleType = "";
    String secondModuleType = "";
    
    if (FrameworkConstants.REQ_EXTERNAL_FEATURES.equals(leftModuleHdr)) {
    	moduleType = FrameworkConstants.REQ_CORE_MODULE;
    }
    
    if (FrameworkConstants.REQ_CUSTOM_FEATURES.equals(leftModuleHdr)) {
    	moduleType = FrameworkConstants.REQ_CUSTOM_MODULE;
    }

    if (FrameworkConstants.REQ_CUSTOM_FEATURES.equals(rightModuleHdr)) {
    	secondModuleType = FrameworkConstants.REQ_CUSTOM_MODULE;
    }
    
    if (FrameworkConstants.REQ_JS_LIBS.equals(rightModuleHdr)) {
    	secondModuleType = FrameworkConstants.REQ_JSLIB_MODULE;
    }

    String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
    ProjectInfo projectInfo = (ProjectInfo) request.getAttribute(FrameworkConstants.REQ_PROJECT_INFO);
    String projectCode = "";
    String techId = "";
    if (projectInfo != null) {
        techId = projectInfo.getTechnology().getId();
        session.setAttribute(projectInfo.getCode(), projectInfo);
        projectCode = projectInfo.getCode(); 
    }
    
    String configServerNames = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_SERVER_NAMES);
    String configDbNames = (String) request.getAttribute(FrameworkConstants.REQ_CONFIG_DB_NAMES);
    
    if (StringUtils.isEmpty(fromPage)) {
    	fromPage = "";
    	disabled = "";
%>
    
    <form id="createProjectForm" action="save" method="post" autocomplete="off" class="app_features_form">
<% 
	} else { 
%>
    <form action="#" method="post" autocomplete="off" class="app_features_form">
<% } %>
	<div class="clearfix" style="margin: 5px 0 0 5px;">
	    <label for="xlInput" style="width: auto;"><s:text name="label.select.pilot.project"/></label>
	
	    <!--  Pilot projects are loaded here starts-->
	    <div class="input">
	    	<%
	    		String pilotProject = "None";
	    		if(StringUtils.isNotEmpty(projectInfo.getPilotProjectName())) {
	    			pilotProject = projectInfo.getPilotProjectName();
	    		}
	    	%>
	    	<input type="text" class="medium" id="selectedPilotProject" name="selectedPilotProject" disabled="disabled" value="<%= pilotProject %>">
	    </div>
	    <!--  Pilot projects are loaded here ends -->
	</div>
	
    <div class="featuresScrollDiv"> <!-- Pilot Projects Combo Box -->
    	<% 
		    String checkedStr = "";
		    String disabledStr = "";
			if (CollectionUtils.isEmpty(leftModules) && CollectionUtils.isEmpty(rightModules)) {
		%>
			<div class="alert-message block-message warning">
				<center><label class="errorMsgLabel"><s:text name="label.feature.not.avail"/></label></center>
			</div>
			
		<% } else { %>
		
			<div class="custom_features_wrapper">
				<div class="tblheader">
					<table class="zebra-striped">
						<tr>
							<th class="editFeatures_th1">
								<input type="checkbox" value="AllCoreModules" id="checkAllCoreModule" name="<%= moduleType %>" onchange="selectAllChkBoxClk('<%= moduleType %>', this)">
							</th>
							<th class="editFeatures_th2"><%= leftModuleHdr %></th>
						</tr>
					</table>
				</div>
				<div class="theme_accordion_container" id="coremodule_accordion_container">
					<section class="accordion_panel_wid">
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
							<%
								for (ModuleGroup leftModule : leftModules) {
									disabledStr = "";
									checkedStr = "";
									
									String pilotVersion = "";
									if (MapUtils.isNotEmpty(alreadySelectedModules) && StringUtils.isNotEmpty(alreadySelectedModules.get(leftModule.getId()))) {
										pilotVersion = alreadySelectedModules.get(leftModule.getId());
										checkedStr = "checked";
										disabledStr = "disabled";
									}
									if (MapUtils.isNotEmpty(pilotModules) && StringUtils.isNotEmpty(pilotModules.get(leftModule.getId()))) {
										pilotVersion = pilotModules.get(leftModule.getId());
										checkedStr = "checked";
										disabledStr = "disabled";
									}
									if (MapUtils.isNotEmpty(selectedModules) && selectedModules.get(leftModule.getId()) != null) {
										checkedStr = "checked";
									} 
							%>
								<span class="siteaccordion closereg">
									<span>
										<input type="checkbox" class="<%= moduleType %>" name="<%= FrameworkConstants.REQ_SELECTEDMODULES %>" 
											id="<%= leftModule.getId()%>checkBox" value="<%= leftModule.getId()%>" <%=disabledStr %> <%= checkedStr %>>
										&nbsp;&nbsp;<%= leftModule.getName() %> &nbsp;&nbsp;
										<p id="<%= leftModule.getId()%>version" class="version versionDisplay_CoreModule"></p>
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt" style="display: none;">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<!-- <thead>
													<tr class="download_tbl_header">
														<th>#</th>
														<th>Name</th>
														<th>Version</th>
													</tr>
												</thead> -->
												<tbody>
												<% 
												String descContent = "";
					                            if (leftModule.getDoc(DocumentationType.DESCRIPTION) != null) { 
												  	descContent = leftModule.getDoc(DocumentationType.DESCRIPTION).getContent();
												}
												  
												String helpTextContent = "";
												if (leftModule.getDoc(DocumentationType.HELP_TEXT) != null) { 
												  	helpTextContent = leftModule.getDoc(DocumentationType.HELP_TEXT).getContent();
												}
												
												List<Module> versions = leftModule.getVersions();
												if (CollectionUtils.isNotEmpty(versions)) {
													for (Module moduleVersion : versions) {
														checkedStr = "";
														if (MapUtils.isNotEmpty(selectedModules)) {
															String selectedVersion = selectedModules.get(leftModule.getId());
															if (StringUtils.isNotEmpty(selectedVersion) && moduleVersion.getVersion().equals(selectedVersion)) {
																checkedStr = "checked";
															} 
														}
														if (StringUtils.isNotEmpty(pilotVersion) && moduleVersion.getVersion().equals(pilotVersion)) {
															checkedStr = "checked";
														}	
												%>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="<%= leftModule.getId()%>" 
																value="<%= moduleVersion.getVersion() %>" <%=disabledStr %> <%= checkedStr %> 
																onclick="selectCheckBox('<%= leftModule.getId()%>', '<%= moduleType %>', this);">
														</td>
														<td class="editFeatures_td2">
															<% descContent = descContent.replaceAll("\"","&quot;"); %>
															<a href="#" name="ModuleDesc" title="<%= descContent %>" class="<%= leftModule.getId()%>" id="<%= helpTextContent %>" ><%= leftModule.getName() %></a>
														</td>
														<td class="editFeatures_td4"><%= moduleVersion.getVersion() %></td>
													</tr>
													<%	} %>
												<% } %>
												</tbody>
											</table>
										</section>
									</div>
								</div>
								<% 		
									}
								%>	
							</section>  
						</div>
					</section>
				</div>
			</div>
			<div class="js_wrapper">
				<div class="tblheader">
					<table class="zebra-striped">
						<tr>
							<th class="editFeatures_th1">
								<input type="checkbox"	value="AllJsLibs" id="checkAllJsLibs" name="<%= secondModuleType %>" onchange="selectAllChkBoxClk('<%= secondModuleType %>', this)">
							</th>
							<th class="editFeatures_th2"><%= rightModuleHdr %></th>
						</tr>
					</table>
				</div>
				<div class="theme_accordion_container jsLib_accordion_container">
				    <section class="accordion_panel_wid">
				        <div class="accordion_panel_inner">
				            <section class="lft_menus_container">
				            <%
								for (ModuleGroup rightModule : rightModules) {
									disabledStr = "";
									checkedStr = "";

									String pilotVersion = "";
									if (rightModuleHdr.equals(FrameworkConstants.REQ_JS_LIBS)) {
										if (MapUtils.isNotEmpty(alreadySelectedJsLibs) && StringUtils.isNotEmpty(alreadySelectedJsLibs.get(rightModule.getId()))) {
											pilotVersion = alreadySelectedJsLibs.get(rightModule.getId());
											checkedStr = "checked";
											disabledStr = "disabled";
										}
										if (MapUtils.isNotEmpty(selectedJsLibs) && selectedJsLibs.get(rightModule.getId()) != null) {
											checkedStr = "checked";
										}
									} else {
										if (MapUtils.isNotEmpty(alreadySelectedModules) && StringUtils.isNotEmpty(alreadySelectedModules.get(rightModule.getId()))) {
											pilotVersion = alreadySelectedModules.get(rightModule.getId());
											checkedStr = "checked";
											disabledStr = "disabled";
										}
										if (MapUtils.isNotEmpty(selectedModules) && selectedModules.get(rightModule.getId()) != null) {
											checkedStr = "checked";
										}
									}
							%>
				                <span class="siteaccordion closereg">
				                	<span>
					                	<% if (rightModuleHdr.equals(FrameworkConstants.REQ_JS_LIBS)) { %>
					                		<input type="checkbox" class="<%= FrameworkConstants.REQ_JSLIB_MODULE %>" name="<%= FrameworkConstants.REQ_SELECTED_JSLIBS %>" value="<%= rightModule.getId()%>" <%=disabledStr %> 
						                		<%= checkedStr %> id="<%= rightModule.getId()%>checkBox">
					                	<% } else { %>
					                		<input type="checkbox" class="<%= FrameworkConstants.REQ_CUSTOM_MODULE %>" name="<%= FrameworkConstants.REQ_SELECTEDMODULES %>" 
						                		value="<%= rightModule.getId()%>" <%=disabledStr %> <%= checkedStr %> id="<%= rightModule.getId()%>checkBox">
					                	<% } %>
				                		&nbsp;&nbsp;<%= rightModule.getName() %>&nbsp;&nbsp;
				                		<p id="<%= rightModule.getId()%>version" class="version versionDisplay_JSLib"></p>
				                	</span>
				                </span>
				                <div class="mfbox siteinnertooltiptxt" style="display: none;">
				                    <div class="scrollpanel">
				                        <section class="scrollpanel_inner">
				                        	<table class="download_tbl">
					                        	<!-- <thead>
					                            	<tr class="download_tbl_header">
					                            		<th>#</th>
				                            			<th>Name</th>
				                            			<th>Version</th>
				                            		</tr>
					                            </thead> -->
					                            <tbody>
					                            <% 
					                            String descContent = "";
												if (rightModule.getDoc(DocumentationType.DESCRIPTION) != null) { 
												  	descContent = rightModule.getDoc(DocumentationType.DESCRIPTION).getContent();
												}
												  
												String helpTextContent = "";
												if (rightModule.getDoc(DocumentationType.HELP_TEXT) != null) { 
												  	helpTextContent = rightModule.getDoc(DocumentationType.HELP_TEXT).getContent();
												}
												
										    	List<Module> versions = rightModule.getVersions();
										    	if (CollectionUtils.isNotEmpty(versions)) {
													for (Module moduleVersion : versions) {
														checkedStr = "";
														if (rightModuleHdr.equals(FrameworkConstants.REQ_JS_LIBS)) {
															if (MapUtils.isNotEmpty(selectedJsLibs)) {
																String selectedVersion = selectedJsLibs.get(rightModule.getId());
																if (StringUtils.isNotEmpty(selectedVersion) && moduleVersion.getVersion().equals(selectedVersion)) {
																	checkedStr = "checked";
																}
															}
															if (StringUtils.isNotEmpty(pilotVersion) && moduleVersion.getVersion().equals(pilotVersion)) {
																checkedStr = "checked";
															}
														} else {
															if (MapUtils.isNotEmpty(selectedModules)) {
																String selectedVersion = selectedModules.get(rightModule.getId());
																if (StringUtils.isNotEmpty(selectedVersion) && moduleVersion.getVersion().equals(selectedVersion)) {
																	checkedStr = "checked";
																} 
															}
															if (StringUtils.isNotEmpty(pilotVersion) && moduleVersion.getVersion().equals(pilotVersion)) {
																checkedStr = "checked";
															}
														}
															
												%>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="<%= rightModule.getId() %>" 
																value="<%= moduleVersion.getVersion() %>" <%=disabledStr %> <%= checkedStr %> 
																onclick="selectCheckBox('<%= rightModule.getId()%>', '<%= secondModuleType %>', this);">
														</td>
														<td class="editFeatures_td2">
															<% descContent = descContent.replaceAll("\"","&quot;"); %>
															<a href="#" name="ModuleDesc" title="<%= descContent %>" class="<%= rightModule.getId()%>" id="<%= helpTextContent %>"><%= rightModule.getName() %></a>
														</td>
														<td class="editFeatures_td4"><%= moduleVersion.getVersion() %></td>
													</tr>
													<%	} %>
										    	<% } %>
					                            </tbody>
				                        	</table>
				                        </section>
				                    </div>
				                </div>
				                <% 		
									}
								%>	
				            </section>  
				        </div>
				    </section>
				</div>
			</div>
		<% } %>
	</div>
	
	<div class="features_actions">
		<a id="previous" href="#" class="primary btn"><s:text name="label.previous"/></a>
		<%
			if(StringUtils.isNotEmpty(fromPage)) {
		%>
			<input id="update" type="button" value="<s:text name="label.update"/>" class="primary btn createProject_btn">
		<% } else { %>
			<input id="finish" type="button" value="<s:text name="label.finish"/>" class="primary btn createProject_btn">
		<% } %>
			<input type="button" id="cancel" value="<s:text name="label.cancel"/>" class="primary btn">
			<input type="hidden" id="technology" name="techId" value="<%= techId %>">
			<input type="hidden" id="configServerNames" name="configServerNames" value="<%= configServerNames %>">
			<input type="hidden" id="configDbNames" name="configDbNames" value="<%= configDbNames %>">
	</div>
    </form>
    
<!-- Tool tip div -->
<div class="twipsy bootstrap-right" id="toolTipDiv" style="display: none;">
	<div class="twipsy-arrow" id="arrow"></div>
	<div class="twipsy-inner"></div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		enableScreen();
	});
	
	$('#projectCode').val('<%= projectCode %>'); //this is for changing the sub-tab.
	$('#fromPage').val('<%= fromPage %>'); //this is for changing the sub-tab.
</script>