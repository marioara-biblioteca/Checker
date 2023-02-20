package kakfka_demo;

import kakfka_demo.db.DbService;
import kakfka_demo.email.service.EmailService;
import kakfka_demo.xlsx.schema.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerEmail {
    private final EmailService emailService;
    private final DbService dbService;
    @Autowired
    public KafkaListenerEmail(EmailService emailService, DbService dbService) {
        this.emailService = emailService;
        this.dbService = dbService;
    }
    @KafkaListener(topics="email")
    public  void listen(Document document){
        emailService.sendMail(document.getOwner(), "Test Subjec2", "Test mail");
        System.out.println("Email succesfully sent from class EmailListener!");
        dbService.saveResultToDb(document.getSourceLink(),document.getResultLink());
        System.out.println("Result successfully updated into db from class EmailListener!");
    }

}
