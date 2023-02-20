package kakfka_demo;

import kakfka_demo.xlsx.reader.CustomerFileReader;
import kakfka_demo.xlsx.schema.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.context.annotation.Bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication

public class KakfkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakfkaDemoApplication.class, args);
	}
	/*@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, Document> kafkaTemplate){

		//Producer de test: citeste din fisierul data.json, creaza obiectele de tip document si le trimite mai departe la listeneri
		//am avut nevoie de un switch case dupa un field topic in Producer ca sa stiu pe ce topic trimit
		Document[] documents= CustomerFileReader.getInstance().getCustomers();
		return args->{
			for(Document document: documents){
				String topic="email";
				//switch(document.getTopic())
				switch(topic)
				{
				case "xlsx":
					kafkaTemplate.send("xlsx",document);
					//kafkaTemplate.send("db",document);
					break;
				case "email":
					kafkaTemplate.send("email",document );
					//kafkaTemplate.send("db",document);
					break;
				case "sheets":
					kafkaTemplate.send("sheets",document);
					//kafkaTemplate.send("db",document);
					break;
				default:
					//kafkaTemplate.send("db",document );
					break;
			}
		}

		};
	}*/

}
