package kakfka_demo.controller;

import kakfka_demo.xlsx.reader.CustomerFileReader;
import kakfka_demo.xlsx.schema.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    private KafkaTemplate<String, Document> kafkaTemplate;
    @GetMapping("/kafka/{topic}")
    public void sendToTopic(@PathVariable String topic){
        Document[] documents= CustomerFileReader.getInstance().getCustomers();
        kafkaTemplate.send(topic,documents[0]);
    }
}
