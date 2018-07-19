#!/bin/bash

nifiservicepod="tba-nifi-0"

case $1 in
0)
    nifiservicepod="tba-nifi-0"
;;
1)
    nifiservicepod="tba-nifi-1"
;;

*)
    echo "Sorry, you specifiy a not existed pod!"
    echo "Use just 0 or 1";;
esac

echo "NiFi pod is $nifiservicepod"
kubectl exec -it $nifiservicepod -- tail -1000f /usr/nifi/logs/nifi-app.log