<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
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
<!doctype html>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<meta content="width=device-width" name="viewport">
<title>Phresco</title>
<link REL="SHORTCUT ICON" HREF="images/favicon.ico">
<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>

<!-- Phresco js -->
<script type="text/javascript" src="js/phresco/common.js"></script>

<link type="text/css" rel="stylesheet" href="css/bootstrap-1.2.0.css">
<link type="text/css" rel="stylesheet" href="themes/photon/css/phresco.css">
<link type="text/css" rel="stylesheet" class="changeme" id="theme">

<script type="text/javascript">
$(document).ready(function() {
	var key = "color";
    showWelcomeImage(key);
    <%
		String cmdLogin = (String) request.getAttribute("cmdLogin");
		if (cmdLogin != null) {
	%>
		createBookmarkLink('Phresco', '<%= request.getScheme() %>://<%= request.getServerName() %>:<%= request.getServerPort() %><%= request.getContextPath() %>');
	<% } %>
});

/* function createBookmarkLink(title, url) {
	if (window.sidebar)  {							// firefox
		window.sidebar.addPanel(title, url, "");
	} else if(window.opera && window.print) { 		// opera
		var elem = document.createElement('a');
		elem.setAttribute('href',url);
		elem.setAttribute('title',title);
		elem.setAttribute('rel','sidebar');
		elem.click();
	} else if(document.all) {						// ie
		window.external.AddFavorite(url, title);
	} else if (window.chrome) {
		chrome.bookmarks.create({'parentId': bookmarkBar.id,'title': 'Extension bookmarks'},
			function(newFolder) {
			console.log("added folder: " + newFolder.title);
		});
	}
} */

/* function showWelcomeImage() {
	var localstore = sessionStorage["color"];
	localStorage["color"] = localstore; 
	sessionStorage.clear();
	
    var theme = localStorage["color"]; 
    if(theme == undefined || theme == null || theme == "null" || theme == "" || theme == "undefined" || theme == "themes/photon/css/red.css") {
		$("link[id='theme']").attr("href", "themes/photon/css/red.css");
	}
	else {
		$("link[id='theme']").attr("href", localStorage["color"]);
	} 
	 
	if(theme == "themes/photon/css/blue.css") {
		$('.welcomeimg').attr("src", "images/welcome-photon_blue.png");
		$('.phtaccinno').attr("src", "images/acceleratinginovation_blue.png");
		$('.logoimage').attr("src", "images/photon_phresco_logo_blue.png");
	}
} */	


</script>
</head>
<body class="lgnBg">
      <div class="logoimg">
          <img class="logoimage" src="images/photon_phresco_logo_red.png">
      </div>
      <div class="innoimg">
           <img class="phtaccinno" border="0" alt="" onclick="window.open('http://www.photon.in','_blank');" src="images/acc_inov_red.png">
      </div>
	 
      <div class="lgnintro_container lgnContainer">
	        <div class="welcome" id="welcome">
                  <img class="welcomeimg" src="images/welcome_photon_red.png">
             </div> 
	    <div class="lgnintro_container_left">
	    <h1 class="l_align">Login</h1><h1 class="lp_align"></h1>    
			<%
			     String loginError = (String)request.getAttribute(FrameworkConstants.REQ_LOGIN_ERROR);
			%>
			&nbsp;&nbsp;&nbsp;<div id="logimErrMesg" class="lgnError"><%= loginError == null?"":loginError %></div>  
            <form name="login" action="login" method="post" class="marginBottomZero">
				<!--  UserName starts -->
				<div class="clearfix" >
				     <label class="labellg" for="xlInput" class="lgnfieldLb1">Username:</label>
				     <input class="xlarge settings_text lgnField" id="xlInput" id="username" name="username" type="text" autofocus="" maxlength="63" title="63 Characters only" placeholder="Enter the username" />
				    </div>
				<!--  UserName ends -->
		              
	            <!--  Password starts -->
	            <div class="clearfix" >
	                <label class="labellg" for="xlInput" class="lgnFieldLbl">Password:</label>
	                <input class="xlarge settings_text lgnField" id="xlInput" id="password" name="password" type="password"  maxlength="63" title="63 Characters only" value =""/>
	            </div>
	            <!--  Password ends -->
		              
                <!-- Remember me check starts  -->
		        <div class="login_check">
                      <input type="checkbox" name="rememberme">
                      <labelrem>Remember me</labelrem>
                    
                </div>
                <!-- Remember me check ends  -->
               
                
	        	<div class="clearfix">
	                <div class="input lgnBtnLabel">
	                    <input type="submit" value="Login" class="primary btn lgnBtn" id="Login">
	                    <%-- <%
	                         String loginError = (String)request.getAttribute(FrameworkConstants.REQ_LOGIN_ERROR);
	                    %>
	                    &nbsp;&nbsp;&nbsp;<div id="logimErrMesg" class="lgnError"><%= loginError == null?"":loginError %></div> --%>
	            </div>
	            </div>
	            <input type="hidden" name="fromPage" value="login"/>
			</form>
				                
	        <!--  <div class="intro_container_left_feed">
	            <p><span></span> &copy;2012 Photon. All rights reserved</p>
	        </div> -->
	        
	    </div>
</div>
	
	<div class="footer_div login">
	   <footer>
	      <div class="copyrit">
	          &copy; 2012.Photon Infotech Pvt Ltd. |
	       <a href="http://www.photon.in"> www.photon.in</a>
	     </div>
	   </footer>
	</div>
	
</body>
</html>