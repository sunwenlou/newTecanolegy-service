package com.sun.wen.lou.newtec.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sun.wen.lou.newtec.entity.Resource;
import com.sun.wen.lou.newtec.entity.User;
import com.sun.wen.lou.newtec.entity.UserRole;
import com.sun.wen.lou.newtec.mapper.ResourceMapper;
import com.sun.wen.lou.newtec.mapper.RoleMapper;
import com.sun.wen.lou.newtec.mapper.UserMapper;
import com.sun.wen.lou.newtec.service.UserService;
import com.sun.wen.lou.newtec.util.PageController;

@Service("UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ResourceMapper resourceMapper;

	/**
	 * 创建用户
	 * 
	 * @param user
	 */
	public int createUser(User user) {
		// 加密密码
		return userMapper.createUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateUser(user);
	}

	@Override
	public int deleteUser(Long userId) {
		return userMapper.deleteUser(userId);
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword) {
		User user = userMapper.findOne(userId);
		user.setPassword(newPassword);
		userMapper.updateUser(user);
	}

	@Override
	public User findOne(Long userId) {
		return userMapper.findOne(userId);
	}

	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username) {
		User user = findByUsername(username);
		if (user == null) {
			return Collections.EMPTY_SET;
		}
		List<String> roleIdlist = new ArrayList<String>();
		for (String str : user.getRoleIdsStr().split(",")) {
			roleIdlist.add(str);
		}
		return roleMapper.findRoles(roleIdlist);
	}

	/**
	 * 根据用户名查找其权限
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username) {
		User user = findByUsername(username);
		if (user == null) {
			return Collections.EMPTY_SET;
		}
		Set<String> permissions = new HashSet<String>();
		for (String resourceId : user.getRoleIdsStr().split(",")) {
			Resource resource = resourceMapper.findOne(Long
					.parseLong(resourceId));
			if (resource != null
					&& StringUtils.hasText(resource.getPermission())) {
				permissions.add(resource.getPermission());
			}
		}
		return permissions;
	}

	@Override
	public PageController getUsers(PageController page, User user) {
		List<User> userlist = userMapper.getUsers(page, user);
		Map<Long, User> usermap = new TreeMap<Long, User>();
		// 同一个用户记录多个角色
		for (User u : userlist) {
			if (usermap.keySet().size() == 0) {
				usermap.put(u.getId(), u);
				continue;
			}
			if (usermap.keySet().contains(u.getId())) {

				u.setRoleNames(u.getRoleNames() + ","
						+ usermap.get(u.getId()).getRoleNames());
				usermap.put(u.getId(), u);
			}else{
				usermap.put(u.getId(), u);
			}
		}
		userlist.clear();
		userlist.addAll(usermap.values());
		if (null != userlist) {
			page.setContent(userlist);
		}

		return page;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < 100000; i++) {
			list.add(i + "");
			set.add(i + "");
		}
		Date d = new Date();
		for (String str : list) {
			System.out.println(System.currentTimeMillis() + "==" + str);
		}

		Date dd = new Date();
		long listint = dd.getTime() - d.getTime();
		System.out.println(listint);
		for (String str : set) {
			System.out.println(System.currentTimeMillis() + "==" + str);
		}
		Date ddd = new Date();
		long setint = ddd.getTime() - dd.getTime();
		System.out.println(setint);
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {

			System.out.println(System.currentTimeMillis() + "==" + it.next());
		}
		Date dddd = new Date();
		long ite = dddd.getTime() - ddd.getTime();
		System.out.println(ite);

		System.out.println("list time is: " + listint + " set time is: "
				+ setint + " iterator time is: " + ite);
	}

	/**
	 * 批量添加中间表
	 * 
	 * @param list
	 */
	public void addByBatchUserRole(List<UserRole> list) {

		userMapper.addByBatchUserRole(list);
	}
    
    /**删除中间表
     * @param user
     * @return
     */
    public  int deleteUserRole(User user){
    	return userMapper.deleteUserRole(user);
    }
}
