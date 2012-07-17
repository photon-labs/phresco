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


<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="java.util.List"%>
<%@ page import="com.photon.phresco.model.ProjectInfo"%>
<%@page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>

<%
	List<ProjectInfo> pilotProjectInfo = (List<ProjectInfo>) request.getAttribute("pilotProjects");
%>


<form class="customer_list">
	<div class="operation">
		<input type="button" class="btn btn-primary" name="pilotproj_add" id="pilotprojAdd" 
		 onclick="loadContent('pilotprojAdd', $('#subcontainer'));"
			value="<s:text name='lbl.hdr.comp.pltprjt.add'/>" /> 
			<input type="button" class="btn" id="del" disabled onclick="loadContent('pilotprojDelete', '', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.delete'/>" />
		<s:if test="hasActionMessages()">
			<div class="alert alert-success alert-message" id="successmsg">
				<s:actionmessage />
			</div>
		</s:if>
		<s:if test="hasActionErrors()">
			<div class="alert alert-error" id="errormsg">
				<s:actionerror />
			</div>
		</s:if>
	</div>
     <% if (CollectionUtils.isEmpty(pilotProjectInfo)) { %>
            <div class="alert alert-block">
                <s:text name='alert.msg.pilotpro.not.available'/>
            </div>
    <% } else { %> 
	<div class="table_div">
		<div class="fixed-table-container">
			<div class="header-background"></div>
			<div class="fixed-table-container-inner">
				<table cellspacing="0" class="zebra-striped">
					<thead>
						<tr>
							<th class="first">
								<div class="th-inner">
									<input type="checkbox" value="" id="checkAllAuto"
										name="checkAllAuto" onclick="checkAllEvent(this);">
								</div></th>
							<th class="second">
								<div class="th-inner tablehead">
									<s:label key="lbl.hdr.cmp.name" theme="simple" />
								</div></th>
							<th class="third">
								<div class="th-inner tablehead">
									<s:label key="lbl.hdr.cmp.desc" theme="simple" />
								</div></th>
							<th class="third">
								<div class="th-inner tablehead">
									<s:label key="lbl.hdr.comp.tchngy" theme="simple" />
								</div></th>
						</tr>
					</thead>

					<tbody>

						<%
							if (CollectionUtils.isNotEmpty(pilotProjectInfo)) {
								for (ProjectInfo proInfo : pilotProjectInfo) {
						%>
						<tr>
							<td class="checkboxwidth"><input type="checkbox" class="check" name="projectId" value="<%=proInfo.getId() %>" onclick="checkboxEvent();">
							</td>
							<td><a href="#" onclick="editPilotProject('<%=proInfo.getId() %>');" name="edit" id=""><%=proInfo.getName()%></a>
							</td>
							<td><%=StringUtils.isNotEmpty(proInfo.getDescription()) ? proInfo
							.getDescription() : ""%></td>
							<td>JAVA</td>
						</tr>

						<%
							}
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<% } %>
</form>
<script type="text/javascript">
    /** To edit the pilot project **/
    function editPilotProject(id) {
        var params = "projectId=";
        params = params.concat(id);
        params = params.concat("&fromPage=");
        params = params.concat("edit");
        loadContent("pilotprojEdit", $('#subcontainer'), params);
    }
</script>

