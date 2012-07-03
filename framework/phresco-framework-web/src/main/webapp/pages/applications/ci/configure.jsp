<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
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

<%@	page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.util.List"%>
<%@ page import="com.photon.phresco.commons.CIJob" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.util.XCodeConstants"%>
<%@ page import="com.photon.phresco.util.AndroidConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.framework.commons.PBXNativeTarget"%>
<%@ page import="com.photon.phresco.configuration.Environment"%>

<script src="js/select-envs.js"></script>

<%
	List<SettingsInfo> serverConfigs = (List<SettingsInfo>) request.getAttribute(FrameworkConstants.REQ_SERVER_CONFIGS);
	List<SettingsInfo> databaseConfigs = (List<SettingsInfo>) request.getAttribute(FrameworkConstants.REQ_DATABASE_CONFIGS);
	List<SettingsInfo> emailConfigs = (List<SettingsInfo>) request.getAttribute(FrameworkConstants.REQ_EMAIL_CONFIGS);
	List<SettingsInfo> webServiceConfigs = (List<SettingsInfo>) request.getAttribute(FrameworkConstants.REQ_WEBSERVICE_CONFIGS);
	
	String showSettings = (String) request.getAttribute(FrameworkConstants.REQ_SHOW_SETTINGS);
	if(showSettings != null && Boolean.valueOf(showSettings)){
		showSettings = "checked";
	}
	
	String[] weekDays = {"", "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	String[] months = {"", "January","February","March","April","May","June","July","August","September","October","November","December"};

	CIJob existingJob =  (CIJob) request.getAttribute(FrameworkConstants.REQ_EXISTING_JOB);
	String disableStr = existingJob == null ? "" : "disabled";
    String projectCode = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE );
	Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
	String technology = (String)project.getProjectInfo().getTechnology().getId();
    String actionStr = "saveJob";
    actionStr = (existingJob == null || StringUtils.isEmpty(existingJob.getSvnUrl())) ? "saveJob" : "updateJob";
    existingJob = (existingJob == null || StringUtils.isEmpty(existingJob.getSvnUrl())) ? null : existingJob; // when we setup it ll have only jenkins url and port in that case we have to check svnUrl and make null
   	//xcode targets
   	List<PBXNativeTarget> xcodeConfigs = (List<PBXNativeTarget>) request.getAttribute(FrameworkConstants.REQ_XCODE_CONFIGS);
   	List<Environment> environments = (List<Environment>) request.getAttribute(FrameworkConstants.REQ_ENVIRONMENTS);
   	// mac sdks
   	List<String> macSdks = (List<String>) request.getAttribute(FrameworkConstants.REQ_IPHONE_SDKS);
%>
<div class="popup_Modal configurePopUp" id="ciDetails">
    <form name="ciDetails" action="<%= actionStr %>" method="post" autocomplete="off" class="ci_form" id="ciForm">
        <div class="modal-header">
            <h3><s:text name="label.ci"/></h3>
            <a class="close" href="#" id="close">&times;</a>
        </div>
        
        <div class="modal-body" id="ciConfigs" style="padding-top: 2px;">
        	<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"></label>
				<div class="input">
					<span id="missingData" class="missingData" style="color:#690A0B;"></span>
				</div>
			</div>
			
        	<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.name"/></label>
				<div class="input">
					<input type="text" id="name" name="name" value="<%= existingJob == null ? projectCode : existingJob.getName()%>" disabled autofocus>
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.svn.url"/></label>
				<div class="input">
					<input type="text" id="svnurl" name="svnurl" value="<%= existingJob == null ? "" : existingJob.getSvnUrl()%>">
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.username"/></label>
				<div class="input">
					<input type="text" id="username" name="username" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getUserName()%>">
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.password"/></label>
				<div class="input">
					<input type="password" id="password" name="password" maxlength="63" title="63 Characters only" value="<%= existingJob == null ? "" : existingJob.getPassword()%>">
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.sender.mail"/></label>
				<div class="input">
					<input type="text" name="senderEmailId" value="<%= existingJob == null ? "" : existingJob.getSenderEmailId()%>">
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.sender.pwd"/></label>
				<div class="input">
					<input type="password" name="senderEmailPassword" value="<%= existingJob == null ? "" : existingJob.getSenderEmailPassword()%>">
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.recipient.mail"/></label>
				
				<div class="input">
					<div class="multipleFields emaillsFieldsWidth">
						<div><input id="successEmail" type="checkbox" name="emails"  value="success"/>&nbsp; <s:text name="label.when.success"/></div>
					</div>
					<div class="multipleFields">
						<div><input id="successEmailId" type="text" name="successEmailIds"  value="<%= existingJob == null ? "" : (String)existingJob.getEmail().get("successEmails")%>" disabled></div>
					</div>
				</div>
				
				<div class="input">
					<div class="multipleFields emaillsFieldsWidth">
						<div><input id="failureEmail" type="checkbox" name="emails" value="failure"/> &nbsp;<s:text name="label.when.fail"/></div>
					</div>
					<div class="multipleFields">
						<div><input id="failureEmailId" type="text" name="failureEmailIds" value="<%= existingJob == null ? "" : (String)existingJob.getEmail().get("failureEmails")%>" disabled></div>
					</div>
				</div>
					<label for="xlInput" class="xlInput popup-label"><span class="red">* </span>Build Triggers</label>
				<div class="input">
					<div class="multipleFields emaillsFieldsWidth">
						<div><input id="buildPeriodically" type="checkbox" name="triggers" value="TimerTrigger"/>&nbsp;Build periodically</div>
					</div>
				</div>
				
				<div class="input">
					<div class="multipleFields emaillsFieldsWidth">
						<div><input id="pollSCM" type="checkbox" name="triggers" value="SCMTrigger"/>&nbsp;Poll SCM</div>
					</div>
				</div>
			</div>
			<%
				String schedule = existingJob == null ? "" : (String)existingJob.getScheduleType();
				
				String dailyEvery = "";
				String dailyHour = "";
				String dailyMinute = "";
				
				String weeklyWeek = "";
				String weeklyHour = "";
				String weeklyMinute = "";
				
				String monthlyDay = "";
				String monthlyMonth = "";
				String monthlyHour = "";
				String monthlyMinute = "";
				
				String CronExpre = existingJob == null ? "" : existingJob.getScheduleExpression();
				String[] cronSplit = CronExpre.split(" ");
				if(schedule.equals("Daily")) {
					if(CronExpre.contains("/")) {
						dailyEvery = "checked";
					}
					
		            if (cronSplit[0].contains("/")) {
		            	dailyMinute = cronSplit[0].substring(2) + "";
		            } else {
		            	dailyMinute = cronSplit[0];
		            }
		            
					if (cronSplit[1].contains("/")) {
						dailyHour = cronSplit[1].substring(2) + "";
		            } else {
		            	dailyHour = cronSplit[1];
		            }
					
				} else if(schedule.equals("Weekly")) {
					weeklyWeek = cronSplit[4];
					weeklyHour = cronSplit[1];
					weeklyMinute = cronSplit[0];
					
				} else if(schedule.equals("Monthly")) {
					monthlyDay = cronSplit[2];
					monthlyMonth = cronSplit[3];
					monthlyHour = cronSplit[1];
					monthlyMinute = cronSplit[0];
					
				}
			%>
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.schedule"/></label>
				
				<div class="input">
					<div class="multipleFields quartsRadioWidth">
						<div><input id="scheduleDaily" type="radio" name="schedule" value="Daily"  onChange="javascript:show('Daily');" <%= ((schedule.equals("Daily")) || (schedule.equals(""))) ? "checked" : "" %> />&nbsp; <s:text name="label.daily"/></div>
					</div>
					<div class="multipleFields quartsRadioWidth">
						<div><input id="scheduleDaily" type="radio" name="schedule" value="Weekly" onChange="javascript:show('Weekly');" <%= schedule.equals("Weekly") ? "checked" : "" %> />&nbsp; <s:text name="label.weekly"/></div>
					</div>
					<div class="multipleFields quartsRadioWidth">
						<div><input id="scheduleDaily" type="radio" name="schedule" value="Monthly" onChange="javascript:show('Monthly');" <%= schedule.equals("Monthly") ? "checked" : "" %>/>&nbsp; <s:text name="label.monthly"/></div>
					</div>
				</div>
				<div  id='Daily' class="schedulerWidth">
					<div><s:text name="label.every"/> &nbsp;&nbsp;&nbsp;&nbsp;	<s:text name="label.hours"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:text name="label.minutes"/></div>
					<div class="dailyInnerDiv">
						<s:text name="label.At"/> &nbsp;&nbsp;
						<input type="checkbox" id="daily_every" name="daily_every" onChange="javascript:show('Daily');" <%= dailyEvery %>>
						&nbsp;&nbsp;
	                    <select id="daily_hour" name="daily_hour" onChange="javascript:show('Daily');" class="schedulerSelectWidth">
	                         <option value="*">*</option>
	                         <% 
	                         	for (int i = 1; i < 24; i++) {
	                         		String selectedStr = "";
	                         		if(!StringUtils.isEmpty(dailyHour) && dailyHour.equals("" + i)) {
	                         			selectedStr = "selected";
	                         		}
	                         %>
	                         	<option value="<%= i %>" <%= selectedStr %>> <%= i %> </option>
	                         <% } %>
	                   	</select>
	                     &nbsp;	&nbsp;
					    <select id="daily_minute" name="daily_minute" onChange="javascript:show('Daily');" class="schedulerSelectWidth">
							<option value="*">*</option>            
						<% 
							for (int i = 1; i < 60; i++) { 
                         		String selectedStr = "";
                         		if(!StringUtils.isEmpty(dailyMinute) && dailyMinute.equals("" + i)) {
                         			selectedStr = "selected";
                         		}
						%>
							<option value="<%= i %>" <%= selectedStr %>><%= i %></option>
						<% } %>
						</select>
                    </div>
				</div>
				<div id='Weekly' class="schedulerWidth">
   					<select id="weekly_week" name="weekly_week" multiple onChange="javascript:show('Weekly');" class="schedulerDay alignVertical">
				   		<%
				   			String defaultSelectedStr = "";
				   			if(StringUtils.isEmpty(weeklyWeek)) {
				             	defaultSelectedStr = "selected";
				           	}
				   		%>
				   			<option value="*" <%= defaultSelectedStr %>>*</option>
				   		<%
				   			for(int i = 1; i < weekDays.length; i++){
				   				String selectedStr = "";
				   				if(!StringUtils.isEmpty(weeklyWeek) && weeklyWeek.equals("" + i)) {
                     				selectedStr = "selected";
                     			}
						%>
							<option value="<%= i %>" <%= selectedStr %>><%= weekDays[i] %></option>
                        <%  
                        	}
				   		%>
                    </select>
                    
                    &nbsp; <s:text name="label.smal.at"/> &nbsp;
                    <select id="weekly_hours" name="weekly_hours" onChange="javascript:show('Weekly');" class="schedulerSelectWidth alignVertical">
                        <option value="*">*</option>
	                    <% 
	                    	for (int i = 1; i < 24; i++) {
	                    		String selectedStr = "";
	                     		if(!StringUtils.isEmpty(weeklyHour) && weeklyHour.equals("" + i)) {
	                     			selectedStr = "selected";
	                     		}
	                    %>
	                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
	                    <% } %>
                    </select>&nbsp;<s:text name="label.hour"/>&nbsp;
                    
                  	<select id="weekly_minute" name="weekly_minute" onChange="javascript:show('Weekly');" class="schedulerSelectWidth alignVertical">
	                  <option value="*">*</option>            
	                  <% 
	                  		for (int i = 1; i < 60; i++) { 
	                    		String selectedStr = "";
	                     		if(!StringUtils.isEmpty(weeklyMinute) && weeklyMinute.equals("" + i)) {
	                     			selectedStr = "selected";
	                     		}
	                  %>
	                      <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
	                  <% } %>
                   	</select>&nbsp;<s:text name="label.minute"/>
                                                
				</div>
				<div id='Monthly' class="schedulerWidth">
                   <s:text name="label.every"/>
                   <select id="monthly_day" name="monthly_day" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
                           <option value="*">*</option>
                       <% 
                       		for (int i = 1; i <= 31; i++) { 
	                    		String selectedStr = "";
	                     		if(!StringUtils.isEmpty(monthlyDay) && monthlyDay.equals("" + i)) {
	                     			selectedStr = "selected";
	                     		}
                       	%>
                           <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
                       <% } %>
                   </select>&nbsp;<s:text name="label.of"/>&nbsp;
                   
                  	<select id="monthly_month" name="monthly_month" multiple onChange="javascript:show('Monthly');" class="schedulerDay alignVertical">
						<%
				   			defaultSelectedStr = "";
				   			if(StringUtils.isEmpty(monthlyMonth)) {
				             	defaultSelectedStr = "selected";
				           	}
				   		%>
				   			<option value="*" <%= defaultSelectedStr %>>*</option>
				   		<%
				   			for(int i = 1; i < months.length; i++){
				   				String selectedStr = "";
				   				if(!StringUtils.isEmpty(monthlyMonth) && monthlyMonth.equals("" + i)) {
                     				selectedStr = "selected";
                     			}
						%>
							<option value="<%= i %>" <%= selectedStr %>><%= months[i] %></option>
                        <%  
                        	}
				   		%>
                    </select>
                    
					&nbsp; <s:text name="label.smal.at"/> &nbsp;
                    <select id="monthly_hour" name="monthly_hour" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
                        <option value="*">*</option>
                    <% 
                    	for (int i = 1; i < 24; i++) { 
                    		String selectedStr = "";
                     		if(!StringUtils.isEmpty(monthlyHour) && monthlyHour.equals("" + i)) {
                     			selectedStr = "selected";
                     		}
                    %>
                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
                    <% } %>
                    </select>&nbsp;<s:text name="label.hour"/>
                    &nbsp;
                    <select id="monthly_minute" name="monthly_minute" onChange="javascript:show('Monthly');" class="schedulerSelectWidth alignVertical">
                        <option value="*">*</option>
                    <% 
                    	for (int i = 1; i < 60; i++) { 
                    		String selectedStr = "";
                     		if(!StringUtils.isEmpty(monthlyMinute) && monthlyMinute.equals("" + i)) {
                     			selectedStr = "selected";
                     		}
                    %>
                        <option value="<%= i %>" <%= selectedStr %>><%= i %></option>
                    <% } %>
                    </select> &nbsp; <s:text name="label.minute"/>
                                                
				</div>
				
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><s:text name="label.cron.expression"/></label>
				<div class="input" id="cronValidation">
<!-- 					<input type="text" id="cronExpression" name="cronExpression" value=""> -->
				</div>
			</div>
        </div>
		
		<div class="modal-body" id="configs">
			<div class="clearfix">
			    <label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.environment"/></label>
			    <div class="input">
			        <select id="environments" name="environment" class="xlarge">
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
									<option value="<%= environment.getName() %>" <%= selectedStr %> onClick="selectEnvs()"><%= environment.getName() %></option>
							<% 		} 
								}
							%>
						</optgroup>
					</select>
				</div>
			</div>
			
			<% if (TechnologyTypes.ANDROIDS.contains(technology)) { %>
				<!-- Android Version -->
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.sdk"/></label>
					<div class="input">
						<select id="androidVersion" name="androidVersion" class="xlarge" >
							<%
								for (int i=0; i<AndroidConstants.SUPPORTED_SDKS.length; i++) {
							%>
								<option value="<%= AndroidConstants.SUPPORTED_SDKS[i] %>"><%= AndroidConstants.SUPPORTED_SDKS[i] %></option>
							<% } %>
						</select>
					</div>
				</div>
			<% } %>	
		
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
	                
				<!-- SDK -->
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.sdk"/></label>
					<div class="input">
						<select id="sdk" name="sdk" class="xlarge" >
							<%
								if (macSdks != null) {
									for (String sdk : macSdks) {
							%>
								<option value="<%= sdk %>"><%= sdk %></option>
							<% 
									} 
								}
							%>
						</select>
					</div>
				</div>
				
				<!-- Mode -->
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.mode"/></label>
					<div class="input">
						<select id="mode" name="mode" class="xlarge" >
							<option value="<%= XCodeConstants.CONFIGURATION_DEBUG %>"><%= XCodeConstants.CONFIGURATION_DEBUG %></option>
							<option value="<%= XCodeConstants.CONFIGURATION_RELEASE %>"><%= XCodeConstants.CONFIGURATION_RELEASE %></option>
						</select>
					</div>
				</div>
			<% } %>	

			<!-- Show Settings -->
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"></label>
				<div class="input">
					<input type="checkbox" id="showSettings" name="showSettings" value="showsettings" <%= showSettings %>> <s:text name="label.show.setting"/>
						&nbsp;
				<% if (TechnologyTypes.ANDROIDS.contains(technology)) { %>
						<input type="checkbox" id="proguard" name="proguard" value="false" disabled="disabled">
						<span><s:text name="label.progurad"/></span>
				<% } %>
				</div>
			</div>
		</div>
    
        <div class="modal-footer">
             <div style="float: left;" id="loadingDiv">
					<div id="errMsg"></div>
				    <img src="themes/photon/images/loading_red.gif" class="popupLoadingIcon" style="display: none;"> 
	    	 </div> 
            <input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
            <input type="button" class="btn primary" value="<s:text name="label.save"/>" id="actionBtn">
            <input type="button" class="btn primary" value="<s:text name="label.next"/>" id="nextBtn">
            <input type="button" class="btn primary" value="<s:text name="label.previous"/>" id="preBtn">
        </div>
    </form>
</div>

<script type="text/javascript">
	var selectedSchedule = $("input:radio[name=schedule]:checked").val();
	loadSchedule(selectedSchedule);
	$(document).ready(function() {
		$("#svnurl").focus();
		$("#configs").hide();
		$("#actionBtn").hide();
		$("#preBtn").hide();
		
		$("#nextBtn").click(function() {
			var svnurl= $("#svnurl").val();
			var username= $("#username").val();
			var password= $("#password").val();
			if(isValidUrl(svnurl)){
				$("#errMsg").html("Enter SVN URL");
				$("#svnurl").focus();
				$("#svnurl").val("");
				return false;
			}
			
			if(isBlank($.trim($("input[name= username]").val()))){
				$("#errMsg").html("Enter UserName");
				$("#username").focus();
				$("#username").val("");
				return false;
			}
			if(password == "") {
				$("#errMsg").html("Enter Password");
				$("#password").focus();
				return;
			} else{
				$("#errMsg").empty();
			}
			if($("input[name='name']").val().length <= 0){
				ciConfigureError('missingData', "Name is missing");
				return;
			} else {
				$("#errMsg").empty();
			}
			
			if( $("[name=triggers]:checked").length == 0 ) {
				$("#errMsg").html("Enter Build Triggers");
				$("#buildPeriodically").focus();
				return false;
			} else {
				$("#errMsg").empty();
				$(this).hide();
				$("#ciConfigs").hide();
				$("#configs").show();
				$("#preBtn").show();
				$("#actionBtn").show();
				showSettings();
			}
        });
        
        $("#preBtn").click(function() {
			$(this).hide();
			$("#ciConfigs").show();
			$("#configs").hide();
			$("#nextBtn").show();
			$("#actionBtn").hide();
        });
        
		$('#close, #cancel').click(function() {
			showParentPage();
		});
		
		$("#actionBtn").click(function() {
			isCiRefresh = true;
			getCurrentCSS();
			$('.popupLoadingIcon').css("display","block");
			var url = $("#ciForm").attr("action");
			$('#ciForm :input').attr('disabled', false);
			popup(url, '', $('#tabDiv'));
        });
		
		$("#successEmail").click(function() {
			if($(this).is(":checked")) {
				$("#successEmailId").attr("disabled", false);
			} else {
				$("#successEmailId").attr("disabled", true);
				$("#successEmailId").val('');
	   		}
		});
		
		$("#failureEmail").click(function() {
			if($(this).is(":checked")) {
				$("#failureEmailId").attr("disabled", false);
			} else {
				$("#failureEmailId").attr("disabled", true);
				$("#failureEmailId").val('');
	   		}
		});
		
		$("input:radio[name=schedule]").click(function() {
		    var selectedSchedule = $(this).val();
		    loadSchedule(selectedSchedule);
		});
		show(selectedSchedule);
		
		// It allows user type text without spaces
	    $("#name").keydown(function(event) {
	        if ( event.keyCode == 32 ) {
	        	event.preventDefault();
	        }
	    });
		
		
		
		<% 
			if(existingJob != null) {
				for(String trigger : existingJob.getTriggers()) {
		%>
					$("input[value='<%= trigger%>']").prop("checked", true);
		<%
				}
			}
		%>
	    
	});

	
	function ciConfigureError(id, errMsg){
		$("#" + id ).empty();
		$("#" + id ).append(errMsg);
	}
    
	function loadSchedule(selectedSchedule) {
		hideAllSchedule();
		$("#" + selectedSchedule).show();	
	}
	
	function hideAllSchedule() {
		$("#Daily").hide();
		$("#Weekly").hide();
		$("#Monthly").hide();
	}
	
    function show(ids) {
        var buttonObj = window.document.getElementById("enableButton");

        if(ids == "Daily") {
            var dailyEveryObj = window.document.getElementById("daily_every");
            var dailyHourObj = window.document.getElementById("daily_hour");
            var hours = dailyHourObj.options[dailyHourObj.selectedIndex].value;
            var dailyMinuteObj = window.document.getElementById("daily_minute");
            var minutes = dailyMinuteObj.options[dailyMinuteObj.selectedIndex].value;
    		var params = "cronBy=";
			params = params.concat("Daily");
			params = params.concat("&hours=");
			params = params.concat(hours);
			params = params.concat("&minutes=");
			params = params.concat(minutes);
			params = params.concat("&every=");
			params = params.concat(dailyEveryObj.checked);
			cronValidationLoad(params);
        } else if(ids == "Weekly") {

            var weeklyHourObj = window.document.getElementById("weekly_hours");
            var hours = weeklyHourObj.options[weeklyHourObj.selectedIndex].value;

            var weeklyMinuteObj = window.document.getElementById("weekly_minute");
            var minutes = weeklyMinuteObj.options[weeklyMinuteObj.selectedIndex].value;

            var weekObj = window.document.getElementById("weekly_week");
            var week;
            var count = 0;
            
            if (weekObj.options.selectedIndex == -1)  {
                window.document.getElementById("cronValidation").innerHTML = '<b>Select Cron Expression</b>';
            } else {
                for (var i = 0; i < weekObj.options.length; i++){

                    if(weekObj.options[i].selected){
                        if (count == 0) {
                            week = weekObj.options[i].value;
                        } else {
                           week += "," + weekObj.options[i].value;
                        }

                        count++;
                    }
                }
	        	var params = "cronBy=";
				params = params.concat("Weekly");
				params = params.concat("&hours=");
				params = params.concat(hours);
				params = params.concat("&minutes=");
				params = params.concat(minutes);
				params = params.concat("&week=");
				params = params.concat(week);
				cronValidationLoad(params);
        		
            }

      } else if(ids == "Monthly") {

            var monthlyDayObj = window.document.getElementById("monthly_day");
            var day = monthlyDayObj.options[monthlyDayObj.selectedIndex].value;

            var monthlyHourObj = window.document.getElementById("monthly_hour");
            var hours = monthlyHourObj.options[monthlyHourObj.selectedIndex].value;

            var monthlyMinuteObj = window.document.getElementById("monthly_minute");
            var minutes = monthlyMinuteObj.options[monthlyMinuteObj.selectedIndex].value;

            var monthObj = window.document.getElementById("monthly_month");
            var month;
            var count = 0;
            if (monthObj.options.selectedIndex == -1)  {
                window.document.getElementById("cronValidation").innerHTML = '<b>Select Cron Expression</b>';
            } else {
                for (var i = 0; i < monthObj.options.length; i++){

                    if(monthObj.options[i].selected){
                        if (count == 0) {
                            month = monthObj.options[i].value;
                        } else {
                           month += "," + monthObj.options[i].value;
                        }

                        count++;
                    }
                }
        		var params = "cronBy=";
				params = params.concat("Monthly");
				params = params.concat("&hours=");
				params = params.concat(hours);
				params = params.concat("&minutes=");
				params = params.concat(minutes);
				params = params.concat("&month=");
				params = params.concat(month);
				params = params.concat("&day=");
				params = params.concat(day);
				cronValidationLoad(params);
            }

        }
    }
    
    function cronValidationLoad(params) {
    	popup('cronValidation', params, $('#cronValidation'));
    }
</script>