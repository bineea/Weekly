package my.weekly.model.poi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.weekly.common.pub.MyManagerException;
import my.weekly.model.BaseModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

public class PoiWriteExcelInfo extends BaseModel {

    @Getter
    @Setter
    private String templetName;
    @Getter
    @Setter
    private int sheetNo;
    @Getter
    @Setter
    private int rowNo;
    @Getter
    @Setter
    private String fileName;
    @Getter
    @Setter
    private String savePath;

    public static void validateInfo(PoiWriteExcelInfo info) throws MyManagerException, IOException {
        Assert.notNull(info, "PoiWriteExcelInfo不能为空");
        if(!StringUtils.hasText(info.getTempletName()))
            throw new MyManagerException("Excel模板文件不能为空");
        File templet = new ClassPathResource("templet/excel/"+info.getTempletName()).getFile();
        if(!templet.exists())
            throw new MyManagerException("Excel模板文件不存在");
        if(!StringUtils.hasText(info.getFileName()))
            throw new MyManagerException("文件名称不能为空");
        if(!StringUtils.hasText(info.getSavePath()))
            throw new MyManagerException("文件保存路径不能为空");
        File toSave = new File(info.getSavePath());
        if(!toSave.exists()) {
            toSave.mkdirs();
        }
        if(!toSave.isDirectory())
            throw new MyManagerException("设置的文件保存路径"+info.getSavePath()+"不是有效的文件路径");
        if(info.getSheetNo() < 0)
            info.setSheetNo(0);
        if(info.getRowNo() < 0)
            info.setRowNo(0);
    }
}
