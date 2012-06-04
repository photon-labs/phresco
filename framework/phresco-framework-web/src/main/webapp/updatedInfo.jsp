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
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ page import="com.photon.phresco.commons.FrameworkConstants" %>

<script type="text/javascript" src="js/about.js"></script>

<div class="modal-header">
	<h3><s:text name="label.updated.version"/></h3>
	<a id="updatedVersionDialog" class="close" href="#" id="close">&times;</a>
</div>

<div class="modal-body abt_modal-body updatedInfoContent">
	<div class="">
		<span id="updatedVersionInfo"><%= request.getAttribute(FrameworkConstants.REQ_UPDATED_MESSAGE) %></span>
	</div>
</div>

<div class="modal-footer">
	<div class="abt_copyright abtQuest"></div>
	<div class="action abt_action">
		<input type="button" class="btn primary" value="<s:text name="label.ok"/>" id="updatedVersionOk">
	</div>
</div>