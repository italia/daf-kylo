default: all

REGISTRY=nexus.default.svc.cluster.local:5000


.PHONY: activemq
activemq:
	docker build -t tba-activemq -f docker/activemq/Dockerfile activemq
	docker tag tba-activemq $(REGISTRY)/tba-activemq


.PHONY: elasticsearch
elasticsearch:
	mkdir -p docker/elasticsearch/dist
	cp -R ../kylok8s/install/install-tar/target/kylo/bin/* docker/elasticsearch/dist
	docker build -t tba-elasticsearch -f docker/elasticsearch/Dockerfile docker/elasticsearch
	docker tag tba-elasticsearch $(REGISTRY)/tba-elasticsearch
	rm -dr docker/elasticsearch/dist 

.PHONY: build-kylo
build-kylo:
	git clone https://github.com/Teradata/kylo.git ../kylok8s | true
	cd ../kylok8s && \
	git checkout tags/v0.8.3.3 && \
	mvn clean install -DskipTests && \
	mkdir install/install-tar/target/kylo && \
	tar -C install/install-tar/target/kylo -xvf install/install-tar/target/kylo-*-dependencies.tar.gz
