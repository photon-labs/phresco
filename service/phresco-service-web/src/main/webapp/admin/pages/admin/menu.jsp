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
	$(document).ready(function() {
		clickMenu($("a[name='adminTab']"),$("#subcontainer"));
		loadContent("customerList",$("#subcontainer"));
		activateMenu($("#customerList"));
	});
</script>
<nav>
	<ul class="tabs">
		<li>
			<a id="customerList" class="active" name="adminTab" href="#">
			<s:label for="description" key="lbl.header.admin.cust"  theme="simple"/></a>
		</li>
		<li>
			<a id="userList" class="inactive" name="adminTab" href="#">
			<s:label for="description" key="lbl.header.admin.users"  theme="simple"/></a>
		</li>
		<li>
			<a id="roleList" class="inactive" name="adminTab" href="#">
			<s:label for="description" key="lbl.header.admin.rles"  theme="simple"/></a>
		</li>
		<li>
			<a id="permissionList" class="inactive" name="adminTab" href="#">
			<s:label for="description" key="lbl.header.admin.perms"  theme="simple"/></a>
		</li>
		<li>
			<a id="ldap" class="inactive" name="adminTab" href="#">
			<s:label for="description" key="lbl.header.admin.ldapstng"  theme="simple"/></a>
		</li>
		<li>
			<a id="video" class="inactive" name="adminTab" href="#">
			<s:label for="description" key="lbl.header.admin.vdos"  theme="simple"/></a>
		</li>
		<li>
			<a id="globalurlList" class="inactive" name="adminTab" href="#">
			<s:label for="description" key="lbl.header.admin.glblurl"  theme="simple"/></a>
		</li>
		<li>
			<a id="downloadList" class="inactive" name="adminTab" href="#">
			<s:label for="description" key="lbl.header.admin.dwnld"  theme="simple"/></a>
		</li>
	</ul>		
</nav>

<section id="subcontainer" class="navTopBorder">

</section>