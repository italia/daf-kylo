#!/bin/bash -x

namespace="sec"

if [ "$1" = 'test' ]
then
    ENV='.'$1
    elif [ "$1" = 'prod' ]
    then
    ENV=''
fi

if [[ $# -eq 3 ]] ; then
    namespace=$3
    echo "some message $namespace"
fi

echo "Working on '$1' environment on namespace '$namespace'"

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
    if [ "$1" = 'prod' ]
    then
	      kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/nifi.yaml
	      kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/nifi-kylo.yaml
  	    kubectl apply --namespace="$namespace" -f kubernetes/deployment$ENV/nifi.yaml
  	    kubectl apply --namespace="$namespace" -f kubernetes/service/nifi.yaml
    else
        echo "This feature is deprecated for testing environment; use nifi-cluster"
    fi
  ;;
  nifi-cluster)
    kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/nifi-cluster.yaml
    kubectl apply --namespace="$namespace" -f kubernetes/config-map$ENV/nifi-kylo.yaml
    kubectl apply --namespace="$namespace" -f kubernetes/deployment$ENV/nifi-cluster.yaml
    kubectl apply --namespace="$namespace" -f kubernetes/service/nifi-cluster.yaml
	;;
esac
