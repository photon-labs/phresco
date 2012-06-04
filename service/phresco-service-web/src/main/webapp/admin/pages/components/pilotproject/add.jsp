<%@ taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
function findError(data) {
	if(data.nameError != undefined) {
		showError($("#nameControl"), $("#nameError"), data.nameError);
	} else {
		hideError($("#nameControl"), $("#nameError"));
	}
	
	if(data.fileError != undefined) {
		showError($("#fileControl"), $("#fileError"), data.fileError);
		} else {
			hideError($("#fileControl"), $("#fileError"));
		}
}
</script>

<form class="form-horizontal customer_list">
	<h4><s:label for="description" key="lbl.header.comp.pltprjt.title" theme="simple"/></h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<s:label for="input01" key="lbl.header.comp.name" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="Customer Name" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<s:label for="input01" key="lbl.header.comp.desc" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<input id="input01" placeholder="Description" class="input-xlarge" type="text">
			</div>
		</div>
		
		<div class="control-group" id="fileControl">
			<s:label for="input01" key="lbl.header.comp.projsrc" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input class="input-xlarge" type="file" id="projArc" name="projArc">
				<span class="help-inline" id="fileError"></span>
			</div>
		</div>
	
	<div class="bottom_button">
		<input type="button" id="pilotprojSave" class="btn btn-primary" onclick="formSubmitFileUpload('pilotprojSave', 'projArc', $('#subcontainer'));" value="<s:text name='lbl.header.comp.save'/>"/>
		<input type="button" id="pilotprojCancel" class="btn btn-primary" onclick="loadContent('pilotprojCancel', $('#subcontainer'));" value="<s:text name='lbl.header.comp.cancel'/>"/>
	</div>
</form>