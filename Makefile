default: all

REGISTRY=nexus.default.svc.cluster.local:5000


.PHONY: activemq
activemq:
	docker build -t tba-activemq -f docker/activemq/Dockerfile docker/activemq
	docker tag tba-activemq $(REGISTRY)/tba-activemq.5.15.1:1.0.2-SNAPSHOT

.PHONY: elasticsearch5
elasticsearch5:
	mkdir -p docker/elasticsearch5/dist
	cp -R ../kylok8s/install/install-tar/target/kylo/bin/* docker/elasticsearch5/dist
	docker build -t tba-elasticsearch5 -f docker/elasticsearch5/Dockerfile docker/elasticsearch5
	docker tag tba-elasticsearch5 $(REGISTRY)/tba-elasticsearch.5.6.4:1.0.2-SNAPSHOT
	rm -dr docker/elasticsearch5/dist

.PHONY: mysql
mysql:
	mkdir -p docker/mysql/dist
	cp -R ../kylok8s/install/install-tar/target/kylo/setup/sql/mysql/kylo/* docker/mysql/dist
	docker build -t tba-mysql -f docker/mysql/Dockerfile docker/mysql
	docker tag tba-mysql $(REGISTRY)/tba-mysql.10.3:1.0.2-SNAPSHOT
	rm -dr docker/mysql/dist

.PHONY: kylo-services
kylo-services:
		mkdir -p docker/kylo-services/dist/kylo-services && \
		if [ ! -f ../kylok8s/install/install-tar/target/kylo/kylo-services/lib/postgresql-42.1.4.jar ] ;then curl -o ../kylok8s/install/install-tar/target/kylo/kylo-services/lib/postgresql-42.1.4.jar http://central.maven.org/maven2/org/postgresql/postgresql/42.1.4/postgresql-42.1.4.jar  ;fi
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-services/lib docker/kylo-services/dist/kylo-services
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-services/plugin docker/kylo-services/dist/kylo-services
		cp -R ../kylok8s/install/install-tar/target/kylo/bin docker/kylo-services/dist
		cp -R ../kylok8s/install/install-tar/target/kylo/lib docker/kylo-services/dist
		docker build -t tba-kylo-services -f docker/kylo-services/Dockerfile docker/kylo-services
		docker tag tba-kylo-services $(REGISTRY)/tba-kylo-services.8.4:1.0.3-SNAPSHOT
		rm -dr docker/kylo-services/dist

.PHONY: kylo-ui
kylo-ui:
		mkdir -p docker/kylo-ui/dist/kylo-ui
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-ui/lib docker/kylo-ui/dist/kylo-ui
		cp -R ../kylok8s/install/install-tar/target/kylo/kylo-ui/plugin docker/kylo-ui/dist/kylo-ui
		cp -R ../kylok8s/install/install-tar/target/kylo/bin docker/kylo-ui/dist
		cp -R ../kylok8s/install/install-tar/target/kylo/lib docker/kylo-ui/dist
		docker build -t tba-kylo-ui -f docker/kylo-ui/Dockerfile docker/kylo-ui
		docker tag tba-kylo-ui $(REGISTRY)/tba-kylo-ui.8.4:1.0.2-SNAPSHOT
		rm -dr docker/kylo-ui/dist

.PHONY: nifi
nifi:
		mkdir -p docker/nifi/dist
		cp -R ../kylok8s/install/install-tar/target/kylo/setup/nifi/* docker/nifi/dist
		docker build -t tba-nifi -f docker/nifi/Dockerfile docker/nifi
		docker tag tba-nifi $(REGISTRY)/tba-nifi.1.4.0:1.0.4-SNAPSHOT
		rm -dr docker/nifi/dist

.PHONY: build-kylo
build-kylo:
	git clone https://github.com/fabiannecci/kylo.git ../kylok8s | true
	cd ../kylok8s && \
	git checkout teamdigitale && \
	mvn clean install -DskipTests && \
	mkdir install/install-tar/target/kylo && \
	tar -C install/install-tar/target/kylo -xvf install/install-tar/target/kylo-*-dependencies.tar.gz

.PHONY: daf-kylo
daf-kylo:
	git clone https://github.com/italia/daf-kylo.git ../daf-kylo8s | true
	cd ../daf-kylo8s && \
    git checkout develop && \
    cd nifi/extensions && \
    mvn clean install -DskipTests
