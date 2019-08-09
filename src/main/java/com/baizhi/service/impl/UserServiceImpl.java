package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.City;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.MD5Utils;
import com.baizhi.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> QueryByPage(Integer pages, Integer rows) {
        Integer page = (pages-1)*rows;
        List<User> users = userDao.SelectByPage(page, rows);
        Map<String,Object> map = new HashMap();
        Integer counts = userDao.SelectBYTotal();

        Integer total=counts%rows==0?counts/rows:counts/rows+1;

        map.put("records",counts);
        map.put("total",total);
        map.put("page",pages);
        map.put("rows",users);

        return map;
    }

    @Override
    public HashMap<String, Object> ModifierStatus(User user) {
        HashMap<String, Object> map = new HashMap();
        try{
            userDao.Update(user);

            map.put("success","200");
            map.put("error","修改成功！");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success","400");
            map.put("error","修改失败！");
        }
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<User> QueryAll() {
        List<User> users = userDao.SelectAll();
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List QueryByMonth(String sex) {

        List list = new ArrayList();

        for (int i = 1; i <=12; i++) {
            if(i<10){
                list.add(userDao.SelectByMonth("%-0" + i + "-%", sex));
            }else if(i>=10){
                list.add(userDao.SelectByMonth("%-" + i + "-%", sex));
            }
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<City> QueryByCity(String sex) {
        List<City> cities = userDao.SelectByCity(sex);
        return cities;
    }

    @Override
    public void AddUser() {
        User user = new User();
        Random random = new Random();
        String phone = null;
        phone = "1"+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10)+""+random.nextInt(10);
        user.setPhone(phone);
        user.setId(UUIDUtil.getUUID());
        String salt = MD5Utils.getSalt();
        String password = salt+random.nextInt(6)+"";
        String pass = MD5Utils.getPassword(password);
        user.setPassword(pass);
        user.setSalt(salt);
        user.setPic_img("1564587104667-2019-04-29_180235.png");
        user.setAhama("测试数据");
        user.setName("测试数据");
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(2);
        if(i2==0){
            user.setSex("男");
        }else{
            user.setSex("女");
        }
        if(i1==1){
            user.setCity("山西");
        }else if(i1==0){
            user.setCity("新疆");
        }else if(i1==2){
            user.setCity("内蒙古");
        }else if(i1==3){
            user.setCity("甘肃");
        }else if(i1==4){
            user.setCity("陕西");
        }else if(i1==5){
            user.setCity("湖南");
        }else if(i1==6){
            user.setCity("云南");
        }else if(i1==7){
            user.setCity("山东");
        }else if(i1==8){
            user.setCity("江苏");
        }else if(i1==9) {
            user.setCity("安徽");
        }
        user.setSign("测试数据");
        user.setStatus("解除冻结");

        //获取随机日期
        Random rndYear=new Random();
        int year=rndYear.nextInt(20)+2000;  //生成[2000,2017]的整数；年
        Random rndMonth=new Random();
        int month=rndMonth.nextInt(12)+1;   //生成[1,12]的整数；月
        Random rndDay=new Random();
        int Day=rndDay.nextInt(30)+1;       //生成[1,30)的整数；日
        String s = year+"-"+month+"-"+Day;
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = f.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setReg_date(parse);
        user.setGuru_ld("1");
        userDao.Insert(user);
    }
}
