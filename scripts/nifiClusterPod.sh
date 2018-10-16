#!/bin/bash

namespace="default"
if [[ $# -eq 2 ]] ; then
    namespace=$2
    echo "some message $namespace"
fi

nifiservicepod="tba-nifi-0"

case $1 in
0)
    nifiservicepod="tba-nifi-0"
;;
1)
    nifiservicepod="tba-nifi-1"
;;
2)
    nifiservicepod="tba-nifi-2"
;;
*)
    echo "Sorry, you specifiy a not existed pod!"
    echo "Insert the number of pod [0, 1, 2]";;
esac

echo "NiFi pod is $nifiservicepod"
kubectl exec -it --namespace="$namespace" $nifiservicepod -- /bin/bash