<?php

/*
  Copyright 2011 3e software house & interactive agency

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

require_once 'WebDriverBase.php';
require_once 'WebElement.php';
require_once 'WebDriverException.php';
require_once 'LocatorStrategy.php';

class WebDriver extends WebDriverBase {

    function __construct($host, $port) {
        parent::__construct("http://" . $host . ":" . $port . "/wd/hub");
    }

    /**
     * Connects to Selenium server.
     * @param $browserName The name of the browser being used; should be one of {chrome|firefox|htmlunit|internet explorer|iphone}. 
     * @param $version 	The browser version, or the empty string if unknown. 
     * @param $caps  array with capabilities see: http://code.google.com/p/selenium/wiki/JsonWireProtocol#/session
    */
    public function connect($browserName="firefox", $version="", $caps=array()) {
        $request = $this->requestURL . "/session";
        $session = $this->curlInit($request);
    $allCaps = 	
        array_merge(
              array(
                  'javascriptEnabled' => true,
                  'nativeEvents'=>false,
                 ),
            $caps,
              array(
                  'browserName'=>$browserName,
                  'version'=>$version,
                 )
        );
    $params = array( 'desiredCapabilities' =>	$allCaps );
    $postargs = json_encode($params);
        $this->preparePOST($session, $postargs);
        curl_setopt($session, CURLOPT_HEADER, true);
        $response = curl_exec($session);
        $header = curl_getinfo($session);
        $this->requestURL = $header['url'];
    }

     /**
     * Delete the session.
     */
    public function close() {
        $request = $this->requestURL;
        $session = $this->curlInit($request);
        $this->prepareDELETE($session);
        $response = curl_exec($session);
        $this->curlClose();
    }
    
     /**
     * Refresh the current page.
     */
    public function refresh() {

        $request = $this->requestURL . "/refresh";
        $session = $this->curlInit($request);
        $this->preparePOST($session, null);
        curl_exec($session);
    }
    
     /**
     * Navigate forwards in the browser history, if possible.
     */
    public function forward() {

        $request = $this->requestURL . "/forward";
        $session = $this->curlInit($request);
        $this->preparePOST($session, null);
        curl_exec($session);
    }

     /**
     * Navigate backwards in the browser history, if possible.
     */
    public function back() {

        $request = $this->requestURL . "/back";
        $session = $this->curlInit($request);
        $this->preparePOST($session, null);
        curl_exec($session);
    }
	
	/**
     * Get the element on the page that currently has focus.
     * @return JSON object WebElement.
     */
    public function getActiveElement() {
        $response = $this->execute_rest_request_GET($this->requestURL . "/element/active");
        return $this->extractValueFromJsonResponse($response);
    }
    
     /**
     * Change focus to another frame on the page. If the frame ID is null, the server should switch to the page's default content.
     */
    public function focusFrame($frameId) {

        $request = $this->requestURL . "/frame";
        $session = $this->curlInit($request);
        $args = array('id' => $frameId);
        $this->preparePOST($session, json_encode($args));
        curl_exec($session);
		
    }

    /**
     * Navigate to a new URL
     * @param string $url The URL to navigate to.
     */
    public function get($url) {
        $request = $this->requestURL . "/url";
        $session = $this->curlInit($request);
        $args = array('url' => $url);
        $this->preparePOST($session, json_encode($args));
        $response = curl_exec($session);
    }

    /**
     * Get the current page title.
     * @return string The current URL.
     */
    public function getCurrentUrl() {
        $response = $this->execute_rest_request_GET($this->requestURL . "/url");
        return $this->extractValueFromJsonResponse($response);
    }

    /**
     * Get the current page title. 
     * @return string current page title
     */
    public function getTitle() {
        $response = $this->execute_rest_request_GET($this->requestURL . "/title");
        return $this->extractValueFromJsonResponse($response);
    }

    /**
     * Get the current page source.
     * @return string page source 
     */
    public function getPageSource() {
        $request = $this->requestURL . "/source";
        $response = $this->execute_rest_request_GET($request);
        return $this->extractValueFromJsonResponse($response);
    }

    /**
     * Get the current user input speed. The server should return one of {SLOW|MEDIUM|FAST}.
     * How these constants map to actual input speed is still browser specific and not covered by the wire protocol.
     * @return string {SLOW|MEDIUM|FAST}
     */
    public function getSpeed() {
        $request = $this->requestURL . "/speed";
        $response = $this->execute_rest_request_GET($request);
        return $this->extractValueFromJsonResponse($response);
    }

    public function setSpeed($speed) {
        $request = $this->requestURL . "/speed";
        $session = $this->curlInit($request);
        $args = array('speed' => $speed);
        $jsonData = json_encode($args);
        $this->preparePOST($session, $jsonData);
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }


    /**
    Change focus to another window. The window to change focus to may be specified 
    by its server assigned window handle, or by the value of its name attribute.
    */
    public function selectWindow($windowName) {
        $request = $this->requestURL . "/window";
        $session = $this->curlInit($request);
        $args = array('name' => $windowName);
        $jsonData = json_encode($args);
        $this->preparePOST($session, $jsonData);
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }

    /**
    Close the current window.
    */
    public function closeWindow() {
        $request = $this->requestURL . "/window";
        $session = $this->curlInit($request);
        $this->prepareDELETE($session);
        $response = curl_exec($session);
        $this->curlClose();
    }

    /**
     * Retrieve all cookies visible to the current page.
     * @return array array with all cookies
     */
    public function getAllCookies() {
        $response = $this->execute_rest_request_GET($this->requestURL . "/cookie");
        return $this->extractValueFromJsonResponse($response);
    }

    /**
    * Set a cookie. 	
    */
    public function setCookie($name, $value, $cookie_path='/', $domain='', $secure=false, $expiry='') {
        $request = $this->requestURL . "/cookie";
        $session = $this->curlInit($request);
    $cookie = array('name'=>$name, 'value'=>$value, 'secure'=>$secure);
    if (!empty($cookie_path)) $cookie['path']=$cookie_path;
    if (!empty($domain)) $cookie['domain']=$domain;
    if (!empty($expiry)) $cookie['expiry']=$exipry;
        $args = array('cookie' => $cookie );
        $jsonData = json_encode($args);
        $this->preparePOST($session, $jsonData);
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }


    /**
    Delete the cookie with the given name. This command should be a no-op if there is no such cookie visible to the current page.
    */
    public function deleteCookie($name) {
        $request = $this->requestURL . "/cookie/".$name;
        $session = $this->curlInit($request);
        $this->prepareDELETE($session);
        $response = curl_exec($session);
        $this->curlClose();
    }

    /**
    	Delete all cookies visible to the current page. 
    */
    public function deleteAllCookies($name) {
        $request = $this->requestURL . "/cookie";
        $session = $this->curlInit($request);
        $this->prepareDELETE($session);
        $response = curl_exec($session);
        $this->curlClose();
    }


    /**
     * Gets the text of the currently displayed JavaScript alert(), confirm(), or prompt() dialog.
     * @return string The text of the currently displayed alert.
     */
    public function getAlertText() {
        $response = $this->execute_rest_request_GET($this->requestURL . "/alert_text");
        return $this->extractValueFromJsonResponse($response);
    }

    /**
     * Sends keystrokes to a JavaScript prompt() dialog.
    */
    public function sendAlertText($text) {
        $request = $this->requestURL . "/alert_text";
        $session = $this->curlInit($request);
        $args = array('keysToSend' => $text);
        $jsonData = json_encode($args);
        $this->preparePOST($session, $jsonData);
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }
    
     /**
     * Get the current browser orientation. The server should return a valid orientation value as defined in ScreenOrientation: LANDSCAPE|PORTRAIT.
     * @return string The current browser orientation corresponding to a value defined in ScreenOrientation: LANDSCAPE|PORTRAIT.
     */
    public function getOrientation() {
        $response = $this->execute_rest_request_GET($this->requestURL . "/orientation");
        return $this->extractValueFromJsonResponse($response);
    }

    /**
     * Set the browser orientation. The orientation should be specified as defined in ScreenOrientation: LANDSCAPE|PORTRAIT.
    */
    public function setOrientation($orientation) {
        $request = $this->requestURL . "/orientation";
        $session = $this->curlInit($request);
        $args = array('orientation' => $orientation);
        $jsonData = json_encode($args);
        $this->preparePOST($session, $jsonData);
        curl_exec($session);
    }

    /**
     * Accepts the currently displayed alert dialog. Usually, this is equivalent to clicking on the 'OK' button in the dialog.     
    */
    public function acceptAlert() {
        $request = $this->requestURL . "/accept_alert";
        $session = $this->curlInit($request);
	$this->preparePOST($session, '');
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }

    /**
     *     Dismisses the currently displayed alert dialog. For confirm() and prompt() dialogs, 
     *	this is equivalent to clicking the 'Cancel' button. For alert() dialogs, this is equivalent to clicking the 'OK' button.
    */
    public function dismissAlert() {
        $request = $this->requestURL . "/dismiss_alert";
        $session = $this->curlInit($request);
	$this->preparePOST($session, '');
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }

    /**
      Inject a snippet of JavaScript into the page for execution in the context of the currently selected frame.
     * The executed script is assumed to be synchronous and the result of evaluating the script
     * is returned to the client.
     * @return Object result of evaluating the script is returned to the client.
     */
    public function execute($script, $script_args) {
        $request = $this->requestURL . "/execute";
        $session = $this->curlInit($request);
        $args = array('script' => $script, 'args' => $script_args);
        $jsonData = json_encode($args);
        $this->preparePOST($session, $jsonData);
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }

    /**
      Inject a snippet of JavaScript into the page for execution in the context of the currently selected frame.
     * The executed script is assumed to be synchronous and the result of evaluating the script
     * is returned to the client.
     * @return Object result of evaluating the script is returned to the client.
     */
    public function executeScript($script, $script_args) {
        $request = $this->requestURL . "/execute";
        $session = $this->curlInit($request);
        $args = array('script' => $script, 'args' => $script_args);
        $jsonData = json_encode($args);
        $this->preparePOST($session, $jsonData);
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }

    /**
      Inject a snippet of JavaScript into the page for execution
     * in the context of the currently selected frame. The executed script
     * is assumed to be asynchronous and must signal that is done by invoking
     * the provided callback, which is always provided as the final argument
     * to the function. The value to this callback will be returned to the client.
     * @return Object result of evaluating the script is returned to the client.
     */
    public function executeAsyncScript($script, $script_args) {
        $request = $this->requestURL . "/execute_async";
        $session = $this->curlInit($request);
        $args = array('script' => $script, 'args' => $script_args);
        $jsonData = json_encode($args);
        $this->preparePOST($session, $jsonData);
        $response = curl_exec($session);
        return $this->extractValueFromJsonResponse($response);
    }

    /**
     * Take a screenshot of the current page.
     * @return string The screenshot as a base64 encoded PNG.
     */
    public function getScreenshot() {
        $request = $this->requestURL . "/screenshot";
        $response = $this->execute_rest_request_GET($request);
        return $this->extractValueFromJsonResponse($response);
    }

    /**
     * Take a screenshot of the current page and saves it to png file.
     * @param $png_filename filename (with path) where file has to be saved
     * @return bool result of operation (false if failure)
     */
    public function getScreenshotAndSaveToFile($png_filename) {
        $img = $this->getScreenshot();
        $data = base64_decode($img);
        $success = file_put_contents($png_filename, $data);
    }

}

?>