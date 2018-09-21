#!/bin/bash

nifiservicepod="tba-nifi-0"

case $2 in
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
if [ "$1" = 'sec' ]
then
    namespace='-n sec'
elif [ "$1" = 'default' ] || [ -z "$1" ];
then
    namespace='-n default'
fi
echo "Port-forward su namespace: $namespace"

echo "NiFi pod is $nifiservicepod"
kubectl exec -it $namespace $nifiservicepod -- tail -1000f /usr/nifi/logs/nifi-app.log