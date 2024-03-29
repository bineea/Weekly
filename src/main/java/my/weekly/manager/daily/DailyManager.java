package my.weekly.manager.daily;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import my.weekly.dao.entity.MailAttachment;
import my.weekly.model.message.SendEmailInfo;
import my.weekly.model.message.SendEmailResult;
import my.weekly.model.weekly.CategoryModel;
import my.weekly.model.weekly.WeeklyModel;
import org.springframework.data.domain.Page;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Daily;
import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.model.weekly.DailyModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

public interface DailyManager {

	/**分页查询
	 * @param spe
	 * @return
	 */
	Page<Daily> pageQuery(WeeklyDailyPageSpe spe);
	
	/**通过id查询
	 * @param id
	 * @return
	 */
	Daily findById(String id);
	
	/**创建日报
	 * @param dailyModel
	 * @return
	 */
	Daily createDaily(DailyModel dailyModel, HttpServletRequest request) throws MyManagerException;
	
	/**修改日报
	 * @param dailyModel
	 * @param request
	 * @return
	 * @throws MyManagerException
	 */
	Daily modifyDaily(DailyModel dailyModel, HttpServletRequest request) throws MyManagerException;
	
	/**删除日报
	 * @param id
	 * @param request
	 * @throws MyManagerException
	 */
	void del(String id, HttpServletRequest request) throws MyManagerException;

	/**创建周报
	 *
	 * @throws MyManagerException
	 */
	MailAttachment combine(WeeklyModel model, HttpServletRequest request) throws MyManagerException, IOException;

	/**查询周报文件信息
	 * @param weeklyFileId
	 * @return
	 */
	MailAttachment findFileById(String weeklyFileId);

	/**发送周报邮件
	 * @return
	 */
	SendEmailResult weekly2SendEmail(SendEmailInfo info, HttpServletRequest request) throws MyManagerException, IOException, MessagingException, SQLException;

	/**
	 * 按照需求类型统计日报
	 * @return
	 */
	List<CategoryModel> categoryByDemand();

	/**
	 * 按照用户统计日报
	 * @return
	 */
	List<CategoryModel> categoryByUser();
}
