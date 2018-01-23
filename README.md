# daf-kylo

## Kylo Stack

[Kylo stack dependencies](http://kylo.readthedocs.io/en/v0.8.3/installation/Dependencies.html#kylo-stack-dependencies)

### Kylo components

| Name 			      | Version |
|:--- 				    |:--- 		|
| ActiveMQ        | 5.15.1 	|
| Elasticsearch 	| 5.6.4 	|
| MariaDB		      | 10.3 		|
| Spark			      | 2.2.0		|
| Kylo-Services		| 8.3.3		|
| Kylo-UI     		| 8.3.3		|
| NiFi		        | 1.3.0		|

### Install required dependencies

```
brew tap caskroom/cask
brew update
brew install kubectl rpm make git
```

In order to be able to build most of Docker images kylo code will be required (source and compiled). To get this run:

```
make build-kylo
```


This will use `Makefile` to download the code and compile it.

### Build Docker images of the components
Once this is completed build every image:

```
make activemq
make elasticsearch
make mysql
make kylo-spark
make kylo-services
make kylo-ui
make nifi
```
This will use `Makefile` to download the basic empty images and build our custom docker images with required tagging.


### Push Docker images to local artifactory repository
Please ensure previously configuration of docker client as well as correct tagging the image has been performed. 'How to' can be found in:
[TeamDigitale onboarding  'Setup Docker '](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.ubxuumcef218)
[TeamDigitale onboarding  'Push Docker Image'](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.47zm3aqq5wip)

After config and proper tagging has been done, push can be performed by: `docker push [repositoryurl:repositoryport/artifact:version]`

for instance:
  ```
  docker push nexus.default.svc.cluster.local:5000/tba-kylo-services.8.4.0:1.0.0
  ```

### Deploy components in kubernetes cluster
Please ensure previously configuration of kubectl has been done. 'How to' can be found in: [TeamDigitale onboarding , 'Setup Kubernetes'](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.vvi8emze7m35)

After config is done, **deploy** into kubernetes cluster can be performed by: `./playbook.sh [component]`

for instance:
  ```
  ./playbook.sh activemq
  ```

or **delete** by: `./cleanup.sh [component]`

for instance:
  ```
  ./cleanup.sh activemq
  ```

### Configuration Ldap
By default ldap configuration is on, which means that if redeploy of kylo-services or kylo-ui happens nothing will break. Bare in mind though that after first time deployment is done (first time kylo is deployed in kubernetes cluster) *ldap login* has to be deactivated and *default login* activated.

This is done as follows:

`config-map/kylo-services.yaml` shoud be changed to from:
```
#Default profile
#spring.profiles.include=native,nifi-v1.2,auth-file,auth-kylo,search-esr,jms-activemq
spring.profiles.include=native,nifi-v1.2,auth-ldap,auth-kylo,search-esr,jms-activemq
```
to
```#Default profile
spring.profiles.include=native,nifi-v1.2,auth-file,auth-kylo,search-esr,jms-activemq
#spring.profiles.include=native,nifi-v1.2,auth-ldap,auth-kylo,search-esr,jms-activemq
```

equivalent should be done in `config-map/kylo-ui.yaml`

```
#Default profile
#spring.profiles.active=native,auth-kylo,auth-file
spring.profiles.active=native,auth-kylo,auth-ldap
```
changing it to

```
#Default profile
spring.profiles.active=native,auth-kylo,auth-file
#spring.profiles.active=native,auth-kylo,auth-ldap
```

After these two changes redeploy as follows:
```
kubectl delete -f config-map/kylo-services.yaml
kubectl delete -f config-map/kylo-ui.yaml

kubectl apply -f config-map/kylo-services.yaml
kubectl apply -f config-map/kylo-ui.yaml
```

As pointed out above, once this is done *ldap login* will be substituted by *default login* , this will allow to log in with default user `dladmin/thinkbig`. This has to be done to create users with the same name that those exist in ldap in order to grant them permissions (same functionality but for groups [is currently being fixed by R&D](https://kylo-io.atlassian.net/browse/KYLO-496)) . Once user/s (or group/s) is/are created change back `config-map/kylo-services.yaml` and `config-map/kylo-ui.yaml` and redeploy again. Ldap is now good to go.

# Licensing

Copyright (c) the respective contributors, as shown by the AUTHORS file.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published
by the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
