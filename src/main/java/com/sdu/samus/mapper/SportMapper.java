package com.sdu.samus.mapper;

import com.sdu.samus.model.Sport;

public interface SportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int deleteByPrimaryKey(Integer sportid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int insert(Sport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int insertSelective(Sport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    Sport selectByPrimaryKey(Integer sportid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int updateByPrimaryKeySelective(Sport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    int updateByPrimaryKey(Sport record);
}