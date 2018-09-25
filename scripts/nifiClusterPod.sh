#!/bin/bash

namespace="sec"
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

*)
    echo "Sorry, you specifiy a not existed pod!"
    echo "Use just 0 or 1";;
esac

echo "NiFi pod is $nifiservicepod"
kubectl exec -it --namespace="$namespace" $nifiservicepod -- /bin/bash