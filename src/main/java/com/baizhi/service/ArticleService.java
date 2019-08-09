package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.HashMap;
import java.util.Map;

public interface ArticleService {

    /**
     * 后台：分页展示文章
     * @param pages
     * @param rows
     * @return
     */
    Map<String,Object> QueryByPage(Integer pages, Integer rows);

    /**
     * 后台：修改文章状态
     * @param article
     */
    HashMap<String, Object> MedifierStatus(Article article);

    /**
     * 后台：删除文章
     * @param id
     */
    void RemoveById(String id);

    /**
     * 后台：增加文章
     * @param article
     */
    HashMap<String, Object> AddArticle(Article article);

    /**
     * 后台：秀给文章
     * @param article
     * @param articleId
     * @return
     */
    HashMap<String, Object> Modifier(Article article);
}
