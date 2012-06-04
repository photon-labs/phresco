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