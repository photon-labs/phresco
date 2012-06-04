<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.io.File"%>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>

<%@include file="../progress.jsp" %>

<style type="text/css">
	.testSuiteDisplay {
	    height: 98%;
	    position: relative;
	    top: -32px;
	}
	.testSuiteListAdj {
	    margin-bottom: 8px;
	    margin-top: 6px;
	    position: relative;
	     top: -30px;
	 }
</style>

<form action="load" method="post" autocomplete="off" class="marginBottomZero">
   <!--  <div class="frame-header frameHeaderPadding btnTestPadding"> -->
     <div class="operation">
            <input id="testbtn" type="button" value="<s:text name="label.test"/>" class="primary btn env_btn">
        <div class="icon_fun_div">
			<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder" /></a>
			<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
		</div>
    </div>
</form>

<!-- load test button ends -->
<% 
	Boolean popup = Boolean.FALSE;
	Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
   	String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
    String technology =  project.getProjectInfo().getTechnology().getName();
	if (TechnologyTypes.ANDROIDS.contains(project.getProjectInfo().getTechnology().getId())) {
		popup = Boolean.TRUE;
	}
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
   	if(testError != null) {
%>
    <div class="alert-message block-message warning" style="margin: 5px 5px 0;">
		<center><label class="errorMsgLabel"><%= testError %></label></center>
	</div>
	
<% } else {
        File[] files = (File[])request.getAttribute(FrameworkConstants.REQ_JMETER_REPORT_FILES); 
%>
    
    <div class="functional_header testSuiteList testSuiteListAdj"><strong><s:text name="label.test.results"/></strong> 
        <select id="testResults" name="testResults" class="testList"> 
            <% 
            if(files != null) {
                for(File file : files) {
            %>
              <option value="<%= file.getName() %>" ><%= file.getName() %> </option>
            <% 
                }
            }
            %>
        </select>
    </div>
    
    <div id="testResultDisplay" class="testSuiteDisplay">
    </div>
    
	<script type="text/javascript">
		$(document).ready(function() {
	    	loadTestResults();
	    	
	    	$('#testResults').change(function() {
	    		loadTestResults();
	    	});
	    });
	
	    function loadTestResults() {
	        var testResult = $("#testResults").val();
	        $("#testResultDisplay").empty();
     	    var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
	 		params = params.concat("testType=");
			params = params.concat('<%= testType%>');
			params = params.concat("&testResultFile=");
			params = params.concat(testResult);
			getCurrentCSS();
	        $('.popupLoadingIcon').show();
			performAction('loadTestResult', params, $('#testResultDisplay'));
	    }
	</script>
<% } %>

<!-- <div class="popup_div" id="generateJmeter">
</div> -->
	

<script type="text/javascript">
    
	    $(document).ready(function() {
	    	
	    	//Disable test button for load
	    	if(<%= popup %>){
	    		disableControl($("#testbtn"), "btn disabled");	
	    	}
	    	
	        $('#testbtn').click(function() {
	        	$("#popup_div").empty(); // remove perfromance html data and to avoid name conflict with load test
			 	if(<%= popup %>){
					openAndroidPopup();
				} else {
					generateJmeter('<%= testType %>');
				}
	        });
	        $('#openFolder').click(function() {
	             openFolder('<%= projectCode %><%= path %>');
	         });
	         
	         $('#copyPath').click(function() {
	            copyPath('<%= projectCode %><%= path %>');
	         });
	    });
    
       
	    function generateJmeter(testType) {
			showPopup();
			$('#popup_div').empty();
			var params = "testType=";
			params = params.concat(testType);
			popup('generateJmeter', params, $('#popup_div'));
			escPopup();
        }
        
        $('#closeGenerateTest, #closeGenTest').click(function() {
			changeTesting('<%= testType %>');
			enableScreen();
		});
        
		function openAndroidPopup(){
			$('#popup_div').empty();
			showPopup();
			var params = "testType=";
			params = params.concat("load");
			popup('testAndroid', params, $('#popup_div'));
		}
		
    </script>