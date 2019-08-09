package com.baizhi;

import com.baizhi.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestPoi {

    @Test
    public void test_1() {

        Student s1 = new Student("1", "小红", 11, "男", new Date());
        Student s2 = new Student("2", "小橙", 22, "女", new Date());
        Student s3 = new Student("3", "小蓝", 33, "男", new Date());
        Student s4 = new Student("4", "小绿", 44, "女", new Date());
        Student s5 = new Student("5", "小黑", 55, "男", new Date());
        Student s6 = new Student("6", "小白", 66, "女", new Date());
        Student s7 = new Student("7", "小紫", 77, "男", new Date());

        List<Student> s = Arrays.asList(s1, s2, s3, s4, s4, s5, s6, s7);


        //创建文档对象
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建工作簿
        HSSFSheet sheet = workbook.createSheet("学生信息表");

        //创建行
        HSSFRow row = sheet.createRow(0);

        //创建行内第一个单元格
        String[] table = {"id", "名字", "年龄", "性别", "生日"};

        for (int i = 0; i < table.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(table[i]);
        }
        //创建格式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //创建日期格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));

        for (int i = 0; i < s.size(); i++) {

            HSSFRow row1 = sheet.createRow(i + 1);

            row1.createCell(0).setCellValue(s.get(i).getId());
            row1.createCell(1).setCellValue(s.get(i).getName());
            row1.createCell(2).setCellValue(s.get(i).getAge());
            row1.createCell(3).setCellValue(s.get(i).getSex());
            Cell cell = row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(s.get(i).getBirthday());
        }

        try {
            workbook.write(new FileOutputStream(new File("G:/First.xls")));
            System.out.println("状态 = YES");


            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("状态 = NO");
        }

    }

    @Test
    public void test1() {
        try {
            Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("G:/First.xls")));

            Sheet sheet = workbook.getSheet("学生信息表");

            for (int i = 1; i < sheet.getLastRowNum(); i++) {

                Student s = new Student();

                //获取行
                Row row = sheet.getRow(i);
                //获取id
                s.setId(row.getCell(0).getStringCellValue());
                //获取Name
                s.setName(row.getCell(1).getStringCellValue());
                //获取Age
                s.setAge((int) row.getCell(2).getNumericCellValue());
                //获取Sex
                s.setSex(row.getCell(3).getStringCellValue());
                //获取Birtaday
                s.setBirthday(row.getCell(4).getDateCellValue());

                List<Student> list = Arrays.asList(s);

                for (Student student : list) {
                    System.out.println("学生信息为：" + student);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
