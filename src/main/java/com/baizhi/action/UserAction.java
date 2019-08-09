package com.baizhi.action;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;
import com.baizhi.entity.City;
import com.baizhi.entity.Pro;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.PhoneMessageUtil;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserAction {

    @Resource
    private UserService userService;

    @RequestMapping("showPage")
    @ResponseBody
    public Map<String, Object> SeveByPage(Integer page, Integer rows) throws Exception {//测试git
        Map<String, Object> map = userService.QueryByPage(page, rows);
        return map;
    }

    @RequestMapping("modifierStatus")
    @ResponseBody
    public HashMap<String, Object> Modifier(User user) throws Exception {
        HashMap<String, Object> map = userService.ModifierStatus(user);
        return map;
    }

    @RequestMapping("outPoi")
    @ResponseBody
    public HashMap<String, Object> OutEasyPoi() throws Exception {

        HashMap<String, Object> map = new HashMap();

        List<User> users = userService.QueryAll();
        //G:/ideaProjects/Work/cmfz_shihl/src/main/webapp/upload/photo

        for (User user : users) {
            user.setPic_img("G:/ideaProjects/Work/cmfz_shihl/src/main/webapp/upload/photo/" + user.getPic_img());
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("持明法州有户表", "用户信息"), User.class, users);

        try {
            workbook.write(new FileOutputStream(new File("G:/ideaProjects/Work/cmfz_shihl/src/main/webapp/user.xls")));

            map.put("success", "200");
            map.put("error", "导出成功");

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", "400");
            map.put("error", "导出失败");
        }
        return map;
    }

    @RequestMapping("queryByMonth")
    @ResponseBody
    public Map<String, Object> QueryByMonth() throws Exception {

        HashMap<String, Object> map = new HashMap<>();

        List boys = userService.QueryByMonth("男");
        List girls = userService.QueryByMonth("女");

        map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月","7月","8月","9月","10月","11月","12月"));
        map.put("boys", boys);
        map.put("girls", girls);

        return map;
    }

    @RequestMapping("queryByCity")
    @ResponseBody
    public ArrayList<Pro> QueryByCity() throws Exception {

        List<City> boys = userService.QueryByCity("男");
        List<City> girls = userService.QueryByCity("女");


        Pro pro1 = new Pro("小姐姐",girls);
        Pro pro2 = new Pro("小哥哥",boys);

        ArrayList<Pro> pros = new ArrayList<>();
        pros.add(pro1);
        pros.add(pro2);

        return pros;
    }

    @RequestMapping("add")
    @ResponseBody
    public String add()throws Exception {
        userService.AddUser();

        List boys = userService.QueryByMonth("男");
        List girls = userService.QueryByMonth("女");

        JSONObject map = new JSONObject();
        map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月","7月","8月","9月","10月","11月","12月"));
        map.put("boys", boys);
        map.put("girls", girls);
        String content = map.toJSONString();


        //发布消息  发布地址，appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-41e63be8af434834b75e776b897845a9");
        //参数: 管道(标识)名称,发布的内容
        goEasy.publish("nice", content);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("testPhone")
    @ResponseBody
    public String TestPhoneCode(String phone)throws Exception{
        //生成随机验证码
        String code = PhoneMessageUtil.getRandomCode(6);
        //存在Session或者redis里以供数据校验
        System.out.println("短信验证码为:"+code);

        System.out.println("电话号码为："+phone);
        String phoneMsg = PhoneMessageUtil.getPhoneMsg(phone, code);
        System.out.println(phoneMsg);

        return null;
    }
}
