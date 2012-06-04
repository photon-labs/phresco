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
<%
    String sonarPath = request.getParameter("sonarPath");
%>
<div class="generateBuildModal" width="75%" height="250px">
	<div class="modal-header">
		<h3><s:text name="label.generatebuild"/></h3>
		<a class="close" href="#" id="close">&times;</a>
	</div>
	<div class="modal-body">
		<iframe src="<%= sonarPath %>" width="98%" height="250px"></iframe>
	</div>
	
	<div class="modal-footer">
        <div class="action popup-action">
            <input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="cancel">
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        $('#close, #cancel').click(function() {
            showParentPage();
        });
    });
    
</script>