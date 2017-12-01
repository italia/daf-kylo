default: all

REGISTRY=nexus.default.svc.cluster.local:5000


.PHONY: activemq
activemq:
	docker build -t tba-activemq -f docker/activemq/Dockerfile docker/activemq
	docker tag tba-activemq $(REGISTRY)/tba-activemq.5.15.1:2.0.0-SNAPSHOT

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
	docker tag tba-elasticsearch5 $(REGISTRY)/tba-elasticsearch.5.6.4:2.0.0-SNAPSHOT
	rm -dr docker/elasticsearch5/dist

.PHONY: mysql
mysql:
	mkdir -p docker/mysql/dist
	cp -R ../kylok8s/install/install-tar/target/kylo/setup/sql/mysql/kylo/* docker/mysql/dist
	docker build -t tba-mysql -f docker/mysql/Dockerfile docker/mysql
	docker tag tba-mysql $(REGISTRY)/tba-mysql.10.3:2.0.0-SNAPSHOT
	rm -dr docker/mysql/dist

.PHONY: kylo-spark
kylo-spark:
	mkdir -p docker/kylo-spark/dist/kylo-services && \
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-services/lib/app docker/kylo-spark/dist/kylo-services/lib
	docker build -t tba-kylo-spark -f docker/kylo-spark/Dockerfile docker/kylo-spark
	docker tag tba-kylo-spark $(REGISTRY)/tba-kylo-spark.2.2.0:1.1.0-SNAPSHOT
	rm -dr docker/kylo-spark/dist

.PHONY: kylo-services
kylo-services:
		mkdir -p docker/kylo-services/dist/kylo-services && \
		if [ ! -f ../kylok8s/install/install-tar/target/kylo/kylo-services/lib/postgresql-42.1.4.jar ] ;then curl -o ../kylok8s/install/install-tar/target/kylo/kylo-services/lib/postgresql-42.1.4.jar http://central.maven.org/maven2/org/postgresql/postgresql/42.1.4/postgresql-42.1.4.jar  ;fi
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-services/lib docker/kylo-services/dist/kylo-services
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-services/plugin docker/kylo-services/dist/kylo-services
		cp -R ../kylok8s/install/install-tar/target/kylo/bin docker/kylo-services/dist
		cp -R ../kylok8s/install/install-tar/target/kylo/lib docker/kylo-services/dist
		docker build -t tba-kylo-services -f docker/kylo-services/Dockerfile docker/kylo-services
		docker tag tba-kylo-services $(REGISTRY)/tba-kylo-services.8.4:1.0.0-SNAPSHOT
		rm -dr docker/kylo-services/dist

.PHONY: kylo-ui
kylo-ui:
		mkdir -p docker/kylo-ui/dist/kylo-ui
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-ui/lib docker/kylo-ui/dist/kylo-ui
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-ui/plugin docker/kylo-ui/dist/kylo-ui
		cp -R ../kylok8s/install/install-tar/target/kylo/bin docker/kylo-ui/dist
		cp -R ../kylok8s/install/install-tar/target/kylo/lib docker/kylo-ui/dist
		docker build -t tba-kylo-ui -f docker/kylo-ui/Dockerfile docker/kylo-ui
		docker tag tba-kylo-ui $(REGISTRY)/tba-kylo-ui.8.4:1.0.0-SNAPSHOT
		rm -dr docker/kylo-ui/dist

.PHONY: nifi
nifi:
		mkdir -p docker/nifi/dist
		cp -R ../kylok8s/install/install-tar/target/kylo/setup/nifi/* docker/nifi/dist
		docker build -t tba-nifi -f docker/nifi/Dockerfile docker/nifi
		docker tag tba-nifi $(REGISTRY)/tba-nifi.1.4.0:1.0.0-SNAPSHOT
		rm -dr docker/nifi/dist

.PHONY: nifi-tba-daf
nifi-tba-daf:
		mkdir -p docker/nifi-tba-daf/dist
		cp -R ../kylok8s/install/install-tar/target/kylo/setup/nifi/* docker/nifi-tba-daf/dist
		docker build -t tba-nifi-daf -f docker/nifi-tba-daf/Dockerfile docker/nifi-tba-daf
		docker tag tba-nifi-daf $(REGISTRY)/tba-nifi-daf.1.3.0:2.0.0-SNAPSHOT
		rm -dr docker/nifi-tba-daf/dist

.PHONY: build-kylo
build-kylo:
	git clone https://github.com/Teradata/kylo.git ../kylok8s | true
	cd ../kylok8s && \
	git checkout tags/v0.8.4 && \
	mvn clean install -DskipTests && \
	mkdir install/install-tar/target/kylo && \
	tar -C install/install-tar/target/kylo -xvf install/install-tar/target/kylo-*-dependencies.tar.gz
