#!/bin/bash -x

if [ "$1" = 'test' ]
then
    ENV='.'$1
    elif [ "$1" = 'prod' ]
    then
    ENV=''
fi

echo $ENV

case $2 in

  activemq)
	kubectl delete -f kubernetes/deployment$ENV/activemq.yaml
	kubectl delete -f kubernetes/service/activemq.yaml
	;;
  mysql)
	kubectl delete -f kubernetes/deployment$ENV/mysql.yaml
	kubectl delete -f kubernetes/service/mysql.yaml
	;;
  kylo-services)
  kubectl delete -f kubernetes/config-map$ENV/kylo-services-spark.yaml
	kubectl delete -f kubernetes/config-map$ENV/kylo-services.yaml
	kubectl delete -f kubernetes/deployment$ENV/kylo-services.yaml
	kubectl delete -f kubernetes/service/kylo-services.yaml
    ;;
  kylo-ui)
	kubectl delete -f kubernetes/config-map$ENV/kylo-ui.yaml
	kubectl delete -f kubernetes/deployment$ENV/kylo-ui.yaml
	kubectl delete -f kubernetes/service/kylo-ui.yaml
	;;
  nifi)
	kubectl delete -f kubernetes/config-map$ENV/nifi.yaml
	kubectl delete -f kubernetes/config-map$ENV/nifi-kylo.yaml
    kubectl delete -f kubernetes/deployment$ENV/nifi.yaml
    kubectl delete -f kubernetes/service/nifi.yaml
    ;;
esac
