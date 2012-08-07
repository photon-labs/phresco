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

<script type="text/javascript">
	function findError(data) {
		if(data.nameError != undefined) {
			showError($("#nameControl"), $("#nameError"), data.nameError);
		} else {
			hideError($("#nameControl"), $("#nameError"));
		}
		
		if(data.versError != undefined) {
			showError($("#verControl"), $("#verError"), data.versError);
		} else {
			hideError($("#verControl"), $("#verError"));
		}
		
		if(data.fileError != undefined) {
    		showError($("#fileControl"), $("#fileError"), data.fileError);
   		} else {
   			hideError($("#fileControl"), $("#fileError"));
   		}
	}
	
	
	$("input[type=radio]").change(function() {
        var name = $(this).attr('name');
        $("input:checkbox[name='" + name + "']").prop("checked", true);
        var version = $("input:radio[name='" + name + "']").val();
		$("p[id='" + name + "']").html(version);
    });

	$("input[type=checkbox]").change(function() {
		var checkboxChecked = $(this).is(":checked");
		var name = $(this).attr('name');
		if (!checkboxChecked) {
			$("input:radio[name='" + name + "']").prop("checked", false);
			$("p[id='" + name + "']").empty();
		} else {
			$("input:radio[name='" + name + "']:first").prop("checked", true);
			var version = $("input:radio[name='" + name + "']").val();
			$("p[id='" + name + "']").html(version);
		}
	});
    
	// Check box change function
	/* $("input[type=checkbox]").change(function() {
		var checkboxChecked = $(this).is(":checked");
		var moduleId = $(this).val();
		alert(moduleId);
		alert("checkboxChecked::"+checkboxChecked);
		if (checkboxChecked) {
			$("input:radio[name='" + moduleId + "']:first").prop("checked", true);
			
			var Version = $("input:radio[name='" + moduleId + "']:first").val();
			$("p[id='" + moduleId + "Version']").html(Version);		
			alert("if" + Version);
		} else {
			$("input[name='" + moduleId + "']").prop("checked", false);
			
			$("p[id='" + moduleId + "Version']").empty();
			var name = $(this).attr('class');
			$("input:checkbox[name='" + name + "']").prop("checked", false);
			alert("else" + Version);
		}
	}); */
    
</script>

<form class="form-horizontal customer_list" method="post" enctype="multipart/form-data">
	<h4 class="hdr"><s:label key="lbl.hdr.comp.featrs.title" theme="simple"/></h4>	
	<div class="content_adder">

		<div class="control-group" id="nameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.name'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.feature.add.name'/>" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.comp.desc'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.feature.add.desc'/>" class="input-xlarge" type="text">
				
			</div>
		</div>
		
		<div class="control-group" id="verControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.version'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.feature.add.version'/>" class="input-xlarge" type="text" name="version">
				<span class="help-inline" id="verError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.comp.help'/>
			</label>
			<div class="controls">
				<textarea id="input01" placeholder="<s:text name='place.hldr.feature.add.help.text'/>" class="input-xlarge" rows="2" cols="10" ></textarea>
			</div>
		</div>
		
		<div class="control-group" id="fileControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.file'/>
			</label>
			<div class="controls">
				<input class="input-xlarge" type="file" id="featureArc" name="featureArc">
				<span class="help-inline" id="fileError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.comp.dependency'/>
			</label>
			<div class="controls">
				<a data-toggle="modal" href="#myModal"><input type="button" class="btn btn-primary addiconAlign" value="Select Dependency"></a>
			</div>
		</div>
	</div>
	
	<div id="myModal" class="modal hide fade">
		<div class="modal-header">
		  <a class="close" data-dismiss="modal" >&times;</a>
		  <h3><s:label key="lbl.hdr.comp.featr.popup.title" theme="simple"/></h3>
		</div>
		<div class="modal-body">
			<div class="control-group">
				<div class="external_features_wrapper">
					<div class="theme_accordion_container popupaccord" id="coremodule_accordion_container">
						<section class="accordion_panel_wid">
							<div class="accordion_panel_inner">
								<section class="lft_menus_container">
									<span class="siteaccordion">
										<span>
											<input type="checkbox" name="loginForm" id="">&nbsp;&nbsp;Login form 
											<p id="loginForm" class="accord_veralign"></p>
										</span>
									</span>
									<div class="mfbox siteinnertooltiptxt">
										<div class="scrollpanel">
											<section class="scrollpanel_inner">
												<table class="download_tbl">
													<thead>
														<tr>
															<th></th>
															<th class="accordiantable modallbl-color"><s:label key="lbl.hdr.cmp.name" theme="simple"/></th>
															<th class="accordiantable modallbl-color"><s:label key="lbl.hdr.comp.ver" theme="simple"/></th>
														</tr>
													</thead>
																										
													<tbody>
														<tr>
															<td class="editFeatures_td1">
																<input type="radio" class="" name="loginForm" value="2.0">
															</td>
															<td class="editFeatures_td2">
																<div class="accordalign"></div>
																<a href="#" name="ModuleDesc" class="modallbl-color">Login form</a>
															</td>
															<td class="editFeatures_td4 modallbl-color">2.0</td>
														</tr>
													</tbody>
												</table>
											</section>
										</div>
									</div>
								</section>  
							</div>
							
							<div class="accordion_panel_inner">
								<section class="lft_menus_container">
									<span class="siteaccordion">
										<span>
											<input type="checkbox" name="blog" value="">&nbsp;&nbsp;Blog
											<p id="blog" class="accord_veralign"></p>
										</span>
									</span>
									<div class="mfbox siteinnertooltiptxt">
										<div class="scrollpanel">
											<section class="scrollpanel_inner">
												<table class="download_tbl">
													<thead>
														<tr>
															<th></th>
															<th class="accordiantable modallbl-color"><s:label key="lbl.hdr.cmp.name" theme="simple"/></th>
															<th class="accordiantable modallbl-color"><s:label key="lbl.hdr.comp.ver" theme="simple"/></th>
														</tr>
													</thead>
																										
													<tbody>
														<tr>
															<td class="editFeatures_td1">
																<input type="radio" name="blog" value="1.0">
															</td>
															<td class="editFeatures_td2">
																<div class="accordalign"></div>
																<a href="#" name="ModuleDesc" class="modallbl-color">Blog</a>
															</td>
															<td class="editFeatures_td4 modallbl-color">1.0</td>
														</tr>
													</tbody>
												</table>
											</section>
										</div>
									</div>
								</section>  
							</div>
							
							<div class="accordion_panel_inner">
								<section class="lft_menus_container">
									<span class="siteaccordion">
										<span>
											<input type="checkbox" name="Forum" value="">&nbsp;&nbsp;Forum
											<p id="Forum" class="accord_veralign"></p>
										</span>
									</span>
									<div class="mfbox siteinnertooltiptxt">
										<div class="scrollpanel">
											<section class="scrollpanel_inner">
												<table class="download_tbl">
													<thead>
														<tr>
															<th></th>
															<th class="accordiantable modallbl-color"><s:label key="lbl.hdr.cmp.name" theme="simple"/></th>
															<th class="accordiantable modallbl-color"><s:label key="lbl.hdr.comp.ver" theme="simple"/></th>
														</tr>
													</thead>
																										
													<tbody>
														<tr>
															<td class="editFeatures_td1">
																<input type="radio" name="Forum" value="2.0">
															</td>
															<td class="editFeatures_td2">
																<div class="accordalign"></div>
																<a href="#" name="ModuleDesc" class="modallbl-color">Forum</a>
															</td>
															<td class="editFeatures_td4 modallbl-color">2.0</td>
														</tr>
													</tbody>
												</table>
											</section>
										</div>
									</div>
								</section>  
							</div>
							
							<div class="accordion_panel_inner">
								<section class="lft_menus_container">
									<span class="siteaccordion">
										<span>
											<input type="checkbox" name="Help" value="">&nbsp;&nbsp;Help
											<p id="Help" class="accord_veralign"></p>
										</span>
									</span>
									<div class="mfbox siteinnertooltiptxt">
										<div class="scrollpanel">
											<section class="scrollpanel_inner">
												<table class="download_tbl">
													<thead>
														<tr>
															<th></th>
															<th class="accordiantable modallbl-color"><s:label key="lbl.hdr.cmp.name" theme="simple"/></th>
															<th class="accordiantable modallbl-color"><s:label key="lbl.hdr.comp.ver" theme="simple"/></th>
														</tr>
													</thead>
																										
													<tbody>
														<tr>
															<td class="editFeatures_td1">
																<input type="radio" name="Help" value="1.1">
															</td>
															<td class="editFeatures_td2">
																<div class="accordalign"></div>
																<a href="#" name="ModuleDesc" class="modallbl-color">Help</a>
															</td>
															<td class="editFeatures_td4 modallbl-color">1.1</td>
														</tr>
													</tbody>
												</table>
											</section>
										</div>
									</div>
								</section>  
							</div>
							
						</section>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
		  <a href="#" class="btn btn-primary" data-dismiss="modal"><s:label key="lbl.hdr.comp.cancel" theme="simple"/></a>
		  <a href="#" class="btn btn-primary" data-dismiss="modal" ><s:label key="lbl.hdr.comp.ok" theme="simple"/></a>
		</div>
	</div>
	<div class="bottom_button">
		<input type="button" id="featuresSave" class="btn btn-primary" onclick="formSubmitFileUpload('featuresSave', 'featureArc', $('#subcontainer'), 'Creating Feature');" value="<s:text name='lbl.hdr.comp.save'/>"/>
		<input type="button" id="featuresCancel" class="btn btn-primary" onclick="loadContent('featuresCancel', '', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.cancel'/>"/>		
	</div>
</form>
