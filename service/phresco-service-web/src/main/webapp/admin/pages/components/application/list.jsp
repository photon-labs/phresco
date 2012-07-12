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

<%@ page import="org.apache.commons.collections.CollectionUtils"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.util.List"%>

<%@ page import="com.photon.phresco.model.ApplicationType"%>
<%@ page import="com.photon.phresco.service.admin.commons.ServiceUIConstants"%>

<%
	List<ApplicationType> appTypes = (List<ApplicationType>) request.getAttribute(ServiceUIConstants.REQ_APP_TYPES);
%>
<form class="customer_list">
	<div class="operation" id="operation">
		<input type="button" class="btn btn-primary" name="application_add" id="applicationAdd" 
            onclick="loadContent('applicationAdd', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.apln.add'/>"/>
		<input type="button" class="btn" id="del" disabled value="<s:text name='lbl.hdr.comp.delete'/>" 
            onclick="loadContent('applicationDelete', $('#subcontainer'));"/>
		<s:if test="hasActionMessages()">
			<div class="alert alert-success alert-message"  id="successmsg">
				<s:actionmessage />
			</div>
		</s:if>
		<s:if test="hasActionErrors()">
			<div class="alert alert-error"  id="errormsg">
				<s:actionerror />
			</div>
		</s:if> 
	</div>
	<% if (CollectionUtils.isEmpty(appTypes)) { %>
            <div class="alert alert-block">
                <s:text name='alert.msg.appType.not.available'/>
            </div>
    <% } else { %>
			<div class="table_div">
				<div class="fixed-table-container">
				  <div class="header-background"></div>
					<div class="fixed-table-container-inner">
						<table cellspacing="0" class="zebra-striped">
							<thead>
								<tr>
									<th class="first nameTd">
										<div class="th-inner">
											<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
										</div>
									</th>
									<th class="second">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.cmp.name" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.cmp.desc" theme="simple"/></div>
									</th>
								</tr>
							</thead>
		
							<tbody>
								<%
									if (CollectionUtils.isNotEmpty(appTypes)) {
										for ( ApplicationType appType : appTypes) {
										    String disabledStr = "";
										    if (appType.isSystem()) {
										        disabledStr = "disabled";
										    } else {
										        disabledStr = "";
										    }
								%>
											<tr>
												<td class="checkboxwidth">
													<%  if (appType.isSystem()) { %>
			                                                <input type="checkbox" name="apptypeId" value="<%= appType.getId() %>" <%= disabledStr %>/>
			                                        <% } else { %>
			                                                <input type="checkbox" class="check" name="apptypeId" value="<%= appType.getId() %>" 
			                                                onclick="checkboxEvent();" <%= disabledStr %>/>
			                                        <% } %>
												</td>
												<td class="namelabel-width">
		                                            <%  if (appType.isSystem()) { %>
		                                                    <a href="#"><%= appType.getName() %></a>
		                                            <% } else { %>
		                                                    <a href="#" onclick="editAppType('<%= appType.getId() %>');"><%= appType.getName() %></a>
													<% } %>
												</td>
												<td class="desclabel-width">
		                                            <%= StringUtils.isNotEmpty(appType.getDescription()) ? appType.getDescription() : "" %>
		                                        </td>	
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
	function editAppType(id) {
		var params = "appTypeId=";
		params = params.concat(id);
		params = params.concat("&fromPage=");
		params = params.concat("edit");
		loadContent("applicationEdit", $('#subcontainer'), params);
	}
</script>