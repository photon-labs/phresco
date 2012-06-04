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

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.util.List" %>

<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.framework.api.ValidationResult" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<style>
	.zebra-striped tbody tr:hover td {
	    background-color: transparent;
	}
</style>

<%
	List<ValidationResult> validationResults = null;
	String validateFrom =(String)request.getAttribute(FrameworkConstants.VALIDATE_FROM);
	String header = null;
	String projectCode = "";
	if (StringUtils.isNotEmpty(validateFrom)) {
		if (FrameworkConstants.VALIDATE_PROJECT.equals(validateFrom)) {
			header = "Project Validation Report";
			projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
			validationResults = (List<ValidationResult>) session.getAttribute(projectCode + FrameworkConstants.SESSION_PRJT_VLDT_RSLT);
		} else if (FrameworkConstants.VALIDATE_FRAMEWORK.equals(validateFrom)) {
			header = "Framework Validation Report";
			validationResults = (List<ValidationResult>) session.getAttribute(FrameworkConstants.SESSION_FRMK_VLDT_RSLT);
		}
	}
%>

<div class="popup_Modal validate_Modal" id="validatePopup">
	<div class="modal-header">
		<h3>
			<%= header %>
		</h3>
		<a class="close" href="#" id="close">&times;</a>
	</div>

	<div class="modal-body validate_Modal_body">
		<%
			if (CollectionUtils.isNotEmpty(validationResults)) {
		%>
			<div class="fixed-table-container">
	      		<div class="header-background"> </div>
	      		<div class="fixed-table-container-inner validatePopup_tbl">
			        <table cellspacing="0" class="zebra-striped">
			          	<thead>
				            <tr>
								<th class="first validate_tblHdr">
				                	<div class="th-inner"><s:text name="label.message"/></div>
				              	</th>
				              	<th class="second validate_tblHdr">
				                	<div class="th-inner"><s:text name="label.status"/></div>
				              	</th>
				            </tr>
			          	</thead>
			
			          	<tbody>
			          		<% for(ValidationResult validationResult : validationResults) { %>
			            	<tr>
			              		<td>
			              		<div class = "validateMsg" style="color: #000000;">
			              			<%= validationResult.getMessage() %>
			              			</div>
			              		</td>
			              		<td>
			              		<div class = "validateStatus" style="color: #000000;">
			              			<% if (validationResult.getStatus().toString() == "ERROR") { %>
					   					<img src="images/icons/wrong_icon.png">
					   				<% } else { %>
					   					<img src="images/icons/tick_icon.png">
					   				<% } %>
					   				</div>
			              		</td>
			            	</tr>
			            <%
							}
						%>	
			          	</tbody>
			        </table>
	      		</div>
    		</div>
	    <% } else { %>
	    		<div class="alert-message block-message warning" >
					<center><label Class="errorMsgLabel"><s:text name="label.validate.sucess"/></label></center>
				</div>
	    <% } %>
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action">
			<div style="float: left;">
				<img src="" class="popupLoadingIcon" style="display: block;">
				<!-- <span style="display: block;text-align: right; margin-top: -26px; margin-left: 37px;">Progressing... </span> -->
	    	</div>
			<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="validateCancel">
			<% if (StringUtils.isNotEmpty(validateFrom)) { %>
				<% if (validateFrom.equals(FrameworkConstants.VALIDATE_FRAMEWORK)) { %>
					<input type="button" class="btn primary" value="<s:text name="label.validate"/>" id="validate" onclick="validateFramework();">
				<% } else if (validateFrom.equals(FrameworkConstants.VALIDATE_PROJECT)) {%>
					<input type="button" class="btn primary" value="<s:text name="label.validate"/>" id="validate" onclick="validateCurrentProject();">
			<%
				   }
			   }
			%>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		escPopup();
		$('#validateCancel, #close').click(function() {
			$("#popup_div").hide();
			enableScreen();
			<%
				String validationStatus = "";
				if (FrameworkConstants.VALIDATE_PROJECT.equals(validateFrom)) {
					validationStatus = (String) session.getAttribute(projectCode + FrameworkConstants.SESSION_PRJT_VLDT_STATUS);
				} else if (FrameworkConstants.VALIDATE_FRAMEWORK.equals(validateFrom)) {
					validationStatus = (String) session.getAttribute(FrameworkConstants.SESSION_FRMK_VLDT_STATUS);
				}
			%>
			
			if('<%= validationStatus %>' == "ERROR") {
				$("#validationSuccess_validateProject").hide();
				$("#validationErr_validateProject").show();
			}
			else {
				$("#validationErr_validateProject").hide();
				$("#validationSuccess_validateProject").show();
			}
		});
	});
	
	/* Validate framework */
	function validateFramework() {
		$("#popup_div").empty();
		disableControl($('#validateCancel'), 'btn disabled');
		$(".popupLoadingIcon").css("display", "block");
		getCurrentCSS();
    
    	disableScreen();
		popup('validateFramework', '', $('#popup_div'));
    }
	
	/* Validate Project */
	function validateCurrentProject() {
		$("#popup_div").empty();
		disableControl($('#validateCancel'), 'btn disabled');
		$(".popupLoadingIcon").css("display", "block");
		getCurrentCSS();
		popup('validateProject', '', $('#popup_div'));
    }
	
</script>