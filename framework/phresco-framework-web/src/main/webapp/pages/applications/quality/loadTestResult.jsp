<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.photon.phresco.framework.model.TestResult"%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@page import="com.photon.phresco.model.SettingsInfo"%>

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
		List<TestResult> testResults = (List<TestResult>) request.getAttribute(FrameworkConstants.REQ_TEST_RESULT);
    %>
            <div class="columns columnStyle">
                <div class="columnStyle">
	                <div class="table_div_unit">
	                   <div class="fixed-table-container">
	      					<div class="header-background"> </div>
				      		<div class="fixed-table-container-inner">
						        <table cellspacing="0" class="zebra-striped">
						          	<thead>
							            <tr>
											<th class="first"><div class="th-inner"><s:text name="label.thread.name"/></div></th>
							              	<th class="second"><div class="th-inner"><s:text name="label.date"/></div></th>
							              	<th class="third"><div class="th-inner"><s:text name="label.elapsed.time"/></div></th>
							              	<th class="third"><div class="th-inner"><s:text name="label.status"/></div></th>
							            </tr>
						          	</thead>
						
						          	<tbody>
						          	<%
						          		for (TestResult testResult : testResults) {
									%>
						            	<tr>
						              		<td style="width: 30%;"><%= testResult.getThreadName() %></td>
						              		<td style="width: 30%;"><%= testResult.getTimeStamp() %></td>
						              		<td><%= testResult.getTime() %></td>
						              		<td>
						              			<% if (!testResult.isSuccess()) { %>
			                                        <img src="images/icons/failure.png" title="Failure">
			                                    <% } else { %>
			                                        <img src="images/icons/success.png" title="Success">
			                                    <% } %>
						              		</td>
						            	</tr>
						            <%
										}
									%>	
						          	</tbody>
						        </table>
				      		</div>
    					</div> 
	                </div><!-- End column 1 -->
	    
	                <div class="graph_div">
	                    <div class="jm_canvas_div">
	                        <iframe src="<%= request.getContextPath() %>/pages/applications/quality/jmeter_graph.jsp"  frameborder="0" width="500px" height="100%" style="overflow: hidden;"></iframe>
	                    </div>
	                </div>
	            </div>
	        </div>

<script type="text/javascript">
/* To check whether the divice is ipad or not */
if(!isiPad()){
	/* JQuery scroll bar */
	$(".jmtable_data_div").scrollbars();
}

$(document).ready(function() {
	if ($.browser.safari && $.browser.version == 530.17)
	{
	$(".columns").show().css("float","left");
	$(".jm_canvas_div").show().css("margin-top","54px");
	}
	$(".styles").click(function() {
		 $("iframe").attr({
             src: $("iframe").attr("src")
         });
	});
});
</script>