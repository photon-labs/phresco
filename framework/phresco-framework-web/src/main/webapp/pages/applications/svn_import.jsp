<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="com.photon.phresco.commons.model.User" %>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>

<% User userInfo = (User)session.getAttribute(FrameworkConstants.REQ_USER_INFO); %>

<div class="popup_Modal topFifty" id="repoDet">
    <form name="repoDetails" action="import" method="post" autocomplete="off" class="repo_form">
        <div class="modal-header">
            <h3><s:text name="label.repository"/></h3>
            <a class="close" href="#" id="close">&times;</a>
        </div>
        
        <div class="modal-body">
        	<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.repository.url"/></label>
				<div class="input">
					<input type="text" class="svnUrlTxtBox" name="repourl" id="repoUrl">&nbsp;&nbsp;<span id="missingURL" class="missingData"></span>
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"> <s:text name="label.other.credential"/></label>
				<div class="input checkFn">
				   <input type="checkbox" name = "credential" class = "credentials" id="credentials" style="margin-top:8px;" />
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.username"/></label>
				<div class="input">
					<input type="text" name="username" id="userName" maxlength="63" title="63 Characters only">&nbsp;&nbsp;<span id="missingUsername" class="missingData"></span>
				</div>
			</div>
			
			<div class="clearfix">
				<label for="xlInput" class="xlInput popup-label"><span class="red">*</span> <s:text name="label.password"/></label>
				<div class="input">
					<input type="password" name="password" id="password" maxlength="63" title="63 Characters only">&nbsp;&nbsp;<span id="missingPassword" class="missingData"></span>
				</div>
			</div>
			
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
        <div class="modal-footer">
            <img class="popupLoadingIcon" style="position: relative;"> 
            <input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
            <input type="button" id="svnImport" class="btn primary" value="<s:text name="label.ok"/>">
            <div id="errMsg" class="envErrMsg"></div>
       </div>
             
    </form>
</div>

<script type="text/javascript">
	$(document).ready(function(){
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
         	var repoUrl = $("input[name='repourl']").val();
         	if (repoUrl.indexOf('insight.photoninfotech.com') != -1) {
           		$('#credentials').attr("checked", false);
           		svnCredentialMark();
           	} else if(!isBlank(repoUrl)) {
           		$('#credentials').attr("checked", true);
           		svnCredentialMark();
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

			$("#errMsg").html("");
			var repoUrl = $("input[name='repourl']").val();
			$('.missingData').empty();
			// When isValidUrl returns false URL is missing information is displayed
			if(isValidUrl(repoUrl)){
				$("#errMsg").html("URL is missing");
				$("#repoUrl").focus();
				$("#repoUrl").val("");
				return false;
			}
			if(isBlank($.trim($("input[name='username']").val()))){
				$("#errMsg").html("Username is missing");
				$("#userName").focus();
				$("#userName").val("");
				return false;
			}
			if(isBlank($.trim($("input[name='password']").val()))){
				$("#errMsg").html("Password is missing");
				$("#password").focus();
				return false;
			}
			if($('input:radio[name=revision]:checked').val() == "revision" && (isBlank($.trim($('#revisionVal').val())))){
				$("#errMsg").html("Revision is missing");
				$("#revisionVal").focus();
				$("#revisionVal").val("");
				return false;
			}
			// before form submit enable textboxes
			enableSvnFormDet();
			$('.popupLoadingIcon').show();
			getCurrentCSS();
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
            performAction('importcredentialdetails', params, '', true);
		});
		
		
 		$('#credentials').click(function() {
 			svnCredentialMark();
 		});
 		
	});
	
	function svnImportError(id, errMsg){
		$("#missing" + id ).empty();
		$("#missing" + id ).append(errMsg);
	}
	
	function fetchJSONData(data){                                  
		if(data.svnImport){                                              // Import Project Success
			$("#errMsg").empty();
			$('.popupLoadingIcon').hide();
			$("#errMsg").html(data.svnImportMsg);
			performAction('applications', '', $("#container"));
			setTimeout(function(){ $("#popup_div").hide(); }, 200);
		} else{                                                         // Import Project Fails
			$("#errMsg").empty();
			$('.popupLoadingIcon').hide();
			$("#errMsg").html(data.svnImportMsg);
		}
	}
	
	function successEvent(pageUrl, data){
		if(pageUrl == "importcredentialdetails"){
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
 			$("#userName").val('<%= userInfo.getName() %>');
<%--  			$("#password").val('<%= userInfo.getCredentials().getPassword() %>'); --%>
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