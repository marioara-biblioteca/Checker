package kakfka_demo.googlesheets.service;



import kakfka_demo.googlesheets.dto.GoogleSheetDTO;
import kakfka_demo.googlesheets.dto.GoogleSheetResponseDTO;
import kakfka_demo.googlesheets.util.GoogleApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Service
public class GoogleApiService {
    private final GoogleApiUtil googleApiUtil;
    @Autowired(required = true)
    public GoogleApiService(GoogleApiUtil googleApiUtil) {
        this.googleApiUtil = googleApiUtil;
    }

    public Map<Object, Object> readDataFromGoogleSheet() throws GeneralSecurityException, IOException {
        return googleApiUtil.getDataFromSheet();
    }

    public GoogleSheetResponseDTO createSheet(GoogleSheetDTO request) throws GeneralSecurityException, IOException {
        return googleApiUtil.createGoogleSheet(request);
    }
}
