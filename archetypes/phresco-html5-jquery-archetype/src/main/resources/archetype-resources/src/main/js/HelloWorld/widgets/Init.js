/*global $, require */

// When the document is ready:
$().ready(function() {

	require( [ "HelloWorld/widgets/HelloWorldWidget" ], function( HelloWorldWidget ) {
		var helloWorldWidget; 

		helloWorldWidget = new HelloWorldWidget();
		helloWorldWidget.setMainContent();
	});
});
