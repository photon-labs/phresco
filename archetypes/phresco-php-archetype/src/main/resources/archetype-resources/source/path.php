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
	error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));

	$rootDir = __dir__;
	$arr1 = explode('\\', $rootDir);
	$arrayCount = sizeof($arr1) - 1;
	$count = 0;
	$contextRoot = "";

	for ($i = $arrayCount ; $i >= 0; $i--) {
		if ($count == 0) {
			$contextRoot = $arr1[$i];
		} else {
			break;
		}
		$count++;
	}
	$foldername = $contextRoot;

	// Constructing url
	$url = (!empty($_SERVER['HTTPS'])) ? "https://".$_SERVER['SERVER_NAME'] . ":" . $_SERVER['SERVER_PORT'] : "http://".$_SERVER['SERVER_NAME'] . ":" . $_SERVER['SERVER_PORT'];
	$serverPath = $url. "/" . $foldername;

	$properties = parse_ini_file('config.ini', true);
	defined("LIBRARY_PATH")
		or define("LIBRARY_PATH", '../library');

	session_start();

	$doc_root = $_SERVER["DOCUMENT_ROOT"];
	$last = $doc_root[strlen($doc_root)-1];
	$documnet_root = ($last == '/')? $doc_root : $doc_root."/";
	$baseurl = $documnet_root.$foldername."/";
	$_SESSION['baseurl'] = $baseurl;
	//echo $baseurl;
	ini_set("error_reporting", "true");
	error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
	$host=$_SERVER['HTTP_HOST'];

	$admin_image = $serverPath ."/public_html/images/blogtheme/";
	$admin_workimage = $serverPath."/public_html/images/blog/";

	$home_path = $serverPath ."/public_html/blog/";
	$js_path = $serverPath ."/public_html/js/";
	$admin_index = $serverPath ."/resources/templates/blog/admin/";
	$csspath = $serverPath ."/public_html/css/blogtheme/";

?>
