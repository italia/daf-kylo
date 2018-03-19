#!/bin/bash -x

case $1 in

  all)
	kubectl apply -f kubernetes/config-map --recursive
	kubectl apply -f kubernetes/deployment.test --recursive
	kubectl apply -f kubernetes/service --recursive
	;;
  elasticsearch)
	kubectl apply -f kubernetes/deployment.test/elasticsearch.yaml
	kubectl apply -f kubernetes/service/elasticsearch.yaml
	;;
  activemq)
	kubectl apply -f kubernetes/deployment.test/activemq.yaml
	kubectl apply -f kubernetes/service/activemq.yaml
	;;
  mysql)
	kubectl apply -f kubernetes/deployment.test/mysql.yaml
	kubectl apply -f kubernetes/service/mysql.yaml
	;;
  kylo-services)
	kubectl apply -f kubernetes/config-map/kylo-services.yaml
  	kubectl apply -f kubernetes/deployment.test/kylo-services.yaml
  	kubectl apply -f kubernetes/deployment.test/kylo-services.yaml
  	kubectl apply -f kubernetes/service/kylo-services.yaml
	;;
  kylo-ui)
	kubectl apply -f kubernetes/config-map/kylo-ui.yaml
  	kubectl apply -f kubernetes/deployment.test/kylo-ui.yaml
  	kubectl apply -f kubernetes/service/kylo-ui.yaml
	;;
  nifi)
	kubectl apply -f kubernetes/config-map/nifi.yaml
	kubectl apply -f kubernetes/config-map/nifi-kylo.yaml
  	kubectl apply -f kubernetes/deployment.test/nifi.yaml
  	kubectl apply -f kubernetes/service/nifi.yaml
	;;
esac
