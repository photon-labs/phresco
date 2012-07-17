<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Set"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<script src="js/reader.js" ></script>

<%
   	String projectCode = (String)request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
	String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
	List<String> reportFiles = (List<String>)request.getAttribute(FrameworkConstants.REQ_PDF_REPORT_FILES);
	String reportGenerationStat = (String)request.getAttribute(FrameworkConstants.REQ_REPORT_STATUS);
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

	<div class="modal-body" style="padding-bottom: 20px;height: 220px;">
		<%
			if (CollectionUtils.isNotEmpty(reportFiles)) {
		%>
			<div class="tabl-fixed-table-container" style="padding-bottom: 20px;">
	      		<div class="header-background"></div>
	      		<div id="reportPopupTbl" class="tabl-fixed-table-container-inner validatePopup_tbl" style="height: 200px;overflow-x: hidden; overflow-y: auto;">
			        <table cellspacing="0" class="zebra-striped">
			          	<thead>
				            <tr>
								<th class="first validate_tblHdr">
				                	<div class="pdfth-inner">Existing Reports</div>
				              	</th>
				              	<th class="second validate_tblHdr">
				                	<div class="pdfth-inner">Type</div>
				              	</th>
				              	<th class="second validate_tblHdr">
				                	<div class="pdfth-inner">Download</div>
				              	</th>
				            </tr>
			          	</thead>
			
			          	<tbody>
			          		<% 
			          			for(String reportFile : reportFiles) { 
			          				String type = "";
			          		%>
			            	<tr>
			              		<td>
			              			<div class ="pdfName" style="color: #000000;">
				              			<%	
			              					String[] reportType = reportFile.split(FrameworkConstants.UNDERSCORE);
			              					type = reportType[0];
				              			%>
				              			<%= reportType[1] %>
			              			</div>
			              		</td>
			              		<td>
			              			<div style="color: #000000;">
				              			<%
				              				if ("crisp".equals(type)) {
				              					type = FrameworkConstants.MSG_REPORT_OVERALL;
				              			%>
				              				<%= type %>
				              			<%
				              				} else {
				              					type = FrameworkConstants.MSG_REPORT_DETAIL;
				              			%>
				              				<%= type %>
				              			<% } %>
			              			</div>
			              		</td>
			              		<td>
			              			<div class="pdfType" style="color: #000000;">
				              			<a href="<s:url action='downloadReport'>
				              				<s:param name="testType"><%= testType == null ? "" : testType %></s:param>
				              				<s:param name="projectCode"><%= projectCode %></s:param>
						          		    <s:param name="reportFileName"><%= reportFile %></s:param>
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
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action" style="">
			<div style="float: left;">
				<img class="popupLoadingIcon" style="position: relative;">
				<div id="errMsg"></div>
			</div>
			<div style="float: right;">
	            <input type="radio" name="reportDataType" value="crisp" checked>
	            <span class="textarea_span popup-span"><s:text name="label.report.overall"/></span>
	            <input type="radio" name="reportDataType" value="detail">
	            <span class="textarea_span popup-span"><s:text name="label.report.detail"/></span>
	            
				<input type="button" class="btn primary" value="Close" id="cancel">
				<input type="button" id="generateReport" class="btn primary" value="Generate">
			</div>
		</div>
	</div>
</div>
</form>

<script type="text/javascript">
	if(!isiPad()){
		/* JQuery scroll bar */
// 		$("#reportPopupTbl").scrollbars();
	}
	$(document).ready(function() {
		escPopup();
		
		$('#close, #cancel').click(function() {
			showParentPage();
		});
		
		$('#generateReport').click(function() {
			$('.popupLoadingIcon').show();
			getCurrentCSS();
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
	    	<%
				if (StringUtils.isEmpty(testType) && !"performance".equals(testType)) {
			%>
	 	    	params = params.concat("&projectCode=");
		    	params = params.concat('<%= projectCode %>');
			<%
				} else if(!"performance".equals(testType)) {
				
			%>
	 	    	params = params.concat("&testType=");
		    	params = params.concat('<%= testType %>');
			<%
				}  	
	    	%>
            performAction('printAsPdf', params, $('#popup_div'), '');
		});
		
		<%
			if (StringUtils.isNotEmpty(reportGenerationStat)) {
		%>
			showHidePopupMsg($("#errMsg"), '<%= reportGenerationStat %>');
		<%
			}
		%>
	});
	
</script>