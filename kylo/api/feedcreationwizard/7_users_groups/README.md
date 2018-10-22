In this README, the 127.0.0.1:8400 must be replaced by the Kylo-UI url.

List Groups:
GET http://127.0.0.1:8400/proxy/v1/security/groups
Request: NONE
Response: response_list_groups.json

List Users:
GET http://127.0.0.1:8400/proxy/v1/security/users
Request: NONE
Response: response_list_users.json

Create Users:
POST http://127.0.0.1:8400/proxy/v1/security/users
Request: create_user_request.json
Response: create_user_response.json
