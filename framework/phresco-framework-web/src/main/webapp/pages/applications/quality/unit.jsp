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

<%@include file="../progress.jsp" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.framework.model.TestSuite"%>
<%@ page import="com.photon.phresco.framework.model.TestCase"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>

<script src="js/reader.js" ></script>
<%
	Boolean popup = Boolean.FALSE;
	//Project project = (Project)session.getAttribute(FrameworkConstants.SESSION_PROJECT);
	Project project = (Project)request.getAttribute(FrameworkConstants.REQ_PROJECT);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	String projectCode = project.getProjectInfo().getCode();
	String technology =  project.getProjectInfo().getTechnology().getName();
	String techId = project.getProjectInfo().getTechnology().getId();
	if (TechnologyTypes.ANDROIDS.contains(techId)) {
		popup = Boolean.TRUE;
	}
	String path = (String) request.getAttribute(FrameworkConstants.PATH);
	String fromPage = (String) request.getAttribute(FrameworkConstants.REQ_FROM_PAGE);
	List<String> projectModules = (List<String>) request.getAttribute(FrameworkConstants.REQ_PROJECT_MODULES);
%>
	<!-- unit test button starts -->

    <form action="unit" method="post" autocomplete="off" class="marginBottomZero" id="form_test">
            <div class="operation">
            <%
            	Boolean showWarning = (Boolean) request.getAttribute(FrameworkConstants.REQ_BUILD_WARNING);
            	if (showWarning) {
            %>
	            <div class="alert-message warning display_msg" >
					<s:label cssClass="labelWarn" key="build.required.message"/>
			    </div>
		   <%
            	}
		   %>
	            <div class="icon_fun_div printAsPdf">
	            	<a href="#" id="pdfPopup" style="display: none;"><img id="pdfCreation" src="images/icons/print_pdf.png" title="generate pdf" style="height: 20px; width: 20px;"/></a>
					<a href="#" id="openFolder"><img id="folderIcon" src="images/icons/open-folder.png" title="Open folder"/></a>
					<a href="#" id="copyPath"><img src="images/icons/copy-path.png" title="Copy path"/></a>
				</div>
				<ul id="display-inline-block-example">
					<li id="first">
						<input id="testbtn" type="button" value="<s:text name="label.test"/>" class="primary btn env_btn">
					</li>
    
    <!-- unit test button ends -->
    
	<script type="text/javascript">
		$(document).ready(function(){
		    $('#testbtn').click(function() {
			 	<% if (TechnologyTypes.ANDROIDS.contains(techId)) { %>
					openAndroidPopup();
				<% } else if (TechnologyTypes.IPHONES.contains(techId)) { %>
					openIphoneNativeUnitTestPopup();
				<% } else { %>
					// If the project is having modules it have to display modules after that it have to display unit test progress
					<% if(CollectionUtils.isNotEmpty(projectModules)) { %>
						openUnitTestPopup();
					<% } else { %>
						unitTestProgress();
					<% } %>
				<%	} %>
		    });
		    
		    $('#openFolder').click(function() {
	            openFolder('<%= projectCode %><%= path %>');
	        });
	        
	        $('#copyPath').click(function() {
	           copyPath('<%= projectCode %><%= path %>');
	        });
		    
	        $('#pdfCreation').click(function() {
	    		showPopup();
	    		$('#popup_div').empty();
				var params = "testType=";
				params = params.concat("unit");
	    		popup('printAsPdfPopup', params, $('#popup_div'));
	    	    escPopup();
		    });
	        
			$('#closeGenerateTest, #closeGenTest').click(function() {
				changeTesting("unit", "testGenerated");
				enableScreen();
				$("#popup_div").css("display","none");
				$("#popup_div").empty();
	        });
			
		    function unitTestProgress() {
		    	disableScreen();
		       $('#build-outputOuter').show().css("display","block");
		       getCurrentCSS();
		       readerHandlerSubmit('unit', '<%= projectCode %>', '<%= FrameworkConstants.UNIT %>', '');
		    }
		    
			function openAndroidPopup() {
				showPopup();
				var params = "testType=";
				params = params.concat("unit");
				popup('testAndroid', params, $('#popup_div'));
			}
		    
			function openIphoneNativeUnitTestPopup() {
				var params = "testType=";
				params = params.concat("unit");
				popup('testIphone', params, $('#popup_div'));
			}
			
			function openUnitTestPopup() {
				showPopup();
				var params = "testType=";
				params = params.concat("unit");
				popup('generateUnitTest', params, $('#popup_div'));
			}
			
		});
	</script>
	
	<% 
	        List<TestSuite> testSuites = (List<TestSuite>) request.getAttribute(FrameworkConstants.REQ_TEST_SUITE);
	        Set<String> testResultFiles = (Set<String>) request.getAttribute(FrameworkConstants.REQ_TEST_RESULT_FILE_NAMES);
	        String selectedTestResultFile = (String) request.getAttribute(FrameworkConstants.REQ_SELECTED_TEST_RESULT_FILE);
	        boolean buttonRow = false;
	%>

			<% 
				if(CollectionUtils.isNotEmpty(projectModules)) { 
					buttonRow = true;
			%>
			
			<li id="label">
				&nbsp;<strong><s:text name="label.module"/></strong> 
			</li>
			<li>
				<select id="projectModule" name="projectModule"> 
					<% for(String projectModule : projectModules) { %>
				  <option value="<%= projectModule %>" id="<%= projectModule %>" ><%= projectModule %> </option>
				
				<% 
			        }
				%>
				</select>
			</li>

			<%  } 
				if (TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.contains(techId) || TechnologyTypes.HTML5_WIDGET.contains(techId) || 
					TechnologyTypes.HTML5_MOBILE_WIDGET.contains(techId) || TechnologyTypes.JAVA_WEBSERVICE.contains(techId)) {

					buttonRow = true;
			%>
			<li id="label" class="techLabel">
				&nbsp;<strong><s:text name="label.technolgies"/></strong> 
			</li>
			<li>
				<select id="techReport" name="techReport"> 
				  <option value="java" id="java" >Java</option>
				  <option value="javascript" id="javascript" >Java Script</option>
				</select>
			</li>
			<% 	}
				if (buttonRow) {
			%>
				</ul>
			<% } %>

        	<div class="alert-message block-message warning hideCtrl" id="errorDiv" style="margin: 5px 0 0 0;">
				<center><label class="errorMsgLabel"></label></center>
			</div>

			<% if (buttonRow) { %>
			<ul id="display-inline-block-example">
				<li id="first"></li>
			<% } %>
			<li id="label">
				&nbsp;<strong class="hideCtrl" id="testResultLbl"><s:text name="label.test.suite"/></strong> 
			</li>
			<li>
				<select id="testSuite" name="testSuite" class="hideContent"> <!-- class="techList" --> 
					<option value="All">All</option>
					<% 
					if(CollectionUtils.isNotEmpty(testSuites)) {
						for(TestSuite testSuiteDisplay : testSuites) {
					%>
					  <option value="<%= testSuiteDisplay.getName() %>" id="<%= testSuiteDisplay.getFailures() %>,<%= testSuiteDisplay.getErrors() %>,<%= testSuiteDisplay.getTests() %>,<%= selectedTestResultFile %>" ><%= testSuiteDisplay.getName() %> </option>
					
					<% 
				        }
					}
					%>
				</select>
			</li>
			
			<li id="label">
				&nbsp;<strong class="hideCtrl" id="testResultLbl"><s:text name="label.test.result.view"/></strong> 
			</li>
			<li>
				<select id="resultView" name="resultView" class="hideContent selectDefaultWidth"> 
					<option value="tabular" >Tabular View</option>
					<option value="graphical" >Graphical View</option>
				</select>
			</li>
			</ul>
		</div>
	</form>
		<!-- Test suite chart display starts -->
		<div id="testSuiteDisplay" class="testSuiteDisplay responsiveTableDisplay">
		</div>
		<!-- Test suite chart display ends -->
		
        <script type="text/javascript">
			$(document).ready(function() {
				$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView").hide();
				
				$('#resultView').change(function() {
					changeView();
				});
				
				// table resize
				var tblheight = (($("#subTabcontainer").height() - $("#form_test").height()));
				$('.responsiveTableDisplay').css("height", parseInt((tblheight/($("#subTabcontainer").height()))*100) +'%');
			});
			
			loadTestResults();
			
			$('#projectModule, #techReport').change(function() {
				loadTestResults();
			});

			$('#testSuite').change(function() {
				testReport();
			});

			function loadTestResults() {
				var params = "";
		    	if (!isBlank($('form').serialize())) {
		    		params = $('form').serialize() + "&";
		    	}
				params = params.concat("testType=");
				params = params.concat('<%= FrameworkConstants.UNIT %>');
				
		    	<% if (StringUtils.isNotEmpty(fromPage)) { %>
					params = params.concat("&fromPage=");
					params = params.concat('<%= fromPage %>');
				<% } %>

				performAction('fillTestSuites', params, '', true);
			}

			function testReport() {
				var params = "";
		    	if (!isBlank($('form').serialize())) {
		    		params = $('form').serialize() + "&";
		    	}
				params = params.concat("testType=");
				params = params.concat('<%= FrameworkConstants.UNIT %>');
				performAction('testReport', params, $('#testSuiteDisplay'));
				//show print as pdf icon
				$('#pdfPopup').show();
			}

			function successEvent(pageUrl, data) {
				if ((data != undefined || !isBlank(data)) && data != "") {
					if (data.validated != undefined && data.validated) {
						return validationError(data.showError);
					}

					var testSuiteNames = data.testSuiteNames;
					if ((testSuiteNames != undefined || !isBlank(testSuiteNames))) {
						$("#errorDiv").hide();
						$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView").show();
						$("#testResultFile").hide();
						$('#testSuite').empty();
						$('#testSuite').append($("<option></option>").attr("value", "All").text("All"));
						for (i in testSuiteNames) {
							$('#testSuite').append($("<option></option>").attr("value", testSuiteNames[i]).text(testSuiteNames[i]));
						}
						testReport();
					}
					
				}
			}

			function validationError(errMsg) {
				$(".errorMsgLabel").html(errMsg);
				$("#errorDiv").show();
				$("#testResultFile, #testSuite, #testSuiteDisplay, #testResultLbl, #resultView").hide();
				enableScreen();
			}

			function changeView() {
				var resultView = $('#resultView').val();
				if (resultView == 'graphical') {
					$("#graphicalView").show();
					$("#tabularView").hide();
				} else  {
					$("#graphicalView").hide();
					$("#tabularView").show();
				}
			}
		</script>
    