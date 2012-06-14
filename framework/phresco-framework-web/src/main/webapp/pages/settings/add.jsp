<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.MapUtils" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.configuration.Environment"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.model.SettingsTemplate"%>
<%@ include file="../userInfoDetails.jsp" %>

<%
    String selectedStr = "";
    String name = "";
    String description = "";
    String selectedType = "";
    String fromPage = "";
    String error = "";
    String envName = "";
    
    String currentEnv = (String) request.getAttribute("currentEnv");
    String oldName = (String) request.getAttribute(FrameworkConstants.SESSION_OLD_NAME);
    if (oldName == null) {
        oldName = "";
    }
    SettingsInfo configInfo = (SettingsInfo) session.getAttribute(oldName);
    
    if (configInfo != null) {
        envName = configInfo.getEnvName();
        name = configInfo.getName();
        description = configInfo.getDescription();
        selectedType = configInfo.getType();
    } 
    
    if(request.getAttribute(FrameworkConstants.REQ_FROM_PAGE) != null) {
        fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
    }
    
    List<Environment> envs = (List<Environment>) request.getAttribute(FrameworkConstants.ENVIRONMENTS);
    Map<String, String> errorMap = (Map<String, String>) session.getAttribute(FrameworkConstants.ERROR_SETTINGS);
    
%>

<!--  Heading starts -->
<%
    if(StringUtils.isEmpty(fromPage)) {
%>
        <div class="page-header">
            <h1><s:text name="label.settings.add"/></h1>
        </div>
<%
    } else {
%>
        <div class="page-header">
            <h1><s:text name="label.settings.edit"/></h1>
        </div>
<%  } %>
<!--  Heading ends -->

<!--  Action Messages starts -->
<s:if test="hasActionMessages()">
    <div class="settings-alert-message success"  id="successmsg">
        <s:actionmessage />
    </div>
</s:if>
<!--  Action Messages ends -->
    
        <!--  Form starts -->
        <%
           if(StringUtils.isEmpty(fromPage)) {
        %>
              <form action="saveSetting" method="post" autocomplete="off" class="settings_add_form">
        <%
           } else {
        %>
			  <form action="updateSetting" method="post" autocomplete="off" class="settings_add_form">
        <% } %>
        
		<% 
			String ErrName = null; 
			if (MapUtils.isNotEmpty(errorMap) && StringUtils.isNotEmpty(errorMap.get("name"))) {
				ErrName =  errorMap.get("name");
			}
		%>
            	<div class="settings_div">
	                <!--  Name starts -->
	                <div class="clearfix" id="nameErrDiv" style="padding: 0;">
	                    <label for="xlInput" class="new-xlInput"><span class="red">*</span> <s:text name="label.name"/></label>
	                    <div class="input new-input"  style="padding-top:5px;">
	                    	<div class="typeFields">
		                        <input class="xlarge settings_text" id="xlInput" name="settingsName"
		                            type="text" maxlength="30" title="30 Characters only" value ="<%= name%>" autofocus="true" 
		                            onfocus="showToolTip('nameHelpTxt_Stg');" placeholder="<s:text name="label.name.config.placeholder"/>"/>
	                        </div>
	                        
	                        <div>
					            <div class="lblDesc configSettingHelp-block" id="nameErrMsg">
					                
						        </div>
					        </div>
					        
					        <!-- <div class="twipsy bootstrap-right" id="nameHelpTxt_Stg">
								<div class="twipsy-arrow"></div>
								<div class="twipsy-inner">Name of the settings</div>
							</div> -->
	                    </div>
	                </div>
	                <!--  Name ends -->
	                
	                <!--  Description starts -->
	                <div class="clearfix">
	                    <s:label for="description" key="label.description" theme="simple" cssClass="new-xlInput"/>
	                    <div class="input new-input">
	                        <textarea  class="appinfo-desc xxlarge" maxlength="150" title="150 Characters only" class="xxlarge" id="textarea" name="description" onfocus="showToolTip('descHelpTxt_Stg');" placeholder="<s:text name="label.description.config.placeholder"/>"><%=description%></textarea>
	                    </div>
	                    
	                    <!-- <div class="twipsy bootstrap-right" id="descHelpTxt_Stg">
							<div class="twipsy-arrow"></div>
							<div class="twipsy-inner">Description of the settings</div>
						</div> -->
	                </div>
	                <!--  Description ends -->
	                
	                <div class="clearfix" id="envDiv">
            			<label for="xlInput" class="new-xlInput"><span class="red">* </span><s:text name="label.environment"/></label>
            			<div class="input new-input"> 
            				<div class="typeFields">
		                        <select id="environments" name="environments" class="selectEqualWidth"  onfocus="showToolTip('envHelpTxt_Stg');">
	                    			<%
	                    			    if(envs != null) {
	                    			        for(Environment env : envs) {
	                    			%>
	                    				        <option value="<%= env.getName() %>"><%= env.getName() %></option>
	                    			<% 
	                    			        } 
	                    			     }
	                    			%>
	                			</select>
	                        </div>
	                        
	                        <div>
								<div class="lblDesc configSettingHelp-block" id="envErrMsg">
					                
						        </div>
					        </div>
					        
					        <!-- <div class="twipsy bootstrap-right" id="envHelpTxt_Stg">
								<div class="twipsy-arrow"></div>
								<div class="twipsy-inner">Configuration for the Environment</div>
							</div> -->
	                    </div>
        			</div>
	                
	                <%
	                    List<SettingsTemplate> settingsTemplates = (List<SettingsTemplate>) request.getAttribute(FrameworkConstants.SESSION_SETTINGS_TEMPLATES);
	                %>
	                    
                    <!--  SettingTemplate starts -->
                    <div class="clearfix" id="settingTypeDiv">
                        <label for="type" class="new-xlInput"><s:text name="label.type"/></label>
                        <div class="input new-input">
                        	<div class="typeFields">
	                            <select id="settingsType" name="settingsType" class="selectEqualWidth" onfocus="showToolTip('typeHelpTxt_Stg');">
	                                <%
	                                    if (settingsTemplates != null) {
	                                        for (SettingsTemplate settingsTemplate : settingsTemplates) {
	                                            String type = settingsTemplate.getType();
	                                            if (selectedType != null  && type.equals(selectedType)){
	                                                selectedStr = "selected";
	                                            } else {
	                                                selectedStr = "";
	                                            }
	                                %>
	                                    <option value="<%= type %>"<%= selectedStr %>><%= type %></option>
	                                <%
	                                        }
	                                    }
	                                %>
	                            </select>
							</div>
                            <div>
                                 <div class="lblDesc configSettingHelp-block" id="configTypeErrMsg"></div>
                            </div>
                            <!-- <div class="twipsy bootstrap-right" id="typeHelpTxt_Stg">
								<div class="twipsy-arrow"></div>
								<div class="twipsy-inner">Type of the settings</div>
							</div> -->
                         </div>
                    </div>
                    <!--  SettingTemplate ends -->
	                        
					<div id="type-child-container" class="settings_type_div"></div>
	            <%
	                if(StringUtils.isNotEmpty(fromPage)) {
	            %>
	                    <input type="hidden" id="oldName" name="oldName" value="<%= oldName %>" />
	                    <input type="hidden" id="oldSettingType" name="oldSettingType" value="<%= selectedType %>" />
	            <% } %>
	                
	        	<input type="hidden" id="settingsCount" name="settingsCount" value="" />
            </div>
            <div class="clearfix">
                <label>&nbsp;</label>
                <div class="submit_input">
                    <input type="button" value="<s:text name="label.save"/>" id="save" class="primary btn">
                    <input type="button" value="<s:text name="label.cancel"/>" id="cancelSettings" class="primary btn">
                </div>
            </div>
            <!--  Submit and Cancel button ends -->
        </form>
        <!--  Form ends -->

<%
	if (configInfo != null && StringUtils.isNotEmpty(oldName)) { //persist configInfo to retain while failing on validation 
	    session.setAttribute(oldName, configInfo);
	}
%>    
<script type="text/javascript">

var selectedType = "";

/** To remove all the environments except the selected environment **/
if(<%= StringUtils.isNotEmpty(currentEnv) %>) {
	$('#environments').empty().append($("<option></option>").attr("value",'<%= currentEnv %>').attr("selected", "selected").text('<%= currentEnv %>'));
}

hideAllDesc();

/* To check whether the divice is ipad or not */
if(!isiPad()){
	/* JQuery scroll bar */
	$(".settings_div").scrollbars();
}

$(document).ready(function() {
	
    settingsType();
    
    $('#settingsType').change(function() {
        settingsType();
    });
    
    $("#cancelSettings").click(function() {
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
		showLoadingIcon($("#container")); // Loading Icon
    	performAction("settings", params, $('#container'));
    });
    
    $('#save').click(function() {
    	var params = "";
    	params = params.concat("&remoteDeploy=");
		params = params.concat($("#remoteDeploy").prop("checked")); 
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
   	<%
       if (StringUtils.isEmpty(fromPage)) {
    %>
        performAction("saveSetting", params, $('#container'));
    <%
       } else {
    %>
        performAction("updateSetting", params, $('#container'));
    <% } %>

    });
    
   /* $('#plus').click(function() {
        addType();
    });*/
    window.setTimeout(function () { document.getElementById('xlInput').focus(); }, 250);
});
 
function settingsType() {
    selectedType = $("#settingsType").val();
    var settingsCount = $("#settingsCount").val();
    var fromPage = "<%= fromPage %>";
	var params = "";
	if (!isBlank($('form').serialize())) {
		params = $('form').serialize() + "&";
	}
	params = params.concat("envName=");
	params = params.concat('<%= envName %>');
	params = params.concat("&" + '<%= FrameworkConstants.REQ_FROM_PAGE %>' + "=");
	params = params.concat(fromPage);
	performAction('settingsType', params, '', true);
}

function successSettingType(data) {
	$("#type-child-container").html(data);
    if (selectedType == "Server") {
    	$("#xlInput").focus();            	
    } else {
        $("#Host").focus();
        $("#Protocol").focus();
    }
}

function addType() {
    var clonedContainer = $('#type-container').clone();
    
    clonedContainer.children(true, true).each(function() {
        var kid = $(this);
        console.log(kid.attr('id'));
    });
    
    $('#type-parent').append(clonedContainer);
}

function validationError(data) {
	$(".clearfix").removeClass("error");
	$(".lblDesc").text("");
	if(data.nameError != null){
		$("#nameErrMsg").text(data.nameError);
    	$("#nameErrDiv").addClass("error");
    	$("#xlInput").focus(); 
	} 
	
	if(data.envError != null){
		$("#envErrMsg").text(data.envError);
    	$("#envDiv").addClass("error");
	}
	
	if(data.typeError != null){
        $("#configTypeErrMsg").text(data.typeError);
        $("#settingTypeDiv").addClass("error");
    }
	
	if(data.dynamicError != null) {
    	var dynamicErrors = data.dynamicError.split(",");
    	
    	for (var i=0; i<dynamicErrors.length; i++) {
    		var dynErr = dynamicErrors[i].split(":");
     		$("div[id='" + dynErr[0] + "']").addClass("error");
     		if (i == 0) {
     			$("input[name='" + dynErr[0] + "']").focus();     			
     		}
        	$("div[id='" + dynErr[0] + "ErrorDiv']").html(dynErr[1]);
    	}
	}

	if(data.portError != null) {
		$("div[id='port']").addClass("error");
    	$("div[id='portErrorDiv']").html(data.portError);
   }
	
	if(data.appliesToError != null) {
		$("#appliesToErrMsg").html(data.appliesToError);
    	$("#appliesToErrDiv").addClass("error");
    	if (data.dynamicError == null) {
    		$("#appliesto").focus();
    	}
	}
	
	if(data.emailError != null) {
		$("div[id='emailid']").addClass("error");
    	$("div[id='emailidErrorDiv']").html(data.emailError);
    }
}

function showToolTip(toolTipId) {
	hideAllDesc();
	$("#"+toolTipId).show();
	setTimeout(function(){
	    $("#"+toolTipId).fadeOut("slow", function () {
	    });
	     
	}, 2000);
}

function hideAllDesc() {
	$(".twipsy").hide();
}

function successEvent(pageUrl, data) {
	if(pageUrl == "settingsType") {
		successSettingType(data);
	}
	if (pageUrl == "fetchSettingProjectInfoVersions") {
		fillVersions("version", data.projectInfoVersions);
		if (versionFrom == "") {
			showSetttingsInfoVersion();				
		}
	}
}

function getCurrentVersions(from) {
	versionFrom = from;
	if (versionFrom == "") {
		showSetttingsInfoServer();			
	}
	var settingType = $("#settingsType").val();
	var name = $('#type').val();
    var params = "";
	params = params.concat("&settingsType=");
	params = params.concat(settingType);
	params = params.concat("&name=");
	params = params.concat(name);
	performAction('fetchSettingProjectInfoVersions', params, '', true);
}
</script>