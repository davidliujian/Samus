package com.sdu.samus.mapper;

import com.sdu.samus.model.Idol;

public interface IdolMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table idol
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int deleteByPrimaryKey(Integer idolid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table idol
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int insert(Idol record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table idol
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int insertSelective(Idol record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table idol
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    Idol selectByPrimaryKey(Integer idolid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table idol
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int updateByPrimaryKeySelective(Idol record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table idol
     *
     * @mbg.generated Sun May 06 10:37:18 CST 2018
     */
    int updateByPrimaryKey(Idol record);
}