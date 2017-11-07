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

esac
