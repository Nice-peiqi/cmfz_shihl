package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
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
public class AlbumServiceImpl implements AlbumService {

    @Resource
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map<String, Object> QueryByPage(Integer pages, Integer rows) {
        Integer page = (pages-1)*rows;
        //当前页数据
        List<Album> albums = albumDao.SelectByPage(page,rows);
        //查询总条数
        Integer totalCount = albumDao.SelectBYTotal();
        HashMap<String, Object> map = new HashMap<>();
        //当前页号
        map.put("page", page);
        //总条数
        map.put("records", totalCount);
        //总页数
        Integer pageCount = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        map.put("total", pageCount);
        //每页具体的数据
        map.put("rows", albums);
        return map;
    }

    @Override
    public String AddAlbum(Album album) {
        String uuid = UUIDUtil.getUUID();
        album.setId(uuid);
        album.setPub_date(new Date());
        albumDao.Insert(album);
        return uuid;
    }

    @Override
    public void RemoveById(String id) {
        albumDao.Delete(id);
    }

    @Override
    public String Modifier(Album album) {
        if(album.getCover_img().isEmpty()||album.getCover_img()==""){
            album.setCover_img(null);
            albumDao.Update(album);
        }else {
            albumDao.Update(album);
        }
        return album.getId();
    }
}
