package my.weekly.model.poi;

import lombok.Getter;
import lombok.Setter;
import my.weekly.common.pub.MyManagerException;
import my.weekly.model.BaseModel;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.InputStream;

public class PoiReadExcelInfo extends BaseModel {

    @Getter
    @Setter
    private InputStream inputStream;
    @Getter
    @Setter
    private String fileName;
    @Getter
    @Setter
    private int sheetNo = 0;//从0开始
    @Getter
    @Setter
    private int rowNo = 0;//从0开始

    public static void validateInfo(PoiReadExcelInfo info) throws MyManagerException {
        Assert.isNull(info, "PoiExcelInfo不能为空");
        if(info.getInputStream() == null)
            throw new MyManagerException("待读取文件不能为空");
        if(!StringUtils.hasText(info.getFileName()))
            throw new MyManagerException("待读取文件文件名不能为空");
        if(info.getSheetNo() < 0)
            info.setSheetNo(0);
        if(info.getRowNo() < 0)
            info.setRowNo(0);
    }
}
