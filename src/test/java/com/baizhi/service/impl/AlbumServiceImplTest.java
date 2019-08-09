package com.baizhi.service.impl;

import com.baizhi.Application;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AlbumServiceImplTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumDao albumDao;

    @Test
    public void addAlbum() {
        Album a = new Album();
        a.setTitle("哈哈");
        a.setAuthor("666");
        a.setScore("44");
        a.setCover_img("2019-04-21_102413.png");
        a.setBroadcast("666");
        a.setCount(1);
        a.setCentent("4444");
        albumService.AddAlbum(a);
    }

    @Test
    public void queryByPage() {
       /* Map<String, Object> map = albumService.QueryByPage(1, 5);
        System.out.println(map);*/
        List<Album> albums = albumDao.SelectByPage(1, 5);
        for (Album album : albums) {
            System.out.println(album);
        }
    }
}