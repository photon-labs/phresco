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

<%@ page import="com.photon.phresco.commons.model.User"%>
<%@ page import="com.photon.phresco.commons.model.Customer"%>
<%@ page import="com.photon.phresco.service.admin.commons.ServiceUIConstants"%>


<script type="text/javascript">
	$(document).ready(function() {
		var params = $('#formCustomerId').serialize();
		clickMenu($("a[name='compTab']"), $("#subcontainer"), params);
		loadContent("featuresList", params, $("#subcontainer"));
		activateMenu($("#features"));
		
		$("select[name='customerId']").change(function() {
			var selectedMenu = $("a[name='compTab'][class='active']").prop("id");
			params = $('#formCustomerId').serialize();
			loadContent(selectedMenu, params, $("#subcontainer"));
		});
	});
</script>

<%
    User userInfo = (User) session.getAttribute(ServiceUIConstants.SESSION_USER_INFO);
    List<Customer> customers = userInfo.getCustomers();
%>

<nav>
	<ul class="tabs">
		<li>
			<a href="#" class="active" name="compTab" id="featuresList"><s:label key="lbl.hdr.comp.featrs" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="archetypesList"><s:label key="lbl.hdr.comp.arhtyp" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="applntypesList"><s:label key="lbl.hdr.comp.aplntyp" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="configtempList"><s:label key="lbl.hdr.comp.cnfigtmplt" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="pilotprojList"><s:label key="lbl.hdr.comp.pltprjt" theme="simple"/></a>
		</li>
	</ul>
	<form id="formCustomerId">
		<div class="control-group customer_name">
			<s:label key="lbl.hdr.comp.customer" cssClass="control-label custom_label labelbold" theme="simple"/>
			<div class="controls customer_select_div">
				<select name="customerId" class="customer_listbox">
	                <% 
	                    if (CollectionUtils.isNotEmpty(customers)) { 
				            for (Customer customer : customers) { 
				    %>
	                            <option value="<%= customer.getId() %>"><%= customer.getName() %></option>
					<% 
				            }
				        } 
				    %>
				</select>
			</div>
		</div>
	</form>			
</nav>			

<section id="subcontainer" class="navTopBorder">

</section>
