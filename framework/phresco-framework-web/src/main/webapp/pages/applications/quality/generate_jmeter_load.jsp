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
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.configuration.Environment"%>

<%@include file="..\progress.jsp" %>
<script src="js/select-envs.js"></script>

<%
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);   
	String technology = (String)request.getAttribute("technology");
	String showSettings = (String) request.getAttribute(FrameworkConstants.REQ_SHOW_SETTINGS);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	String selectedTestType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE_SELECTED);
	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
%>
<div class="popup_Modal per_popup_Modal" id="performance-popup" style="width: 620px; top: 76%; margin-left: -280px; ">
	<form action="perTest">
		<div class="modal-header">
			<h3><s:text name="label.load.test"/></h3>
			<a class="close" href="#" id="close">&times;</a>
		</div>
		
		<div class="modal-body perModalBody">
			<fieldset class="popup-fieldset fieldsetBottom perFieldSet">
				<legend class="fieldSetLegend"><s:text name="label.test.against"/></legend>
	            <!-- Show Settings -->
	            <div class="clearfix marginBottomZero">
	                <div class="xlInput">
	                    <ul class="inputs-list marginBottomZero" style="text-align: center;">
	                        <li class="popup-li" style="padding-top: 0px;"> 
	                            <input type="radio" id="showServer" name="jmeterTestAgainst" value="<%= Constants.SETTINGS_TEMPLATE_SERVER %>" checked>
	                            <span class="textarea_span popup-span"><s:text name="label.server"/></span>
	                            <input type="radio" id="showWebservice" name="jmeterTestAgainst" value="<%= Constants.SETTINGS_TEMPLATE_WEBSERVICE %>" >
	                            <span class="textarea_span popup-span"><s:text name="label.webservices"/></span>
	                        </li>
	                    </ul>
	                </div>  
	            </div>
	            
	            <div class="clearfix perClearFix">
	            	<label for="xlInput" class="xlInput popup-label perShowSettingLbl" style="width: 169px;"></label>
	            	<label for="xlInput" class="xlInput popup-label perSelectedTypeTitle">
	            	<span class="red">*</span>
	            	<s:text name="label.environment"/></label>
	            	<label for="xlInput" class="xlInput popup-label perfConfName" id="selectedTypeTitle" style="width: auto;">
	            	<span class="red">*</span>
	            	<s:text name="label.server"/></label>
	            </div>
	            
	            <div class="clearfix perClearFix" style="padding-top: 0; margin-left: 28px;">
	            	<label for="xlInput" class="xlInput popup-label perShowSettingLbl" style="width: 140px;">
	            		<font style="padding-top: 0px;vertical-align: top;"><s:text name="label.show.setting"/> &nbsp;</font>
	            		<input type="checkbox" style="" name="showSettings" id="showSettings">
	            	</label>
	            	<label for="xlInput" class="xlInput popup-label perSelectBox">
						<select id="environments" name="environment" class="xlarge" style="width: 149px;">
							<optgroup label="Configurations" class="optgrplbl">
							<%
								String defaultEnv = "";
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
									<option value="<%= environment.getName() %>" <%= selectedStr %>><%= environment.getName() %></option>
							<% 		} 
								}
							%>
							</optgroup>
						</select>
	            	</label>
	            	<label for="xlInput" class="xlInput popup-label perfConfName" style="width: auto;">
	            		<input type="text" id="Server" name="Server" class="xlarge" style="width: 149px;" disabled="disabled">
						
						<select id="WebService" name="WebService" class="xlarge" style="width: 149px;display: none;">
							
						</select>
	            	</label>
	            </div>

				<div class="clearfix serverSettingsDiv perCaptionDisp" id="caption">
		            	<label for="xlInput" class="xlInput popup-label perTestName" style="width: 163px;">
		            	<span class="red">*</span>
		            	<s:text name="label.test.rslt.name"/></label>
		            	<label for="xlInput" class="xlInput popup-label perTestNameLab">
		            		<input type="text" name="testName" id="testName" maxlength="20" title="20 Characters only" value="" style="width: 170px;">
		            	</label>
				</div>				
	        </fieldset>
	        
			<fieldset class="popup-fieldset perFieldSetLoad perThreadGroupAlgnLoad" style="margin-left: 0;">
				<legend class="fieldSetLegend"><s:text name="label.thread.group"/></legend>
				<div class="clearfix" style="margin-bottom: 10px;">
					<span class="red">*</span>
					<s:text name="label.no.of.users"/> &nbsp;
					<input id="noOfUsers" type="text" name="noOfUsers" title="No of Users" class="perThreadGrouptxtbox">&nbsp;
					<span class="red">*</span>
					<s:text name="label.ramp.period"/> &nbsp;
					<input id="rampUpPeriod" type="text" name="rampUpPeriod" title="Ramp-Up Period" class="perThreadGrouptxtbox">&nbsp;
					<span class="red">*</span>
					<s:text name="label.loop.count"/> &nbsp;
					<input id="loopCount" type="text" name="loopCount" title="Loop Count" class="perThreadGrouptxtbox">&nbsp;
				</div>
			</fieldset>
		</div>
			
		<div class="modal-footer">
			<div class="action popup-action" style="width: 98%;">
			<div id="errMsg" style="width:72%; text-align: left; float: left;"></div>
			<div style="float: right;">
				<input type="button" id="actionBtn" class="btn primary" value="<s:text name="label.test"/>" style="float: left;">
				<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel" style="float: right;">
			</div>
			</div>
		</div>
	</form> 
</div>

<script type="text/javascript">
	getConfigNames();
	
    if( <%= TechnologyTypes.SHAREPOINT.equals(technology) %> || <%= TechnologyTypes.DOT_NET.equals(technology) %>) {
        $('#showWebservice').attr("disabled","disabled");
    }
    
    var resultFileLists = null;
    
    $(document).ready(function() {
		getTestResultFiles();
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});

		$('#actionBtn').click(function() {
			checkForConfigType($('input:radio[name=jmeterTestAgainst]:checked').val());
		});
		
		$('input[type="radio"]').change(function() {
			if($('input:radio[name=jmeterTestAgainst]:checked').val() == "Server") {
				getConfigNames();
				emptyAndReplaceWith('selectedTypeTitle', '<font color="red">* </font>Server');
				showHideDiv('Server', 'WebService');
			} else if($('input:radio[name=jmeterTestAgainst]:checked').val() == "WebService") {
				getConfigNames();
				emptyAndReplaceWith('selectedTypeTitle', '<font color="red">* </font>Webservice');
				showHideDiv('WebService', 'Server');
			} 
		});
		
		$("#noOfUsers, #rampUpPeriod, #loopCount").keydown(function(e) {
            var key = e.charCode || e.keyCode || 0;
            // allow backspace, tab, delete, arrows, numbers and keypad numbers ONLY
            return (
                key == 8 || 
                key == 9 ||
                key == 46 ||
                (key >= 37 && key <= 40) ||
                (key >= 48 && key <= 57) ||
                (key >= 96 && key <= 105));
        });
		
		$('#environments').change(function() {
			getConfigNames();
		});
		 
	    $("#testName").live('input paste',function(e) { 	//testName validation
	     	var name = $(this).val();
	     	name = isContainSpace(name);
	     	$(this).val(name);
	    });
	});
    
    function showHideDiv(showId, hideId) {
		$('#'+ showId).show();
		$('#'+ hideId).hide();
	}
    
    function emptyAndReplaceWith(elemId, data) {
		$('#'+ elemId).empty();
		$('#'+ elemId).html(data);
	}
    
    function successEnvValidation(data) {
		if(data.hasError == true) {
			showError(data);
		} else {
			var testname = $("#testName").val();
			if($('#testName').val() == null || $('#testName').val().trim() == "") {
				$("#errMsg").empty();
				$("#errMsg").html("Enter Test Result Name");
				$("#testName").val("");
				$("#testName").focus();
			} else if($('#noOfUsers').val() == null || $('#noOfUsers').val() == "") {
                $("#errMsg").html("Enter No of Users");
                $('#noOfUsers').focus();
                return false;
            } else if($('#rampUpPeriod').val() == null || $('#rampUpPeriod').val() == "") {
                $("#errMsg").html("Enter Ramp-up Period");
                $('#rampUpPeriod').focus();
                return false;
            } else if($('#loopCount').val() == null || $('#loopCount').val() == "") {
                $("#errMsg").html("Enter Loop Count");
                $('#loopCount').focus();
                return false;
            } else if(testname != "" && resultFileLists != undefined && resultFileLists != "") {
				var found = false;
				for (var i=0; i < resultFileLists.length ; i++) {
					  if (resultFileLists[i] == $('#testName').val()+'.xml') {
					    found = true;
					    break;
					  }
				}
				if(found == true) {
					validator = false;
					$("#errMsg").empty();
					$("#errMsg").html("Test Name Already Exists.");
					return false;
				}
				else {
	                $("#build-output").empty();
	                $('#tests').show().css("display","none");
	                $('#build-outputOuter').show().css("display","block");
	                progress();
	            }
			}  else {
                $("#build-output").empty();
                $('#tests').show().css("display","none");
                $('#build-outputOuter').show().css("display","block");
                progress();
            }
		}
	}
	
	function progress() {
		$('.popup_div').show().css("display","none");
		var param = '<%= FrameworkConstants.ENVIRONMENTS %>' + "=";
		param = param.concat(getSelectedEnvs());
		readerHandlerSubmit('load', '<%= projectCode %>', '<%= FrameworkConstants.LOAD %>', param);
		return false;
	}
	
	function getTestResultFiles() {
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	performAction('tstResultFiles', params, '', true);
	}
	
	function successGetTestResultFiles(data) {
		resultFileLists = data;
	}
	
	function successEvent(pageUrl, data) {
		if(pageUrl == "tstResultFiles") {
			successGetTestResultFiles(data);
		}
		if(pageUrl == "checkForConfigType") {
			successEnvValidation(data);
		}
	}
</script>