package my.weekly.manager.daily;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Daily;
import my.weekly.dao.entity.Demand;
import my.weekly.dao.entity.Project;
import my.weekly.dao.repo.Spe.WeeklyProjectPageSpe;
import my.weekly.dao.repo.jpa.DailyRepo;
import my.weekly.dao.repo.jpa.DemandRepo;
import my.weekly.dao.repo.jpa.ProjectRepo;
import my.weekly.manager.AbstractManager;
import my.weekly.manager.LoginHelper;

@Service
public class ProjectManagerImpl extends AbstractManager implements ProjectManager {

	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private DemandRepo demandRepo;
	@Autowired
	private DailyRepo dailyRepo;
	
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
		List<Project> plist = projectRepo.findByNameOrAbbrDesc(project.getName(), project.getAbbr());
		if(plist != null && !plist.isEmpty())
			throw new MyManagerException("项目名称或项目简称已存在");
	}

	@Override
	public Project edit(Project project, HttpServletRequest request) throws MyManagerException {
		Optional<Project> proOpt = projectRepo.findById(project.getId());
		if(!proOpt.isPresent()) 
			throw new MyManagerException("项目信息不存在");
		List<Project> plist = projectRepo.findByNameOrAbbrDesc(project.getName(), project.getAbbr());
		if(plist.stream().anyMatch(p -> !p.getId().equals(project.getId())))
			throw new MyManagerException("项目名称或项目简称已存在");
		//TODO 项目名称、项目描述相似度
		Project p = proOpt.get();
		BeanUtils.copyProperties(project, p, new String[] {"id","createTime","user"});
		projectRepo.save(p);
		return project;
	}

	@Override
	public void del(String id, HttpServletRequest request) throws MyManagerException {
		Optional<Project> proOpt = projectRepo.findById(id);
		if(!proOpt.isPresent()) 
			throw new MyManagerException("项目信息不存在");
		List<Demand> demandList = demandRepo.findByProjectDesc(id);
		for(Demand demand : demandList) {
			List<Daily> dailyList = dailyRepo.findByDemandDesc(demand.getId());
			if(!CollectionUtils.isEmpty(dailyList))
				throw new MyManagerException("存在关联该项目的日报数据，无法删除");
		}
		if(!CollectionUtils.isEmpty(demandList))
			throw new MyManagerException("存在关联该项目的需求数据，无法删除");
		projectRepo.delete(proOpt.get());
	}

}
