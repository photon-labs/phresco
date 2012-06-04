<%@ taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
function findError(data) {
	if(data.nameError != undefined) {
		showError($("#nameControl"), $("#nameError"), data.nameError);
	} else {
		hideError($("#nameControl"), $("#nameError"));
	}
	
	if(data.applyError != undefined) {
		showError($("#applyControl"), $("#applyError"), data.applyError);
	} else {
		hideError($("#applyControl"), $("#applyError"));
	}
}
</script>

<form name="configForm" class="form-horizontal customer_list">
	<h4><s:label for="description" key="lbl.header.comp.cnfigtmplt.title" theme="simple"/></h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<s:label for="input01" key="lbl.header.comp.name" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="configname" placeholder="Config Template Name" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<s:label for="input01" key="lbl.header.comp.desc" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<input id="input01" placeholder="Description" class="input-xlarge" type="text">
			</div>
		</div>
		
		<div class="control-group" id="applyControl">
			<s:label for="input01" key="lbl.header.comp.appliesto" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<select id="multiSelect" multiple="multiple" name="applies">
					<option value="PH">PHP</option>
					<option value="DR">Drupal</option>
					<option value="HT">HTML5 Multichannel Widget</option>
					<option value="JA">Java</option>
					<option value="NJ">Node JS</option>
				</select>
				<span class="help-inline" id="applyError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<s:label for="input01" key="lbl.header.comp.help" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<input id="input01" placeholder="Help Text" class="input-xlarge" type="text">
			</div>
		</div>						
			
		<fieldset class = "configFieldset">
			<legend class = "configLegend"><s:label for="description" key="lbl.header.comp.proptemplate" theme="simple"/></legend>
			<div class = "table_div">
				<div class="fixed-table-container prpt-header">
					<div class="fixed-table-container-inner">
						<table cellspacing="0" class="zebra-striped tablelegend">
							<thead class = "fieldset-tableheader" >
								<tr>
									<!-- <th class="first" >
										<div class="th-inner fieldset-table tablehead" ><s:label for="description" key="lbl.header.comp.cnfigtmplt.prtyname.title" theme="simple"/></div>
									</th>-->
									<th class="second">
										<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.cnfigtmplt.key.title" theme="simple"/></div>
									</th>
									<th class="second">
										<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.cnfigtmplt.type.title" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.cnfigtmplt.psblvalue.title"  theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.cnfigtmplt.mndtry.title"  theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.cnfigtmplt.mltpl.title" theme="simple"/></div>
									</th>
									<th class="third">
										<div class="th-inner">
											
										</div>
									</th>
								</tr>
							</thead>
							<div id="input1" class="clonedInput">
								<tbody>
									<tr class="configdynamiadd">
										<!-- td style="width: 14%;">
											<input type="text" placeholder="" class="input-medium propertytext">
										</td> -->
										<td class="textwidth">
											<input type="text" id = "concate" value="" placeholder="" class="span2">
										</td>
										<td class="textwidth">
											<select id="select01" class = "select">
												<option>- select -</option>
												<option>String</option>
												<option>Integer</option>
												<option>Data</option>
												<option>Password</option>
											</select>
										</td>
										<td class="psblevalue">
											<input type="text" placeholder="" class="span3">
											<a data-toggle="modal" href="#myModal"><img class="addiconAlign imagealign" src="images/add_icon.png"/></a>
										</td>
										<td class="mandatoryfld">
											<input type="checkbox" value="option1" id="optionsCheckbox">
										</td>
										<td class="multiplefld">
											<input type="checkbox" value="option1" id="optionsCheckbox">
										</td>
										<td class="imagewidth">
											<a ><img class="add imagealign" src="images/add_icon.png"></a>
										</td>
									</tr>
								</tbody>
							</div>
						</table>
						
						<div id="myModal" class="modal hide fade">
							<div class="modal-header">
								<a class="close" data-dismiss="modal" >&times;</a>
								<h3><s:label for="description" key="lbl.header.comp.cnfigtmplt.popup.title" theme="simple"/></h3>
							</div>
							<div class="modal-body">
								<div class="control-group">
									<s:label for="description" key="lbl.header.comp.popup.enter" cssClass="control-label labelbold modallbl-color" theme="simple"/>
									<div class="controls">
										<input type="text" name="txtCombo" id="txtCombo" class="span3"/>
										<button type="button" value="" id="addValues" class="btn btn-primary popupadd">
											<s:label for="description" key="lbl.header.comp.popup.add" theme="simple"/>
										</button>
									</div>
								</div>
								
								<div class="control-group">
									<div class="controls">
										<select name="valuesCombo" id="valuesCombo" multiple="multiple">
										
										</select>
										<div class="popopimage">
											<img src="images/up_arrow.jpeg" title="Move up" id="up" class="imageupdown"><br>
											<img src="images/remove.jpeg" title="Remove" id="remove" class="imageremove"><br>
											<img src="images/down_arrow.png" title="Move down" id="down" class="imageupdown" >
										</div>
									</div>
								</div>
							</div>
							
							<div class="modal-footer">
								<a href="#" class="btn btn-primary" data-dismiss="modal"><s:label for="description" key="lbl.header.comp.cancel" theme="simple"/></a>
								<a href="#" class="btn btn-primary" data-dismiss="modal" ><s:label for="description" key="lbl.header.comp.ok" theme="simple"/></a>
							</div>
						</div>
					</div>	
				</div>
			</div>	
		</fieldset>
	</div>
	
	<div class="bottom_button">
		<input type="button" id="configtempSave" class="btn btn-primary" onclick="loadContent('configtempSave', $('#subcontainer'));" value="<s:text name='lbl.header.comp.save'/>"/>
		<input type="button" id="configtempCancel" class="btn btn-primary" onclick="loadContent('configtempCancel', $('#subcontainer'));" value="<s:text name='lbl.header.comp.cancel'/>"/>
	</div>
</form>

<script language="javascript">
$(document).ready(function() {
	$("#addValues").click(function() {
		var val = $("#txtCombo").val();
		$("#valuesCombo").append($("<option></option>").attr("value", val).text(val));
		$("#txtCombo").val("");
	});

	$('.add').click(function() {
		var appendRow =  '<tr class="configdynamiadd">' + $('.configdynamiadd').html() + '</tr>';
		appendRow = appendRow.replace('class="add imagealign" src="images/add_icon.png"', 'class = "del imagealign" src="images/minus_icon.png" onclick="removeTag(this);"');
		$("tr:last").after(appendRow);			
	}); 

	$('#remove').click(function() {
		$('#valuesCombo option:selected').each( function() {
			$(this).remove();
		});
	});

	//To move up the values
	$('#up').bind('click', function() {
		$('#valuesCombo option:selected').each( function() {
			var newPos = $('#valuesCombo  option').index(this) - 1;
			if (newPos > -1) {
				$('#valuesCombo  option').eq(newPos).before("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
				$(this).remove();
			}
		});
	});

	//To move down the values
	$('#down').bind('click', function() {
		var countOptions = $('#valuesCombo option').size();
		$('#valuesCombo option:selected').each( function() {
			var newPos = $('#valuesCombo  option').index(this) + 1;
			if (newPos < countOptions) {
				$('#valuesCombo  option').eq(newPos).after("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
				$(this).remove();
			}
		});
	});
});

function removeTag(currentTag) {
	$(currentTag).parent().parent().parent().remove();
}
</script>