package com.sdu.samus.model;

public class Cartoon {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cartoon.cartoonId
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    private Integer cartoonid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cartoon.cartoonName
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    private String cartoonname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cartoon.cartoonCount
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    private Integer cartooncount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cartoon.cartoonId
     *
     * @return the value of cartoon.cartoonId
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public Integer getCartoonid() {
        return cartoonid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cartoon.cartoonId
     *
     * @param cartoonid the value for cartoon.cartoonId
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public void setCartoonid(Integer cartoonid) {
        this.cartoonid = cartoonid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cartoon.cartoonName
     *
     * @return the value of cartoon.cartoonName
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public String getCartoonname() {
        return cartoonname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cartoon.cartoonName
     *
     * @param cartoonname the value for cartoon.cartoonName
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public void setCartoonname(String cartoonname) {
        this.cartoonname = cartoonname == null ? null : cartoonname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cartoon.cartoonCount
     *
     * @return the value of cartoon.cartoonCount
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public Integer getCartooncount() {
        return cartooncount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cartoon.cartoonCount
     *
     * @param cartooncount the value for cartoon.cartoonCount
     *
     * @mbg.generated Mon Apr 23 19:07:20 CST 2018
     */
    public void setCartooncount(Integer cartooncount) {
        this.cartooncount = cartooncount;
    }
}