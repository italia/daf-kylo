#!/bin/bash -x

case $1 in

  elasticsearch)
	kubectl delete -f kubernetes/storage/elasticsearch.yaml
	kubectl delete -f kubernetes/pvc/elasticsearch.yaml
	kubectl delete -f kubernetes/deployment/elasticsearch.yaml
	kubectl delete -f kubernetes/service/elasticsearch.yaml
	;;
  activemq)
	kubectl delete -f kubernetes/storage/activemq.yaml
	kubectl delete -f kubernetes/pvc/activemq.yaml
	kubectl delete -f kubernetes/deployment/activemq.yaml
	kubectl delete -f kubernetes/service/activemq.yaml
	;;
  mysql)
	kubectl delete -f kubernetes/storage/mysql.yaml
	kubectl delete -f kubernetes/pvc/mysql.yaml
	kubectl delete -f kubernetes/deployment/mysql.yaml
	kubectl delete -f kubernetes/service/mysql.yaml
	;;
  kylo-spark)
	kubectl delete -f kubernetes/config-map/hadoop-config.yaml
	kubectl delete -f kubernetes/deployment/kylo-spark.yaml
	kubectl delete -f kubernetes/service/kylo-spark.yaml
	;;
  kylo-services)
	kubectl delete -f kubernetes/config-map/kylo-services.yaml
	kubectl delete -f kubernetes/storage/kylo-services.yaml
	kubectl delete -f kubernetes/pvc/kylo-services.yaml
	kubectl delete -f kubernetes/deployment/kylo-services.yaml
	kubectl delete -f kubernetes/service/kylo-services.yaml
  ;;
  kylo-ui)
	kubectl delete -f kubernetes/config-map/kylo-ui.yaml
	kubectl delete -f kubernetes/storage/kylo-ui.yaml
	kubectl delete -f kubernetes/pvc/kylo-ui.yaml
	kubectl delete -f kubernetes/deployment/kylo-ui.yaml
	kubectl delete -f kubernetes/service/kylo-ui.yaml
	;;
esac
