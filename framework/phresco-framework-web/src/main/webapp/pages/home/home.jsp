<!--saravana kumar -->
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
    String showWelcome = (String)request.getAttribute(FrameworkConstants.REQ_SHOW_WELCOME);
    if(showWelcome == null || !showWelcome.equals("block")){
        showWelcome = "none";
    }
%>

<div class="intro_container">
<!-- Phresco js -->
<script type="text/javascript" src="../../js/phresco/common.js"></script>

<!-- 
<script type="text/javascript">

<!--
if(localStorage.welcome) {
    $(".errorOverlay").show().css("display","none");
    $(".intro_container").show().css("display","none");
 } else {
     $(".errorOverlay").show().css("display","<%= showWelcome %>");
     $(".intro_container").show().css("display","<%= showWelcome %>");
 }
-->

<script type="text/javascript">

	if(localStorage["welcome"]) {
		$(".errorOverlay").show().css("display","none");
		$(".intro_container").show().css("display","none");
	 }else if (localStorage.menuSelected == 'video'){// To hide the welcome overlay page.
		 $(".errorOverlay").show().css("display","none");
		$(".intro_container").show().css("display","none"); 
	 }else {
		 $(".errorOverlay").show().css("display","<%= showWelcome %>");
		 $(".intro_container").show().css("display","<%= showWelcome %>");
	}

</script>
    <div class="intro_container_left">
        <h1>Welcome to <span class="hed_red">Phres</span>co<span class="hed_gray">.com</span></h1>
        <p class="p_align">
            The Next Generation Internet Accelerating Innovation.We have traversed across ecommerce 
            and dynamic website design in the early days to Web2.0, Social Media and Mobile applications 
            in recent times. Now we are stepping into Advance generation of Frame work architecture called 
            PHRESCO embedded with all platforms and their corresponding technologies. PHRESCO will guide
            each and every individual to follow their own quality measure and standards as specified.
        </p>
        <p class="p_align">
            The main idea of PHRESCO is to form a basic structure for projects which are newly created.
            Phresco will be having the basic standards sample code and basic sample quality standards 
            documentation which will consume less time in creating basic need for a  project  which in 
            turn will increase the productivity of the project.
        </p>
        <p class="p_align">
            We at PHRESCO have produced amazing work, the best of which have been handpicked to give you
            a hint into our expertise over the years.
        </p>
        <div class="intro_container_bottom_text">Explore and Experience our universe at Phresco Photon </div>
        
        <div class="homegostart">
        	<div class="gohomebut">
        		<div class="gohometexts"><a href="JavaScript:void(0);" id="home_dis">Go To Phresco Home </a></div>
        		<div class="gohomebutfade"></div>
        	</div>
        </div>
        
        <div class="intro_container_left_feed">
            <p>Please get back to us at <a href="mailto:webmaster@photon.in">webmaster@photon.in</a></p>
            <p><span><input type="checkbox" name="dontShow" id="dontShowCheck" /></span> Please don't show this message again</p>
        </div>
    </div>
<%
	String selectedNav = "home";
	if(request.getAttribute(FrameworkConstants.REQ_SELECTED_MENU) != null) {
		 selectedNav = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_MENU);
	}
%>
    <div class="intro_container_right">
        <h1>How to navigate through <span class="hed_red">Phres</span>co<span class="hed_gray">.com</span></h1>
        <p>
            In order to help you navigate through the site we have here a
           small and easy help section that guides you on how to use this site.
        </p>
        <div class="intro_container_right_icons">
            <ul>
                <li><a href="#" class="inactive" name="navMenu" id="applications">
					<div class="linkbox">
						<div class="inbox" style="text-transform: uppercase;"><s:text name="label.appln"/></div>
					</div>
				</a></li>
				<li><a href="#" class="inactive" name="navMenu" id="settings">
					<div class="linkbox">
						<div class="inbox" style="text-transform: uppercase;"><s:text name="label.hdr.settings"/></div>
					</div>
				</a></li>
				<li><a href="#"  class="inactive" name="navMenu" id="forum">
					<div class="linkbox">
						<div class="inbox" style="text-transform: uppercase;"><s:text name="label.help"/></div>
					</div>
				</a></li>
			</ul>
        </div>
    </div>
</div>

<%
    String serverUrl = (String) request.getAttribute(FrameworkConstants.REQ_SERVER_URL);
%>

<div id="container" class="container">
   
</div>

<img class="loadingIcon">

<script type="text/javascript">
$(document).ready(function() {
	bacgroundValidate("validateFramework", '');
	var params = "fromPage=";
	params = params.concat("home");
	performAction('home', params, $("#container"));
	$(".mycube_slides").show().css("border","none");
    // for navigation to page
    $("a[id='<%= selectedNav%>']").attr("class", "active"); 

    $("#dontShowCheck").click(function() { 
         if ($(this).is(":checked"))
        	 localStorage.welcome = no;
    });
 
    $(".homegostart").click(function () {
        $(".intro_container").slideUp(3000);
        setInterval(function() {
            $(".mycube_slides").show().css("border","block");  
        }, 3000);
        $(".errorOverlay").show().css("display", "none");
    });
    
	// for navigation to page
    $("a[name='navMenu']").click(function() {
		$("a[name='navMenu']").attr("class", "inactive");
		$(this).attr("class", "active");
        var selectedNav = $(this).attr("id");
        if (selectedNav == "applications") {
        	bacgroundValidate("validateFramework", "true");
        }
		performAction(selectedNav, '', $("#container"));
    });
	
 	// shows preloader until page loads
    $("a[id='forum']").click(function() {
    	$(".loadingIcon").show();
    	getCurrentCSS();
    });
    
    if (localStorage.menuSelected) {
    	if (localStorage.menuSelected == 'video') {
    		localStorage.menuSelected = 'home';	//To clear the video value in home page.
    	} else {
    		$("a[name='navMenu']").attr("class", "inactive");
    		$(this).attr("class", "active");
            var selectedNav = localStorage.menuSelected;
            if (selectedNav == "applications") {
            	bacgroundValidate("validateFramework", "true");
            }
    		performAction(selectedNav, '', $("#container"));
    	}
	}
});
</script>