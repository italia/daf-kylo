# Custom processors

## Daf pre-standardization

The goal of this processor is to create the function call and the parameters need to apply
the standardization process to the input dataset as explained [here](https://daf-docs.readthedocs.io/en/latest/datamgmt/dataset_conventions.html#ingestion-pipeline-steps).

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

Additional documentation can be found [here](./daf-prestandardization.md).