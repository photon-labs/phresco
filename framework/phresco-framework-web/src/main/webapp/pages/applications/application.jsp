<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.framework.api.Project" %>
<%@ page import="com.photon.phresco.model.ProjectInfo" %>
<%@ page import="com.photon.phresco.model.ApplicationType" %>
<%@ page import="com.photon.phresco.model.UserInfo" %>

<%@ include file="../userInfoDetails.jsp" %>

<!--  Heading Starts -->
<%
    String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
    if (StringUtils.isEmpty(fromPage)) {
        fromPage = "";
    }
    ProjectInfo selectedInfo = (ProjectInfo) request.getAttribute(FrameworkConstants.REQ_PROJECT_INFO); 
    String projectCode = "";
    if(selectedInfo != null) {
        projectCode = selectedInfo.getCode();
    }
    String disabled = "disabled";
    if (StringUtils.isNotEmpty(fromPage)) {
%>
        <div class="page-header">
        	<h1 style="float: left;"><s:text name="label.editappln"/> - <%= selectedInfo.getName() %></h1>
        	<div class="icon_div">
				<a href="#" onclick="showProjectValidationResult();" title="Validate project">
					<img src="images/icons/validate_failure_icon.png" id="validationErr_validateProject" style="display: none;">
				</a>
				<a href="#" onclick="showProjectValidationResult();" title="Validate project">
					<img src="images/icons/validate_success_icon.png" id="validationSuccess_validateProject" style="display: none;">
				</a>
			</div>
        </div>
        
        <ul class="tabs">
            <li><a href="#" class="unselected" name="appTabs" id="appinfo"><s:text name="label.editappln.appinfo"/></a></li>
            <li><a href="#" class="unselected" name="appTabs" id="features"><s:text name="label.editappln.feature"/></a></li>
            <li><a href="#" class="unselected" name="appTabs" id="code"><s:text name="label.editappln.code"/></a></li>
            <li><a href="#" class="unselected" name="appTabs" id="configuration"><s:text name="label.editappln.confign"/></a></li>
            <li><a href="#" class="unselected" name="appTabs" id="buildView"><s:text name="label.editappln.build"/></a></li>
            <li><a href="#" class="unselected" name="appTabs" id="quality"><s:text name="label.editappln.quality"/></a></li>
            <li><a href="#" class="unselected" name="appTabs" id="ci"><s:text name="label.editappln.cntusintgrn"/></a></li>
            <li><a href="#" class="unselected" name="appTabs" id="download"><s:text name="label.editappln.download"/></a></li>
        </ul>
<%
    } else {
        disabled = "";
%>
        <div class="page-header">
            <h1>
                <s:text name="label.addappln"/> <small><span class="red">*</span> <s:text name="label.mandatory"/></small>
            </h1>
        </div>
        <ul class="tabs">
            <li><a href="#" class="unselected" name="appTabs" id="appinfo"><s:text name="label.addappln.appinfo"/></a></li>
            <li><a href="#" class="unselected" name="appTabs" id="features"><s:text name="label.addappln.feature"/></a></li>
        </ul>
<%  } %>

	<input type="hidden" id="fromPage" value="<%= fromPage %>" name="fromPage"/>
	
<form> 
	<input type="hidden" id="projectCode" value="<%= projectCode %>" name="projectCode"/>
</form>
<!--  Heading Ends-->

<input type="hidden" name="alreadyConstructed" id="alreadyConstructed" value="">

<div class="tabDiv appInfoTabDiv" id="tabDiv">
</div>

<!-- <div id="validateProject">
</div> -->

<script type="text/javascript">
	$(".appInfoTabDiv").css("padding-top", "0px");
	
    var isCiRefresh = false; // for ci page use
    
	$(document).ready(function() {
		
		// When project is loaded, project related validation has to be loaded
		bacgroundValidate("validateProject", '<%= projectCode %>');
		
		<% 
			if (session.getAttribute(projectCode + FrameworkConstants.SESSION_PRJT_VLDT_STATUS) != null) {
				String projectValidationStatus = (String)session.getAttribute(projectCode + FrameworkConstants.SESSION_PRJT_VLDT_STATUS);
				if (projectValidationStatus == "ERROR") {
		%>
					$("#validationSuccess_validateProject").css("display", "none");
					$("#validationErr_validateProject").show();
	    <%		} else { %>
	    			$("#validationErr_validateProject").hide();
	    			$("#validationSuccess_validateProject").css("display", "block");
	   	<% 		} 
			}
	   	%>
		
        var selectedTab = "appinfo";
        $("#validationDiv").hide();	
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
		params = params.concat("fromPage=");
		params = params.concat('<%= fromPage %>');
    	showLoadingIcon($("#tabDiv")); // Loading Icon
		performAction(selectedTab, params, $("#tabDiv"));
		
        $("a[name='appTabs']").click(function() {
        	var selectedTab = $(this).attr("id");
        	// the below code is to hide the project validation icon for specific tabs
        	var alreadySelectedTab = $(this).attr("class");
        	if ($.trim(alreadySelectedTab) != "selected" || $.trim(selectedTab) == "configuration" || $.trim(selectedTab) == "ci") {
        		bacgroundValidate("validateProject", $('#projectCode').val());
				var params = "";
		    	if (!isBlank($('form').serialize())) {
		    		params = $('form').serialize() + "&";
		    	}
				params = params.concat("fromPage=");
				params = params.concat('<%= fromPage %>');
// 				showLoadingIcon($("#tabDiv")); // Loading Icon
				if($.trim(alreadySelectedTab) != "features" && $.trim(selectedTab) != "features") {
		    		showLoadingIcon($("#tabDiv")); // Loading Icon
		    	}
        		performAction(selectedTab, params, $("#tabDiv"));
        	}
        });
    });

    function changeStyle(selectedTab) {
        $("a[name='appTabs']").attr("class", "unselected");
        $("a[id='" + selectedTab + "']").attr("class", "selected");
    }
    
    function openFolder(path) {
         var params = "path=";
         params = params.concat(path);
         performAction('openFolder', params, '');
    }
    
    function copyPath(path) {
         var params = "path=";
         params = params.concat(path);
         performAction('copyPath', params, '');
	}
    
    function copyToClipboard(data) {
        var params = "copyToClipboard=";
        params = params.concat(data);
        performAction('copyToClipboard', params, '');
	}
    
    /* To show the validation result */
	function showProjectValidationResult() {
    	$("#popup_div").empty();
		$('#popup_div').show().css("display", "block"); 
		disableScreen();		
		popup('showProjectValidationResult', '', $('#popup_div')); // there was xtra param here
	}

    // this method is to fill select box with data in ShowSettings
    function fillData(element, data) {
    	if ((data != undefined || !isBlank(data)) && data != "") {
    		$('#' + element).append('<optgroup label="Settings" class="optgrplbl" id="' + element + 'Group">');
			for (i in data) {
				$('#' + element + 'Group').append($("<option></option>").attr("value",data[i]).text(data[i]));
			}
			$('#' + element).append('</optgroup>');
		} else {
			$('#' + element + 'Group').remove();
		}
	}

</script>