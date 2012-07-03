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

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %>
<%@ page import="org.apache.commons.lang.StringUtils;" %>

<style type="text/css">
	.btn.success, .alert-message.success {
       	margin-top: -35px;
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

    <% 
		Project project = (Project) request.getAttribute(FrameworkConstants.REQ_PROJECT);
		String projectCode = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE); 
        String NoData = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_DATA);
        if (NoData != null) {
    %>
    	<div class="alert-message block-message warning">
            <center class="noDataForAndroid"><%= FrameworkConstants.ERROR_ANDROID_DATA %></center>
        </div>
    
        <script type="text/javascript">
			
		</script>
    <% 
    	} else {
    		String graphData = "";
    		String testSuiteLabels = FrameworkConstants.SQUARE_OPEN;
    		Map<String, String> allTestSuiteReport = (Map<String, String>) request.getAttribute(FrameworkConstants.REQ_ALL_TESTSUITE_MAP);
    		Set<String> keySet = allTestSuiteReport.keySet();
    %>
    	<script type="text/javascript">

		</script>
            <div class="table_div_unit qtyTable_view" id="tabularView">
            	<div class="fixed-table-container responsiveFixedTableContainer qtyFixedTblContainer">
	      			<div class="header-background"> </div>
		      		<div class="fixed-table-container-inner">
				        <div style="overflow: auto;">
					        <table cellspacing="0" class="zebra-striped">
					          	<thead>
					       
						            <tr>
										<th class="first"><div id="thDiv" class="th-inner-test"><s:text name="label.testsuite.name"/></div></th>
						              	<th class="second"><div id="thDiv1" class="th-inner-test"><s:text name="label.testsuite.total"/></div></th>
						              	<th class="third"><div id="thDiv2" class="th-inner-test"><s:text name="label.testsuite.success"/></div></th>
						              	<th class="third"><div id="thDiv3" class="th-inner-test"><s:text name="label.testsuite.failure"/></div></th>
						              	<th class="first"><div id="thDiv4" class="th-inner-test"><s:text name="label.testsuite.error"/></div></th>
						            </tr>
					          	</thead>
					
					          	<tbody>
					          	<%
					          		int totalValue = keySet.size();
					          		// All testSuite total colum value calculation
					          		int totalTstCases = 0;
					          		int totalSuccessTstCases = 0;
					          		int totalFailureTstCases = 0;
					          		int totalErrorTstCases = 0;
					          		
				            		ArrayList<String> successData = new ArrayList<String>();
				            		ArrayList<String> failureData = new ArrayList<String>();
				            		ArrayList<String> errorData = new ArrayList<String>();
				            		
					          		for (String key : keySet) {
					          			String results = allTestSuiteReport.get(key);
					          			String[] result = results.split(",");
					          			
					          			float total = Float.parseFloat(result[0]);
					          			float success = Float.parseFloat(result[1]);
					          			float failure = Float.parseFloat(result[2]);
					          			float error = Float.parseFloat(result[3]);
					          			totalTstCases = totalTstCases + (int)total;
					          			totalSuccessTstCases = totalSuccessTstCases + (int)success;
					          			totalFailureTstCases = totalFailureTstCases + (int)failure;
					          			totalErrorTstCases = totalErrorTstCases + (int)error;
					          			
					            		graphData = graphData + FrameworkConstants.SQUARE_OPEN + (int)success + "," + (int)failure + "," + (int)error + FrameworkConstants.SQUARE_CLOSE + ",";
					            		testSuiteLabels = testSuiteLabels + "'" + key + "',";
					          	%>
					            	<tr>
					              		<td class="width-ten-percent">
					              			<a href="#" name="loadTestSuite" id="<%= key %>" ><%= key %></a>
					              		</td>
					              		<td class="width-ten-percent"><%= (int)total %></td>
					              		<td class="width-en-percent"><%= (int)success %></td>
					              		<td class="width-eight-percent"><%= (int)failure %></td>
					              		<td class="width-eight-percent"><%= (int)error %></td>
					            	</tr>
					            <%
					            	
					          		}
// 					          		graphData = successData + " ," + failureData + " ," + errorData;
									graphData = graphData.substring(0, graphData.length() - 1);
					          		testSuiteLabels = testSuiteLabels.substring(0, testSuiteLabels.length() - 1);
					          		testSuiteLabels = testSuiteLabels + FrameworkConstants.SQUARE_CLOSE;
					            %>
					          	</tbody>
					          	 <tfoot>
					          	 	<tr>
							              <td class="width-ten-percent loadTestPopupBold">Total</td>
							              <td class="width-ten-percent loadTestPopupBold"><%= totalTstCases %></td>
							              <td class="width-ten-percent loadTestPopupBold"><%= totalSuccessTstCases %></td>
							              <td class="width-eight-percent loadTestPopupBold"><%= totalFailureTstCases %></td>
							              <td class="width-eight-percent loadTestPopupBold"><%= totalErrorTstCases %></td>
					          	 	</tr>
					          	 </tfoot>
					        </table>
						</div>
						<div>
							
						</div>
		      		</div>
    			</div>
    		</div>
            <div class="graph_div" id="graphicalView" style="padding-left: 15px; display:none; text-align: center;">
                <canvas id="bar" width="620" height="400">[No canvas support]</canvas>               
            </div>
   

<script type="text/javascript">
	/* To check whether the divice is ipad or not */
	if(!isiPad()){
		/* JQuery scroll bar */
		$(".fixed-table-container-inner").scrollbars();
		$("#graphicalView").scrollbars();
	}
	
	$(document).ready(function() {
		if ($('#label').hasClass('techLabel')){
			$("#thDiv").removeClass("th-inner-test").addClass("th-inner-testtech");
			$("#thDiv1").removeClass("th-inner-test").addClass("th-inner-testtech");
			$("#thDiv2").removeClass("th-inner-test").addClass("th-inner-testtech");
			$("#thDiv3").removeClass("th-inner-test").addClass("th-inner-testtech");
			$("#thDiv4").removeClass("th-inner-test").addClass("th-inner-testtech");
		}
		
		if ($.browser.safari) {
    		$(".th-inner-test").css("top","231px");  
    		$(".th-inner-testtech").css("top","263px"); 
		}

	
		// based on view show the list(table/graph)
		changeView();
		canvasInit();
		enableScreen();
		
		 $(".styles").click(function() {
			 canvasInit();
		 });
		 
		 // display report based on testsuite name selection
		 $('a[name="loadTestSuite"]').click(function() {
			 $('#testSuite option[value="'+ this.id +'"]').attr('selected', 'selected');
			 testReport();
		 });
		 
		// table resizing
		 var tblheight = (($("#subTabcontainer").height() - $("#form_test").height()));
		 $('.responsiveTableDisplay').css("height", parseInt((tblheight/($("#subTabcontainer").height()))*100) +'%');
			
		 var fixedTblheight = ((($('#tabularView').height() - 30) / $('#tabularView').height()) * 100);
		 $('.responsiveFixedTableContainer').css("height", fixedTblheight+'%');
	});
	
	function canvasInit() {
		var theme = localStorage["color"];
        var chartTextColor = "";
        var chartGridColor = "";
        var chartAxisColor = "";
        var chartBarColor = "";
      //line chart color
      	var successColor = "";
      	var failureColor = "";
      	var errorColor = "";
		if (theme == undefined || theme == "themes/photon/css/red.css") {
	        chartTextColor = "white"; // axis text color
	        chartGridColor = "white"; // grid
	        chartAxisColor = "white"; // axis color
	        chartBarColor = "#B1121D"; //Bar color
	        //line chart color
	      	successColor = "#6f6";
	      	failureColor = "orange";
	      	errorColor = "red";
		} else {
	        chartTextColor = "#4C4C4C";
	        chartGridColor = "#4C4C4C";
	        chartAxisColor = "#4C4C4C";
	        chartBarColor = "#00A8F0";
	      //line chart color
	      	successColor = "#6f6";
	      	failureColor = "orange";
	      	errorColor = "red";
		}
		
        var bar1 = new RGraph.Bar('bar', [<%= graphData %>]);
         bar1.Set('chart.background.barcolor1', 'transparent');
         bar1.Set('chart.background.barcolor2', 'transparent');
         bar1.Set('chart.labels', <%= testSuiteLabels%>);
         bar1.Set('chart.key', ['Success', 'Failure', 'Error']);
         bar1.Set('chart.key.position.y', 35);
         bar1.Set('chart.key.position', 'gutter');
         bar1.Set('chart.colors', [successColor, failureColor, errorColor]);
         bar1.Set('chart.shadow', false);
         bar1.Set('chart.shadow.blur', 0);
         bar1.Set('chart.shadow.offsetx', 0);
         bar1.Set('chart.shadow.offsety', 0);
         bar1.Set('chart.key.linewidth', 0);
         bar1.Set('chart.yaxispos', 'left');
         bar1.Set('chart.strokestyle', 'rgba(0,0,0,0)');
         bar1.Set('chart.text.angle', 45);
         bar1.Set('chart.text.color', chartTextColor);
         bar1.Set('chart.axis.color', chartAxisColor);
         bar1.Set('chart.gutter.left', 60);
		 bar1.Set('chart.background.grid.color', chartGridColor);				
//          bar1.Set('chart.gutter.right', 1);
         bar1.Set('chart.gutter.bottom', 175);
         bar1.Set('chart.background.grid.autofit',true);
         // size above bars dispalyed
         // bar1.Set('chart.labels.above', true);
<%--          bar1.Set('chart.ymax', <%= totalSuccessTstCases + totalFailureTstCases + totalErrorTstCases%>); --%>
         bar1.Draw();
	}
	
<% } %>
</script>