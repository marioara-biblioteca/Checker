package kakfka_demo.googlesheets.controller;
import kakfka_demo.googlesheets.dto.GoogleSheetResponseDTO;
import kakfka_demo.googlesheets.dto.GoogleSheetDTO;
import kakfka_demo.googlesheets.service.GoogleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    private final GoogleApiService googleApiService;


    @Autowired
    public DashboardController(GoogleApiService googleApiService) {
        this.googleApiService = googleApiService;
    }

    @GetMapping("/check")
    public String check() {
        return "TEST";
    }

    @GetMapping("/data")
    public Map<Object, Object> readDataSheet() throws GeneralSecurityException, IOException {
        return googleApiService.readDataFromGoogleSheet();
    }

    @PostMapping("/createSheet")
    public GoogleSheetResponseDTO createGoogleSheet(@RequestBody GoogleSheetDTO request) throws GeneralSecurityException, IOException {
        return googleApiService.createSheet(request);
    }
}
