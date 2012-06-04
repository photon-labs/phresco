set REPO_URL=http://localhost:8080/nexus-webapp-1.9.2.2/content/repositories/repository/
set username=admin
set password=admin123

call mvn install:install-file -DrepositoryId=repository -DartifactId=repository -Durl=http://localhost:8080/nexus-webapp-1.9.2.2/content/repositories/repository/ -Dfile=../tools/pom.xml -DgroupId=com.photon.phresco.repository -Dversion=1.0-SNAPSHOT -Dpackaging=pom

call mvn deploy:deploy-file -DgroupId=com.photon.phresco.plugins -DartifactId=drupal-maven-plugin -Dversion=1.0 -Dpackaging=maven-plugin -Dfile=../../plugins/drupal-maven-plugin/pom.xml -DrepositoryId=repository -Durl=%REPO_URL%

call mvn deploy:deploy-file -DgroupId=com.photon.phresco.plugins -DartifactId=php-maven-plugin -Dversion=1.0 -Dpackaging=maven-plugin -Dfile=../../plugins/php-maven-plugin/pom.xml -DrepositoryId=repository -Durl=%REPO_URL%

call mvn deploy:deploy-file -DgroupId=archetypes -DartifactId=phresco-sharepoint-archetype -Dversion=1.0 -Dpackaging=jar -Dfile=../../archetypes/phresco-sharepoint-archetype/pom.xml -DrepositoryId=repository -Durl=%REPO_URL%

call mvn deploy:deploy-file -DgroupId=archetypes -DartifactId=phresco-drupal7-archetype -Dversion=1.0 -Dpackaging=jar -Dfile=../../archetypes/phresco-drupal7-archetype/pom.xml -DrepositoryId=repository -Durl=%REPO_URL%

call mvn deploy:deploy-file -DgroupId=archetypes -DartifactId=phresco-iphone-archetype -Dversion=1.0 -Dpackaging=jar -Dfile=../../archetypes/phresco-iphone-archetype/pom.xml -DrepositoryId=repository -Durl=%REPO_URL%

call mvn deploy:deploy-file -DgroupId=archetypes -DartifactId=phresco-javawebservice-archetype -Dversion=1.0 -Dpackaging=jar -Dfile=../../archetypes/phresco-javawebservice-archetype/pom.xml -DrepositoryId=%REPO_URL%

call mvn deploy:deploy-file -DgroupId=archetypes -DartifactId=phresco-nodejs-archetype -Dversion=1.0 -Dpackaging=jar -Dfile=../../archetypes/phresco-nodejs-archetype/pom.xml -DrepositoryId=repository -Durl=%REPO_URL%

call mvn deploy:deploy-file -DgroupId=archetypes -DartifactId=phresco-php-archetype -Dversion=1.0 -Dpackaging=jar -Dfile=../../archetypes/phresco-php-archetype/pom.xml -DrepositoryId=repository -Durl=%REPO_URL%
