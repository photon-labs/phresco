export PHRESCO_HOME=$PWD/..
export MAVEN_HOME=$PHRESCO_HOME/tools/maven
export SONAR_HOME=$PHRESCO_HOME/workspace/tools/sonar-2.12
export PATH=$SONAR_HOME:$MAVEN_HOME/bin:$PHRESCO_HOME/bin:$PATH
mvn $1 $2 $3 $4 $5 $6 $7 $8 $9
