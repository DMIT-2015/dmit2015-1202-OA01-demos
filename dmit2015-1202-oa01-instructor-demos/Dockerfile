FROM openliberty/open-liberty:kernel-java11-openj9-ubi

COPY --chown=1001:0  target/dmit2015-1202-oa01-instructor-demos.war /config/dropins/
COPY --chown=1001:0  server.xml /config

RUN configure.sh