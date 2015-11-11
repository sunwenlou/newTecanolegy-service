package com.sun.wen.lou.newtec.util;

import org.springframework.jdbc.core.CallableStatementCallback;

/**
 * 
 * 类 名: CallableStatementCallbackImpl<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 作 者: huangyongtao<br/>
 * 版 本：<br/>
 *
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public abstract class CallableStatementCallbackImpl implements
		CallableStatementCallback {
	private Object parameterObject;

	public Object getParameterObject() {
		return this.parameterObject;
	}

	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}
}