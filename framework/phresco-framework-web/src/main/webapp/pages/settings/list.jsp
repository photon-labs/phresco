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
<%@ taglib uri="/struts-tags" prefix="s" %>

<%@ include file="../applications/errorReport.jsp" %>
<%@ include file="../userInfoDetails.jsp" %>

<%@ page import="java.util.Collection"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.util.Map"%>

<%@ page import="java.util.regex.*"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.model.PropertyInfo"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.Constants"%>
<%@ page import="com.photon.phresco.configuration.Environment" %>

<script type="text/javascript" src="js/delete.js" ></script>
<script type="text/javascript" src="js/confirm-dialog.js" ></script>
<script type="text/javascript" src="js/loading.js"></script>
<script type="text/javascript" src="js/home-header.js" ></script>
<%
List<Environment> envs = (List<Environment>) request.getAttribute(FrameworkConstants.ENVIRONMENTS);
%>
<style type="text/css">
    .btn.success, .alert-message.success {
        margin-top: 2px;
    }
    
   	table th {
		padding: 0 0 0 9px;  
	}
	   	
	td {
	 	padding: 5px;
	 	text-align: left;
	}
	  
	th {
	 	padding: 0 5px;
	 	text-align: left;
	}
</style>

<div class="page-header">
    <h1>
        Settings
    </h1>
</div>
    
<form action="deleteSettings" name="myform" method="post" id="deleteObjects" class="settings_list_form" >
    <div class="operation">
        <input id="addButton" type="button" value="<s:text name="label.add"/>" class="btn primary"/>
        <input id="deleteButton" type="button" value="<s:text name="label.delete"/>" class="btn disabled" disabled="disabled"/>
        <input id="environmentButton" type="button" value="<s:text name="label.environment"/>" class="btn primary" />
        <s:if test="hasActionMessages()">
            <span class="alert-message success"  id="successmsg">
                <s:actionmessage />
            </span>
        </s:if>
    </div>
    <div class="settings_container settings_settings_container">
        <%
            List<SettingsInfo> settings = (List<SettingsInfo>)request.getAttribute("settings");
        %>

        <%
        	Map<String, String> urls = new HashMap<String, String>();
            if (settings == null || settings.size() == 0) {
        %>
            <div class="alert-message block-message warning" >
                <center><s:label key="settings.error.message" cssClass="errorMsgLabel"/></center>
            </div>
        <%
            } else {
        %>
        		<div class="settingsList_table_div">
		    		<div class="fixed-table-container">
			      		<div class="header-background"> </div>
			      		<div class="fixed-table-container-inner">
					        <table cellspacing="0" class="zebra-striped">
					          	<thead>
						            <tr>
										<th class="first">
						                	<div class="th-inner">
						                		<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto">
						                	</div>
						              	</th>
						              	<th class="second">
						                	<div class="th-inner"><s:text name="label.name"/></div>
						              	</th>
						              	<th class="third">
						                	<div class="th-inner"><s:text name="label.description"/></div>
						              	</th>
						              	<th class="third">
						                	<div class="th-inner"><s:text name="label.type"/></div>
						              	</th>
						              	<th class="third">
                                            <div class="th-inner"><s:text name="label.environment"/></div>
                                        </th>
						              	<th class="third">
						                	<div class="th-inner"><s:text name="label.status"/></div>
						              	</th>
						            </tr>
					          	</thead>
					
					          	<tbody>
							<%
								for (SettingsInfo setting : settings) {
										   for(Environment enves : envs) { 
										       if(setting.getEnvName().equals(enves.getName())) {
							%>
							<tr>
					              		<td class="checkbox_list">
					              			<input type="checkbox" class="check" name="check" value="<%=setting.getEnvName() + "," +setting.getName() %>">
					              		</td>
					              		<td>
					              			<a href="#" name="edit" id="<%= setting.getName() %>" onclick="editSetting('<%= setting.getName()%>', '<%= setting.getEnvName() %>')"><%= setting.getName() %></a>
					              		</td>
					              		<td style="width: 25%;"><%= setting.getDescription() %></td>
					              		<td><%= setting.getType() %></td>
					              		<td style="width: 25%;" title="<%= enves.getDesc()%>" ><%= setting.getEnvName() %></td>
					              		<td>
						              		<%	
				                            	String protocol = null;
				                            	String host = null;
				                            	int port = 0;
			                    				List<PropertyInfo> propertyInfos = setting.getPropertyInfos();
			                            		String url = "";
			                            		for (PropertyInfo propertyInfo : propertyInfos) {
			                    					if (propertyInfo.getKey().equals(Constants.SERVER_PROTOCOL)) {
			                    						protocol = propertyInfo.getValue();
			                    					}
			                    					if (propertyInfo.getKey().equals(Constants.SERVER_HOST)) {
			                    						host = propertyInfo.getValue();
			                    					}
			                    					if (propertyInfo.getKey().equals(Constants.SERVER_PORT)) {
			                    						port = Integer.parseInt(propertyInfo.getValue());
			                    					}
			                            		}
				                           		if (StringUtils.isEmpty(protocol)) {
				                           			protocol = Constants.DB_PROTOCOL;
				                           		}
			                               		String settingName = setting.getName();
			                               		Pattern pattern = Pattern.compile("\\s+");
			                               		Matcher matcher = pattern.matcher(settingName);
			                               		boolean check = matcher.find();
			                               		String settingNameForId = matcher.replaceAll("");
			                            		urls.put(settingNameForId, protocol +","+ host + "," + port);
			                            	%>	
			                            		<img src="images/icons/inprogress.png" alt="status-up" title="Loading" id="isAlive<%= settingNameForId %>">
			                           	</td>
					            	</tr>
					            <%
								 	 }
					          	   } 
					          	 }		
								%>	
					          	</tbody>
					        </table>
			      		</div>
		    		</div>
    			</div>
            </div>
        <%
            }
        %>
</form>

<!-- <div class="popup_div" id="environment">

</div> -->

<script type="text/javascript">
    $("#settings").attr("class", "active");
    $("#home").attr("class", "inactive");
   
    /* To check whether the divice is ipad or not */
	if(!isiPad()){
	    /* JQuery scroll bar */
		$(".fixed-table-container-inner").scrollbars();
	}
    function isConnectionAlive(url, id) {
        $.ajax({
        	url : 'connectionAliveCheck',
        	data : {
        		'url' : url,
        	},
        	type : "get",
        	datatype : "json",
        	success : function(data) {
        		if($.trim(data) == 'true') {
        			$('#isAlive' + id).attr("src","images/icons/status-up.png");
        			$('#isAlive' + id).attr("title","Alive");
        		}
				if($.trim(data) == 'false') {
					$('#isAlive' + id).attr("src","images/icons/status-down.png");
					$('#isAlive' + id).attr("title","Down");
        		}
        	}
        });
    }
    
    function openEnvironmentPopup() {
    	showPopup();
    	$('#popup_div').empty();
        var params = "fromTab=";
		params = params.concat("settings");
		popup('openSettingsEnvPopup', params, $('#popup_div'));
     }
    
    $(document).ready(function() {
    	<% 
			if(urls != null) {
		    	Iterator iterator = urls.keySet().iterator();  
		    	while (iterator.hasNext()) { 
		    	   String id = iterator.next().toString();  
		    	   String url = urls.get(id).toString();   
	 	%>
		 		isConnectionAlive('<%= url%>', '<%= id%>');
		<%
		    	}
			}
		%>
		
		$('#deleteButton').click(function() {
			$("#confirmationText").html("Do you want to delete the selected setting(s)?");
		    dialog('block');
		    escBlockPopup();
		});
		
		$('#addButton').click(function() {
			disableScreen();
			showLoadingIcon($("#loadingIconDiv"));
			
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
            performAction("addSettings", params, $('#container'));
        });
		
		$('form').submit(function() {
			showProgessBar("Deleting Setting (s)", 100);
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
            performAction("deleteSettings", params, $('#container'));
            return false;
        });
		
	    $('#environmentButton').click(function() {
			openEnvironmentPopup();
		});
    });
    
    function editSetting(configName, envName) {
    	disableScreen();
		showLoadingIcon($("#loadingIconDiv"));
    	var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
    	params = params.concat("oldName=");
    	params = params.concat(configName);
    	params = params.concat("&envName=");
    	params = params.concat(envName);
    	performAction("editSetting", params, $('#container'));
    }
</script>