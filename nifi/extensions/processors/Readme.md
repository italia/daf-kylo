# Custom processors

## Daf pre-standardization

The goal of this processor is to create the function call and the parameters need to apply
the standardization process to the input dataset as explained [here](https://daf-docs.readthedocs.io/en/latest/datamgmt/dataset_conventions.html#ingestion-pipeline-steps).

An overview can be found [here](./StandardizationFlow.md)


## Deploy

In order to deploy the processor into nifi, you need to:

1. create the nar file `mvn clean install`
2. copy the file `./target/teamdigitale-daf-nifi-processors-XXX.nar` into the gluster folder `/glusterfs/volume1/tba/evergreen2/nifi/extentions`
