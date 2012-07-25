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
											<th class="first"><div class="th-inner-test"><s:text name="label.thread.name"/></div></th>
							              	<th class="second"><div class="th-inner-test"><s:text name="label.date"/></div></th>
							              	<th class="third"><div class="th-inner-test"><s:text name="label.elapsed.time"/></div></th>
							              	<th class="third"><div class="th-inner-test"><s:text name="label.status"/></div></th>
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
	$(".fixed-table-container-inner").scrollbars();
}

$(document).ready(function() {
	if ($.browser.safari) {
		$(".th-inner-test").css("top", "235px"); 
	}
	
	if ($.browser.opera) {
		$(".graph_div").css("float", "left"); 
		$(".columns").css("column-count", "1"); 
	}
	
	var OSName="Unknown OS";
    if (navigator.appVersion.indexOf("Mac")!=-1) {
          OSName="MacOS";
    }
    
    if (OSName == "MacOS") { 
        $(".th-inner-test").css("top","225px");  
        $(".th-inner-testtech").css("top","225px");
    }
	
	enableScreen();
	
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