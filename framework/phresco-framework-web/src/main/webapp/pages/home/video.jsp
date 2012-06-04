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
<%@ page import="java.util.List" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.VideoInfo" %>
<%@ page import="com.photon.phresco.model.VideoType" %>

<script type="text/javascript">
	$(document).ready(function() {
		VideoJS.setupAllWhenReady();
	});
</script>
<%
	List<VideoType> videoTypes = (List<VideoType>) request.getAttribute(FrameworkConstants.REQ_VIDEO_TYPES);
	String serverUrl = (String) request.getAttribute(FrameworkConstants.REQ_SERVER_URL);
%>

<video id="video" controls  preload="none" class="video-js" width="440" height="315">
	<%
		String url = "";
		String vType = "";
		String codecs = "";
		for(VideoType videoType : videoTypes) {
			url = serverUrl + videoType.getUrl();
			vType = videoType.getType();
			codecs = videoType.getCodecs();
	%>
	
		<source id="video" type='video/<%= vType %>' src="<%= url %>"></source>
		<% }%>
	
</video>