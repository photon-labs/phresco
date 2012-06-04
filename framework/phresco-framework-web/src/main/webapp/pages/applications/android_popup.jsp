<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.AndroidConstants"%>

<%@include file="progress.jsp" %>
<%
	String buildNumber = (String) request.getAttribute(FrameworkConstants.REQ_DEPLOY_BUILD_NUMBER);
	String fromTab = (String) request.getAttribute(FrameworkConstants.REQ_FROM_TAB);
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String testType = (String)request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
%>
<!-- This popup is for deploy and Test-->
<div id="tests">
	<form action="" method="post" autocomplete="off" class="build_form">
		<div class="popup_Modal">
			<div class="modal-header">
				<h3><%= fromTab %></h3>
				<a class="close" href="#" id="close">&times;</a>
			</div>
			<!-- For build alone starts-->
			<input name="<%= FrameworkConstants.REQ_DEPLOY_BUILD_NUMBER %>" type="hidden" value="<%= buildNumber %>"/>
			<!-- For build alone ends -->
			<div class="modal-body">
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.device"/></label>
					<div class="input">
						<select id="device" name="device" class="xlarge">
							<option value="usb">USB</option>
							<option value="emulator">Emulator</option>
							<option value="serialNumber">Serial Number</option>
						</select>
					</div>
				</div>
				
				<!-- Android Version -->
<!-- 				<div class="clearfix"> -->
<!-- 					<label for="xlInput" class="xlInput popup-label">SDK</label> -->
<!-- 					<div class="input"> -->
<!-- 						<select id="androidVersion" name="androidVersion" class="xlarge" > -->
<%-- 							<% --%>
<%-- 							for (int i=0; i<AndroidConstants.SUPPORTED_SDKS.length; i++) { --%>
<%-- 							%> --%>
<%-- 								<option value="<%= AndroidConstants.SUPPORTED_SDKS[i] %>"><%= AndroidConstants.SUPPORTED_SDKS[i] %></option> --%>
<%-- 							<% } %> --%>
<!-- 						</select> -->
<!-- 					</div> -->
<!-- 				</div> -->
				
				<div class="clearfix" id="serialNo_div">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.sreialno"/></label>
					<div class="input">
						<input type="text" id="serialNum" name="serialNumber" class="xlarge deploy_xlarge">	
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				<div class="action popup-action">
					<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
					<input type="button" id="actionBtn" class="btn primary" value="<%= fromTab %>">
				</div>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	$('#serialNo_div').hide();
	$(document).ready(function(){
		$('#close, #cancel').click(function() {
			showParentPage();
		});

		$('#actionBtn').click(function() {
			$("#build-output").empty();
			$('#tests').show().css("display","none");
			$('#build-outputOuter').show().css("display","block");
			if($('#actionBtn').val() == "Test"){
				getCurrentCSS();
				readerHandlerSubmit('<%= testType%>', '<%= projectCode %>', '<%= testType%>');
				//showParentPage();
			}else if($('#actionBtn').val() == "Deploy"){
				readerHandlerSubmit('deploy', '<%= projectCode %>', '<%= FrameworkConstants.REQ_FROM_TAB_DEPLOY %>');
				showParentPage();
			}
		});
		
		$('#device').change(function() {
			if($('#device').val() == "serialNumber"){
				$('#serialNo_div').show();
			}else{
				$('#serialNo_div').hide();
			}
			
		});
		
		showPopup();
		
	});
</script>