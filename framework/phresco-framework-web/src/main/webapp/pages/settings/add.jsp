<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
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
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.MapUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.configuration.Environment"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.model.SettingsTemplate"%>
<%@ page import="com.photon.phresco.model.PropertyInfo"%>
<%@ page import="com.photon.phresco.model.Technology"%>

<%@ include file="../userInfoDetails.jsp" %>

<%
    String selectedStr = "";
    String name = "";
    String description = "";
    String selectedType = "";
    String fromPage = "";
    String error = "";
    String envName = "";
    List<String> appliesTo = null;
    
    String currentEnv = (String) request.getAttribute("currentEnv");
    String oldName = (String) request.getAttribute(FrameworkConstants.SESSION_OLD_NAME);
    if (oldName == null) {
        oldName = "";
    }
    SettingsInfo configInfo = (SettingsInfo) session.getAttribute(oldName);
    
    if (configInfo != null) {
        envName = configInfo.getEnvName();
        name = configInfo.getName();
        description = configInfo.getDescription();
        selectedType = configInfo.getType();
        appliesTo = configInfo.getAppliesTo();
    } 
    
    if(request.getAttribute(FrameworkConstants.REQ_FROM_PAGE) != null) {
        fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
    }
    
    List<Environment> envs = (List<Environment>) request.getAttribute(FrameworkConstants.ENVIRONMENTS);
    Map<String, String> errorMap = (Map<String, String>) session.getAttribute(FrameworkConstants.ERROR_SETTINGS);
    Map<String, Technology> mapTechnologies = (Map<String, Technology>)request.getAttribute(FrameworkConstants.REQ_ALL_TECHNOLOGIES);
%>

<!--  Heading starts -->
<%
    if(StringUtils.isEmpty(fromPage)) {
%>
        <div class="page-header">
            <h1><s:text name="label.settings.add"/></h1>
        </div>
<%
    } else {
%>
        <div class="page-header">
            <h1><s:text name="label.settings.edit"/></h1>
        </div>
<%  } %>
<!--  Heading ends -->

<!--  Action Messages starts -->
<s:if test="hasActionMessages()">
    <div class="settings-alert-message success"  id="successmsg">
        <s:actionmessage />
    </div>
</s:if>
<!--  Action Messages ends -->
    
        <!--  Form starts -->
        <%
           if(StringUtils.isEmpty(fromPage)) {
        %>
              <form action="saveSetting" method="post" autocomplete="off" class="settings_add_form">
        <%
           } else {
        %>
			  <form action="updateSetting" method="post" autocomplete="off" class="settings_add_form">
        <% } %>
        
		<% 
			String ErrName = null; 
			if (MapUtils.isNotEmpty(errorMap) && StringUtils.isNotEmpty(errorMap.get("name"))) {
				ErrName =  errorMap.get("name");
			}
		%>
            	<div class="settings_div">
	                <!--  Name starts -->
	                <div class="clearfix" id="nameErrDiv" style="padding: 0;">
	                    <label class="new-xlInput"><span class="red">*</span> <s:text name="label.name"/></label>
	                    <div class="input new-input"  style="padding-top:5px;">
	                    	<div class="typeFields">
		                        <input class="xlarge settings_text" id="xlInput" name="settingsName"
		                            type="text" maxlength="30" title="30 Characters only" value ="<%= name%>" autofocus="true" 
		                            placeholder="<s:text name="label.name.config.placeholder"/>"/>
	                        </div>
	                        
	                        <div>
					            <div class="lblDesc configSettingHelp-block" id="nameErrMsg">
					                
						        </div>
					        </div>
	                    </div>
	                </div>
	                <!--  Name ends -->
	                
	                <!--  Description starts -->
	                <div class="clearfix">
	                    <s:label key="label.description" theme="simple" cssClass="new-xlInput"/>
	                    <div class="input new-input">
	                        <textarea  class="appinfo-desc xxlarge" maxlength="150" title="150 Characters only" class="xxlarge" id="textarea" name="description" placeholder="<s:text name="label.description.config.placeholder"/>"><%=description%></textarea>
	                    </div>
	                </div>
	                <!--  Description ends -->
	                
	                <div class="clearfix" id="envDiv">
            			<label class="new-xlInput"><span class="red">* </span><s:text name="label.environment"/></label>
            			<div class="input new-input"> 
            				<div class="typeFields">
		                        <select id="environments" name="environments" class="selectEqualWidth">
	                    			<%
	                    			    if(envs != null) {
	                    			        for(Environment env : envs) {
	                    			%>
	                    				        <option value="<%= env.getName() %>"><%= env.getName() %></option>
	                    			<% 
	                    			        } 
	                    			     }
	                    			%>
	                			</select>
	                        </div>
	                        
	                        <div>
								<div class="lblDesc configSettingHelp-block" id="envErrMsg">
					                
						        </div>
					        </div>
	                    </div>
        			</div>
	                
	                <%
	                    List<SettingsTemplate> settingsTemplates = (List<SettingsTemplate>) request.getAttribute(FrameworkConstants.SESSION_SETTINGS_TEMPLATES);
	                %>
	                    
                    <!--  SettingTemplate starts -->
                    <div class="clearfix" id="settingTypeDiv">
                        <label class="new-xlInput"><s:text name="label.type"/></label>
                        <div class="input new-input">
                        	<div class="typeFields">
	                            <select id="settingsType" name="settingsType" class="selectEqualWidth">
	                                <%
	                                    if (settingsTemplates != null) {
	                                        for (SettingsTemplate settingsTemplate : settingsTemplates) {
	                                            String type = settingsTemplate.getType();
	                                            if(selectedType != null  && (type.equals(selectedType) || FrameworkConstants.REQ_CONFIG_TYPE_OTHER.equals(selectedType))){
	                                                selectedStr = "selected";
	                                            } else {
	                                                selectedStr = "";
	                                            }
	                                %>
	                                    <option value="<%= type %>"<%= selectedStr %>><%= type %></option>
	                                <%
	                                        }
	                                    }
	                                %>
	                                <option value="Other" <%= selectedStr %>>Other</option>
	                            </select>
							</div>
                            <div>
                                 <div class="lblDesc configSettingHelp-block" id="configTypeErrMsg"></div>
                            </div>
                         </div>
                    </div>
                    <!--  SettingTemplate ends -->
	                        
					<div id="type-child-container" class="settings_type_div"></div>
					
					<!-- Dynamic configuration starts -->
					<div id="configTypeDivOthers" class="hideContent">
						<% if (configInfo == null) { %>
							<div class="clearfix parent_key1" id="propTemplateDiv1">
								<input type="text" id="key1" name="propertyKey" placeholder="<s:text name="placeholder.config.prop.key"/>" 
									style="float: left; margin-left: 80px;">
								<div class="input new-input">
									<div class="typeFields">
										<input id="value1" type="text" name="value" class="xlarge key1" placeholder="<s:text name="placeholder.config.prop.value"/>">
									</div>
									<div style="float: left;">
										<div class="lblDesc configSettingHelp-block" id="errorMsg_key1"></div>
									</div>
									
									<a id="addFields" href="#" style="vertical-align: top;">
										<img class="headerlogoimg perImg" src="images/icons/add_icon.png" alt="add">
									</a> &nbsp;&nbsp; 
									<a id="removeFields" href="#">
										<img class="headerlogoimg perImg" src="images/icons/minus_icon.png" alt="remove">
									</a>
								</div>			
							</div>
						<% 
							} else {
								List<PropertyInfo> propertyInfos = configInfo.getPropertyInfos();
								if (CollectionUtils.isNotEmpty(propertyInfos)) {
									int i = 1;
									for (PropertyInfo propertyInfo : propertyInfos) {
										String key = propertyInfo.getKey();
										String value = propertyInfo.getValue();
						%>
										<div class="clearfix parent_key<%= i %>" id="propTemplateDiv<%= i %>">
											<input type="text" id="key<%= i %>" name="propertyKey" value="<%= key %>"
												placeholder="<s:text name="placeholder.config.prop.key"/>" style="float: left; margin-left: 80px;">
											<div class="input new-input">
												<div class="typeFields">
													<input type="text" name="<%= key %>" class="xlarge key<%= i %>" value="<%= value %>"
														placeholder="<s:text name="placeholder.config.prop.value"/>">
												</div>
												<div style="float: left;">
													<div class="lblDesc configSettingHelp-block" id="errorMsg_key<%= i %>"></div>
												</div>
											<% if (i == 1) { %>	
												<a id="addFields" href="#" style="vertical-align: top;">
													<img class="headerlogoimg perImg" src="images/icons/add_icon.png" alt="add">
												</a> &nbsp;&nbsp; 
												<a id="removeFields" href="#">
													<img class="headerlogoimg perImg" src="images/icons/minus_icon.png" alt="remove">
												</a>
											<% } %>
											</div>			
										</div>
						<%
										i++;
									}
								}
							}
						%>
					</div>
					<!-- Dynamic configuration ends -->
					
					<div class="clearfix hideContent appliesToDiv" id="appliesToErrDiv">
						<label class="new-xlInput"><span class="red">*</span>
							<s:text name="label.applies.to" />
						</label>
						<div class="input new-input">
							<div class="typeFields" id="typefield">
								<div id="multilist-scroller">
									<ul>
									<%
										if (MapUtils.isNotEmpty(mapTechnologies)) {
											Collection<Technology> technologies = mapTechnologies.values();
											if (CollectionUtils.isNotEmpty(technologies)) {
												for (Technology technology : technologies) {
													if (CollectionUtils.isNotEmpty(appliesTo) && appliesTo.contains(technology.getId())) {
														selectedStr = "checked";
				    								} else {
				    									selectedStr = "";
				    								}
						            %>
												<li>
													<input type="checkbox" name="appliesto" id="appliesto" value="<%= technology.getId() %>"
														class="check" <%= selectedStr %>> <%= mapTechnologies.get(technology.getId()).getName() %>
												</li>
									<%
												}
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
	                if(StringUtils.isNotEmpty(fromPage)) {
	            %>
	                    <input type="hidden" id="oldName" name="oldName" value="<%= oldName %>" />
	                    <input type="hidden" id="oldSettingType" name="oldSettingType" value="<%= selectedType %>" />
	            <% } %>
	                
	        	<input type="hidden" id="settingsCount" name="settingsCount" value="" />
            </div>
            <div class="clearfix">
                <label>&nbsp;</label>
                <div class="submit_input">
                    <input type="button" value="<s:text name="label.save"/>" id="save" class="primary btn">
                    <input type="button" value="<s:text name="label.cancel"/>" id="cancelSettings" class="primary btn">
                </div>
            </div>
            <!--  Submit and Cancel button ends -->
        </form>
        <!--  Form ends -->

<%
	if (configInfo != null && StringUtils.isNotEmpty(oldName)) { //persist configInfo to retain while failing on validation 
	    session.setAttribute(oldName, configInfo);
	}
%>    
<script type="text/javascript">

var selectedType = "";
var fromPage = "";

/** To remove all the environments except the selected environment **/
if(<%= StringUtils.isNotEmpty(currentEnv) %>) {
	$('#environments').empty().append($("<option></option>").attr("value",'<%= currentEnv %>').attr("selected", "selected").text('<%= currentEnv %>'));
}

/* To check whether the divice is ipad or not */
if(!isiPad()){
	/* JQuery scroll bar */
	$(".settings_div").scrollbars();
}

$(document).ready(function() {
	
    settingsType();
    
    $('#settingsType').change(function() {
        settingsType();
    });
    
    $("#cancelSettings").click(function() {
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
		showLoadingIcon($("#container")); // Loading Icon
    	performAction("settings", params, $('#container'));
    });
    
    $('#save').click(function() {
    	if (validateKeyFields()) {
	    	$("input[name=certificate]").prop("disabled", false);
	    	var params = "";
	    	params = params.concat("&remoteDeployment=");
			params = params.concat($("input[name='remoteDeployment']").prop("checked"));
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
		   	<%
		       if (StringUtils.isEmpty(fromPage)) {
		    %>
		        performAction("saveSetting", params, $('#container'));
		    <%
		       } else {
		    %>
		        performAction("updateSetting", params, $('#container'));
		    <% } %>
    	}
    });
    
    window.setTimeout(function () { document.getElementById('xlInput').focus(); }, 250);
    
    var counter = 0;
	//To update the counter value during edit
	<%
		if (configInfo != null) {
			List<PropertyInfo> propertyInfos = configInfo.getPropertyInfos();
	%>
			counter = <%= propertyInfos.size() + 1 %>;
	<%
		} else {
	%>
			counter = 2;
	<% } %>
	
	$("#addFields").click(function () {
		if (counter > 25) {
			return false;
		}
		
		var key = "key" + counter;
		var value = "value" + counter;
		
		var keyElement = '<input type="text" id="' + key + '" name="propertyKey" placeholder="<s:text name="placeholder.config.prop.key"/>"' + 
							' style="float: left; margin-left: 80px;">';
		var valueElement = '<input type="text" id="' + value + '" name="value" class="xlarge ' + key + '" ' + 
							'placeholder="<s:text name="placeholder.config.prop.value"/>">';
		
	 	var newFieldsDiv = $(document.createElement('div')).attr("id", 'propTemplateDiv' + counter).attr("class", 'clearfix parent_' + key);
	 	
	 	newFieldsDiv.html(keyElement + '<div class="input new-input"><div class="typeFields">' + valueElement + '</div>' + 
	 						'<div style="float: left;"><div class="lblDesc configSettingHelp-block" id="errorMsg_'+ key + '"></div></div></div>');
		newFieldsDiv.appendTo("#configTypeDivOthers");
		counter++;
	});
	
	$("#removeFields").click(function () {
		if (counter == 2) {
			return false;
	    }
		counter--;
        $("#propTemplateDiv" + counter).remove();
	});
	
	//To change the name of the property value textbox based on the property key textbox value
	$("input[name=propertyKey]").live("blur", function() {
		var valueClass = $(this).attr("id");
		$("." + valueClass).attr("name", $(this).val());
	});
});
 
function settingsType() {
    selectedType = $("#settingsType").val();
    if (selectedType == "Other") {
    	$("#type-child-container").empty();
    	$("#configTypeDivOthers").show();
    	$(".appliesToDiv").show();
    	enableScreen();
    } else {
    	$("#configTypeDivOthers").hide();
    	$(".appliesToDiv").hide();
	    var settingsCount = $("#settingsCount").val();
	    fromPage = "<%= fromPage %>";
		var params = "";
		if (!isBlank($('form').serialize())) {
			params = $('form').serialize() + "&";
		}
		params = params.concat("envName=");
		params = params.concat('<%= envName %>');
		params = params.concat("&" + '<%= FrameworkConstants.REQ_FROM_PAGE %>' + "=");
		params = params.concat(fromPage);
		performAction('settingsType', params, '', true);
    }
}

function successSettingType(data) {
	$("#type-child-container").html(data);
	if (fromPage != undefined) {
    	$("#Host").focus();
        $("#Protocol").focus();     	
    } 
}

function addType() {
    var clonedContainer = $('#type-container').clone();
    
    clonedContainer.children(true, true).each(function() {
        var kid = $(this);
        console.log(kid.attr('id'));
    });
    
    $('#type-parent').append(clonedContainer);
}

function validationError(data) {
	$(".clearfix").removeClass("error");
	$(".lblDesc").text("");
	if(data.nameError != null){
		$("#nameErrMsg").text(data.nameError);
    	$("#nameErrDiv").addClass("error");
    	$("#xlInput").focus(); 
	} 
	
	if(data.envError != null){
		$("#envErrMsg").text(data.envError);
    	$("#envDiv").addClass("error");
	}
	
	if(data.typeError != null){
        $("#configTypeErrMsg").text(data.typeError);
        $("#settingTypeDiv").addClass("error");
    }
	
	if(data.dynamicError != null) {
    	var dynamicErrors = data.dynamicError.split(",");
    	
    	for (var i=0; i<dynamicErrors.length; i++) {
    		var dynErr = dynamicErrors[i].split(":");
     		$("div[id='" + dynErr[0] + "']").addClass("error");
     		if (i == 0) {
     			$("input[name='" + dynErr[0] + "']").focus();     			
     		}
        	$("div[id='" + dynErr[0] + "ErrorDiv']").html(dynErr[1]);
    	}
	}

	if(data.portError != null) {
		$("div[id='port']").addClass("error");
    	$("div[id='portErrorDiv']").html(data.portError);
   }
	
	if(data.appliesToError != null) {
		$("#appliesToErrMsg").html(data.appliesToError);
    	$("#appliesToErrDiv").addClass("error");
    	if (data.dynamicError == null) {
    		$("#appliesto").focus();
    	}
	}
	
	if(data.emailError != null) {
		$("div[id='emailid']").addClass("error");
    	$("div[id='emailidErrorDiv']").html(data.emailError);
    }
}

function successEvent(pageUrl, data) {
	if(pageUrl == "settingsType") {
		successSettingType(data);
	}
	if (pageUrl == "fetchSettingProjectInfoVersions") {
		fillVersions("version", data.projectInfoVersions);
		if (versionFrom == "") {
			showSetttingsInfoVersion();				
		}
	}
}

function getCurrentVersions(from) {
	versionFrom = from;
	if (versionFrom == "") {
		showSetttingsInfoServer();			
	}
	var settingType = $("#settingsType").val();
	var name = $('#type').val();
    var params = "";
	params = params.concat("&settingsType=");
	params = params.concat(settingType);
	params = params.concat("&name=");
	params = params.concat(name);
	performAction('fetchSettingProjectInfoVersions', params, '', true);
}

//To empty validate the dynamic configurations property template keys and value
function validateKeyFields() {
	var returnValue = true;
	var selectedType = $("#settingsType").val();
	var name = $("input[name=settingsName]").val();
    if (selectedType != "Other" || isBlank(name)) {
		return true;
	}
    $("input[name=propertyKey]").each(function() {
    	var keyElementId = $(this).attr("id");
    	var key = $(this).val();
    	var value = $("input[name='" + key + "']").val();
    	var focusObj;
    	var errorMsgObj = $("#errorMsg_" + $(this).attr("id"));
    	var parentDivClass = "parent_" + $(this).attr("id");
    	var errorMsg = "";
    	if (isBlank(key)) {
    		errorMsg = "Property key is Missing";
    		focusObj = $(this);
    		returnValue = false;
    	} else if (isBlank(value)) {
    		errorMsg = "Property value is Missing";
    		focusObj = $("input[name='" + key + "']");
    		returnValue = false;
    	} else {
    		$("input[name=propertyKey]").each(function() {
        		if (keyElementId != $(this).attr("id") && key == $(this).val()) {
        			errorMsg = "Property key already exists";
        			focusObj = $(this);
        			returnValue = false;
        			parentDivClass = "parent_" + $(this).attr("id");
        			errorMsgObj = $("#errorMsg_" + $(this).attr("id"));
        			return false;
        		}
        	});
    	}
    	
    	if (!returnValue) { //To show the error msg if exists
    		$(".clearfix").removeClass("error");
    		$(".lblDesc").text("");
    		focusObj.focus();
    		$("." + parentDivClass).addClass("error");
    		errorMsgObj.html(errorMsg);
    		return false;
    	} else {
    		$(".clearfix").removeClass("error");
    		$("#errorMsg_" + $(this).attr("id")).html("");
    	}
    });

    return returnValue;
}
</script>