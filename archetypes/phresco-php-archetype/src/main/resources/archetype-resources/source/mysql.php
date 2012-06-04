<?php
include "path.php";
include('config/config.php');
error_reporting(E_ALL ^ (E_NOTICE | E_WARNING));
class MyClass
{
    function connect() {
		$currentEnv = getenv('SERVER_ENVIRONMENT');
		$type = "Database";
		$name = "";
		$properties = getConfigByName($currentEnv, $type, $name);
		if($properties !=""){
			$DB_USER =  $properties[0]->username;
			$DB_PASSWORD = $properties[0]->password;
			$DB_HOST = $properties[0]->host;
			$DB_NAME = $properties[0]->dbname;
			$DB_TYPE = $properties[0]->type;
			$dbc = mysql_connect ($DB_HOST, $DB_USER, $DB_PASSWORD) or $error = mysql_error();
			mysql_select_db($DB_NAME) or $error = mysql_error();
			return $dbc;
		}
	}
	function insert() {
		$conn = $this->connect();
		if($conn !=""){
			$result = mysql_query("SELECT name from helloworld");
			$num_rows = mysql_num_rows($result);
			if($num_rows == 0 || $num_rows == ""){
				$result = mysql_query("INSERT INTO helloworld(NAME) VALUES ('Hello World')");
			}
		}
	}
	function select() {
	$conn = $this->connect();
		if($conn !=""){
			$result = mysql_query("SELECT name from helloworld");
			while ($row = mysql_fetch_assoc($result)) {
				echo $text = $row['name'];
			}
		}
	}
}
$class = new MyClass();
$class->connect();
$class->insert();


?>
