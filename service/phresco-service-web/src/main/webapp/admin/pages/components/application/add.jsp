<%@ taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
function findError(data) {
	if(data.nameError != undefined) {
		showError($("#nameControl"), $("#nameError"), data.nameError);
	} else {
		hideError($("#nameControl"), $("#nameError"));
	}
}
</script>

<form class="form-horizontal customer_list">
	<h4><s:label for="description" key="lbl.header.comp.apln.title" theme="simple"/></h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<s:label for="input01" key="lbl.header.comp.name" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="Application Type Name" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<s:label for="input01" key="lbl.header.comp.desc" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<input id="input01" placeholder="Description" class="input-xlarge" type="text">
			</div>
		</div>
	</div>
	
	<div class="bottom_button">
		<input type="button" id="applicationSave" class="btn btn-primary" onclick="loadContent('applicationSave', $('#subcontainer'));" value="<s:text name='lbl.header.comp.save'/>"/>
		<input type="button" id="applicationCancel" class="btn btn-primary" onclick="loadContent('applicationCancel', $('#subcontainer'));" value="<s:text name='lbl.header.comp.cancel'/>"/>
	</div>
</form>