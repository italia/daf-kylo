FROM elasticsearch:2.3.5

ENV ELASTICSEARCH_VERSION=2.3.5
ENV ELASTICSEARCH_HOME=/usr/share/elasticsearch
ENV ELASTICSEARCH_DATA=$ELASTICSEARCH_HOME/data
ENV ELASTICSEARCH_LOG=$ELASTICSEARCH_HOME/log

# RUN yes | $ELASTICSEARCH_HOME/bin/plugin install io.fabric8/elasticsearch-cloud-kubernetes/$ELASTICSEARCH_VERSION

# Copy files
# COPY elasticsearch.yml /elasticsearch/config/

ENV NUMBER_OF_SHARDS 1
ENV NUMBER_OF_REPLICAS 0

COPY dist/create-kylo-indexes-es.sh /opt/kylo/bin/create-kylo-indexes-es.sh
