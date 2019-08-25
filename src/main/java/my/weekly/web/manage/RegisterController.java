package my.weekly.web.manage;

import my.weekly.common.pub.MyManagerException;
import my.weekly.manager.acl.UserManager;
import my.weekly.model.acl.UserInfoModel;
import my.weekly.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RegisterController extends AbstractController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value="common/register", method= RequestMethod.GET)
    public String registerGet(@ModelAttribute("addModel") UserInfoModel userInfoModel, Model model) {
        return "manage/register";
    }

    @RequestMapping(value="common/register", method= RequestMethod.POST)
    public void registerPost(@ModelAttribute("addModel") UserInfoModel userInfoModel, Model model,
                             HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {

    }

    @RequestMapping(value="common/checkCode", method= RequestMethod.POST)
    public void checkCodePost(@RequestParam("email") String email, Model model,
                              HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {
        userManager.sendCheckCode(email, request);
        addSuccess(response, "邮件验证码已成功推送，请注意查收");
    }
}
