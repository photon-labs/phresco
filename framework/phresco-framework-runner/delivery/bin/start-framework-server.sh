export PHRESCO_HOME=$PWD/..
export JENKINS_HOME=$PHRESCO_HOME/workspace/tools/jenkins
export MAVEN_HOME=$PHRESCO_HOME/tools/maven
export M2_HOME=$MAVEN_HOME
export PATH=$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PATH
mvn clean validate
