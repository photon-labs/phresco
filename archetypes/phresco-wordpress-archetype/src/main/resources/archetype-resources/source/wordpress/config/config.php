<?php

function getConfigByName($currentEnv, $type, $name) {
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
				//print "If " . $envDefault;
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

?>