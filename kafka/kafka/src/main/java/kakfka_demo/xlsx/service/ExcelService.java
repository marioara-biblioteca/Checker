package kakfka_demo.xlsx.service;


import kakfka_demo.xlsx.schema.Document;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
@Service
public class ExcelService {
    public ExcelService() {
    }

    public  void writeToExcelFile(Document document) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        createWorkbookFromAttributes(workbook,document);

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        //String fileLocation = path.substring(0, path.length() - 1) + document.getName()+".xlsx";
        String fileLocation = path.substring(0, path.length() - 1) + "Test"+".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();

    }
    private void createWorkbookFromAttributes(Workbook workbook,Document document) {
//        Sheet sheet = workbook.createSheet(document.getName() + " Sheet");
        Sheet sheet = workbook.createSheet("Test " + " Sheet");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Owner name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Document content");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);//cream randul 2 (randul 1 e lasat liber intre headings si content) pe care punem prima celula numele si in a doua continutul
        Cell cell = row.createCell(0);
        cell.setCellValue(document.getOwner());
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(document.getSourceLink());
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue(document.getResultLink());
        cell.setCellStyle(style);
    }

    public Workbook readFromExcelFile(String inFile) throws FileNotFoundException, IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + inFile;

        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for (Cell cell : row) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    data.get(i).add(cell.getDateCellValue() + "");
                } else {
                    switch (cell.getCellType()) {
                        case STRING:
                            data.get(new Integer(i)).add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            data.get(new Integer(i)).add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case BOOLEAN:
                            data.get(new Integer(i)).add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case FORMULA:
                            data.get(new Integer(i)).add(cell.getCellFormula() + "");
                            break;

                        default:
                            data.get(new Integer(i)).add(" ");
                    }
                }
            }
            i++;
        }
        return workbook;

    }


}