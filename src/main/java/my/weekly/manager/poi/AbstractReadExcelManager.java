package my.weekly.manager.poi;

import my.weekly.common.pub.MyManagerException;
import my.weekly.model.MyFinals;
import my.weekly.model.poi.PoiReadExcelInfo;
import my.weekly.model.poi.PoiWriteExcelInfo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

//FIXME 所有读取数据都放在内存中，导致大数据量EXCEL无法导入
//Excel抽象类
public interface AbstractReadExcelManager<E> {

    /**
     * 读取并处理Excel数据
     * @param info
     * @return
     * @throws MyManagerException
     * @throws IOException
     */
    default File readExcelData(PoiReadExcelInfo info) throws MyManagerException, IOException {
        List<E> dataList = new ArrayList<>();
        Map<Row, String> failedMap = new HashMap<>();
        Workbook workbook = null;
        try {
            workbook = createWorkbook(info);
            Sheet sheet = workbook.getSheetAt(info.getSheetNo());
            Iterator<Row> rowIterator = sheet.iterator();
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum < info.getRowNo())
                throw new MyManagerException("");
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if(row == null || row.getRowNum() < info.getRowNo())
                    continue;
                try
                {
                    E e = customValidata2Model(row);
                    checkData(e);
                    dataList.add(e);
                }
                catch (Exception ep)
                {
                    failedMap.put(row, ep.getMessage());
                }
            }
        } finally {
            info.getInputStream().close();
            workbook.close();
        }
        saveData(dataList);
        if(!CollectionUtils.isEmpty(failedMap))
        {
            PoiWriteExcelInfo writeInfo = initWriteInfo();
            return generateExcel(writeInfo, failedMap);
        }
        return null;
    }

    // 数据格式校验
    public abstract E customValidata2Model(Row row) throws MyManagerException;

    // 数据业务逻辑校验
    public abstract void checkData(E e) throws MyManagerException;

    // 保存数据
    public void saveData(List<E> dataList);

    // 初始化Excel读取信息
    public abstract PoiReadExcelInfo initReadInfo(InputStream inputStream);

    // 初始化Excel生成信息
    public abstract PoiWriteExcelInfo initWriteInfo();

    /**
     * 根据Excel类型获取对应的workbook
     * @param info
     * @return
     * @throws IOException
     * @throws MyManagerException
     */
    default Workbook createWorkbook(PoiReadExcelInfo info) throws IOException, MyManagerException {
        PoiReadExcelInfo.validateInfo(info);
        if(info.getFileName().toLowerCase().endsWith(MyFinals.EXCEL_XLSX)) {
            XSSFWorkbook workbook = new XSSFWorkbook(info.getInputStream());
            return workbook;
        } else {
            HSSFWorkbook workbook = new HSSFWorkbook(info.getInputStream());
            return workbook;
        }
    }

    // 错误数据生成文件
    default File generateExcel(PoiWriteExcelInfo info, Map<Row, String> failedMap) throws MyManagerException, IOException {
        FileOutputStream outputStream = null;
        File f = null;
        XSSFWorkbook workbook = null;
        try {
            PoiWriteExcelInfo.validateInfo(info);
            f = new File(info.getSavePath() + File.separator + info.getFileName());
            outputStream = new FileOutputStream(f);
            workbook = new XSSFWorkbook(new ClassPathResource("templet/"+info.getTempletName()).getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(info.getSheetNo());
            int rno = 0;
            for(Row row : failedMap.keySet()) {
                String msg = failedMap.get(row);
                Row r = getRow(sheet, info.getRowNo() + rno);
                Iterator<Cell> iterator = row.cellIterator();
                Cell c = getCell(r, 0);
                c.setCellValue(msg);
                int cno = 1;
                while (iterator.hasNext())
                {
                    c = getCell(r, cno);
                    Cell cell = iterator.next();
                    switch (cell.getCellType()) {
                        case NUMERIC : //数值类型 整数，小数，日期
                            c.setCellValue(cell.getNumericCellValue());
                            break;
                        case FORMULA: // 公式
                            c.setCellValue(cell.getCellFormula());
                            break;
                        case BOOLEAN: // 布尔值
                            c.setCellValue(cell.getBooleanCellValue());
                            break;
                        case STRING: // 字符串
                            c.setCellValue(cell.getStringCellValue());
                            break;
                        case ERROR: // 错误单元格
                            c.setCellValue(cell.getErrorCellValue());
                            break;
                        case BLANK: // 空值，但是有样式
                            c.setCellValue("");
                            break;
                        case _NONE: // 未知类型
                        default:
                            c.setCellValue("未知类型，无法读取数据");
                            XSSFCellStyle style = workbook.createCellStyle();
                            style.setFillBackgroundColor(IndexedColors.RED.getIndex());
                            Font font = workbook.createFont();
                            font.setColor(IndexedColors.BLACK.getIndex());
                            style.setFont(font);
                            c.setCellStyle(style);
                            break;
                    }
                    cno++;
                }
                rno++;
            }
            workbook.write(outputStream);
        } finally {
            outputStream.flush();
            outputStream.close();
            workbook.close();
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
}
