<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript">
function findError(data) {
	if(data.nameError != undefined) {
		showError($("#nameControl"), $("#nameError"), data.nameError);
	} else {
		hideError($("#nameControl"), $("#nameError"));
	}
	
	if(data.urlError != undefined) {
		showError($("#urlControl"), $("#urlError"), data.urlError);
	} else {
		hideError($("#urlControl"), $("#urlError"));
	}
}

</script>

<form class="form-horizontal customer_list">
	<h4><s:label for="description" key="lbl.header.admin.url.tiltle" theme="simple"/></h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<s:label for="input01" key="lbl.header.admin.name" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="Customer Name" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
		<s:label for="input01" key="lbl.header.admin.desc" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<input id="input01" placeholder="Description" class="input-xlarge" type="text" >
				
			</div>
		</div>
		
		<div class="control-group" id="urlControl">
			<s:label for="input01" key="lbl.header.admin.glblurl.url" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="URL" class="input-xlarge" type="text" name="url">
				<span class="help-inline" id="urlError"></span>
			</div>
		</div>
	</div>
	
	<div class="bottom_button">
		<input type="button" id="globalurlSave" class="btn btn-primary" onclick="loadContent('globalurlSave', $('#subcontainer'));" value="<s:text name='lbl.header.comp.save'/>"/>
		<input type="button" id="globalurlCancel" class="btn btn-primary" onclick="loadContent('globalurlCancel', $('#subcontainer'));" value="<s:text name='lbl.header.comp.cancel'/>"/>
	</div>
</form>