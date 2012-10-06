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
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="com.photon.phresco.model.UserInfo" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>

<% 
	UserInfo userInfo = (UserInfo)session.getAttribute(FrameworkConstants.REQ_USER_INFO); 
	String repoUrl = (String)request.getAttribute(FrameworkConstants.REPO_URL);
	String fromTab = (String)request.getAttribute(FrameworkConstants.REQ_FROM_TAB);
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
%>

<div class="popup_Modal topFifty" id="repoDet">
    <form name="repoDetails" action="import" method="post" autocomplete="off" class="repo_form">
        <div class="modal-header">
            <h3><s:text name="label.repository"/></h3>
            <a class="close" href="#" id="close">&times;</a>
        </div>
        
        <div class="modal-body">
        
			<!--   import from type -->
			<div id="typeInfo">
	        	<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><s:text name="label.svn.type"/></label>
					
					<div class="input">
						<select name="repoType" class="medium" >
							<option value="<s:text name="label.repo.type.svn"/>" selected ><s:text name="label.repo.type.svn"/></option>
							<option value="<s:text name="label.repo.type.git"/>"><s:text name="label.repo.type.git"/></option>
					    </select>
					</div>
				</div>
			</div>
			
        	<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.repository.url"/></label>
				<div class="input">
					<input type="text" class="svnUrlTxtBox" name="repourl" id="repoUrl" value="<%= StringUtils.isEmpty(repoUrl) ? "http://" : repoUrl %>">&nbsp;&nbsp;<span id="missingURL" class="missingData"></span>
				</div>
			</div>
			
			<div id="otherCredentialInfo">
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"> <s:text name="label.other.credential"/></label>
					<div class="input checkFn">
					   <input type="checkbox" name = "credential" class = "credentials" id="credentials" style="margin-top:8px;" />
					</div>
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red credentialDet">*</span> <s:text name="label.username"/></label>
				<div class="input">
					<input type="text" name="username" id="userName" maxlength="63" title="63 Characters only">&nbsp;&nbsp;<span id="missingUsername" class="missingData"></span>
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red credentialDet">*</span> <s:text name="label.password"/></label>
				<div class="input">
					<input type="password" name="password" id="password" maxlength="63" title="63 Characters only">&nbsp;&nbsp;<span id="missingPassword" class="missingData"></span>
				</div>
			</div>
			
			<div id="svnRevisionInfo">
				<div class="clearfix">
					<label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.revision"/></label>
					<div class="input"  style="padding-top:8px;">
						<input id="revisionHead" type="radio" name="revision" value="HEAD" checked/>&nbsp; HEAD Revision
					</div>
					<div class="input">
						<input id="revision" type="radio" name="revision" value="revision"/> &nbsp;Revision &nbsp; &nbsp; &nbsp; &nbsp;<input id="revisionVal" type="text" name="revisionVal" maxLength="10" title="10 Characters only" disabled>
					</div>
					<div class="input" style="padding-top:5px;">
						<span id="missingRevision" class="missingData"></span>
					</div>
				</div>
			</div>
			
<!-- 			<div id="branchInfo"> -->
<!-- 				<div class="clearfix"> -->
<%-- 					<label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.branch"/></label> --%>
<!-- 					<div class="input"> -->
<%-- 						<input type="text" name="branch" id="branch" value="master" maxlength="63" title="63 Characters only">&nbsp;&nbsp;<span id="missingBranch" class="missingData"></span> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
        </div>
        <div class="modal-footer">
            <img class="popupLoadingIcon" style="position: relative; float: left; display: none;"> 
            <input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
            <input type="button" id="svnImport" class="btn primary" value="<%= StringUtils.isEmpty(fromTab) ? "Import" : "Update" %>">
            <div id="errMsg" class="envErrMsg"></div>
            <div id="reportMsg" class="envErrMsg"></div>
       </div>
             
    </form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("#repoUrl").focus();
		
		// svn import already selected value display 
		if(localStorage["svnImport"] != null && !isBlank(localStorage["svnImport"])) {
			$('#credentials').attr("checked", true);
			svnCredentialMark();
		} else {
			svnCredentialMark();
		}
		
        $("#repoUrl").keyup(function(event) {
         	var repoUrl = $("input[name='repourl']").val();
        });
        
        $("#repoUrl").blur(function(event) {
        	if ($("[name=repoType]").val() == 'svn') {
        		urlBasedAction();
        	} else if ($("[name=repoType]").val() == 'git') {
        		enableSvnFormDet();
        	}
        });
        
        $('#revisionVal').bind('input propertychange', function (e) { 
        	var revisionVal = $(this).val();
        	revisionVal = checkForRevision(revisionVal);
        	$(this).val(revisionVal);
         });
        
		$('#close, #cancel').click(function() {
			showParentPage();
		});

		$('#revision').click(function() {
		    $("#revisionVal").removeAttr("disabled");
		});
	  
		$('#revisionHead').click(function() {
		    $('#revisionVal').attr("disabled", "disabled");
		});
		
		$("#userName").bind('input propertychange',function(e){ 	//envName validation
	     	var name = $(this).val();
	     	name = isContainSpace(name);
	     	$(this).val(name);
	    });
		
		$('#svnImport').click(function() {
			var action = getAction();
			
			$("#errMsg").html("");
			var repoUrl = $("input[name='repourl']").val();
			$('.missingData').empty();
			// When isValidUrl returns false URL is missing information is displayed
			if(isValidUrl(repoUrl)){
				$("#errMsg").html("URL is missing");
				$("#repoUrl").focus();
				return false;
			}
			
			// if it is svn need to validate username and password fields
			if($("[name=repoType]").val() == 'svn') {
				
				if(isBlank($.trim($("input[name='username']").val()))) {
					$("#errMsg").html("Username is missing");
					$("#userName").focus();
					$("#userName").val("");
					return false;
				}
				
				if(isBlank($.trim($("input[name='password']").val()))) {
					$("#errMsg").html("Password is missing");
					$("#password").focus();
					return false;
				}
				
				// the revision have to be validated
				if($('input:radio[name=revision]:checked').val() == "revision" && (isBlank($.trim($('#revisionVal').val())))) {
					$("#errMsg").html("Revision is missing");
					$("#revisionVal").focus();
					$("#revisionVal").val("");
					return false;
				}
			} else if($("[name=repoType]").val() == 'git') {
// 				if(isBlank($.trim($("input[name='branch']").val()))) {
// 					$("#errMsg").html("Branch name is missing");
// 					$("#branch").focus();
// 					return false;
// 				}
			}
			
			// before form submit enable textboxes
			enableSvnFormDet();
			
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
	    	params = params.concat("projectCode=");
	    	params = params.concat("<%= projectCode %>");
	    	// do import or update operation
			$('.popupLoadingIcon').show();
			getCurrentCSS();
			performAction(action, params, '', true);

		});
		
 		$('#credentials').click(function() {
 			svnCredentialMark();
 		});
 		
 		$('[name=repoType]').change(function() {
 			extraInfoDisplay();
 		});
 		
		$('#closeGenerateTest, #closeGenTest').click(function() {
			performAction('applications', '', $("#container"));
			$("#popup_div").css("display","none");
			$("#popup_div").empty();
        });
		
		extraInfoDisplay();
		
 		<%
 			if (StringUtils.isNotEmpty(repoUrl) && repoUrl.contains(FrameworkConstants.GIT)) { //git config
 		%>
 			$('.credentialDet').hide();
 			$("[name=repoType] option[value='git']").attr('selected', 'selected');
 			$('#typeInfo, #branchInfo, #svnRevisionInfo, #otherCredentialInfo').hide();
 			enableSvnFormDet();
 		<%
 			} else if (StringUtils.isNotEmpty(repoUrl)) { //svn config
 		%>
 			$('.credentialDet').show();
 			$("[name=repoType] option[value='svn']").attr('selected', 'selected');
 			$('#typeInfo, #branchInfo').hide();
 		<%
 			}
 		%>
 		
	});
	
	function getAction() {
		var actionUrl = "";
		if ($("[name=repoType]").val() == 'svn') {
			actionUrl = actionUrl + "SVNProject";
		}
		if ($("[name=repoType]").val() == 'git') {
			actionUrl = actionUrl + "GITProject";
		}
		if ($('#svnImport').val() == "Import") {
			actionUrl = "import" + actionUrl;
		} else if ($('#svnImport').val() == "Update") {
			actionUrl = "update" + actionUrl;
		}
		
		return actionUrl;
	}
	
	//base on the repo type credential info need to be displayed
	function extraInfoDisplay() {
		$("#errMsg").html("");
		if($("[name=repoType]").val() == 'svn') {
			$('.credentialDet').show();
			$('#svnRevisionInfo').show();
			$('#branchInfo').hide();
 			// hide other credential checkbox
			$('#otherCredentialInfo').show();
 			// to make check box untick (fill with insight username and password)
			urlBasedAction();
		} else if($("[name=repoType]").val() == 'git') {
			$('.credentialDet').hide();
			$('#svnRevisionInfo').hide();
			$('#branchInfo').show();
 			// hide other credential checkbox
			$('#otherCredentialInfo').hide();
			enableSvnFormDet();
		}
	}
	
	function urlBasedAction() {
     	var repoUrl = $("input[name='repourl']").val();
       	if (repoUrl.indexOf('insight.photoninfotech.com') != -1) {
			$('#credentials').attr("checked", false);
       		svnCredentialMark();
       	} else if(!isBlank(repoUrl)) {
			$('#credentials').attr("checked", true);
       		svnCredentialMark();
       	}
	}
	
	function svnImportError(id, errMsg){
		$("#missing" + id ).empty();
		$("#missing" + id ).append(errMsg);
	}
	
	function fetchJSONData(data){                                  
		if(data.svnImport){                                              // Import Project Success
			$("#errMsg").empty();
			$('.popupLoadingIcon').hide();
			if(data.svnImportMsg == "<%= FrameworkConstants.IMPORT_SUCCESS_PROJECT%>") {
				$("#reportMsg").html(data.svnImportMsg);
			} else {
				$("#errMsg").html(data.svnImportMsg);
			}
			performAction('applications', '', $("#container"));
			setTimeout(function(){ $("#popup_div").hide(); }, 200);
		} else{                                                         // Import Project Fails
			$("#errMsg").empty();
			$('.popupLoadingIcon').hide();
			$("#errMsg").html(data.svnImportMsg);
		}
	}
	
	function successEvent(pageUrl, data){
		if(pageUrl == "importSVNProject" || pageUrl == "importGITProject" || pageUrl == "updateSVNProject" || pageUrl == "updateGITProject"){
			fetchJSONData(data);
		}
	}
	
	function svnCredentialMark() {
		if($('#credentials').is(':checked')) {
			enableSvnFormDet();
			$("#userName").val('');
	 		$("#password").val('');
	 		localStorage["svnImport"] = "credentials";
	 	} else {
 			$("#userName").val('<%= userInfo.getCredentials().getUsername() %>');
 			<%
 				byte[] decodedBytes = Base64.decodeBase64(userInfo.getCredentials().getPassword());
 				String password = new String(decodedBytes);
 			%>
 			$("#password").val('<%= password %>');
 			disableSvnFormDet();
 			localStorage["svnImport"] = "";
	 	}
	}
	
	function enableSvnFormDet() {
 		enableControl($("input[name='password']"), "");
 		enableControl($("input[name='username']"), "");
	}
	
	function disableSvnFormDet() {
 		disableControl($("input[name='password']"), "");
 		disableControl($("input[name='username']"), "");
	}
</script>