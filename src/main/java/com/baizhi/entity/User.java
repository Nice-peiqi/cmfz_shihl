package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    @Excel(name="ID",width = 20)
    private String id;
    @Excel(name="手机号",width = 20)
    private String phone;
    @Excel(name="密码",width = 20)
    private String password;
    @ExcelIgnore
    private String salt;
    @Excel(name="头像",type = 2,width = 20)
    private String pic_img;
    @Excel(name="法名",width = 20)
    private String ahama;
    @Excel(name="姓名",width = 20)
    private String name;
    @Excel(name="性别",width = 20)
    private String sex;
    @Excel(name="地址",width = 20)
    private String city;
    @Excel(name="签名",width = 20)
    private String sign;
    @Excel(name="状态",width = 20)
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name="注册时间",format = "yyyy-MM-dd",width = 20)
    private Date reg_date;
    @Excel(name="所属上师ID",width = 20)
    private String guru_ld;


}
