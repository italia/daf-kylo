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
    docker push nexus.daf.teamdigitale.it/tba-mysql.10.3:1.1.0
    docker push nexus.daf.teamdigitale.it/tba-activemq.5.15.1:1.1.0
    docker push nexus.daf.teamdigitale.it/tba-kylo-services.9.1:1.0.1
    docker push nexus.daf.teamdigitale.it/tba-kylo-ui.9.1:1.0.0
    docker push nexus.daf.teamdigitale.it/tba-nifi.1.6.0:1.0.1-CLUSTER
	;;
esac