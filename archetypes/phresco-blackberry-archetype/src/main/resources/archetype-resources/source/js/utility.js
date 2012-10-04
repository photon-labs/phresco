var json = {};
function configParse(jsonStr, currentEnv, type, name) {
	this.jsonStr = jsonStr;
	this.currentEnv = currentEnv;
	this.type = type;
	this.name = name;
	this.parseString = parseString;
}

 function parseString() {
	//alert("this.name = " + this.name);
	var jsonStrObj = jQuery.parseJSON(this.jsonStr);
	var envLength = jsonStrObj.length;
	if (envLength != 'undefined' && envLength > 1) {	
		for (i = 0; i < envLength; i++) {
			var environment = jsonStrObj[i];
			var env = environment["@name"];
			var envDefault = environment["@default"];
			if (this.currentEnv != undefined) {
				if (this.currentEnv == env) {
					json = getConfigJson(environment, this.type, this.name);
				}
			} else if (envDefault == "true") {
				json = getConfigJson(environment, this.type, this.name);
			}

		}
	} else {
		var environment = jsonStrObj[0];
		var env = environment["@name"];
        var envDefault = environment["@default"];
		json = getConfigJson(environment, this.type, this.name);
	}
	return json;
}

function getConfigJson(environment, type, name) {
//	alert("getConfigJson: name = " + name);
	var json = {};
    var configurations = environment[type];
	if (configurations.length > 1) {
        for (var i = 0; i < configurations.length; i++) {
            if (name != undefined) {
                var configName = configurations[i]["@name"];
				if (configName == name) {
                    json = configurations[i];
                }
            } else {
                json = configurations[0];
            }
        }
    } else {
        json = configurations;
    }
    return json;
}