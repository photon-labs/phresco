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
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>

<%
	String error = (String) request.getAttribute(FrameworkConstants.REQ_ERROR);
	
	String sonarPath = "";
	
	if(error != null) {
%>
        <div class="alert-message block-message warning" >
			<center><label Class="errorMsgLabel"><%= error %></label></center>
		</div>
        
   <% } else { 
		sonarPath = (String) request.getAttribute(FrameworkConstants.REQ_SONAR_PATH);
   %>
		 <iframe src="" frameBorder="0" class="iframe_container"></iframe>
		
	<% } %>
	
<script>
	var localstore = $("link[title='phresco']").attr("href");
	localStorage["color"] =localstore;
	
	$(document).ready(function() {
	    reloadIframe();
	    $(".styles").click(function() {
	        reloadIframe();
	    });
	});
	
	function reloadIframe() {
		var theme = localStorage["color"];
	    if(theme == null || theme == undefined || theme == "undefined" || theme == "null" || theme == "themes/photon/css/red.css") {
	         theme = "themes/photon/css/red.css";
	    }
	    var source = "<%= sonarPath %>?css=" + theme;
	    
	    $("iframe").attr({
	        src: source
	    });
	    $('iframe').load(function() {
	        $(".loadingIcon").hide();
	    });
	}
</script>