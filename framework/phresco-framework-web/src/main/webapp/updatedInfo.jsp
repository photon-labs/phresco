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