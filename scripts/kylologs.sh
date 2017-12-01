#!/bin/bash
nifiservicepod=$(kubectl get pods --selector=io.kompose.service=tba-nifi -o yaml | sed -En 's/name: tba-nifi-(.*)-(.*)/tba-nifi-\1-\2/p')
echo "NiFi pod is nifiservicepod"
kubectl exec -it nifiservicepod -- tail -1000f /usr/nifi/logs/nifi-app.log