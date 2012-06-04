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

<aside id="sidebar">
	<section>
		<div class="right">
			<div class="right_navbar active">
				<div class="barclose">
					<div class="lnclose"><s:text name="label.latest"/>&nbsp;<s:text name="label.news"/></div>
				</div>
			</div>
			<div class="right_barcont">
				<div class="searchsidebar">
					<div class="newstext">
						<s:text name="label.latest"/> <span><s:text name="label.news"/></span>
					</div>
					<div class="topsearchinput">
						<input name="" type="text" id="search" title="search" disabled="disabled" onkeypress="javascript:filter();">
					</div>
					<div class="linetopsearch"></div>
				</div>
				<div id="tweets" class="sc7 scrollable dymanic paddedtop">
					<div class="tweeterContent"></div>
				</div>
			</div>
			<br clear="left">
		</div>
	</section>
</aside>

<script type="text/javascript">
	$(document).ready(function() {
		$.getJSON("http://twitter.com/statuses/user_timeline/photoninfotech.json?callback=?", function(data) {
		for(i in data) {
			$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');
		}				
		});
		});
	
	function parseTwitterDate($stamp)
	{		
		var date = $stamp;	
		return date.substr(0,16);
	}
	
	function filter(){
		var value = document.getElementById('search').value;
		$('.tweeterContent').empty();
		$.getJSON("http://twitter.com/statuses/user_timeline/photoninfotech.json?callback=?", function(data) {
			for(i in data) {
				if(data[i].text.indexOf(value)!= -1){
					$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');
				}
			}				
		});
	}
</script>