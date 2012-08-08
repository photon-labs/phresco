<%--
  ###
  Service Web Archive
  
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

<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.photon.phresco.model.ProjectInfo"%>
<%@ page import="com.photon.phresco.service.admin.commons.ServiceUIConstants" %> 
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@page import="com.photon.phresco.model.Technology"%>
<%@page import="java.util.List"  %>

<% 
	ProjectInfo pilotProjectInfo = (ProjectInfo)request.getAttribute(ServiceUIConstants.REQ_PILOT_PROINFO); 
	String fromPage = (String)request.getAttribute(ServiceUIConstants.REQ_FROM_PAGE); 
	List<Technology> technologys = (List<Technology>)request.getAttribute(ServiceUIConstants.REQ_ARCHE_TYPES);
	String customerId = (String) request.getAttribute(ServiceUIConstants.REQ_CUST_CUSTOMER_ID);
%>

<form id="formPilotProAdd" class="form-horizontal customer_list">
	<h4 class="hdr">
	<% if (StringUtils.isNotEmpty(fromPage)) { %>
	      <s:label key="lbl.hdr.comp.edit.pltprjt.title" theme="simple"/>
	    <% } else { %>
	      <s:label key="lbl.hdr.comp.add.pltprjt.title" theme="simple"/>
	     <% } %> 
	
	</h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.name'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.pilot.add.name'/>" value="<%= pilotProjectInfo != null ? pilotProjectInfo.getName() : "" %>" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.comp.desc'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.pilot.add.desc'/>" value="<%= pilotProjectInfo != null ? pilotProjectInfo.getDescription() : "" %>"  class="input-xlarge" type="text" name="description">
			</div>
		</div>

		<div class="control-group" id="applyControl">
			<label class="control-label labelbold"> <span
				class="mandatory">*</span>&nbsp;<s:text name="Technology" /> </label>
			<div class="controls">
				<select id="multiSelect" name="technology">
					   <%
                        if (technologys != null) {
                            for (Technology technology : technologys) {
                    %>
                      <option value="<%=technology.getName() %>"><%=technology.getName() %></option>
                    <%
                        }
                        }
                    %>
				</select> <span class="help-inline applyerror" id="techError"></span>
			</div>
		</div>

		<div class="control-group" id="fileControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.projsrc'/>
			</label>
			<div class="controls">
				<input class="input-xlarge" type="file" id="projArc" name="projArc">
				<span class="help-inline" id="fileError"></span>
			</div>
		</div>
	</div>
	
	<div class="bottom_button">
	    <% if (StringUtils.isNotEmpty(fromPage)) { %>
               <%--  <input type="button" id="pilotprojUpdate" class="btn btn-primary" value="<s:text name='lbl.hdr.comp.update'/>" 
                    onclick="formSubmitFileUpload('pilotprojUpdate', 'projArc', $('#subcontainer'), 'Updating Pilotproject');" /> --%>
			<input type="button" id="pilotprojUpdate" class="btn btn-primary"
				value="<s:text name='lbl.hdr.comp.update'/>"
				onclick="validate('pilotprojUpdate', $('#formPilotProAdd'), $('#subcontainer'), 'Updating Pilotproject');" />
		<%
			} else {
		%>
		<%-- <input type="button" id="pilotprojSave" class="btn btn-primary" onclick="formSubmitFileUpload('pilotprojSave', 'projArc', $('#subcontainer'), 'Creating Pilotproject');" value="<s:text name='lbl.hdr.comp.save'/>"/> --%>
			<input type="button" id="pilotprojSave" class="btn btn-primary"
				onclick="validate('pilotprojSave', $('#formPilotProAdd'), $('#subcontainer'), 'Creating Pilotproject');"
				value="<s:text name='lbl.hdr.comp.save'/>" />
		<% } %>
		<input type="button" id="pilotprojCancel" class="btn btn-primary" onclick="loadContent('pilotprojList', '', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.cancel'/>"/>
	</div>
	
    <!-- Hidden Fields -->
    <input type="hidden" name="fromPage" value="<%= StringUtils.isNotEmpty(fromPage) ? fromPage : "" %>"/>
    <input type="hidden" name="projectId" value="<%=  pilotProjectInfo != null ?  pilotProjectInfo.getId() : "" %>"/>
    <input type="hidden" name="oldName" value="<%=  pilotProjectInfo != null ?  pilotProjectInfo.getName() : "" %>"/>  
    <input type="hidden" name="customerId" value="<%= customerId %>"> 
</form>

<script type="text/javascript">
	function findError(data) {
		if(data.nameError != undefined) {
			showError($("#nameControl"), $("#nameError"), data.nameError);
		} else {
			hideError($("#nameControl"), $("#nameError"));
		}
		
		if(data.fileError != undefined) {
			showError($("#fileControl"), $("#fileError"), data.fileError);
			} else {
				hideError($("#fileControl"), $("#fileError"));
			}
	}
</script>