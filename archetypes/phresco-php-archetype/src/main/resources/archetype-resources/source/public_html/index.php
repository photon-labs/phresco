<?php
if(strtolower($_GET['DBTYPE'])=="mysql"){
include "../mysql.php";
}
if(strtolower($_GET['DBTYPE'])=="db2"){
include "../db2.php";
}


?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Landing Page</title>
<style>
.sample
{
	margin:0 auto;
	width:80%;
	text-align:center;
	font-weight:bold;
	font-size:30px;
	padding:30% 0 35% 0;
	background-color:#480D0F;
	color:#FFFFFF;
}

html, body {
	height:100%;
	margin:0;
	padding:0;
}
</style>
</head>
<body>
<div class="sample"><?php if($_GET['DBTYPE'] !=""){ $class->select(); } else { echo "Hello World"; } ?></div>
</body>
</html>

