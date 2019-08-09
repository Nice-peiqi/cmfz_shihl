package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    /**
     * 后台：分页展示
     * @param pages
     * @param rows
     * @return
     */
    Map<String,Object> QueryByPage(Integer pages, Integer rows,String albumId);

    /**
     * 后台：增加专辑
     * @param chapter
     * @return
     */
    String AddChapter(Chapter chapter,String albumId);

    /**
     * 后台：删除专辑
     * @param id
     */
    void RemoveById(String id,String albumId);

    /**
     * 后台：修改专辑信息
     * @param chapter
     * @return
     */
    String Modifier(Chapter chapter);
}
