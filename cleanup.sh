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
esac
