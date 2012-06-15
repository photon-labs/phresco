/*
 * ###
 * Framework Web Archive
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

Event.observe(document, 'dom:loaded', function() {

    HumbleFinance.trackFormatter = function (obj) {
        
        var x = Math.floor(obj.x);
        var data = jsonData[x];
        var text = data.timeStamp + " Success: " + data.success + " Elapsed Time: " + data.time;
        
        return text;
    };
    
    HumbleFinance.yTickFormatter = function (n) {
        
        if (n == this.max) {
            return false;
        }
        
        return n;
    };
    
    HumbleFinance.xTickFormatter = function (n) { 
        
	n = parseInt(n);
	
        if (n == 0) {
            return false;
        }
        
        var date = jsonData[n].time;
       // date = date.split(' ');
       // date = date[2];
        
        return date; 
    };
    
    HumbleFinance.init('humblefinance', priceData, volumeData, summaryData);
    HumbleFinance.setFlags(flagData);
});
