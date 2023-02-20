package kakfka_demo.googlesheets.dto;

public class GoogleSheetResponseDTO {
    private String spreadSheetId;
    private String spreadSheetUrl;

    public String getSpreadSheetId() {
        return spreadSheetId;
    }

    public void setSpreadSheetId(String spreadSheetId) {
        this.spreadSheetId = spreadSheetId;
    }

    public String getSpreadSheetUrl() {
        return spreadSheetUrl;
    }

    public void setSpreadSheetUrl(String spreadSheetUrl) {
        this.spreadSheetUrl = spreadSheetUrl;
    }
}
