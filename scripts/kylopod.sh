#!/bin/bash
kyloservicepod=$(kubectl get pods -o yaml | sed -En 's/name: tba-kylo-services0.9.1-(.*)-(.*)/tba-kylo-services-\1-\2/p')
echo "kylo service pod is $kyloservicepod"
kubectl exec -it $kyloservicepod -- /bin/bash