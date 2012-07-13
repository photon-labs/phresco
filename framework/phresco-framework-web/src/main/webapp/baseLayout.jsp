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
<!doctype html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<meta name="viewport" content="width=device-width, height=device-height, minimum-scale=0.25, maximum-scale=1.6">
<title>Phresco</title>
<link REL="SHORTCUT ICON" HREF="images/favicon.ico">
<link type="text/css" rel="stylesheet" href="css/bootstrap-1.2.0.css">
<link type="text/css" rel="stylesheet" href="themes/photon/css/phresco.css" id="phresco" >
<link type="text/css" rel="stylesheet" class="changeme" title="phresco">
<!--<link type="text/css" rel="stylesheet" href="themes/photon/css/blue.css">-->
<!-- media queries css -->
<link type="text/css" rel="stylesheet" href="themes/photon/css/media-queries.css">

<!-- jquery file tree css starts -->
<link type="text/css" rel="stylesheet" href="css/jqueryFileTree.css" media="screen">
<!-- jquery file tree css ends -->

<!-- rightpanel js -->
<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/home.js"></script>

<!-- rightpanel scrollbar -->

<script type="text/javascript" src="js/home-header.js"></script>
<script type="text/javascript" src="js/jquery.loadmask.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/selection-list.js"></script>
<script type="text/javascript" src="js/jquery-jvert-tabs-1.1.4.js"></script>
<script type="text/javascript" src="js/jquery-jvert-tabs-1.1.4.js"></script>
<script type="text/javascript" src="js/RGraph.common.core.js"></script>
<script type="text/javascript" src="js/RGraph.common.tooltips.js"></script>
<script type="text/javascript" src="js/RGraph.common.effects.js"></script>
<script type="text/javascript" src="js/RGraph.pie.js"></script>
<script type="text/javascript" src="js/RGraph.bar.js"></script>
<script type="text/javascript" src="js/RGraph.line.js"></script>
<script type="text/javascript" src="js/RGraph.common.key.js"></script>
<script type="text/javascript" src="js/video.js"></script>
<script type="text/javascript" src="js/confirm-dialog.js"></script>

<!-- Scroll Bar -->
<script type="text/javascript" src="js/scrollbars.js"></script>
<script type="text/javascript" src="js/jquery.event.drag-2.0.min.js"></script>
<script type="text/javascript" src="js/jquery.ba-resize.min.js"></script>
<script type="text/javascript" src="js/jquery.mousewheel.js"></script>
<script type="text/javascript" src="js/mousehold.js"></script>

<!-- Phresco js -->
<script type="text/javascript" src="js/phresco/common.js"></script>

<!-- Window Resizer -->

<!-- jquery file tree starts-->
<script type="text/javascript" src="js/jqueryFileTree.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<!-- jquery file tree ends -->
<script type="text/javascript">
    
	if (localStorage["color"] != null) {
        $("link[title='phresco']").attr("href", localStorage["color"]);
    } else {
        $("link[title='phresco']").attr("href", "themes/photon/css/red.css");
    }  

    $(document).ready(function() {
        $(".styles").click(function() {
            var key = "color";
            var title = "phresco";
            var value = $(this).attr("rel");
            setLocalstorage(key,value);
            getLocalstorage(key, title);
            $("iframe").attr({
                src: $("iframe").attr("src")
            });
        });
    });
</script>
</head>
<body>
	<div class="wel_come"></div>
	<div class="errorOverlay"></div>
	<div id="loadingIconDiv"></div>
	
	<!-- In Progress starts -->
	<div id="progressbar" class="progressPosition">
		<div id="indicatorInnerElem">
			<span id="progressnum"><s:text name="label.creating.project"/></span>
		</div>
		<div id="indicator"></div>
	</div>
	<!-- In Progress Ends -->

	<header>
		<tiles:insertAttribute name="header" />
	</header>
	
	<div class="main_wrapper">
		<div id="wrapper" class="wrapper">
			<div class="shortcut_top">
				<div class="lefttopnav">
					<a href="JavaScript:void(0);" id="applications" name="headerMenu"
						class="arrow_links_top"><span class="shortcutRed" id="lf_tp1"></span><span
						class="shortcutWh" id="lf_tp2"><s:text name="label.appln"/></span>
					</a>
				</div>
				<div class="control-group customer_name">
                    <s:label key="label.customer" cssClass="control-label custom_label labelbold"/>
                    <select class="customer_listbox">
                        <option>Walgreens</option>
                        <option>NBO</option>
                        <option>Cengage</option>
                        <option>Horizon Blue</option>
                        <option>Photon</option>
                    </select>				
				</div>
				<div class="righttopnav">
					<a href="JavaScript:void(0);" class="abtPopUp" class="arrow_links_top"><span
						class="shortcutRed" id="lf_tp1"><s:text name="label.about"/></span><span class="shortcutWh"
						id="lf_tp2"> <s:text name="label.abt.us"/></span>
					</a>
				</div>
			</div>
			<tiles:insertAttribute name="content" />
			<!-- <div class="content" id="content">
				
			</div> -->
			<div class="shortcut_bottom">
				<div class="leftbotnav">
					<a href="JavaScript:void(0);" id="forum" name="headerMenu"
						class="arrow_links_bottom"><span class="shortcutRed" id="lf_tp1"></span><span
				class="shortcutWh" id="lf_tp2"><s:text name="label.help"/></span>
					</a>
				</div>
				<div class="rightbotnav">
					<a href="JavaScript:void(0);" id="settings" name="headerMenu"
						class="arrow_links_bottom"><span class="shortcutRed" id="lf_tp1"></span><span
				class="shortcutWh" id="lf_tp2"><s:text name="label.settings"/></span>
					</a>
				</div>
			</div>
		</div>
	<!-- /#sidebar -->
		<tiles:insertAttribute name="rightPanel" />
	</div>
	<footer>
		<tiles:insertAttribute name="footer" />
	</footer>
	<tiles:insertAttribute name="confirmDialog" />

	<!-- About dialog starts -->
	<div id="aboutDialog" class="modal about" style="display: none;">
	
	</div>
	<!-- About dialog ends -->
	
    <!-- Pop Up Starts-->
    <div class="popup_div" id="popup_div">
    
    </div>
    <!-- Pop Up Ends -->
    
</body>

<script type="text/javascript">
	var refreshIntervalId;
	if ($.browser.safari && $.browser.version == 530.17) {
		$(".shortcut_bottom").show().css("float","left");
		/* $(".buildDiv").show().css("margin-top","-18px");  */
	}
	
	/** To include the js based on the device **/
	var body = document.getElementsByTagName('body')[0];
	var script = document.createElement('script');
	if (isiPad()) {
		script.src = 'js/windowResizer-ipad.js';
	} else {
		script.src = 'js/windowResizer.js';
	}
	body.appendChild(script);
</script>
</html>

