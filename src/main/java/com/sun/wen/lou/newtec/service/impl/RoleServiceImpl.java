package com.sun.wen.lou.newtec.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.wen.lou.newtec.entity.Resource;
import com.sun.wen.lou.newtec.entity.Role;
import com.sun.wen.lou.newtec.entity.RoleResource;
import com.sun.wen.lou.newtec.mapper.ResourceMapper;
import com.sun.wen.lou.newtec.mapper.RoleMapper;
import com.sun.wen.lou.newtec.service.RoleService;
import com.sun.wen.lou.newtec.util.PageController;

@Service("RoleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ResourceMapper resourceMapper;

	@Override
	public int createRole(Role role) {
		return roleMapper.createRole(role);
	}

	@Override
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

	@Override
	public int deleteRole(String roleId) {
		return roleMapper.deleteRole(roleId);
	}

	@Override
	public Role findOne(Long roleId) {
		// TODO Auto-generated method stub
		return roleMapper.findOne(roleId);
	}

	@Override
	public List<Role> findAll() {
		return roleMapper.findAll();
	}

	@Override
	public Set<String> findRoles(List<String> roleIdlist) {
		// TODO Auto-generated method stub
		return roleMapper.findRoles(roleIdlist);
	}

	@Override
	public Set<String> findPermissions(Long[] roleIds) {
		return roleMapper.findPermissions(roleIds);
	}

	/**
	 * 批量添加角色资源中间表
	 * 
	 * @param list
	 * @return
	 */
	public void addByBatchRoleResource(List<RoleResource> list) {

		roleMapper.addByBatchRoleResource(list);
	}

	@Override
	public PageController getRolelist(PageController page, Role role) {
		List<Role> rolelist = roleMapper.getRolelist(page, role);
		List<Resource> rescourcelist = resourceMapper.findAll();
		boolean flag = false;
		for (Role r : rolelist) {
			String ids = r.getResourceIds();
			if (ids == null) {
				continue;
			}
			String[] idstr = ids.split(",");
			for (Resource resouce : rescourcelist) {
				System.out.println(resouce.getId());
				for (String id : idstr) {
					if (id.equals(resouce.getId() + "")) {
						if (r.getResourceNames() == null) {

							r.setResourceNames(resouce.getName());
						} else {
							r.setResourceNames(r.getResourceNames() + ","
									+ resouce.getName());
						}
						flag = true;
						break;
					}
				}
				/*
				 * if (flag) { flag = false; break; }
				 */
			}
		}
		if (rolelist != null) {
			page.setContent(rolelist);
		}
		return page;
	}

	@Override
	public Role findByRolename(String rolename) {
		return roleMapper.findByRolename(rolename);
	}
	

	public int deleteRoleResource(String roleid){
		return roleMapper.deleteRoleResource(roleid);
	}
}
