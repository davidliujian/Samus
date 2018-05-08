package com.sdu.samus.mapper;

import com.sdu.samus.model.Travel;

public interface TravelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table travel
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int deleteByPrimaryKey(Integer travelid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table travel
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int insert(Travel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table travel
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int insertSelective(Travel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table travel
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    Travel selectByPrimaryKey(Integer travelid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table travel
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int updateByPrimaryKeySelective(Travel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table travel
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int updateByPrimaryKey(Travel record);

    int deUpdate(String travelName);

    int inUpdate(String travelName);

    int hasTravel(String travelName);
}