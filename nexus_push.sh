#!/bin/bash -x

case $1 in

  test)
    docker push nexus.teamdigitale.test/tba-mysql.10.3:1.1.0
    docker push nexus.teamdigitale.test/tba-activemq.5.15.1:1.1.0
    docker push nexus.teamdigitale.test/tba-kylo-services.9.1:1.0.5-FIX
    docker push nexus.teamdigitale.test/tba-kylo-ui.9.1:1.0.5-FIX
    docker push nexus.teamdigitale.test/tba-nifi.1.6.0:1.2.6-CLUSTER
	;;
  prod)
    docker push nexus.default.svc.cluster.local:5000/tba-mysql.10.3:1.1.0
    docker push nexus.default.svc.cluster.local:5000/tba-activemq.5.15.1:1.1.0
    docker push nexus.default.svc.cluster.local:5000/tba-kylo-services.8.4:1.1.0
    docker push nexus.default.svc.cluster.local:5000/tba-kylo-ui.8.4:1.1.0
    docker push nexus.default.svc.cluster.local:5000/tba-nifi.1.4.0:1.1.1-SNAPSHOT
	;;
esac

    docker push 192.168.20.52/tba-kylo-services.9.1:1.0.5-FIX
    docker push 192.168.20.52/tba-kylo-ui.9.1:1.0.5-FIX
    docker push 192.168.20.52/tba-nifi.1.6.0:1.2.5-CLUSTER