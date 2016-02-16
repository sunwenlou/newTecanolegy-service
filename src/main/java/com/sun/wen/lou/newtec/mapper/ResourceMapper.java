package com.sun.wen.lou.newtec.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sun.wen.lou.newtec.entity.Resource;
import com.sun.wen.lou.newtec.entity.RoleResource;

public interface ResourceMapper {

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
	public List<Resource> findUserResource(@Param("username") String username);

	/**
	 * 查询是否存在
	 * 
	 * @param resourceFlag
	 * @return
	 */
	public Resource isExistFlag(String resourceFlag);

	public List<Resource> queryResourceUucByTag(
			@Param("resourceId") String resourceId,
			@Param("searchName") String searchName,
			@Param("searchValue") String searchValue);

	
	public List<Resource> queryResource(	@Param("resourceId")  long resourceId);
	
	public int deleteRoleResource(@Param("resourceId")  long resourceId);
	
	/**查询子节点
	 * @param resourceId
	 * @return
	 */
	public List<Resource> queryChildren(long resourceId);
	public List<RoleResource> queryRoleResource(@Param("resourceId")  String resourceId,@Param("roleId")  String roleId);

}
