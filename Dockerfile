FROM tomcat:9.0.80-jdk17 as maven_builder
WORKDIR $HOME
ADD pom.xml $HOME
COPY ./target/demo112-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
