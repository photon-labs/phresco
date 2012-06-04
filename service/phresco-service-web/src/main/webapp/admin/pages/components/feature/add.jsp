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
    });

    $("input[type=checkbox]").change(function() {
        var checkboxChecked = $(this).is(":checked");
        var name = $(this).attr('name');
        if (!checkboxChecked) {
            $("input:radio[name='" + name + "']").prop("checked", false);
        } else {
            $("input:radio[name='" + name + "']:first").prop("checked", true);
        }
    });
    
</script>

<form class="form-horizontal customer_list" method="post" enctype="multipart/form-data">
	<h4><s:label for="description" key="lbl.header.comp.featrs.title" theme="simple"/></h4>	
	<div class="content_adder">

		
		<div class="control-group" id="nameControl">
			<s:label for="input01" key="lbl.header.comp.name" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="Feature Name" class="input-xlarge" type="text" name="name">
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
			<s:label for="input01" key="lbl.header.comp.help" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<textarea id="input01" placeholder="Help Text" class="input-xlarge" rows="2" cols="10" ></textarea>
			</div>
		</div>
		
		<div class="control-group" id="fileControl">
			<s:label for="input01" key="lbl.header.comp.file" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input class="input-xlarge" type="file" id="featureArc" name="featureArc">
				<span class="help-inline" id="fileError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<s:label for="input01" key="lbl.header.comp.dependency" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<a data-toggle="modal" href="#myModal"><input type="button" class="btn btn-primary addiconAlign" value="Select Dependency"></a>
			</div>
		</div>
	</div>
	
	<div id="myModal" class="modal hide fade">
		<div class="modal-header">
		  <a class="close" data-dismiss="modal" >&times;</a>
		  <h3><s:label for="description" key="lbl.hdr.comp.featr.popup.title" theme="simple"/></h3>
		</div>
		<div class="modal-body">
			<div class="content_adder">
				<div class="control-group">
					<div class="external_features_wrapper">
						<div class="theme_accordion_container popupaccord" id="coremodule_accordion_container">
							<section class="accordion_panel_wid">
								<div class="accordion_panel_inner">
									<section class="lft_menus_container">
										<span class="siteaccordion">
											<span>
												<input type="checkbox" name="loginForm" value="loginForm" id="">&nbsp;&nbsp;Login form 
											</span>
										</span>
										<div class="mfbox siteinnertooltiptxt">
											<div class="scrollpanel">
												<section class="scrollpanel_inner">
													<table class="download_tbl">
														<thead>
															<tr>
																<th></th>
																<th class="accordiantable modallbl-color"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></th>
																<th class="accordiantable modallbl-color"><s:label for="description" key="lbl.header.comp.ver" theme="simple"/></th>
															</tr>
														</thead>
																											
														<tbody>
															<tr>
																<td class="editFeatures_td1">
																	<input type="radio" class="" name="loginForm" value="">
																</td>
																<td class="editFeatures_td2">
																	<div class="accordalign"></div>
																	<a href="#" name="ModuleDesc" class="modallbl-color">Login form</a>
																</td>
																<td class="editFeatures_td4 modallbl-color">version 2.0</td>
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
											</span>
										</span>
										<div class="mfbox siteinnertooltiptxt">
											<div class="scrollpanel">
												<section class="scrollpanel_inner">
													<table class="download_tbl">
														<thead>
															<tr>
																<th></th>
																<th class="accordiantable modallbl-color"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></th>
																<th class="accordiantable modallbl-color"><s:label for="description" key="lbl.header.comp.ver" theme="simple"/></th>
															</tr>
														</thead>
																											
														<tbody>
															<tr>
																<td class="editFeatures_td1">
																	<input type="radio" name="blog" value="">
																</td>
																<td class="editFeatures_td2">
																	<div class="accordalign"></div>
																	<a href="#" name="ModuleDesc" class="modallbl-color">Blog</a>
																</td>
																<td class="editFeatures_td4 modallbl-color">version 2.0</td>
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
											</span>
										</span>
										<div class="mfbox siteinnertooltiptxt">
											<div class="scrollpanel">
												<section class="scrollpanel_inner">
													<table class="download_tbl">
														<thead>
															<tr>
																<th></th>
																<th class="accordiantable modallbl-color"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></th>
																<th class="accordiantable modallbl-color"><s:label for="description" key="lbl.header.comp.ver" theme="simple"/></th>
															</tr>
														</thead>
																											
														<tbody>
															<tr>
																<td class="editFeatures_td1">
																	<input type="radio" name="Forum" value="">
																</td>
																<td class="editFeatures_td2">
																	<div class="accordalign"></div>
																	<a href="#" name="ModuleDesc" class="modallbl-color">Forum</a>
																</td>
																<td class="editFeatures_td4 modallbl-color">version 2.0</td>
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
											</span>
										</span>
										<div class="mfbox siteinnertooltiptxt">
											<div class="scrollpanel">
												<section class="scrollpanel_inner">
													<table class="download_tbl">
														<thead>
															<tr>
																<th></th>
																<th class="accordiantable modallbl-color"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></th>
																<th class="accordiantable modallbl-color"><s:label for="description" key="lbl.header.comp.ver" theme="simple"/></th>
															</tr>
														</thead>
																											
														<tbody>
															<tr>
																<td class="editFeatures_td1">
																	<input type="radio" name="Help" value="">
																</td>
																<td class="editFeatures_td2">
																	<div class="accordalign"></div>
																	<a href="#" name="ModuleDesc" class="modallbl-color">Help</a>
																</td>
																<td class="editFeatures_td4 modallbl-color">version 2.0</td>
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
		</div>
		<div class="modal-footer">
		  <a href="#" class="btn btn-primary" data-dismiss="modal"><s:label for="description" key="lbl.header.comp.cancel" theme="simple"/></a>
		  <a href="#" class="btn btn-primary" data-dismiss="modal" ><s:label for="description" key="lbl.header.comp.ok" theme="simple"/></a>
		</div>
	</div>
	<div class="bottom_button">
		<input type="button" id="featuresSave" class="btn btn-primary" onclick="formSubmitFileUpload('featuresSave', 'featureArc', $('#subcontainer'));" value="<s:text name='lbl.header.comp.save'/>"/>
		<input type="button" id="featuresCancel" class="btn btn-primary" onclick="loadContent('featuresCancel', $('#subcontainer'));" value="<s:text name='lbl.header.comp.cancel'/>"/>		
	</div>
</form>

<script type="text/javascript">
	
		/** Accordian starts **/
		var showContent = 0;
	    $('.siteaccordion').removeClass('openreg').addClass('closereg');
	    $('.mfbox').css('display','none')
	    
	    $('.siteaccordion').bind('click',function(e){
	        var _tempIndex = $('.siteaccordion').index(this);
	            $('.siteaccordion').removeClass('openreg').addClass('closereg');
	            $('.mfbox').each(function(e){
	                if($(this).css('display')=='block'){
	                    $(this).slideUp('300');
	                }
	            })
	        if($('.mfbox').eq(_tempIndex).css('display')=='none'){
	            $(this).removeClass('closereg').addClass('openreg');
	            $('.mfbox').eq(_tempIndex).slideDown(300,function(){
	                
	            });
	            
	        }
	        
	    });
	    /** Accordian ends **/
		
</script> 
