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

<%@ page import="com.photon.phresco.model.Technology" %>
<%@ page import="com.photon.phresco.service.admin.commons.ServiceUIConstants"%>

<%
	List<Technology> technologies = (List<Technology>) request.getAttribute(ServiceUIConstants.REQ_ARCHE_TYPES);
%>

<form class="customer_list">
	<div class="operation" id="operation">
		<input type="button" id="archetypeAdd" class="btn btn-primary" name="archetype_add" onclick="loadContent('archetypeAdd', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.arhtyp.add'/>"/>
		<input type="button" id="del" class="btn" disabled value="<s:text name='lbl.hdr.comp.delete'/>" onclick="loadContent('archetypeDelete', $('#subcontainer'));"/>
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
	<% if (CollectionUtils.isEmpty(technologies)) { %>
            <div class="alert alert-block">
                <s:text name='alert.msg.archetype.not.available'/>
            </div>
    <% } else { %>
			<div class="table_div">
				<div class="fixed-table-container">
					<div class="header-background"> </div>
					<div class="fixed-table-container-inner">
						<table cellspacing="0" class="zebra-striped">
							<thead>
								<tr>
									<th class="first">
										<div class="th-inner">
											<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
										</div>
									</th>
									<th class="second">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.cmp.name"  theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.cmp.desc"  theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.comp.ver"  theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.comp.apptype"  theme="simple"/></div>
									</th>
								</tr>
							</thead>
				
							<tbody>
										<%
											if (CollectionUtils.isNotEmpty(technologies)) {
												for ( Technology technology : technologies) {
										%>
													<tr>
														<td class="checkboxwidth">
															<input type="checkbox" class="check" name="techId" value="<%= technology.getId() %>" onclick="checkboxEvent();" />
														</td>
														<td class="namelabel-width">
															<a href="#" onclick="editTech('<%= technology.getId() %>');">
                                                                <%= StringUtils.isNotEmpty(technology.getName()) ? technology.getName() : "" %>
                                                            </a>
														</td>
														<td class="desclabel-width"><%= StringUtils.isNotEmpty(technology.getDescription()) ? technology.getDescription() : "" %></td>	
														<td class="namelabel-width"><%= CollectionUtils.isNotEmpty(technology.getVersions()) ? technology.getVersions() : "" %></td>
														<td class="namelabel-width"><%= CollectionUtils.isNotEmpty(technology.getAppType()) ? technology.getAppType() : "" %></td>		
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
    function editTech(id) {
		var params = "techId=";
		params = params.concat(id);
		params = params.concat("&fromPage=");
		params = params.concat("edit");
		loadContent("archetypeEdit", '', $('#subcontainer'), params);
	}
</script>