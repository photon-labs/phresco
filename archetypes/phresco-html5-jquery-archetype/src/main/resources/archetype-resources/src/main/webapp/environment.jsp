<%--
  ###
  Archetype - phresco-html5-jquery-archetype
  
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
<%! 
	class Environment {
		String environment;
		public String getEnvironment() {
			return environment;
		}
		public void setEnvironment(String environment) {
			this.environment = environment;
		}
	}
%>
<%
	String currentEnv = System.getProperty("SERVER_ENVIRONMENT");
	com.google.gson.Gson gson = new com.google.gson.Gson();
	Environment env = new Environment();
	env.setEnvironment(currentEnv);
	String json = gson.toJson(env, Environment.class);
	response.setContentType("application/json");
	out.print(json);
%>