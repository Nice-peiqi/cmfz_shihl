package com.baizhi.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class UploadUtil {

    public static void getUpload(MultipartFile fileName,HttpServletRequest request,String path) {

        String realPath = getRealPath(request,path);

        File file = new File(realPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        //获取文件名
        String filename = fileName.getOriginalFilename();
        //给文件名加上时间戳
        String name = new Date().getTime() + "-" + filename;

        //文件上传
        try {
            fileName.transferTo(new File(realPath, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取文件上传的目标目录 路径
    private static String getRealPath(HttpServletRequest request,String path){
        return request.getSession().getServletContext().getRealPath(path);
    }
}
