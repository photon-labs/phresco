<?php
/*	

Author by {phresco} QA Automation Team	

*/

require_once 'PHPUnit/Framework.php';
require_once 'DrupalBaseclass.php';


class Node extends DrupalBaseclass {
		
	function testNodeType(){
		$this->connect();
		$nodeType = node_load(array("nid" => 1));
		$this->assertEquals("story", $nodeType->type);
	}
		
	function testNodeTitle(){
		$node = node_load(1); // node_load(17) problem in data
		$title = node_page_title($node);
		$this->assertEquals('Hello World', $title);
	}
}
?>