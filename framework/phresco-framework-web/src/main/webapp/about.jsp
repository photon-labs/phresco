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
<%@ page import="com.photon.phresco.model.VersionInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>

<script type="text/javascript" src="js/about.js"></script>

<div id="versionInfo"  style="display:none">
	<div class="modal-header">
		<h3><s:text name="label.phresco"/> <span id="phrescoVersion"></span></h3>
		<a id="closeAboutDialog" class="close" href="#" id="close">&times;</a>
	</div>
	
	<div class="modal-body abt_modal-body">
		<div class="abt_logo">
			<img src="images/phresco.png" alt="logo" class="abt_logo_img">
		</div>
		
		<div class="abt_content">
				Phresco is a next-generation development framework of frameworks. 
				It is a platform for creating next generation web, 
				mobile and multi channel presences leveraging existing investments combined with accepted industry best practices.
		</div>
	</div>
	
	<div class="modal-footer">
		<div class="abt_copyright">&copy;&nbsp;2012.Photon Infotech Pvt Ltd</div>
		<div class="action abt_action">
			<img class="popupLoadingIcon" style="position: relative;">
			<input type="button" class="btn primary" value="Ok" id="okAboutDialog">
			<input type="button" class="btn disabled" value="Update Available" id="latestVersion" disabled="disabled">
		</div>
	</div>
</div>

<div id="availableVersion" style="display:none">
	<div class="modal-header">
		<h3><s:text name="label.version"/></h3>
		<a id="closeVersionDialog" class="close" href="#" id="close">&times;</a>
	</div>
	
	<div class="modal-body abt_modal-body latestVersionContent">
		<div class="curVersContent">
			<div class="lftDiv">
				<s:text name="label.framework.current.version"/>
			</div>
			
			<div class="rhtDiv">
					<span id="CurrentVersion"></span>
			</div>
		</div>
		<div class="verInfo">
			<div class="lftDiv">
				<s:text name="label.latest.version"/>
			</div>
			
			<div class="rhtDiv">
					<span id="LatestVersion"></span>
			</div>
		</div>	
		<div class="verInfo">
			<span id="updateAvailableInfo"></span>
		</div>
	</div>
	
	<div class="modal-footer">
		<div class="abt_copyright abtQuest">&nbsp;<s:text name="label.do.u.want.delete"/></div>
		<div class="action abt_action">
			<input type="button" class="btn primary" value="<s:text name="label.no"/>" id="UpdateNo">
			<input type="button" class="btn primary" value="<s:text name="label.yes"/>" id="updateYes">
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("#versionInfo").css("display", 'block');
		getCurrentCSS();
		showVersionInfo();
	});
	
	function showVersionInfo() {
		$.ajax({
			url : 'versionInfo',
	        type : 'POST',
	        success : function(data) {
	        	$('#phrescoVersion').html(data.currentVersion);
 	        	$('#CurrentVersion').html(data.currentVersion);
 	        	$('#LatestVersion').html(data.latestVersion);
 	        	$('#updateAvailableInfo').html(data.message);
 	        	if (data.updateAvail == true) {
	        	    $("#latestVersion").attr("class", "primary btn");
	        	    $("#latestVersion").attr("disabled", false);
  	        	}
 	        	$('.popupLoadingIcon').hide();
	        }
		});
	}

</script>