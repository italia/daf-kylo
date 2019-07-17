**How to view logs**
===================


In this document there are instruction on how to view logs in production environment.

----------

## **How to view Kylo and Nifi logs**

### **Kylo logs**
In order to view Kylo logs, just enter in the path you saved daf-kylo projects and the go in the folder scripts and run `kylologs.sh`. You can enter in your folder typing
```
cd <PREVIOUS_PATH>/daf-kylo/scripts
```
and run `kylologs.sh` typing
```
./kylologs.sh
```
### **NiFi logs**
NiFi logs are saved in the file named `nifi-app.log`, located in the logs folder under the folder you installed NiFi on your device. As instance, if you installed it in `/usr/nifi`, you can view the logs of the node named `tba-nifi-0` typing
```
kubectl exec -it tba-nifi-0 -- tail -1000f /usr/nifi/logs/nifi-app.log 
```
## **How to view Spark logs**


First perform Kerberos initialization by typing  `kinit -k -t <path> daf@DAF.GOV.IT` on the command line, where `<path >` stands for the local path daf.keytab file is saved, useful for the Kerberos initialization in production environment.
Then click on this [link](https://master-2.platform.daf.gov.it:8090/cluster/) on Firefox browser, but not in a private window.
Once redirected on the link, you can see a list of application id and everyone of them refers to the corresponding Spark job. Alongside them there are all the corresponding properties. Clicking on application-id, you are redirected to another web page where you can see a resume of the most important informations of the job. Clicking on the bottom right button, you can see Spark logs.

Another way to view Spark jobs is opening the command-line of the node where yarn CLI is installed and type
```
yarn logs -applicationId <APPLICATION_ID>
```