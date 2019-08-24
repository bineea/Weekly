package my.weekly.web.weekly;

import my.weekly.common.pub.MyManagerException;
import my.weekly.common.tools.HttpResponseHelper;
import my.weekly.dao.entity.Daily;
import my.weekly.dao.entity.MailAttachment;
import my.weekly.dao.entity.dict.EmailConfType;
import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.manager.daily.DailyManager;
import my.weekly.model.NoteModel;
import my.weekly.model.message.SendEmailInfo;
import my.weekly.model.message.SendEmailResult;
import my.weekly.model.weekly.WeeklyModel;
import my.weekly.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;

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
        MailAttachment mailAttachment = dailyManager.combine(weeklyModel, request);
        addSuccess(response, "汇总日报成功");
        NoteModel note = new NoteModel(true, mailAttachment.getId());
        HttpResponseHelper.responseJson(note.toJson(), response);
    }

    @RequestMapping(value="/daily/sendEmail", method=RequestMethod.GET)
    public String dailySendEmailGet(
            @RequestParam(value="mailAttachmentId", required=true) String mailAttachmentId,
            HttpServletRequest request, Model model) {
        model.addAttribute("emailConfTypes", EmailConfType.values());
        model.addAttribute("mailAttachmentId", mailAttachmentId);
        return prefix + "weekly/sendEmail";
    }

    @RequestMapping(value="/daily/sendEmail", method=RequestMethod.POST)
    public void dailySendEmailPost(@Valid @ModelAttribute SendEmailInfo info,
                                   HttpServletRequest request, HttpServletResponse response )
            throws MyManagerException, IOException, MessagingException, SQLException {
        SendEmailResult result = dailyManager.weekly2SendEmail(info, request);
        if(result.getMailAttachmentList().size() > 1)
            throw new MyManagerException("尚不支持返回多个附件信息");
        addSuccess(response, "成功发送邮件至"+ StringUtils.join(info.getRecipients().toArray(), ","));
        NoteModel note = new NoteModel(true, result.getMailAttachmentList().stream().findFirst().get().getId());
        HttpResponseHelper.responseJson(note.toJson(), response);
    }

    @RequestMapping(value="/daily/mailAttachment/result", method=RequestMethod.GET)
    public String daily2WeeklyFileResult(
            @RequestParam(value="mailAttachmentId", required=true) String mailAttachmentId,
            Model model) {
        model.addAttribute("mailAttachment", dailyManager.findFileById(mailAttachmentId));
        return prefix + "weekly/result";
    }

    @RequestMapping(value="/daily/mailAttachment/download", method=RequestMethod.GET)
    public void daily2WeeklyFileDownload(
            @RequestParam(value="mailAttachmentId", required=true) String mailAttachmentId,
            HttpServletRequest request, HttpServletResponse response) {

    }
}
