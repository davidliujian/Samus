package com.sdu.samus.dao;

import com.sdu.samus.mapper.CartoonMapper;
import com.sdu.samus.model.Cartoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartoonDao {
	@Autowired
	private CartoonMapper cartoonMapper;

	public int deUpdate(String cartoonName){
		return cartoonMapper.deUpdate(cartoonName);
	}

	public int inUpdate(String cartoonName){
		return cartoonMapper.inUpdate(cartoonName);
	}

	public int hasCartoon(String cartoonName){
		return cartoonMapper.hasCartoon(cartoonName);
	}

	public int insertCartoon(Cartoon cartoon){
		return cartoonMapper.insertSelective(cartoon);
	}
}
