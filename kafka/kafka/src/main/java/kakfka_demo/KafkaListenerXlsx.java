package kakfka_demo;

import kakfka_demo.db.DbService;
import kakfka_demo.xlsx.schema.Document;
import kakfka_demo.xlsx.service.ExcelService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaListenerXlsx {
    private final ExcelService excelHandler;
    private final DbService dbService;

    public KafkaListenerXlsx(ExcelService excelHandler, DbService dbService) {
        this.excelHandler = excelHandler;
        this.dbService = dbService;
    }
    @KafkaListener(topics="xlsx")
    public  void listen(Document document) {
        try {
            excelHandler.writeToExcelFile(document);
            System.out.println("Document successfully saved from XlsxListener!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dbService.saveResultToDb(document.getSourceLink(), document.getResultLink());
        System.out.println("Result successfully updated into db from XlsxListener!!");

    }
}
