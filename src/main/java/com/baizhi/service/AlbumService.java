package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    /**
     * 后台：分页展示
     * @param pages
     * @param rows
     * @return
     */
    Map<String,Object> QueryByPage(Integer pages,Integer rows);

    /**
     * 后台：增加专辑
     * @param album
     * @return
     */
    String AddAlbum(Album album);

    /**
     * 后台：删除专辑
     * @param id
     */
    void RemoveById(String id);

    /**
     * 后台：修改专辑信息
     * @param album
     * @return
     */
    String Modifier(Album album);

}
