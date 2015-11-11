package com.sun.wen.lou.newtec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.wen.lou.newtec.dto.UserAccountDTO;
import com.sun.wen.lou.newtec.entity.UserAccount;
import com.sun.wen.lou.newtec.mapper.UserAccountMapper;
import com.sun.wen.lou.newtec.service.UserAccountService;
import com.sun.wen.lou.newtec.util.PageController;
@Service("UserAccountService")
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountMapper useraccountMapper;

	@Override
	public int save(UserAccountDTO UserAccount) {
		
		return useraccountMapper.save(UserAccount);
	}

	@Override
	public void addByBatch(List<UserAccountDTO> list) {
		useraccountMapper.addByBatch(list);
	}

	@Override
	public int update(UserAccountDTO UserAccount) {
		return useraccountMapper.update(UserAccount);
	}

	@Override
	public UserAccountDTO queryById(String UserAccountId) {
		return useraccountMapper.queryById(UserAccountId);
	}

	@Override
	public UserAccount queryByUserName(String username) {
		return useraccountMapper.queryByUserName(username);
	}

	@Override
	public PageController queryList(PageController page,
			UserAccountDTO UserAccountDTO) {
		
		 List<UserAccountDTO>list=	useraccountMapper.queryList(page, UserAccountDTO);
		 
		 if(list!=null){
			 page.setContent(list);
		 }
				
		return page;		
	}

	@Override
	public int deleteByIds(List<String> ids) {
		return useraccountMapper.deleteByIds(ids);
	}

	@Override
	public void updateSts(UserAccountDTO UserAccount) {
		useraccountMapper.updateSts(UserAccount);
	}

	@Override
	public List<UserAccount> queryListAll() {
		return useraccountMapper.queryListAll();
	}

	@Override
	public List<UserAccount> queryListForCommon(String uucid) {
		return useraccountMapper.queryListForCommon(uucid);
	}

	@Override
	public UserAccount queryUserAccount(UserAccount user) {
		return useraccountMapper.queryUserAccount(user);
	}
	
	
}
