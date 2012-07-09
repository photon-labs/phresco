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
		clickMenu($("a[name='compTab']"), $("#subcontainer"));
		loadContent("featuresList", $("#subcontainer"));
		activateMenu($("#features"));
	});
</script>
	
<nav>
	<ul class="tabs">
		<li>
			<a href="#" class="active" name="compTab" id="featuresList"><s:label for="description" key="lbl.hdr.comp.featrs" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="archetypesList"><s:label for="description" key="lbl.hdr.comp.arhtyp" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="applntypesList"><s:label for="description" key="lbl.hdr.comp.aplntyp" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="configtempList"><s:label for="description" key="lbl.hdr.comp.cnfigtmplt" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="pilotprojList"><s:label for="description" key="lbl.hdr.comp.pltprjt" theme="simple"/></a>
		</li>
			
	</ul>
	<div class="control-group customer_name">
		<s:label for="input01" key="lbl.hdr.comp.customer" cssClass="control-label custom_label labelbold" theme="simple"/>
		<div class="controls customer_select_div">
			<select id="select01" class="customer_listbox">
				<option>Walgreens</option>
				<option>NBO</option>
				<option>Cengage</option>
				<option>Horizon Blue</option>
				<option>Photon</option>
			</select>
		</div>
	</div>			
</nav>			

<section id="subcontainer" class="navTopBorder">

</section>
