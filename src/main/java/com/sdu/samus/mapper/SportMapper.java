package com.sdu.samus.mapper;

import com.sdu.samus.model.Sport;

public interface SportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int deleteByPrimaryKey(Integer sportid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int insert(Sport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int insertSelective(Sport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    Sport selectByPrimaryKey(Integer sportid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int updateByPrimaryKeySelective(Sport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sport
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int updateByPrimaryKey(Sport record);

    int deUpdate(String sportName);

    int inUpdate(String sportName);

    int hasSport(String sportName);
}