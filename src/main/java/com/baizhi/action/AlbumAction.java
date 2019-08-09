package com.baizhi.action;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumAction {

    @Autowired
    private AlbumService albumService;

    @RequestMapping("edit")
    @ResponseBody
    public String edit(Album album, String oper) throws Exception {
        String uid = null;
        if ("add".equals(oper)) {
            uid = albumService.AddAlbum(album);
        } else if ("del".equals(oper)) {
            albumService.RemoveById(album.getId());
        } else {
            uid = albumService.Modifier(album);
        }
        return uid;
    }

    //文件上传
    @RequestMapping("uploadAlbum")
    @ResponseBody
    public void uploadBanner(MultipartFile cover_img, String id, HttpServletRequest request) {

        System.out.println(cover_img);
        System.out.println(id);

        if (!cover_img.getOriginalFilename().isEmpty()) {
            //1.获取要上传文件夹的路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/img");

            //获取文件夹
            File file = new File(realPath);

            //创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }

            //获取上传文件的名字
            String filename = cover_img.getOriginalFilename();

            String name = new Date().getTime() + "-" + filename;

            //文件上传
            try {
                cover_img.transferTo(new File(realPath, name));
            } catch (IOException e) {
                e.printStackTrace();
            }


            Album a = new Album();
            a.setId(id);
            a.setCover_img(name);
            //做修改
            System.out.println("执行修改图片路径: " + a);
            albumService.Modifier(a);
        }

    }

    @RequestMapping("showPage")
    @ResponseBody
    public Map<String, Object> ShowAll(Integer page, Integer rows) throws Exception {
        Map<String, Object> map = albumService.QueryByPage(page,rows);
        return map;
    }
}
