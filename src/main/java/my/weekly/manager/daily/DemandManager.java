package my.weekly.manager.daily;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Demand;
import my.weekly.dao.repo.Spe.WeeklyDemandPageSpe;
import my.weekly.model.weekly.DemandModel;

public interface DemandManager {

	/**分页查询
	 * @param spe
	 * @return
	 */
	Page<Demand> pageQuery(WeeklyDemandPageSpe spe);
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	Demand findById(String id);
	
	/**
	 * 新增需求
	 * @param demandModel
	 * @param request
	 * @return
	 * @throws MyManagerException
	 */
	Demand add(DemandModel demandModel, HttpServletRequest request) throws MyManagerException;
	
	/**
	 * 更新需求执行状态
	 * @param demand
	 */
	void updateStatus(Demand demand);
	
}
