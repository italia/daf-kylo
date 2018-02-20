#!/bin/bash
kylouipod=$(kubectl get pods --selector=io.kompose.service=tba-kylo-ui -o yaml | sed -En 's/name: tba-kylo-ui-(.*)-(.*)/tba-kylo-ui-\1-\2/p')
echo "kylo ui pod is kylouipod"
kubectl exec -it tba-kylo-ui-6fcd9dd856-sz7ld -- /bin/bash