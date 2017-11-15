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


This will read from Makefile to download the code and compile it.

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


### Push Docker images to local artifactory repository
Please ensure previously configuration of docker client as well as tagging the image has been performed. 'How to' can be found in:
[TeamDigitale on board  'Setup Docker '](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.ubxuumcef218)
[TeamDigitale on board  'Push Docker Image'](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.47zm3aqq5wip)

After config and tagging is done, push can be performed by: `docker push [repositoryurl:repositoryport/artifact:version]`

  i.e:
  ```
  docker push nexus.default.svc.cluster.local:5000/tba-kylo-services.8.3.3:1.0.0-SNAPSHOT
  ```

### Deploy components in kubernetes cluster
Please ensure previously configuration of kubectl has been done. 'How to' can be found in: [TeamDigitale on board , 'Setup Kubernetes'](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.vvi8emze7m35)

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
