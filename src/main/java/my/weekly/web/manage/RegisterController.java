package my.weekly.web.manage;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.User;
import my.weekly.manager.LoginHelper;
import my.weekly.manager.acl.CheckCodeMapHolder;
import my.weekly.manager.acl.RoleResourceManager;
import my.weekly.manager.acl.UserManager;
import my.weekly.model.MySession;
import my.weekly.model.acl.UserInfoModel;
import my.weekly.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class RegisterController extends AbstractController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private RoleResourceManager roleResourceManager;
    @Autowired
    private CheckCodeMapHolder checkCodeMapHolder;

    @RequestMapping(value="common/register", method= RequestMethod.GET)
    public String registerGet(@ModelAttribute("addModel") UserInfoModel userInfoModel, Model model) {
        return "manage/register";
    }

    @RequestMapping(value="common/register", method= RequestMethod.POST)
    public void registerPost(@ModelAttribute("addModel") UserInfoModel userInfoModel, Model model,
                             HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {
        User user = userManager.register(userInfoModel);
        LoginHelper.addLoginSession(request, user, roleResourceManager.getRoleMenuList(user.getRole().getId()));
        checkCodeMapHolder.delCheckCode(user.getEmail());
        String lastUri = (String) WebUtils.getSessionAttribute(request, MySession.LAST_URI);
        if(!StringUtils.hasText(lastUri) || lastUri.startsWith("/app/common/welcome"))
        {
            toJump(response, request.getContextPath() + "/app/index?myMenuId=index");
            return;
        }
        WebUtils.setSessionAttribute(request, MySession.LAST_URI, null);
        toJump(response, lastUri);
    }

    @RequestMapping(value="common/checkCode", method= RequestMethod.POST)
    public void checkCodePost(@RequestParam("email") String email, Model model,
                              HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException, MessagingException, SQLException {
        userManager.sendCheckCode(email, request);
        addSuccess(response, "邮件验证码已成功推送，请注意查收");
    }
}
