package checker.webapp.kafka;

import net.minidev.json.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
public class KafkaProducerConfig {
    private static final String bootstrapServer = "localhost:29092";

    public void config(String programmingLanguage,String userEmail,String sourceLink,String testLink){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(programmingLanguage,
                        jsonize(userEmail,sourceLink,testLink).toJSONString());
                //new ProducerRecord<>("python-topic", jsonize().toJSONString());

        // send data - asynchronous
        producer.send(producerRecord);
        // flush data - synchronous
        producer.flush();
        //flush and close producer
        producer.close();

    }
    private JSONObject jsonize(String userEmail,String sourceLink,String testLink){
        JSONObject fileInfoJson = new JSONObject();

        fileInfoJson.put("userEmail",userEmail);
        fileInfoJson.put("sourceLink",sourceLink);
        fileInfoJson.put("testLink",testLink);

        return fileInfoJson;
    }
}
