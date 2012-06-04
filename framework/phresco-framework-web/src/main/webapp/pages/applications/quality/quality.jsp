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

<%@ include file="../errorReport.jsp" %>
<%@ include file="../../userInfoDetails.jsp" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.IOException"%>
<%@ page import="com.photon.phresco.exception.PhrescoException"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.*"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes"%>

<%
    List<SettingsInfo> serverSettings = (List<SettingsInfo>)request.getAttribute(FrameworkConstants.REQ_ENV_SERVER_SETTINGS);
	String testTypeSelected = (String)request.getAttribute(FrameworkConstants.REQ_TEST_TYPE_SELECTED);
   	Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
    ProjectInfo selectedInfo = null; 
    String projectCode = null;
    if(project != null) {
        selectedInfo = project.getProjectInfo();
        projectCode = selectedInfo.getCode();
        
    }
%>

<s:if test="hasActionMessages()">
    <div class="alert-message success"  id="successmsg">
        <s:actionmessage />
    </div>
</s:if>

<div id="subTabcontent">
	<div id="navigation">
		<ul>
			<li><a href="#" class="unselected" name="quality" title="unit"><s:text name="label.unit"/></a></li>
			<li><a href="#" class="unselected" name="quality" title="functional"><s:text name="label.funtional"/></a></li>
			<%
				if (!TechnologyTypes.IPHONES.contains(selectedInfo.getTechnology().getId())) {
			%>
					<li><a href="#" class="unselected" name="quality" title="performance"><s:text name="label.performance"/></a></li>
			<%
				}
				if (!(TechnologyTypes.ANDROIDS.contains(selectedInfo.getTechnology().getId()) || TechnologyTypes.IPHONES.contains(selectedInfo.getTechnology().getId()))) {
			%>
					<li><a href="#" class="unselected" name="quality" title="load"><s:text name="label.load"/></a></li>
			<%
				}
			%>
		</ul>
	</div>
	<div id="subTabcontainer">

	</div>
</div>

<!-- <div class="popup_div_per" id="generateJmeter">
</div>  -->

<script type="text/javascript">
    $(document).ready(function() {
    	changeStyle("quality");
		var testType = "<%= testTypeSelected%>"
		if(testType == "null"){
			testType = "unit";
			$("a[title='unit']").attr("class", "selected");	
		} else {
			$("a[title='" + testType + "']").attr("class", "selected");
		}		

		changeTesting(testType);

		$("a[name='quality']").click(function() {
			$("a[name='quality']").attr("class", "unselected");
			$(this).attr("class", "selected");
			var testingType = $(this).attr("title");
			changeTesting(testingType);
		});

	});

	//This function is to handle the change event for testing
	function changeTesting(testingType, fromPage) {
		$("#subTabcontainer").empty(); 
     	//$("#subTabcontainer").html("<div><img class='popupLoadingIcon' style='display: block'></div>");
     	//getCurrentCSS();
 		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
		params = params.concat("testType=");
		params = params.concat(testingType);
        if (fromPage != undefined) {
            params = params.concat("&fromPage=");
            params = params.concat(fromPage);
        }
		performAction('testType', params, $('#subTabcontainer'));
		//$("#subTabcontainer").css("display","block");
	}
	
	function getConfigNames() {
        var envName = $('#environments').val();
        var type = $('input:radio[name=jmeterTestAgainst]:checked').val();
        $.ajax({
            url : "getConfigNames",
            data: {
                'envName': envName,
                'type': type,
                'projectCode' : '<%= projectCode %>',
            },
            type: "POST",
            success : function(data) {
                if(data.configName != "") {
                    fillSelectData(type, data.configName);
                } else {
                    $('#' + type).find('option').remove();
                    $('#' + type).val("");
                }
            },
            async:false
        }); 
    }
    
    /** This method is to fill data in the appropriate controls **/
    function fillSelectData(type, data) {
    	if(type == "Server") {
    		$('#' + type).val(data);
    	} else {
	    	$('#' + type).find('option').remove();
            for (i in data) {
            	$('#' + type).append($("<option></option>").attr("value", data[i]).text(data[i]));
            }
    	}
    }
</script>