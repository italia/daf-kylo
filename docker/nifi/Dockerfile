# Base java image
FROM openjdk:alpine

# Set environment
ENV NIFI_VERSION=1.6.0
ENV GLIBC_VERSION=2.25-r0

# Install required packages
RUN apk add --update --no-cache ca-certificates wget bash java-snappy && \
    update-ca-certificates

# Download nifi
RUN wget -qO- https://archive.apache.org/dist/nifi/$NIFI_VERSION/nifi-$NIFI_VERSION-bin.tar.gz | tar -xz && \
    mv nifi-$NIFI_VERSION /usr && mv /usr/nifi-$NIFI_VERSION /usr/nifi



# Install glibc
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://raw.githubusercontent.com/sgerrand/alpine-pkg-glibc/master/sgerrand.rsa.pub && \
    wget -q https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-$GLIBC_VERSION.apk && \
    apk add glibc-$GLIBC_VERSION.apk && \
    rm glibc-$GLIBC_VERSION.apk

RUN rm -r /usr/nifi/conf

# Link kylo jars

ADD dist/ /usr/nifi/kylo
RUN mkdir -p /usr/nifi/lib/app
RUN mkdir -p /usr/nifi/dump

RUN ln -f -s /usr/nifi/kylo/kylo-nifi-core-service-nar-*.nar /usr/nifi/lib/kylo-nifi-core-service-nar.nar
RUN ln -f -s /usr/nifi/kylo/kylo-nifi-standard-services-nar-*.nar /usr/nifi/lib/kylo-nifi-standard-services-nar.nar

RUN ln -f -s /usr/nifi/kylo/kylo-nifi-core-v1-nar-*.nar /usr/nifi/lib/kylo-nifi-core-nar.nar
RUN ln -f -s /usr/nifi/kylo/kylo-nifi-spark-v1-nar-*.nar /usr/nifi/lib/kylo-nifi-spark-nar.nar
RUN ln -f -s /usr/nifi/kylo/kylo-nifi-spark-service-v1-nar-*.nar /usr/nifi/lib/kylo-nifi-spark-service-nar.nar
RUN ln -f -s /usr/nifi/kylo/kylo-nifi-hadoop-v1-nar-*.nar /usr/nifi/lib/kylo-nifi-hadoop-nar.nar
RUN ln -f -s /usr/nifi/kylo/kylo-nifi-hadoop-service-v1-nar-*.nar /usr/nifi/lib/kylo-nifi-hadoop-service-nar.nar
RUN ln -f -s /usr/nifi/kylo/kylo-nifi-elasticsearch-v1-nar-*.nar /usr/nifi/lib/kylo-nifi-elasticsearch-nar.nar

# Create links for custom kylo provenance
RUN ln -f -s /usr/nifi/kylo/kylo-nifi-provenance-repo-v1.2-nar-*.nar /usr/nifi/lib/kylo-nifi-provenance-repo-nar.nar

# Create links for custom kylo-spark jars
RUN ln -f -s /usr/nifi/kylo/kylo-spark-validate-cleanse-v2-*-jar-with-dependencies.jar /usr/nifi/lib/app/kylo-spark-validate-cleanse-jar-with-dependencies.jar
RUN ln -f -s /usr/nifi/kylo/kylo-spark-job-profiler-v2-*-jar-with-dependencies.jar /usr/nifi/lib/app/kylo-spark-job-profiler-jar-with-dependencies.jar
RUN ln -f -s /usr/nifi/kylo/kylo-spark-interpreter-v2-*-jar-with-dependencies.jar /usr/nifi/lib/app/kylo-spark-interpreter-jar-with-dependencies.jar

# Create links for Teamdigitale custom processors
RUN ln -f -s /usr/nifi/kylo/daf/teamdigitale-daf-nifi-processors-*.nar /usr/nifi/lib/teamdigitale-daf-nifi-processors.nar



RUN mkdir -p /usr/nifi/activemq
RUN mkdir -p /usr/nifi/drivers
RUN mkdir -p /usr/nifi/drivers/impala
RUN mkdir -p /usr/nifi/conf.temp

RUN wget http://central.maven.org/maven2/org/apache/activemq/activemq-all/5.15.0/activemq-all-5.15.0.jar -P /usr/nifi/activemq/
RUN wget http://central.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/2.1.0/mariadb-java-client-2.1.0.jar -P /usr/nifi/drivers/

#download Impala JDBC driver
RUN wget -q https://downloads.cloudera.com/connectors/impala_jdbc_2.5.42.1062.zip
RUN unzip impala_jdbc_2.5.42.1062.zip
RUN unzip ClouderaImpalaJDBC-2.5.42.1062/ClouderaImpalaJDBC4_2.5.42.zip
RUN mv ClouderaImpalaJDBC4_2.5.42/* /usr/nifi/drivers/impala

# Expose default nifi port
EXPOSE 8080

RUN apk update
RUN apk add --no-cache krb5-libs krb5 krb5-dev sudo coreutils python cyrus-sasl alpine-sdk libsasl libffi-dev openssl-dev cyrus-sasl-dev && \
apk add --no-cache python3 python3-dev  && \
python3 -m ensurepip && \
rm -r /usr/lib/python*/ensurepip && \
pip3 install --upgrade pip setuptools thrift_sasl==0.2.1 sasl impyla hdfs polling requests_kerberos

ADD scripts/ /scripts
RUN chmod a+x /scripts/*

# Start nifi
CMD /scripts/setup.sh ; /usr/nifi/bin/nifi.sh run