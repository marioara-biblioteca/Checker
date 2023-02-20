import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Arrays;
import java.util.Properties;

public class UtilsKafka {
    static  int counter=0;
    private static JSONObject createPublisherMessage(String[] sender, String fileName){
        JSONObject fileInfoJson = new JSONObject();
        fileInfoJson.put("userEmail", sender[0]);
        fileInfoJson.put("sourceLink", sender[1]);
      //  fileInfoJson.put("resultLink", sender[2]);
        try {
            fileInfoJson.put("resultLink", UtilsMinIO.getUrlsFromMinIO(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileInfoJson;
    }
    public static void setProps(Properties properties, String bootstrapServers){
        String groupId = "testGroup";

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }
    public static KafkaConsumer<String, String> setConsumerProps(String topic, String bootstrapServers){
        topic = topic + "-topic";
        // create consumer configs
        Properties properties = new Properties();

        setProps(properties, bootstrapServers);

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // subscribe consumer to our topic(s)
        consumer.subscribe(Arrays.asList(topic));

        return consumer;
    }
    private static void sendToPublisher(JSONObject fileInfoJson){
        String bootstrapServer = "localhost:29095";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> producerRecord_xlsx =
                new ProducerRecord<>("xlsx", fileInfoJson.toJSONString());
        ProducerRecord<String, String> producerRecord_email =
                new ProducerRecord<>("email", fileInfoJson.toJSONString());
        ProducerRecord<String, String> producerRecord_sheets =
                new ProducerRecord<>("sheets", fileInfoJson.toJSONString());
        // send data - asynchronous
        producer.send(producerRecord_xlsx);
        producer.send(producerRecord_email);
        producer.send(producerRecord_sheets);
        // flush data - synchronous
        producer.flush();
        //flush and close producer
        producer.close();
    }
    public static void uploadResults(String fileName,String[] sender,String publisherTopic){

       // final String fileName = "results";
        try{
            UtilsMinIO.writeToMinIO( fileName);
            JSONObject fileInfoJson = createPublisherMessage(sender,fileName);
            sendToPublisher(fileInfoJson);
            System.out.println(fileInfoJson.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String[] processMessage(String jsonContent){
        JSONParser parser = new JSONParser();

        final String sourceLink;
        final String testLink;
        final String userEmail;
        final String resultLink="";

        String[] sender = new String[3];
        try {
            Object obj = parser.parse(jsonContent);
            JSONObject jsonObj = (JSONObject) obj;

            userEmail = (String)jsonObj.get("userEmail");
            sourceLink = (String)jsonObj.get("sourceLink");
            testLink = (String)jsonObj.get("testLink");
            String newsStr="'" +sourceLink+"'";
            //obtinem continutul fisierelor local si le dezipam
            UtilsMinIO.readFromMinIOLink(newsStr,"testFileSource.zip");
            UtilsMinIO.readFromMinIOLink("'" +testLink+"'","testFileTest.zip");

            sender[0]=userEmail;
            sender[1]=sourceLink;
            sender[2]=resultLink;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return sender;
    }

}
