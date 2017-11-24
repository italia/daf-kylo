# Base java image
FROM ubuntu:16.04

# Set environment
ENV NIFI_VERSION=1.3.0
ENV GLIBC_VERSION=2.25-r0

## COPY hadoop hadoop

# Install required packages
RUN apt-get --update --no-cache ca-certificates wget bash java-snappy sudo && \
    update-ca-certificates  \

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -yq --no-install-recommends \
    default-jdk \
    vim \
    krb5-user \
    sasl2-bin libsasl2-2 libsasl2-modules libsasl2-dev libldap2-dev \
    python3 python3-dev python3-pip python3-requests-kerberos build-essential && \
    pip3 install --upgrade pip && \
    pip3 install setuptools && \
    pip3 install thrift_sasl sasl impyla hdfs polling && \

# Download nifi
RUN wget -qO- https://archive.apache.org/dist/nifi/$NIFI_VERSION/nifi-$NIFI_VERSION-bin.tar.gz | tar -xz && \
    mv nifi-$NIFI_VERSION /usr && mv /usr/nifi-$NIFI_VERSION /usr/nifi



# Install glibc
RUN apt-get install libc6

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

#Create default users and groups
RUN adduser -S -s /bin/bash nifi
RUN adduser -S -s /bin/bash activemq
RUN addgroup nifi
RUN addgroup activemq
RUN adduser nifi nifi
RUN adduser activemq activemq

RUN mkdir -p /usr/nifi/activemq
RUN mkdir -p /usr/nifi/drivers

RUN wget http://central.maven.org/maven2/org/apache/activemq/activemq-client/5.15.0/activemq-client-5.15.0.jar -P /usr/nifi/activemq/
RUN wget http://central.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/2.1.0/mariadb-java-client-2.1.0.jar -P /usr/nifi/drivers/
# Expose default nifi port
EXPOSE 8080
USER nifi
# RUN nifi
CMD ["/usr/nifi/bin/nifi.sh", "run"]
