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
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.configuration.Environment" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.photon.phresco.model.*"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>

<% 
    String fromTab = (String) request.getAttribute(FrameworkConstants.SETTINGS_FROM_TAB);
    String projectCode = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
    List<Environment> envInfoValues = (List<Environment>) request.getAttribute(FrameworkConstants.ENVIRONMENTS);
    String configName = (String) request.getAttribute(FrameworkConstants.CLONE_FROM_CONFIG_NAME);
    String copyFromEnvName = (String)request.getAttribute(FrameworkConstants.CLONE_FROM_ENV_NAME);
    String configType = (String) request.getAttribute(FrameworkConstants.CLONE_FROM_CONFIG_TYPE);
%> 
<form autocomplete="off">
<div class="popup_Modal" style="top: 40%;">
	<div class="modal-header">
		<h3 id="generateBuildTitle">
			Clone Environment
		</h3>
		<a class="close" href="#" id="close">&times;</a>
	</div>

	<div class="modal-body">
		
		<fieldset class="popup-fieldset"">
			<legend class="fieldSetLegend" >Clone Configuration</legend>
			<div class="clearfix">
	            <label for="xlInput" class="xlInput popup-label" ><span class="red">*</span><s:text name = "label.name" /></label>
	            <div class="input">
	                <input type="text" id="configEnvName" name="configName" tabindex=1 style="float: left;" class="xlarge configEnvName" maxlength="30" title="30 Characters only"> 
	            </div>
	        </div>
	        <div class="clearfix">
	            <label for="xlInput" class="xlInput popup-label"><s:text name="label.description"/></label>
	            <div class="input">
	                <textarea id="envDesc" name="description" class="xlarge env-desc"  style="float: left;" tabindex=2 maxlength="150" title="150 Characters only"></textarea>
	            </div>
	        </div>
	        <div class="clearfix">
                   <label for="xlInput" class="xlInput popup-label"><span class="red">*</span><s:text name="label.environment"/></label>
                   <div class="input">
                       <select name="envName" id="configEnv" tabindex=3 class="xlarge cloneConfigToEnv" style="float: left;width: 300px;">
						<%
						   for(Environment env : envInfoValues ) {
							   if(!env.getName().equals(copyFromEnvName) ) {
	                    %>
	                       <option value="<%= env.getName() %>" title="<%= env.getDesc() %>" id="created"><%= env.getName() %></option>
	                    <% 
							   }  
						}
	                    %>
                    </select>
                   </div>
               </div>
		</fieldset>
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action">
		    <input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel" tabindex=4>
            <input type="button" class="btn primary" value="<s:text name="label.add"/>" id="add" tabindex=5>
            
			<div id="errMsg" class="envErrMsg"></div>
			<div id="reportMsg" class="envErrMsg"></div>
<!-- 			error and success message -->
			<div class="popup alert-message success" id="popupSuccessMsg"></div>
			<div class="popup alert-message error" id="popupErrorMsg"></div>
		</div>
	</div>
</div>

<!-- Hidden fields -->
<!-- <input type="hidden" name="cloneConfigStatus" value="true"> -->
<input type="hidden" name="cloneFromEnvName" value="<%= copyFromEnvName %>">
<input type="hidden" name="cloneFromConfigType" value="<%= configType %>">
<input type="hidden" name="cloneFromConfigName" value="<%= configName %>">
</form>

<script type="text/javascript">
    escPopup();

	$('#close, #cancel').click(function() {
		$('#popup_div').empty();
	    showParentPage();
	});
	
	$(document).ready(function() {
		$("#add").click(function() {
			 if (isBlank($('#configEnvName').val())) {
				 $("#errMsg").html("<%=FrameworkConstants.CONFIGURATION_NAME_EMPTY %>");
				 return false;
			 } else if ($("#created").size() == 0 ) {
				 $("#errMsg").html("<%=FrameworkConstants.ADD_ONE_ENVIRONMENT %>");
				 return false;
			 } else {
				 var params = "";
				 if (!isBlank($('form').serialize())) {
				 params = $('form').serialize();
				 }
				 performAction('cloneConfiguration', params, '', true);
			 }
		});
	});
	
	function emptyMessages() {
        $("#errMsg, #reportMsg").html("");
    }
	
	function successEvent(pageUrl, data) {
        if (pageUrl == "cloneConfiguration") {
        	emptyMessages();
        	
        	if (!data.flag && data.envError != undefined) {
                    $("#errMsg").html(data.envError);
            } else {
                var params = "";
                // cloneConfig = success
                $("#popup_div").css("display","none");
                $('#popup_div').empty();
                if (!isBlank($('form').serialize())) {
                    params = $('form').serialize() + "&";
                }
                params = params.concat("cloneConfigStatus=");
                params = params.concat("true"); 
                performAction("configuration", params, $('#tabDiv'));
            }
        }
        
    }
	
	function emptyMessages() {
        $("#errMsg, #reportMsg").html("");
    }
</script>