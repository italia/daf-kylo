# Base java image
FROM openjdk:alpine

ENV GLIBC_VERSION=2.25-r0
ENV SPARK_PROFILE 2.1
ENV SPARK_VERSION 2.2.0

ENV HADOOP_PROFILE 2.7


# Install required packages
RUN apk add --update --no-cache wget mysql-client ca-certificates bash java-snappy python coreutils py-pip && \
    update-ca-certificates && \
    apk add krb5-libs krb5 sudo coreutils python cyrus-sasl

# Install glibc
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://raw.githubusercontent.com/sgerrand/alpine-pkg-glibc/master/sgerrand.rsa.pub && \
    wget -q https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-$GLIBC_VERSION.apk && \
    apk add glibc-$GLIBC_VERSION.apk && \
    rm glibc-$GLIBC_VERSION.apk


# Add kylo spark shell jar
RUN mkdir -p /opt/kylo/kylo-spark/lib/app
ADD dist/kylo-services/lib/kylo-spark-shell-client-v2-0.8.3.3.jar /opt/kylo/kylo-spark/lib/app/kylo-spark-shell-client-v2-0.8.3.3.jar


# Add mariadb driver
RUN mkdir -p /opt/kylo/drivers
RUN wget http://central.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/2.1.0/mariadb-java-client-2.1.0.jar -P /opt/kylo/drivers/

EXPOSE 7077 8450 8451
# RUN kylo-service
#CMD ["kinit","-k","-t","/etc/nifi/daf.keytab","daf@" ]
CMD ["/opt/cloudera/parcels/SPARK2/bin/spark-submit", "--master", "yarn", "--conf", "spark.driver.userClassPathFirst=true", "--class", "com.thinkbiganalytics.spark.SparkShellApp", "--driver-class-path", "/opt/kylo/kylo-services/conf:/opt/kylo/drivers/mariadb-java-client-2.1.0.jar", "--driver-java-options", "-Dlog4j.configuration=log4j-spark.properties", "/opt/kylo/kylo-spark/lib/app/kylo-spark-shell-client-v2-0.8.3.3.jar"]
