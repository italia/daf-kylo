# daf-kylo

## Kylo Stack

[Kylo stack dependencies](http://kylo.readthedocs.io/en/v0.8.3/installation/Dependencies.html#kylo-stack-dependencies)

### Kylo components

| Name 			| Version |
|:--- 				|:--- 		|
| ActiveMQ 		| 5.15.1 	|
| Elasticsearch 	| 2.3.5 	|

### Install required dependencies

```
brew tap caskroom/cask
brew update
brew install kubectl rpm make git
```

In order to be able to build most of Docker images kylo code (source and compiled will be required). To get this run:

`make build-kylo`

This will read from Makefile to download the code and compile it.

Once this is completed build every image:
`make activemq`
`make elasticsearch`

Then push them to repository please have a look at:
[https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.xbaiz1wufxmp]
