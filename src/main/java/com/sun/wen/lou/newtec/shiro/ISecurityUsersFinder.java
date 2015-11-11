package com.sun.wen.lou.newtec.shiro;

import java.util.List;


/**
 * 角色权限查询接口
 */
public interface ISecurityUsersFinder {

	/**
	 * 根据用户名查询校验用户
	 * @param userName 用户名
	 * @return 校验
	 */
	SecurityUsers getSecurityUsers(String userName);
	
	/**
	 * 根据用户ID查询用户角色权限
	 * @param userId 用户ID
	 * @return
	 */
	PurviewUsers getPurviewUsers(String userId);
	
	/**
	 * 查询所有权限(资源/菜单/操作)的名称和请求路径
	 * @return
	 */
	List<PurviewInfo> getAllPurivewInfos();
}