<!doctype html>
<html lang="en">
    <div class="helloworld-widget"></div>
    <script src="lib/jquery/jquery.js"></script>
    <script type="text/javascript" src="js/org/codehaus/mojo/almond/0.0.2-alpha-1/almond-0.0.2-alpha-1.js"></script>
    <script type="text/javascript" src="js/jslibraries/files/jslib_jquery-amd/1.7.1-alpha-1/jslib_jquery-amd-1.7.1-alpha-1.js"></script>
   <script type="text/javascript" src="js/HelloWorld/widgets/HelloWorldWidget.js"></script>
		<%
			String currentEnv = System.getProperty("SERVER_ENVIRONMENT");
			String path = getServletContext().getRealPath("/WEB-INF/resources/phresco-env-config.xml");
			File file = new File(path);
			ConfigReader reader = new ConfigReader(file);
			String configJson = reader.getConfigAsJSON(currentEnv, "WebService");
		%>
		
		<script type="text/javascript">
			var configJsonData = $.parseJSON('<%= configJson%>'); 
		</script>
    <script type="text/javascript" src="js/HelloWorld/widgets/Init.js"></script>
</html>
