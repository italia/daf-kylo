# Custom processors

## Daf pre-standardization

The goal of this processor is to create the function call and the parameters need to apply
the standardization process to the input dataset as explained [here](https://daf-docs.readthedocs.io/en/latest/datamgmt/dataset_conventions.html#ingestion-pipeline-steps).

### processors

### Query the catalog manager

The first processor, given a dataset name does:

1. query the catalog service
2. extract all the information about the dataset fields and the ingestion steps
3. create an instance of [IngestionFlow](src/main/java/it/gov/daf/nifi/processors/models2/IngestionFlow.Java) with all the [IngestionStep](src/main/java/it/gov/daf/nifi/processors/models2/IngestionStep.java)s.
4. send all them as output of the processor.

The second processor, given as input the an object of type `IngestionFlow`:
1. calls the spark job defined into the [daf-job-ingestion](https://github.com/teamdigitale/daf-job-ingestion) to execute the ingestion pipeline

### Ingestion Transformations

The supported dataTransformation are:

      - "text to utf-8",
      - "empty values to null",
      - "date to ISO8601",
      - "url to standard",
      - "vocabulary validation",
      - "enrich address",
      - "add row id",
      - "add ingestion date",
      - "add update date"

And are named in the file [transformations.json](./transformations.json).
Each transformation has:

- a priority
- an id
- a name
- a description

These information are materialized in the [IngestionFlow](src/main/java/it/gov/daf/nifi/processors/models2/IngestionFlow.java) class, that has
as attributes:
- a datasetName
- a datasetUri
- a List of [IngestionSteps](src/main/java/it/gov/daf/nifi/processors/models2/IngestionStep.java).

The class **IngestionStep** translates the information from the file `transformation.json` and add details about the transformations.
In particular, for each IngestionStep is defined:
- a priority
- a name
- a list of [StepDetails](src/main/java/it/gov/daf/nifi/processors/models2/StepDetail.java)

Finally, a **StepDetail** holds information about:

- the column
- an optional vocabulary path
- an optional source format in case of date types
- an optional source charset encoding.

Ideally, in this class we will add all the information to perform the data transformation.


## Test

For additional details start by taking a look at the [tests](./src/test/java/it/gov/daf/nifi/TestPreStandardization.java).


## Deploy

In order to deploy the processor into nifi, you need to:

1. create the nar file `mvn clean install`
2. copy the file `./target/teamdigitale-daf-nifi-processors-XXX.nar` into the gluster folder `/glusterfs/volume1/tba/evergreen2/nifi/extentions`

## Generate the Models

to generate the code for the catalog manage models do the following:

1. place into `src/main/resources/schema` the json schema of a catalog manager response for get_dataset by id.
2. run `mvn package`

it will update the model definition in the package `it.gov.daf.nifi.processors.models`. (I have generate the json schema using https://jsonschema.net/)
Everything is handled by the `jsonschema2pojo-maven-plugin` in the `pom.xml` file.
