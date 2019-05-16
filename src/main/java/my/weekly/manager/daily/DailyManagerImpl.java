package my.weekly.manager.daily;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Daily;
import my.weekly.dao.entity.Demand;
import my.weekly.dao.entity.Project;
import my.weekly.dao.entity.User;
import my.weekly.dao.entity.dict.DemandType;
import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.dao.repo.jpa.DailyRepo;
import my.weekly.dao.repo.jpa.DemandRepo;
import my.weekly.dao.repo.jpa.ProjectRepo;
import my.weekly.manager.AbstractManager;
import my.weekly.manager.LoginHelper;
import my.weekly.model.weekly.DailyModel;

@Service
public class DailyManagerImpl extends AbstractManager implements DailyManager {

	@Autowired
	private DailyRepo dailyRepo;
	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private DemandRepo demandRepo;
	
	@Override
	public Page<Daily> pageQuery(WeeklyDailyPageSpe spe) {
		return dailyRepo.findAll(spe.handleSpecification(), spe.getPageRequest());
	}

	@Override
	public Daily createDaily(DailyModel dailyModel, HttpServletRequest request) throws MyManagerException {
		validateDaily(dailyModel);
		//TODO 重复日志相似度校验
		User user = LoginHelper.getLoginUser(request);
		Daily daily = getDailyByModel(dailyModel, user);
		dailyRepo.save(daily);
		return daily;
	}
	
	private void validateDaily(DailyModel dailyModel) throws MyManagerException {
		if(!StringUtils.hasText(dailyModel.getProjectId()))
			throw new MyManagerException("所属项目不能为空");
		if(!StringUtils.hasText(dailyModel.getDemandId()))
			throw new MyManagerException("对应需求不能为空");
		if(!StringUtils.hasText(dailyModel.getOperateContent()))
			throw new MyManagerException("具体操作不能为空");
		if(dailyModel.getHandleStatus() == null)
			throw new MyManagerException("操作处理状态不能为空");
		if(dailyModel.getOperateDate() == null)
			throw new MyManagerException("操作处理时间不能为空");
		if(dailyModel.getOperateDate().isAfter(LocalDate.now()))
			throw new MyManagerException("无法执行处理属于未来的操作");
		
		Optional<Project> projectOpt = projectRepo.findById(dailyModel.getProjectId());
		if(!projectOpt.isPresent())
			throw new MyManagerException("未查询到所属项目信息");
		Optional<Demand> demandOpt = demandRepo.findById(dailyModel.getDemandId());
		if(!demandOpt.isPresent())
			throw new MyManagerException("未查询到对应需求信息");
		if(!demandOpt.get().getProject().getId().equals(projectOpt.get().getId()))
			throw new MyManagerException("对应需求与所属项目不匹配");
		if(!StringUtils.hasText(dailyModel.getSqlContent()) && demandOpt.get().getDemandType() == DemandType.ZSSJXG)
			throw new MyManagerException("sql语句不能为空");
		
	}

	private Daily getDailyByModel(DailyModel dailyModel, User user) {
		Daily daily = new Daily();
		daily.setOperateContent(StringUtils.trimAllWhitespace(dailyModel.getOperateContent()));
		daily.setSqlContent(StringUtils.trimAllWhitespace(dailyModel.getSqlContent()));
		daily.setHandleStatus(dailyModel.getHandleStatus());
		daily.setUser(user);
		daily.setOperateDate(dailyModel.getOperateDate());
		daily.setCreateTime(LocalDateTime.now());
		daily.setUpdateTime(LocalDateTime.now());
		daily.setDemand(demandRepo.findById(dailyModel.getDemandId()).orElse(null));
		return daily;
	}

	@Override
	public Daily findById(String id) {
		Assert.hasText(id,"id不能为空");
		return dailyRepo.findById(id).orElse(null);
	}

}
