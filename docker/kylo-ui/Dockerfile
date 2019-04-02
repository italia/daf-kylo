# Base java imfkylo-services/libage
FROM openjdk:alpine3.7

ENV GLIBC_VERSION=2.25-r0

# Install required packages
RUN apk add --update --no-cache wget mysql-client ca-certificates bash java-snappy python py-pip && \
    update-ca-certificates

# Install glibc
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub && \
    wget -q https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-$GLIBC_VERSION.apk && \
    apk add glibc-$GLIBC_VERSION.apk && \
    rm glibc-$GLIBC_VERSION.apk

RUN mkdir -p /opt/kylo/kylo-ui
ADD dist/kylo-ui/lib  /opt/kylo/kylo-ui/lib
ADD dist/kylo-ui/plugin /opt/kylo/kylo-ui/plugin
ADD dist/lib  /opt/kylo/lib
ADD dist/bin /opt/kylo/bin

EXPOSE 8400
# RUN kylo-service
CMD ["java", "-cp", "/opt/kylo/kylo-ui/conf:/opt/kylo/kylo-ui/lib/*:/opt/kylo/kylo-ui/plugin/*", "com.thinkbiganalytics.KyloUiApplication"]
