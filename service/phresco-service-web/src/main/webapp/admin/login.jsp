<%--
  ###
  Service Web Archive
  
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
<!DOCTYPE html>
<%@ page import="com.photon.phresco.service.admin.commons.ServiceUIConstants"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<link rel="stylesheet" href="css/bootstrap.css">
		<link rel="stylesheet" href="theme/photon/css/phresco.css">
		<link rel="stylesheet" href="theme/photon/css/red.css" class="changeme"> 
		
		<!-- basic js -->
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
		
		<!-- rightpanel scrollbar -->
		<script type="text/javascript" src="js/home.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<script type="text/javascript" src="js/jquery.cookie.js"></script>
	   
		<!-- document resizer -->
		<script type="text/javascript" src="js/windowResizer.js"></script>

		<script type="text/javascript">
			$(document).ready(function() {
				if($.cookie("css")) {
					$("link.changeme").attr("href", $.cookie("css"));
				}
				
				$(".styles").click(function() {
					$("link.changeme").attr("href", $(this).attr('rel'));
					$.cookie("css", $(this).attr('rel'), {expires: 365, path: '/'});
					showHeaderImage();
					return false;
				});
				showHeaderImage();
			});
			
			function showHeaderImage() {
				var theme = $.cookie("css");
				if(theme != undefined && theme != "theme/photon/css/red.css") {
					$('.headerlogoimg').attr("src", "theme/photon/images/phresco_header_blue.png");
					$('.phtaccinno').attr("src", "theme/photon/images/acc_inov_blue.png");
					$('.welcomeimg').attr("src", "theme/photon/images/welcome_photon_blue.png");
					
				} else {
					$('.headerlogoimg').attr("src", "theme/photon/images/phresco_header_red.png");
					$('.phtaccinno').attr("src", "theme/photon/images/acc_inov_red.png");
					$('.welcomeimg').attr("src", "theme/photon/images/welcome_photon_red.png");
				}
			}
		</script>
	</head>

	<body class="lgnBg">
		<header>
			<div class="header">
				<div class="Logo">
					 <a href="#" id="goToHome"><img class="headerlogoimg" src="" alt="logo"></a>
				</div>
			</div>
			
			<div class="innoimg">
			   <img class="phtaccinno" src="" alt="" border="0" onclick="window.open('http://www.photon.in','_blank');">
			</div>
		</header>
      
		<div class="lgnintro_container lgnContainer">
	        <div class="welcome" id="welcome">
                  <img class="welcomeimg" src="">
             </div> 
			<div class="lgnintro_container_left">
			<h1 class="l_align">Login</h1><h1 class="lp_align"></h1>    
			   
				<form name="login" action="login" method="post" class="marginBottomZero">
					<!--  UserName starts -->
					<div class="clearfix">
						 <label class="labellg" for="xlInput">Username:</label>
						 <input class="xlarge settings_text lgnField" id="xlInput" name="username" autofocus="" placeholder="Enter Your Insight User Name" type="text">
						</div>
					<!--  UserName ends -->
						  
					<!--  Password starts -->
					<div class="clearfix">
						<label class="labellg" for="xlInput">Password:</label>
						<input class="xlarge settings_text lgnField" id="xlInput" name="password" value="" type="password">
					</div>
					<!--  Password ends -->
						  
					<!-- Remember me check starts  -->
					<div class="login_check">
						  <input name="rememberme" type="checkbox">
						  <labelrem>Remember me</labelrem>
						
					</div>
					<!-- Remember me check ends  -->
				   
					
					<div class="clearfix">
						<div class="input lgnBtnLabel">
							<input type="hidden" name="loginFirst" value="false"> 
							<input type="submit" value="Login" class="btn btn-primary lgnBtn">
							<%
	                        	String loginError = (String)request.getAttribute(ServiceUIConstants.REQ_LOGIN_ERROR);
	                    	%>
							&nbsp;&nbsp;&nbsp;<div class="lgnError"><%= loginError == null ? "" : loginError %></div>
						</div>
					</div>
					<input name="fromPage" value="login" type="hidden">
				</form>
			</div>
		</div>
	
		<div class="footer_div login">
		   <footer>
			  <div class="copyrit">
				 &copy; 2012.Photon Infotech Pvt Ltd. |
			   <a href="http://www.photon.in/"> www.photon.in</a>
			 </div>
		   </footer>
		</div>
	</body>
</html>