package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.HashMap;
import java.util.Map;

public interface BannerService {
    /**
     * 后台：增加轮播图方法
     * @param banner
     * @return
     */
    String AddBanner(Banner banner);

    /**
     * 后台：假删除轮播图
     * @param banner
     */
    HashMap<String,Object> Remove(Banner banner);

    /**
     * 后台：修改轮播图描述的方法
     * @param banner
     */
    String Modifier(Banner banner);

    /**
     * 后台：查询全部数据
     * @param page
     * @param rows
     * @return
     */
    Map<String , Object> ShowAll(Integer page, Integer rows);

    /**
     * 后台：后台修改图片路径
     * @param banner
     */
    void updateByimg_path(Banner banner);

    /**
     * 后台:删除图片
     * @param banner
     */
    void RemoveById(Banner banner);
}
