package com.sun.wen.lou.newtec.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * shiro工具类
 */
public class CommonSecurityUtils {
	
	private static Logger log = LoggerFactory.getLogger(CommonSecurityUtils.class);
	
    public final static String CURRENT_USER = "CURRENT_USER";

    /**
     * 注册当前用户
     *
     * @param user
     */
    public static final void setCurrentUser(SecurityUsers user) {
        if (null != user) {
            setSession(CURRENT_USER, user);
        }
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static final SecurityUsers getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
            	SecurityUsers user = (SecurityUsers) session.getAttribute(CURRENT_USER);
                if (null != user) {
                    return user;
                }
            }
        }
        return null;
    }


    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    public static void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            log.debug("Shiro 的 Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}
