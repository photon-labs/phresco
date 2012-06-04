<%@ taglib uri="/struts-tags" prefix="s" %>
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
	if(data.appError != undefined) {
		showError($("#appControl"), $("#appError"), data.appError);
	} else {
		hideError($("#appControl"), $("#appError"));
	}
	
	if(data.fileError != undefined) {
		showError($("#fileControl"), $("#fileError"), data.fileError);
		} else {
			hideError($("#fileControl"), $("#fileError"));
		}
}
</script>
<form class="form-horizontal customer_list">
	<h4><s:label for="description" key="lbl.header.comp.arhtyp.title" theme="simple"/></h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<s:label for="input01" key="lbl.header.comp.name" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="Archetype Name" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<s:label for="input01" key="lbl.header.comp.desc" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<input id="input01" placeholder="Description" class="input-xlarge" type="text">
			</div>
		</div>
		
		<div class="control-group" id="verControl">
			<s:label for="input01" key="lbl.header.comp.version" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="Version" class="input-xlarge" type="text" name="version">
				<span class="help-inline" id="verError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<s:label for="input01" key="lbl.header.com.vercmnt" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<textarea id="input01" placeholder="Version Comment" class="input-xlarge" rows="2" cols="10" ></textarea>
			</div>
		</div>
		
		<div class="control-group apptype" id="appControl">
		<s:label for="input01" key="lbl.header.comp.apptype" cssClass="control-label labelbold" theme="simple"/>
		<span class="mandatory">*</span>
			<div class="controls">
				<select id="select01" name="apptype">
					<option value="">- select -</option>
					<option value="MA">Mobile Application</option>
					<option value="WA">Web Application</option>
					<option value="NJ">Node JS</option>
					<option value="HT">HTML5</option>
				</select>
				<span class="help-inline" id="appError"></span>
			</div>
		</div>
		
		<div class="control-group" id="fileControl">
			<s:label for="input01" key="lbl.header.comp.applnjar" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input class="input-xlarge" type="file" id="applnArc" name="applnArc">
				<span class="help-inline" id="fileError"></span>
			</div>
		</div>
		
		<div id="jar">
			<div class="control-group">
				<s:label for="input01" key="lbl.header.comp.pluginjar" cssClass="control-label labelbold" theme="simple"/>
				<div class="controls">
					<input class="input-xlarge" type="file" id="pluginArc" name="pluginArc">
						<a><img src="images/add_icon.png" class="add imagealign"></a>
				</div>
			</div>
		</div>
		
	</div>
	
	<div class="bottom_button">
		<input type="button" id="archetypeSave" class="btn btn-primary" onclick="formSubmitFileUpload('archetypeSave', 'applnArc,pluginArc', $('#subcontainer'));" value="<s:text name='lbl.header.comp.save'/>"/>
		<input type="button" id="archetypeCancel" class="btn btn-primary" onclick="loadContent('archetypeCancel', $('#subcontainer'));" value="<s:text name='lbl.header.comp.cancel'/>"/>
	</div>
</form>

<script type="text/javascript">
	$(document).ready(function(){		
		$('.del').live('click',function(){
			$(this).parent().parent().remove();
		});
		
		$('.add').click(function(){
			var appendTxt = "<div id='jar'><div class='control-group'><label class='control-label labelbold' for='input01'>Plugin jar</label><div class='controls'><input class='input-xlarge' type='file'id='pluginArc' name='pluginArc'>&nbsp;<img src='images/minus_icon.png' class='del imagealign'></div></div></div>";
			$("div[id='jar']:last").after(appendTxt);			
		});
	});
</script>