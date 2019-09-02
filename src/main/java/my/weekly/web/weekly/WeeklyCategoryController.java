package my.weekly.web.weekly;

import my.weekly.manager.daily.DailyManager;
import my.weekly.model.weekly.CategoryModel;
import my.weekly.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("weekly")
public class WeeklyCategoryController extends AbstractController {

    @Autowired
    private DailyManager dailyManager;
    private final String prefix = "weekly/common/";

    @RequestMapping(value="/category/demand", method= RequestMethod.POST)
    public String categoryDemandPost(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        List<CategoryModel> modelList = dailyManager.categoryByDemand();
        model.addAttribute("queryResult", modelList);
        addSuccess(response, "需求类型分类统计成功");
        return prefix + "categoryResult";
    }

    @RequestMapping(value="/category/user", method= RequestMethod.POST)
    public String categoryUserPost(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        List<CategoryModel> modelList = dailyManager.categoryByUser();
        model.addAttribute("queryResult", modelList);
        addSuccess(response, "用户分类统计成功");
        return prefix + "categoryResult";
    }
}
