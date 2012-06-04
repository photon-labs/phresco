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