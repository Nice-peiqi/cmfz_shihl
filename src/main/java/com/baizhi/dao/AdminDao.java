package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    /**
     * 后台：通过有户名查询管理员
     * @param username
     * @return Admin
     */
    Admin SelectByusername(@Param("username") String username);
}
