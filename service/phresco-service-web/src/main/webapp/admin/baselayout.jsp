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
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Phresco</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<link rel="stylesheet" href="css/bootstrap.css">
		<link rel="stylesheet" href="theme/photon/css/phresco.css">
		<link rel="stylesheet" href="theme/photon/css/red.css" class="changeme">
		<link rel="stylesheet" href="css/media-queries.css">
		<link rel="stylesheet" href="css/datepicker.css"> <!-- used for date picker-->
		<link rel="stylesheet" href="css/jquery.ui.all.css"> <!-- used for date picker -->
		
		<!-- basic js -->
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
		
		<!-- document resizer -->
		<script type="text/javascript" src="js/windowResizer.js"></script>
		
		<!-- Pop Up box -->
		<script type="text/javascript" src="js/bootstrap-modal.js"></script>
		<script type="text/javascript" src="js/bootstrap-transition.js"></script>
		
		<!-- rightpanel scrollbar -->
		<script type="text/javascript" src="js/home.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		
		<!-- file upload -->
		<script type="text/javascript" src="js/ajaxfileupload.js"></script>
		
		<!-- datepicker -->
		<script type="text/javascript" src="js/jquery.ui.datepicker.js"></script>
	   	<script type="text/javascript" src="js/jquery.cookie.js"></script>

			
		<script type="text/javascript">
			$(document).ready(function() {
				if($.cookie("css")) {
					$("link.changeme").attr("href",$.cookie("css"));
				}
				
				$(".styles").click(function() {
					$("link.changeme").attr("href",$(this).attr('rel'));
					$.cookie("css", $(this).attr('rel'), {expires: 365, path: '/'});
					showHeaderImage();
					return false;
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
				
				clickMenu($("a[name='headerMenu']"), $("#container"));
				loadContent("dashboard", $("#container"));
				activateMenu($("#dashboard"));
				showHeaderImage();
			});
			
			function showHeaderImage() {
				var theme = $.cookie("css");
				if(theme != undefined && theme != "theme/photon/css/red.css") {
					$('.headerlogoimg').attr("src", "theme/photon/images/phresco_header_blue.png");
				} else {
					$('.headerlogoimg').attr("src", "theme/photon/images/phresco_header_red.png");
				}
			}
		</script>
	</head>
	<body>
		<!-- Header Starts Here -->
		<header>
			<div class="header">
				<div class="Logo">
					 <a href="#" id="goToHome"><img class="headerlogoimg" src="images/phresco_header_red.png" alt="logo"></a>
				</div>
				<div class="headerInner">
					<div class="nav_slider">
						<nav class="headerInnerTop">
							<ul>
								<li class="wid_home"><a href="#" class="inactive" name="headerMenu" id="dashboard"><s:label for="description" key="lbl.header.dashboard"  theme="simple"/></a></li>
								<li class="wid_app"><a href="#" class="inactive" name="headerMenu" id="components"><s:label for="description" key="lbl.header.components" theme="simple"/></a></li>
								<li class="wid_set"><a href="#" class="inactive" name="headerMenu" id="adminMenu"><s:label for="description" key="lbl.header.admin"  theme="simple"/></a></li>
							</ul>
							<div class="close_links" id="close_links">
								<a href="JavaScript:void(0);">
									<div class="headerInnerbottom">
										<img src="images/uparrow.png" alt="logo">
									</div>
								</a>
							</div>
						</nav>
					</div>
					<div class="quick_lnk" id="quick_lnk">
						<a href="JavaScript:void(0);">
							<div class="quick_links_outer">
								<s:label for="description" key="lbl.header.quicklink" theme="simple"/>
							</div>
						</a>
					</div>
				</div>
				<div id="signOut" class="signOut">
					<li class="usersettings">
						<s:label for="description" key="lbl.header.usersettings" theme="simple"/>
						<img src="images/downarrow.png" class="arrow">
						  <div class="userInfo">&nbsp;<s:label for="description" key="lbl.usersettings.skins"  theme="simple"/>&nbsp;
								<a class="styles" href="#"  rel="theme/photon/css/red.css">
									<img src="images/red_themer.jpg" class="skinImage">
								</a>
								<a class="styles" href="#"  rel="theme/photon/css/blue.css">
									<img src="images/blue_themer.jpg" class="skinImage">
								</a>
						 </div>
						 <div class="userInfo"><a href="#" class="abtPopUp about"><s:label for="description" key="lbl.usersettings.abtservice" theme="simple"/></a></div>
						 <div class="userInfo"><a href="<s:url action='logout'/>" id="signOut"><s:label for="description" key="lbl.usersettings.signout" theme="simple"/></a></div>
					</li>
				</div>
			</div>
		</header>
		<!-- Header Ends Here -->
		
		
		<!-- Content Starts Here -->
		<section class="main_wrapper">
			<section class="wrapper">
			
				<!-- Shortcut Top Arrows Starts Here -->
				<aside class="shortcut_top">
					<div class="lefttopnav">
						<a href="JavaScript:void(0);" id="applications" name="headerMenu"
							class="arrow_links_top">
							<span class="shortcutRed" id=""></span>
							<span class="shortcutWh" id="">
							<s:label for="description" key="lbl.header.topleftnavlab"  theme="simple"/></span>
						</a>
					</div>
					<div class="righttopnav">
						<a href="JavaScript:void(0);" class="abtPopUp" class="arrow_links_top"><span
							class="shortcutRed" id=""></span><span class="shortcutWh"
							id="">
							<s:label for="description" key="lbl.header.toprightnavlab" theme="simple"/></span>
						</a>
					</div>
				</aside>
				<!-- Shortcut Top Arrows Ends Here -->
				
				<section id="container" class="container">
				
					<!-- Content Comes here-->
					
				</section>
				
				<!-- Shortcut Bottom Arrows Starts Here -->
				<aside class="shortcut_bottom">
				   <div class="leftbotnav">
						<a href="JavaScript:void(0);" id="forum" name="headerMenu"
							class="arrow_links_bottom"><span class="shortcutRed" id=""></span><span
							class="shortcutWh" id=""><s:label for="description" key="lbl.header.bottomleftnavlab"  theme="simple"/></span>
						</a>
					</div>
					<div class="rightbotnav">
						<a href="JavaScript:void(0);" id="settings" name="headerMenu"
							class="arrow_links_bottom"><span class="shortcutRed" id="lf_tp1"></span><span
							class="shortcutWh" id="lf_tp2"><s:label for="description" key="lbl.header.bottomrightnavlab" theme="simple"/></span>
						</a>
					</div>
				</aside>
				
				<!-- Shortcut Bottom Arrows Ends Here -->
			</section>
			
			<!-- Slide News Panel Starts Here -->
			<aside>
				<section>
					<div class="right">
						<div class="right_navbar active">
							<div class="barclose">
								<div class="lnclose">Latest&nbsp;News</div>
							</div>
						</div>
						<div class="right_barcont">
							<div class="searchsidebar">
								<div class="newstext">
									Latest<span>News</span>
								</div>
								<div class="topsearchinput">
									<input name="" type="text">
								</div>
								<div class="linetopsearch"></div>
							</div>
							<div id="tweets" class="sc7 scrollable dymanic paddedtop">
								<div class="tweeterContent"></div>
							</div>
						</div>
						<br clear="left">
					</div>
				</section>
			</aside>
			<!-- Slide News Panel Ends Here -->
		</section>
		<!-- Content Ends Here -->
		
		<!-- Footer Starts Here -->
		<footer>
			<address class="copyrit">&copy; 2012.Photon Infotech Pvt Ltd. |<a href="http://www.photon.in"> www.photon.in</a></address>
		</footer>
		<!-- Footer Ends Here -->
		
	</body>
</html>