<?php 
/*
Author by {phresco} QA Automation Team
*/

define ('WAIT_FOR_NEXT_LINE',"2");
define ('WAIT_FOR_NEXT_LINES',"5");
define ('WAIT_FOR_NEXT_PAGES', "60000");
define ('WAIT_FOR_SEC',"2");


//Create New AC
define ('DRU_CREATE_AC_LINK', "//a[contains(text(),'Create new account')]");
define ('DRU_CREATE_AC_CLICK_UNAME', "//input[@id='edit-name']");
define ('DRU_CREATE_AC_CLICK_EMAIL', "//input[@id='edit-mail']");
define ('DRU_CREATE_AC_CLICK_SUBMIT', "//input[@id='edit-submit']");
define ('DRU_CREATE_AC_WAIT_FOR_TEXT',"Your password and further instructions have been sent to your e-mail address.");
//SignUp

define ('DRU_LOGIN_UNAME', "//input[@id='edit-name']");
define ('DRU_LOGIN_PASSWORD', "//input[@id='edit-pass']");
define ('DRU_LOGIN_BUTTON', "//input[@id='edit-submit']");
define ('DRU_LOGIN_CONFIRM_MSG',"Hello World!!!");

define ('DRU_LOGOUT_TEXT', "Log out");
define ('DRU_REGISTER_SUCESS_MSG', "//div[@class='messages status']");
define('DRU_GET_STRING', "Thank you for applying for an account. Your account is currently pending approval by the site administrator.In the meantime, a welcome message with further instructions has been sent to your e-mail address.");


?>