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

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Project;
import my.weekly.dao.entity.dict.Area;
import my.weekly.manager.daily.ProjectManager;
import my.weekly.web.AbstractController;

@Controller
@RequestMapping("weekly")
public class WeeklyProjectController extends AbstractController {
	
	private final String prefix = "weekly/";
	
	@Autowired
	private ProjectManager projectManager;

	@RequestMapping(value = "/project/add", method = RequestMethod.GET)
	public String dailyProjectAddGet(HttpServletRequest request, Model model) {
		model.addAttribute("areas", Area.values());
		return prefix + "record/project/add";
	}
	
	@RequestMapping(value = "/project/add", method = RequestMethod.POST)
	public void dailyProjectAddPost(@Valid @ModelAttribute("project") Project project,
			HttpServletRequest request, HttpServletResponse response, Model model) throws MyManagerException, IOException {
		projectManager.add(project, request);
		toJump(response, request.getContextPath() + "/app/weekly/daily/project?projectId=" + project.getId());
	}
	
	@RequestMapping(value = "/project/edit/{id}", method = RequestMethod.GET)
	public String dailyProjectEditGet(@PathVariable("id") String id, HttpServletRequest request, Model model) {
		Project project = projectManager.findById(id);
		model.addAttribute("projectModel", project);
		model.addAttribute("areas", Area.values());
		return prefix + "record/project/edit";
	}
	
	@RequestMapping(value = "/project/edit", method = RequestMethod.POST)
	public void dailyProjectEditPost(@Valid @ModelAttribute("project") Project project,
			HttpServletRequest request, HttpServletResponse response, Model model) throws MyManagerException, IOException {
		projectManager.edit(project, request);
		toJump(response, request.getContextPath() + "/app/weekly/daily/project?projectId=" + project.getId());
	}
	
	@RequestMapping(value = "/project/del/{id}", method = RequestMethod.POST)
	public void dailyProjectDel(@PathVariable("id") String id, 
			HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {
//		projectManager.del(id, request);
		addSuccess(response, "成功删除项目数据");
	}
}
