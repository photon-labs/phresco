<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="java.util.Iterator"%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="java.util.List" %>
<%@ page import="com.photon.phresco.model.VideoInfo" %>
<%@ page import="com.photon.phresco.model.VideoType" %>
<%@ page import="java.util.ArrayList"%>

<%
    List<VideoInfo> videoInfos = (List<VideoInfo>) request.getAttribute(FrameworkConstants.REQ_VIDEO_INFOS);
    String serverUrl = (String) request.getAttribute(FrameworkConstants.REQ_SERVER_URL);
%>
<!-- <script type="text/javascript" src="js/windowResizer.js">
</script> -->
<div class="video_height">
	 <div class='holder'>
	 		<div class="video-title">
	            <div class="video-title-left" id='video-title'><s:text name="label.phresco.video"/></div>
	            <div class="video-title-right" id=""><s:text name="label.playlist"/></div>
	        </div>
	                
	        <!-- Begin VideoJS -->
	        <div class="video-js-box" id="videoPlayer">
	            <!-- Download links provided for devices that can't play video in the browser. -->
	        </div>
	        <!-- End VideoJS -->
	        <div id='video-list-holder' class="video-list-holder">
	            <div class="listviews">
	            <%
	                String imageUrl = "";
	                String videoDesc = "";
	                String videoName = "";
	                int i = 0;
	                if (videoInfos != null) {
	                    for(VideoInfo videoDetail : videoInfos) {
	                        imageUrl = serverUrl + videoDetail.getImageurl();
	                        videoDesc = videoDetail.getDescription();
	                        videoName = videoDetail.getName();
	            %>
	            
	                    <a href="#" title="<%= videoName %>">
	                    <div class="listindex" id="listindex<%= i %>" title="imageDiv" >
	                        <div class="imgblock" id="<%= videoName %>" ><img src="<%=imageUrl%>"></div>
	                        <div class="vidcontent">
	                            <div class="vidcontent-title"><%= videoName%></div>
	                                <input type="hidden" name="videoName" value="<%= videoName%>"> 
	                        </div>
	                    </div></a>
	                    <% i++;
	                    }
	                } else { %>
	                    Services temporarily unavailable
	             <% } %>
	            </div>
	        </div>
	    </div>
    </div>

<script type="text/javascript">
	/* To check whether the device is ipad or not */
	if(!isiPad()){
		/* JQuery scroll bar */
		$("#video-list-holder").scrollbars();
	}
	
	<% if (videoInfos != null) { %>
    $(document).ready(function() {
        $("div[id='listindex0']").attr("class", "listindex-active");
        changeVideo("<%= videoInfos.get(0).getName() %>");

        <%  int j = 0;
            for(VideoInfo videoDetail : videoInfos) {
        %>      
                $("a[title='<%= videoDetail.getName() %>']").click(function() {
                    $("div[title='imageDiv']").attr("class", "listindex");
                    changeVideo('<%= videoDetail.getName() %>');
                    var selectedList = '#listindex' + '<%= j %>';
                    $(selectedList).attr("class", "listindex-active");
                });
        <% j++;
        } %>
    });
	<% } %>
	
	function changeVideo(videoId) {
        $.ajax({
            url : 'video',
            data : {
                'video' : videoId
            },
            type : "POST",
            success : function(data) {
                $("#videoPlayer").html(data);
            }
        });
    }
	
</script>