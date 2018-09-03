# Base java image
FROM openjdk:alpine

ENV GLIBC_VERSION=2.25-r0

# Install required packages
RUN apk add --update --no-cache wget mysql-client ca-certificates bash java-snappy python py-pip krb5 sudo coreutils && \
    update-ca-certificates

# Install glibc
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://raw.githubusercontent.com/sgerrand/alpine-pkg-glibc/master/sgerrand.rsa.pub && \
    wget -q https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-$GLIBC_VERSION.apk && \
    apk add glibc-$GLIBC_VERSION.apk && \
    rm glibc-$GLIBC_VERSION.apk

RUN mkdir -p /opt/kylo/kylo-services
ADD dist/kylo-services/lib  /opt/kylo/kylo-services/lib
ADD dist/kylo-services/plugin /opt/kylo/kylo-services/plugin
RUN rm -f /opt/kylo/kylo-services/lib/jetty*
RUN rm -f /opt/kylo/kylo-services/lib/servlet-api*
ADD dist/lib /opt/kylo/lib
ADD dist/bin /opt/kylo/bin

#download Impala JDBC driver
RUN wget -q https://downloads.cloudera.com/connectors/impala_jdbc_2.5.42.1062.zip
RUN unzip impala_jdbc_2.5.42.1062.zip
RUN unzip ClouderaImpalaJDBC-2.5.42.1062/ClouderaImpalaJDBC4_2.5.42.zip

RUN cp ClouderaImpalaJDBC4_2.5.42/ImpalaJDBC4.jar /opt/kylo/kylo-services/plugin && \
    cp ClouderaImpalaJDBC4_2.5.42/TCLIServiceClient.jar /opt/kylo/kylo-services/plugin && \
    cp ClouderaImpalaJDBC4_2.5.42/ql.jar /opt/kylo/kylo-services/plugin

RUN mv ClouderaImpalaJDBC4_2.5.42/ImpalaJDBC4.jar /opt/kylo/kylo-services/lib && \
    mv ClouderaImpalaJDBC4_2.5.42/TCLIServiceClient.jar /opt/kylo/kylo-services/lib && \
    mv ClouderaImpalaJDBC4_2.5.42/ql.jar /opt/kylo/kylo-services/lib

#Create kylo user and groups
RUN adduser -S -s /bin/bash kylo
RUN addgroup kylo
RUN adduser kylo kylo

#Grant ownership to kylo over kylo :)
RUN chown -R kylo:kylo /opt/kylo

ENV SPARK_HOME=/opt/cloudera/parcels/CDH-5.12.2-1.cdh5.12.2.p0.4/lib/spark
EXPOSE 8420
# RUN kylo-service
CMD ["java", "-cp", "/opt/kylo/kylo-services/conf:/opt/kylo/kylo-services/lib/*:/opt/kylo/kylo-services/lib/nifi-v1.2/*:/opt/kylo/kylo-services/plugin/*", "com.thinkbiganalytics.server.KyloServerApplication"]