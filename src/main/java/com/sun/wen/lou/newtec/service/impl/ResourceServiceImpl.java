package com.sun.wen.lou.newtec.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sun.wen.lou.newtec.entity.Resource;
import com.sun.wen.lou.newtec.entity.RoleResource;
import com.sun.wen.lou.newtec.mapper.ResourceMapper;
import com.sun.wen.lou.newtec.service.ResourceService;
import com.sun.wen.lou.newtec.util.JsonUtils;
import com.sun.wen.lou.newtec.util.PageController;
import com.sun.wen.lou.newtec.util.RedisClient;
import com.sun.wen.lou.newtec.util.SerializationUtil;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;

	@Override
	public int createResource(Resource resource) {
		return resourceMapper.createResource(resource);
	}

	@Override
	public int updateResource(Resource resource) {
		return resourceMapper.updateResource(resource);
	}

	@Override
	public int deleteResource(Long resourceId) {
		return resourceMapper.deleteResource(resourceId);
	}

	@Override
	public Resource findOne(Long resourceId) {
		return resourceMapper.findOne(resourceId);
	}

	@Override
	public List<Resource> findAll() {
		return resourceMapper.findAll();
	}

	@Override
	public Set<String> findPermissions(Set<Long> resourceIds) {

		Set<String> permissions = new HashSet<String>();
		for (Long resourceId : resourceIds) {
			Resource resource = findOne(resourceId);
			if (resource != null
					&& StringUtils.hasText(resource.getPermission())) {
				permissions.add(resource.getPermission());
			}
		}
		return permissions;
	}

	@Override
	public List<Resource> findMenus(Set<String> permissions) {

		List<Resource> allResources = resourceMapper.findAll();
		List<Resource> menus = new ArrayList<Resource>();
		for (Resource resource : allResources) {
			if (resource.isRootNode()) {
				continue;
			}
			if (!"menu".equals(resource.getType())) {
				continue;
			}
			/*
			 * if (!hasPermission(permissions, resource)) { continue; }
			 */
			menus.add(resource);
		}
		return menus;

	}

	private boolean hasPermission(Set<String> permissions, Resource resource) {
		if (!StringUtils.hasText(resource.getPermission())) {
			return true;
		}
		for (String permission : permissions) {
			WildcardPermission p1 = new WildcardPermission(permission);
			WildcardPermission p2 = new WildcardPermission(
					resource.getPermission());
			if (p1.implies(p2) || p2.implies(p1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据用户名查询用户的资源
	 * redis保存序列化菜单啊
	 * @param username
	 * @return
	 */
	public List<Resource> findUserResource(String username) {

		List<Resource> menus = new ArrayList<Resource>();
		Map<byte[], byte[]> temp=RedisClient.getJc().hgetAll(username.getBytes());

		Date d1=new Date();
		if ( temp.isEmpty()) {
			List<Resource> allResources = resourceMapper
					.findUserResource(username);
			for (Resource resource : allResources) {
				if (resource.isRootNode()) {
					continue;
				}
				if (!"menu".equals(resource.getType())) {
					continue;
				}
				/*
				 * if (!hasPermission(permissions, resource)) { continue; }
				 */
				menus.add(resource);
				RedisClient.getJc().hset(username.getBytes(), resource.getId().toString().getBytes(),SerializationUtil.serialize(resource));
				try {
					RedisClient.getJc().append("$$$"+username, JsonUtils.getJsonForString(resource));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(("redis")+((new Date()).getTime()-d1.getTime()));
			
		}else{
			Collection<	byte[]> valueset=temp.values();
			for(byte[] re:valueset){
				Resource resource=(Resource)SerializationUtil.deserialize(re);

				menus.add(resource);
			}
			System.out.println("serial"+((new Date()).getTime()-d1.getTime()));
			
		}
		return menus;
	}

	/**
	 * 获取分页数据
	 * 
	 * @param page
	 * @return
	 */
	public PageController getResourceAll(PageController page) {
		List<Resource> listresource = resourceMapper.findAll();
		if (listresource != null) {
			page.setContent(listresource);
		}
		return page;
	}

	/**
	 * 查询是否存在
	 * 
	 * @param resourceFlag
	 * @return
	 */
	public Resource isExistFlag(String resourceFlag) {
		return resourceMapper.isExistFlag(resourceFlag);
	}

	public List<Resource> queryResourceUucByTag(
			@Param("resourceId") String resourceId,
			@Param("searchName") String searchName,
			@Param("searchValue") String searchValue) {
		return resourceMapper.queryResourceUucByTag(resourceId, searchName,
				searchValue);
	}

	public List<Resource> queryResource(String resourceId) {
		if (resourceId.equals("")) {
			resourceId = "0";
		}
		List<Resource> list = resourceMapper.queryResource(Long
				.parseLong(resourceId));
		return list;
	}

	@Override
	public int deleteRoleResource(long resourceId) {
		// TODO Auto-generated method stub
		return resourceMapper.deleteRoleResource(resourceId);
	}

	/**
	 * 查询子节点
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<Resource> queryChildren(long resourceId) {
		return resourceMapper.queryChildren(resourceId);
	}

	public List<RoleResource> queryRoleResource(String resourceId, String roleId) {
		return resourceMapper.queryRoleResource(resourceId, roleId);
	}
}
