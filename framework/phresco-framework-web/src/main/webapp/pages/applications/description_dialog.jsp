<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- Description dialog starts -->	
   	<div id="descriptionDialog" class="modal about" style="display:none;">
		<!-- Description dialog starts -->	
		<div id="descDialog" class="modal descPopUP" style="display:none">
			<div class="modal-header">
				<h3><s:text name="label.description"/></h3>
				<a id="close" href="#" class="close">&times;</a>
			</div>
			
			<div class="modal-body" style="height:100px; overflow-y:auto">
				<p id="moduleDescription"><s:text name="label.modal.description"/></p>
			</div>
			
			<div class="modal-footer">
<!-- 				<input id="cancel" type="button" value="Cancel" class="btn primary"/> -->
				<input type="button" value="<s:text name="label.ok"/>" class="btn primary" id="closeDesc"/>
			</div>
		</div>
		<!-- Description dialog ends -->
	</div>
<!-- Description dialog ends -->	