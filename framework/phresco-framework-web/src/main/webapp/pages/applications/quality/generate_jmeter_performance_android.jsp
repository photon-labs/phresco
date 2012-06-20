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
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>

<%@ include file="..\progress.jsp" %>

<script src="js/reader.js" ></script>
<script type="text/javascript" src="js/home-header.js" ></script>

<style type="text/css">
   	table th {
		padding: 0 0 0 9px;
	}
</style>

<%
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	String selectedTestType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE_SELECTED);
	ArrayList<String> connAndroidDevices = (ArrayList<String>) request.getAttribute(FrameworkConstants.REQ_ANDROID_CONN_DEVICES);
%>

<div class="popup_Modal" id="performance-popup">
	<form action="perTest">
		<div class="modal-header">
			<h3><s:text name="label.performance.test"/></h3>
			<a class="close" href="#" id="close">&times;</a>
		</div>

		<div class="modal-body perModalBody" style="height: 325px;">
			<% if (connAndroidDevices == null || connAndroidDevices.size() <= 0) { %>
					<div class="alert-message block-message warning" >
						<center><label Class="errorMsgLabel"><s:text name="label.no.device.found"/></label></center>
					</div>
			<%	} else { %>
					<div class="fixed-table-container" style="height: 88%;">
			      		<div class="header-background"></div>
			      		<div class="fixed-table-container-inner validatePopup_tbl">
					        <table cellspacing="0" class="zebra-striped">
					          	<thead>
						            <tr>
										<th class="first" style="width: 1%;">
						                	<div class="th-inner">
						                		<input type="checkbox" id="selectAll"/>
						                	</div>
						              	</th>
						              	<th class="second">
						                	<div class="th-inner"><s:text name="label.connected.devices"/></div>
						              	</th>
						            </tr>
					          	</thead>
		
					          	<tbody>
						          	<% for (String device : connAndroidDevices) { %>
						            	<tr>
						              		<td style="background-color: transparent;">
						              			<input type="checkbox" class="check" name="device" value="<%= device %>"/>
						              		</td>
						              		<td style="color: #000000; background-color: transparent;">
						              			<%= device.substring(0,1).toUpperCase() + device.substring(1) %>
						              		</td>
						            	</tr>
									<% } %>
					          	</tbody>
					        </table>
			      		</div>
		    		</div>
	    	<% } %>
		</div>

		<div class="modal-footer">
			<div class="action popup-action">
				<ul class="inputs-list" style="width: auto; float: left;">
					<li class="popup-li"> 
						<input type="checkbox" id="showError" name="showError" value="showerror" >
						<span class="textarea_span popup-span"><s:text name="label.show.error"/></span>
						<input type="checkbox" id="hideLog" name="hideLog" value="hidelog" >
						<span class="textarea_span popup-span"><s:text name="label.hide.log"/></span>
						<input type="checkbox" id="showDebug" name="showDebug" value="showdebug">
						<span class="textarea_span popup-span"><s:text name="label.show.debug"/></span>
					</li>
				</ul>	
				<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
				<input type="button" id="actionBtn" class="btn disabled" value="<s:text name="label.test"/>" disabled="disabled">
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		// add multiple select / deselect functionality
	    $("#selectAll").click(function () {
			$('.check').attr('checked', this.checked);
			if($('#selectAll').is(':checked')){
				enableTestBtn();
			} else {
				disableTestBtn();
			}
	    });

	    // if all checkbox are selected, check the selectAll checkbox
	    // and viceversa
	    $(".check").click(function() {
			if($(".check").length == $(".check:checked").length) {
	            $("#selectAll").attr("checked", "checked");
	        } else {
	            $("#selectAll").removeAttr("checked");
	        }
			
			if($('.check').is(':checked')) {
				enableTestBtn();
			} else {
				disableTestBtn();
			}
	    });
		
	    disableScreen();
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});
		
		$("#actionBtn").click(function() {
			$("#build-output").empty();
			$('#performance-popup').hide();
			disableScreen();
			$('#build-outputOuter').show();
			readerHandlerSubmit('perTest', '<%= projectCode %>', '<%= FrameworkConstants.PERFORMACE %>');
		});
	});
	
	function showParentPagePer() {
		$(".wel_come").show().css("display","none");
      	$('.popup_div_per').show().css("display","none");
	}
	
	function enableTestBtn() {
		enableControl($('#actionBtn'), "btn primary");
	}
	
	function disableTestBtn() {
		disableControl($('#actionBtn'), "btn disabled");
	}
</script>