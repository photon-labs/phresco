<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>

<script src="js/reader.js" ></script>

<%
   	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	List<String> reportFiles = (List<String>)request.getAttribute(FrameworkConstants.REQ_PDF_REPORT_FILES);;
%>

<style>
	.zebra-striped tbody tr:hover td {
	    background-color: transparent;
	}
</style>

<form action="printAsPdf" method="post" autocomplete="off" class="build_form" id="generatePdf">
<div class="popup_Modal">
	<div class="modal-header">
		<h3 id="generateBuildTitle">
			Generate Report
		</h3>
		<a class="close" href="#" id="close">&times;</a>
	</div>

	<div class="modal-body" style="padding-bottom: 20px;">
		<%
			if (CollectionUtils.isNotEmpty(reportFiles)) {
		%>
			<div class="fixed-table-container" style="padding-bottom: 20px;">
	      		<div class="header-background"></div>
	      		<div class="fixed-table-container-inner validatePopup_tbl">
			        <table cellspacing="0" class="zebra-striped">
			          	<thead>
				            <tr>
								<th class="first validate_tblHdr">
				                	<div class="th-inner">Existing Reports</div>
				              	</th>
				              	<th class="second validate_tblHdr">
				                	<div class="th-inner">Download</div>
				              	</th>
				            </tr>
			          	</thead>
			
			          	<tbody>
			          		<% 
			          			for(String reportFile : reportFiles) { 
			          		%>
			            	<tr>
			              		<td>
			              		<div class = "validateMsg" style="color: #000000;">
			              			<%= reportFile %>
			              			</div>
			              		</td>
			              		<td>
			              			<div class = "validateStatus" style="color: #000000;">
<!-- 			              				<img src="images/icons/download.png" title="Download"> -->
				              			<a href="<s:url action='downloadReport'>
						          		     <s:param name="reportFileName"><%= reportFile %></s:param>
						          		     <s:param name="projectCode"><%= projectCode %></s:param>
						          		     <s:param name="testType"><%= testType %></s:param>
						          		     </s:url>">
						          		     <img src="images/icons/download.png" title="<%= reportFile %>.pdf"/>
			                            </a>
					   				</div>
			              		</td>
			            	</tr>
			            	<% } %>
			          	</tbody>
			        </table>
	      		</div>
    		</div>
    	<%
			} else { %>
    		<div class="alert-message block-message warning" >
				<center><label Class="errorMsgLabel"><s:text name="label.report.unavailable"/></label></center>
			</div>
    	<% } %>
		<!-- Report Name -->
<!-- 		<div class="clearfix server"> -->
<!-- 			<label for="xlInput" class="xlInput popup-label">Report Name</label> -->
<!-- 			<div class="input"> -->
<%-- 				<input type="text" name="reportName" id="reportName" maxlength="30" title="30 Characters only"  value="<%= projectCode%>"> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		
		<!--  Report Location -->
<!-- 		<div class="clearfix database"> -->
<!-- 			<label for="xlInput" class="xlInput popup-label">Disk Location</label> -->
<!-- 			<div class="input"> -->
<!-- 				<input type="text" name="reoportLocation" id="reoportLocation" title="Report location"  value=""> -->
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action">
		<div id="errMsg"></div>
			<input type="button" class="btn primary" value="Cancel" id="cancel">
			<input type="button" id="generateReport" class="btn primary" value="Generate">
		</div>
	</div>
</div>
</form>

<script type="text/javascript">
	$(document).ready(function() {
		escPopup();
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});
		
		// Node js run against source
		$('#generateReport').click(function() {
			showParentPage();			
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
 	    	params = params.concat("&testType=");
	    	params = params.concat('<%= testType %>');
            performAction('printAsPdf', params, '', '');
		});
		
// 		function showParentPage() {
// 			$(".wel_come").show().css("display","none");
// 			$('.popup_div').show().css("display","none");
// 		}
	});
	
</script>