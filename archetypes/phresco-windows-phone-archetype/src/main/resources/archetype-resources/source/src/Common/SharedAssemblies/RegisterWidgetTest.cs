/*global require */
require(
    ['src/UseYUI!node,widget', 'src/UseYUI!EShopAPI', 'src/UseYUI!BaseWidget', 'src/UseYUI!PhrescoWidget','src/UseYUI!RegistrationWidget', 'src/UseYUI!RegistrationSuccessWidget'], 
	function (Y, eshopAPI, BaseWidget, phrescoWidget, registrationWidget , registerSuccessWidget ) {
        module("RegistrationWidget.js;RegisterWidget");
        asyncTest("RegisterWidget success widget unit test case.", function() {

			var output1, output2;
			
			var registerNode = Y.Node.create('<div></div>');
			var eshopAPI = new Y.Phresco.EShopAPI({"context":"eshop", "host":"172.16.17.180" ,"port":"2020", "protocol":"http"});
            // instantiate NavigationWidget with the HTML
            var registrationWidget = new Y.Phresco.RegistrationWidget({
                // place holder can be decided by specifying the attribute
                targetNode : registerNode,
                apiReference : eshopAPI
           });
           var registerSuccessWidget = new Y.Phresco.RegistrationSuccessWidget({
                // place holder can be decided by specifying the attribute
                targetNode : registerNode,
                apiReference : eshopAPI
           });
            
			 var register = {};
                register.firstName = 'na44reen';
                register.lastName = 'ewe44e';
                register.email = 'aafggdfdfdibbbfdfda@gmail.com';
                register.password = 'sathisheere';
                register.phoneNumber = '5522545455555';
                var registerData = {};
                registerData.register = register;
                
                
		        eshopAPI.doRegister(registerSuccessWidget, registerData, registerSuccessWidget , function(response){                
                         output1 = response;
                });
			    output2 = "Success";
            setTimeout(function(){
                start();
                ok(true, "always fine");
                equal(output1, output2, "Expected response set in Register-widget");
            }, 1000);
        });  
		
       asyncTest("RegisterWidget failure widget unit test case.", function() {

			var output1, output2;
			
			var registerNode = Y.Node.create('<div></div>');
			var eshopAPI = new Y.Phresco.EShopAPI({"context":"eshop", "host":"172.16.17.180" ,"port":"2020", "protocol":"http"});
            // instantiate NavigationWidget with the HTML
            var registrationWidget = new Y.Phresco.RegistrationWidget({
                // place holder can be decided by specifying the attribute
                targetNode : registerNode,
                apiReference : eshopAPI
           });
           var registerSuccessWidget = new Y.Phresco.RegistrationSuccessWidget({
                // place holder can be decided by specifying the attribute
                targetNode : registerNode,
                apiReference : eshopAPI
           });
            
			 var register = {};
                register.firstName = 'naren';
                register.lastName = 'eee';
                register.email = 'sathish@gmail.com';
                register.password = 'sathisheee';
                register.phoneNumber = '55225555';
                var registerData = {};
                registerData.register = register;
                
                output1;
		        eshopAPI.doRegister(registerSuccessWidget, registerData, registerSuccessWidget , function(response){
                         output1 = response;
                });
			 
			    output2 = "Success";
            
            setTimeout(function(){
                start();
                ok(true, "always fine");
                notEqual(output1, output2, "Expected response set in login-widget");
            }, 1000);
        }); 
		
           
});