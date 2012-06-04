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

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>

<script src="js/reader.js" ></script>

<%
	Project project = (Project) request.getAttribute(FrameworkConstants.REQ_PROJECT);
	String projectCode = (String)project.getProjectInfo().getCode();
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
%>
<div id="tests" style="display:block">
<form action="unit" method="post" autocomplete="off" class="build_form">
	<div class="popup_Modal" id="generateTest">
		<div class="modal-header">
			<h3><s:text name="label.unit.test"/></h3>
			<a class="close" href="#" id="close">&times;</a>
		</div>
		
		<div class="modal-body">
            <% if (CollectionUtils.isNotEmpty(projectModules)) {  %>
            <div id="agnBrowser" class="build server">
				<!-- Modules -->
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.modules"/></label>
					<div class="input">
						<select id="testModule" name="testModule" class="xlarge" >
						 <%
						       for(String projectModule : projectModules) {
						 %>
								<option value="<%= projectModule%>"> <%= projectModule %></option>
						 <% } %>
						</select>
					</div>
				</div>
            </div>
            <% } %>
		</div>
		
		<div class="modal-footer">
		    <div class="action popup-action">
		    	<div id="errMsg" style="width:72%; text-align: left;"></div>
	        	<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
	        	<input type="button" id="test" class="btn primary" value="<s:text name="label.test"/>">
	        </div>
		</div>
	</div>
</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});

		$('#test').click(function() {
			$('#tests').show().css("display","none");
		    $('#build-outputOuter').show().css("display","block");
		    getCurrentCSS();
		    readerHandlerSubmit('unit', '<%= projectCode %>', '<%= FrameworkConstants.UNIT %>', '');
		});
		
// 		showPopup();
	});
</script>