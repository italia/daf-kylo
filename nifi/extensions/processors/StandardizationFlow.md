# Ingestion Pipeline

To goal of this document is to provide a design-document for the [ingestion and standardization process](https://daf-docs.readthedocs.io/en/latest/datamgmt/dataset_conventions.html#ingestion-pipeline-steps).
The process is formalized as a **Nifi** flow composed by two processors.
The first processor, given a dataset name does:

1. query the catalog service
2. extract all the information about the dataset fields and the ingestion steps
3. create an instance of [IngestionFlow]() with all the [IngestionStep]()s.
4. send all them as output

The second processor, given as input the an object of type `IngestionFlow`:
1. calls the spark job defined into the [daf-job-ingestion](https://github.com/teamdigitale/daf-job-ingestion) to execute the ingestion pipeline

## Ingestion Transformations

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

These information are materialized in the [IngestionFlow](./src/main/java/it/gov/daf/nifi/processors/models/IngestionFlow.java) class, that has
as attributes:
- a datasetName
- a datasetUri
- a List of [IngestionSteps](./src/main/java/it/gov/daf/nifi/processors/models/IngestionStep.java).

The class **IngestionStep** translates the information from the file `transformation.json` and add details about the transformations.
In particular, for each IngestionStep is defined:
- a priority
- a name
- a list of [StepDetails](./src/main/java/it/gov/daf/nifi/processors/models/StepDetail.java)

Finally, a **StepDetail** holds information about:

- the column
- an optional vocabulary path
- an optional source format in case of date types
- an optional source charset encoding.

Ideally, in this class we will add all the information to perform the data transformation.

For additional details start by taking a look at the [tests](./src/test/java/it/gov/daf/nifi/TestPreStandardization.java).
