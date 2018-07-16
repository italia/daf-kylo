#!/bin/bash -x

namespace="default"

if [ "$1" = 'test' ]
then
    ENV='.'$1
    elif [ "$1" = 'prod' ]
    then
    ENV=''
fi

echo $ENV

case $2 in

  all)
	kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV --recursive
	kubectl apply --namespace="$namespace" -f kubernetes/deployment$ENV --recursive
	kubectl apply --namespace="$namespace" -f kubernetes/service --recursive
	;;
  activemq)
	kubectl apply -f kubernetes/deployment$ENV/activemq.yaml
	kubectl apply -f kubernetes/service/activemq.yaml
	;;
  mysql)
	kubectl apply --namespace="$namespace" -f kubernetes/deployment$ENV/mysql.yaml
	kubectl apply --namespace="$namespace" -f kubernetes/service/mysql.yaml
	;;
  kylo-services)
  	#kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/kylo-services-spark.yaml
	kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/kylo-services.yaml
  	kubectl apply --namespace="$namespace" -f kubernetes/deployment$ENV/kylo-services.yaml
  	kubectl apply --namespace="$namespace" -f kubernetes/service/kylo-services.yaml
	;;
  kylo-ui)
	kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/kylo-ui.yaml
  	kubectl apply --namespace="$namespace" -f kubernetes/deployment$ENV/kylo-ui.yaml
  	kubectl apply --namespace="$namespace" -f kubernetes/service/kylo-ui.yaml
	;;
  nifi)
	kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/nifi.yaml
	kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/nifi-kylo.yaml
  	kubectl apply --namespace="$namespace" -f kubernetes/deployment$ENV/nifi.yaml
  	kubectl apply --namespace="$namespace" -f kubernetes/service/nifi.yaml
	;;
esac
