package com.sun.wen.lou.newtec.dto;

import com.sun.wen.lou.newtec.entity.UserAccount;

public class UserAccountDTO extends UserAccount {

	/**查询条件DTO
	 * 
	 */
	private static final long serialVersionUID = 4871218815394304078L;

	
	private String beginDate;
	private String endDate;

	public String getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
