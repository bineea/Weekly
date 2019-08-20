package my.weekly.manager.poi;

import my.weekly.common.pub.MyManagerException;
import my.weekly.model.poi.PoiWriteExcelInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface AbstractGenerateExcelManager<E> {

    default File generateExcel(PoiWriteExcelInfo info, List<E> dataList) throws MyManagerException, IOException {
        FileOutputStream outputStream = null;
        File f = null;
        XSSFWorkbook workbook = null;
        try {
            PoiWriteExcelInfo.validateInfo(info);
            f = new File(info.getSavePath() + File.separator + info.getFileName());
            outputStream = new FileOutputStream(f);
            workbook = new XSSFWorkbook(new ClassPathResource("templet/excel/"+info.getTempletName()).getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(info.getSheetNo());
            int rno = 0;
            for(E e : dataList) {
                Row row = sheet.getRow(info.getRowNo() + rno);
                fillData(row, e);
                rno++;
            }
            workbook.write(outputStream);
        } finally {
            if(outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            if(workbook != null) {
                workbook.close();
            }
        }
        return f;
    }

    default Row getRow(XSSFSheet sheet, int row) {
        Row sheetRow = sheet.getRow(row);
        if (sheetRow == null) sheetRow = sheet.createRow(row);
        return sheetRow;
    }

    default Cell getCell(Row sheetRow, int col) {
        Cell cell = sheetRow.getCell(col);
        if (cell == null) cell = sheetRow.createCell(col);
        return cell;
    }

    public abstract PoiWriteExcelInfo initWriteInfo();

    public abstract void fillData(Row row, E e);
}
