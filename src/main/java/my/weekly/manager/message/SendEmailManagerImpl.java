package my.weekly.manager.message;

import my.weekly.common.pub.MyManagerException;
import my.weekly.manager.AbstractManager;
import my.weekly.model.message.SendEmailInfo;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class SendEmailManagerImpl extends AbstractManager implements SendEmailManager {

    // TODO 尚未实现邮件模板
    @Override
    public void sendEmailHandler(SendEmailInfo sendEmailInfo) throws MyManagerException, MessagingException, UnsupportedEncodingException {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        senderImpl.setHost(sendEmailInfo.getConfType().getHost());
        senderImpl.setPort(sendEmailInfo.getConfType().getPort());
        senderImpl.setProtocol(sendEmailInfo.getConfType().getProtocol());
        senderImpl.setUsername(sendEmailInfo.getAccount());
        senderImpl.setPassword(sendEmailInfo.getPasswd());
        senderImpl.setJavaMailProperties(props);
        MimeMessage mimeMessge = senderImpl.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessge,true);
        mimeMessageHelper.setFrom(sendEmailInfo.getAccount());
        mimeMessageHelper.setTo(sendEmailInfo.getRecipientList().toArray(new String[] {}));
        mimeMessageHelper.setSubject(sendEmailInfo.getSubject());
        mimeMessageHelper.setText(sendEmailInfo.getContent(),true);
        if(!CollectionUtils.isEmpty(sendEmailInfo.getAnnexList()))
        {
            for(File file : sendEmailInfo.getAnnexList())
            {
                String fileName = MimeUtility.encodeText(file.getName(), "utf-8", null);
                mimeMessageHelper.addAttachment(fileName,file);
            }
        }
        senderImpl.send(mimeMessge);
    }
}
