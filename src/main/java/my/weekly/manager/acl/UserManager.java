package my.weekly.manager.acl;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.User;
import my.weekly.dao.entity.User.UserStatus;
import my.weekly.dao.repo.Spe.UserPageSpe;
import my.weekly.model.acl.UserInfoModel;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

public interface UserManager {

	/**
	 * 用户登录
	 * @param userInfoModel
	 * @return
	 * @throws MyManagerException
	 */
	User toLogin(UserInfoModel userInfoModel) throws MyManagerException;
	
	/**
	 * 分页查询
	 * @param spe
	 * @return
	 */
	Page<User> pageQuery(UserPageSpe spe);
	
	/**
	 * 添加用户
	 * @param model
	 * @return
	 */
	User add(UserInfoModel model) throws MyManagerException, IOException;
	
	/**
	 * 通过id查询用户
	 * @param id
	 * @return
	 */
	User findById(String id);
	
	/**
	 * 更新用户状态
	 * @param id
	 * @param status
	 * @return
	 * @throws MyManagerException
	 */
	User updateStatus(String id, UserStatus status) throws MyManagerException;
	
	/**
	 * 更新用户角色
	 * @param id
	 * @param roleId
	 * @return
	 * @throws MyManagerException
	 */
	User updateRole(String id, String roleId) throws MyManagerException;
	
	/**
	 * 更新用户信息
	 * @param model
	 * @return
	 * @throws MyManagerException
	 */
	User updateProfile(UserInfoModel model) throws MyManagerException;
	
	/**
	 * 修改密码
	 * @param model
	 * @return
	 * @throws MyManagerException
	 */
	User updatePasswd(UserInfoModel model) throws MyManagerException;
	
	/**
	 * 更新头像
	 * @param userId
	 * @param profilePic
	 * @return
	 */
	String updateProfilePic(String userId, MultipartFile profilePic) throws MyManagerException, IOException;

	/**
	 * 推送邮件验证码
	 * @param email
	 */
	void sendCheckCode(String email, HttpServletRequest request) throws MessagingException, SQLException, MyManagerException, IOException;
	
}
