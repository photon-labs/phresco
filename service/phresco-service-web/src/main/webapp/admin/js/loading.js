/*
 * ###
 * Service Web Archive
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
 */
 
var maxprogress = 100;   // total to reach
var actualprogress = 0;  // current value
var itv = 0;  // id for setinterval
var load = 0;
var loadDots = 0;

function prog() {
    if (actualprogress >= maxprogress) {
        actualprogress = 0;
        $("#progress-bar").css("width", "0%");
        //return;
    }
    actualprogress += 3;
    $("#progress-bar").css("width", actualprogress + "%");
}

/*setInterval(prog, 100);
clearInterval(itv);*/