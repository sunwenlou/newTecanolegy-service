package com.sun.wen.lou.newtec.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.wen.lou.newtec.util.PageController;
import com.sun.wen.lou.newtec.dto.UserAccountDTO;
import com.sun.wen.lou.newtec.entity.UserAccount;

public interface UserAccountMapper {

	public int save(UserAccountDTO UserAccount);
	//批量新增
	public void addByBatch(List<UserAccountDTO> list);

	public int update(UserAccountDTO UserAccount);

	public UserAccountDTO queryById(String UserAccountId);

	public UserAccount queryByUserName(String username);

	public List<UserAccountDTO> queryList(@Param("page") PageController page,
			@Param("UserAccountDTO") UserAccountDTO UserAccountDTO);

	public int deleteByIds(List<String> ids);

	public void updateSts(UserAccountDTO UserAccount);

	public List<UserAccount> queryListAll();

	public List<UserAccount> queryListForCommon(@Param("uucid") String uucid);
	public UserAccount queryUserAccount(UserAccount user);

}
