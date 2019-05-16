package my.weekly.manager.daily;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Project;
import my.weekly.dao.repo.Spe.WeeklyProjectPageSpe;
import my.weekly.dao.repo.jpa.ProjectRepo;
import my.weekly.manager.AbstractManager;
import my.weekly.manager.LoginHelper;

@Service
public class ProjectManagerImpl extends AbstractManager implements ProjectManager {

	@Autowired
	private ProjectRepo projectRepo;
	
	@Override
	public Page<Project> pageQuery(WeeklyProjectPageSpe spe) {
		Assert.notNull(spe, "spe不能为空");
		return projectRepo.findAll(spe.handleSpecification(), spe.getPageRequest());
	}

	@Override
	public Project findById(String id) {
		Assert.hasText(id, "id不能为空");
		return projectRepo.findById(id).orElse(null);
	}

	@Override
	public Project add(Project project, HttpServletRequest request) throws MyManagerException {
		validateProject(project);
		//TODO 项目名称、项目描述相似度
		project.setCreateTime(LocalDateTime.now());
		project.setUser(LoginHelper.getLoginUser(request));
		projectRepo.save(project);
		return project;
	}
	
	private void validateProject(Project project) throws MyManagerException {
		if(!StringUtils.hasText(project.getName()))
			throw new MyManagerException("项目名称不能为空");
		if(!StringUtils.hasText(project.getAbbr()))
			throw new MyManagerException("项目简称不能为空");
		if(!StringUtils.hasText(project.getSummary()))
			throw new MyManagerException("项目描述不能为空");
		if(project.getArea() == null)
			throw new MyManagerException("项目所属区域不能为空");
		if(project.getAbbr().length() > 6)
			throw new MyManagerException("项目简称长度不能超过6");
		List<Project> plist = projectRepo.findByNameOrAbbr(project.getName(), project.getAbbr());
		if(plist != null && !plist.isEmpty())
			throw new MyManagerException("项目名称或项目简称已存在");
	}

}
