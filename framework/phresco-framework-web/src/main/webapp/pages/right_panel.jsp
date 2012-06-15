<%@ taglib uri="/struts-tags" prefix="s"%>
<%--
	private static final org.apache.log4j.Logger S_LOGGER = org.apache.log4j.Logger.getLogger("Tweeter");
%>

<% 
String responseString = "hariharan";
java.io.FileWriter fw = null;
java.io.File resultJson = null;
try {
	com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
	com.sun.jersey.api.client.WebResource webResource = client.resource("http://localhost:3030/service/news");
	responseString = webResource.get(String.class);
	System.out.println("info :: " + responseString);
} catch (java.lang.Exception e) {
	S_LOGGER.error("Exception while retreiving the Tweeter Message :: " + e.getMessage());
	e.printStackTrace();
}
%>


if(responseString!=null && !responseString.isEmpty() && !responseString.contains("Rate limit exceeded. Clients may not make more than 150 requests per hour.")){
	try {
		responseString = responseString.replace("([", "[");
		responseString = responseString.replace("]);", "]");
		String path = request.getSession().getServletContext().getRealPath("") + "/result.json";
		resultJson = new java.io.File(path);
		fw = new java.io.FileWriter(resultJson);
		fw.write(responseString);
	} catch (java.lang.Exception e) {
		S_LOGGER.error("Exception while writing Tweeter Message to a local file :: " + e.getMessage());
	} finally {
		if (fw != null) {
			try {
				fw.flush();
				fw.close();
			} catch (java.lang.Exception e) {
				S_LOGGER.error("Exception while closing local file after writing Tweeter Message :: " + e.getMessage());
			}
		}
	}
}
--%>

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
						<input name="" type="text" id="search" title="search" disabled="disabled" onkeyup="javascript:filter();">
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
		$.ajax({
		    url: 'http://localhost:3030/service/news/jsonp', //Sub the 0.0.0.0:8080 to the ip or name of where the webservice is located.
		    type: 'GET',
		    dataType: 'jsonp',
		    jsonp: 'callback',
		    success: function(data){
		        for(i in data) {
					$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');
				}
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
		$.ajax({
		    url: 'http://localhost:3030/service/news/jsonp', //Sub the 0.0.0.0:8080 to the ip or name of where the webservice is located.
		    type: 'GET',
		    dataType: 'jsonp',
		    jsonp: 'callback',
		    success: function(data){
		    	$('.tweeterContent').empty();
				var matchPos1 = -1;
				for(i in data) {
					var string1 = data[i].text.toUpperCase();
					var myRegExp = value.toUpperCase();
					if(myRegExp.length !=0){
						matchPos1 = string1.search(myRegExp);
						if(matchPos1!= -1){
							$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');	
						}
					}else{
						$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');
					}
				}
		    }
		});
	}
</script>