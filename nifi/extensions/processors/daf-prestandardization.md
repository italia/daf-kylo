# Daf Prestandardization

The goal of this component is to create 

This components, given a dataset name:

1. retrieves the list of the transformationSteps to apply from  `Operational.ingestion_pipeline`
```yaml

  ingestion_pipeline:
    type: array
    description: List of ingestion pipeline to be applied, in order of declaration, to the data to be ingested.
    items:
      type: string
```

2. get all the fields of the dataset plus the semantic annotations from `flatschema`.

```json
{
    "flatSchema": [
        {
            "name": "anno",
            "type": "int",
            "metadata": {
                "semantics": {
                    "id": "latitudine",
                    "context": "[Indirizzi(Luoghi)]"
                },
                "tag": [],
                "field_type": "",
                "constr": [
                    {
                        "type": "",
                        "param": ""
                    }
                ],
                "required": 0,
                "cat": ""
            }
        },
        {
            "name": "provincia",
            "type": "string",
            "metadata": {
                "semantics": {
                    "id": "",
                    "context": ""
                },
                "desc": "",
                "tag": [],
                "field_type": "",
                "constr": [
                    {
                        "type": "",
                        "param": ""
                    }
                ],
                "required": 0,
                "cat": ""
            }
        },
        {
            "name": "mese",
            "type": "int",
            "metadata": {
                "semantics": {
                    "id": "",
                    "context": ""
                },
                "desc": "",
                "tag": [],
                "field_type": "",
                "constr": [
                    {
                        "type": "",
                        "param": ""
                    }
                ],
                "required": 0,
                "cat": ""
            }
        },
        {
            "name": "macrotipologia",
            "type": "string",
            "metadata": {
                "semantics": {
                    "id": "",
                    "context": ""
                },
                "desc": "",
                "tag": [],
                "field_type": "",
                "constr": [
                    {
                        "type": "",
                        "param": ""
                    }
                ],
                "required": 0,
                "cat": ""
            }
        },
        {
            "name": "descrizione",
            "type": "string",
            "metadata": {
                "semantics": {
                    "id": "",
                    "context": ""
                },
                "desc": "",
                "tag": [],
                "field_type": "",
                "constr": [
                    {
                        "type": "",
                        "param": ""
                    }
                ],
                "required": 0,
                "cat": ""
            }
        },
        {
            "name": "arrivi",
            "type": "int",
            "metadata": {
                "semantics": {
                    "id": "",
                    "context": ""
                },
                "desc": "",
                "tag": [],
                "field_type": "",
                "constr": [
                    {
                        "type": "",
                        "param": ""
                    }
                ],
                "required": 0,
                "cat": ""
            }
        },
        {
            "name": "presenze",
            "type": "int",
            "metadata": {
                "semantics": {
                    "id": "",
                    "context": ""
                },
                "desc": "",
                "tag": [],
                "field_type": "",
                "constr": [
                    {
                        "type": "",
                        "param": ""
                    }
                ],
                "required": 0,
                "cat": ""
            }
        }
    ]
}

```

3. check if exists a vocabulary for the extracted semantic contexts and get the vocabularies path

4. create the parameters for the spark job

```scala


sealed trait Transformation
//trasnform all the data to UTF-8
case object Utf8 extends Transformation
//transform all the empty strings to null
case object EmptyToNull extends Transformation
case object DateToISO8601 extends Transformation
case class NormalizeUrls(columns: List[String]) extends Transformation
case class VocabularyCheck(column: String, vocabularyPath: String) extends Transformation
case class Address(column: String) extends Transformation

case object UniqueRowId extends Transformation
case object GenDateTime extends Transformation
case object UpdateDateTime extends Transformation
case class Unknown(value: String) extends Transformation
```

5. call the spark job processor passing the following parameters