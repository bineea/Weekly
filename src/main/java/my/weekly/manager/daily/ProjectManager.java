package my.weekly.manager.daily;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.Project;
import my.weekly.dao.repo.Spe.WeeklyProjectPageSpe;

public interface ProjectManager {

	/**分页查询
	 * @param spe
	 * @return
	 */
	Page<Project> pageQuery(WeeklyProjectPageSpe spe);
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	Project findById(String id);
	
	/**
	 * 新增项目
	 * @param project
	 * @return
	 * @throws MyManagerException
	 */
	Project add(Project project, HttpServletRequest request) throws MyManagerException;
}
