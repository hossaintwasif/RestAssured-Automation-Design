package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtilities {

    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    String path;

    public XLUtilities(String path) {
        this.path = path;
    }

    // Method to get the total number of rows in a sheet
    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        workbook.close();
        fi.close();
        return rowCount;
    }

    // Method to get the total number of cells in a row
    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellCount = row.getLastCellNum();
        workbook.close();
        fi.close();
        return cellCount;
    }

    // Method to get cell data as a string
    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        String data;
        try {
            if (cell.getCellType() == CellType.STRING) {
                data = cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                data = String.valueOf(cell.getNumericCellValue());
            } else {
                data = "";
            }
        } catch (Exception e) {
            data = "";
        }

        workbook.close();
        fi.close();
        return data;
    }

    // Method to set data in a cell
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }

        cell = row.getCell(colnum);
        if (cell == null) {
            cell = row.createCell(colnum);
        }

        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }

    // Method to check the type of a cell (for validation)
    public CellType getCellType(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);
        CellType type = cell.getCellType();
        workbook.close();
        fi.close();
        return type;
    }
}
