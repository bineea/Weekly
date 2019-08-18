package my.weekly.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.User;
import my.weekly.manager.LoginHelper;
import my.weekly.manager.acl.UserManager;
import my.weekly.model.MySession;
import my.weekly.model.acl.UserInfoModel;

/**
 * @author Administrator
 * 个人信息
 */
@Controller
public class ProfileController extends AbstractController {
	@Autowired
	private UserManager userManager;

	@RequestMapping(value = "/modPasswd", method = RequestMethod.GET)
	public String modPasswdGet(@ModelAttribute("userInfoModel") UserInfoModel userInfoModel, Model model,
			HttpServletRequest request) {
		return "manage/modPasswd";
	}
	
	@RequestMapping(value = "/modPasswd", method = RequestMethod.POST)
	public void modPasswdPost(@ModelAttribute("userInfoModel") UserInfoModel userInfoModel, 
			HttpServletResponse response, HttpServletRequest request) throws IOException, MyManagerException {
		userInfoModel.setUserId(LoginHelper.getLoginUser(request).getId());
		User user = userManager.updatePasswd(userInfoModel);
		WebUtils.setSessionAttribute(request, MySession.LOGIN_USER, user.toJson());
		addSuccess(response, "成功重置密码");
	}
	
	@RequestMapping(value = "/modProfile", method = RequestMethod.GET)
	public String modProfileGet(@ModelAttribute("userInfoModel") UserInfoModel userInfoModel, Model model,
			HttpServletRequest request) {
		User user = LoginHelper.getLoginUser(request);
		model.addAttribute("userId", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("male", user.getMale());
		return "manage/modProfile";
	}
	
	@RequestMapping(value = "/modProfile", method = RequestMethod.POST)
	public void modProfilePost(@ModelAttribute("userInfoModel") UserInfoModel userInfoModel, Model model,
			HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {
		User user = userManager.updateProfile(userInfoModel);
		WebUtils.setSessionAttribute(request, MySession.LOGIN_USER, user.toJson());
		addSuccess(response, "成功修改个人信息");
	}
	
	@RequestMapping(value = "/modProfilePic", method = RequestMethod.POST)
	public void modProfilePic(@RequestParam("profilePic") MultipartFile profilePic, Model model, 
			HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {
		User user = LoginHelper.getLoginUser(request);
		userManager.updateProfilePic(user.getId(), profilePic);
		addSuccess(response, "成功修改头像");
	}
}
