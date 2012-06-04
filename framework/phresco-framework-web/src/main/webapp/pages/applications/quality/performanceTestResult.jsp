<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.photon.phresco.framework.model.PerformanceTestResult"%>
<%@ page import="com.photon.phresco.framework.api.Project"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.model.SettingsInfo"%>
<%@ page import="com.photon.phresco.framework.commons.FrameworkUtil"%>
<%@ page import="com.photon.phresco.util.TechnologyTypes" %> 

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
        String errorDeviceData = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_DATA);
        if (errorDeviceData != null) {
    %>
    	<div class="alert-message block-message warning">
            <center class="noDataForAndroid"><%= FrameworkConstants.ERROR_ANDROID_DATA %></center>
        </div>
    
        <script type="text/javascript">
			$("#resultView").hide();
		</script>
    <% 
    	} else { 
            String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
            double totalStdDev = (Double)request.getAttribute(FrameworkConstants.REQ_TOTAL_STD_DEV);
            double totalThroughput = (Double)request.getAttribute(FrameworkConstants.REQ_TOTAL_THROUGHPUT);
    %>
    	<script type="text/javascript">
    		$("#resultView").show();
    		$(".noTestAvail").show();
			$("#graphBasedOn").show();
			$("#noFiles").hide();
			<%
				if(TechnologyTypes.ANDROIDS.contains(project.getProjectInfo().getTechnology().getId())) {
			%>
				disableType();
			<%
				}
			%>
		</script>
    <%
            Map<String, PerformanceTestResult> performanceReport = (Map<String, PerformanceTestResult>) request.getAttribute(FrameworkConstants.REQ_TEST_RESULT);
            String graphData = (String) request.getAttribute(FrameworkConstants.REQ_GRAPH_DATA);
			String graphLabel = (String) request.getAttribute(FrameworkConstants.REQ_GRAPH_LABEL);
            Set<String> keySet = performanceReport.keySet();
            
            String graphAllData = (String) request.getAttribute(FrameworkConstants.REQ_GRAPH_ALL_DATA);
            
            String graphFor = (String)request.getAttribute(FrameworkConstants.REQ_SHOW_GRAPH);
            
            String chartTitle = "";
            String chartUnit = "";
            if (graphFor.equals("responseTime")) {
            	chartTitle = "Avg Response Time";
            	chartUnit = "ms";
            } else if (graphFor.equals("throughPut")) {
            	chartTitle = "Throughput";
            	chartUnit = "sec";
            } else if (graphFor.equals("minResponseTime")) {
            	chartTitle = "Min Response Time";
            	chartUnit = "ms";
            } else if (graphFor.equals("maxResponseTime")) {
            	chartTitle = "Max Response Time";
            	chartUnit = "ms";
            } else if (graphFor.equals("all")) {
            	chartTitle = "Throughput";
            	chartUnit = "sec";
            }
            
    %>
            <div class="table_div_unit" id="tabularView">
            	<div class="fixed-table-container">
	      			<div class="header-background"> </div>
		      		<div class="fixed-table-container-inner" >
				        <div style="overflow: auto;">
					        <table cellspacing="0" class="zebra-striped">
					          	<thead>
						            <tr>
										<th class="first"><div class="th-inner"><s:text name="label.label"/></div></th>
						              	<th class="second"><div class="th-inner"><s:text name="label.sample"/></div></th>
						              	<th class="third"><div class="th-inner"><s:text name="label.average"/></div></th>
						              	<th class="third"><div class="th-inner"><s:text name="label.min"/></div></th>
						              	<th class="first"><div class="th-inner"><s:text name="label.max"/></div></th>
						              	<th class="second"><div class="th-inner"><s:text name="label.std.dev"/></div></th>
						              	<th class="third"><div class="th-inner"><s:text name="label.error"/></div></th>
						              	<th class="third"><div class="th-inner"><s:text name="label.throughput"/></div></th>
						              	<th class="third"><div class="th-inner"><s:text name="label.kb.sec"/></div></th>
						              	<th class="third"><div class="th-inner"><s:text name="label.avg.bytes"/></div></th>
						            </tr>
					          	</thead>
					
					          	<tbody>
					          	<%	int totalValue = keySet.size();
				          			int NoOfSample = 0; 
				          			double avg = 0; 
					          		int min = 0;
					          		int max = 0;
					          		double StdDev = 0;
					          		int Err = 0;
					          		double KbPerSec = 0;
					          		double sumOfBytes = 0;
					          		int i = 1;
						          	for (String key : keySet) {
			                        	PerformanceTestResult performanceTestResult = performanceReport.get(key);
			                        	NoOfSample = NoOfSample + performanceTestResult.getNoOfSamples();
			                        	avg = avg + performanceTestResult.getAvg();
			                        	
			                        	if (i == 1) {
			                        		min = performanceTestResult.getMin();
			                        		max = performanceTestResult.getMax();
			                        	}
			                        	if (i != 1 && performanceTestResult.getMin() < min) {
			                        		min = performanceTestResult.getMin();
			                        	}
			                        	if (i != 1 && performanceTestResult.getMax() > max) {
			                        		max = performanceTestResult.getMax();
			                        	}
			                        	StdDev = StdDev + performanceTestResult.getStdDev();
			                        	Err = Err + performanceTestResult.getErr();
			                        	sumOfBytes = sumOfBytes + performanceTestResult.getAvgBytes();
								%>
					            	<tr>
					              		<td style="width: 10%;"><%= performanceTestResult.getLabel() %></td>
					              		<td style="width: 10%;"><%= performanceTestResult.getNoOfSamples() %></td>
					              		<td style="width: 10%;"><%= (int)performanceTestResult.getAvg() %></td>
					              		<td style="width: 8%;"><%= performanceTestResult.getMin() %></td>
					              		<td style="width: 8%;"><%= performanceTestResult.getMax() %></td>
					              		<td style="width: 8%;"><%= FrameworkUtil.roundFloat(2, performanceTestResult.getStdDev()) %></td>
					              		<td style="width: 8%;"><%= performanceTestResult.getErr() %> %</td>
					              		<td style="width: 12%;"><%= FrameworkUtil.roundFloat(1, performanceTestResult.getThroughtPut()) %></td>
					              		<td style="width: 10%;"><%= FrameworkUtil.roundFloat(2, performanceTestResult.getKbPerSec()) %></td>
					              		<td style="width: 10%;"><%= performanceTestResult.getAvgBytes() %></td>
					            	</tr>
					            <%
					            	i++;
									}
						          	double avgBytes = sumOfBytes / totalValue;
						          	KbPerSec = (avgBytes / 1024) * totalThroughput;
								%>	
					          	</tbody>
					          	 <tfoot>
					          	 	<tr>
					          	 		<% PerformanceTestResult performanceTestResult = new PerformanceTestResult(); %>
							              <td style="width: 10%;font-weight: bold">Total</td>
							              <td style="width: 10%;font-weight: bold"><%= NoOfSample %></td>
							              <td style="width: 10%;font-weight: bold"><%= FrameworkUtil.roundFloat(2,avg/totalValue) %></td>
							              <td style="width: 8%;font-weight: bold"><%= min %></td>
							              <td style="width: 8%;font-weight: bold"><%= max %></td>
							              <td style="width: 8%;font-weight: bold"><%= FrameworkUtil.roundFloat(2,totalStdDev) %></td>
							              <td style="width: 8%;font-weight: bold"><%= Err/totalValue %> %</td>
							              <td style="width: 12%;font-weight: bold"><%= FrameworkUtil.roundFloat(1,totalThroughput) %></td>
							              <td style="width: 10%;font-weight: bold"><%= FrameworkUtil.roundFloat(2,KbPerSec) %></td>
							              <td style="width: 10%;font-weight: bold"><%= FrameworkUtil.roundFloat(2,avgBytes) %></td>
					          	 	</tr>
					          	 </tfoot>
					        </table>
						</div>
						<div>
							
						</div>
		      		</div>
    			</div>
    		</div>
            <div class="graph_div" id="graphicalView" style="margin-left: 15px;">
                <!--<div class="jm_canvas_div">
                    <iframe src="<%= request.getContextPath() %>/pages/applications/quality/jmeter_graph.jsp"  frameborder="0" width="100%" height="100%"></iframe>
                </div>-->
                <% if(request.getAttribute("showGraphFor").toString().equals("all")) { %>
                	<canvas id="allData" width="420" height="300">[No canvas support]</canvas>
                <% } %>
                <canvas id="myCanvas" width="420" height="300">[No canvas support]</canvas>
                
            </div>
   

<script type="text/javascript">
	/* To check whether the divice is ipad or not */
	if(!isiPad()){
		/* JQuery scroll bar */
		$(".jmtable_data_div").scrollbars();
		$("#graphicalView").scrollbars();
	}
	
	$(document).ready(function() {
		canvasInit();
		
		 $(".styles").click(function() {
			 canvasInit();
		 });
		/*$(".styles").click(function() {
			 $("iframe").attr({
	             src: $("iframe").attr("src")
	         });
		});*/
		
		changeView (); // when graph is loaded base on selection of list box(tabular / list) it do automatically
	});
	
	$('#resultView').change(function() {
		changeView ();
	});
	
	/* $('#showGraphFor').change(function() {
		changeGraph ($(this).val());
	}); */
	
	function changeGraph(showGraphFor) {
		var testResultFile = $("#testResultFile").val();
		var testResult = $("#testResultsType").val();
		var params = "";
    	if (!isBlank($('form').serialize())) {
    		params = $('form').serialize() + "&";
    	}
		showLoadingIcon($("#testResultDisplay")); // Loading Icon
		performAction('performanceTestResult', params, $('#testResultDisplay'));
		$("#graphicalView").show();
	}
	
	function changeView() {
		var resultView = $('#resultView').val();
		if (resultView == 'graphical') {
			$("#graphBasedOn").show();
			$('#graphicalView').show();
			$('#tabularView').hide();
		} else  {
			$("#graphBasedOn").hide();
			$('#graphicalView').hide();
			$('#tabularView').show();
		}
	}
	
	function canvasInit() {
		var data = <%= graphData %>;
        var bar = new RGraph.Bar('myCanvas', data);
        
		var theme = $.cookie("css");
        var chartTextColor = "";
        var chartGridColor = "";
        var chartAxisColor = "";
        var chartBarColor = "";
      //line chart color
      	var minColor = "";
      	var maxColor = "";
      	var avgColor = "";
		if (theme == undefined || theme == "themes/photon/css/red.css") {
	        chartTextColor = "white"; // axis text color
	        chartGridColor = "white"; // grid
	        chartAxisColor = "white"; // axis color
	        chartBarColor = "#B1121D"; //Bar color
	        
	        //line chart color
	      	minColor = "#FF9900";
	      	maxColor = "#B2B2FF";
	      	avgColor = "red";
		} else {
	        chartTextColor = "#4C4C4C";
	        chartGridColor = "#4C4C4C";
	        chartAxisColor = "#4C4C4C";
	        chartBarColor = "#00A8F0";
	        
	      //line chart color
	      	minColor = "#00A8F0";
	      	maxColor = "#008000";
	      	avgColor = "red";
		}
        
        //bar2.Set('chart.title', 'Sales in the last 8 months (tooltips)');
        bar.Set('chart.gutter.left', 70);
        bar.Set('chart.text.color', chartTextColor);
        bar.Set('chart.background.barcolor1', 'transparent');
        bar.Set('chart.background.barcolor2', 'transparent');
        bar.Set('chart.background.grid', true);
        bar.Set('chart.colors', [chartBarColor]);
        bar.Set('chart.background.grid.width', 0.5);
        bar.Set('chart.text.angle', 45);
        bar.Set('chart.gutter.bottom', 140);
        bar.Set('chart.background.grid.color', chartGridColor);
        bar.Set('chart.axis.color', chartAxisColor);
        bar.Set('chart.title', '<%= chartTitle %>');
        bar.Set('chart.title.color', chartTextColor);
        bar.Set('chart.labels',<%= graphLabel %>);
        bar.Set('chart.units.post', '<%= chartUnit %>');
        bar.Draw();
        
        <% 
        	if(request.getAttribute("showGraphFor").toString().equals("all")) {
        %>
			var line = new RGraph.Line('allData', <%= graphAllData %>);
	        line.Set('chart.background.grid', true);
	        line.Set('chart.linewidth', 5);
	        line.Set('chart.gutter.left', 85);
	        line.Set('chart.text.color', chartTextColor);
	        line.Set('chart.hmargin', 5);
	        if (!document.all || RGraph.isIE9up()) {
	            line.Set('chart.shadow', true);
	        }
	        line.Set('chart.tickmarks', 'endcircle');
	        line.Set('chart.units.post', 's');
	        line.Set('chart.colors', [minColor, avgColor, maxColor]);
	        line.Set('chart.background.grid.autofit', true);
	        line.Set('chart.background.grid.autofit.numhlines', 10);
	        line.Set('chart.curvy', true);
	        line.Set('chart.curvy.factor', 0.5); // This is the default
	        line.Set('chart.animation.unfold.initial',0);
	        line.Set('chart.labels',<%= graphLabel %>);
	        line.Set('chart.title','Response Time');// Title
	        line.Set('chart.axis.color', chartAxisColor);
	        line.Set('chart.text.angle', 45);
	        line.Set('chart.gutter.bottom', 140);
	        line.Set('chart.key', ['Min','Avg','Max']);
	        line.Set('chart.background.grid.color', chartGridColor);
	        line.Set('chart.title.color', chartTextColor);
	        line.Set('chart.shadow', false);
	        line.Draw();
        <% } %>
        
	}
<% } %>
</script>