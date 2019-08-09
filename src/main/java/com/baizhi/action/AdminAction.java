package com.baizhi.action;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminAction {

    @Autowired
    private AdminService adminService;

    @RequestMapping("code")
        public void  Code(HttpSession session,HttpServletResponse response) throws Exception {
        String securityCode = ImageCodeUtil.getSecurityCode();
        session.setAttribute("code", securityCode);
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        ServletOutputStream stream = null;
        try{
            stream = response.getOutputStream();
            ImageIO.write(image,"png",stream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("login")
    @ResponseBody
    public HashMap<String, Object> Login(Admin admin, String code, HttpServletRequest request) throws Exception {
        HashMap<String, Object> map = adminService.Login(admin, code, request);
        return map;
    }

    @RequestMapping("exit")
    public String LoginExit(HttpSession session)throws Exception{
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}
