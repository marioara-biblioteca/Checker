package kakfka_demo.xlsx.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import kakfka_demo.xlsx.schema.Document;

import java.io.File;

public class CustomerFileReader {
    private static final CustomerFileReader customerFileReaderInstance = new CustomerFileReader();

    public static CustomerFileReader getInstance() {
        return customerFileReaderInstance;
    }

    String DATAFILE = "src/main/resources/data/data.json";

    private Document[] documents;

    private CustomerFileReader() {
        final ObjectMapper mapper;
        mapper = new ObjectMapper();
        ;
        try {
            documents = mapper.readValue(new File(DATAFILE), Document[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Document[] getCustomers() {
        return documents;
    }
}
