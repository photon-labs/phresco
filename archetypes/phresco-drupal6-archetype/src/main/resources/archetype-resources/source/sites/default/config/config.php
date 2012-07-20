<?php /*
 * ###
 * Archetype - phresco-drupal6-archetype
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

function getConfigByName($currentEnv, $type, $name) {
	
   /*  if(DRUPAL_ROOT) {
		$cwd = DRUPAL_ROOT; 
	} else { 
		$cwd = getcwd(); 
	}  */
	$cwd = getcwd();
	$fileContents = file_get_contents($cwd."/sites/default/config/phresco-env-config.xml");
	$file = getOriginalString($fileContents);
	
	$document = new DOMDocument();
	$document->loadXML($file);
	$xmlDoc = $document->documentElement;
	$xml = simplexml_load_string($file);
	foreach ($xmlDoc->childNodes AS $envNode) {
		if ($envNode->nodeName == "environment") {
			$env = $envNode->getAttribute("name");
			$envDefault = $envNode->getAttribute("default");
			
			if ($currentEnv != "") {
				if ($currentEnv == $env) {
					if($name != "")
						$result = $xml->xpath("//environment[@name='". $env . "']//". $type ."[@name='". $name ."']");
					else
						$result = $xml->xpath("//environment[@name='". $env . "']//". $type ." ");
					return $result;
				}
			} else if ($envDefault == "true") {
					if($name != "")
						$result = $xml->xpath("//environment[@name='". $env . "']//". $type ."[@name='". $name ."']");
					else
						$result = $xml->xpath("//environment[@name='". $env . "']//". $type ." ");
				return $result;
			}
			
		}
	}
}

function getOriginalString($enc_string){
	$cipher = "rijndael-128";
	$mode = "cbc";
	$secret_key = "D4:6E:AC:3F:F0:BE";
	$iv = "fedcba9876543210";

	// Make sure the key length should be 16 bytes
	$key_len = strlen($secret_key);
	if($key_len < 16 ){
		$addS = 16 - $key_len;
		for($i =0 ;$i < $addS; $i++){
			$secret_key.=" ";
		}
	} else {
		$secret_key = substr($secret_key, 0, 16);
	}

	$td = mcrypt_module_open($cipher, "", $mode, $iv);
	mcrypt_generic_init($td, $secret_key, $iv);
	$decrypted_text = mdecrypt_generic($td, hex2bin($enc_string));
	mcrypt_generic_deinit($td);
	mcrypt_module_close($td);
	return trim($decrypted_text);
}


function hex2bin($str) {
    $bin = "";
    $i = 0;
    do {
        $bin .= chr(hexdec($str{$i}.$str{($i + 1)}));
        $i += 2;
    } while ($i < strlen($str));
    return $bin;
}
?>