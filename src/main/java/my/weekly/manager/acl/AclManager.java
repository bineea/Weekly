package my.weekly.manager.acl;

import org.springframework.web.bind.annotation.RequestMethod;

import my.weekly.dao.entity.AppResource;
import my.weekly.dao.entity.Role;

public interface AclManager {

	AppResource findByUrlMethod(String url, RequestMethod method);
	
	String checkAuth(Role role, AppResource resource); 
}
