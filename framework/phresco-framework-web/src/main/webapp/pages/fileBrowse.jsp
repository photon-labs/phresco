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
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.framework.commons.ApplicationsUtil"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
	
	<!--[if IE]>
	<script src="js/html5.js"></script>
	<![endif]-->
	
<script src="js/reader.js" ></script>
<script src="js/select-envs.js"></script>

<%
	String projectLocation = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_LOCATION);
	String fileTypes = (String) request.getAttribute(FrameworkConstants.FILE_TYPES);
	String fileorfolder = (String) request.getAttribute(FrameworkConstants.FILE_BROWSE);
%>

<form action="build" method="post" autocomplete="off" class="build_form" id="browseLocation">
<div class="popup_Modal topFouty" id="generateBuild_Modal">
	<div class="modal-header">
		<h3 id="generateBuildTitle">
			<s:text name="label.select.file"/>
		</h3>
		<a class="close" href="#" id="fileBrowseClose">&times;</a>
	</div>

	<div class="modal-body fileTreeBrowseOverflow">
		<div id="JQueryFTD" class="JQueryFTD"></div>
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action">
			<input type="text" class="xlarge javastd" id="browseSelectedLocation" name="browseLocation" > 
			<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="fileBrowseCancel">
			<input type="button" id="fileBrowseOkay" class="btn primary" value="<s:text name="label.ok"/>">
		</div>
	</div>
</div>
</form> 

<script type="text/javascript">
    
	if(!isiPad()){
	    /* JQuery scroll bar */
		$(".generate_build").scrollbars();
	}
	
	$(document).ready(function() {
		
		$('#fileBrowseClose, #fileBrowseCancel').click(function() {
			showAdvBuildSettings();
			showFunctionalTestForm();
		});
		
		// android build advanced technology
		$('#fileBrowseOkay').click(function() {
			$("input[name=keystore]").val($('#browseSelectedLocation').val());
			$('#advancedSettingsBuildForm').show();
			$('#generateBuild_Modal').hide();
			$('#browseLocation').hide();
		});
		
		// java standalone functional test jar browse location
		$('#fileBrowseOkay').click(function() {
			$("input[name=jarLocation]").val($('#browseSelectedLocation').val());
			$('.build_form').show();
			$('#browseLocation').hide();
		});

		$('#JQueryFTD').fileTree({
			root: '<%= projectLocation %>',
			script: 'pages/jqueryFileTree.jsp',
			expandSpeed: 1000,
			collapseSpeed: 1000,
			multiFolder: true,
			fileTypes: '<%= fileTypes %>',
			fileOrFolder: '<%= fileorfolder %>'
		}, function(file) {
			$('#browseSelectedLocation').val(file);
		});
		
	});
		
	function checkObj(obj) {
		if(obj == "null" || obj == undefined) {
			return "";
		} else {
			return obj;
		}
	}
	
	function showAdvBuildSettings() {
		$('#browseLocation').hide();
		$('#generateBuild_Modal').hide();
	}
	
	function showFunctionalTestForm() {
		$('#browseLocation').empty();
		$('.build_form').show();
	}
</script>