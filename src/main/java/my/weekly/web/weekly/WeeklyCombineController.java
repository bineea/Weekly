package my.weekly.web.weekly;

import my.weekly.common.pub.MyManagerException;
import my.weekly.common.tools.HttpResponseHelper;
import my.weekly.dao.entity.Daily;
import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.manager.daily.DailyManager;
import my.weekly.model.NoteModel;
import my.weekly.model.weekly.WeeklyModel;
import my.weekly.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("weekly")
public class WeeklyCombineController extends AbstractController {

    private final String prefix = "weekly/";

    @Autowired
    private DailyManager dailyManager;

    @RequestMapping(value="/daily/combine", method= RequestMethod.GET)
    public String dailyCombineGet(@ModelAttribute("spe") WeeklyDailyPageSpe spe, HttpServletRequest request) {
        return prefix + "weekly/manage";
    }

    @RequestMapping(value="/daily/combine", method=RequestMethod.POST)
    public String dailyCombinePost(@ModelAttribute("spe") WeeklyDailyPageSpe spe, HttpServletRequest request, Model model) throws MyManagerException {
        if(spe.getStartOpDate() == null)
            throw new MyManagerException("START DATE不能为空");
        if(spe.getEndOpDate() == null)
            throw new MyManagerException("END DATE不能为空");
        spe.setPageSize(500);
        Page<Daily> page = dailyManager.pageQuery(spe);
        model.addAttribute("queryResult", page.getContent());
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        return prefix + "weekly/queryResult";
    }

    @RequestMapping(value="/daily/weekly", method=RequestMethod.POST)
    public void daily2WeeklyPost(
            @Valid @ModelAttribute WeeklyModel weeklyModel,
            HttpServletRequest request, HttpServletResponse response)
            throws MyManagerException, IOException {
        String fileName = dailyManager.combine(weeklyModel, request);
        addSuccess(response, "汇总日报成功");
        NoteModel note = new NoteModel(true, fileName);
        HttpResponseHelper.responseJson(note.toJson(), response);
    }

    @RequestMapping(value="/daily/sendEmail", method=RequestMethod.GET)
    public String dailySendEmailGet(
            @RequestParam(value="fileName", required=true) String fileName,
            HttpServletRequest request) {

        return prefix + "weekly/sendEmail";
    }

    @RequestMapping(value="/daily/sendEmail", method=RequestMethod.POST)
    public String dailySendEmailPost() {

        return prefix + "weekly/result";
    }
}
