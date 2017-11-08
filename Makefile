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

.PHONY: elasticsearch5
elasticsearch5:
	mkdir -p docker/elasticsearch5/dist
	cp -R ../kylok8s/install/install-tar/target/kylo/bin/* docker/elasticsearch5/dist
	docker build -t tba-elasticsearch5 -f docker/elasticsearch5/Dockerfile docker/elasticsearch5
	docker tag tba-elasticsearch5 $(REGISTRY)/tba-elasticsearch5
	rm -dr docker/elasticsearch5/dist

.PHONY: mysql
mysql:
		mkdir -p docker/mysql/dist
		cp -R ../kylok8s/install/install-tar/target/kylo/setup/sql/mysql/kylo/* docker/mysql/dist
		docker build -t tba-mysql -f docker/mysql/Dockerfile docker/mysql
		docker tag tba-mysql $(REGISTRY)/tba-mysql
		rm -dr docker/mysql/dist

.PHONY: build-kylo
build-kylo:
	git clone https://github.com/Teradata/kylo.git ../kylok8s | true
	cd ../kylok8s && \
	git checkout tags/v0.8.3.3 && \
	mvn clean install -DskipTests && \
	mkdir install/install-tar/target/kylo && \
	tar -C install/install-tar/target/kylo -xvf install/install-tar/target/kylo-*-dependencies.tar.gz
