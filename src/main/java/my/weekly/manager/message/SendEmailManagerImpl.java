package my.weekly.manager.message;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.MailAttachment;
import my.weekly.dao.repo.jpa.MailAttachmentRepo;
import my.weekly.manager.AbstractManager;
import my.weekly.model.message.SendEmailInfo;
import my.weekly.model.message.SendEmailResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

@Service
public class SendEmailManagerImpl extends AbstractManager implements SendEmailManager {

    @Autowired
    private MailAttachmentRepo mailAttachmentRepo;

    // TODO 尚未实现邮件模板
    @Override
    public void sendEmailHandler(SendEmailInfo sendEmailInfo) throws IOException, MessagingException, SQLException, MyManagerException {
        SendEmailInfo.validateInfo(sendEmailInfo);
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        senderImpl.setHost(sendEmailInfo.getConfType().getHost());
        senderImpl.setPort(sendEmailInfo.getConfType().getPort());
        senderImpl.setProtocol(sendEmailInfo.getConfType().getProtocol());
        senderImpl.setUsername(sendEmailInfo.getAccount() + sendEmailInfo.getConfType().getSuffix());
        senderImpl.setPassword(sendEmailInfo.getPasswd());
        senderImpl.setJavaMailProperties(props);
        MimeMessage mimeMessge = senderImpl.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessge,true);
        mimeMessageHelper.setFrom(sendEmailInfo.getAccount());
        mimeMessageHelper.setTo(sendEmailInfo.getRecipientList().toArray(new String[] {}));
        mimeMessageHelper.setSubject(sendEmailInfo.getSubject());
        mimeMessageHelper.setText(sendEmailInfo.getContent(),true);
        if(!CollectionUtils.isEmpty(sendEmailInfo.getFiles())) {
            for(File file : sendEmailInfo.getFiles())
            {
                if(file.exists() && file.isFile()) {
                    String fileName = MimeUtility.encodeText(file.getName(), "utf-8", null);
                    mimeMessageHelper.addAttachment(fileName,file);
                }
            }
        }
        if(!CollectionUtils.isEmpty(sendEmailInfo.getMailAttachmentIds())) {
            for(String fileId : sendEmailInfo.getMailAttachmentIds())
            {
                Optional<MailAttachment> mailAttachmentOpt = mailAttachmentRepo.findById(fileId);
                if(mailAttachmentOpt.isPresent()) {
                    String fileName = MimeUtility.encodeText(mailAttachmentOpt.get().getName(), "utf-8", null);
                    mimeMessageHelper.addAttachment(fileName,new InputStreamResource(mailAttachmentOpt.get().getFile().getBinaryStream()));
                }
            }
        }
        if(!CollectionUtils.isEmpty(sendEmailInfo.getMultipartFiles())) {
            for(MultipartFile multipartFile : sendEmailInfo.getMultipartFiles())
            {
                String fileName = MimeUtility.encodeText(multipartFile.getName(), "utf-8", null);
                mimeMessageHelper.addAttachment(fileName,new InputStreamResource(multipartFile.getInputStream()));
            }
        }
        senderImpl.send(mimeMessge);
    }

    @Override
    public SendEmailResult saveSendEmailByInfo(SendEmailInfo sendEmailInfo, HttpServletRequest request) {
        return null;
    }
}
