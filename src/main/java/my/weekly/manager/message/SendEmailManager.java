package my.weekly.manager.message;

import my.weekly.common.pub.MyManagerException;
import my.weekly.model.message.SendEmailInfo;
import my.weekly.model.message.SendEmailResult;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface SendEmailManager {

    /**
     * 发送邮件
     * @param sendEmailInfo
     * @throws MyManagerException
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @throws SQLException
     * @throws IOException
     */
    void sendEmailHandler(SendEmailInfo sendEmailInfo) throws MyManagerException, MessagingException, UnsupportedEncodingException, SQLException, IOException;

    /**
     * 保存邮件发送信息
     * @param sendEmailInfo
     * @param request
     * @return
     */
    SendEmailResult saveSendEmailByInfo(SendEmailInfo sendEmailInfo, HttpServletRequest request);
}
