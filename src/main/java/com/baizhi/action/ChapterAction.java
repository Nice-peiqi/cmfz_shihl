package com.baizhi.action;


import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterAction {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("edit")
    @ResponseBody
    public String edit(Chapter chapter, String oper,String albumId) throws Exception {
        String uid = null;
        if ("add".equals(oper)) {
            chapter.setAlbum_id(albumId);
            System.out.println("信息+"+chapter);
            uid = chapterService.AddChapter(chapter,albumId);
        } else if ("del".equals(oper)) {
            chapterService.RemoveById(chapter.getId(),albumId);
        } else {
            uid = chapterService.Modifier(chapter);
        }
        return uid;
    }

    //文件上传
    @RequestMapping("uploadChapter")
    @ResponseBody
    public void uploadBanner(MultipartFile url, String id, HttpServletRequest request) {

        System.out.println(url);
        System.out.println(id);

        if (!url.getOriginalFilename().isEmpty()) {
            //1.获取要上传文件夹的路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/music");

            //获取文件夹
            File file = new File(realPath);

            //创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }

            //获取上传文件的名字
            String filename = url.getOriginalFilename();

            String name = new Date().getTime() + "-" + filename;

            //文件上传
            try {
                url.transferTo(new File(realPath, name));
            } catch (IOException e) {
                e.printStackTrace();
            }


            Chapter c = new Chapter();
            c.setId(id);
            c.setUrl(name);
            //获取文件大小-1
            long size = url.getSize();
            String sizes =size/1024/1024+"MB";
            //获取音频大小
            DecimalFormat format = new DecimalFormat("0.00");
            String str = String.valueOf(size);
            Double dd = Double.valueOf(str)/1024/1024;
            String d2 = format.format(dd)+"MB";
            System.out.println("此音频大小为:" +d2+"MB");
            c.setSize(d2+"MB");

            //获取下载的目标文件
            File f = new File(realPath + "/" + name);
            //获取文件时长
            Encoder encoder = new Encoder();
            try {
                MultimediaInfo m = encoder.getInfo(f);
                long ls = m.getDuration();
                long second = ls /1000;
                long M  = second%60;
                long S  = second/60;
                System.out.println("此音频时长为:" +S+"分"+M+"秒");
                c.setDuration(S+"分"+M+"秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //做修改
            System.out.println("执行修改图片路径: " + c);
            chapterService.Modifier(c);
        }

    }

    @RequestMapping("showPage")
    @ResponseBody
    public Map<String, Object> ShowAll(Integer page, Integer rows,String albumId) throws Exception {
        Map<String, Object> map = chapterService.QueryByPage(page,rows,albumId);
        return map;
    }

    //第一种
    @RequestMapping("/dawnload")         //下载文件名   //接收要下载的文件名
    public ResponseEntity<byte[]> download(String  filename, HttpSession session) throws  Exception{
        System.out.println(filename);
        //获取目标文件所在文件夹的路径
        String realPath = session.getServletContext().getRealPath("/upload/music");
        //获取下载的目标文件
        File file = new File(realPath + "/" + filename);
        //把目标文件转换为字节数组
        byte[] bytes = FileUtils.readFileToByteArray(file);
        //文件下载的请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        //解决文件下载过后乱码问题
        String downname = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        //指定下载过后文件打开的形式  附件
        httpHeaders.setContentDispositionFormData("attachment",downname);
        //指定下载文件时  用什么样的形式           二进制流的形式
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return  new ResponseEntity<byte[]>(bytes,httpHeaders, HttpStatus.CREATED);
    }
}
