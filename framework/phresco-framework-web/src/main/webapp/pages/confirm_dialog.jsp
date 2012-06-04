<%@ taglib uri="/struts-tags" prefix="s"%>
<!-- Confirmation dialog starts -->	
<div id="confirm" class="modal confirm">
	<div class="modal-header">
		<h3><s:text name="label.delete.confirm"/></h3>
		<a id="close" href="#" class="close">&times;</a>
	</div>
	
	<div class="modal-body">
		<p id="confirmationText">Do you want to delete the selected object(s)?</p>
	</div>
	
	<div class="modal-footer">
		<input id="cancel" type="button" value="<s:text name="label.cancel"/>" class="btn primary"/>
		<input type="button" value="<s:text name="label.ok"/>" class="btn primary" id="ok" onClick="onOk()" />
	</div>
</div>
<!-- Confirmation dialog ends -->	