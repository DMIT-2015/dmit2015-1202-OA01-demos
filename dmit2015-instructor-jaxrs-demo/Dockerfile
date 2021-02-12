# Wildfly settings
FROM jboss/wildfly:latest
ADD target/dmit2015-instructor-jaxrs-demo.war /opt/jboss/wildfly/standalone/deployments/

USER root
RUN ln -s -f /usr/share/zoneinfo/MST /etc/localtime

