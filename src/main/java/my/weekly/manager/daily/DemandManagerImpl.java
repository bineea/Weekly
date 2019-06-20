package my.weekly.manager.daily;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Daily;
import my.weekly.dao.entity.Demand;
import my.weekly.dao.entity.Project;
import my.weekly.dao.entity.User;
import my.weekly.dao.entity.dict.HandleStatus;
import my.weekly.dao.repo.Spe.WeeklyDemandPageSpe;
import my.weekly.dao.repo.jpa.DailyRepo;
import my.weekly.dao.repo.jpa.DemandRepo;
import my.weekly.dao.repo.jpa.ProjectRepo;
import my.weekly.manager.AbstractManager;
import my.weekly.manager.LoginHelper;
import my.weekly.model.weekly.DemandModel;

@Service
public class DemandManagerImpl extends AbstractManager implements DemandManager {

	@Autowired
	private DemandRepo demandRepo;
	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private DailyRepo dailyRepo;
	
	@Override
	public Page<Demand> pageQuery(WeeklyDemandPageSpe spe) {
		Assert.notNull(spe, "spe不能为空");
		return demandRepo.findAll(spe.handleSpecification(), spe.getPageRequest());
	}

	@Override
	public Demand findById(String id) {
		Assert.hasText(id, "id不能为空");
		return demandRepo.findById(id).orElse(null);
	}

	@Override
	public Demand add(DemandModel demandModel, HttpServletRequest request) throws MyManagerException {
		validateDemand(demandModel);
		//TODO 需求标题、需求描述相似度
		Demand demand = model2Demand(demandModel, request);
		demandRepo.save(demand);
		return demand;
	}

	private void validateDemand(DemandModel demandModel) throws MyManagerException {
		if(!StringUtils.hasText(demandModel.getTitle()))
			throw new MyManagerException("需求标题不能为空");
		if(!StringUtils.hasText(demandModel.getSummary()))
			throw new MyManagerException("需求描述不能为空");
		if(!StringUtils.hasText(demandModel.getProjectId()))
			throw new MyManagerException("无法获取需求对应项目信息");
		if(demandModel.getDemandType() == null)
			throw new MyManagerException("需求类型不能为空");
		Optional<Project> projectOpt = projectRepo.findById(demandModel.getProjectId());
		if(!projectOpt.isPresent())
			throw new MyManagerException("无法获取需求对应项目信息");
		Demand demand = demandRepo.findByProjectAndTitle(demandModel.getProjectId(), demandModel.getTitle());
		if(demand != null)
			throw new MyManagerException("需求标题已存在");
	}
	
	private Demand model2Demand(DemandModel demandModel, HttpServletRequest request) {
		Demand demand = new Demand();
		demand.setTitle(demandModel.getTitle());
		demand.setSummary(demandModel.getSummary());
		demand.setDemandType(demandModel.getDemandType());
		demand.setCreateTime(LocalDateTime.now());
		demand.setUpdateTime(LocalDateTime.now());
		demand.setHandleStatus(HandleStatus.NEW);
		demand.setUser(LoginHelper.getLoginUser(request));
		demand.setProject(projectRepo.findById(demandModel.getProjectId()).get());
		return demand;
	}

	@Override
	public void updateStatus(Demand demand) {
		if(demand.getDemandType().isHandleDone()) {
			List<Daily> dlist = dailyRepo.findByDemandDesc(demand.getId());
			List<User> ulist = dlist.stream().map(d -> d.getUser()).distinct().collect(Collectors.toList());
			boolean done = true;
			for(User u : ulist) {
				Daily daily = dailyRepo.findByDemandAndUserDesc(demand.getId(), u.getId()).stream().findFirst().get();
				if(daily.getHandleStatus() != HandleStatus.DONE) {
					done = false;
					break;
				}
			}
			if(done) {
				demand.setHandleStatus(HandleStatus.DONE);
				demand.setUpdateTime(LocalDateTime.now());
				demandRepo.save(demand);
			}
		}
		
	}

	@Override
	public Demand edit(Demand demand, HttpServletRequest request) throws MyManagerException {
		Optional<Demand> demandOpt = demandRepo.findById(demand.getId());
		if(!demandOpt.isPresent())
			throw new MyManagerException("需求信息不存在");
		Demand other = demandRepo.findByProjectAndTitle(demandOpt.get().getProject().getId(), demand.getTitle());
		if(other != null && !demand.getId().equals(other.getId()))
			throw new MyManagerException("需求标题已存在");
		Demand d = demandOpt.get();
		d.setTitle(demand.getTitle());
		d.setDemandType(demand.getDemandType());
		d.setSummary(demand.getSummary());
		d.setUpdateTime(LocalDateTime.now());
		demandRepo.save(d);
		return d;
	}

	@Override
	public void del(String id, HttpServletRequest request) throws MyManagerException {
		Optional<Demand> demandOpt = demandRepo.findById(id);
		if(!demandOpt.isPresent())
			throw new MyManagerException("需求信息不存在");
		List<Daily> dailyList = dailyRepo.findByDemandDesc(id);
		if(!CollectionUtils.isEmpty(dailyList))
			throw new MyManagerException("存在关联该项目的日报数据，无法删除");
		demandRepo.delete(demandOpt.get());
	}
}
