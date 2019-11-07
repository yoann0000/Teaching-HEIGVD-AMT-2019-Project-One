FROM jboss/wildfly:15.0.0.Final

MAINTAINER Julien Rod <julien.rod@heig-vd.ch>

COPY data/modules/ /opt/jboss/wildfly/modules/system/layers/base/
COPY data/standalone.xml /opt/jboss/wildfly/standalone/configuration/

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug", "*:8787"]

