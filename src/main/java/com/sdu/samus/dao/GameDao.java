package com.sdu.samus.dao;

import com.sdu.samus.mapper.GameMapper;
import com.sdu.samus.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameDao {
	@Autowired
	private GameMapper gameMapper;

	public int deUpdate(String gameName){
		return gameMapper.deUpdate(gameName);
	}

	public int inUpdate(String gameName){
		return gameMapper.inUpdate(gameName);
	}

	public int hasGame(String gameName){
		return gameMapper.hasGame(gameName);
	}

	public int insertGame(Game game){
		return gameMapper.insertSelective(game);
	}
}
