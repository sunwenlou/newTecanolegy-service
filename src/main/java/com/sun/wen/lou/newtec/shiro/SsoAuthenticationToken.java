package com.sun.wen.lou.newtec.shiro;

import org.apache.shiro.authc.AuthenticationToken;


/**
 * 认证凭据类
 */
public class SsoAuthenticationToken implements AuthenticationToken {

	
	private static final long serialVersionUID = 1L;

	private String username;
	
	private Object CredentialInfo;
	
	public SsoAuthenticationToken() {

	}

	public SsoAuthenticationToken(String username) {
		this.username = username;
	}
	

	public SsoAuthenticationToken(String username, Object credentialInfo) {
		this.username = username;
		CredentialInfo = credentialInfo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Object getCredentialInfo() {
		return CredentialInfo;
	}

	public void setCredentialInfo(Object credentialInfo) {
		CredentialInfo = credentialInfo;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		//return null;
		return this.username;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

    public void clear(){
        this.username = null;
    }

    public String toString(){
        return "userName="+this.username;
    }
}
