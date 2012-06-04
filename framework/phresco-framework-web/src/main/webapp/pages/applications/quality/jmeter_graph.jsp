<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.photon.phresco.commons.FrameworkConstants"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="/phresco/js/humble/finance.css" type="text/css" />

<script type="text/javascript" src="/phresco/js/humble/prototype.js"></script>
<script type="text/javascript" src="/phresco/js/humble/scriptaculous.js"></script>
<!--[if IE]>
<script type="text/javascript" src="/phresco/js/humble/excanvas.js"></script>
<script type="text/javascript" src="/phresco/js/humble/base64.js"></script>
<![endif]-->
<script type="text/javascript" src="/phresco/js/humble/canvas2image.js"></script>
<script type="text/javascript" src="/phresco/js/humble/canvastext.js"></script>
<script type="text/javascript" src="/phresco/js/humble/flotr.js"></script>
<script type="text/javascript" src="/phresco/js/humble/HumbleFinance.js"></script>
<%= session.getAttribute(FrameworkConstants.SESSION_GRAPH_SCRIPT) %>
<!--  <script type="text/javascript" src="/phresco/js/humble/data-small.js"></script>-->
<script type="text/javascript" src="/phresco/js/humble/jmeter-graph.js"></script>

<div id="humblefinance" style="position: relative; width: 380px;"></div>
