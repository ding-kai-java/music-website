package com.example.demo.dao;

import com.example.demo.domain.MusicOrder;

public interface MusicOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table music_order
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table music_order
     *
     * @mbggenerated
     */
    int insert(MusicOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table music_order
     *
     * @mbggenerated
     */
    int insertSelective(MusicOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table music_order
     *
     * @mbggenerated
     */
    MusicOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table music_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MusicOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table music_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MusicOrder record);
}