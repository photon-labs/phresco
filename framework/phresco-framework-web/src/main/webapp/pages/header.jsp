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
<%@ page import="com.photon.phresco.model.UserInfo" %>

<script src="js/reader.js" ></script>

<%
	String validationStatus = (String)session.getAttribute("validationStatus");
	String displayName = "";
	UserInfo userInfo = (UserInfo)session.getAttribute(FrameworkConstants.REQ_USER_INFO);
	if(userInfo == null || userInfo.getDisplayName() == null){
	request.setAttribute(FrameworkConstants.REQ_LOGIN_ERROR, "Invalid Username or Password");
%>

	 <script type="text/javascript"> 
	     $.ajax({
	         url : 'logout',
	         success : function(data) {
	             $("html").empty();
	             $("html").addClass('lgnBg');
	             $("html").html(data);
	         }
	     }); 
     
	 </script>
	
<%
	} else {
		displayName = userInfo.getDisplayName();
	}
%>

<div class="header">
	<div class="Logo">
		 <a href="#" id="goToHome"><img class="headerlogoimg"  src="images/phresco_header_red.png" alt="logo"></a>
	</div>
	<div class="headerInner">
		<div class="nav_slider">
			<div class="headerInnerTop">
				<ul>
					<li class="wid_home"><a href="#" class="inactive" name="headerMenu" oncontextmenu="localStorage.menuSelected = 'home';" id="home"><s:text name="label.hdr.home"/></a></li>
					<li class="wid_app"><a href="#" class="inactive" name="headerMenu" oncontextmenu="localStorage.menuSelected = 'applications';" id="applications"><s:text name="label.appln"/></a></li>
					<li class="wid_set"><a href="#" class="inactive" name="headerMenu" oncontextmenu="localStorage.menuSelected = 'settings';" id="settings"><s:text name="label.hdr.settings"/></a></li>
					<li class="wid_help"><a href="#" class="inactive" name="headerMenu" oncontextmenu="localStorage.menuSelected = 'forum';"  id="forum"><s:text name="label.help"/></a></li>
				</ul>
				<div class="close_links" id="close_links">
					<a href="JavaScript:void(0);">
						<div class="headerInnerbottom">
							<img src="images/uparrow.png" alt="logo">
						</div>
					</a>
				</div>
			</div>
		</div>
		<div class="quick_lnk" id="quick_lnk">
			<a href="JavaScript:void(0);">
				<div class="quick_links_outer">
					<center class="center"><s:text name="label.hdr.quicklink"/></center>
				</div>
			</a>
		</div>
	</div>
</div>

<!-- Sign out starts -->
<div id="signOut" class="signOut">
      <li class="usersettings"><%= displayName %><img src="images/downarrow.png" class="arrow">
			<div class="userInfo">&nbsp;<s:text name="label.skins"/>&nbsp;
				<a href="#" id="red" rel="themes/photon/css/red.css" class="styles" title="theme">
					<img src="images/red_themer.jpg" class="skinImage">
				</a>&nbsp;
		    	<a href="#" id="blue" rel="themes/photon/css/blue.css" class="styles" title="theme">
		    		<img src="images/blue_themer.jpg" class="skinImage">
		       </a>
			</div>
		        <div class="userInfo">&nbsp;<a name ="headerMenu" href="#" id="forum" class=""><s:text name="label.help"/></a></div>
           	<div class="userInfo"><a href="#" id="about" class="abtPopUp"><s:text name="label.abt.phresco"/></a></div>
		<div class="userInfo"><a href="<s:url action='logout'/>" id="signOut"><s:text name="label.signout"/></a></div>
      </li>
</div>

<!-- Sign out ends -->	

<%
	String selectedMenu = "home";
	if(request.getAttribute(FrameworkConstants.REQ_SELECTED_MENU) != null) {
		 selectedMenu = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_MENU);
	}
%>

<script type="text/javascript">
    $(document).ready(function() {
    	var key = "color";
    	showHeaderImage(key);
    	$("a[name='headerMenu']").attr("class", "inactive");
		$("a[id='<%= selectedMenu%>']").attr("class", "active");	
		
        $("a[name='headerMenu']").click(function() {
			$("a[name='headerMenu']").attr("class", "inactive");
			$(this).attr("class", "active");
            var selectedMenu = $(this).attr("id");
            if (selectedMenu == "applications") {
            	bacgroundValidate("validateFramework");
            }
            
            performAction(selectedMenu, '', $("#container"));
        });
        
        $("#goToHome").click(function() {
        	$("a[name='headerMenu']").attr("class", "inactive");
			$("#home").attr("class", "active");
            var selectedMenu = "home";
           
            performAction(selectedMenu, '', $("#container"));
        });
        
        //This function is to handle the click event of about click(Version update)
        $(".abtPopUp").click(function(){
        	disableScreen();
        	$("#aboutDialog").css("display", 'block');
        	$("#loadingIcon").show();
        	getCurrentCSS();
        	$.ajax({
    			url : 'about',
    			success : function(data) {
    				$("#aboutDialog").html(data);
    			}
    		});
        	$(document).keydown(function(e) {
                // ESCAPE key pressed
               if (e.keyCode == 27) {
                    //dialog('none');
                  enableScreen();
                  $('#aboutDialog').show().css("display","none");      
                }
            });
    		$('#availableVersion').css("display", "none");
    		$('#versionInfo').css("display", "block");
    	});
        
        // function to show user info in toggle 
        $('div li.usersettings div').hide(0);
		$('div li.usersettings').click(function () {
			$('div li.usersettings div').slideToggle(0);
    	});
		
		// to show user info on mouse over
        $('#signOut li').mouseenter(function(){
         	$("div li.usersettings div").hide(0);
         	$(this).children("div li.usersettings div").show(0);
     	}).mouseleave(function(){
         	$("div li.usersettings div").hide(0);
     	});
		
     	// shows preloader until page loads
        $("#forum").click(function(){
        	$(".loadingIcon").show();
        	getCurrentCSS();
        })
    });
       
    function disableCreateProject() {
    	disableControl($("#add"), "btn disabled");
    }
    
    function disableCI() {
    	disableControl($("#setup"), "btn disabled");
    	disableControl($("#configure"), "btn disabled");
    	disableControl($("#build"), "btn disabled");
    	disableControl($("#startJenkins"), "btn disabled");
    	disableControl($("#stopJenkins"), "btn disabled");
    }
    
</script>