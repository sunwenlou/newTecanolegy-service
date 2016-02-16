package com.sun.wen.lou.newtec.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;

import com.sun.wen.lou.newtec.shiro.SecurityRealm;


/**
 * 自定义单点登录用授权验证类
 */
public class ShiroSsoRealm extends SecurityRealm {

	public ShiroSsoRealm(){
		//重写shiro密码验证，使用自定义验证方法
		setCredentialsMatcher(new AllowAllCredentialsMatcher());
		setAuthenticationTokenClass(SsoAuthenticationToken.class);
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		// 认证前调用
		SsoAuthenticationToken upToken = (SsoAuthenticationToken) authcToken;
		String username = upToken.getUsername();
		
		if (null != username) {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
					upToken.getPrincipal(), upToken.getCredentials(), getName());
			//CommonSecurityUtils.setCurrentUser(credentialInfo);
			return authcInfo;
		}
		return null;
	}
}
