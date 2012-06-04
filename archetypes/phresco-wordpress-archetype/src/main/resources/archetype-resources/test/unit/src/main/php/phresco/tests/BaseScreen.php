<?php /*
 * ###
 * Archetype - phresco-wordpress-archetype
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */ ?>
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
