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
<%@ page import="java.util.List"%>

<%@ page import="com.photon.phresco.commons.model.Customer"%>
<%@ page import="com.photon.phresco.service.admin.commons.ServiceUIConstants"%>

<%
	List<Customer> customers = (List<Customer>) request.getAttribute(ServiceUIConstants.REQ_CUST_CUSTOMERS);
%>

<form class="customer_list">
	<div class="operation" id="operation">
		<input type="button" id="customerAdd" class="btn btn-primary" name="customer_add" value="<s:text name='lbl.hdr.adm.cust.add'/>" 
		      onclick="loadContent('customerAdd', $('#subcontainer'));"/>
		<input type="button"  id="del"  class="btn del" class="btn btn-primary" disabled value="<s:text name='lbl.hdr.adm.delete'/>" 
		      onclick="loadContent('customerDelete', $('#subcontainer'));"/>
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
	<% if (CollectionUtils.isEmpty(customers)) { %>
            <div class="alert alert-block">
                <s:text name='alert.msg.cust.not.available'/>
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
											<input type="checkbox" id="checkAllAuto" class="checkAllAuto" name="checkAllAuto" 
                                                onclick="checkAllEvent(this);">
										</div>
									</th>
									<th class="second">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.admim.cuslt.name" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.admim.cuslt.desc" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.cuslt.vldupto" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead">
                                            <div class="th-inner">
                                                <s:label key="lbl.hdr.adm.cusrlt.linctype" theme="simple"/>
                                            </div>
                                        </div>
									</th>
								</tr>
							</thead>
				
							<tbody>
								<%
									for (Customer customer : customers) {
								%>
										<tr>
											<td class="checkboxwidth">
												<input type="checkbox" class="check" name="customerId" value="<%= customer.getId() %>" 
												   onclick="checkboxEvent();" />
											</td>
											<td class="namelabel-width">
												<a href="#" onclick="editCustomer('<%= customer.getId() %>');"><%= customer.getName() %></a>
											</td>
											<td class="desclabel-width"><%= customer.getDescription() %></td>
											<td><%= customer.getValidUpto() %></td>
											<td><%= customer.getType() %></td>		
										</tr>	
								<%		
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
	/** To edit the customer **/
	function editCustomer(id) {
		var params = "customerId=";
		params = params.concat(id);
		params = params.concat("&fromPage=");
		params = params.concat("edit");
		loadContent("customerAdd", $('#subcontainer'), params);
	}
</script>