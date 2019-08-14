package my.weekly.web.weekly;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import my.weekly.common.pub.MyManagerException;
import my.weekly.common.tools.HttpResponseHelper;
import my.weekly.dao.entity.Daily;
import my.weekly.dao.entity.Demand;
import my.weekly.dao.entity.Project;
import my.weekly.dao.entity.dict.HandleStatus;
import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.dao.repo.Spe.WeeklyDemandPageSpe;
import my.weekly.dao.repo.Spe.WeeklyProjectPageSpe;
import my.weekly.manager.daily.DailyManager;
import my.weekly.manager.daily.DemandManager;
import my.weekly.manager.daily.ProjectManager;
import my.weekly.model.MyFinals;
import my.weekly.model.NoteModel;
import my.weekly.model.weekly.DailyModel;
import my.weekly.web.AbstractController;

@Controller
@RequestMapping("weekly")
public class WeeklyDailyController extends AbstractController {
	
	@Autowired
	private DailyManager dailyManager;
	@Autowired
	private ProjectManager projectManager;
	@Autowired
	private DemandManager demandManager;
	
	private String prefix = "weekly/";
	
	@RequestMapping(value = "/dailyIndex", method = RequestMethod.POST)
	public String dailyIndexPost(@ModelAttribute("spe") WeeklyDailyPageSpe spe, HttpServletRequest request, Model model) {
		spe.setPageSize(5);
		Page<Daily> page = dailyManager.pageQuery(spe);
		model.addAttribute("queryResult", page.getContent());
		model.addAttribute("currentPage", page.getNumber());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		return prefix + "dailyIndexResult";
	}
	
	@RequestMapping(value = "/daily/check", method = RequestMethod.POST)
	public void dailyCheck(@RequestParam(name = "projectId") String projectId, 
			@RequestParam(name = "demandId", required = false) String demandId,
			HttpServletResponse response) throws MyManagerException, IOException{
		Project project = projectManager.findById(projectId);
		if(project == null) 
			throw new MyManagerException("项目信息不存在");
		if(StringUtils.hasText(demandId)) {
			Demand demand = demandManager.findById(demandId);
			if(demand == null)
				throw new MyManagerException("需求信息不存在");
			else if(demand.getHandleStatus() == HandleStatus.DONE)
				throw new MyManagerException("该需求已处理完成，无法创建新的日报");
		}
		addSuccess(response, "校验成功");
	}
	
	@RequestMapping(value = "/daily/project", method = RequestMethod.GET)
	public String dailyReocrdProjectGet(@RequestParam(name = "projectId", required = false) String projectId,
			HttpServletRequest request, Model model) {
		model.addAttribute("projectId", projectId);
		return prefix + "record/project/manage";
	}
	
	@RequestMapping(value = "/daily/project", method = RequestMethod.POST)
	public String dailyReocrdProjectPost(@RequestParam(name = "projectId", required = false) String projectId,
			@ModelAttribute("spe") WeeklyProjectPageSpe spe,
			HttpServletRequest request, Model model) {
		spe.setPageSize(MyFinals.DEFAULT_WEEKLY_SIZE);
		Page<Project> page = projectManager.pageQuery(spe);
		model.addAttribute("queryResult", page.getContent());
		model.addAttribute("currentPage", page.getNumber());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		return prefix + "record/project/queryResult";
	}
	
	@RequestMapping(value = "/daily/demand", method = RequestMethod.GET)
	public String dailyRecordDemandGet(@RequestParam(name = "projectId", required = true) String projectId,
			@RequestParam(name = "demandId", required = false) String demandId,
			HttpServletRequest request, Model model) {
		model.addAttribute("projectId", projectId);
		return prefix + "record/demand/manage";
	}
	
	@RequestMapping(value = "/daily/demand", method = RequestMethod.POST)
	public String dailyRecordDemandPost(@ModelAttribute("spe") WeeklyDemandPageSpe spe,
			HttpServletRequest request, Model model) {
		spe.setPageSize(MyFinals.DEFAULT_WEEKLY_SIZE);
		spe.setHandleStatues(Arrays.asList(HandleStatus.NEW, HandleStatus.DOING));
		Page<Demand> page = demandManager.pageQuery(spe);
		model.addAttribute("queryResult", page.getContent());
		model.addAttribute("currentPage", page.getNumber());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		return prefix + "record/demand/queryResult";
	}
	
	@RequestMapping(value = "/daily/add", method = RequestMethod.GET)
	public String dailyAddGet(@RequestParam(name = "projectId", required = true) String projectId,
			@RequestParam(name = "demandId", required = true) String demandId,
			HttpServletRequest request, Model model) {
		//FIXME 校验projectId、demandId
		Project project = projectManager.findById(projectId);
		if(project == null) 
			return prefix + "record/project/manage";
		Demand demand = demandManager.findById(demandId);
		if(demand == null)
			return prefix + "record/project/manage";
		else if(demand.getHandleStatus() == HandleStatus.DONE)
			return prefix + "record/project/manage";
		model.addAttribute("project", project);
		model.addAttribute("demand", demand);
		model.addAttribute("handleStatues", HandleStatus.values());
		return prefix + "record/daily/add";
	}
	
	@RequestMapping(value = "/daily/add", method = RequestMethod.POST)
	public void dailyAddPost(@ModelAttribute(name = "dailyModel") DailyModel dailyModel,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws IOException, MyManagerException {
		Daily daily = dailyManager.createDaily(dailyModel, request);
		addSuccess(response, "创建日报成功");
		NoteModel note = new NoteModel(true, daily.getId());
		HttpResponseHelper.responseJson(note.toJson(), response);
	}
	
	@RequestMapping(value = "/daily/edit/{dailyId}", method = RequestMethod.GET)
	public String dailyEditGet(@PathVariable(name = "dailyId", required = true) String dailyId,
			HttpServletRequest request, Model model) {
		Daily daily = dailyManager.findById(dailyId);
		model.addAttribute("daily", daily);
		model.addAttribute("project", daily.getDemand().getProject());
		model.addAttribute("demand", daily.getDemand());
		model.addAttribute("handleStatues", HandleStatus.values());
		return prefix + "record/daily/edit";
	}
	
	@RequestMapping(value = "/daily/edit", method = RequestMethod.POST)
	public void dailyEditPost(@ModelAttribute(name = "dailyModel") DailyModel dailyModel,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws IOException, MyManagerException {
		Daily daily = dailyManager.modifyDaily(dailyModel, request);
		addSuccess(response, "修改日报成功");
		NoteModel note = new NoteModel(true, daily.getId());
		HttpResponseHelper.responseJson(note.toJson(), response);
	}
	
	@RequestMapping(value = "/daily/record", method = RequestMethod.GET)
	public String dailyRecordGet(@RequestParam(name = "dailyId", required = true) String dailyId,
			HttpServletRequest request, Model model) {
		Daily daily = dailyManager.findById(dailyId);
		model.addAttribute("daily", daily);
		return prefix + "record/daily/result";
	}
	
	@RequestMapping(value = "/daily/del/{id}", method = RequestMethod.POST)
	public void dailyDel(@PathVariable("id") String id, 
			HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {
		dailyManager.del(id, request);
		addSuccess(response, "成功删除日报数据");
	}
}
