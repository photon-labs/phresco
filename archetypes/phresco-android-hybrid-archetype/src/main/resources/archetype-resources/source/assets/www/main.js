var showMessageBox = function() {  
  
                             navigator.notification.alert("Hello World of PhoneGap....");  
  
                        }  
  
                        function init(){  
  
                             document.addEventListener("deviceready", showMessageBox, true);                 
  
                        }  
  