package org.example.helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;

public class ExcelHelpers {

    private FileInputStream fileIn;
    private FileOutputStream fileOut;
    private Workbook workbook;
    private Sheet sheet;
    private Cell cell;
    private Row row;
    private String excelFilePath;

    // Set Excel File to work
    public void setExcelFile(String excelPath, String sheetName) {
        try {
            File file = new File(excelPath);
            if (!file.exists()) {
                LogUtils.error("File Excel not found: " + excelPath);
                return;
            }
            fileIn = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fileIn);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                LogUtils.error("Sheet " + sheetName + " not found in file " + excelPath);
            }
            this.excelFilePath = excelPath;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    public String getCellData(int rowNum, int colNum) {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            DataFormatter formatter = new DataFormatter();

            return formatter.formatCellValue(cell);
        } catch (Exception e) {
            return "";
        }
    }

    public int getRows() {
        try {
            return sheet.getLastRowNum();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getColumns() {
        try {
            row = sheet.getRow(0);
            return row.getLastCellNum();
        } catch (Exception e) {
            return 0;
        }
    }

    // Get Data for DataProvider
    public Object[][] getDataHashTable(String excelPath, String sheetName, int startRow, int startCol) {
        LogUtils.info("Reading Excel file: " + excelPath + " | Sheet: " + sheetName);
        setExcelFile(excelPath, sheetName);

        int rowCount = getRows();
        int colCount = getColumns();
        int actualRows = rowCount - startRow + 1;

        if (actualRows <= 0) {
            return new Object[0][0];
        }

        Object[][] data = new Object[actualRows][1];
        Hashtable<String, String> table = null;

        for (int i = startRow; i <= rowCount; i++) {
            table = new Hashtable<>();
            for (int j = startCol; j < colCount; j++) {
                String header = getCellData(0, j);
                String value = getCellData(i, j);
                table.put(header, value);
            }
            data[i - startRow][0] = table;
        }
        return data;
    }
}