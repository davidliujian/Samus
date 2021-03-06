package com.sdu.samus.dao;

import com.sdu.samus.mapper.SchoolMapper;
import com.sdu.samus.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolDao {
	@Autowired
	private SchoolMapper schoolMapper;

	public School findSchoolByName(String schoolname)throws DataAccessException {
		return schoolMapper.selectBySchoolName(schoolname);
	}

	public School findSchoolBySchoolId5(String schoolId5) throws DataAccessException{
		return schoolMapper.selectByPrimaryKey(schoolId5);
	}

	public School findSchoolById5(String schoolid5)throws DataAccessException {
		return schoolMapper.selectByPrimaryKey(schoolid5);
	}

}
