# Daf Prestandardization

The goal of this component is to create the methods call plu the need parameters to run the standardization process.

The process is composed by 4 steps:

1. to retrieve the list of the steps to apply from  `Operational.ingestion_pipeline`
```yaml

  ingestion_pipeline:
    type: array
    description: List of ingestion pipeline to be applied, in order of declaration, to the data to be ingested.
    items:
      type: string
```

2. to get all the fields of the dataset plus the semantic annotations from `flatschema`.

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

3. based on the transformation to execute fill the need paramaters

4. to create an instance of the `Transformations` class with all the information. 

