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

<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.framework.commons.PBXNativeTarget"%>

<script src="js/reader.js" ></script>

<%
   	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
	String technology = (String)project.getProjectInfo().getTechnology().getId();
	
	//xcode targets
   	List<PBXNativeTarget> xcodeConfigs = (List<PBXNativeTarget>) request.getAttribute(FrameworkConstants.REQ_XCODE_CONFIGS);
%>
<form action="code" method="post" autocomplete="off" class="build_form" id="generateBuildForm">
<div class="popup_Modal">
	<div class="modal-header">
		<h3 id="generateBuildTitle">
				<s:text name="label.validation"/>
		</h3>
		<a class="close" href="#" id="close">&times;</a>
	</div>

	<div class="modal-body">
		
		<% if (TechnologyTypes.IPHONES.contains(technology)) { %>

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
		<% }  else { %>
		
		<fieldset class="popup-fieldset">
			<legend class="fieldSetLegend" style="font-weight:normal"><s:text name="label.select.validate.against"/></legend>

			<!-- Show Settings -->
			<div class="clearfix">
				<div class="xlInput">
					<ul class="inputs-list">
						<li class="popup-li"> 
							<input type="radio" name="validateAgainst" value="source" checked>
							<span class="textarea_span popup-span"><s:text name="label.validateAgainst.source"/></span>
							<input id="funTestRadio" type="radio" name="validateAgainst" value="functional" >
							<span  id="funTestText" class="textarea_span popup-span"><s:text name="label.validateAgainst.functionalTest"/></span>
						</li>
					</ul>
					<ul id="skipTestUl" class="inputs-list" style="text-align: center;" >
						<li class="popup-li"> 
							<input type="checkbox" id="skipTest" name="skipTest" value="true" />
							<span class="textarea_span popup-span"><s:text name="label.skip.unit.test"/></span>
						</li>
					</ul>
				</div>	
			</div>


			<% if (TechnologyTypes.HTML5_WIDGET.equals(technology) || TechnologyTypes.HTML5_MOBILE_WIDGET.equals(technology) 
				|| TechnologyTypes.HTML5.equals(technology) || TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET.equals(technology) 
				|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(technology) || TechnologyTypes.JAVA_WEBSERVICE.equals(technology)) { %>	

				<div class="clearfix" id="techDiv">
					<div class="xlInput">
						<ul class="inputs-list">
							<li class="popup-li"> 
								<span class="popup-span"><s:text name="label.technology"/></span>
								<select class="xlarge" id="codeTechnology" name="codeTechnology">
									<option value="java" ><s:text name="label.tech.java"/></option>
									<option value="js" ><s:text name="label.tech.javascript"/></option>
									<option value="web" ><s:text name="label.tech.jsp"/></option>
								</select>
							</li>
						</ul>
					</div>	
				</div>

			<% } %>

		</fieldset>
		<% } %>

	</div>
	
	<div class="modal-footer">
		<div class="action popup-action">
		<div id="errMsg"></div>
			<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
			<input type="button" id="codeValidate" class="btn primary" value="<s:text name="label.validate"/>">
		</div>
	</div>
</div>
</form>

<script type="text/javascript">

	$(document).ready(function() {
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});
		
		$('#codeValidate').click(function() {
			showParentPage();
			progress();
		});
		
		$('input[name="validateAgainst"]').click(function() {
			var selectedVal = $(this).val();
			if (selectedVal == "functionalTest") {
				$('#techDiv, #skipTestUl').hide();
			} else if (selectedVal == "source") {
				$('#techDiv, #skipTestUl').show();
			}
		});
		
		 $('#technology').change(function() {
			var selectedval = $("#codeTechnology option:selected").val();
			if (selectedval == "js" || selectedval == "jsp") {
				$('#funTestRadio').prop("disabled", true);
				$('input:radio[name="validateAgainst"]').filter('[value="source"]').attr('checked', true);
 				$('#skipTestUl').show();
			} else {
				$('#funTestRadio').prop("disabled", false);
				$('input:radio[name="validateAgainst"]').filter('[value="source"]').attr('checked', true);
 				$('#skipTestUl').show();
			}
		}); 
	});
	
</script>