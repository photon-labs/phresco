<?php /*
 * ###
 * Archetype - phresco-php-archetype
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
	global $baseurl;
    $self = $_SERVER['PHP_SELF'];
    $deploydir = explode("/", $self);

    $host = "http://".$_SERVER["HTTP_HOST"]."/".$deploydir[1]."/";
    
	$document = new DOMDocument();
	$document->load($host.'config/phresco-env-config.xml');
	
	$xmlDoc = $document->documentElement;
	$xml = simplexml_load_file($host.'config/phresco-env-config.xml');
	
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
//getConfigByName($currentEnv, $type, $name);

/*
function getConfigJson($envNode, $type, $name) {
	$nodes = $envNode->childNodes;

	foreach ($nodes AS $configNode) {

		//echo $configNode->nodeName;
		if ($configNode->nodeName == $type && $name != "") {
			$configName = $configNode->getAttribute("name");

			$xml = simplexml_load_file("confignew.xml"); 
			$result = $xml->xpath("//environment[@name='Dev']//database[@name='oracledb']");
			print_r($result);
		}
		else if ($configNode->nodeName == $type) {
		}
	}
}

getConfigByName($currentEnv, $type, $name);

foreach ($xmlDoc->childNodes AS $item)
  {
  print $item->nodeName . " = " . $item->nodeValue . "<br />";
  } */
?>