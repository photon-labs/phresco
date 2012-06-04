<%@ taglib uri="/struts-tags" prefix="s"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.photon.phresco.framework.model.TestResult"%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@page import="com.photon.phresco.model.SettingsInfo"%>

    <% 
        String testError = (String) request.getAttribute(FrameworkConstants.REQ_ERROR_TESTSUITE);
        if(testError != null) {
    %>
        <div class="alert-message block-message warning" >
            <center><%= testError %></center>
        </div>
    <%
        } else {
            List<TestResult> testResults = (List<TestResult>) request.getAttribute(FrameworkConstants.REQ_TEST_RESULT);
    %>
            <div class="columns columnStyle">
                <div class="columnStyle">
	                <div class="table_div">
	                    <div class="tblheader">
	                        <table class="zebra-striped">
	                            <tr>
	                                <th class="tstRst_th1"><s:text name="label.thread.name"/></th>
	                                <th class="tstRst_th2"><s:text name="label.date"/></th>
	                                <th class="tstRst_th3"><s:text name="label.elapsed.time"/></th>
	                                <th class="tstRst_img"><s:text name="label.status"/></th>
	                            </tr>
	                        </table>
	                    </div>
	                    <div class="jmtable_data_div">
	                        <table class="zebra-striped">
	                            <%
	                                for (TestResult testResult : testResults) {
	                            %>
	                            <tr>
                                    <td class="tstRst_th1"><%= testResult.getThreadName() %></td>
	                                <td class="tstRst_th2"><%= testResult.getTimeStamp() %></td>
	                                <td class="tstRst_th3"><%= testResult.getTime() %></td>
	                                
	                                <td class="tstRst_img">
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
	                        </table>
	                    </div>
	                </div><!-- End column 1 -->
	    
	                <div class="graph_div">
	                    <div class="jm_canvas_div">
	                        <iframe src="<%= request.getContextPath() %>/pages/applications/quality/jmeter_graph.jsp"  frameborder="0" width="100%" height="100%"></iframe>
	                    </div>
	                </div>
	            </div>
	        </div>
    <%         
        }
    %>

<script type="text/javascript">
/* To check whether the divice is ipad or not */
if(!isiPad()){
	/* JQuery scroll bar */
	$(".jmtable_data_div").scrollbars();
}

$(document).ready(function() {
	$(".styles").click(function() {
		 $("iframe").attr({
             src: $("iframe").attr("src")
         });
	});
});
</script>