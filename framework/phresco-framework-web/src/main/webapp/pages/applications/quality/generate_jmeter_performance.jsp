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
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.configuration.Environment"%>

<%@ include file="..\progress.jsp" %>
<%@ include file="..\quality\request_header.jsp" %>

<script src="js/reader.js" ></script>
<script src="js/select-envs.js"></script>

<%
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	String selectedTestType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE_SELECTED);
	String jmeterTestAgainst = (String) request.getAttribute("jmeterTestAgainst");
	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
%>
<style>
.ui-autocomplete {
   width:165px;
   background:#F5F5F5;
   display: block;
   left: 489px;
   position: relative;
   top: 271px;
   z-index: 60003;
   height:40px;
   overflow-y:auto;
}

.ui-menu-item{
   padding :2px 10px;
   list-style: none outside none;
}

.ui-menu-item a{
    color:#000;
} 

</style>
<div class="popup_Modal per_popup_Modal" id="performance-popup">
<form>	
		<div class="modal-header">
			<h3><s:text name="label.performance.test"/></h3>
			<a class="close" href="#" id="close">&times;</a>
		</div>
		
		<div class="modal-body perModalBody">
			<fieldset class="popup-fieldset fieldsetBottom perFieldSet">
				<legend class="fieldSetLegend"><s:text name="label.test.against"/></legend>
	            <!-- Show Settings -->
	            <div class="clearfix marginBottomZero">
	                <div class="xlInput">
	                    <ul class="inputs-list marginBottomZero">
	                        <li class="popup-li" style="padding-top: 0px;"> 
	                            <input type="radio" id="showServer" name="jmeterTestAgainst" value="<%= Constants.SETTINGS_TEMPLATE_SERVER %>" checked>
	                            <span class="textarea_span popup-span"><s:text name="label.server"/></span>
	                            <input type="radio" id="showWebservice" name="jmeterTestAgainst" value="<%= Constants.SETTINGS_TEMPLATE_WEBSERVICE %>" >
	                            <span class="textarea_span popup-span"><s:text name="label.webservices"/></span>
	                            <input type="radio" id="showDatabase" name="jmeterTestAgainst" value="<%= Constants.SETTINGS_TEMPLATE_DB %>">
	                            <span class="textarea_span popup-span"><s:text name="label.db"/></span>
	                        </li>
	                    </ul>
	                </div>  
	            </div>
	            
	            	            <!-- Show Settings -->
	            <div class="clearfix perClearFix">
	            	<label for="xlInput" class="xlInput popup-label perShowSettingLbl"></label>
	            	<label for="xlInput" class="xlInput popup-label perSelectedTypeTitle">
	            	<span class="red">*</span>
	            	<s:text name="label.environment"/></label>
	            	<label for="xlInput" class="xlInput popup-label perfConfName" id="selectedTypeTitle">
	            	<span class="red">*</span>
	            	<s:text name="label.server"/></label>
	            </div>
	            
	            <div class="clearfix perClearFix">
	            	<label for="xlInput" class="xlInput popup-label perShowSettingLbl">
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
	            	<label for="xlInput" class="xlInput popup-label perfConfName">
	            		<input type="text" id="Server" name="Server" class="xlarge" style="width: 149px;" disabled="disabled">
						
						<select id="WebService" name="WebService" class="xlarge" style="width: 149px;display: none;">
							
						</select>
                         	<select id="Database" name="Database" class="xlarge" style="width: 149px;display: none;">
							
						</select>
	            	</label>
	            </div>
	        <!-- label starts -->
			<div class="clearfix serverSettingsDiv perCaptionDisp" id="caption">
						<s:text name="label.retrieving"/>
			</div>	
			<!-- label ends -->

			<div class="clearfix serverSettingsDiv perCaptionDisp" id="caption">
				<table>
					<tr>
						<td style="width: 20%; border-bottom: none;">
							<label for="xlInput" class="xlInput popup-label perTestName" style="width: 208px">
								<span class="red">*</span>
								<s:text name="label.test.rslt.name"/>
							</label>
						</td>
						<td style="width: 20%; border-bottom: none;">
							<input type="text" name="testName" id="testName" maxlength="20" title="20 Characters only" 
								value="" style="width: 160px; position: relative;">
						</td>
						<td style="border-bottom: none;">
							<input type="button" class="btn primary" id="addHeader" value="<s:text name="label.add.header"/>" >
						</td>
					</tr>
				</table>
			</div>				
	        </fieldset>

     <!-- Multiple text box generation starts -->
    <fieldset class="popup-fieldset fieldsetBottom perFieldSet perContextUrlFieldset" id="context">
       		<legend class="fieldSetLegend"><s:text name="label.context.url"/></legend>
			<div id="screenUrlsContainer" class="screenUrlsContainer headerContainer perContextTitle">			
				<div class="tblheader perf_tblhdr">
	               	<table class="zebra-striped" style="height: 25px;">
						<tr>
							<th class="editFeatures_th1 headerNameWidth"><span class="red">*</span> <s:text name="label.name"/></th>
							<th class="editFeatures_th4 headerContextWidth"><span class="red">*</span> <s:text name="label.context"/></th>
							<th class="editFeatures_th4 headerTypeWidth"><s:text name="label.type"/></th>
							<th class="editFeatures_th4"><s:text name="label.encoding"/></th>
						</tr>
					</table>
				</div>
			</div>	
			<div style="width: 100%;">
				<div class="perContentDiv">
					<div class="screenUrlsContainer perScreenUrlContain" id="screenUrls">
					<fieldset class="popup-fieldset perContentFieldsetDiv">
			                 <label for="xlInput" class="xlInput popup-label algnLeft perScreenNameTxt">
			                 	<input type="text" name="name" title="Name" maxlength="20" id="name1" value="" class="screenName">
			                 </label>
			                 
			                 <div class="input padTop" style="margin-left: 133px;">
			                    <input type="text" name="context" title="Context" id="context1" value="" class="screenUrl">&nbsp;&nbsp;
			                    
			                    <select id="contextType1" name="contextType" class="perGetPostField" onChange="showHidePostData(this);">
			                    	<option value="GET"><s:text name="label.get"/></option>
			                    	<option value="POST"><s:text name="label.post"/></option>
			                    </select> &nbsp;&nbsp;
			                    
			                	<select id="encodingType1" name="encodingType" class="perEncodingField">
			                    	<option value="UTF-8"><s:text name="label.utf8"/></option>
			                    	<option value="UTF-16"><s:text name="label.utf16"/></option>
			                    </select> 
			                 </div>
			                 <div id="postData1" style="display:none;">
				                 <label for="xlInput" class="xlInput popup-label algnLeft perScreenNameTxt">
				                 	<span class='red'>* </span><s:text name="label.request.body"/>
				                 </label>
				                 
				                 <div class="input padTop"  style="margin-left: 133px;">
					                <textarea title="json data" class="screenUrl perTxtArea env-desc" id="contextPost1" name="contextPostData" rows="1"></textarea>
				                 </div>
			                 </div>
			         </fieldset>
		             </div>
	             </div>
             
	             <div class="perAddRemoveDiv">
		         	 <a id="addUrl" href="#" class="addUrlColor" style="vertical-align: top;">
						<img class="headerlogoimg perImg" src="images/icons/add_icon.png" alt="add">
		             </a> &nbsp;&nbsp; 
		             <a id="removeUrl" href="#" class="addUrlColor">
		               	<img class="headerlogoimg perImg" src="images/icons/minus_icon.png" alt="remove">
		             </a>
	             </div>
             </div>
             </fieldset>
			<!-- Multiple text box generation ends -->
          	<!-- Database Jmeter starts -->
			<fieldset class="popup-fieldset fieldsetBottom perFieldSet perContextUrlFieldset hideContent" id="database">
	       		<legend class="fieldSetLegend">Database Query</legend>
			
			<div id="dbQueryContainer" class="screenUrlsContainer headerContainer perContextTitle">			
				<div class="tblheader perf_tblhdr">
	               	<table class="zebra-striped" style="height: 25px;">
						<tr>
							<th class="editFeatures_th1 headerNameWidth" ><span class="red">*&nbsp;</span>Name</th>
							<th class="editFeatures_th4 headerContextWidth"><span class="red">*&nbsp;</span>Query Type</th>
							<th class="editFeatures_th4"><span class="red">*&nbsp;</span>Query</th>
						</tr>
					</table>
				</div>
			</div>	
			<div style="width: 100%;" id="dbPerScreen">
				<div class="perContentDiv">
					<div class="screenUrlsContainer perScreenUrlContain" id="dbUrls">
					<fieldset class="popup-fieldset perContentFieldsetDiv">
			                 <label for="xlInput" class="xlInput popup-label algnLeft perScreenNameTxt">
			                 	<input type="textbox" name="dbPerName" title="Name" maxlength="20" id="queryName1" value="" class="screenName">
			                 </label>
			                 
			                 <div class="input padTop" style="margin-left: 133px;">
			                    <!-- <input type="textbox" name="context" id="context1" value="" class="screenUrl" style="width: 150px;"> -->
			                    <select id="queryType" name="queryType" class="perEncodingField" style="width: 150px;">
			                    	<option value="Select Statement">Select Statement</option>
			                    	<option value="Update Statement">Update Statement</option>
			                    </select> 
			                    <textarea id="queryStatement1" name="query" class="xlarge env-desc" style="width: 250px;"></textarea>
			                 </div>
			         </fieldset>
		             </div>
	             </div>
             
	             <div class="perAddRemoveDiv">
		         	 <a id="addDbUrl" href="#" class="addUrlColor" style="vertical-align: top;">
						<img class="headerlogoimg perImg" src="images/icons/add_icon.png" alt="add">
		             </a> &nbsp;&nbsp; 
		             <a id="removeDbUrl" href="#" class="addUrlColor">
		               	<img class="headerlogoimg perImg" src="images/icons/minus_icon.png" alt="remove">
		             </a>
	             </div>
             </div>
			<!-- Database Jmeter ends -->
			
	        </fieldset> 			
		<fieldset class="popup-fieldset perFieldSet perThreadGroupAlgn">
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
		
	<!-- Hidden Fields -->
	<input type="hidden" name="requestHeaders">
</form>
</div>

<script type="text/javascript">
    var params = null;
	var resultFileLists = null;
	var autoCompleteFileNames = new Array();
	var counter = 2;
	$(document).ready(function() {
		$("input[name='context']").die('input propertychange');
		
		loadGetCaption();
		loadTestFiles();
		
		$("#testName").autocomplete({
			select:function(event,ui){
				var testName = ui.item.value;
				var jmeterTestAgainst = $('input:radio[name=jmeterTestAgainst]:checked').val();
				var params = "projectCode=" + '<%= projectCode %>';
				params = params.concat("&testName=" + testName);
				params = params.concat("&jmeterTestAgainst=" + jmeterTestAgainst);
				performAction('getPerfTestJSONData', params, '', true);
			}
		});  
		
		$(".wel_come").show().css("display","block");
		
		$('#close, #cancel').click(function() {
			showParentPage();
			<%-- changeTesting('<%= testType %>');  --%>
		});
		
		// made the radio button selected
		<% 
			if (jmeterTestAgainst != null) {
		%>
			var selectJmeterTest = '<%= jmeterTestAgainst%>';
			$('input:radio[name=jmeterTestAgainst]').filter("[value='"+ selectJmeterTest +"']").attr("checked","checked");
		<% 
			}
		%>
		
		$('#environments').change(function() {
// 			getConfigNames();
			loadGetCaption();
		});
		
		$('input:radio[name=jmeterTestAgainst]').change(function() {
			if ($(this).val() == "Database") {
				$("#addHeader").hide();
			} else {
				$("#addHeader").show();
			}
			loadGetCaption();
			loadTestFiles();
			$("#testName").val('');
			clearTxtPerfPopup();
			defaulturl();
			defaultdburl();
		});
		
		$("#Server").change(function() {
			getCaption('server', $('#Server').val());
		});
		
		$("#WebService").change(function() {
			getCaption('webservices', $('#WebService').val());
		});
		
		$("#Database").change(function() {
			getCaption('database', $('#Database').val());
		});
		
		 $("#addUrl").click(function () {
			if(counter > 25){
		          return false;
			}
		 	
		 	var nameId = "name" + counter;
		 	var contextId = "context" + counter;
		 	var contextPostId = "contextPost" + counter;
		 	var contextType = "contextType" + counter;
		 	var postDivId = "postData" + counter;
		 	var encodingId = "encodingType"+ counter;
		 	
		 	var newTextBoxDiv = $(document.createElement('div')).attr("id", 'urlGroup' + counter);
			newTextBoxDiv.html("<fieldset class='popup-fieldset perContentFieldsetDiv'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'><input type='textbox' name='name' id='" + nameId + "' value='' class='screenName' title='Name' maxlength='20'>" +
					"</label><div class='input padTop' style='margin-left: 133px;'><input type='textbox' name='context' id='" + contextId + "' value='' class='screenUrl' title='Context'>&nbsp;&nbsp;&nbsp;<select id='" + contextType + "' name='contextType' class='perGetPostField' onChange='showHidePostData(this);'><option value='GET'>GET</option><option value='POST'>POST</option>"+
					"</select>&nbsp;&nbsp;&nbsp;&nbsp;<select id='" + encodingId + "' name='encodingType' class='perEncodingField'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select> </div><div id='" + postDivId + "' style='display:none;'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'><span class='red'>*</span> Request Body</label> <div class='input padTop'  style='margin-left: 133px;'><textarea title='json data' class='screenUrl perTxtArea env-desc' id='" + contextPostId + "' name='contextPostData' rows='1'></textarea></div></div></fieldset>");
			// root element
			newTextBoxDiv.appendTo("#screenUrls");
			counter++;
		});
		$("#addDbUrl").click(function () {
			if(counter > 25){
		          return false;
			}
		     
			var qName = "queryName"  + counter;
			var qType = "queryType" +counter;                 
			var qStmt = "queryStatement" +counter;
		 	var newDBDiv = $(document.createElement('div')).attr("id", 'urlGroup' + counter);
		 	newDBDiv.html("<fieldset class='popup-fieldset perContentFieldsetDiv'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'><input type='textbox' name='dbPerName' id='" + qName + "' value='' class='screenName'title='Name' maxlength='20'>" +
					"</label><div class='input padTop' style='margin-left: 133px;'><select style='width: 150px;' id='" + qType + "' name='queryType' class='perEncodingField' onChange='showHidePostData(this);'><option value='Select Statement'>Select Statement</option><option value='Update Statement'>Update Statement</option>"+
					"</select>&nbsp;<textarea style='width: 250px;' class='xlarge env-desc' id='" + qStmt + "' name='query' rows='1'></textarea></div></fieldset>"); 
			// root element
			newDBDiv.appendTo("#dbUrls");
			counter++;
		});
		 
		$("#removeUrl").click(function () {
			if(counter == 2){
		          return false;
		    }   
			counter--;
	        $("#urlGroup" + counter).remove();
		 
		 });
		
		$("#removeDbUrl").click(function () {
			if(counter == 2){
		          return false;
		    }   
			counter--;
	        $("#urlGroup" + counter).remove();
		 
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
	    
	    $('#actionBtn').click(function() {
			checkForConfigType($('input:radio[name=jmeterTestAgainst]:checked').val());
		});
	    
	    $("#testName").bind('input propertychange',function(e) { 	//testName validation
	     	var name = $(this).val();
	     	name = isContainSpace(name);
	     	$(this).val(name);
	      });
	    
	    $("#noOfUsers, #rampUpPeriod, #loopCount").bind('input propertychange',function(e) { 	//testName validation
	     	var name = $(this).val();
	     	name = isContainSpace(name);
	     	$(this).val(name);
		});
	    
	    $("#addHeader").click(function() {
	    	$("#headerPopup").show();
	    	$("#performance-popup").hide();
	    	$("input[name=headerName]").val("");
	    	$("input[name=headerValue]").val("");
	    });
	    
	    $('#headerPopupCancelClose, #headerPopupCancel').click(function() {
	    	$("#headerPopup").hide();
	    	$("#performance-popup").show();
		});
	    
	    $("#addHeaderActionBtn").click(function() {
	    	addRequestHeader();
	    });
	});
	
	function addRequestHeader() {
		var headerName = $("input[name=headerName]").val();
		var headerValue = $("input[name=headerValue]").val();
		if (isBlank(headerName)) {
			showErrorMsg('errMsgHeader', "Enter Header name");
			$("input[name=headerName]").focus();
		} else if (isBlank(headerValue)) {
			showErrorMsg('errMsgHeader', "Enter Header value");
			$("input[name=headerValue]").focus();
		} else {
			var existingHeaders = "";
			if ($("input[name=requestHeaders]").val() != undefined) {
				existingHeaders = $("input[name=requestHeaders]").val();
			}
			var existingHeaders = $("input[name=requestHeaders]").val();
			var header = existingHeaders + headerName + "#VSEP#" + headerValue + "#SEP#";
			$("input[name=requestHeaders]").val(header);
			$("#headerPopup").hide();
	    	$("#performance-popup").show();
		}
	}
	
	function defaulturl() {
		
		$("#screenUrls").empty();
		counter = 1;
		var nameId = "name" + counter;
	 	var contextId = "context" + counter;
	 	var contextPostId = "contextPost" + counter;
	 	var contextType = "contextType" + counter;
	 	var postDivId = "postData" + counter;
	 	var encodingId = "encodingType"+ counter;
	 	
	 	var newTextBoxDiv = $(document.createElement('div')).attr("id", 'urlGroup' + counter);
		newTextBoxDiv.html("<fieldset class='popup-fieldset perContentFieldsetDiv'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'><input type='textbox' name='name' id='" + nameId + "' value='' class='screenName' title='Name' maxlength='20'>" +
				"</label><div class='input padTop' style='margin-left: 133px;'><input type='textbox' name='context' id='" + contextId + "' value='' class='screenUrl' title='Context'>&nbsp;&nbsp;&nbsp;<select id='" + contextType + "' name='contextType' class='perGetPostField' onChange='showHidePostData(this);'><option value='GET'>GET</option><option value='POST'>POST</option>"+
				"</select>&nbsp;&nbsp;&nbsp;&nbsp;<select id='" + encodingId + "' name='encodingType' class='perEncodingField'><option value='UTF-8'>UTF-8</option><option value='UTF-16'>UTF-16</option></select> </div><div id='" + postDivId + "' style='display:none;'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'><span class='red'>*</span> Request Body</label> <div class='input padTop'  style='margin-left: 133px;'><textarea title='json data' class='screenUrl perTxtArea env-desc' id='" + contextPostId + "' name='contextPostData' rows='1'></textarea></div></div></fieldset>");
		// root element
		newTextBoxDiv.appendTo("#screenUrls");
		counter++;
		
	} 
	
	function defaultdburl() {
		
		$("#dbUrls").empty();
		counter = 1;
		var qName = "queryName"  + counter;
		var qType = "queryType" +counter;                 
		var qStmt = "queryStatement" +counter;
	 	var newDBDiv = $(document.createElement('div')).attr("id", 'urlGroup' + counter);
	 	newDBDiv.html("<fieldset class='popup-fieldset perContentFieldsetDiv'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'><input type='textbox' name='dbPerName' id='" + qName + "' value='' class='screenName'title='Name' maxlength='20'>" +
				"</label><div class='input padTop' style='margin-left: 133px;'><select style='width: 150px;' id='" + qType + "' name='queryType' class='perEncodingField' onChange='showHidePostData(this);'><option value='Select Statement'>Select Statement</option><option value='Update Statement'>Update Statement</option>"+
				"</select>&nbsp;<textarea style='width: 250px;' class='xlarge env-desc' id='" + qStmt + "' name='query' rows='1'></textarea></div></fieldset>"); 
		// root element
		newDBDiv.appendTo("#dbUrls");
		counter++;
	}
	
	
	function successEnvValidation(data) {
		if(data.hasError == true) {
			showError(data);
		} else {
			var validator = true;
		    if($('#testName').val() == null || $('#testName').val().trim() == "") {
		        showErrorMsg('errMsg', "Enter Test Result Name");
			    $("#testName").val("");
			   	$("#testName").focus();
				return false;
			}
			 
			/* if(resultFileLists != null) {
					var found = false;
					for (var i=0; i < resultFileLists.length ; i++) {
						var filename = $('#testName').val()+'.xml';
						  if (resultFileLists[i] == $('#testName').val()+'.xml') {
						    found = true;
						    break;
						  }
					}
					if(found) {
						validator = false;
						showErrorMsg('errMsg', "Test Name Already Exists.");
						return false;
					}
			   }  */
				if($('input:radio[name=jmeterTestAgainst]:checked').val() != "Database") {
					for(i = 1; i < counter; i++) {
							if(($('#name'+ i).val() == null || $('#name'+ i).val() == "")) {
								showErrorMsg('errMsg', "Enter Name");
								$('#name'+ i).focus();
								return false;
							}
							if (($('#context'+ i).val() == null || $('#context'+ i).val() == "")) {
								showErrorMsg('errMsg', "Enter Context");
								$('#context'+ i).focus();
								return false;
							}
			
							
				    		if ($('#contextType' + i).val() == "POST") {
				    			var contextTypeId = $('#contextType' + i).attr('id');
				    			var lastChar = contextTypeId[contextTypeId.length-1];
				    			var postDiv = "postData" + lastChar;
				    			if (isBlank($('#contextPost'+lastChar).val())) {
				    				showErrorMsg('errMsg', "Enter Request Body");
				    				$('#contextPost'+lastChar).focus();
				                    isEmptyRequestBody = true;
				    			   	return false;
				    	}
	    			}

              	}
			}
	         if($('input:radio[name=jmeterTestAgainst]:checked').val() == "Database") {
					   for(i = 1; i < counter; i++) {
					     if(($('#queryName'+ i).val() == undefined || $('#queryName'+ i).val() == "")) {
							showErrorMsg('errMsg', "Enter Query Name");
							$('#queryName'+ i).focus();
							return false;
						}  
						 if (($('#queryStatement'+ i).val() == undefined || $('#queryStatement'+ i).val() == "")) {
							showErrorMsg('errMsg', "Enter Query");
							$('#queryStatement'+ i).focus();
							return false;
						} 
					}
			}      
			
			// below method checks whther the select box is empty before submitting the form
			if($('#noOfUsers').val() == null || $('#noOfUsers').val() == "") {
				showErrorMsg('errMsg', "Enter No of Users");
				$('#noOfUsers').focus();
				return false;
			}
			
			if($('#rampUpPeriod').val() == null || $('#rampUpPeriod').val() == "") {
				showErrorMsg('errMsg', "Enter Ramp-up Period");
				$('#rampUpPeriod').focus();
				return false;
			}
			
			if($('#loopCount').val() == null || $('#loopCount').val() == "") {
				showErrorMsg('errMsg', "Enter Loop Count");
				$('#loopCount').focus();
				return false;
			}
			
			if (validator) {
				$('.popup_div_per').show().css("display","none");
				$("#build-output").empty();
				$('#performance-popup').show().css("display","none");
				disableScreen();
				$('#build-outputOuter').show().css("display","block");
				var param = '<%= FrameworkConstants.ENVIRONMENTS %>' + "=";
				param = param.concat(getSelectedEnvs());
				$('#Server').removeAttr("disabled");
				readerHandlerSubmit('perTest', '<%= projectCode %>', '<%= FrameworkConstants.PERFORMACE %>', param);
			}
		}
	}
	
	function getCaption(settingType, settingName) {
		var envs = getSelectedEnvs();
		$.ajax({
			url : 'getCaption',
			data : {
				'settingType' : settingType,
				'settingName' : settingName,
				'projectCode' : '<%= projectCode %>',
				'environments' : envs,
			},
			type : "POST",
			success : function(data) {
				if(settingType == 'server') {
					$("#caption").empty();
					$("#caption").html("Server URL " + data.caption);
				} 
				if(settingType == 'webservices') {
					$("#caption").empty();
					$("#caption").html("Webservices URL " + data.caption);
				}
				if(settingType == 'database') {
					$("#caption").empty();
					$("#caption").html("Database URL " + data.caption);
				}
			}
		});
	}
	
	function emptyAndReplaceWith(elemId, data) {
		$('#'+ elemId).empty();
		$('#'+ elemId).html(data);
	}
	
	function showHideDiv(showId, hideId) {
		$('#'+ showId).show();
		$('#'+ hideId).hide();
	}
	
	function emptyCaption() {
		$("#caption").empty();
		$("#caption").html("No URL found");
	}
	
	function showErrorMsg(elementId, msg) {
		$('#'+elementId).empty();
		$('#'+elementId).html(msg);
	}
	
	function loadGetCaption() {//$('input:radio[name=jmeterTestAgainst]')
		if($('input:radio[name=jmeterTestAgainst]:checked').val() == "Server") {
			getConfigNames();
			emptyAndReplaceWith('selectedTypeTitle', '<font color="red">* </font>Server');
			showHideDiv('Server', 'WebService');
            showHideDiv('Server', 'Database');
			showHideDiv('context', 'database');
			showHideDiv('screenUrlsContainer', 'dbQueryContainer' );
			showHideDiv('perScreenUrl', 'dbPerScreen');
			showHideDiv('screenUrls', 'dbUrls');
			showHideDiv('addUrl', 'addDbUrl');
			showHideDiv('removeUrl', 'removeDbUrl');
			if(!isBlank($('#Server').val())) {
				getCaption('server', $('#Server').val());
			} else {
				emptyCaption();
			}
		} else if($('input:radio[name=jmeterTestAgainst]:checked').val() == "WebService") {
			getConfigNames();
			emptyAndReplaceWith('selectedTypeTitle', '<font color="red">*</font>Webservice');
			showHideDiv('WebService', 'Server');
            showHideDiv('WebService', 'Database');
			showHideDiv('context', 'database');
			showHideDiv('screenUrlsContainer', 'dbQueryContainer' );
			showHideDiv('perScreenUrl', 'dbPerScreen');
			showHideDiv('screenUrls', 'dbUrls');
			showHideDiv('addUrl', 'addDbUrl');
			showHideDiv('removeUrl', 'removeDbUrl');

			if(!isBlank($('#WebService').val())) {
				getCaption('webservices', $('#WebService').val());	
			} else {
				emptyCaption();
			  }
			 } else if($('input:radio[name=jmeterTestAgainst]:checked').val() == "Database") {
			getConfigNames();
			emptyAndReplaceWith('selectedTypeTitle', '<font color="red">* </font>Databse');
			showHideDiv('Database', 'Server');
			showHideDiv('Database', 'WebService');
			showHideDiv('database', 'context');
			showHideDiv('dbQueryContainer', 'screenUrlsContainer'); 
			showHideDiv('dbPerScreen', 'perScreenUrl');
			showHideDiv('dbUrls', 'screenUrls');
			showHideDiv('addDbUrl', 'addUrl');
			showHideDiv('removeDbUrl', 'removeUrl'); 
		   if(!isBlank($('#Database').val())) {	
            getCaption('database', $('#Database').val());	
           } else {
			emptyCaption();
		}
	  }
	}
	
	function loadTestFiles() {
		var settingType = $('input:radio[name=jmeterTestAgainst]:checked').val();
        var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	params = params.concat("settingType=");
		params = params.concat(settingType);
    	performAction('tstResultFiles', params, '', true);
    }
	
	function successLoadTestFiles(data) {
		resultFileLists = data;
		var name = null;
		autoCompleteFileNames = [];
		for(var i = 0; i < data.length; i++){
 			name = data[i].slice(0,-4);
			autoCompleteFileNames.push(name);
		}
		
		$("#testName").autocomplete({
			source: autoCompleteFileNames
		}); 
		
	}
	
	function fillJSONData(data) {
		
		var jmeter = $('input:radio[name=jmeterTestAgainst]:checked').val();
		var name = data.performanceDetails.name;
		var context = data.performanceDetails.context;
	    var type = data.performanceDetails.contextType;
	    var encoding = data.performanceDetails.encodingType;
	    var contextdata = data.performanceDetails.contextPostData;
	    var noOfUsers = data.performanceDetails.noOfUsers;
	    var rampUpPeriod = data.performanceDetails.rampUpPeriod;
		var loopCount = data.performanceDetails.loopCount;
		var dbName = data.performanceDetails.dbPerName;
		var queryType = data.performanceDetails.queryType;
		var query = data.performanceDetails.query;
		$("#noOfUsers").val(noOfUsers);
		$("#rampUpPeriod").val(rampUpPeriod);
		$("#loopCount").val(loopCount);
		counter = 1;
		if(jmeter == "Server" || jmeter == "WebService"){
			$("#screenUrls").empty();
			for(i = 0 ;i < name.length;i++) {
		     	addContextUrl(name[i],context[i],type[i],encoding[i],contextdata[i]);
			}
		} else {
			$("#dbUrls").empty();
			for(i = 0 ;i < dbName.length;i++) {
				adddbContextUrl(dbName[i],queryType[i],query[i]);
			}
		}
	}
	
	function addContextUrl(name, context, contextType, encodingType, contextPostData) {
		var nameId = "name" + counter;
	 	var contextId = "context" + counter;
	 	var contextPostId = "contextPost" + counter;
	 	var contextTypeId = "contextType" + counter;
	 	var postDivId = "postData" + counter;
	 	var encodingId = "encodingType"+ counter;
	 	var postDivDisp = "none";
	 	var getselectOption = "";
	 	var postselectOption = "";
	 	var utf8option = "";
	 	var utf16option = "";
	 	if(contextType == "GET") {
	 		postselectOption = "";
	 		getselectOption = "selected";
			postDivDisp = "none";
		} else {
			getselectOption = "";
			postselectOption = "selected";
			postDivDisp = "block";
		}
	 	if(contextType == "GET") {
	 		utf8option = "selected";
	 		utf16option = "";
		} else {
			utf8option = "";
			utf16option = "selected";
		}
	 	
	 	var newTextBoxDiv = $(document.createElement('div')).attr("id", 'urlGroup' + counter);
	 	newTextBoxDiv.html("<fieldset class='popup-fieldset perContentFieldsetDiv'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'><input type='textbox' name='name' id='" + nameId + "' value='" + name + "' class='screenName'>" +
				"</label><div class='input padTop' style='margin-left: 133px;'><input type='textbox' name='context' id='" + contextId + "' value='"+ context +"' class='screenUrl'>&nbsp;&nbsp;&nbsp;<select id='" + contextTypeId + "' name='contextType' class='perGetPostField' onChange='showHidePostData(this);'><option value='GET' "+ getselectOption +">GET</option><option value='POST' "+ postselectOption +">POST</option>"+
				"</select>&nbsp;&nbsp;&nbsp;&nbsp;<select id='" + encodingId + "' name='encodingType' class='perEncodingField'><option value='UTF-8' "+ utf8option +">UTF-8</option><option value='UTF-16' "+ utf16option +">UTF-16</option></select> </div><div id='" + postDivId + "' style='display:"+ postDivDisp +"'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'>Request Body</label><div class='input padTop'  style='margin-left: 133px;'><textarea title='json data' class='screenUrl perTxtArea' id='" + contextPostId + "' name='contextPostData' rows='1'>"+ contextPostData +"</textarea></div></div></fieldset>");
		// root element
		newTextBoxDiv.appendTo("#screenUrls");
		counter++;
		
	}
	
	function adddbContextUrl(dbName,queryType,query){
		
		var selectoption = ""; 
		var updateoption = "";
		
		if(queryType == "Select Statement"){
			selectoption = "selected";
		} else {
			updateoption = "selected";
		}
		
		var qName = "queryName"  + counter;
		var qType = "queryType" +counter;                 
		var qStmt = "queryStatement" +counter;
	 	var newDBDiv = $(document.createElement('div')).attr("id", 'urlGroup' + counter);
	 	newDBDiv.html("<fieldset class='popup-fieldset perContentFieldsetDiv'><label for='xlInput' class='xlInput popup-label algnLeft perScreenNameTxt'><input type='textbox' name='dbPerName' id='" + qName + "' value='"+ dbName +"' class='screenName'title='Name' maxlength='20'>" +
				"</label><div class='input padTop' style='margin-left: 133px;'><select style='width: 150px;' id='" + qType + "' name='queryType' class='perEncodingField' onChange='showHidePostData(this);'><option value='Select Statement' " + selectoption + " >Select Statement</option><option value='Update Statement' " + updateoption + ">Update Statement</option>"+
				"</select>&nbsp;<textarea style='width: 250px;' class='xlarge env-desc' id='" + qStmt + "' name='query' rows='1'>"+ query +"</textarea></div></fieldset>"); 
		// root element
		newDBDiv.appendTo("#dbUrls");
		counter++;
		
	} 
	
	function clearTxtPerfPopup(){
		$('#errMsg').empty();
		$('#noOfUsers').val('');
		$('#rampUpPeriod').val('');
		$('#loopCount').val('');
	}
	
	function showHidePostData(obj) {
		var contextTypeValue = obj.options[obj.selectedIndex].value;  ;
		var contextTypeId = obj.id;
		var lastChar = contextTypeId[contextTypeId.length-1];
		var postDiv = "postData" + lastChar;
		if(contextTypeValue == "GET") {
			$('#'+postDiv).css("display", "none");
		} else if(contextTypeValue == "POST") {
			$('#'+postDiv).css("display", "block");
		}
	}
	
</script>