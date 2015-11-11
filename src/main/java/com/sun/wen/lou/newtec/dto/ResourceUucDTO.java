package com.sun.wen.lou.newtec.dto;

import com.sun.wen.lou.newtec.entity.ResourceUuc;


/** 
 * <br>类 名: ResourceUucDTO 
 * <br>描 述: UUC资源实体类DTO
 */
public class ResourceUucDTO extends ResourceUuc {

	private static final long serialVersionUID = 167033844243405934L;

	/**
	 * 系统名称
	 */
	private String systemName = "统一用户中心";

	/**
	 * 操作
	 */
	private String opt;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}
}
