Show tables:
GET http://127.0.0.1:8400/proxy/v1/hive/tables?table=a
Request:
 - table=table_name
Response: show_tables_response.json

Analyze table
GET http://127.0.0.1:8400/proxy/v1/hive/schemas/{DB_NAME}/tables/{TABLE_NAME}
Request:
 - {DB_NAME} = Database
 - {TABLE_NAME} = Table
Response:

Transform
POST http://127.0.0.1:8400/proxy/v1/spark/shell/transform
First Request: transform_request.json
First Response: transform_response.json
Second Request (with function): transform_second_request.json
Second Response (with function): transform_second_response.json
Third Request (with function): transform_third_request.json
Third Response (with function): transform_third_response.json

```
the field parent.script is the union of all previous script field without "\ndf = df.limit(1000)\ndf\n"
```

Status transformation
GET http://127.0.0.1:8400/proxy/v1/spark/shell/transform/{ID}
Request:
  - {ID} is present on the response of the transform call. The id change for every request
  ```
  "status": "PENDING",
  "table": "d7eb12de8aca4e3b8948056b5c775b0c"
  ```
Response: transform_pending_response.json
```
{
  "progress": 0.0, // It goes from 0 to 1
  "status": "PENDING", // It becames complet when progress is 1
  "table": "af0ca62f0a75427aa0685f9d49e8c932"
}
```

List Kylo Spark funtions
GET http://127.0.0.1:8400/api/v1/ui/spark-functions
Request: NONE
Response: list_spark_function_response.json

Create transformation
POST http://127.0.0.1:8400/proxy/v1/feedmgr/feeds
Request: feed_creation_request.json
Response: feed_creation_response.json