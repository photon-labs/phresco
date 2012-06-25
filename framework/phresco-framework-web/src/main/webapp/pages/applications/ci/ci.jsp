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

<%@ include file="../progress.jsp" %>
<%@ include file="../errorReport.jsp" %>
<%@ include file="../../userInfoDetails.jsp" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.photon.phresco.exception.PhrescoException"%>
<%@ page import="com.photon.phresco.commons.CIJob" %>
<%@ page import="com.photon.phresco.commons.CIBuild" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.*"%>

<script src="js/reader.js" ></script>
<script type="text/javascript" src="js/delete.js" ></script>
<script type="text/javascript" src="js/home-header.js" ></script>

<style type="text/css">
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
	
	.labelWarn{
        color:#000000;
        width:auto;
        padding: 0;
    }
     
    #warningmsg {
     	display: none;
     	width: auto; 
/*      	left: 52%;  */
		left: 521px;
     	position: absolute;
     	text-align: center;
     	padding: 4px 14px;
     	margin-top: 5px;
     	float: right;
    }
</style>

<%
    Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
    ProjectInfo selectedInfo = null;
    String projectCode = null;
    if(project != null) {
       selectedInfo = project.getProjectInfo();
       projectCode = selectedInfo.getCode();
    }
    boolean jenkinsAlive = request.getAttribute("jenkinsAlive").toString().equals("true") ? true : false;
    boolean buildJenkinsAlive = request.getAttribute("buildJenkinsAlive").toString().equals("true") ? true : false;
    boolean isBuildRunning = request.getAttribute(FrameworkConstants.REQ_CI_BUILD_PROGRESS).toString().equals("true") ? true : false;
    boolean buildStatus = false;
    String buildStatusStr = (String) request.getAttribute(FrameworkConstants.REQ_BUILD_STATUS);
    if(buildStatusStr != null) {
    	buildStatus = buildStatusStr.equals("true") ? true : false;
    }
    String buildSize = (String) request.getAttribute(FrameworkConstants.REQ_TOTAL_BUILDS_SIZE);
    if(buildSize == null) {
    	buildSize = "-1";
    }
    
    CIJob existingJob = (CIJob) request.getAttribute(FrameworkConstants.REQ_EXISTING_JOB);
    boolean isExistJob = true;
    if (existingJob == null || StringUtils.isEmpty(existingJob.getSvnUrl())) { // When we set up cijob. info ll have only url and port
    	isExistJob = false;
    }
    
%>

<s:if test="hasActionMessages()">
    <div class="alert-message success"  id="successmsg">
        <s:actionmessage />
    </div>
</s:if>

<div class="alert-message"  id="warningmsg" style="">
	<s:label cssClass="labelWarn" key="ci.warn.message" />
</div>

<form action="CIBuildDelete" name="ciBuilds" id="deleteObjects" method="post" class="configurations_list_form">
    <div class="operation">
        <input id="setup" type="button" value="<s:text name="label.setup"/>" class="primary btn">
        <input id="startJenkins" type="button" value="<s:text name="label.start"/>" class="primary btn">
        <input id="stopJenkins" type="button" value="<s:text name="label.stop"/>" class="btn disabled" disabled="disabled">
        <input id="configure" type="button" value="<s:text name="label.configure"/>" class="primary btn">
        <input id="build" type="button" value="<s:text name="label.build"/>" class="primary btn" onclick="buildCI();">
        <input id="deleteButton" type="button" value="<s:text name="label.deletebuild"/>" class="btn disabled" disabled="disabled">
        <input id="deleteJob" type="button" value="<s:text name="label.deletejob"/>" class="primary btn" onclick="deleteCIJob();">
    </div>
    
    <% 
        List<CIBuild> builds = (List<CIBuild>)request.getAttribute(FrameworkConstants.CI_JOB_JSON_BUILDS);
        if (builds == null || builds.size() < 1) { 
    %>
        <div class="alert-message block-message warning" >
            <center><s:label key="ci.error.message" cssClass="errorMsgLabel"/></center>
        </div>
    <%
        } else {
    %>
	    <div class="table_div">
	        <!-- List -->
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
								<th class="first">
				                	<div class="th-inner">
				                		#
				                	</div>
				              	</th>
				              	<th class="second">
				                	<div class="th-inner"><s:text name="label.url"/></div>
				              	</th>
				              	<th class="third" style="width: 20%;">
				                	<div class="th-inner"><center><s:text name="label.download"/></center></div>
				              	</th>
				         		<th class="third">
				                	<div class="th-inner"><s:text name="label.time"/></div>
				              	</th>
				              	<th class="third">
				                	<div class="th-inner"><s:text name="label.status"/></div>
				              	</th>
				            </tr>
			          	</thead>
			
			          	<tbody>
						<%
						    if (builds == null) {
						        builds = new ArrayList(1);
						    }
						    for (CIBuild ciBuild : builds) {
						%>
			            	<tr>
			            	    <td>
			            	    	<input type="checkbox" class="check" name="selectedBuilds" value="<%= ciBuild.getNumber() %>">
			            	    </td> 
			              		<td>
			              			<%= ciBuild.getNumber() %>
			              		</td>
			              		<td style="width: 35%;">
			              			<a href="<%= ciBuild.getUrl() %>" target="_blank"><%= ciBuild.getUrl() %></a>
			              		</td>
			              		<td style="width: 20%;padding-left: 3%;">
									<%
		                                if(ciBuild.getStatus().equals("INPROGRESS"))  {
		                            %>
		                                <img src="images/icons/inprogress.png" title="In progress"/>
				              		<% 
		                                } else if(ciBuild.getStatus().equals("SUCCESS")) {
		                                	String downloadUrl = ciBuild.getUrl()+ "artifact/" + ciBuild.getDownload().replaceAll("\"",""); 
		                            %>
				                		<a href="<s:url action='CIBuildDownload'>
						          		     <s:param name="buildDownloadUrl"><%= downloadUrl %></s:param>
						          		     <s:param name="projectCode"><%= projectCode %></s:param>
						          		     </s:url>"><img src="images/icons/download.png" title="Download"/>
			                            </a>

		                            <%  
		                            	}  else {
			                        %>
		                                <img src="images/icons/wrong_icon.png" title="Not available"/>
		                            <%  
		                            	}
				              		%>
								</td>
								<td>
			              			<%= ciBuild.getTimeStamp() %> 
								</td>
			              		<td style="width: 15%;padding-left: 2%;">
			              			<% 
		                                if(ciBuild.getStatus().equals("SUCCESS")) {
		                            %>
		                                <img src="images/icons/success.png" title="Success">
		                           	<%
		                                } else if(ciBuild.getStatus().equals("INPROGRESS"))  { 
		                            %>
		                                <img src="images/icons/inprogress.png" title="In progress"/>
		                            <%
		                                } else { 
		                            %>
		                                <img src="images/icons/failure.png" title="Failure">
		                            <%  } 
		                            %>
								</td>
			            	</tr>
			            <%
							}
						%>	
			          	</tbody>
			        </table>
	      		</div>
    		</div>
	    </div>
    <%
        }
    %>
</form>

<!-- <div class="popup_div" id="CI">
</div> -->

<script type="text/javascript">
/* To check whether the divice is ipad or not */
if(!isiPad()){
	/* JQuery scroll bar */
	$(".table_data_div").scrollbars();
}

// buildSize to refresh ci after build completed
var buildSize = <%= buildSize %>;
var refreshCi = false;

$(document).ready(function() {
	$("#popup_div").css("display","none");
	enableScreen();
		
    $('#configure').click(function() {
        showCI();
    });
    
    $('#setup').click(function() {
       	ProgressShow("block");
       	$("#build-output").empty();
       	getCurrentCSS();
       	setupProgress();
    });
    
    $('#closeGenerateTest, #closeGenTest').click(function() {
    	ProgressShow("none");
    	refreshAfterServerUp();
    });
    
    $('#startJenkins').click(function() {
    	isCiRefresh = true;
       	ProgressShow("block");
       	$("#build-output").empty();
       	getCurrentCSS();
        startJenkins();
    });
    
    $('#stopJenkins').click(function() {
       	ProgressShow("block");
       	$("#build-output").empty();
       	getCurrentCSS();
        stopJenkins();
    });
    
    if('<%= jenkinsAlive %>' == 'true') {
    	enableStart();
    	enableControl($("#configure"), "primary btn");
    	enableControl($("#build"), "primary btn");
    	disableControl($("#setup"), "btn disabled"); // when CI running setup should not work
    } else {
    	enableStop();
    	disableControl($("#configure"), "btn disabled");
    	disableControl($("#build"), "btn disabled");
    }
    
    if ('<%= isExistJob %>' == 'true' && '<%= buildJenkinsAlive %>' == 'true' ) {
    	enableControl($("#build"), "primary btn");
    	enableControl($("#deleteJob"), "primary btn");
    } else {
    	disableControl($("#build"), "btn disabled");
    	disableControl($("#deleteJob"), "btn disabled");
    }
    
    // RBACK implemented
    if ('<%= disableCI %>' == 'true') {
    	disableCI();	//Restrict CI
    }
    
    // After user triggered build , if build is in progress disable build btn
    if ('<%= buildStatus %>' == 'true') {
    	disableControl($("#configure"), "btn disabled"); // when building , user should not configure which leads to restart of jenkins
    	disableControl($("#build"), "btn disabled");
    	refreshCi = true;
    	refreshCI();	// Get total no of builds 
    }
    
    // delete ci builds

    $('#deleteButton').click(function() {
           $("#confirmationText").html("Do you want to delete the selected build(s)");
           dialog('block');
           escBlockPopup();
    });
    
	$('form').submit(function() {
        showProgessBar("Deleting Build (s)", 100);
        var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
        performAction('CIBuildDelete', params, $("#tabDiv"));
        return false;
    });
	
	// if build is in progress disable configure button
    if ('<%= isBuildRunning %>' == 'true') {
    	disableControl($("#configure"), "btn disabled"); // when building , user should not configure which leads to restart of jenkins
    	refreshBuild();
    } else {
//     	enableBtn('configure');    	
    }
	
	if(isCiRefresh == true) {
		refreshAfterServerUp(); // after server restarted , it ll reload builds and ll refresh page (reload page after 10 sec)	
	}
});

function buildCI(){
	popup('buildCI', '', $('#tabDiv'));
}

function showCI() {
	$("#popup_div").empty();
    $("#showConfigure").empty();
    showPopup();
	popup('configure', '', $('#popup_div'));
    escPopup();
}

function setupProgress() {
	$('#loadingDiv').css("display","block");
	readerHandlerSubmit('setup', '<%= projectCode %>', '<%= FrameworkConstants.CI_SETUP %>');
}

function ProgressShow(prop) {
    $(".wel_come").show().css("display",prop);
    $('#build-outputOuter').show().css("display",prop);
}

function startJenkins() {
	$('#loadingDiv').css("display","block");
	readerHandlerSubmit('startJenkins', '<%= projectCode %>', '<%= FrameworkConstants.CI_START %>');
}

function stopJenkins() {
	$('#loadingDiv').css("display","block");
	readerHandlerSubmit('stopJenkins', '<%= projectCode %>', '<%= FrameworkConstants.CI_STOP %>');
}

function deleteCIJob(){
    showProgessBar("Deleting job", 100);
    var params = "";
	if (!isBlank($('form').serialize())) {
		params = $('form').serialize() + "&";
	}
	performAction('CIJobDelete', params, $("#tabDiv"));
}

function enableStart() {
    disableControl($("#startJenkins"), "btn disabled");
    enableControl($("#stopJenkins"), "primary btn");
}

function enableStop() {
    enableControl($("#startJenkins"), "primary btn");
    disableControl($("#stopJenkins"), "btn disabled");
}

//after build completion page should be refreshed
function refreshCI() {
	if(refreshCi == true) {
    	var params = "";
		if (!isBlank($('form').serialize())) {
			params = $('form').serialize() + "&";
		}
		performAction('getBuildsSize', params, '', true);
	}
}

function successRefreshCI(data) {
	console.log("successRefreshCI.....");
	if (data != null && !isBlank(data) && data > buildSize) {
		refreshCi == false;
        var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	showLoadingIcon($("#tabDiv")); // Loading Icon
		performAction('ci', params, $("#tabDiv"));
	} else {
		window.setTimeout(refreshCI, 15000); // wait for 15 sec
	}
}
//when background build is in progress, have to refresh ci page
function refreshBuild() {
	if(refreshCi != true) {
    	var params = "";
		if (!isBlank($('form').serialize())) {
			params = $('form').serialize() + "&";
		}
		performAction('getBuildProgress', params, '', true);
	}
}

function successRefreshBuild(data) {
	console.log("successRefreshBuild.....");
	if (data == false) { // build is not in progress , refresh the page
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	showLoadingIcon($("#tabDiv")); // Loading Icon
		performAction('ci', params, $("#tabDiv"));
	} else {
		window.setTimeout(refreshBuild, 15000); // wait for 15 sec
	}
}

//after configured , it ll take some time to start server , so we ll get no builds available , in that case have to refresh ci
function refreshAfterServerUp() {
	console.log("Normal Refresh....");
	// after configured job , jenkins will take some time to load. In that case after jenkins started(fully up and running), we have to enable this
// 	$("#warningmsg").show();
   	disableControl($("#configure"), "btn disabled");
   	disableControl($("#build"), "btn disabled");
   	disableControl($("#deleteJob"), "btn disabled");
   	$(".errorMsgLabel").text("Builds will be loaded shortly");
	if (isCiRefresh == false) {
		if ($("a[name='appTabs'][class='selected']").attr("id") == "ci" && $(".wel_come").css("display") == "none"){
	    	var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
	    	showLoadingIcon($("#tabDiv")); // Loading Icon
			performAction('ci', params, $("#tabDiv"));
		} else {
			$(".errorMsgLabel").text("No Builds Available");
		}
	} else {
		isCiRefresh = false;
		window.setTimeout(refreshAfterServerUp, 30000); // wait for 30 sec
	}
}

function successEvent(pageUrl, data) {
	if(pageUrl == "getBuildProgress") {
		successRefreshBuild(data);
	} else if(pageUrl == "getBuildsSize") {
		successRefreshCI(data);
	} else if(pageUrl == "checkForConfiguration") {
		successEnvValidation(data);
	}
}
</script>