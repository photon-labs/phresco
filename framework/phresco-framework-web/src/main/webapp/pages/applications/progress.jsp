<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>

<div class="popup_Modal testCmdLine" id="build-outputOuter">
	<form name="testSuiteCall" action="functional" method="post" id="testSuiteCall" class="marginBottomZero">
		<div class="modal-header repo_modal_header">
		    <h3><s:text name="label.progress"/></h3>
		    <a class="close" href="#" id="closeGenerateTest">&times;</a>
		</div>
	</form>
    
    <div id="build-output" class="testCmdLineInner">
 
    </div>
    
    <div class="modal-footer">
	    <div class="action popup-action">
	    	<div style="float: left; display: block;" id="loadingDiv">
				<img src="" class="popupLoadingIcon">
				<!-- <span style="display: block;text-align: right; margin-top: -26px; margin-left: 37px;">Progressing... </span> -->
	    	</div>
	     	<input type="button" class="btn primary" value="<s:text name="label.close"/>" id="closeGenTest">
	    </div>
	</div>
</div>

<script>
	var popupHeight = -(($(".popup_Modal").height()/2)-4);
	$(".popup_Modal").css("margin-top", popupHeight +"px");
	escPopup();
	function closePopup() {
		$(".wel_come").show().css("display","none");
	   	$("#build-outputOuter").hide();
	}
	
	$('#performance-popup').css("margin-top", "-300px");

</script>