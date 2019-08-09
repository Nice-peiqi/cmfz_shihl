package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {

    /**
     * 后台：添加轮播图
     * @param banner
     * @returnvoid
     */
     void InsertBanner(Banner banner);

    /**
     * 后台：删除轮播图（假删除，修改图片状态）或者修改描述
     * @param banner
     */
    void UpdateById(Banner banner);

    /**
     * 后台：分页之后展示的数据
     * @param page
     * @param rows
     * @return
     */
    List<Banner> SelectAll(@Param("page") Integer page, @Param("rows") Integer rows);

    /**
     * 后台：图片的总数
     * @return
     */
    Integer SelectTotalCount();

    /**
     * 后台：根据id查询数据
     * @param id
     * @return
     */
    Banner SelectById(String id);

    /**
     * 后台：根据id删除
     * @param id
     */
    void DelectById(String id);
}
