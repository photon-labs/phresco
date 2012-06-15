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
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.BuildInfo"%>
<%@ page import="com.photon.phresco.util.XCodeConstants" %>
<%@ page import="com.photon.phresco.framework.commons.ApplicationsUtil"%>
<%@ page import="com.photon.phresco.framework.commons.PBXNativeTarget"%>
<%@ include file="progress.jsp" %>
<%
	// For deploy
    String buildNumber = (String) request.getAttribute(FrameworkConstants.REQ_DEPLOY_BUILD_NUMBER);
    String fromTab = (String) request.getAttribute(FrameworkConstants.REQ_FROM_TAB);
    String projectCode = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	// For test
    String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
    List<BuildInfo> buildInfos = (List<BuildInfo>) request.getAttribute(FrameworkConstants.REQ_TEST_BUILD_INFOS);
   	//xcode targets
   	List<PBXNativeTarget> xcodeConfigs = (List<PBXNativeTarget>) request.getAttribute(FrameworkConstants.REQ_XCODE_CONFIGS);
   	// mac sdks
   	List<String> macSimulatorSdkVersions = (List<String>) request.getAttribute(FrameworkConstants.REQ_IPHONE_SIMULATOR_SDKS);
   	List<String> macSdks = (List<String>) request.getAttribute(FrameworkConstants.REQ_IPHONE_SDKS);
%>
<div id="tests">
    <form action="" method="post" autocomplete="off" class="build_form">
        <div class="popup_Modal">
            <div class="modal-header">
                <h3><%= fromTab%></h3>
                <a class="close" href="#" id="close">&times;</a>
            </div>
<!--             iphone deploy popup -->
        	<% 
        		if(!fromTab.equals("Test")) { 
        			   	//Show deployto device field
   					boolean showDeployToDevice = (Boolean) request.getAttribute(FrameworkConstants.REQ_HIDE_DEPLOY_TO_DEVICE); 
    			   		//Show deployto simulator field
					boolean showDeployToSimulator = (Boolean) request.getAttribute(FrameworkConstants.REQ_HIDE_DEPLOY_TO_SIMULATOR);
        	%>
	            <div class="modal-body">
			       	<div class="clearfix">
			       		<% if (showDeployToSimulator) { %>
						<div class="popup-input">
							<div class="multipleFields emaillsFieldsWidth">
								<div class="popup-label popup-lbl-style"><input type="radio" name="deployTo" value="simulator" checked="checked">&nbsp; <s:text name="label.simulator"/></div>
							</div>
							<div class="multipleFields">
								<div>
									<select id="simulatorVersion" name="simulatorVersion" class="medium">
										<%
										    if (macSimulatorSdkVersions != null) {
										    	for (String simulatorVersion : macSimulatorSdkVersions) {
										%>
										     <option value="<%= simulatorVersion %>"><%= simulatorVersion %></option>
										<%
										    	}
										    }
										%>
									</select>
								</div>
							</div>
						</div>
						<% } else if (showDeployToDevice) { %>
						<div class="popup-input">
							<div class="multipleFields emaillsFieldsWidth">
								<div class="popup-label popup-lbl-style"><input type="radio" name="deployTo" value="device" checked> &nbsp;<s:text name="label.deployto.device"/></div>
							</div>
							<div class="multipleFields">
								<div></div>
							</div>
						</div>
						<% } %>
					</div>
				</div>
<!-- 				iphone unit popup -->
            <% } else if(FrameworkConstants.UNIT.equals(testType)) { %>
        	
				<div class="modal-body">
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
				</div>
<!--         	iphone functional popup -->
            <% } else { %>
            
            	<div class="modal-body">
	                <div class="clearfix">
	                    <label for="xlInput" class="xlInput popup-label"><s:text name="label.buil.id"/></label>
	                    <div class="input">
							<select id="buildId" name="<%= FrameworkConstants.REQ_TEST_BUILD_ID %>" class="xlarge">
							   <%
							       for(BuildInfo buildInfo : buildInfos) {
							   %>
									<option value="<%= buildInfo.getBuildNo()%>"> <%= buildInfo.getBuildNo() %></option>
							   <% } %>
								  
							</select>
	                    </div>
	                </div>
				</div>
            
            <% } %>
            <div class="modal-footer">
                <div class="action popup-action">
                    <input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
                    <input type="button" id="actionBtn" class="btn primary" value="<%= fromTab%>">
                    <div id="errMsg" class="envErrMsg" style="width: 72%;"></div>
                </div>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
    $(document).ready(function() {
    	showPopup();
    	
        $('#close, #cancel').click(function() {
            showParentPage();
        });

        $('#actionBtn').click(function() {
            $("#build-output").empty();
            $('#tests').show().css("display","none");
            $('#build-outputOuter').show().css("display","block");
			if($('#actionBtn').val() == "Test") {
				getCurrentCSS();
				iphoneAction('<%= testType%>', '<%= testType%>');
			} else if($('#actionBtn').val() == "Deploy") {
				iphoneAction('deploy', '<%= FrameworkConstants.REQ_FROM_TAB_DEPLOY %>');
				showParentPage();
			}
        });
        
        $('input:radio[name=deployTo][value=simulator]').click(function() {
        	$("#errMsg").html("");
        });
        $('input:radio[name=deployTo][value=device]').click(function() {
        	$("#errMsg").html("Project will be deployed to device, which is connected first.");
        });
       });

    function iphoneAction(urlAction, readerHandlerValue) {
    	var params = '<%= FrameworkConstants.REQ_DEPLOY_BUILD_NUMBER%>';
    	params = params.concat("=");
    	params = params.concat('<%=buildNumber %>');
    	readerHandlerSubmit(urlAction, '<%= projectCode %>', readerHandlerValue, params);
    }
</script>