package kakfka_demo.email.controller;


import kakfka_demo.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    @GetMapping(value = "/sendmail")
    public String sendmail() {
        emailService.sendMail("stratulatdragos2000@gmail.com", "Test Subjec22222222222222t", "Test mail");
        return "emailsent";
    }
    public void sendMailKafka(String toEmail){
        emailService.sendMail(toEmail, "Test Subjec22222222222222t", "Test mail");
    }
}