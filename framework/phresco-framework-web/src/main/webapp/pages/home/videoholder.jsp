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
	            
	                    <a href="#" title="<%= videoName %>" oncontextmenu="localStorage.menuSelected = 'video';localStorage.index = <%= i %>;localStorage.videoname = '<%= videoName %>'">
		                    <div class="listindex" id="listindex<%= i %>">
		                        <div class="imgblock" id="<%= videoName %>" ><img src="<%=imageUrl%>"></div>
		                        <div class="vidcontent">
		                            <div class="vidcontent-title"><%= videoName%></div>
		                                <input type="hidden" name="videoName" value="<%= videoName%>"> 
		                        </div>
		                    </div>
	                    </a>
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
    	if (localStorage.index) {
    		$("div[id='listindex"+localStorage.index+"']").attr("class", "listindex-active"); //Highlight which video is selected.
    		changeVideo(localStorage.videoname); //To play the selected video.
    	} else {
    		$("div[id='listindex0']").attr("class", "listindex-active"); // To higlight intro video when you login after sign out.
    		changeVideo("<%= videoInfos.get(0).getName() %>");
    	}
        
        

        <%  int j = 0;
            for(VideoInfo videoDetail : videoInfos) {
        %>      
                $("a[title='<%= videoDetail.getName() %>']").click(function() {
                	$(".listindex-active").removeClass('listindex-active').addClass("listindex");
					<%-- $("div[title='<%= videoDetail.getName() %>']").attr("class", "listindex"); --%>
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