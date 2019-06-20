package my.weekly.web.weekly;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Demand;
import my.weekly.dao.entity.Project;
import my.weekly.dao.entity.dict.DemandType;
import my.weekly.manager.daily.DemandManager;
import my.weekly.manager.daily.ProjectManager;
import my.weekly.model.weekly.DemandModel;
import my.weekly.web.AbstractController;

@Controller
@RequestMapping("weekly")
public class WeeklyDemandController extends AbstractController {
	
	private final String prefix = "weekly/";
	
	@Autowired
	private ProjectManager projectManager;
	@Autowired
	private DemandManager demandManager;

	@RequestMapping(value = "/demand/add", method = RequestMethod.GET)
	public String dailyDemandAddGet(@RequestParam(name = "projectId", required = true) String projectId,
			HttpServletRequest request, Model model) {
		Project project = projectManager.findById(projectId);
		model.addAttribute("project", project);
		model.addAttribute("demandTypes", DemandType.values());
		return prefix + "record/demand/add";
	}
	
	@RequestMapping(value = "/demand/add", method = RequestMethod.POST)
	public void dailyDemandAddPost(@Valid @ModelAttribute("demandModel") DemandModel demandModel,
			HttpServletRequest request, HttpServletResponse response, Model model) throws MyManagerException, IOException {
		Demand demand = demandManager.add(demandModel, request);
		toJump(response, request.getContextPath() + "/app/weekly/daily/demand?projectId=" + demand.getProject().getId() + "&demandId=" + demand.getId());
	}
	
	@RequestMapping(value = "/demand/edit/{id}", method = RequestMethod.GET)
	public String dailyProjectEditGet(@PathVariable("id") String id, HttpServletRequest request, Model model) {
		Demand demand = demandManager.findById(id);
		model.addAttribute("demandModel", demand);
		model.addAttribute("demandTypes", DemandType.values());
		return prefix + "record/demand/edit";
	}
	
	@RequestMapping(value = "/demand/edit", method = RequestMethod.POST)
	public void dailyProjectEditPost(@Valid @ModelAttribute("demand") Demand demand,
			HttpServletRequest request, HttpServletResponse response, Model model) throws MyManagerException, IOException {
		Demand d = demandManager.edit(demand, request);
		toJump(response, request.getContextPath() + "/app/weekly/daily/demand?projectId=" + d.getProject().getId() + "&demandId=" + d.getId());
	}
	
	@RequestMapping(value = "/demand/del/{id}", method = RequestMethod.POST)
	public void dailyProjectDel(@PathVariable("id") String id, 
			HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {
		demandManager.del(id, request);
		addSuccess(response, "成功删除需求数据");
	}
}
