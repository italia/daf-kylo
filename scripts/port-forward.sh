#!/bin/bash

case $1 in

kylo-ui)
    kylouipod=$(kubectl get pods -o yaml | sed -En 's/name: tba-kylo-ui-(.*)-(.*)/tba-kylo-ui-\1-\2/p')
    echo "kylo ui pod is kylouipod"
    kubectl exec -it $kyloservicepod -- tail -1000f /var/log/kylo-services/kylo-services.log
  ;;
nifi)
    nifipod=$(kubectl get pods -o yaml | sed -En 's/name: tba-nifi-(.*)-(.*)/tba-nifi-\1-\2/p')
    echo "nifi service pod is $nifipod"
    kubectl port-forward $nifipod 8080:8080
    ;;
esac

