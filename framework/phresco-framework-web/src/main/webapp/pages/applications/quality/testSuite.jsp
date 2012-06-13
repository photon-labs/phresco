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

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.photon.phresco.framework.model.TestCaseFailure"%>
<%@page import="com.photon.phresco.framework.model.TestCaseError"%>
<%@page import="com.photon.phresco.framework.model.TestSuite"%>
<%@page import="com.photon.phresco.framework.model.TestCase"%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@page import="com.photon.phresco.model.SettingsInfo"%>

<style type="text/css">
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

	<% 
		List<TestCase> testCases = (List<TestCase>) request.getAttribute(FrameworkConstants.REQ_TESTCASES);
		String testSuiteName = (String) request.getAttribute(FrameworkConstants.REQ_TESTSUITE_NAME);
		float failures = Float.parseFloat((String) request.getAttribute(FrameworkConstants.REQ_TESTSUITE_FAILURES));
		float errors  = Float.parseFloat((String) request.getAttribute(FrameworkConstants.REQ_TESTSUITE_ERRORS));
		float tests  = Float.parseFloat((String) request.getAttribute(FrameworkConstants.REQ_TESTSUITE_TESTS)); 
        String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
        String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE); 
        if(testError != null) {
	%>
        <div class="alert-message block-message warning" >
            <center><label class="errorMsgLabel"><%= testError %></label></center>
        </div>
    <%
        } else {
			
            float success = 0;
            
            if (failures != 0 && errors == 0) {
                if (failures > tests) {
                    success = failures - tests;
                } else {
                    success = tests - failures;
                }
            } else if (failures == 0 && errors != 0) {
                if (errors > tests) {
                    success = errors - tests;
                } else {
                    success = tests - errors;
                }
            } else if (failures != 0 && errors != 0) {
                float failTotal = (failures + errors);
                if (failTotal > tests) {
                    success = failTotal - tests;
                } else {
                    success = tests - failTotal;
                }
            } else {
            	success = tests;
            }

            float total = tests;

            int failurePercentage = (int) (Math.round((failures / total) * 100));
            int errorsPercentage = (int) (Math.round((errors / total) * 100));
            int successPercentage = (int) (Math.round((success / total) * 100));

    %>

    <script>
		$(document).ready(function() {
			canvasInit();
		});
	
        function canvasInit() {
            var failurePercent = '<%= failurePercentage %>';
            var errorsPercent = '<%= errorsPercentage %>';
            var successPercent = '<%= successPercentage %>';

            var pie2 = new RGraph.Pie('pie2', [ parseInt(failurePercent), parseInt(errorsPercent), parseInt(successPercent)]); // Create the pie object
            //var pie2 = new RGraph.Pie('pie2', [60,0,40]); // Create the pie object

            pie2.Set('chart.gutter.left', 45);
            pie2.Set('chart.colors', ['orange', 'red', '#6f6']);
            pie2.Set('chart.key', ['Failures (<%= failurePercentage %>%)[<%= (int) failures %>]', 'Errors (<%= errorsPercentage %>%)[<%= (int) errors %>]', 'Success (<%= successPercentage %>%)[<%= (int) success %>]', 'Total (' + parseInt(<%= total %>) + ' Tests)']);
            pie2.Set('chart.key.background', 'white');
            pie2.Set('chart.strokestyle', 'white');
            pie2.Set('chart.linewidth', 3);
            pie2.Set('chart.title', '<%= testSuiteName %> Report');
            pie2.Set('chart.title.size',10);
            pie2.Set('chart.title.color', '#8A8A8A');
            pie2.Set('chart.exploded', [5,5,0]);
            pie2.Set('chart.shadow', true);
            pie2.Set('chart.shadow.offsetx', 0);
            pie2.Set('chart.shadow.offsety', 0);
            pie2.Set('chart.shadow.blur', 25);
            pie2.Set('chart.radius', 100);
            pie2.Set('chart.background.grid.autofit',true);
			/* console.info(pie2); */
            if (RGraph.isIE8()) {
                pie2.Draw();
            } else {
                RGraph.Effects.Pie.RoundRobin(pie2);
            }
    </script>
	
				<div class="table_div_unit qtyTable_view" id="tabularView">
	                <div class="fixed-table-container responsiveFixedTableContainer qtyFixedTblContainer">
	      				<div class="header-background"> </div>
			      		<div class="fixed-table-container-inner">
			      		<div style="overflow: auto;">
					        <table cellspacing="0" class="zebra-striped">
					          	<thead>
						            <tr>
										<th class="first">
						                	<div class="th-inner"><s:text name="label.name"/></div>
						              	</th>
						              	<th class="second">
						                	<div class="th-inner"><s:text name="label.class"/></div>
						              	</th>
						              	<th class="third">
						                	<div class="th-inner"><s:text name="label.time"/></div>
						              	</th>
						              	<th class="third">
						                	<div class="th-inner"><s:text name="label.status"/></div>
						              	</th>
						              	<th class="third">
						                	<div class="th-inner"><s:text name="label.log"/></div>
						              	</th>
						              	<% 
						              		if(FrameworkConstants.FUNCTIONAL.equals(testType)) { 
						              	%>
						              	<th class="width-ten-percent">
						                	<div class="th-inner"><s:text name="label.screenshot"/></div>
						              	</th>
						              	<% 
						              		} 
						              	%>
						            </tr>
					          	</thead>
					
					          	<tbody>
					          	<%
						          	for (TestCase testCase : testCases) {
										TestCaseFailure failure = testCase.getTestCaseFailure();
										TestCaseError error = testCase.getTestCaseError(); 	
								%>
					            	<tr>
					              		<td id="tstRst_td1" class="width-twenty-five-percent"><%= testCase.getName() %></td>
					              		<td id="tstRst_td2" class="width-twenty-five-percent"><%= testCase.getTestClass() == null ? "" : testCase.getTestClass() %></td>
					              		<td class="width-fifteen-percent"><%= testCase.getTime() == null ? "" : testCase.getTime() %></td>
					              		<td class="width-fifteen-percent">
					              			<% if (testCase.getTestCaseFailure() != null) { %>
												<img src="images/icons/failure.png" title="Failure">
											<% } else if (testCase.getTestCaseError() != null) { %>
												<img src="images/icons/error.png" title="Error">
											<% } else { %>
												<img src="images/icons/success.png" title="Success">
											<% } %>  
					              		</td>
					              		<td class="width-ten-percent">
					              			<% if (testCase.getTestCaseFailure() != null) { %>
												<input type="hidden" name="<%= testCase.getName() %>" value="<%= testCase.getTestCaseFailure().getFailureType()%>,<%= testCase.getTestCaseFailure().getDescription()%>" id="<%= testCase.getName() %>">
												<a class="testCaseFailOrErr" name="<%= testCase.getName() %>" href="#"><img src="images/icons/log.png" alt="logo"> </a>
											<% } else if (testCase.getTestCaseError() != null) { %>
												<input type="hidden" name="<%= testCase.getName() %>" value="<%= testCase.getTestCaseError().getErrorType()%>,<%= testCase.getTestCaseError().getDescription()%>" id="<%= testCase.getName() %>">
												<a class="testCaseFailOrErr" name="<%= testCase.getName() %>" href="#"><img src="images/icons/log.png" alt="logo"> </a>
											<% } else { %>
												&nbsp;
											<% } %>
					              		</td>
					             		<% 
						              		if(FrameworkConstants.FUNCTIONAL.equals(testType)) { 
						              	%>
					            		<td class="width-ten-percent">
					            			<% 
					            				if(testCase.getTestCaseFailure() != null || testCase.getTestCaseError() != null)  { 
					            			%>
					            				<a class="testCaseScreenShot" name="<%= testCase.getName() %>" href="#"><img src="images/icons/screenshot.png" alt="logo"> </a>
<%-- 					            				<a name="<%= testCase.getName() %>" href="<%= request.getContextPath()%>/getScreenShotImage.action?projectCode=<%= (String) request.getAttribute("projectCode")%>&testCaseName=<%= testCase.getName() %>"><img src="images/icons/log.png" alt="screenshot"> </a> --%>
					            			<% 
					            				}
					            			%>
					              		</td>
					             		<% 
						              		} 
						              	%>
					            	</tr>
					            <%
									}
								%>	
					          	</tbody>
					        </table>
					       
					       </div>
					    <div>
							
						</div>
			      		</div>
    				</div>
	            </div>
	
                <div class="canvas_div canvasDiv" id="graphicalView">
                    <canvas id="pie2" width="620" height="335">[No canvas support]</canvas>
                </div>
			</div>
    	</div>
    
    <!-- Test case error or Test case failure starts -->	
	<div id="testCaseErrOrFail" class="modal abtDialog">
		<div class="modal-header">
			<div class="TestType"></div><a id="closeTestCasePopup" href="#" class="close">&times;</a>
		</div>
		<div class="abt_div">
<!-- 			<div id="testCaseType" class="testCaseType"> -->
					
<!-- 			</div> -->
			<div id="testCaseDesc" class="testCaseDesc">
					
			</div>
		</div>
		
		<div class="modal-footer">
			<div class="action abt_action">
				<input type="button" class="btn primary" value="<s:text name="label.close"/>" id="closeDialog">
			</div>
		</div>
	</div>
	<!-- Test case error or Test case failure starts -->
	
	<!-- 
		Screen shot pop-up starts
	 -->
	<div id="testCaseScreenShotPopUp" class="modal screenShotDialog">
		<div class="modal-header">
			<div class="screenShotTCName"></div><a id="closeTCScreenShotPopup" href="#" class="close">&times;</a>
		</div>
		<div class="abt_div">
			<div id="testCaseDesc" class="testCaseImg">
					<div id="imgNotFoundErr" style="hideContenthideContenthideContenthideContenthideContenthideContenthideContent"><b>Screenshot is not available</b></div>
					<img class="testCaseImg" id="screenShotImgSrc" src="" title="screenShot"  height= "100px" width= "100px"></img>
			</div>
		</div>
		
		<div class="modal-footer">
			<div class="action abt_action">
				<input type="button" class="btn primary" value="<s:text name="label.close"/>" id="closeTCScreenShotPopupDlg">
			</div>
		</div>
	</div>
	<!-- Screen shot pop-up ends -->
	
	<script type="text/javascript">
	/* To check whether the divice is ipad or not */
	if(!isiPad()) {
		/* JQuery scroll bar */
		$("#graphicalView").scrollbars();
	}
	
    $(document).ready(function() {
    	
    	changeView();
    	
        $("td[id = 'tstRst_td1']").text(function(index) {
            return textTrim($(this));
        });
        
        $("td[id = 'tstRst_td2']").text(function(index) {
            return textTrim($(this));
        });
        
    	$(".testCaseFailOrErr").click(function(){
    	   	var name = $(this).attr('name');
            var errValue = window.document.getElementById(name).value;
    	   	var testCaseErrAndFail = errValue;
    	   	var results = testCaseErrAndFail.split(",");
    	   	var testCaseErrorOrFailName = results[0];
    	   	var testCaseErrorOrFailDesc = results[1];
    	   	$('.TestType').html(testCaseErrorOrFailName);
    	   	$('.testCaseDesc').html(testCaseErrorOrFailDesc);
    	   	funcPopUp('block', 'testCaseErrOrFail');
    	});
        
    	$('#closeTestCasePopup').click(function() {
    		funcPopUp('none', 'testCaseErrOrFail');
    	});
    	
    	$('#closeDialog').click(function() {
    		$(".wel_come").show().css("display", "none");
    		$("#testCaseErrOrFail").show().css("display", "none");
    	});
    	
    	$(".testCaseScreenShot").click(function() {
    		// Before screen shot loading , have to hide No Screenshot available message
    		$("#screenShotImgSrc").attr("src", "");
    		hideImageIsNotLoaded();
    		
    		// This code loads image
    		var testCaseName = $(this).attr('name');
			$('.screenShotTCName').html(testCaseName);
			$("#screenShotImgSrc").attr("src", "<%= request.getContextPath()%>/getScreenshot.action?projectCode=<%= (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE)%>&testCaseName=" + testCaseName);
			funcPopUp('block', 'testCaseScreenShotPopUp');
			
			// Based on screenshot loading , have to call methods
			$(".testCaseImg").load(function() { hideImageIsNotLoaded(); })
		    .error(function() { showImageIsNotLoaded(); });

    	});
    	
    	$('#closeTCScreenShotPopup, #closeTCScreenShotPopupDlg').click(function() {
    		funcPopUp('none', 'testCaseScreenShotPopUp');
    	});
    	
    	// table resizing
		var tblheight = (($("#subTabcontainer").height() - $("#form_test").height()));
		$('.responsiveTableDisplay').css("height", parseInt((tblheight/($("#subTabcontainer").height()))*100) +'%');
		
		var fixedTblheight = ((($('#tabularView').height() - 30) / $('#tabularView').height()) * 100);
		$('.responsiveFixedTableContainer').css("height", fixedTblheight+'%');
		 
		// jquery affects pie chart responsive
		window.setTimeout(function () { $(".scroll-content").css("width", "100%"); }, 250);
	    	
    });
    
    function showImageIsNotLoaded() {
    	$("#imgNotFoundErr").css("display", "block");
    }
    
    function hideImageIsNotLoaded() {
    	$("#imgNotFoundErr").css("display", "none");	
    }
    
    function funcPopUp(enableProp, popup) {
    	$(".wel_come").show().css("display", enableProp);
    	$("#" + popup).show().css("display", enableProp);
    }
    
    function textTrim(obj) {
        var val = $(obj).text();
        $(obj).attr("title", val);
        var len = val.length;
        if(len > 10) {
            val = val.substr(0, 30) + "...";
            return val;
        }
        return val;
    }
	</script>
<% } %>