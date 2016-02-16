package com.sun.wen.lou.newtec.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 授权认证核心类
 */
public class SecurityRealm extends AuthorizingRealm {
    
	private ISecurityUsersFinder securityUsersFinder;

    // 用户登录认证
    protected AuthenticationInfo doGetAuthenticationInfo(
        AuthenticationToken authcToken) throws AuthenticationException {

        // 认证前调用
        UsernamePasswordToken upToken = (UsernamePasswordToken) authcToken;
        String username = upToken.getUsername();
        SecurityUsers user = securityUsersFinder.getSecurityUsers(username);
        if (null != user) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                user.getLoginName(), user.getPassword(), getName());
            if (user.getDelStatus() == 0) {
                // 注册当前用户
                CommonSecurityUtils.setCurrentUser(user);
            } else {
               /* throw new DelSourceException("用户已删除！");*/
            }
            return authcInfo;
        }
        return null;
    }

    // 获取认证后信息：用户的角色，享有的权限
    protected AuthorizationInfo doGetAuthorizationInfo(
        PrincipalCollection principals) {
        // 获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        //String currentUsername = (String) super.getAvailablePrincipal(principals);
    	SecurityUsers se = (SecurityUsers) CommonSecurityUtils.getCurrentUser();
        String userSign = se.getLoginName();
        PurviewUsers user = null;
        // 从数据库中获取当前登录用户的详细信息
        if (userSign != null && !"".equals(userSign)) {
            user = securityUsersFinder.getPurviewUsers(userSign);
        }
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        if (null != user) {
            // 用户的的角色信息
            simpleAuthorInfo.addRoles(user.getRoles());
            simpleAuthorInfo.addStringPermissions(user.getPermissions());
            // 获取权限
        } else {
            //throw new AuthorizationException();
            return null;
        }

        return simpleAuthorInfo;
    }

    /**
     * 清除所有用户授权信息缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    public void setSecurityUsersFinder(ISecurityUsersFinder securityUsersFinder) {
        this.securityUsersFinder = securityUsersFinder;
    }
}
