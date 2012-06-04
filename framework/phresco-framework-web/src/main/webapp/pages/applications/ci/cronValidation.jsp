<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="java.util.Date"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.commons.CIJob" %>

<input type="text" id="cronExpression" name="cronExpression" value="<%= (String)request.getAttribute(FrameworkConstants.REQ_CRON_EXPRESSION)%>">&nbsp; 
<a id="showPattern" href="#">
	<img src="images/icons/Help.png" />
</a>

<!-- Cron Expression Pattern starts -->	
<div id="Pattern" class="modal abtDialog">
	<div class="modal-header">
		<div class="TestType"><s:text name="label.cronvalidate.title"/></div><a id="closePatternPopup" href="#" class="close" style="top: 5px;">&times;</a>
	</div>
	<div class="abt_div">
		<div id="testCaseDesc" class="testCaseDesc">
               <table border="1" cellpadding="0" cellspacing="0" class="tbl" width="100%">
               	   <tr>
                       <td width="1%" nowrap><b id="SelectedSchedule" class="popup-label"></b></td>
                       <td><b></b></td>
                   </tr>
                   <tr class="popup-label">
                       <td width="1%" nowrap class="popup-label"><b><s:text name="label.name"/></b></td>
                       <td class="popup-label"><b><s:text name="label.date"/></b></td>
                   </tr>
                <% 	
                   	Date[] dates = (Date[])request.getAttribute(FrameworkConstants.REQ_CRON_DATES);
                   	if (dates != null) {
                    	String jobName = (String)request.getAttribute(FrameworkConstants.REQ_JOB_NAME);
	                    for (int i = 0; i < dates.length; i++) {
	                        Date date = dates[i]; 
                %>
                        <tr class="popup-label">
                            <td class="jobName popup-label" nowrap></td>
                            <td class="popup-label"><%= date %><%= ((i + 1) == dates.length) ? "    .....</b>" : "" %></td>
                        </tr>
               	<%
                     	} 
                    }
                %>
               </table>
		</div>
	</div>
	
	<div class="modal-footer">
		<div class="action abt_action">
			<input type="button" class="btn primary" value="<s:text name="label.close"/>" id="closeDialog">
		</div>
	</div>
		
</div>
<!-- Cron Expression Pattern ends -->

<script type="text/javascript">
   $(document).ready(function() {
	    var selectedSchedule = $("input:radio[name=schedule]:checked").val();
	    $('#SelectedSchedule').html(selectedSchedule + "&nbsp;Schedule");
	    var jobName = $("input:text[name=name]").val();
	    $('.jobName').html(jobName);
	   	$('#closeDialog').click(function() {
	//    		$(".wel_come").show().css("display", "none");
	   		patternPopUp('none');
	   	});
	   	
		$("#showPattern").click(function(){
			patternPopUp('block');
		});
		
		$('#closePatternPopup').click(function() {
			patternPopUp('none');
		});
	   	
   });
   
   function patternPopUp(enableProp) {
	   	//$(".wel_come").show().css("display", enableProp);
	   	$("#Pattern").show().css("display", enableProp);
   }
</script>