<%@ taglib uri="/struts-tags" prefix="s" %>

<form class="form-horizontal customer_list">
	<div class="operation">
		<input type="button" id="featuresAdd" class="btn btn-primary" name="features_add" onclick="loadContent('featuresAdd', $('#subcontainer'));" value="<s:text name='lbl.header.comp.featrs.add'/>"/>
	</div>	
	<div class="content_adder">
		<div class="control-group">
			<div class="external_features_wrapper">
				<div class="theme_accordion_container" id="coremodule_accordion_container">
					<section class="accordion_panel_wid">
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion">
									<span>
										Login form 
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<thead>
													<tr>
														<th></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.cmp.desc" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.comp.ver" theme="simple"/></th>
													</tr>
												</thead>
													
												<tbody>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="" value="">
														</td>
														<td class="editFeatures_td2">
															<div class="accordalign"></div>
															<a href="#" name="ModuleDesc" class="" id="" >Log In</a>
														</td>
														<td class="editFeatures_td4">Authentication</td>
														<td class="editFeatures_td4">version 2.0</td>
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
										Blog
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<thead>
													<tr>
														<th class="accordiantable"></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.cmp.desc"  theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.comp.ver"  theme="simple"/></th>
													</tr>
												</thead>
																									
												<tbody>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="" value="">
														</td>
														<td class="editFeatures_td2">
															<div class="accordalign"></div>
															<a href="#" name="ModuleDesc" class="" id="" >Blog</a>
														</td>
														<td class="editFeatures_td4">Journal</td>
														<td class="editFeatures_td4">version 2.0</td>
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
										Help
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<thead>
													<tr>
														<th></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.cmp.desc"  theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.comp.ver" theme="simple"/></th>
													</tr>
												</thead>
																									
												<tbody>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="" value="">
														</td>
														<td class="editFeatures_td2">
															<div class="accordalign"></div>
															<a href="#" name="ModuleDesc" class="" id="" >Help</a>
														</td>
														<td class="editFeatures_td4">help</td>
														<td class="editFeatures_td4">version 2.0</td>
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
										Forum
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<thead>
													<tr>
														<th></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.cmp.desc"  theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.header.comp.ver" theme="simple"/></th>
													</tr>
												</thead>
																									
												<tbody>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="" value="">
														</td>
														<td class="editFeatures_td2">
															<div class="accordalign"></div>
															<a href="#" name="ModuleDesc" class="" id="" >Forum</a>
														</td>
														<td class="editFeatures_td4">Discussion site</td>
														<td class="editFeatures_td4">version 2.0</td>
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
