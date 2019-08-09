package com.baizhi.action;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleAction {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("showPage")
    @ResponseBody
    public Map<String, Object> SeveByPage(Integer page, Integer rows) throws Exception {
        Map<String, Object> map = articleService.QueryByPage(page, rows);
        return map;
    }

    @RequestMapping("modifierStatus")
    @ResponseBody
    public HashMap<String, Object> Modifier(Article article) throws Exception {
        HashMap<String, Object> map = articleService.MedifierStatus(article);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public void Remove(String id, String oper) throws Exception {
        if ("del".equals(oper)) {
            articleService.RemoveById(id);
        }
    }


    @RequestMapping("add")
    @ResponseBody
    public HashMap<String, Object> add(Article article) throws Exception {

       // System.out.println(upload);

        HashMap<String, Object> map = articleService.AddArticle(article);

        return map;
    }

    @RequestMapping("update")
    @ResponseBody
    public HashMap<String, Object> update(Article article, String articleId) throws Exception {
        article.setId(articleId);
        HashMap<String, Object> map = articleService.Modifier(article);

        return map;
    }
}
