#CHECHER C-114E

#usable subject controller
curl --header "Content-Type: application/json" -X POST --data '{"name":"OOP","status":"FIRST"}' http//localhost:8080/subjects
curl --header "Content-Type: application/json" -X POST --data '{"name":"IP","status":"FIRST"}' http//localhost:8080/subjects

in browser http://localhost:8080/subjects
curl -X DELETE http://localhost:8080/subjects/2
curl --header "Content-Type: application/json" -X PUT --data 'IA' http://localhost:8080/subjects/1
