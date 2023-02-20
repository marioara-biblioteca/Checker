import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class CppExecutor {
    private static final Logger log = LoggerFactory.getLogger(CppExecutor.class);
    private static  final String pathPart="Executor/src/main/resources/temp_zip/";
    public static void main(String[] args) {
        KafkaConsumer<String,String> consumer = UtilsKafka.setConsumerProps("c", "localhost:29092");

        while(true){
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                String jsonContent = record.value();
                String[] sender;
                sender = UtilsKafka.processMessage(jsonContent);
                long testNmb = Utils.countTests(pathPart+"inputs");

                try {
                    Utils.runProcess("wsl.exe pwd");
                    System.out.println("**********");
                    Utils.runProcess("wsl.exe gcc -o " + pathPart+"homework "+ pathPart+"homework.c");
                    System.out.println("**********");

                    String outFileName=Utils.evaluate(testNmb, "evaluate-cpp.sh");
                    UtilsKafka.uploadResults(outFileName,sender,"email");

                    Utils.cleanupFolder(pathPart);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                log.info("Key: " + record.key() + ", Value: " + record.value());
                log.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }
    }
}
