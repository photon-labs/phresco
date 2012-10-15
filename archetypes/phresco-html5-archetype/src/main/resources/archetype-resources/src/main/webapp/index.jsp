<!--
  ###
  Archetype - phresco-html5-archetype
  
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
  -->
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Properties" %>			
<%@ page import="com.photon.phresco.configuration.ConfigReader" %>
<%@ page import="com.photon.phresco.configuration.Configuration" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Landing Page</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <!-- Linking styles -->
        	
           <!--  Style sheets -->
           <style>
				#helloworld{
					margin:0 auto;
					width:80%;
					text-align:center;
					font-weight:bold;
					font-size:30px;
					padding:30% 0 35% 0;					
					color:#00000;
				}
				
				html, body {
					height:100%;
					margin:0;
					padding:0;
				}
			</style>
            	
        <!-- Linking styles end  -->
        
        
        <!-- Linking scripts -->
        	
            <!--  Javascripts -->

        <!-- Linking scripts ends -->
        
		
		<!-- Api scripts -->
        <script src="lib/jquery/jquery.js"></script>
		<script type="text/javascript" src="lib/yui/build/yui/yui-min.js"></script>
        <script type="text/javascript" src="lib/jquery/jquery.js"></script>
        <script type="text/javascript" src="lib/xml2json/jquery.xml2json.js"></script>
		
		<%
			String currentEnv = System.getProperty("SERVER_ENVIRONMENT");
			String path = application.getRealPath("/WEB-INF/resources/phresco-env-config.xml");
			File file = new File(path);
			ConfigReader reader = new ConfigReader(file);
			String configJson = reader.getConfigAsJSON(currentEnv, "WebServices");
		%>

        <!-- EShop API JS -->
		<script type="text/javascript" src="js/controller/AppAPI.js"></script>
		<!-- Api end -->
        <!-- Widget JS -->
		<script type="text/javascript" src="js/widgets/BaseWidget.js"></script>
		<script type="text/javascript" src="js/widgets/HelloWidget.js"></script>

        <!-- <script type="text/javascript" src="js/widgets/configure.js"></script> -->
		
     		
            <!--  All widgets -->
            
		<!-- Widget JS end -->
		<script type="text/javascript">
					var configJson = '<%= configJson %>';
					var currentEnv = '<%= currentEnv %>';
            YUI().use('node', 'widget', 'appAPI', 'baseWidget', 'helloWidget', function(Y) {

                Y.on("domready", function () {
                     var appAPI = new Y.Base.AppAPI(($.parseJSON(configJson)),currentEnv);
					

                    // instantiate NavigationWidget with the HTML
                    var helloWidget = new Y.Base.HelloWidget({
                        // place holder can be decided by specifying the attribute
                        targetNode : "#helloworld",
						apiReference : appAPI
                   });

                  //  appAPI.getWSConfig();
                    //helloAPI.getConfig();
                    helloWidget.renderUI();
                });
            });
        </script>

    </head>

    <body>
        <div id="helloworld"></div>
    </body>
</html>
