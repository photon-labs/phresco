<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.IOException"%>
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>

<%@ include file="errorReport.jsp" %>

<%
    String projectCode = (String) request.getAttribute(FrameworkConstants.REQ_PROJECT_CODE);
    String testType = (String) request.getAttribute(FrameworkConstants.REQ_TEST_TYPE);
    if (projectCode == null && testType == null) {
        projectCode = request.getParameter(FrameworkConstants.REQ_PROJECT_CODE);
        testType = request.getParameter(FrameworkConstants.REQ_TEST_TYPE);
    }
    BufferedReader reader = (BufferedReader) session.getAttribute(projectCode + testType);

    String line = null;
    if (reader != null) {
        try {
			line = reader.readLine();
            if (line == null) {
                line = "EOF";
                session.removeAttribute(projectCode + testType);
            }
%>
                <%= line %>
		<%
		    } catch (IOException e) {
		%>
            	<%=e.getMessage()%>
<%
    		}
    } else {
    	session.removeAttribute(projectCode + testType);
%>
		<%= "EOF" %>
<%
    }
%>