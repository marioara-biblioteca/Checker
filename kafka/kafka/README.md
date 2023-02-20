prima data se ruleaza aplicatia checker (varianta de pe branch-ul dockerize care contine UserDTO)
apoi bin/zookeeper-server-start.sh config/zookeeper.properties
apoi bin/kafka-server-start.sh config/server.properties
apoi aplicatia de kafka


eu trebuie sa primesc linkul sursa (functioneaza ca un id) unui rezultat pe care trebuie sa il updatez si un link catre rezultat
de cate ori modific clasa Document(protocolul de comunicare cu executorii kafka):
-in schema Document.json la properties mai adaug un camp
-in clasele DocumentSerializer si DocumentDeserializer

in application.properties am serverul si numele grupului
topicurile sunt descrise in topicconfig
CA SA MEARGA FLOW-UL CU UPDATE LA RESULT IN BD TREBUIE CA DOCUMENTUL SA AIBA UN SOURCE LINK DIN BAZA DE DATE, SA FIE UN REZULTAT EXISTENT DEJA
(in data.json in "sourceLink" trebuie pus ceva valid)

curl -X GET http://localhost:8080/kafka/{topicName}