FROM jboss/wildfly:latest
ADD target/dmit2015-security-backendapp-demo-start.war /opt/jboss/wildfly/standalone/deployments/

USER root
RUN ln -s -f /usr/share/zoneinfo/MST /etc/localtime
