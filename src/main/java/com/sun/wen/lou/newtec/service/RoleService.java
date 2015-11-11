package com.sun.wen.lou.newtec.service;


import java.util.List;
import java.util.Set;

import com.sun.wen.lou.newtec.entity.Role;
import com.sun.wen.lou.newtec.entity.RoleResource;
import com.sun.wen.lou.newtec.util.PageController;

public interface RoleService {


    public int createRole(Role role);
    public int updateRole(Role role);
    public int deleteRole(String roleId);

    public Role findOne(Long roleId);
    public List<Role> findAll();

    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(List<String>roleIdlist);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Long[] roleIds);
    
    /**批量添加角色资源中间表
     * @param list
     * @return
     */
    public void addByBatchRoleResource(List<RoleResource> list);
    public PageController getRolelist(PageController page,Role role);
    
    
    public Role findByRolename(String rolename);
    

	public int deleteRoleResource(String roleid);
}
