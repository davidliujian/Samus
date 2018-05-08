package com.sdu.samus.dao;

import com.sdu.samus.mapper.IdolMapper;
import com.sdu.samus.model.Idol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IdolDao {
	@Autowired
	private IdolMapper idolMapper;

	public int deUpdate(String idolName){
		return idolMapper.deUpdate(idolName);
	}

	public int inUpdate(String idolName){
		return idolMapper.inUpdate(idolName);
	}

	public int hasIdol(String idolName){
		return idolMapper.hasIdol(idolName);
	}

	public int insertIdol(Idol idol){
		return idolMapper.insertSelective(idol);
	}
}
