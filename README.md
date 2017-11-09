# daf-kylo

## Kylo Stack

[Kylo stack dependencies](http://kylo.readthedocs.io/en/v0.8.3/installation/Dependencies.html#kylo-stack-dependencies)

### Kylo components

| Name 			| Version |
|:--- 				|:--- 		|
| ActiveMQ 		| 5.15.1 	|
| Elasticsearch 	| 5.6.4 	|
| MariaDB		| 10.3 		|
| Spark			| 2.2.0		|
| Kylo-Services		| 8.3.3		|

### Install required dependencies

```
brew tap caskroom/cask
brew update
brew install kubectl rpm make git
```

In order to be able to build most of Docker images kylo code will be required (source and compiled). To get this run:

`make build-kylo`

This will read from Makefile to download the code and compile it.

Once this is completed build every image:
`make activemq`
`make elasticsearch`
`make mysql`
`make kylo-spark`
`make kylo-services`

Then push them to repository please have a look at:
[https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.xbaiz1wufxmp]


Once this is done can be deploy into kubernetes cluster through:
`./playbook.sh [component]` 	i.e. `./playbook.sh activemq`

or delete from it with:
`./cleanup.sh [component]` 	i.e `./cleanup.sh activemq`
