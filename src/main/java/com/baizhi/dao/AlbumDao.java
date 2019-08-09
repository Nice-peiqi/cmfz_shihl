package com.baizhi.dao;

import com.baizhi.entity.Album;

public interface AlbumDao extends DAO<Album> {
    /**
     * 后台：根据id查询专辑
     * @param id
     * @return
     */
    Album SelectOne(String id);
}
