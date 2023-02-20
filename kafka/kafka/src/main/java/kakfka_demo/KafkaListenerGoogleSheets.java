package kakfka_demo;

import kakfka_demo.db.DbService;
import kakfka_demo.googlesheets.dto.GoogleSheetDTO;
import kakfka_demo.googlesheets.dto.GoogleSheetResponseDTO;
import kakfka_demo.googlesheets.service.GoogleApiService;
import kakfka_demo.xlsx.schema.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class KafkaListenerGoogleSheets {
    private final GoogleApiService googleApiService;
    private final DbService dbService;

    @Autowired
    public KafkaListenerGoogleSheets(GoogleApiService googleApiService, DbService dbService) {
        this.googleApiService = googleApiService;
        this.dbService = dbService;
    }
    @KafkaListener(topics="sheets")
    public  void listen(Document document) throws GeneralSecurityException, IOException {
       // GoogleSheetDTO request=new GoogleSheetDTO(document.getName());
        GoogleSheetDTO request=new GoogleSheetDTO("Test");
        GoogleSheetResponseDTO response = googleApiService.createSheet(request);
        System.out.println("Created google sheets with id: "
                + response.getSpreadSheetId() +" and url: "
                +response.getSpreadSheetUrl());
        dbService.saveResultToDb(document.getSourceLink(), document.getResultLink());
        System.out.println("Result successfully updated into db from class GoogleSheets!");
    }
}
