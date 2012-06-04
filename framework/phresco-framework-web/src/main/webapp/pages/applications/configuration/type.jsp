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

<%@ include file="../errorReport.jsp" %>

<%@ page import="freemarker.template.utility.StringUtil"%>

<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.regex.*"%>
<%@ page import="java.util.Map"%>

<%@ page import="org.apache.commons.collections.MapUtils"%>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="org.apache.commons.collections.MapUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.model.SettingsTemplate"%>
<%@ page import="com.photon.phresco.model.PropertyTemplate"%>
<%@ page import="com.photon.phresco.model.PropertyInfo"%>
<%@ page import="com.photon.phresco.model.Technology"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.I18NString"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.Server" %>
<%@ page import="com.photon.phresco.model.Database"%>
<%@ page import="com.photon.phresco.util.Constants" %>

<%
    String value = "";
	String selectedValue = "";
	String selectedVersion = "";
	String disableStr = "";
    String fromPage = (String) request.getParameter(FrameworkConstants.REQ_FROM_PAGE);
    String oldName = (String) request.getParameter(FrameworkConstants.REQ_OLD_NAME);
    SettingsInfo settingsInfo = null;
    if (StringUtils.isNotEmpty(oldName)) {
    	settingsInfo = (SettingsInfo) request.getAttribute(FrameworkConstants.REQ_CONFIG_INFO);
    }
    SettingsTemplate settingsTemplate = (SettingsTemplate)request.getAttribute(FrameworkConstants.REQ_CURRENT_SETTINGS_TEMPLATE);
    Map<String, Technology> technologies = (Map<String, Technology>)request.getAttribute(FrameworkConstants.REQ_ALL_TECHNOLOGIES);
    Map<String, String> errorMap = (Map<String, String>) session.getAttribute(FrameworkConstants.ERROR_SETTINGS);
    List<Server> projectInfoServers = null;
    List<Database> projectInfoDatabases = null;
    if (settingsTemplate != null) {
    	projectInfoServers = (List<Server>) request.getAttribute(FrameworkConstants.REQ_PROJECT_INFO_SERVERS);
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
	  			if (StringUtils.isNotEmpty(errorMap.get(key))) {
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
		        		List<PropertyTemplate> compPropertyTemplates = propertyTemplate.getpropertyTemplates();
		        		for (PropertyTemplate compPropertyTemplate : compPropertyTemplates) {
		        			i18NString = propertyTemplate.getName();
		        	        label = i18NString.get("en-US").getValue();
		        			key = compPropertyTemplate.getKey();
		        		}
		        %>
	        			<select id="type" name="type" class="selectEqualWidth  server_db_Width" onfocus="showDesc(this);">
	                	
	                	</select>
	        			    
	        			<div class="versionDiv">
							<label for="xlInput" id="versionsLbl" class="versionsLbl"><s:text name="label.version"/></label>&nbsp;&nbsp;&nbsp;
	                		<select id="version" name="version" class="config_version_select">
	                		
	                		</select>		                		
                		</div>
		        <%	
		        	} else if (possibleValues == null) {
		        %>
		        		<input class="xlarge" id="<%= label %>" name="<%= key %>" type="text"  value ="<%= value %>" onfocus="showDesc(this);" placeholder="<%= desc %>"/>
		        
		        <% 	} else { %>
	        			<select id="<%= label %>" name="<%= key %>" class="selectEqualWidth" onfocus="showDesc(this);">
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
    
		<% if (masterKey.equals("Server")) { %>
			    <div id="remoteDeployDiv" class="clearfix" >
			    	<label for="xlInput" class="new-xlInput" id="remoteDeployLbl"><s:text name="label.remote.deploy"/></label>
				    <div class="input new-input">
				        <input type="checkbox" id="remoteDeploy" name="remoteDeploy" value="true" style = "margin-top: 8px;">
				    </div>
				</div>
		<% } %>
<%
    	}
    }
%>

<script type="text/javascript">
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
		
		/** To display projectInfo servers starts **/
		<%
		     String serverName = null; 
			if (CollectionUtils.isNotEmpty(projectInfoServers) && projectInfoServers != null) {
		%>
				$('#type').find('option').remove();
				<%
					for(Server projectInfoServer : projectInfoServers) {
						 serverName = projectInfoServer.getName();
						%>
							$('#type').append($("<option></option>").attr("value", '<%= serverName %>').text('<%= serverName %>'));
							var server = $('#type').val();
							if( server.trim() == "Apache Tomcat" || server.trim() == "JBoss" || server.trim() == "WebLogic"){
								 $("#remoteDeployDiv").show();	
						     } else {
						    	 $("#remoteDeployDiv").hide();
						     }
		<%
             }
		%>
				getCurrentVersions('');
				
				$("#type").change(function() {
				 var server = $('#type').val();
				 if( server.trim() == "Apache Tomcat" || server.trim() == "JBoss" || server.trim() == "WebLogic"){
					 $("#remoteDeployDiv").show();	
			     } else {
			    	 $("#remoteDeployDiv").hide();
			     }
				
			});   
		<%
			}
		%>
		
		/** To display projectInfo servers ends **/
		
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
		/** To display projectInfo databases ends **/
		
		// Hide deploy dir if NodeJs server is created
		if($("#type option:selected").val() == "NodeJS") {
			hideDeployDir();
		}
		
		// hide deploy dir if remote Deployment selected
		 
         $('#remoteDeploy').change(function() {
				var isChecked = $('#remoteDeploy').is(":checked");
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
        
		 
		/** to display corressponding versions **/
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
		
        $('#Port').live('input paste', function (e) { 	//Port validation
        	var portNo = $(this).val();
        	portNo = checkForNumber(portNo);
        	$(this).val(portNo);
         });
        
        $("#xlInput").live('input paste',function(e){ 	//Name validation
        	var name = $(this).val();
        	name = checkForSplChr(name);
        	$(this).val(name);
        });
        
        $("input[name='dbname']").live('input paste',function(e){ 
        	var name = $(this).val();
        	name = checkForSplChr(name);
        	name = removeSpace(name);
        	$(this).val(name);
        });
        
        $("input[name='context']").live('input paste',function(e){	//Con
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
</script>