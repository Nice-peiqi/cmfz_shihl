package com.baizhi.service.impl;

import com.baizhi.Application;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BannerServiceImplTest {

    @Autowired
    private BannerService bannerService;

    @Test
    public void addBanner() {
        Banner b = new Banner();
        b.setTitle("耐克");
        b.setImg_path("timg.jpg");
        b.setDescription("代码全过");
        bannerService.AddBanner(b);
    }
}