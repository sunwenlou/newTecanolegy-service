package com.sun.wen.lou.newtec.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sun.wen.lou.newtec.entity.Role;
import com.sun.wen.lou.newtec.entity.RoleResource;
import com.sun.wen.lou.newtec.util.PageController;

public interface RoleMapper {

	public int createRole(Role role);

	public int updateRole(Role role);

	public int deleteRole(String roleid);

	public Role findOne(Long roleId);

	public List<Role> findAll();

	/**
	 * 批量添加角色资源中间表
	 * 
	 * @param list
	 * @return
	 */
	public void addByBatchRoleResource(List<RoleResource> list);

	/**
	 * 根据角色编号得到角色标识符列表
	 * 
	 * @param roleIds
	 * @return
	 */
	Set<String> findRoles(List<String> roleIdlist);

	/**
	 * 根据角色编号得到权限字符串列表
	 * 
	 * @param roleIds
	 * @return
	 */
	Set<String> findPermissions(Long[] roleIds);

	public List<Role> getRolelist(@Param("page") PageController page,
			@Param("role") Role role);

	public Role findByRolename(String rolename);
	public int deleteRoleResource(String roleid);
}
