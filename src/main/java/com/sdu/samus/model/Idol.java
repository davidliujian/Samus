package com.sdu.samus.model;

public class Idol {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column idol.idolId
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    private Integer idolid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column idol.idolName
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    private String idolname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column idol.idolCount
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    private Integer idolcount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column idol.idolId
     *
     * @return the value of idol.idolId
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public Integer getIdolid() {
        return idolid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column idol.idolId
     *
     * @param idolid the value for idol.idolId
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public void setIdolid(Integer idolid) {
        this.idolid = idolid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column idol.idolName
     *
     * @return the value of idol.idolName
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public String getIdolname() {
        return idolname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column idol.idolName
     *
     * @param idolname the value for idol.idolName
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public void setIdolname(String idolname) {
        this.idolname = idolname == null ? null : idolname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column idol.idolCount
     *
     * @return the value of idol.idolCount
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public Integer getIdolcount() {
        return idolcount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column idol.idolCount
     *
     * @param idolcount the value for idol.idolCount
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public void setIdolcount(Integer idolcount) {
        this.idolcount = idolcount;
    }
}