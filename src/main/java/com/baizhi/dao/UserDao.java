package com.baizhi.dao;

import com.baizhi.entity.City;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao extends DAO<User>{
    /**
     * 后台：根据月份和性别查询
     * @param month
     * @param sex
     * @return
     */
    Integer SelectByMonth(@Param("month") String month, @Param("sex") String sex);

    /**
     * 后台：根据城市和性别查询
     * @param sex
     * @return
     */
    List<City> SelectByCity(String sex);
}
