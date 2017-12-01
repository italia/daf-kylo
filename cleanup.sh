#!/bin/bash -x

case $1 in

  elasticsearch)
	kubectl delete -f kubernetes/deployment/elasticsearch.yaml
	kubectl delete -f kubernetes/service/elasticsearch.yaml
	;;
  activemq)
	kubectl delete -f kubernetes/deployment/activemq.yaml
	kubectl delete -f kubernetes/service/activemq.yaml
	;;
  mysql)
	kubectl delete -f kubernetes/deployment/mysql.yaml
	kubectl delete -f kubernetes/service/mysql.yaml
	;;
  kylo-services)
	kubectl delete -f kubernetes/config-map/kylo-services.yaml
	kubectl delete -f kubernetes/deployment/kylo-services.yaml
	kubectl delete -f kubernetes/service/kylo-services.yaml
  ;;
  kylo-ui)
	kubectl delete -f kubernetes/config-map/kylo-ui.yaml
	kubectl delete -f kubernetes/deployment/kylo-ui.yaml
	kubectl delete -f kubernetes/service/kylo-ui.yaml
	;;
  nifi)
	kubectl delete -f kubernetes/config-map/nifi.yaml
	kubectl delete -f kubernetes/config-map/nifi-kylo.yaml

  kubectl delete -f kubernetes/deployment/nifi.yaml
  kubectl delete -f kubernetes/service/nifi.yaml
esac
