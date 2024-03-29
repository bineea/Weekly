package my.weekly.manager.acl;

import my.weekly.common.pub.MyManagerException;
import my.weekly.common.tools.MyTools;
import my.weekly.common.tools.SecurityHelper;
import my.weekly.common.tools.SecurityHelper.DigestType;
import my.weekly.dao.entity.Role;
import my.weekly.dao.entity.User;
import my.weekly.dao.entity.User.UserStatus;
import my.weekly.dao.entity.dict.EmailConfType;
import my.weekly.dao.repo.Spe.UserPageSpe;
import my.weekly.dao.repo.jpa.RoleRepo;
import my.weekly.dao.repo.jpa.UserRepo;
import my.weekly.manager.AbstractManager;
import my.weekly.manager.message.SendEmailManager;
import my.weekly.model.MyFinals;
import my.weekly.model.acl.UserInfoModel;
import my.weekly.model.message.SendEmailInfo;
import org.hibernate.engine.jdbc.NonContextualLobCreator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@Service
public class UserManagerImpl extends AbstractManager implements UserManager {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private SendEmailManager sendEmailManager;
	@Autowired
	private CheckCodeMapHolder checkCodeMapHolder;

	@Override
	public User toLogin(UserInfoModel userInfoModel) throws MyManagerException {
		
		if(!StringUtils.hasText(userInfoModel.getLoginName()))
			throw new MyManagerException("账号不能为空");
		if(!StringUtils.hasText(userInfoModel.getPasswd()))
			throw new MyManagerException("密码不能为空");
		String passwd = SecurityHelper.encryStr(userInfoModel.getPasswd(), DigestType.SHA_1);
		User user = userRepo.findByLoginNamePasswd(userInfoModel.getLoginName(), passwd);
		if(user == null)
			throw new MyManagerException("账号与密码不匹配");
		if(user.getStatus() != UserStatus.NORMAL)
			throw new MyManagerException("该用户已停用");
		return user;
	}

	@Override
	public Page<User> pageQuery(UserPageSpe spe) {
		return userRepo.findAll(spe.handleSpecification(), spe.getPageRequest());
	}

	@Override
	@Transient
	public User add(UserInfoModel model) throws MyManagerException, IOException {
		User user = initUserByModel(model);
		user.setPasswd(SecurityHelper.encryStr(User.DEFAULT_PWD, DigestType.SHA_1));
		userRepo.save(user);
		return user;
	}

	@Override
	public User findById(String id) {
		Assert.hasText(id, "id不能为空");
		return userRepo.findById(id).orElse(null);
	}

	@Override
	@Transient
	public User updateStatus(String id, UserStatus status) throws MyManagerException {
		User user = userRepo.findById(id).orElse(null);
		if(user == null)
			throw new MyManagerException("该用户信息不存在");
		if(user.getStatus() == status)
			throw new MyManagerException("该用户状态为【"+status.getValue()+"】，无需变更状态");
		user.setStatus(status);
		userRepo.save(user);
		return user;
	}

	@Override
	public User updateRole(String id, String roleId) throws MyManagerException {
		User user = userRepo.findById(id).orElse(null);
		if(user == null)
			throw new MyManagerException("该用户信息不存在");
		Role role = roleRepo.findById(roleId).orElse(null);
		if(role == null)
			throw new MyManagerException("该角色信息不存在");
		user.setRole(role);
		userRepo.save(user);
		return user;
	}

	@Override
	public User updateProfile(UserInfoModel model) throws MyManagerException {
		User user = userRepo.findById(model.getUserId()).orElse(null);
		if(user == null)
			throw new MyManagerException("用户信息不存在，请重新登录");
		profileInfoValid(model);
		User u = userRepo.findByEmailUnequalId(model.getEmail(), user.getId());
		if(u != null)
			throw new MyManagerException("该邮箱地址也被注册");
		user.setName(model.getName());
		user.setEmail(model.getEmail());
		user.setMale(model.getMale());
		userRepo.save(user);
		return user;
	}

	@Override
	public User updatePasswd(UserInfoModel model) throws MyManagerException {
		if(!StringUtils.hasText(model.getOldPasswd()))
			throw new MyManagerException("原密码不能为空");
		if(!StringUtils.hasText(model.getPasswd()))
			throw new MyManagerException("新密码不能为空");
		if(!StringUtils.hasText(model.getConfirmPw()))
			throw new MyManagerException("确认密码不能为空");
		if(!model.getPasswd().equals(model.getConfirmPw()))
			throw new MyManagerException("新密码输入不一致，请重新确认");
		User user = userRepo.findById(model.getUserId()).orElse(null);
		if(user == null)
			throw new MyManagerException("用户信息不存在，请重新登录");
		String oldPasswd = SecurityHelper.encryStr(model.getOldPasswd(), DigestType.SHA_1);
		if(!oldPasswd.equals(user.getPasswd()))
			throw new MyManagerException("原密码输入错误");
		user.setPasswd(SecurityHelper.encryStr(model.getPasswd(), DigestType.SHA_1));
		userRepo.save(user);
		return user;
	}
	
	private void userInfoValid(UserInfoModel model) throws MyManagerException {
		if(!StringUtils.hasText(model.getLoginName()))
			throw new MyManagerException("账号不能为空");
		if(!MyFinals.checkLoginNameFormat(model.getLoginName()))
			throw new MyManagerException("账号格式错误");
		profileInfoValid(model);
		User user = userRepo.findByLoginName(model.getLoginName());
		if(user != null)
			throw new MyManagerException("该账号【"+model.getLoginName()+"】已存在");
		user = userRepo.findByEmail(model.getEmail());
		if(user != null)
			throw new MyManagerException("该邮箱地址【"+model.getEmail()+"】已存在");
	}
	
	private void profileInfoValid(UserInfoModel model) throws MyManagerException {
		if(!StringUtils.hasText(model.getName()))
			throw new MyManagerException("昵称不能为空");
		if(!StringUtils.hasText(model.getEmail()))
			throw new MyManagerException("邮箱不能为空");
		if(!MyFinals.checkEmailFormat(model.getEmail()))
			throw new MyManagerException("邮箱格式错误");
		if(model.getName().length() > 25)
			throw new MyManagerException("昵称长度超长，最多25个字符");
	}

	@Override
	public String updateProfilePic(String userId, MultipartFile profilePic) throws MyManagerException, IOException {
		User user = userRepo.findById(userId).orElse(null);
		if(user == null)
			throw new MyManagerException("用户信息不存在，请重新登录");
		user.setProfilePicture(NonContextualLobCreator.INSTANCE.createBlob(profilePic.getBytes()));
		userRepo.save(user);
		return user.getId();
	}

	@Override
	public void sendCheckCode(String email, HttpServletRequest request) throws MessagingException, SQLException, MyManagerException, IOException {
		String checkCode = MyTools.randomNumCode(6);
		SendEmailInfo info = initCheckCodeEmailInfo(email, checkCode);
		sendEmailManager.sendEmailHandler(info);
		checkCodeMapHolder.handleCheckCode(email, checkCode, 5 * 60 *60);
	}

	@Transactional
	@Override
	public User register(UserInfoModel model) throws MyManagerException, IOException {
		if(!StringUtils.hasText(model.getPasswd()))
			throw new MyManagerException("新密码不能为空");
		if(!StringUtils.hasText(model.getConfirmPw()))
			throw new MyManagerException("确认密码不能为空");
		if(!model.getPasswd().equals(model.getConfirmPw()))
			throw new MyManagerException("新密码输入不一致，请重新确认");
		if(!StringUtils.hasText(model.getCheckCode()))
			throw new MyManagerException("随机码不能为空");
		if(!checkCodeMapHolder.checkCodeValidate(model.getEmail(), model.getCheckCode()))
			throw new MyManagerException("随机码无效，请重新获取");
		User user = initUserByModel(model);
		user.setPasswd(SecurityHelper.encryStr(model.getPasswd(), DigestType.SHA_1));
		user.setRole(roleRepo.findByName("default"));
		userRepo.save(user);
		return user;
	}

	private SendEmailInfo initCheckCodeEmailInfo(String email, String checkCode) {
		User user = userRepo.findByLoginName("admin");
		SendEmailInfo info = new SendEmailInfo();
		for(EmailConfType type : EmailConfType.values()) {
			if(user.getEmail().toLowerCase().endsWith(type.getSuffix().toLowerCase()))
				info.setConfType(type);
		}
		info.setAccount(user.getEmail().toLowerCase().replace(info.getConfType().getSuffix().toLowerCase(), ""));
		info.setSubject("账号注册验证码");
		info.setContent(checkCode);
		info.setPasswd("xsyexenkhcyybcjc");
		info.setRecipients(Arrays.asList(email));
		return info;
	}

	private User initUserByModel(UserInfoModel model) throws IOException, MyManagerException {
		userInfoValid(model);
		User user = new User();
		BeanUtils.copyProperties(model, user);
		user.setStatus(UserStatus.NORMAL);
		if(user.getProfilePicture() == null) {
			char firstChar = MyTools.handleStr2Spell(user.getName()).toCharArray()[0];
			File profilePic = new ServletContextResource(ContextLoader.getCurrentWebApplicationContext().getServletContext(), "/assets/img/profile/user-"+String.valueOf(firstChar)+".jpg").getFile();
			user.setProfilePicture(NonContextualLobCreator.INSTANCE.createBlob(new FileInputStream(profilePic), profilePic.length()));
		}
		return user;
	}
}
