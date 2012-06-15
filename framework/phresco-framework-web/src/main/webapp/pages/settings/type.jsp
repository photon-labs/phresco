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

<%@ include file="../applications/errorReport.jsp" %>

<%@ page import="freemarker.template.utility.StringUtil"%>
<%@ page import="org.apache.commons.collections.MapUtils"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Collections"%>

<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.regex.*"%>
<%@ page import="java.util.Map"%>
<%@ page import="org.apache.commons.collections.MapUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.model.SettingsTemplate"%>
<%@ page import="com.photon.phresco.model.PropertyTemplate"%>
<%@ page import="com.photon.phresco.model.PropertyInfo"%>
<%@ page import="com.photon.phresco.model.Technology"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.I18NString"%>
<%@ page import="com.photon.phresco.model.Server" %>
<%@ page import="com.photon.phresco.model.Database" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>

<%
    String value = "";
    String selectedValue = "";
    String selectedVersion = "";
	String disableStr = "";
    String fromPage = (String) request.getParameter(FrameworkConstants.REQ_FROM_PAGE);
    String oldName = (String) request.getParameter(FrameworkConstants.REQ_OLD_NAME);
    List<Server> projectInfoServers = null;
    List<Database> projectInfoDatabases = null;
    SettingsInfo settingsInfo = null;
    if (StringUtils.isNotEmpty(oldName)) {
        settingsInfo = (SettingsInfo) session.getAttribute(oldName);
    }
    SettingsTemplate settingsTemplate = (SettingsTemplate)request.getAttribute(
            FrameworkConstants.REQ_CURRENT_SETTINGS_TEMPLATE);
    Map<String, Technology> technologies = (Map<String, Technology>)request.getAttribute(
            FrameworkConstants.REQ_ALL_TECHNOLOGIES);
    
    Map<String, String> errorMap = (Map<String, String>) session.getAttribute(FrameworkConstants.ERROR_SETTINGS);
    if (settingsTemplate != null) {
    	projectInfoServers = (List<Server>) request.getAttribute(FrameworkConstants.REQ_TEST_SERVERS);
    	projectInfoDatabases = (List<Database>) request.getAttribute(FrameworkConstants.REQ_PROJECT_INFO_DATABASES);
    	List<PropertyTemplate> propertyTemplates = settingsTemplate.getProperties();
	    for (PropertyTemplate propertyTemplate : propertyTemplates) {
	    	String masterKey = "";
	        String key = propertyTemplate.getKey();
	        String propType = propertyTemplate.getType();
	        List<String> possibleValues = propertyTemplate.getPossibleValues();
	        boolean isRequired = propertyTemplate.isRequired();
	        
			I18NString i18NString = propertyTemplate.getName();
	        String label = i18NString.get("en-US").getValue();
			
			I18NString descStr = propertyTemplate.getDescription();
			String desc = "";
			if (descStr != null) {
	        	desc = descStr.get("en-US").getValue();
			}
			
			if (key.equals("Server") || key.equals("Database")) {
        		List<PropertyTemplate> comPropertyTemplates = propertyTemplate.getpropertyTemplates();
        		for (PropertyTemplate comPropertyTemplate : comPropertyTemplates) {
        			if (comPropertyTemplate.getKey().equals("type") && settingsInfo != null) {
        				selectedValue = settingsInfo.getPropertyInfo(comPropertyTemplate.getKey()).getValue();
        			}
        			if (comPropertyTemplate.getKey().equals("version") && settingsInfo != null) {
        				selectedVersion = settingsInfo.getPropertyInfo(comPropertyTemplate.getKey()).getValue();
        			}
        		}
        	}
			
	        
	        if (settingsInfo != null && settingsInfo.getPropertyInfo(key) != null) {
	        	value = settingsInfo.getPropertyInfo(key).getValue();
	        }
%>
	
	<% 
			String errDisplay = "";
			if (MapUtils.isNotEmpty(errorMap) && StringUtils.isNotEmpty(errorMap.get(key))) {
	  			if(StringUtils.isNotEmpty(errorMap.get(key))){
	  				errDisplay = "configSettingError";
	  			}
	    	}
	%>
                    
    <div class="clearfix" id="<%= key %>">
    	
        <label for="<%= key %>" class="new-xlInput">
        	<% if(isRequired == true) { %>
        		<span class="red">*</span> 
        	<% } %>
        	
        	<%= label %>
        </label>
       
        <div class="input new-input">
	        <div class="typeFields">
		        <% 
		        	if (key.equals(FrameworkConstants.ADMIN_FIELD_PASSWORD) || key.equals(FrameworkConstants.PASSWORD)) {
		        %>
		        		<input class="xlarge" id="<%= label %>" name="<%= key %>" type="password"  value ="<%= value %>" onfocus="showDesc(this);" placeholder="<%= desc %>"/>
		       	<%  
		        	} else if ((key.equals("Server") || key.equals("Database")) && possibleValues == null) {
		        		masterKey = key;
		        %>
<%-- 	        			<select id="type" name="<%= key %>" class="selectEqualWidth" onfocus="showDesc(this);"> --%>
                      
                       <select id="type" name="type" class="selectEqualWidth  server_db_Width" onfocus="showDesc(this);">
	                	
	                	</select>
	        			
	        			<div class="versionDiv">
							<label for="xlInput" id="versionsLbl" class="versionsLbl"><s:text name="label.version"/></label>&nbsp;&nbsp;&nbsp;
	                		<select id="version" name="version" class="app_type_version_select">
	                		
	                		</select>		                		
                		</div>
                 <%
		        	} else if ("remoteDeployment".equals(key)) {
		        		 boolean remoteDeply = Boolean.parseBoolean(value);
		        		 String checkedStr = "";
		        		 if (remoteDeply) {
		        			 checkedStr = "checked";
		        		 }
		        %>		
		        		<input type="checkbox" id="<%= label %>" name="<%= key %>" value="true" <%= checkedStr %> style = "margin-top: 8px;">		
		        <%	
		        	} else if (possibleValues == null) {
		        %>
		        		<input class="xlarge" id="<%= label %>" name="<%= key %>" type="text"  value ="<%= value %>" onfocus="showDesc(this);" placeholder="<%= desc %>"/>
		        <% 	} else { %>
		        			<select id="<%= label %>" name="<%= key %>" class="selectEqualWidth" onfocus="showDesc(this);" >
		        				<option disabled="disabled" selected="selected" value="" style="color: #BFBFBF;"><%= desc %></option>
		        				<% 
		        					String selectedStr = "";
		        					List<String> selectedType = null;
		        					for(String possibleValue : possibleValues) {
		    								if (possibleValue.equals(value) ) {
		    									selectedStr = "selected";
		    								} else {
		    									selectedStr = "";
		    								}
		        				%>
		        					<option value="<%= possibleValue %>" <%= selectedStr %> > <%= possibleValue %></option>
		        				<%	
		        					}
		        				%>
		                	</select>
		        <% } %>
	        </div>
	        
			<div>		
				<div class="lblDesc configSettingHelp-block" id="<%= key %>ErrorDiv">

				</div>
       		</div>
       		
        </div>
    </div> <!-- /clearfix -->
<%
    	}
    }	
%>

	<!-- applies to starts -->
    <% 
    	String errAppliesTo = "";
    	String errAppliesToStyle = "";
    	if (MapUtils.isNotEmpty(errorMap) && StringUtils.isNotEmpty(errorMap.get("appliesto"))) {
  			errAppliesTo =  errorMap.get("appliesto");
  			if(StringUtils.isNotEmpty(errAppliesTo)){
  				errAppliesToStyle = "configSettingError";
  			}		
    	} 
    %>
	<% 	
		if ( settingsTemplate != null) {
			List<String> appliesTos = settingsTemplate.getAppliesTo();
			if(appliesTos != null) {
	%>
	
	<div class="clearfix" id="appliesToErrDiv">
		<label for="xlInput" class="new-xlInput"><span class="red">*</span>
			<s:text name="label.applies.to" />
		</label>
		<div class="input new-input">
			<div class="typeFields" id="typefield">
				<div id="multilist-scroller">
					<ul>
						<% 	
							String checkedStr = "";
			            	List<String> selectedAppliesTos = null;
							if (settingsInfo != null) {
								selectedAppliesTos = settingsInfo.getAppliesTo();
							} else {
								Project project = (Project)session.getAttribute(FrameworkConstants.APPLICATION_PROJECT);
								if (project != null){
									selectedAppliesTos = Arrays.asList(project.getProjectInfo().getTechnology().getId());
									disableStr = "disabled";
								}
							}
			            	for (String appliesTo : appliesTos) {
								if (selectedAppliesTos != null ) {
									
									if (selectedAppliesTos.contains(appliesTo) ) {
										checkedStr = "checked";
										
									} else {
										checkedStr = "";
									}
									
								}
								if (technologies.get(appliesTo) != null) {
			            %>
						<li>
						<input type="checkbox" name="appliesto" id="appliesto" value="<%= appliesTo %>"  <%= checkedStr %> <%= disableStr %>
							class="check"> <%= technologies.get(appliesTo).getName() %>
						</li>
						<%        
								}
							}
						%>
					</ul>
				</div>
			</div>
	
			<div>
				<div class="lblDesc configSettingHelp-block" id="appliesToErrMsg">
	
				</div>
			</div>
		</div>
	</div>
	<% 
			}
		} 
	%>
    <!-- applies to ends -->
    
<script type="text/javascript">
	if(!isiPad()){
		/* JQuery scroll bar */
		$("#multilist-scroller").scrollbars();
	}

	hideAllDesc();
	function showDesc(obj){
		hideAllDesc();
	    var id = obj.id;
		id = id.replace(/\s/g, "");
		$("."+id).show();
		setTimeout(function(){
		    $("."+id).fadeOut("slow", function () {
		    });
		     
		}, 2000);
	}
	
	$(document).ready(function() {
		enableScreen();

		<%
			if (CollectionUtils.isNotEmpty(projectInfoServers) && projectInfoServers != null) {
		%>
			$('#type').find('option').remove();
			<%
				for(Server projectInfoServer : projectInfoServers) {
					String serverName = projectInfoServer.getName();
			%>
						$('#type').append($("<option></option>").attr("value", '<%= serverName %>').text('<%= serverName %>'));
			<%
				}
			%>
			getCurrentVersions('');
			$("#type").change(function() {
				 var server = $('#type').val();
				 if( server.trim() == "Apache Tomcat" || server.trim() == "JBoss" || server.trim() == "WebLogic"){
					 $('#remoteDeployment').show();
			     } else {
			    	 hideRemoteDeply();
			     }
				 remoteDeplyChecked();
				 if( $(this).val() != "Apache Tomcat" || $(this).val() != "JBoss" || $(this).val() != "WebLogic"){	
					 $("input[name='remoteDeployment']").attr("checked",false);
					 $("#admin_username label").html('Admin Username');
					 $("#admin_password label").html('Admin Password'); 
				 } 
				
			});   
	<%
		}
	%>
	
	/** To display projectInfo databases starts **/
	<%
		if (CollectionUtils.isNotEmpty(projectInfoDatabases) && projectInfoDatabases != null) {
	%>
			$('#type').find('option').remove();
			<%
				for(Database projectInfoDatabase : projectInfoDatabases) {
					String databaseName = projectInfoDatabase.getName();
			%>
						$('#type').append($("<option></option>").attr("value", '<%= databaseName %>').text('<%= databaseName %>'));
			<%
				}
			%>
			getCurrentVersions('');
	<%
		}
	%>
	
	var server = $('#type').val();
	if (server.trim() == "Apache Tomcat" || server.trim() == "JBoss" || server.trim() == "WebLogic") {
		$('#remoteDeployment').show();	
    } else {
    	hideRemoteDeply();
    }
	
	/** To display projectInfo databases ends **/
	
		// hide deploy dir if remote Deployment selected
		 
         $("input[name='remoteDeployment']").change(function() {
				var isChecked = $("input[name='remoteDeployment']").is(":checked");
				if (isChecked) {
					hideDeployDir();
					$("#admin_username label").html('<span class="red">* </span>Admin Username');
					$("#admin_password label").html('<span class="red">* </span>Admin Password');  
			    }  else {
			    	$("#admin_username label").html('Admin Username');
					$("#admin_password label").html('Admin Password');  
			    	$('#deploy_dir').show();
			     } 
			});
	 
	    $("#type").change(function() {
	    	$('#deploy_dir').show();
			if($(this).val() == "NodeJS") {
				hideDeployDir();
			}
		    getCurrentVersions('onChange');
	      }); 
		
		$("input[name='name']").prop({"maxLength":"20", "title":"20 Characters only"});
		$("input[name='context']").prop({"maxLength":"60", "title":"60 Characters only"});
		$("input[name='port']").prop({"maxLength":"5", "title":"Port number must be between 1 and 65535"});
		
		$('#Port').bind('input propertychange', function (e) { //Port validation
        	var portNo = $(this).val();
        	portNo = checkForNumber(portNo);
        	$(this).val(portNo);
         });
        
		$("#xlInput").bind('input propertychange',function(e) { //Name validation
        	var name = $(this).val();
        	name = checkForSplChr(name);
        	$(this).val(name);
        });
		
		$("input[name='dbname']").bind('input propertychange',function(e){ 	//DB_Name validation
        	var name = $(this).val();
        	name = checkForSplChr(name);
        	name = removeSpace(name);
        	$(this).val(name);
        });
		
		$("input[name='context']").bind('input propertychange',function(e){ 	//Context validation
        	var name = $(this).val();
        	name = checkForContext(name);
        	$(this).val(name);
        });
	});
	
	function showSetttingsInfoServer() {
		$("#type option").each(function() {
			if ($(this).val().trim().toLowerCase() == '<%= selectedValue.toLowerCase() %>') {
				$(this).prop("selected", "selected");
			}
			// When editing nodejs server config, hide deploy directory field
			if ('<%= selectedValue %>' == "NodeJS") {
				$('#deploy_dir').hide();
			}
			if( '<%= selectedValue %>' == "Apache Tomcat" || '<%= selectedValue %>' == "JBoss" || '<%= selectedValue %>' == "WebLogic"){
				 $('#remoteDeployment').show(); 
		     } else {
		    	  hideRemoteDeply(); 
		     }
			remoteDeplyChecked();
		});
	}
	
	function showSetttingsInfoVersion() {
		$("#version option").each(function() {
			if ($(this).val().trim() == '<%= selectedVersion %>') {
				$(this).prop("selected", "selected");
			}
		});
	}
	
	function hideDeployDir() {
		$("input[name='deploy_dir']").val("");
		$('#deploy_dir').hide();
	}
	
	function hideRemoteDeply() {
		$('#remoteDeployment').hide();
	}
	
	function remoteDeplyChecked() {
		var isRemoteChecked = $("input[name='remoteDeployment']")
				.is(":checked");
		if (isRemoteChecked) {
			hideDeployDir();
			$("#admin_username label").html('<span class="red">* </span>Admin Username');
			$("#admin_password label").html('<span class="red">* </span>Admin Password'); 
		} else {
			$('#deploy_dir').show();
		}
	}
</script>