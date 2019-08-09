package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public HashMap<String, Object> Login(Admin admin, String code, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        Admin a = null;
        a = adminDao.SelectByusername(admin.getUsername());
        String yzm = (String) request.getSession().getAttribute("code");
        if (yzm.equals(code)) {
            if (a!=null) {
                if (admin.getPassword().equals(a.getPassword())) {
                    map.put("success", "200");
                    request.getSession().setAttribute("admin",a);
                } else {
                    map.put("success", "400");
                    map.put("error", "*密码输入错误！");
                }
            } else {
                map.put("success", "400");
                map.put("error", "*该用户不是管理员！");
            }
        } else {
            map.put("success", "400");
            map.put("error", "*验证码错误！");
        }
        return map;
    }
}
