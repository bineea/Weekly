package my.weekly.manager.message;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.MailAttachment;
import my.weekly.dao.entity.SendEmail;
import my.weekly.dao.repo.jpa.MailAttachmentRepo;
import my.weekly.dao.repo.jpa.SendEmailRepo;
import my.weekly.manager.AbstractManager;
import my.weekly.manager.LoginHelper;
import my.weekly.model.message.SendEmailInfo;
import my.weekly.model.message.SendEmailResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class SendEmailManagerImpl extends AbstractManager implements SendEmailManager {

    @Autowired
    private SendEmailRepo sendEmailRepo;
    @Autowired
    private MailAttachmentRepo mailAttachmentRepo;

    // TODO 尚未实现邮件模板
    @Override
    public void sendEmailHandler(SendEmailInfo sendEmailInfo) throws IOException, MessagingException, SQLException, MyManagerException {
        try {
            SendEmailInfo.validateInfo(sendEmailInfo);
            JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.ssl.enable", sendEmailInfo.getConfType().isSslEnable());
            senderImpl.setHost(sendEmailInfo.getConfType().getHost());
            senderImpl.setPort(sendEmailInfo.getConfType().getPort());
            senderImpl.setProtocol(sendEmailInfo.getConfType().getProtocol());
            senderImpl.setUsername(sendEmailInfo.getConfType().isAcountHasSuffix() ? sendEmailInfo.getAccount() + sendEmailInfo.getConfType().getSuffix() : sendEmailInfo.getAccount());
            senderImpl.setPassword(sendEmailInfo.getPasswd());
            senderImpl.setJavaMailProperties(props);
            MimeMessage mimeMessge = senderImpl.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessge,true, "UTF-8");
            mimeMessageHelper.setFrom(sendEmailInfo.getAccount() + sendEmailInfo.getConfType().getSuffix());
            mimeMessageHelper.setTo(sendEmailInfo.getRecipients().toArray(new String[] {}));
            mimeMessageHelper.setSubject(sendEmailInfo.getSubject());
            mimeMessageHelper.setText(sendEmailInfo.getContent(),true);
            if(!CollectionUtils.isEmpty(sendEmailInfo.getFiles())) {
                for(File file : sendEmailInfo.getFiles())
                {
                    if(file.exists() && file.isFile()) {
                        String fileName = "=?UTF-8?B?" + Base64Utils.encodeToString(file.getName().getBytes("UTF-8")) + "?=";
                        mimeMessageHelper.addAttachment(fileName,file);
                    }
                }
            }
            if(!CollectionUtils.isEmpty(sendEmailInfo.getMailAttachmentIds())) {
                for(String fileId : sendEmailInfo.getMailAttachmentIds())
                {
                    Optional<MailAttachment> mailAttachmentOpt = mailAttachmentRepo.findById(fileId);
                    if(mailAttachmentOpt.isPresent()) {
                        String fileName = "=?UTF-8?B?" + Base64Utils.encodeToString(mailAttachmentOpt.get().getName().getBytes("UTF-8")) + "?=";
                        mimeMessageHelper.addAttachment(fileName,
                                new ByteArrayDataSource(mailAttachmentOpt.get().getFile().getBinaryStream(), mailAttachmentOpt.get().getMimeType()));
                    }
                }
            }
            if(!CollectionUtils.isEmpty(sendEmailInfo.getMultipartFiles())) {
                for(MultipartFile multipartFile : sendEmailInfo.getMultipartFiles())
                {
                    String fileName = "=?UTF-8?B?" + Base64Utils.encodeToString(multipartFile.getName().getBytes("UTF-8")) + "?=";
                    mimeMessageHelper.addAttachment(fileName,new InputStreamResource(multipartFile.getInputStream()));
                }
            }
            senderImpl.send(mimeMessge);
        } catch (MailAuthenticationException e) {
            e.printStackTrace();
            throw new MyManagerException("邮箱信息验证失败");
        }
    }

    @Transactional
    @Override
    public SendEmailResult saveSendEmailByInfo(SendEmailInfo sendEmailInfo, HttpServletRequest request) {
        SendEmailResult result = new SendEmailResult();
        SendEmail sendEmail = new SendEmail();
        List<MailAttachment> mailAttachmentList = new ArrayList<>();
        sendEmail.setAccount(sendEmailInfo.getAccount() + sendEmailInfo.getConfType().getSuffix());
        sendEmail.setSubject(sendEmailInfo.getSubject());
        sendEmail.setContent(sendEmailInfo.getContent());
        sendEmail.setRecipients(StringUtils.join(sendEmailInfo.getRecipients().toArray(), ","));
        sendEmail.setCreateTime(LocalDateTime.now());
        sendEmail.setUser(LoginHelper.getLoginUser(request));
        sendEmailRepo.save(sendEmail);
        if(!CollectionUtils.isEmpty(sendEmailInfo.getMailAttachmentIds())) {
            for(String fileId : sendEmailInfo.getMailAttachmentIds())
            {
                Optional<MailAttachment> mailAttachmentOpt = mailAttachmentRepo.findById(fileId);
                if(mailAttachmentOpt.isPresent()) {
                    mailAttachmentOpt.get().setSendEmail(sendEmail);
                    mailAttachmentRepo.save(mailAttachmentOpt.get());
                    mailAttachmentList.add(mailAttachmentOpt.get());
                }
            }
        }
        result.setSendEmail(sendEmail);
        result.setMailAttachmentList(mailAttachmentList);
        return result;
    }
}
