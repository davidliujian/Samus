package com.sdu.samus.mapper;

import com.sdu.samus.model.Game;

public interface GameMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int deleteByPrimaryKey(Integer gameid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int insert(Game record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int insertSelective(Game record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    Game selectByPrimaryKey(Integer gameid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int updateByPrimaryKeySelective(Game record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int updateByPrimaryKey(Game record);
}