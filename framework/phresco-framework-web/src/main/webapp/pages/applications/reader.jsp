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