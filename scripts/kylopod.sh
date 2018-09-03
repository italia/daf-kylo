#!/bin/bash
kyloservicepod=$(kubectl get pods -o yaml | sed -En 's/name: tba-kylo-services-(.*)-(.*)/tba-kylo-services-\1-\2/p')
echo "kylo service pod is $kyloservicepod"
kubectl exec -it $kyloservicepod -- /bin/bash


/etc/hadoop/conf/core-site.xml,/etc/hadoop/conf/hdfs-site.xml