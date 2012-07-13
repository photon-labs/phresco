export PHRESCO_HOME=$PWD/..
export JENKINS_HOME=$PHRESCO_HOME/workspace/tools/jenkins
export MAVEN_HOME=$PHRESCO_HOME/tools/maven
export SONAR_HOME=$PHRESCO_HOME/workspace/tools/sonar-2.12
export CHECKER_HOME=$PHRESCO_HOME/workspace/tools/checker-267
export CHIEF_HOME=$PHRESCO_HOME/workspace/tools/chief
export M2_HOME=$MAVEN_HOME
export PATH=$CHIEF_HOME:$CHECKER_HOME:$SONAR_HOME:$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PATH
mvn clean validate