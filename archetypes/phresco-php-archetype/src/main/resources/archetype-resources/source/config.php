<?php
include "path.php";
include('config/config.php');

error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
function connectDatabase(){
	$currentEnv = getenv('SERVER_ENVIRONMENT');
	$type = "";
	$name = "";
	$properties = getConfigByName($currentEnv, $type, $name);

	//$properties = parse_ini_file('config.ini', true);

		$DB_USER =  $properties[0]->username;
		$DB_PASSWORD = $properties[0]->password;
		$DB_HOST = $properties[0]->host;
		$DB_NAME = $properties[0]->name;
		$dbc = @mysql_connect ($DB_HOST, $DB_USER, $DB_PASSWORD) or $error = mysql_error();
		@mysql_select_db($DB_NAME) or $error = mysql_error();

		if(strlen($error) > 10){
			echo "<!--$error.-->";
			exit();
			die();
		}
		return $dbc;
}

function disconnectDatabase($dbc){
	mysql_close($dbc);
}


?>