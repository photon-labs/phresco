<?php

/*	Author by {phresco} QA Automation Team	*/

class BaseScreen {
	
	
	public function check_username($user_name) {
	
		if (preg_match( '/[a-z0-9]+/', $user_name)){
		
            return true;
				
        }
	}
	
	public function check_dateformat($Date,$format = 'Y-m-d H:i:s') {
	
		if(preg_match('#([0-9]{1,4})-([0-9]{1,2})-([0-9]{1,2}) ([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})#',$Date)){
		
            return true;
        }
    }
	
	public function check_email($local,$domain){
	
		if (preg_match( '/^[a-zA-Z0-9!#$%&\'*+\/=?^_`{|}~\.-]+$/', $local ) ) {
	
			return true;
		}

		if ( preg_match( '/\.{2,}/', $domain ) ) {
	
			return true;
		}
	}
}
?>
