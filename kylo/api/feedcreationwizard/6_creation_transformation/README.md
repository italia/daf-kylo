Table Schema:
GET http://tba-kylo-services.default.svc.cluster.local:8420/api/v1/hive/schemas/{DB_NAME}/tables/{TABLE_NAME}

Transform:
POST http://tba-kylo-services.default.svc.cluster.local:8420/api/v1/spark/shell/transform

Transform Status:
GET http://tba-kylo-services.default.svc.cluster.local:8420/api/v1/spark/shell/transform/{TEMP_TABLE_NAME}