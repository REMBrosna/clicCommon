#! /bin/bash
mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install -DpomFile=pom.xml
mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file -Dfile=clibr-0.0.9-SNAPSHOT.jar -DgroupId=com.vcc.camelone -DartifactId=clibr -Dversion=0.0.9-SNAPSHOT -Dpackaging=jar
