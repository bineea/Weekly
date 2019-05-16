package my.weekly.manager.acl;

import java.util.List;

import org.springframework.data.domain.Page;

import my.weekly.common.pub.MyManagerException;
import my.weekly.dao.entity.AppResource;
import my.weekly.dao.repo.Spe.AppResourcePageSpe;
import my.weekly.model.acl.ResourceTreeModel;

public interface ResourceManager {

	/**
	 * 获取根菜单资源
	 */
	List<AppResource> findRootMenu();
	
	/**
	 * 获取资源菜单对应子菜单资源
	 */
	List<AppResource> listByParent(String menuId);
	
	/**
	 * 获取jstree
	 */
	List<ResourceTreeModel> getResourceTree(String id);
	
	/**
	 * 分页查询
	 */
	Page<AppResource> pageQuery(AppResourcePageSpe pageSpe);
	
	/**
	 * 查询资源
	 * @param id
	 * @return
	 */
	AppResource findById(String id);
	
	/**
	 * 删除资源
	 * @param id
	 */
	void deleteById(String id) throws MyManagerException;
	
	/**
	 * 添加资源
	 * @param resource
	 */
	AppResource add(AppResource resource) throws MyManagerException;
	
	/**
	 * 更新资源
	 * @param resource
	 * @return
	 */
	AppResource update(AppResource resource) throws MyManagerException;
}
