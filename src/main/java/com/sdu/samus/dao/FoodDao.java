package com.sdu.samus.dao;

import com.sdu.samus.mapper.FoodMapper;
import com.sdu.samus.model.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FoodDao {
	@Autowired
	private FoodMapper foodMapper;

	public int deUpdate(String foodName){
		return foodMapper.deUpdate(foodName);
	}

	public int inUpdate(String foodName){
		return foodMapper.inUpdate(foodName);
	}

	public int hasFood(String foodName){
		return foodMapper.hasFood(foodName);
	}

	public int insertFood(Food food){
		return foodMapper.insertSelective(food);
	}
}
