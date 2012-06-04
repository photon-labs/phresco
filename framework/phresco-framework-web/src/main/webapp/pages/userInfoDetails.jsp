<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>
<%@ page import="com.photon.phresco.model.UserInfo" %>

<%
	UserInfo userInfo = (UserInfo)session.getAttribute(FrameworkConstants.REQ_USER_INFO);
	boolean disableCI = false;
	if (userInfo == null || userInfo.getDisplayName() == null) {
	request.setAttribute(FrameworkConstants.REQ_LOGIN_ERROR, "Session expired");
%>
	 <script type="text/javascript"> 
	    $.ajax({
	  			url : 'logout',
	  			success : function(data) {
	  			   $("html").html(data);
	             }
	  			
	   	}); 
	 </script>
<%
	} else {
		if (userInfo.getRoles() != null) {
			for (String role : userInfo.getRoles()) {
				if (role.equals(FrameworkConstants.ENGINEER)) {
				 	disableCI = true;	// Restrict CI
%>
				 <script type="text/javascript">
				 	disableCreateProject(); // Restrict creating project
				 </script>
<%
				}
				
				if (role.equals(FrameworkConstants.RELEASE_ENGINEER)) {
%>
				 <script type="text/javascript">
				 	disableCreateProject(); // Restrict creating project
				 </script>
<%
				}
			}
		}
	}
%>