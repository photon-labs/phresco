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

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.model.LogInfo" %>

	<%-- <style type="text/css">
		.errorMessage li{ 
			list-style: none;
			color:#000000;
			text-align: left;
		}
	</style> --%>

	<% 
		LogInfo log = (LogInfo)request.getAttribute(FrameworkConstants.REQ_LOG_REPORT); 
		if (log != null) {
	%>
	
<script>
	$(".exceptionErrors").css("top", "44%");
	$(".errorReportTrace").css("height", "165px");
	$('#performance-popup').css("display", "none");
	$('#generateBuild_Modal').hide();
	$('.popup_div').css("z-index", "60000");

	$(document).ready(function() {
		$('#closeAboutDialog').click(function(){
			errorReportEnable('none');
		});
		
		$('#clipboard').click(function(){
			copyToClipboard($('#trackTrace').text());
		});
		
		$('#submitReport').click(function(){
			var errorOrigin = $('.errorMessage li span').html();
			var errorMessage = $('#message').html();
			var errorTrace =  $('#trace').html();
			$.ajax({
				url : 'sendReport',
	            data : {
	                'message' : errorMessage,
	                'trace' : errorTrace,
	                'action' : "<%= log.getAction() %>",
	                'userid' : "<%= log.getUserId() %>",
	            },
	            type : "POST",
	            success : function(data) {
	            	$('#reportMsg').html(data.reportStatus);
	            	$("#submitReport").attr("class", "btn disabled");
	                $("#submitReport").attr("disabled", true);
	            }
			});
		});
		
		$('#submitReportCancel').click(function(){
			errorReportEnable('none');
		});
		
// 		$('#trackTraceDisplay').click(function(){
// 			$("#trackTrace").css("height", "0px");
// 			$("#errorReportTrace").next().slideToggle();
// 			$("#trackTrace").next().animate({height: 'toggle'});
// 		});
		
		
	});

	function errorReportEnable(prop) {
		$("#errorDialog").css("display", prop);
		$(".errorOverlay").css("display", prop);
		$('#build-outputOuter').css("display", prop);
		$('.popup_div').css("display", prop);
		$('.wel_come').css("display", prop);
		// for build
		$('.build_cmd_div').css("display", prop);
		$('#warningmsg').css("display", "none");
	}
</script>

	<!-- Error dialog starts -->	
	<div id="errorDialog" class="modal exceptionErrors">
		<% if (log != null) { %>
			<script type="text/javascript">
				errorReportEnable('block');
			</script>
		<% } %>
		
		<div id="versionInfo">
			<div class="modal-header">
				<h3><s:text name="label.phresco.alert"/><span id="version"></span></h3>
				<a id="closeAboutDialog" class="close" href="#" id="close">&times;</a>
			</div>
			
			<div class="modal-body abt_modal-body errorReportContainer">
				<div class="abt_logo errLogo">
					<img src="images/crashReport.png" alt="logo" class="abt_err_img">
				</div>
				<div class="abt_content errorMsgDisplay">
<%-- 					<a id="trackTraceDisplay" style="color:#000000;"><%= log.getErrorMessage() %></a> --%>
					<%= log.getErrorMessage() %>
				</div>
				<div class="clipboard" style="position:absolute; right:10px; top:35px;">
                    <img src="images/icons/clipboard.png" alt="clipboard" id="clipboard" style= "height:25px; width:25px; cursor:pointer;" title="Copy to clipboard"> 
				</div>
				<div style="display:none;" id="errorOrigin">
					<s:if test="hasActionErrors()">
							<s:actionerror />
					</s:if>
				</div>
				<div style="display:none;" id="message">
						<%= log.getMessage() %>
				</div>
				<div style="display:none;" id="trace">
						<%= log.getTrace() %>
				</div>
			</div>
			
			<div class="modal-body abt_modal-body errorReportTrace" style="color: #000000;">
				<div id="trackTrace"><%= log.getTrace() %></div>
			</div>
			
			<div class="modal-footer">
				<div class="errMsg" id="reportMsg"></div>
				<div class="action abt_action">
					<input type="button" class="btn primary" value="<s:text name="label.sent.report"/>" id="submitReport">
					<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="submitReportCancel">
				</div>
			</div>
		</div>
	</div>
	<!-- Error dialog ends -->
	
	<% } %>