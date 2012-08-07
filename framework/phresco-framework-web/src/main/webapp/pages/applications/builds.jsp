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
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.util.Utility" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.commons.BuildInfo" %>
<%@ page import="com.photon.phresco.framework.commons.NodeJSUtil" %>
<%@ page import="org.apache.commons.collections.MapUtils" %>

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
</style>

<%
	String technology = null;
	Boolean popup = Boolean.FALSE;
	
    List<BuildInfo> buildInfos = (List<BuildInfo>) request.getAttribute(FrameworkConstants.REQ_BUILD);
    Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
    String projectCode = project.getProjectInfo().getCode();
	ProjectInfo selectedInfo = project.getProjectInfo();
	technology =selectedInfo.getTechnology().getId();
	
	if (TechnologyTypes.ANDROIDS.contains(project.getProjectInfo().getTechnology().getId())) {
		popup = Boolean.TRUE;
	}
%>

<% if (buildInfos == null || buildInfos.size() == 0) { %>
	<div class="alert-message block-message warning" style="margin-top: 5px">
	    <center><s:label key="configuration.info.message" cssClass="errorMsgLabel"/></center>
	</div>
<% } else { %>
		<div class="build_table_div">
			<div class="fixed-table-container">
	      		<div class="header-background"> </div>
	      		<div class="fixed-table-container-inner">
			        <table cellspacing="0" class="zebra-striped">
			          	<thead>
				            <tr>
								<th class="first">
				                	<div class="th-inner-head ">
				                		<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto">
				                	</div>
				              	</th>
				              	<th class="second">
				                	<div class="th-inner-head ">#</div>
				              	</th>
				              	<th class="third">
				                	<div class="th-inner-head "><s:text name="label.date"/></div>
				              	</th>
				              	<th class="third">
				                	<div class="th-inner-head "><s:text name="label.download"/></div>
				              	</th>
				              	<% if (!(TechnologyTypes.NODE_JS_WEBSERVICE.equals(technology) || TechnologyTypes.JAVA_STANDALONE.contains(technology))) { %>
					              	<th class="third">
					                	<div class="th-inner-head ">
					                		Deploy
					                	</div>
					              	</th>
				              	<% } %>
				            </tr>
			          	</thead>
			
			          	<tbody>
			          	<%
			          		for (BuildInfo buildInfo : buildInfos) {
						%>
			            	<tr>
			              		<td class="checkbox_list">
			              			<input type="checkbox" class="check" name="build-number" value="<%= buildInfo.getBuildNo() %>">
			              		</td>
			              		<td><%= buildInfo.getBuildNo() %></td>
			              		<td style="width: 40%;">
			              				<% if (TechnologyTypes.JAVA_STANDALONE.contains(technology)) { %>
			              					<label class="bldLable"><%= buildInfo.getTimeStamp() %></label>
			              				<% } else { %>
			              					<label class="bldLable" title="Configured with <%= buildInfo.getEnvironments() %>"><%= buildInfo.getTimeStamp() %></label>
			              				<% } %>	
			              		</td>
			              		<td>
			              			<a href="<s:url action='downloadBuild'>
					          		     <s:param name="buildNumber"><%= buildInfo.getBuildNo() %></s:param>
					          		     <s:param name="projectCode"><%= projectCode %></s:param>
					          		     </s:url>"><img src="images/icons/download.png" title="<%= buildInfo.getBuildName()%>"/>
		                            </a>
		                            
		                            <% 
		                            	boolean createIpa = false;
		                            	boolean deviceDeploy = false;
		                            	if (TechnologyTypes.IPHONES.contains(technology)) {
		                            		createIpa = MapUtils.getBooleanValue(buildInfo.getOptions(), "canCreateIpa");
		                            		deviceDeploy = MapUtils.getBooleanValue(buildInfo.getOptions(), "deviceDeploy");
		                            		if (createIpa && deviceDeploy)  {
		                            %>
		                                <a href="<s:url action='downloadBuildIpa'> 
		                                  <s:param name="buildNumber"><%= buildInfo.getBuildNo() %></s:param>
                                          <s:param name="projectCode"><%= projectCode %></s:param>
                                          </s:url>"><img src="images/icons/downloadipa.jpg" title="ipa Download"/>
                                    </a>
                                    <% 		} 
		                            	}
		                            %>     
			              		</td>
			              		<td>
			              			<% if (TechnologyTypes.NODE_JS_WEBSERVICE.equals(technology)) { %>
										<!-- By default disable all Run buttons under builds -->
				       	  				<!-- <input type="button" value="Run" id="<%= buildInfo.getBuildNo() %>" name="<%= buildInfo.getBuildNo() %>" class="btn disabled" disabled="disabled" onClick="startNodeJS(this);"> -->
				       	  			<% } else if (TechnologyTypes.ANDROIDS.contains(technology)) { %>
		                                <a id="buildNumberHref#<%= buildInfo.getBuildNo() %>" href="#" value="<%= buildInfo.getBuildNo() %>" onClick="deployAndroid(this);">
		                                    <img src="images/icons/deploy.png" />
		                                </a>
		                            <% } else if (TechnologyTypes.IPHONES.contains(technology) && createIpa && deviceDeploy) { %>
		                                <a id="buildNumberHref#<%= buildInfo.getBuildNo() %>" href="#" value="<%= buildInfo.getBuildNo() %>" onClick="deploy(this);">
		                                 <img src="images/icons/deploy.png" />  
		                                </a>    
		                            <% } else if (TechnologyTypes.IPHONES.contains(technology)) { %>
		                                <a id="buildNumberHref#<%= buildInfo.getBuildNo() %>" href="#" value="<%= buildInfo.getBuildNo() %>" onClick="deployIphone(this);">
		                                 <img src="images/icons/deploy.png" />  
		                                </a>   
		                            <% } else if (!TechnologyTypes.JAVA_STANDALONE.contains(technology)) { %>
				       	  				<a id="buildNumberHref#<%= buildInfo.getBuildNo() %>" href="#" value="<%= buildInfo.getBuildNo() %>" onClick="generateBuild('<%= projectCode %>', 'deploy', this);">			       	  				
				       	  					<img src="images/icons/deploy.png" />
				       	  				</a>
				       	  			<% } %>
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
	<% } %> 
<script src="js/delete.js" ></script>

<script type="text/javascript">
	/* To check whether the divice is ipad or not */
	if(!isiPad()){
		/* JQuery scroll bar */
		$(".fixed-table-container-inner").scrollbars();
	}
	
	// By default disable all Run buttons under builds
    $(".nodejs_startbtn").attr("class", "btn disabled");
    $(".nodejs_startbtn").attr("disabled", true);
	
    if (<%= TechnologyTypes.NODE_JS_WEBSERVICE.equals(technology) %>) {
        $('.build_td4').css("width", "18.5%");  
    }
    
    function startNodeJS(obj) {
		$("#build-output").empty();
		$("#build-output").html("Server is starting...");  
        var params = "buildNumber=";
        params = params.concat(obj.id);
        readerHandlerSubmit('startNodeJSServer', '<%= projectCode %>', '', params, true);
    }
    
    function stopNodeJS() {
    	$("#build-output").empty();
    	$("#build-output").html("Server is stopping...");
    	$("#stopbtn").attr("class", "btn disabled");
       	$("#restartbtn").attr("class", "btn disabled");
		readerHandlerSubmit('stopNodeJSServer', '<%= projectCode %>', '<%= FrameworkConstants.REQ_READ_LOG_FILE %>', '', true);
    }
    
    function restartNodeJS() {
		$("#build-output").empty();
    	$("#build-output").html("Server is restarting...");
      	$("#stopbtn").attr("class", "btn disabled");
       	$("#restartbtn").attr("class", "btn disabled");
        readerHandlerSubmit('restartNodeJSServer', '<%= projectCode %>', '<%= FrameworkConstants.REQ_READ_LOG_FILE %>', '', true);
    } 

</script>