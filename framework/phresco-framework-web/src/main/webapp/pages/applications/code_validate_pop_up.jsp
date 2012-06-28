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
			
		<% } else if (TechnologyTypes.HTML5_WIDGET.equals(technology) || TechnologyTypes.HTML5_MOBILE_WIDGET.equals(technology) 
				|| TechnologyTypes.HTML5.equals(technology)|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(technology)) { %>
		
		<fieldset class="popup-fieldset">
			<legend class="fieldSetLegend"><s:text name="label.select.tech"/></legend>
			<!-- Show Settings -->
			<div class="clearfix">
				<div class="xlInput">
					<ul class="inputs-list">
						<li class="popup-li"> 
							<input type="radio" name="codeTechnology" value="" checked>
							<span class="textarea_span popup-span"><s:text name="label.tech.java"/></span>
							<input type="radio" name="codeTechnology" value="js" >
							<span class="textarea_span popup-span"><s:text name="label.tech.javascript"/></span>
						</li>
					</ul>
				</div>	
			</div>
		</fieldset>
		<% } %>
	</div>
	
	<fieldset class="popup-fieldset">
			<legend class="fieldSetLegend"><s:text name="label.select.validate.against"/></legend>
			<!-- Show Settings -->
			<div class="clearfix">
				<div class="xlInput">
					<ul class="inputs-list">
						<li class="popup-li"> 
							<input type="radio" name="validateAgainst" value="source" checked>
							<span class="textarea_span popup-span"><s:text name="label.validateAgainst.source"/></span>
							<input id="funTestRadio" type="radio" name="validateAgainst" value="functionalTest" >
							<span  id="funTestText" class="textarea_span popup-span"><s:text name="label.validateAgainst.functionalTest"/></span>
						</li>
					</ul>
				</div>	
			</div>
		</fieldset>
		<ul id="skipTestUl" class="inputs-list" style="text-align: center;" >
			<li class="popup-li"> 
				<input type="checkbox" name="skipTest" value="true" />
				<span class="textarea_span popup-span"><s:text name="label.skip.unit.test"/></span>
			</li>
		</ul>
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
				$('#skipTestUl').hide();
			} else if (selectedVal == "source") {
				$('#skipTestUl').show();
			}
		});
		
		 $('input[name="codeTechnology"]').click(function() {
			var selectedval = $(this).val();
			if(selectedval == "js") {
				$('#funTestRadio').hide();
				$('#funTestText').hide();
				$('input:radio[name="validateAgainst"]').filter('[value="source"]').attr('checked', true);
				$('#skipTestUl').show();
			}else {
				$('#funTestRadio').show();
				$('#funTestText').show();
				$('input:radio[name="validateAgainst"]').filter('[value="source"]').attr('checked', true);
				$('#skipTestUl').show();
			}
		}); 
	});
	
</script>