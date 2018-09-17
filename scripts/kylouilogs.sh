#!/bin/bash

if [ "$1" = 'sec' ]
then
    namespace='-n sec'
elif [ "$1" = 'default' ] || [ -z "$1" ];
then
    namespace='-n default'
fi
echo "Port-forward su namespace: $namespace"
kylouipod=$(kubectl get pods $namespace -o yaml | sed -En 's/name: tba-kylo-ui-(.*)-(.*)/tba-kylo-ui-\1-\2/p')
echo "kylo ui pod is $kylouipod"
kubectl exec $namespace -it $kylouipod -- tail -f /var/log/kylo-ui/kylo-ui.log