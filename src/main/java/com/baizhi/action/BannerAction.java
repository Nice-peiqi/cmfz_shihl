package com.baizhi.action;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerAction {

    @Resource
    private BannerService bannerService;

    @RequestMapping("edit")
    @ResponseBody
    public String edit(Banner banner, String oper) throws Exception {
        String uid = null;
        if ("add".equals(oper)) {
            uid = bannerService.AddBanner(banner);
        } else if ("del".equals(oper)) {
            bannerService.RemoveById(banner);
        } else {
            uid = bannerService.Modifier(banner);
        }
        return uid;
    }

    //文件上传
    @RequestMapping("uploadBanner")
    @ResponseBody
    public void uploadBanner(MultipartFile img_path, String id, HttpServletRequest request) {

        System.out.println(img_path);
        System.out.println(id);

        if (!img_path.getOriginalFilename().isEmpty()) {
            //1.获取要上传文件夹的路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

            //获取文件夹
            File file = new File(realPath);

            //创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }

            //获取上传文件的名字
            String filename = img_path.getOriginalFilename();

            String name = new Date().getTime() + "-" + filename;

            //文件上传
            try {
                img_path.transferTo(new File(realPath, name));
            } catch (IOException e) {
                e.printStackTrace();
            }


            Banner b = new Banner();
            b.setId(id);
            b.setImg_path(name);
            //做修改
            System.out.println("执行修改图片路径: " + b);
            bannerService.updateByimg_path(b);
        }

    }

    @RequestMapping("showPage")
    @ResponseBody
    public Map<String, Object> ShowAll(Integer page, Integer rows) throws Exception {
        Map<String, Object> map = bannerService.ShowAll(page, rows);
        return map;
    }

    @RequestMapping("updateStatus")
    @ResponseBody
    public Map<String, Object> updateStatus(Banner banner) throws Exception {
        HashMap<String, Object> map = bannerService.Remove(banner);

        return map;
    }
}
