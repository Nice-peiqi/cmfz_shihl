package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao extends DAO<Chapter> {
    /**
     * 分页展示根据专辑id
     * @param albumId
     * @param start
     * @param rows
     * @return
     */
    List<Chapter> queryByPage(@Param("albumId") String albumId, @Param("start") Integer start, @Param("rows") Integer rows);

    /**
     * 查询总条数
     * @param albumId
     * @return
     */
    Integer queryByTotal(String albumId);
}
