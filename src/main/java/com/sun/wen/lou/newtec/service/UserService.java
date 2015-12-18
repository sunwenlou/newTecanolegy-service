package com.sun.wen.lou.newtec.service;


import java.util.List;
import java.util.Set;

import com.sun.wen.lou.newtec.entity.User;
import com.sun.wen.lou.newtec.entity.UserRole;
import com.sun.wen.lou.newtec.util.PageController;

public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    public int createUser(User user);

    public int updateUser(User user);

    public int deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);


    User findOne(Long userId);

    List<User> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
    
    /**分页获取数据
     * @param page
     * @param user
     * @return
     */
    public PageController getUsers(PageController page,User user);
    
    
    
    /**批量添加中间表
     * @param list
     */
    public void addByBatchUserRole(List<UserRole> list);
    
    /**删除中间表
     * @param user
     * @return
     */
    public  int deleteUserRole(User user);
	public User queryUserAccount(User user);

}
