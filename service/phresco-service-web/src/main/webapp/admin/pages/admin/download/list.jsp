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
<%@page import="org.apache.commons.collections.CollectionUtils" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="java.util.List" %>
<%@ page import="com.photon.phresco.model.DownloadInfo" %>
<%@ page import="com.photon.phresco.service.admin.commons.ServiceUIConstants" %>

<% List<DownloadInfo> downloadInfo = (List<DownloadInfo>)request.getAttribute(ServiceUIConstants.REQ_DOWNLOAD_INFO); %>


<form class="customer_list">
	<div class="operation" id="operation">
		<input type="button" id="downloadAdd" class="btn btn-primary" name="download_add" onclick="loadContent('downloadAdd', $('#subcontainer'));" value="<s:text name='lbl.hdr.adm.dwndllst.title'/>"/>
		<input type="button" id="del" class="btn" disabled onclick="loadContent('downloadDelete', $('#subcontainer'));" value="<s:text name='lbl.hdr.adm.delete'/>"/>
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
	<% if (CollectionUtils.isEmpty(downloadInfo)) { %>
            <div class="alert alert-block">
                <s:text name='alert.msg.download.not.available'/>
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
									<input type="checkbox" value="" id="checkAllAuto" class="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
								</div>
							</th>
							<th class="second">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.dwnldlst.name" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.dwnldlst.desc" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.dwnldlst.appltfrm" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.dwnldlst.ver"  theme="simple"/></div>
							</th>
						</tr>
					</thead>
		
					<tbody>
					       <% if(CollectionUtils.isNotEmpty(downloadInfo)){
					    	     for(DownloadInfo download : downloadInfo) {
					    	    	 
					       %>
					 
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="downloadId" value="<%= download.getId() %>" onclick="checkboxEvent();" >
							</td>
							<td class="namelabel-width">
								<a href="#" onclick="editDownload('<%= download.getId() %>');" name="edit" id="" ><%= download.getName() %></a>
							</td>
							<td ><%= download.getDescription() %></td>
							<td>WindowsXP/7</td>
							<td><%= download.getVersion()  %></td>
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
    /** To edit the download **/
    function editDownload(id) {
        var params = "id=";
        params = params.concat(id);
        params = params.concat("&fromPage=");
        params = params.concat("edit");
        loadContent("downloadEdit", $('#subcontainer'), params);
    }
</script>
