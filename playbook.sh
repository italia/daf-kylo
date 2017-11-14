#!/bin/bash -x

case $1 in

  all)
	kubectl apply -f k8s/config-map --recursive
	kubectl apply -f k8s/storage --recursive
	kubectl apply -f k8s/pvc --recursive
	kubectl apply -f k8s/deployment --recursive
	kubectl apply -f k8s/service --recursive
	;;
  elasticsearch)
	kubectl apply -f kubernetes/storage/elasticsearch.yaml
	kubectl apply -f kubernetes/pvc/elasticsearch.yaml
	kubectl apply -f kubernetes/deployment/elasticsearch.yaml
	kubectl apply -f kubernetes/service/elasticsearch.yaml
	;;
  activemq)
	kubectl apply -f kubernetes/storage/activemq.yaml
	kubectl apply -f kubernetes/pvc/activemq.yaml
	kubectl apply -f kubernetes/deployment/activemq.yaml
	kubectl apply -f kubernetes/service/activemq.yaml
	;;
  mysql)
	kubectl apply -f kubernetes/storage/mysql.yaml
	kubectl apply -f kubernetes/pvc/mysql.yaml
	kubectl apply -f kubernetes/deployment/mysql.yaml
	kubectl apply -f kubernetes/service/mysql.yaml
	;;
  kylo-spark)
	kubectl apply -f kubernetes/config-map/hadoop-config.yaml
	kubectl apply -f kubernetes/deployment/kylo-spark.yaml
	kubectl apply -f kubernetes/service/kylo-spark.yaml
	;;
  kylo-services)
	kubectl apply -f kubernetes/config-map/kylo-services.yaml
  kubectl apply -f kubernetes/storage/kylo-services.yaml
  kubectl apply -f kubernetes/pvc/kylo-services.yaml
  kubectl apply -f kubernetes/deployment/kylo-services.yaml
  kubectl apply -f kubernetes/service/kylo-services.yaml
	;;
  kylo-ui)
	kubectl apply -f kubernetes/config-map/kylo-ui.yaml
  kubectl apply -f kubernetes/storage/kylo-ui.yaml
  kubectl apply -f kubernetes/pvc/kylo-ui.yaml
  kubectl apply -f kubernetes/deployment/kylo-ui.yaml
  kubectl apply -f kubernetes/service/kylo-ui.yaml
	;;
esac
