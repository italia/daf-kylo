#!/bin/bash
nifiservicepod=$(kubectl get pods -o yaml | sed -En 's/name: tba-nifi-(.*)-(.*)/tba-nifi-\1-\2/p')
echo "NiFi pod is $nifiservicepod"
kubectl exec -it $nifiservicepod -- /bin/bash