package com.sun.wen.lou.newtec.entity;

import java.io.Serializable;
import java.util.Date;

public class UserAccount implements Serializable {
	/** **/
	private static final long serialVersionUID = 5741852102358678529L;
	private String userAccountId;
	private String userAccountName;
	private String pwd;
	/** 所属部门 **/
	private String dept;
	/** 所属公司 **/
	private String company;
	/** 创建时间 **/
	private Date createDate;
	/** 状态 :A 正常， P  停用  ，D 禁用 **/
	private String sts;
	/** 是否管理员 Y是 N否 A超级管理员 **/
	private String isAdmin;
	/**
	 * 职位名称
	 */
	private String jobName;
	/**
	 * Email
	 */
	private String email;

	public String getDept() {
		return dept;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setDept(String dept) {
		this.dept = (dept == null ? "" : dept.trim());
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = (company == null ? "" : company.trim());
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * 获取 职位名称
	 * 
	 * @return jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * 设置 职位名称
	 * 
	 * @param jobName
	 */
	public void setJobName(String jobName) {
		this.jobName = (jobName == null ? "" : jobName.trim());
	}

	/**
	 * 获取 Email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置 Email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = (email == null ? "" : email.trim());
	}

	public String getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(String userAccountId) {
		this.userAccountId = userAccountId == null ? "" : userAccountId.trim();
	}

	public String getUserAccountName() {
		return userAccountName;
	}

	public void setUserAccountName(String userAccountName) {
		this.userAccountName = userAccountName == null ? "" : userAccountName
				.trim();
	}

	public UserAccount() {
		
	}

}