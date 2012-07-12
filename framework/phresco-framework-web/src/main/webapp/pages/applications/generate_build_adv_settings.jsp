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
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.phresco.pom.android.AndroidProfile" %>

<!--[if IE]>
<script src="js/html5.js"></script>
<![endif]-->
	
<script src="js/reader.js" ></script>
<script src="js/select-envs.js"></script>

<%
// 	android profile information details
	AndroidProfile androidProfile = (AndroidProfile) request.getAttribute(FrameworkConstants.REQ_ANDROID_PROFILE_DET);
%>
<form action="build" method="post" autocomplete="off" class="build_form" id="advancedSettingsBuildForm">
<div class="popup_Modal topFouty" id="generateBuild_Modal">
	<div class="modal-header">
		<h3 id="generateBuildTitle">
			<s:text name="build.advanced.settings"/>
		</h3>
		<a class="close" href="#" id="advSettingsClose">&times;</a>
	</div>

	<div class="modal-body">
<!-- 		advanced settingd -->
		<div class="theme_accordion_container clearfix" style="float: none;">
		    <section class="accordion_panel_wid">
		        <div class="accordion_panel_inner adv-settings-accoridan-inner">
		            <section class="lft_menus_container adv-settings-width">
		                <span class="siteaccordion" id="siteaccordion_active"><span><s:text name="label.signing"/></span></span>
		                <div class="mfbox siteinnertooltiptxt" id="build_adv_sett">
		                    <div class="scrollpanel adv_setting_accordian_bottom">
		                        <section class="scrollpanel_inner">
							       	<div class="clearfix">
										<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.keystore"/></label>
										<div class="input">
											<input type="text" name="keystore" value="<%= androidProfile != null ? androidProfile.getKeystore() : ""%>" autofocus>
											<input type="button" id="fileLocation" class="btn primary btn_browse browseFileLocation" value="<s:text name="label.select.file"/>">
										</div>
									</div>
									
									<div class="clearfix">
										<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.storepass"/></label>
										<div class="input">
											<input type="text" name="storepass" value="<%= androidProfile != null ? androidProfile.getStorepass() : ""%>">
										</div>
									</div>
									
									<div class="clearfix">
										<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.keypass"/></label>
										<div class="input">
											<input type="text" name="keypass" value="<%= androidProfile != null ? androidProfile.getKeypass() : ""%>">
										</div>
									</div>
									
									<div class="clearfix">
										<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.alias"/></label>
										<div class="input">
											<input type="text" name="alias" value="<%= androidProfile != null ? androidProfile.getAlias() : ""%>">
										</div>
									</div>
		                        </section>
		                    </div>
		                </div>
		            </section>  
		        </div>
		    </section>
		</div>
<!-- 		advanced settings end -->
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action adv-settings-popup-action">
			<div id="advSettingsErrMsg" class="adv-settings-error-msg"></div>
			<!-- 			error and success message -->
			<div class="popup alert-message success" id="popupSuccessMsg"></div>
			<div class="popup alert-message error" id="popupErrorMsg"></div>
			
			<div style="float: right;">
				<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="advSettingsCancel">
				<%
					if (androidProfile == null) {
				%>
					<input type="button" id="createProfile" class="btn primary" value="<s:text name="label.create.profile"/>">
				<%
					} else {
				%>
					<input type="button" id="createProfile" class="btn primary" value="<s:text name="label.update.profile"/>">
				<%
					}
				%>
			</div>
		</div>
	</div>
</div>
</form>

<script type="text/javascript">

	<%
		if (androidProfile != null) {
	%>
		$('#profileAvailable').val("true");
	<%
		}
	%>
	
    // Hide the generate build popup
    $('#generateBuildForm').hide();

	$(document).ready(function() {
		// accodion for advanced issue
		accordion();
		
		// Intially accorian should be opened
		$('#build_adv_sett').css('display', 'block');
		
		$('#advSettingsClose, #advSettingsCancel').click(function() {
			// when we go back to generate build popup, accoridan is not working due to event bind in gnerate_build_adv_settings
			$('.siteaccordion').unbind('click');
			accordion();
			
			//when profile is not created , checkbox should be unchecked
// 			if(isBlank($('#profileAvailable').val())) {
// 				$('input[type=checkbox][name=signing]').removeAttr("checked");
// 			}
			$('#advancedSettingsBuildForm').hide();
			$('#generateBuildForm').show();
			$("#advSettingsErrMsg").html("");
			$("#errMsg").html('');
		});
		
		$('#createProfile').click(function() {
			if(isBlank($.trim($("input[name=keystore]").val()))) {
				$("#advSettingsErrMsg").html("Enter keystore file location");
				$("input[name=keystore]").focus();
				$("input[name=keystore]").val("");
				return false;
			} else if(isBlank($.trim($("input[name=storepass]").val()))) {
				$("#errMsg").html("Enter storepass");
				$("input[name=storepass]").focus();
				$("input[name=storepass]").val("");
				return false;
			} else if(isBlank($.trim($("input[name=keypass]").val()))) {
				$("#advSettingsErrMsg").html("Enter keypass");
				$("input[name=keypass]").focus();
				$("input[name=keypass]").val("");
				return false;
			} else if(isBlank($.trim($("input[name=alias]").val()))) {
				$("#advSettingsErrMsg").html("Enter alias");
				$("input[name=alias]").focus();
				$("input[name=alias]").val("");
				return false;
			} else {
				var params = "";
		       	popup('createProfile', params, '', true);
			}
		});
		
		$('.browseFileLocation').click(function(){
			$('#browseLocation').remove();
			$('#advancedSettingsBuildForm').hide();
			popup('browse', '', $('#popup_div'), '', true);
		});

	});
		
	function successProfileCreation(data) {
		if(data.profileCreationStatus == true) {
			$('#createProfile').val("Update Profile");
			$('#profileAvailable').val("true");
			showHidePopupMsg($("#popupSuccessMsg"), data.profileCreationMessage);
		} else {
			$('#profileAvailable').val("");
			showHidePopupMsg($("#popupErrorMsg"), data.profileCreationMessage);
		}
		// profile creation status to use from build popup
		$('#profileAvailable').val(data.profileCreationStatus);
	}
	
	function checkObj(obj) {
		if(obj == "null" || obj == undefined) {
			return "";
		} else {
			return obj;
		}
	}
</script>