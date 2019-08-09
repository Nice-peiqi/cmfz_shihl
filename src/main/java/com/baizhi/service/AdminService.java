package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AdminService {

    /**
     * 后台:登录方法
     * @param admin
     * @return
     */
    HashMap<String,Object> Login(Admin admin, String code, HttpServletRequest request);
}
