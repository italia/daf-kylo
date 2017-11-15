# Base java image
FROM openjdk:alpine

# Set environment
ENV NIFI_VERSION=1.3.0
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

#Create default users and groups
RUN adduser -S -s /bin/bash nifi
RUN adduser -S -s /bin/bash kylo
RUN adduser -S -s /bin/bash activemq
RUN addgroup kylo
RUN addgroup nifi
RUN addgroup activemq
RUN adduser nifi nifi
RUN adduser kylo kylo
RUN adduser activemq activemq

RUN mkdir -p /usr/nifi/activemq
RUN mkdir -p /usr/nifi/drivers

RUN wget http://central.maven.org/maven2/org/apache/activemq/activemq-client/5.15.0/activemq-client-5.15.0.jar -P /usr/nifi/activemq/
RUN wget http://central.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/2.1.0/mariadb-java-client-2.1.0.jar -P /usr/nifi/drivers/
# Expose default nifi port
EXPOSE 8080

# RUN nifi
CMD ["/usr/nifi/bin/nifi.sh", "run"]
