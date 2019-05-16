package my.weekly.manager.daily;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Daily;
import my.weekly.dao.repo.Spe.WeeklyDailyPageSpe;
import my.weekly.model.weekly.DailyModel;

public interface DailyManager {

	/**分页查询
	 * @param spe
	 * @return
	 */
	Page<Daily> pageQuery(WeeklyDailyPageSpe spe);
	
	/**通过id查询
	 * @param id
	 * @return
	 */
	Daily findById(String id);
	
	/**创建日报
	 * @param dailyModel
	 * @return
	 */
	Daily createDaily(DailyModel dailyModel, HttpServletRequest request) throws MyManagerException;
	
}
