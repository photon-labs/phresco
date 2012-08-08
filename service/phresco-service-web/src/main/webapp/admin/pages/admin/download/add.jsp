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

<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<%@ page import="com.photon.phresco.model.Technology"%>
<%@ page import="com.photon.phresco.model.DownloadInfo" %>
<%@ page import="com.photon.phresco.service.admin.commons.ServiceUIConstants" %> 

<%
    DownloadInfo downloadInfo = (DownloadInfo)request.getAttribute(ServiceUIConstants.REQ_DOWNLOAD_INFO);
    String fromPage = (String) request.getAttribute(ServiceUIConstants.REQ_FROM_PAGE);
    List<Technology> technologys = (List<Technology>)request.getAttribute(ServiceUIConstants.REQ_ARCHE_TYPES);
    String customerId = (String) request.getAttribute(ServiceUIConstants.REQ_CUST_CUSTOMER_ID);
%>

<form id="formDownloadAdd" class="form-horizontal customer_list">
	<h4>
	<% if (StringUtils.isNotEmpty(fromPage)) { %>
		<s:label key="lbl.hdr.adm.dwnlad.edit.title" theme="simple"/>
	<% } else { %>	
		<s:label key="lbl.hdr.adm.dwnlad.add.title" theme="simple"/>
	<% } %> 
	</h4>
	 
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.adm.name'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.download.add.name'/>" 
					value="<%= downloadInfo != null ? downloadInfo.getName() : "" %>" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
			
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.adm.desc'/>
			</label>
			<div class="controls">
				<input id="input01"  placeholder="<s:text name='place.hldr.download.add.desc'/>" class="input-xlarge" type="text"
					value="<%= downloadInfo != null ? downloadInfo.getDescription() : "" %>" name="description">
			</div>
		</div>
		
		<div class="control-group" id="applyControl">
            <label class="control-label labelbold">
                <span class="mandatory">*</span>&nbsp;<s:text name="Technology"/>
            </label>
            <div class="controls">
                <select id="multiSelect" multiple="multiple" name="technology">
                    <% if(technologys != null) {
                    	 for(Technology technology : technologys) { %>
                    		<option value="<%=technology.getName() %>"><%=technology.getName() %></option> 
                   <% 	 
                      }
                      }
                   %> 
                    
                </select>
                <span class="help-inline applyerror" id="techError"></span>
            </div>
        </div>
		
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.adm.dwnld.fle'/>
			</label>
			<div class="controls">
				<input class="input-xlarge" type="file" id="fileArc" name="fileArc">
			</div>
		</div>
		
		<div class="control-group" id="appltControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.adm.dwnld.appltfrm'/>
			</label>
			<div class="controls">
				<select id="multiSelect" multiple="multiple" name="application">
					<option  value="WN">Windows</option>
					<option  value="LN">Linux</option>
					<option  value="MC">Mac</option>
					<option  value="SL">Solaris</option>
				</select>
				<span class="help-inline applyerror" id="appltError"></span>
			</div>
		</div>
			
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.adm.dwnld.icon'/>
			</label>
			<div class="controls">
				<input class="input-xlarge" type="file" id="iconArc" name="iconArc">
			</div>
		</div>
			
		<div class="control-group" id="verControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.adm.dwnld.ver'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.download.add.version'/>" value="<%= downloadInfo != null ? downloadInfo.getVersion() : "" %>" class="input-xlarge" type="text" name="version">
				<span class="help-inline" id="verError"></span>
			</div>
		</div>
			
		<div class="control-group" id="groupControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.adm.dwnld.group'/>
			</label>	
			<div class="controls">
				<select id="select01" name="group">
					<option value="" onclick="javascript:hideDiv();">- select -</option>
					<option value="DB" onclick="javascript:hideDiv();">Database</option>
					<option value="SR" onclick="javascript:hideDiv();">Servers</option>
					<option value="ED" onclick="javascript:hideDiv();">Editors</option>
                    <option value="OT" onclick="javascript:showDiv();">Others</option>
				</select>
				<span class="help-inline" id="groupError"></span>
			</div>
		</div>
        
        <div class="control-group popupalign" id="othersDiv">
			<%-- <s:label cssClass="control-label labelbold" theme="simple"/> --%>
			<label class="control-label labelbold">
				<span class="mandatory">*</span>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.download.add.others'/>" class="input-xlarge" type="text" name="others">
				<span class="help-inline" id="othersError"></span>
			</div>
		</div>
	</div>

	<div class="bottom_button">
	   <% if (StringUtils.isNotEmpty(fromPage)) { %>
			<%-- <input type="button" id="downloadUpdate" class="btn btn-primary" value="<s:text name='lbl.hdr.comp.update'/>" 
				onclick="formSubmitFileUpload('downloadUpdate', 'fileArc,iconArc', $('#subcontainer'), 'Updating Download');" /> --%>
			   <input type="button" id="downloadUpdate" class="btn btn-primary" value="<s:text name='lbl.hdr.comp.update'/>" 
                onclick="validate('downloadUpdate', $('#formDownloadAdd'), $('#subcontainer'), 'Updating Download');" />	
        <% } else { %>
			<%-- <input type="button" id="downloadSave" class="btn btn-primary" onclick="formSubmitFileUpload('downloadSave', 'fileArc,iconArc', $('#subcontainer'), 'Creating Download');" value="<s:text name='lbl.hdr.comp.save'/>"/> --%>
		<input type="button" id="downloadSave" class="btn btn-primary"
			onclick="validate('downloadSave', $('#formDownloadAdd'), $('#subcontainer'), 'Creating Download');"
			value="<s:text name='lbl.hdr.comp.save'/>" />
		<% } %>
		<input type="button" id="downloadCancel" class="btn btn-primary" onclick="loadContent('downloadList', $('#formDownloadAdd'), $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.cancel'/>"/>
	</div>
	
	<!-- Hidden Fields -->
    <input type="hidden" name="fromPage" value="<%= StringUtils.isNotEmpty(fromPage) ? fromPage : "" %>"/>
    <input type="hidden" name="id" value="<%= downloadInfo != null ? downloadInfo.getId() : "" %>"/>
    <input type="hidden" name="oldName" value="<%= downloadInfo != null ? downloadInfo.getName() : "" %>"/> 
</form>

<script type="text/javascript">
	function findError(data) {
		if(data.nameError != undefined) {
			showError($("#nameControl"), $("#nameError"), data.nameError);
		} else {
			hideError($("#nameControl"), $("#nameError"));
		}
		
		if(data.verError != undefined) {
			showError($("#verControl"), $("#verError"), data.verError);
		} else {
			hideError($("#verControl"), $("#verError"));
		}
		
		if(data.appltError != undefined) {
			showError($("#appltControl"), $("#appltError"), data.appltError);
		} else {
			hideError($("#appltControl"), $("#appltError"));
		}
		
		if(data.groupError != undefined) {
			showError($("#groupControl"), $("#groupError"), data.groupError);
		} else {
			hideError($("#groupControl"), $("#groupError"));
		}
	}
	
	function showDiv() {
	    $('#othersDiv').show();
	}
	
	function hideDiv(){
	    $('#othersDiv').hide();
	}
</script>