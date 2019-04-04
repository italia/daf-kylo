# daf-kylo

## Getting started with Kubernetes
```
./playbook.sh test all
```

## Kylo Stack

[Kylo stack dependencies](http://kylo.readthedocs.io/en/v0.9.1/installation/Dependencies.html#kylo-stack-dependencies)

### Kylo components

| Name 			      | Version |
|:--- 				    |:--- 		|
| ActiveMQ        | 5.15.1 	|
| Elasticsearch 	| 5.6.4 	|
| MariaDB		      | 10.3 		|
| Spark			      | 2.2.0		|
| Kylo-Services		| 9.1.0		|
| Kylo-UI     		| 9.1.0		|
| NiFi		        | 1.6.0		|

### Install required dependencies

```
brew tap caskroom/cask
brew update
brew install kubectl rpm make git
```

In order to be able to build most of Docker images kylo code will be required (source and compiled). To get this run:
#### Production
```
make -f Makefile daf-kylo
make -f Makefile build-kylo
```

#### Test
```
make -f Makefile.test daf-kylo
make -f Makefile.test build-kylo
```

This will use `Makefile` to download the code and compile it.
### Login to nexus repository 
`docker login nexus.daf.teamdigitale.it`

### Build Docker images of the components
Once this is completed build every image:
#### Production
```
make activemq
make mysql
make kylo-services
make kylo-ui
make nifi
```
#### Test
```
make -f Makefile.test activemq
make -f Makefile.test mysql
make -f Makefile.test kylo-services
make -f Makefile.test kylo-ui
make -f Makefile.test nifi
```

This will use `Makefile` to download the basic empty images and build our custom docker images with required tagging.


### Push Docker images to local artifactory repository
Check if this docker setting are correct and to do in the installation
Please ensure previously configuration of docker client as well as correct tagging the image has been performed. 'How to' can be found in:
[TeamDigitale onboarding  'Setup Docker '](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.ubxuumcef218)
[TeamDigitale onboarding  'Push Docker Image'](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.47zm3aqq5wip)

After config and proper tagging has been done, push can be performed by: `docker push [repositoryurl:repositoryport/artifact:version]`

for instance:

#### Production
  ```
    ./nexus_push.sh prod [namespace]
  ```
#### Test
  ```
    ./nexus_push.sh test [namespace]
  ```

The [namespace] is optional.

### Deploy components in kubernetes cluster
Please ensure previously configuration of kubectl has been done. 'How to' can be found in: [TeamDigitale onboarding , 'Setup Kubernetes'](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.vvi8emze7m35)

After config is done, **deploy** into kubernetes cluster can be performed by: `./playbook.sh [component]`
#### Production

for instance:
  ```
  ./playbook.sh prod activemq [namespace]
  ```

or **delete** by: `./cleanup.sh [environment] [component]`

for instance:
  ```
  ./cleanup.sh prod activemq [namespace]
  ```
#### Test

for instance:
  ```
  ./playbook.sh test activemq [namespace]
  ```

or **delete** by: `./cleanup.sh [environment] [component]`

for instance:
  ```
  ./cleanup.sh test activemq [namespace]
  ```
### Mysql Configuration
By default the kylo database is not created in mysql container, so you have to create it.

### Configuration Ldap
To configure Ldap authentication:
1. Edit the config-maps kylo-services.yaml & kylo-ui.yaml as it follows:

`config-map/kylo-services.yaml` shoud be:
#### Production
```
    security.auth.ldap.server.uri=ldap://idm.daf.gov.it:389/cn=users,cn=accounts,dc=daf,dc=gov,dc=it
    security.auth.ldap.server.authDn=uid=admin,cn=users,cn=accounts,dc=daf,dc=gov,dc=it
    security.auth.ldap.server.password=xxxxx
```
#### Test
```   
    security.auth.ldap.server.uri=ldap://idm.teamdigitale.test:389/cn=users,cn=accounts,dc=daf,dc=gov,dc=it
    security.auth.ldap.server.authDn=uid=application,cn=users,cn=accounts,dc=daf,dc=gov,dc=it
    security.auth.ldap.server.password=xxxxx
```

equivalent should be done in `config-map/kylo-ui.yaml`
#### Production
```
    security.auth.ldap.server.uri=ldap://idm.daf.gov.it:389/cn=users,cn=accounts,dc=daf,dc=gov,dc=it
    security.auth.ldap.server.authDn=uid=admin,cn=users,cn=accounts,dc=daf,dc=gov,dc=it
    security.auth.ldap.server.password=xxxxxx
```
#### Test

```
    security.auth.ldap.server.uri=ldap://idm.teamdigitale.test:389/cn=users,cn=accounts,dc=daf,dc=gov,dc=it
    security.auth.ldap.server.authDn=uid=application,cn=users,cn=accounts,dc=daf,dc=gov,dc=it
    security.auth.ldap.server.password=xxxxxx
```
After these two changes redeploy as follows:
```
kubectl delete -f config-map/kylo-services.yaml
kubectl delete -f config-map/kylo-ui.yaml

kubectl apply -f config-map/kylo-services.yaml
kubectl apply -f config-map/kylo-ui.yaml
```

In the above example, it is not take in account the [namespace].

2. Go to idm.teamdigitale.test and create an user such as *dladmin* with a password *password*

After these you are able to login into kylo ui!

As pointed out above, once this is done *ldap login* will be substituted by *default login* , this will allow to log in with default user `dladmin/thinkbig`. This has to be done to create users with the same name that those exist in ldap in order to grant them permissions (same functionality but for groups [is currently being fixed by R&D](https://kylo-io.atlassian.net/browse/KYLO-496)) . Once user/s (or group/s) is/are created change back `config-map/kylo-services.yaml` and `config-map/kylo-ui.yaml` and redeploy again. Ldap is now good to go.

### Bootstrap note
When kylo starts for the first time it need liquibase for creating Kylo DB, make sure that in the application.properties in kylo-service's config map:

```
liquibase.enabled=true
```

## Log configuration

## Custom Processors

[Here](./nifi/extensions/processors/Readme.md) you can find additional information about custom processors created for the [DAF](https://teamdigitale.governo.it/it/projects/daf.htm).

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
