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

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>

<div class="popup_Modal testCmdLine" id="build-outputOuter">
	<form name="testSuiteCall" action="functional" method="post" id="testSuiteCall" class="marginBottomZero">
		<div class="modal-header repo_modal_header">
		    <h3><s:text name="label.progress"/></h3>
		    <img src="images/icons/clipboard-copy.png" alt="clipboard" id="clipboard" 
		    	class="close progressClipboard" title="Copy to clipboard"/>
		    <a class="close" href="#" id="closeGenerateTest">&times;</a>
		</div>
	</form>
    
    <div id="build-output" class="testCmdLineInner">
 
    </div>
    
    <div class="modal-footer">
	    <div class="action popup-action">
	    	<div style="float: left; display: block;" id="loadingDiv">
				<img src="" class="popupLoadingIcon">
				<!-- <span style="display: block;text-align: right; margin-top: -26px; margin-left: 37px;">Progressing... </span> -->
	    	</div>
	     	<input type="button" class="btn primary" value="<s:text name="label.close"/>" id="closeGenTest">
	    </div>
	</div>
</div>

<script>
	var popupHeight = -(($(".popup_Modal").height()/2)-4);
	$(".popup_Modal").css("margin-top", popupHeight +"px");
	escPopup();
	function closePopup() {
		$(".wel_come").show().css("display","none");
	   	$("#build-outputOuter").hide();
	}
	
	$('#performance-popup').css("margin-top", "-300px");
	
	$('#clipboard').click(function(){
		copyToClipboard($('#build-output').text());
	});
	
	function copyToClipboard(data) {
        var params = "copyToClipboard=";
        params = params.concat(data);
        performAction('copyToClipboard', params, '');
	}
</script>