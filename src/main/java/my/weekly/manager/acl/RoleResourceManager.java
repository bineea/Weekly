package my.weekly.manager.acl;

import java.util.List;

import my.weekly.model.acl.MenuModel;

public interface RoleResourceManager {

	List<MenuModel> getRoleMenuList(String roleId);
}
