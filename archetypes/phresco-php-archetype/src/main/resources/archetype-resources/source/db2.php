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
			$conn = db2_connect($DB_NAME, $DB_USER, $DB_PASSWORD);
			return $conn;
		}else{ 
			$conn = "";  
			return $conn;
		}
	}
	function insert() {
		
		$conn = $this->connect();
		if($conn == true){
			$sql = "SELECT name from helloworld";
			$stmt = db2_exec($conn, $sql);
	
			$num_rows = db2_num_rows($stmt);;
			if($num_rows == 0 || $num_rows == ""){
				$sql = "INSERT INTO helloworld(NAME) VALUES ('Hello World')";
				$stmt = db2_exec($conn, $sql);
			}
		}
	}
	function select() {
		$conn = $this->connect();
		if($conn == true){
			$sql = "SELECT name from helloworld";
			$result = db2_exec($conn, $sql);
			while ($row = db2_fetch_array($result)) {
				echo $text = $row[0];
			}
		}
	}

}
$class = new MyClass();
$class->connect();
$class->insert();
?>


