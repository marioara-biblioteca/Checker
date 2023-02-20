package kakfka_demo.config;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.context.annotation.Bean;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic xlsxTopic(){
        return TopicBuilder.name("xlsxs")
                .build();
    }
//    @Bean NewTopic dbTopic(){
//        return TopicBuilder.name("dbs")
//                .build();
//    }
    @Bean NewTopic emailTopic(){
        return TopicBuilder.name("emails")
                .build();
    }
    @Bean NewTopic sheetsTopic(){
        return TopicBuilder.name("sheetss")
                .build();
    }
}
