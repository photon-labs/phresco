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

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.framework.api.ValidationResult" %>

<%@ include file="../userInfoDetails.jsp" %>
<%@ include file="errorReport.jsp" %>

<script type="text/javascript" src="js/delete.js" ></script>
<script type="text/javascript" src="js/confirm-dialog.js" ></script>
<script type="text/javascript" src="js/loading.js"></script>
<script type="text/javascript" src="js/home-header.js" ></script>
<script type="text/javascript" src="js/reader.js" ></script>
	<!-- Window Resizer -->
<script type="text/javascript" src="js/windowResizer.js"></script>
<style type="text/css">
	.btn.success, .alert-message.success {
       	margin-top: -35px;
   	}
   	
   	table th {
		padding: 0 0 0 9px;  
	}
	   	
	td {
	 	padding: 5px;
	 	text-align: left;
	}
	  
	th {
	 	padding: 0 5px;
	 	text-align: left;
	}
</style>

<div class="page-header">
	<h1 style="float: left;">
		Applications 
	</h1>
	
	<div class="icon_div">
		<a href="#" onclick="showFrameworkValidationResult();" title="Validate framework">
			<img src="images/icons/validate_failure_icon.png" id="validationErr_validateFramework" style="display: none;">
		</a>
		<a href="#" onclick="showFrameworkValidationResult();" title="Validate framework">
			<img src="images/icons/validate_success_icon.png" id="validationSuccess_validateFramework" style="display: none;">
		</a>
	</div>
</div>

<form name="delete" action="delete" method="post" autocomplete="off" id="deleteObjects" class="app_list_form">
	<div class="operation">
		<input id="add" type="button" value="<s:text name="label.addappln"/>" class="btn primary"/>
		<!-- <a href="#" class="btn primary" id="discover">Discover</a> -->
		<a href="#" class="btn primary" id="import"><s:text name="label.import.from.svn"/></a>
		<input id="deleteButton" type="button" value="<s:text name="label.delete"/>" class="btn disabled" disabled="disabled"/>
	</div>
	
	<div class="table_div">
		<%
			List<Project> projects = (List<Project>)request.getAttribute("Projects");
		%>
		
		<s:if test="hasActionMessages()">
			<div class="alert-message success"  id="successmsg">
				<s:actionmessage />
			</div>
		</s:if>
		
		<%
			if(projects == null || projects.size()== 0) {
		%>
			<div class="alert-message block-message warning" >
				<center><s:label key="error.message" cssClass="errorMsgLabel"/></center>
			</div>
		<%
			} else {
		%>
			<div class="fixed-table-container">
	      		<div class="header-background"> </div>
	      		<div class="fixed-table-container-inner">
			        <table cellspacing="0" class="zebra-striped">
			          	<thead>
				            <tr>
								<th class="first">
				                	<div class="th-inner">
				                		<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto">
				                	</div>
				              	</th>
				              	<th class="second">
				                	<div class="th-inner"><s:text name="label.name"/></div>
				              	</th>
				              	<th class="third">
				                	<div class="th-inner"><s:text name="label.description"/></div>
				              	</th>
				              	<th class="third">
				                	<div class="th-inner"><s:text name="label.technology"/></div>
				              	</th>
<!-- 				              	<th class="third"> -->
<%-- 				                	<div class="th-inner"><s:text name="label.print"/></div> --%>
<!-- 				              	</th> -->
				            </tr>
			          	</thead>
			
			          	<tbody>
			          	<%
							for (Project project : projects) {
								ProjectInfo projectInfo = project.getProjectInfo();
						%>
			            	<tr>
			              		<td class="checkbox_list">
			              			<input type="checkbox" class="check" name="selectedProjects" value="<%= projectInfo.getCode() %>">
			              		</td>
			              		<td>
			              			<a href="#" name="edit" id="<%= projectInfo.getCode() %>" ><%= projectInfo.getName() %></a>
			              		</td>
			              		<td style="width: 40%;"><%= projectInfo.getDescription() %></td>
			              		<td><%= projectInfo.getTechnology().getName() %></td>
<!-- 			              		<td> -->
<%-- 			              			<a href="#" id="pdfPopup"><img id="<%= projectInfo.getCode() %>" class="pdfCreation" src="images/icons/print_icon.png" title="generate pdf" style="height: 20px; width: 20px;"/></a> --%>
<!-- 			              		</td> -->
			            	</tr>
			            <%
							}
						%>	
			          	</tbody>
			        </table>
	      		</div>
    		</div>
		<%
			}
		%>
	</div>
</form>

<!--  Repo dialog Start -->
<!-- <div class="popup_div" id="importFromSvn"></div>  -->
<!--  Repo dialog Start -->

<script type="text/javascript">
	/* To check whether the device is ipad or not */
	if(!isiPad()){
		/* JQuery scroll bar */
		$(".fixed-table-container-inner").scrollbars();  
	}

	$(document).ready(function() {
		if(refreshIntervalId != undefined) {
			clearInterval(refreshIntervalId);			
		}
		enableScreen(); // for some popups like svn_import and CI , it should stay till the completion of the process
		
		<% 
			if (session.getAttribute(FrameworkConstants.SESSION_FRMK_VLDT_STATUS) != null) {
				String frameworkValidationStatus = (String)session.getAttribute(FrameworkConstants.SESSION_FRMK_VLDT_STATUS);
				if (frameworkValidationStatus == "ERROR") {
		%>
					$("#validationErr_validateFramework").show();
        <%		} else { %>
        			$("#validationSuccess_validateFramework").css("display", "block");
       	<% 		} 
			}
       	%>
		
		$("#applications").attr("class", "active");
		$("#home").attr("class", "inactive");
		
		$('#deleteButton').click(function() {
			$("#confirmationText").html("Do you want to delete the selected project(s)");
		    dialog('block');
		    escBlockPopup();
		});
	
		$('#import').click(function() {
			importFromSvn();
		});
		
		$('#add').click(function() {
			disableScreen();
			showLoadingIcon($("#loadingIconDiv"));
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
	        performAction('applicationDetails', params, $('#container'));
	    });
		
		$('#discover').click(function() {
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			showLoadingIcon($("#container")); // Loading Icon
			performAction('applicationDetails', params, $('#container'));
	    });
		
		$('form').submit(function() {
            showProgessBar("Deleting Project (s)", 100);
            var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			performAction('delete', params, $('#container'));
	        return false;
	    });
		
		$("a[name='edit']").click(function() {
			disableScreen();
			showLoadingIcon($("#loadingIconDiv"));
	        var projectCode = $(this).attr("id");
	        var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			params = params.concat("projectCode=");
			params = params.concat(projectCode);
			params = params.concat("&fromPage=");
			params = params.concat("edit");
	        performAction('applicationDetails', params, $('#container'));
	    });
		
        $('.pdfCreation').click(function() {
    		showPopup();
    		$('#popup_div').empty();
    		var params = "";
    		params = params.concat("projectCode=");
			params = params.concat($(this).attr("id"));
    		popup('printAsPdfPopup', params, $('#popup_div'));
    	    escPopup();
	    });
	});
	
	function importFromSvn() {
		showPopup();
		$('#popup_div').empty();
		popup('importFromSvn', '', $('#popup_div'));
	    escPopup();
	}
	
	/* To show the validation result */
	function showFrameworkValidationResult() {
		showPopup();
		$('#popup_div').empty();
		popup('showFrameworkValidationResult', '', $('#popup_div'));
	}
</script>