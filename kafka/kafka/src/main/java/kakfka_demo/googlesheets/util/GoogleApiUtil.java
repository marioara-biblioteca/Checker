package kakfka_demo.googlesheets.util;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import kakfka_demo.googlesheets.dto.GoogleSheetDTO;
import kakfka_demo.googlesheets.dto.GoogleSheetResponseDTO;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;
@Component
public class GoogleApiUtil {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens/path";
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GoogleApiUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(System.getProperty("user.home"),TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */

    public Map<Object, Object>  getDataFromSheet() throws GeneralSecurityException, IOException {
        final String spreadsheetId = "12flkNFg4kaiKmqydjjR0UJHUPbXc2FV1qoFREJ4fB-Y";
        final String range = "A2:B25";
        Sheets service = getGoogleSheetService();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        Map<Object, Object> storeDataFromGoogleSheet = new HashMap<>();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                storeDataFromGoogleSheet.put(row.get(0),row.get(1));

            }
            return storeDataFromGoogleSheet;
        }
        return storeDataFromGoogleSheet;
    }

    private Sheets getGoogleSheetService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }

    private Drive getDriveService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }

//    public static void main(String... args) throws IOException, GeneralSecurityException {
//        // Build a new authorized API client service.
//
//    }



    public GoogleSheetResponseDTO createGoogleSheet(GoogleSheetDTO request) throws GeneralSecurityException, IOException {
        Sheets service = getGoogleSheetService();

        SpreadsheetProperties spreadsheetProperties = new SpreadsheetProperties();
        spreadsheetProperties.setTitle(request.getSheetName());


        SheetProperties sheetProperties = new SheetProperties();
        sheetProperties.setTitle(request.getSheetName());
        Sheet sheet = new Sheet().setProperties(sheetProperties);
        Spreadsheet spreadsheet = new Spreadsheet().setProperties(spreadsheetProperties).setSheets(Collections.singletonList(sheet));
        Spreadsheet createdResponse =  service.spreadsheets().create(spreadsheet).execute();
        GoogleSheetResponseDTO googleSheetResponseDTO = new GoogleSheetResponseDTO();
        ValueRange valueRange = new ValueRange().setValues(request.getDataToBeUpdated());
        service.spreadsheets().values().update(createdResponse.getSpreadsheetId(),"A1",valueRange).setValueInputOption("RAW").execute();
        googleSheetResponseDTO.setSpreadSheetId(createdResponse.getSpreadsheetId());
        googleSheetResponseDTO.setSpreadSheetUrl(createdResponse.getSpreadsheetUrl());

        Drive driveService = getDriveService();

        Permission newPermission = new Permission();
        newPermission.setType("anyone");
        newPermission.setRole("reader");
        driveService.permissions().create(createdResponse.getSpreadsheetId(), newPermission).execute();
        return googleSheetResponseDTO;

    }

}
