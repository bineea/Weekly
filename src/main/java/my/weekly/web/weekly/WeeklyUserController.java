package my.weekly.web.weekly;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.User;
import my.weekly.manager.LoginHelper;
import my.weekly.manager.acl.UserManager;
import my.weekly.model.MySession;
import my.weekly.model.acl.UserInfoModel;
import my.weekly.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("weekly/")
public class WeeklyUserController extends AbstractController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/modPasswd", method = RequestMethod.GET)
    public String modPasswdGet(@ModelAttribute("userInfoModel") UserInfoModel userInfoModel, Model model,
                               HttpServletRequest request) {
        return "";
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
    public String modProfileGet() {
        return "";
    }

    @RequestMapping(value = "/modProfile", method = RequestMethod.POST)
    public void modProfilePost() {

    }
}
