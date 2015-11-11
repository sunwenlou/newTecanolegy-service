package com.sun.wen.lou.newtec.service;

import java.util.List;
import java.util.Set;


import com.sun.wen.lou.newtec.entity.Resource;
import com.sun.wen.lou.newtec.entity.RoleResource;
import com.sun.wen.lou.newtec.util.PageController;

public interface ResourceService {

	public int createResource(Resource resource);

	public int updateResource(Resource resource);

	public int deleteResource(Long resourceId);

	Resource findOne(Long resourceId);

	List<Resource> findAll();

	/**
	 * 得到资源对应的权限字符串
	 * 
	 * @param resourceIds
	 * @return
	 */
	Set<String> findPermissions(Set<Long> resourceIds);

	/**
	 * 根据用户权限得到菜单
	 * 
	 * @param permissions
	 * @return
	 */
	List<Resource> findMenus(Set<String> permissions);

	/**
	 * 根据用户名查询用户的资源
	 * 
	 * @param username
	 * @return
	 */
	public List<Resource> findUserResource(String username);

	/**
	 * 获取分页数据
	 * 
	 * @param page
	 * @return
	 */
	public PageController getResourceAll(  PageController page);

	/**
	 * 查询是否存在
	 * 
	 * @param resourceFlag
	 * @return
	 */
	public Resource isExistFlag(  String resourceFlag);

	public List<Resource> queryResourceUucByTag(
			  String resourceId,
			  String searchName,
		  String searchValue);
	
	public List<Resource> queryResource(String resourceId);

	public int deleteRoleResource( long resourceId);
	
	public List<Resource> queryChildren(long resourceId);
	

	public List<RoleResource> queryRoleResource(  String resourceId,   String roleId);

}
