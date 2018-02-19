#!/bin/bash
kylouipod=$(kubectl get pods --selector=io.kompose.service=tba-kylo-ui -o yaml | sed -En 's/name: tba-kylo-ui-(.*)-(.*)/tba-kylo-ui-\1-\2/p')
echo "kylo ui pod is $kylouipod"
kubectl exec -it $kylouipod -- tail -f /var/log/kylo-ui/kylo-ui.log