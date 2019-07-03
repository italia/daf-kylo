# Daf-Kylo for PDND (Piattaforma Digitale Nazionale Dati), previously DAF (Data & Analytics Framework)

<!-- > Insert here the application logo and badges if present. -->

> In order to install and use this repo you may deploy all the components into a _cloudera_ shared edge node.

> Daf-Kylo is a data lake platform built on Apache Hadoop and Spark. Daf-Kylo provides a data lake solution enabling self-service data ingest, data preparation, and data discovery. 
> Kylo integrates best practices around metadata capture, security, and data quality. 
> Apache Nifi provides a flexible data processing framework for building batch or streaming pipeline templates, and for enabling self-service features.

## What is the PDND (previously DAF)?

PDND stands for "Piattaforma Digitale Nazionale Dati" (Italian Digital Data Platform), previously known as Data & Analytics Framework (DAF).

In brief, is an attempt to establish a central Chief Data Officer (CDO) for the Government and Public Administration. Its main goal is to promote data exchange among Italian Public Administrations (PAs), to support the diffusion of open data, and to enable data-driven policies. You can find more about the PDND on the official [Digital Transformation Team website](https://teamdigitale.governo.it/it/projects/daf.htm).

## What is Daf-Kylo?

<!--> Insert here an extended description of the project with informations about context, goals, stakeholders, use cases, and finally the role of the project within the PDND with links to other repositories requiring this code or this code depends on. Embed also screenshots or video if present to give a preview of the application.-->
> Daf-Kylo repository contains the set of components used to deploy and manage the PDND data ingestion process.

> Folder /docker contains all the docker files for build images of daf-kylo components.

> Folder /kubernetes contains all the yaml files for deploy pods and services on kubernetes.

> Folder /kylo contains all the kylo stuff such as api documentation for the integration with PDND Portal, kylo templates, kylo patch.

> Folder /nifi contains all the nifi templates and customized processors used in ingestion process.

> Folder /scripts contains utils scripts for manage pods, log and other kubernetes stuff.




<!--> Insert here informations about files and folders structure, branch model adopted and release policy.-->

### Prerequisites 


* [Kubernetes](https://kubernetes.io/)
* [Cloudera](https://www.cloudera.com/)

### Project dependencies
Project dependencies can be find by clicking on this [link](https://kylo.readthedocs.io/en/v0.8.3/installation/Dependencies.html#kylo-stack-dependencies).

### Project components 
Project Daf-Kylo depends by the following components.

*  **ActiveMQ** version 5.15.1, available [here]([https://activemq.apache.org/activemq-5151-release.html](https://activemq.apache.org/activemq-5151-release.html));
*  **Elasticsearch** version 5.6.4, available [here]([https://www.elastic.co/downloads/past-releases/elasticsearch-5-6-4](https://www.elastic.co/downloads/past-releases/elasticsearch-5-6-4));
*  **MariaDB** version 10.3, available [here]([https://mariadb.com/kb/en/library/changes-improvements-in-mariadb-103/](https://mariadb.com/kb/en/library/changes-improvements-in-mariadb-103/));
*  **Spark** version 2.2.0, available [here]([http://spark.apache.org/downloads.html](http://spark.apache.org/downloads.html));
*  **Kylo-Services** version 9.1.0, available [here]([https://kylo.readthedocs.io/en/v0.9.1/about/Downloads.html](https://kylo.readthedocs.io/en/v0.9.1/about/Downloads.html));
*  **Kylo-UI** version 9.1.0, available [here]([https://kylo.readthedocs.io/en/v0.9.1/about/Downloads.html](https://kylo.readthedocs.io/en/v0.9.1/about/Downloads.html));
*  **NiFi** version 1.7.0, available [here]([https://nifi.apache.org/download.html](https://nifi.apache.org/download.html)).

## How to install and use Daf-Kylo 

<!--> Insert here a brief documentation to use this project as an end-user (not a developer) if applicable, including pre-requisites and internal and external dependencies. Insert a link to an extended documentation (user manual) if present.-->

### MacOS and Linux
Installing Daf-Kylo on Unix-like systems requires a package manager such as Homebrew. You can download and install Homebrew following the instructions given in the [Homebrew official website]([https://brew.sh/](https://brew.sh/)). Once you have installed Homebrew, you can follow some steps to complete the setup. First step is Homebrew cask installation. Open a terminal and type the following command to install Homebrew cask:
```  
brew tap caskroom/cask  
```
Then, update all formulas and Homebrew itself by typing
```
brew update  
```
Last, install kube-controller-manager, RPM, make and Git by typing
```
brew install kubectl rpm make git  
```

## How to build Daf-Kylo

<!--> Insert here a brief documentation for the developer to build, test and contribute. Insert a link to an extended documentation (developer manual) if present.-->
To build most of Docker images, kylo code is required (source and compiled). To get it run, you have to download and compile it, using `Makefile`, by typing the following commands (production and test environment):

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

### Login to nexus repository 
```
docker login nexus.daf.teamdigitale.it
```  

### Build Docker images of the components  
Once this is completed, you can build every image (production and test environment), by typing the following comands:  
<!-- This will use `Makefile` to download the basic empty images and build our custom docker images with required tagging. -->
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
### Push Docker images to local artifactory repository  
Please ensure previously configuration of docker client as well as correct tagging the image has been performed. 'How to' can be found in:  
  [TeamDigitale onboarding  'Setup Docker '](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.ubxuumcef218)  
  [TeamDigitale onboarding  'Push Docker Image'](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.47zm3aqq5wip)  
  
After config and proper tagging has been done, push can be performed typing: 
`docker push [repositoryurl:repositoryport/artifact:version]`  
  
for instance:  

  #### Production  
 ``` ./nexus_push.sh prod [namespace] ```
  #### Test  
 ``` ./nexus_push.sh test [namespace] ```  
The [namespace] is optional.  
  
### Deploy components in kubernetes cluster  
Please ensure previously configuration of kubectl has been done. 'How to' can be found in: 
  -[TeamDigitale onboarding , 'Setup Kubernetes'](https://docs.google.com/document/d/1KqeaZ2yj7rofslqzklYTCLb3AxPnV1mzOgSXOuTHTyw/edit?ts=59faf23f&pli=1#heading=h.vvi8emze7m35)  

#### Production

After config is done, **deploy** into kubernetes cluster can be performed typing `./playbook.sh [component]` . 


As an example, ``` ./playbook.sh prod activemq [namespace] ```. 

**Pod deletion** can be performed typing: `./cleanup.sh [environment] [component]`. 

As an example,  
 ``` ./cleanup.sh prod activemq [namespace] ```
 #### Test  
  
for instance:  
 ``` ./playbook.sh test activemq [namespace] ```  
or **delete** by: `./cleanup.sh [environment] [component]`  
  
for instance:  
 ``` ./cleanup.sh test activemq [namespace] ```
 
 ### Mysql Configuration  
By default the kylo database is not created in mysql container, so you have to create it.  
  
### Configuration Ldap  
 To configure Ldap authentication:  
 Edit the config-maps kylo-services.yaml & kylo-ui.yaml as follows:   		
 `config-map/kylo-services.yaml`   shoud be:  	 
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
  
## How to views Log 

## Custom Processors  
  
[Here](./nifi/extensions/processors/Readme.md) you can find additional information about custom processors created for the [DAF](https://teamdigitale.governo.it/it/projects/daf.htm).

## How to contribute

Contributions are welcome. Feel free to [open issues](./issues) and submit a [pull request](./pulls) at any time, but please read [our handbook](https://github.com/teamdigitale/daf-handbook) first.

## License

Copyright (c) 2019 Presidenza del Consiglio dei Ministri

This program is a free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <https://www.gnu.org/licenses/>.