#!/bin/bash
kylouipod=$(kubectl get pods -o yaml | sed -En 's/name: tba-kylo-ui0.9.1-(.*)-(.*)/tba-kylo-ui-\1-\2/p')
echo "kylo ui pod is kylouipod"
kubectl exec -it $kylouipod -- /bin/bash