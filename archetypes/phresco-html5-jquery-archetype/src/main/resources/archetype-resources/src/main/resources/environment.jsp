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