package com.sdu.samus.dao;

import com.sdu.samus.mapper.UserInfoMapper;
import com.sdu.samus.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Autowired
	private UserInfoMapper userInfoMapper;

	public UserInfo findUserInfoById(String id) throws DataAccessException {
		return userInfoMapper.selectByPrimaryKey(id);
	}

	public int registerUser(UserInfo userInfo) throws DataAccessException{
		return userInfoMapper.insertSelective(userInfo);
	}
}
