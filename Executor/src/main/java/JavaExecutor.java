import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class JavaExecutor {
    private static final Logger log = LoggerFactory.getLogger(JavaExecutor.class);
    private static  final String pathPart="Executor/src/main/resources/temp_zip/";
    public static void main(String[] args) {

        KafkaConsumer<String,String> consumer = UtilsKafka.setConsumerProps("java", "localhost:29092");

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
                    Utils.runProcess("wsl.exe touch "+pathPart+"isDiff");
                    System.out.println("**********");
                    Utils.runProcess("wsl.exe javac "+pathPart+"homework.java");
                    System.out.println("**********");

                    String outFileName= Utils.evaluate(testNmb, "evaluate-java.sh");
                    UtilsKafka.uploadResults(outFileName,sender,"sheets");

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