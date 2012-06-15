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
<style>
<!--
.alert-message.block-message {
	margin: 45px 5px;
}
-->
</style>

<%	
	String error = (String) request.getAttribute(FrameworkConstants.REQ_ERROR);
	String jForumUrl = ""; 
%>
<div class="page-header"><h1><s:text name="label.phresco.forum"/></h1></div>
<%
	if(error != null ) {
%>
    <div class="alert-message block-message warning" >
		<center><%= error %></center>
	</div>
<%
	} else {
		 jForumUrl = (String) request.getAttribute(FrameworkConstants.REQ_JFORUM_URL);
%>
	<iframe src="<%= jForumUrl %>" frameBorder="0" class="iframe_container_forum"></iframe>
<%
	}
 %>

<script>
	var localstore = $("link[title='phresco']").attr("href");
	localStorage["color"] =localstore;
	
    $("a[id='home']").attr("class", "inActive");
    $("a[id='forum']").attr("class", "active");

	$(document).ready(function() {
		reloadIframe();
		$(".styles").click(function() {
			reloadIframe();
		});
	});
	
	function reloadIframe() {
		var theme = localStorage["color"];
		if(theme == undefined) {
			 theme = "themes/photon/css/red.css";
		}
		var source = "<%= jForumUrl %>&css=" + theme;
		$("iframe").attr({
            src: source
        });
		$('iframe').load(function() {
				$(".loadingIcon").hide();
		});
	}

</script>