package com.sdu.samus.mapper;

import com.sdu.samus.model.Movie;

public interface MovieMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int deleteByPrimaryKey(Integer movieid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int insert(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int insertSelective(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    Movie selectByPrimaryKey(Integer movieid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int updateByPrimaryKeySelective(Movie record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table movie
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int updateByPrimaryKey(Movie record);
}