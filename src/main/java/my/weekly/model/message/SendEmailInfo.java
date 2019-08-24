package my.weekly.model.message;

import lombok.Getter;
import lombok.Setter;
import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.dict.EmailConfType;
import my.weekly.model.BaseModel;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class SendEmailInfo extends BaseModel {
    @NotNull
    @Getter
    @Setter
    private EmailConfType confType;
    @NotBlank
    @Getter
    @Setter
    private String account; //账号
    @NotBlank
    @Getter
    @Setter
    private String passwd; //密码
    //TODO 收件地址格式校验
    @NotEmpty
    @Getter
    @Setter
    private List<String> recipients; //收件地址
    @Getter
    @Setter
    private String templet; //模板
    @Getter
    @Setter
    private List<File> files; //附件
    @Getter
    @Setter
    private List<String> mailAttachmentIds; //附件
    @Getter
    @Setter
    private List<MultipartFile> multipartFiles; //附件
    @NotBlank
    @Getter
    @Setter
    private String content; //内容
    @Getter
    @Setter
    private String subject; //主题

    public static void validateInfo(SendEmailInfo sendEmailInfo) throws MyManagerException {
        Assert.notNull(sendEmailInfo, "EmailInfo不能为空");
        if(sendEmailInfo.getConfType() == null)
            throw new MyManagerException("邮箱类型不能为空");
        if(!StringUtils.hasText(sendEmailInfo.getAccount()))
            throw new MyManagerException("账号不能为空");
        if(!StringUtils.hasText(sendEmailInfo.getPasswd()))
            throw new MyManagerException("密码不能为空");
        if(!StringUtils.hasText(sendEmailInfo.getContent()))
            throw new MyManagerException("邮件内容不能为空");
        if(CollectionUtils.isEmpty(sendEmailInfo.getRecipients()))
            throw new MyManagerException("收件地址不能为空");
        if(!CollectionUtils.isEmpty(sendEmailInfo.getFiles())) {
            for(File f : sendEmailInfo.getFiles()) {
                if(!f.exists() || !f.isFile())
                    throw new MyManagerException("附件信息异常");
            }
        }
    }
}
