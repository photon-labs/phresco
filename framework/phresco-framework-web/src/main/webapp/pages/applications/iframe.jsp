<%@page import="com.photon.phresco.commons.FrameworkConstants"%>

<%
	String error = (String) request.getAttribute(FrameworkConstants.REQ_ERROR);
	
	if(error != null) {
%>
        <div class="alert-message block-message warning" >
			<center><label Class="errorMsgLabel"><%= error %></label></center>
		</div>
        
   <% } else { 
		String sonarPath = (String) request.getAttribute(FrameworkConstants.REQ_SONAR_PATH);
		
   %>
		 <iframe src="<%= sonarPath %>" frameBorder="0" class="iframe_container"></iframe>
		
	<% } %>