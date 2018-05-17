package com.sdu.samus.dao;

import com.sdu.samus.mapper.UserRelationshipMapper;
import com.sdu.samus.model.UserRelationship;
import com.sdu.samus.model.UserRelationshipWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class UserRelationshipDao {
	@Autowired
	private UserRelationshipMapper userRelationshipMapper;

	public UserRelationshipWithBLOBs findUserRelationship(String userId) throws DataAccessException {
		return userRelationshipMapper.selectByPrimaryKey(userId);
	}

	public int registerRelationship(UserRelationshipWithBLOBs userRelationship) throws DataAccessException{
		return userRelationshipMapper.insertSelective(userRelationship);
	}

	public int setActive(UserRelationshipWithBLOBs userRelationshipWithBLOBs){
		return userRelationshipMapper.updateByPrimaryKeySelective(userRelationshipWithBLOBs);
	}

	public int updateDislike(String userid){
		return userRelationshipMapper.updateDislikeByPrimaryKey(userid);
	}

	public int updateLike(String userid , String hobbyCount,String like){
		return userRelationshipMapper.updateLikeByPrimaryKey(userid,hobbyCount,like);
	}

	public int update(UserRelationshipWithBLOBs userRelationship){
		return userRelationshipMapper.updateByPrimaryKeySelective(userRelationship);
	}
}
