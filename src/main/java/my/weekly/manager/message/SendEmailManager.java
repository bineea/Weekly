package my.weekly.manager.message;

import my.weekly.common.pub.MyManagerException;
import my.weekly.model.message.SendEmailInfo;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface SendEmailManager {

    void sendEmailHandler(SendEmailInfo sendEmailInfo) throws MyManagerException, MessagingException, UnsupportedEncodingException;
}
