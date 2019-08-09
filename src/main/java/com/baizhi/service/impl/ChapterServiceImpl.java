package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> QueryByPage(Integer pages, Integer rows,String albumId) {
        List<Chapter> chapters = chapterDao.queryByPage(albumId, (pages - 1) * rows, rows);
        Integer count = chapterDao.queryByTotal(albumId);
        HashMap<String, Object> map = new HashMap<>();

        Integer total=count%rows==0?count/rows:count/rows+1;
        map.put("total",total);
        map.put("records",count);
        map.put("page",pages);
        map.put("rows",chapters);

        return map;
    }

    @Override
    public String AddChapter(Chapter chapter,String albumId) {
        String uuid = UUIDUtil.getUUID();
        try{
            Album album = albumDao.SelectOne(albumId);
            album.setId(albumId);
            album.setCount(album.getCount()+1);
            albumDao.Update(album);
            chapter.setId(uuid);
            chapter.setUp_date(new Date());
            chapterDao.Insert(chapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return uuid;
    }

    @Override
    public void RemoveById(String id,String albumId) {
        try {
            Album album = albumDao.SelectOne(albumId);
            album.setId(albumId);
            album.setCount(album.getCount()-1);
            albumDao.Update(album);
            chapterDao.Delete(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String Modifier(Chapter chapter) {
        if(chapter.getUrl().isEmpty()||chapter.getUrl()==""){
            chapter.setUrl(null);
            chapterDao.Update(chapter);
        }else {
            chapterDao.Update(chapter);
        }
        return chapter.getId();
    }
}
