package com.baizhi.service;

import com.baizhi.entity.City;
import com.baizhi.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 后台：分页展示用户
     * @param pages
     * @param rows
     * @return
     */
    Map<String,Object> QueryByPage(Integer pages,Integer rows);

    /**
     * 后台：修改用户状态
     * @param user
     */
    HashMap<String, Object> ModifierStatus(User user);

    /**
     * 后台：查询全部用用户
     * @return
     */
    List<User> QueryAll();

    /**
     * 后台：根据月份和性别查询
     * @param sex
     * @return
     */
    List QueryByMonth(String sex);

    /**
     * 后台：根据城市和性别查询
     * @param sex
     * @return
     */
    List<City> QueryByCity(String sex);

    /**
     * 后台：增加用户测试数据
     * @param
     */
    void AddUser();
}
