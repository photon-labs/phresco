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

<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.photon.phresco.model.ApplicationType" %>

<script type="text/javascript">
	$(document).ready(function() {
		//To focus the name textbox by default
		$('#name').focus();
		
		// To check for the special character in name
		$('#name').bind('input propertychange', function (e) {
			var name = $(this).val();
			name = checkForSplChr(name);
	    	$(this).val(name);
		});	
	});

	function findError(data) {
		if (data.nameError != undefined) {
			showError($("#nameControl"), $("#nameError"), data.nameError);
		} else {
			hideError($("#nameControl"), $("#nameError"));
		}
	}
	
	function constructParams(mthdName, url, tag, progText) {
		var params = "";
		if (!isBlank($('#formAppTypeAdd').serialize())) {
			params = $('#formAppTypeAdd').serialize() + "&";
		}
		params = params.concat($('#formCustomerId').serialize());
		window[mthdName](url, params, tag, progText); //This is call methods dynamically
	}
</script>

<%
	ApplicationType apptype = (ApplicationType)request.getAttribute("appType");
	String fromPage = (String) request.getAttribute("fromPage");
%>

<form id="formAppTypeAdd" class="form-horizontal customer_list">
  <h4 class="hdr">
   <% if (StringUtils.isNotEmpty(fromPage)) { %>
				<s:label key="lbl.hdr.comp.apln.edit.title" theme="simple" />
		<% } else { %>
	            <s:label key="lbl.hdr.comp.apln.title" theme="simple"/>	
	    <% } %>
     </h4>       
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.name'/>
			</label>
			<div class="controls">
				<input id="name" class="input-xlarge" placeholder='<s:text name="place.hldr.appType.add.name"/>'  type="text" name="name" value="<%= apptype != null ? apptype.getName() : "" %>" maxlength="30" title="30 Characters only">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.comp.desc'/>
			</label>
			<div class="controls">
				<textarea id="description" class="input-xlarge" placeholder='<s:text name="place.hldr.appType.add.desc"/>' rows="3" name="description" maxlength="150" title="150 Characters only"><%= apptype != null ? apptype.getDescription() : "" %></textarea>
			</div>
		</div>
	</div>
	
	<div class="bottom_button">
	   	<% if (StringUtils.isNotEmpty(fromPage)) { %>
				<input type="button" id="applicationUpdate" class="btn btn-primary" value="<s:text name='lbl.hdr.comp.update'/>" 
				    onclick="constructParams('validate', 'applicationUpdate', $('#subcontainer'), 'Updating Application Type');" />
		<% } else { %>
                <input type="button" id="applicationSave" class="btn btn-primary" value="<s:text name='lbl.hdr.comp.save'/>"
                    onclick="constructParams('validate', 'applicationSave', $('#subcontainer'), 'Creating Application Type');" />
		<% } %>
		<input type="button" id="applicationCancel" class="btn btn-primary" value="<s:text name='lbl.hdr.comp.cancel'/>" 
            onclick="constructParams('loadContent', 'applntypesList', $('#subcontainer'));" />
    </div>
	
	<!-- Hidden Fields -->
	<input type="hidden" name="fromPage" value="<%= StringUtils.isNotEmpty(fromPage) ? fromPage : "" %>"/>
	<input type="hidden" name="appTypeId" value="<%= apptype != null ? apptype.getId() : "" %>"/>
	<input type="hidden" name="oldName" value="<%= apptype != null ? apptype.getName() : "" %>"/> 
</form>