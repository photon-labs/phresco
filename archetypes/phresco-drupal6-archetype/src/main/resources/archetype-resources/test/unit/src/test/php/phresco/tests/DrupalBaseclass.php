<?php
/*	

Author by {phresco} QA Automation Team	

*/

require_once 'PHPUnit/Framework.php';

	if ( !isset( $_SERVER['REMOTE_ADDR'] ) ) {
	  $_SERVER['REMOTE_ADDR'] = 'localhost';
	}

	if ( !isset( $GLOBALS['language'] ) ) {
	  $GLOBALS['language']= 'en';
	}
	
	if ( !isset($_SERVER['HTTP_HOST'] ) ) {
	  $_SERVER['HTTP_HOST'] = 'localhost';
	}

	$cwd = explode("test",getcwd());
	$fileContents = file_get_contents($cwd[0]."source/sites/default/config/phresco-env-config.xml");
	$file = getDecryptedString($fileContents);
	$document = new DOMDocument();
	$document->loadXML($file);
	$xmlDoc = $document->documentElement;
	$config = $xmlDoc->getElementsByTagName("Server");

	foreach( $config as $Server ){
		$deployDirTag = $Server->getElementsByTagName("deploy_dir");
		$deploy_dir = $deployDirTag->item(0)->nodeValue."/";

		$contextTag = $Server->getElementsByTagName("context");
		$context = $contextTag->item(0)->nodeValue;
		$docRoot = $deploy_dir.$context;
	}



	function getDecryptedString($enc_string){
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
		$decrypted_text = mdecrypt_generic($td, hexadecimalToBinary($enc_string));
		mcrypt_generic_deinit($td);
		mcrypt_module_close($td);
		return trim($decrypted_text);
	}


function hexadecimalToBinary($str) {
    $bin = "";
    $i = 0;
    do {
        $bin .= chr(hexdec($str{$i}.$str{($i + 1)}));
        $i += 2;
    } while ($i < strlen($str));
    return $bin;
}

define('DRUPAL_ROOT', $docRoot);
$cwd = getcwd(); 
class DrupalBaseclass extends PHPUnit_Framework_TestCase {
	function connect(){
		chdir(DRUPAL_ROOT);
		require_once './includes/bootstrap.inc';
		drupal_bootstrap(DRUPAL_BOOTSTRAP_FULL);
		set_error_handler('drupal_error_handler');
	}
	
  }
  
?>