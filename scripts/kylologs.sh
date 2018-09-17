#!/bin/bash
if [ "$1" = 'sec' ]
then
    namespace='-n sec'
elif [ "$1" = 'default' ] || [ -z "$1" ];
then
    namespace='-n default'
fi

echo "Port-forward su namespace: $namespace"
kyloservicepod=$(kubectl get pods $namespace -o yaml | sed -En 's/name: tba-kylo-services-(.*)-(.*)/tba-kylo-services-\1-\2/p')
echo "kylo service pod is $kyloservicepod"
kubectl exec -it $namespace $kyloservicepod -- tail -1000f /var/log/kylo-services/kylo-services.log