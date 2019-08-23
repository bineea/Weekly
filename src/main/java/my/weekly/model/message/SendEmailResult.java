package my.weekly.model.message;

import lombok.Getter;
import lombok.Setter;
import my.weekly.dao.entity.MailAttachment;
import my.weekly.dao.entity.SendEmail;
import my.weekly.model.BaseModel;

import java.util.List;

public class SendEmailResult extends BaseModel {
    @Getter
    @Setter
    private SendEmail sendEmail;
    @Getter
    @Setter
    private List<MailAttachment> mailAttachmentList;
}
