package com.baizhi.service.impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import com.baizhi.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> QueryByPage(Integer pages, Integer rows) {
        Integer page = (pages-1)*rows;

        List<Article> articles = articleDao.SelectByPage(page, rows);
        HashMap<String,Object> map = new HashMap();
        Integer counts = articleDao.SelectBYTotal();

        Integer total = counts%rows==0?counts/rows:counts/rows+1;

        map.put("records",counts);
        map.put("total",total);
        map.put("page",pages);
        map.put("rows",articles);

        return map;
    }

    @Override
    public HashMap<String, Object> MedifierStatus(Article article) {
        HashMap<String, Object> map = new HashMap();
        try{
            articleDao.Update(article);
            map.put("success","200");
            map.put("error","修改成功！");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success","400");
            map.put("error","修改失败！");
        }
        return map;
    }

    @Override
    public void RemoveById(String id) {
        articleDao.Delete(id);
    }

    @Override
    public HashMap<String, Object> AddArticle(Article article) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            article.setId(UUIDUtil.getUUID());
            article.setInsert_img("1564587129658-preview2.jpg");
            article.setUp_date(new Date());
            article.setGuru_id("1");
            article.setStatus("展示");
            articleDao.Insert(article);

            map.put("success","200");
            map.put("error","添加成功");
        }catch (Exception e){
            e.printStackTrace();

            map.put("success","400");
            map.put("error","添加失败");
        }
        return map;
    }

    @Override
    public HashMap<String, Object> Modifier(Article article) {
        HashMap<String, Object> map = new HashMap();
        try{
            System.out.println(article+"--------------------");
            articleDao.Update(article);

            map.put("success","200");
            map.put("error","修改成功");
        }catch (Exception e){
            e.printStackTrace();

            map.put("success","400");
            map.put("error","修改成功");
        }
        return map;
    }
}
