**PDND open data pipeline documentation**
===================

> **Note:**

>At the time this documentation is written, PDND open data pipeline is still in test environment, and is not deployed yet on production cluster. Some things need to be changed from test environment, such as file path from which tables that are created on Hadoop refers.


<!--This document contains the basics about open data pipeline operation. PDND (Piattaforma digitale nazionale dati - Italian digital data platform) is composed by ... (write a better intro). Open data -->

----------


**Open data feed pipeline operation** <!--(working process)-->
----------------

After the harvesting from ckan portal, data are transferred through a kafka producer into the pipeline , in order to start ETL process that ends with the creation of the table on Superset.

Pipeline operation can be divided four parts:

 - File saving on HDFS and table creation on Hadoop;
 - Superset request payload construction;
 - Kerberos initialization and ACL's setting;
 - Creation of the table on Superset.


#### **File saving on HDFS and table creation on Hadoop**

Open data feed pipeline starts with data retrieval through a Kafka consumer, which sends all required data to create the table on Hadoop to a python script. This one connects to Impala engine using `impala_admin` user, and then it provides to retrieve all necessary metadata from ElasticSearch through a post request. Once all necessary data are retrieved, the script performs the creation of the tables whose each of them refers to the files persisting in the directory `/usr/daf/open_data`, different from the one to use in production environment, that is `/daf/opendata`. Files persisting in `/user/daf/open_data` are saved under the path `/organization_name/database_name/table_name` and named with a progressive number, such as `/usr/daf/open_data/parco_salento/AGRI_policy/attivitacommerciali/256.csv`.

This way, the script performs the database creation (if it doesn't exist), with the same name as `database_name` but with lower case letters, as instance `agri_policy`, and the table creation (if it doesn't exist), with the same name as `table_name`, concatenated with the same progressive number the file is named, as instance `attivitacommerciali_256`.


#### **Superset request payload construction**

The tables on Superset are created through a post request, whose payload is a json with the same schema of the following ⧸⧸one:
```
{
	"schema":"agri__policy",
	"dbName":"default_org-db",
	"tableName":"attivitacommerciali_256"
}
```
Field "schema" refers to the database name where tables are saved on Hadoop through Impala engine; field "dbName" refers to the database name where Superset tables are saved; field "tableName" refers to the name the table is saved on Hadoop through Impala engine.

>**Note:**
>String used to create the table to view Superset must be no longer than one hundred characters, otherwise an error is generated and the table cannot be created.

#### **Kerberos initialization and ACL's setting**
Before performing the post request to create the table that will be viewed on Superset, permission are set as stated in the following table:

<table>
  <tr>
    <th><span style="font-weight:bold">PATH_HDFS</span></th>
    <th><span style="font-weight:700">OWNER:GROUP</span></th>
    <th><span style="font-weight:700">PERMISSIONS</span></th>
    <th><span style="font-weight:700">DEFAULT ACL</span></th>
    <th><span style="font-weight:bold">CUSTOM ACL</span></th>
  </tr>
  <tr>
    <td>/daf</td>
    <td>daf:daf</td>
    <td>rwx:rwx:--x</td>
    <td>-</td>
    <td>NO</td>
  </tr>
  <tr>
    <td>/opendata</td>
    <td>daf:daf</td>
    <td>rwx:rwx:--x</td>
    <td>hive:rwx<br>impala:rwx</td>
    <td>NO</td>
  </tr>
  <tr>
    <td>/[org_name]</td>
    <td>daf:[org_name]</td>
    <td>rwx:rwx:--x</td>
    <td>hive:rwx<br>impala:rwx</td>
    <td>NO</td>
  </tr>
  <tr>
    <td>/[domain]__[subdomain]</td>
    <td>daf:[org_name]</td>
    <td>rwx:rwx:--x</td>
    <td>hive:rwx<br>impala:rwx</td>
    <td>NO</td>
  </tr>
  <tr>
    <td>/[dataset_name]</td>
    <td>[default user]:<br>[default user group]</td>
    <td>rwx:rwx:---</td>
    <td>hive:rwx<br>impala:rwx</td>
    <td>open_data_group:rwx<br></td>
  </tr>
  <tr>
    <td>/[files].csv</td>
    <td>[default user]:<br>[default user group]</td>
    <td>rwx:rwx:---</td>
    <td>hive:rwx<br>impala:rwx</td>
    <td>open_data_group:rwx</td>
  </tr>
</table>

#### **Creation of the table on Superset**
This is the last step and involves the performing of the post request, whose aim is to create the table to view on Superset.