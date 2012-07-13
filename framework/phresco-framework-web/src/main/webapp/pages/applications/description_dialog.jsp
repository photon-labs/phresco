<%--
  ###
  Framework Web Archive
  
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
<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- Description dialog starts -->	
   	<div id="descriptionDialog" class="modal about" style="display:none;">
		<!-- Description dialog starts -->	
		<div id="descDialog" class="modal descPopUP" style="display:none">
			<div class="modal-header">
				<h3><s:text name="label.description"/></h3>
				<a id="close" href="#" class="close">&times;</a>
			</div>
			
			<div class="modal-body" style="height:113px; overflow-y:hidden">
				<div class = "desc_img">
					<img id="featureImg" border="0" alt="image" src="">
				</div>
				<div class = "desc_text">
					<p id="moduleDescription" class = "description"><s:text name="label.modal.description"/></p>
				</div>
			</div>
			
			<div class="modal-footer">
<!-- 				<input id="cancel" type="button" value="Cancel" class="btn primary"/> -->
				<input type="button" value="<s:text name="label.ok"/>" class="btn primary" id="closeDesc"/>
			</div>
		</div>
		<!-- Description dialog ends -->
	</div>
<!-- Description dialog ends -->	


<script type="text/javascript">
if(!isiPad()){
		$(".desc_text").scrollbars();
	}
</script>