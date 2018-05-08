package com.sdu.samus.dao;

import com.sdu.samus.mapper.MovieMapper;
import com.sdu.samus.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDao {
	@Autowired
	private MovieMapper movieMapper;
	public int deUpdate(String movieName){
		return movieMapper.deUpdate(movieName);
	}

	public int inUpdate(String movieName){
		return movieMapper.inUpdate(movieName);
	}

	public int hasMovie(String movieName){
		return movieMapper.hasMovie(movieName);
	}

	public int insertMovie(Movie movie){
		return movieMapper.insertSelective(movie);
	}
}
