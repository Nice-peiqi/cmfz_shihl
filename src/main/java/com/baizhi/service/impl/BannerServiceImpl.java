package com.baizhi.service.impl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
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
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerDao bannerDao;

    @Override
    public String AddBanner(Banner banner) {
        String uuid = UUIDUtil.getUUID();
        banner.setId(uuid);
        banner.setStatus("1");
        banner.setUp_date(new Date());
        bannerDao.InsertBanner(banner);
        return uuid;
    }

    @Override
    public HashMap<String, Object> Remove(Banner banner) {
        Banner b = bannerDao.SelectById(banner.getId());
        Banner b1 = new Banner();
        b1.setId(banner.getId());
        if (b.getStatus().equals("1")) {
            b1.setStatus("2");
        } else {
            b1.setStatus("1");
        }

        HashMap<String, Object> map = new HashMap<>();
        try {
            bannerDao.UpdateById(b1);
            map.put("success", "200");
            map.put("message", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", "400");
            map.put("message", "修改失败");
        }
        return map;
    }

    @Override
    public String Modifier(Banner banner) {
        if(banner.getImg_path().isEmpty()||banner.getImg_path()==""){
            banner.setImg_path(null);
            bannerDao.UpdateById(banner);
        }else {
            bannerDao.UpdateById(banner);
        }

        return banner.getId();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Object> ShowAll(Integer page, Integer rows) {
        //当前页数据
        List<Banner> banners = bannerDao.SelectAll(page, rows);
        //查询总条数
        Integer totalCount = bannerDao.SelectTotalCount();
        HashMap<String, Object> map = new HashMap<>();
        //当前页号
        map.put("page", page);
        //总条数
        map.put("records", totalCount);
        //总页数
        Integer pageCount = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        map.put("total", pageCount);
        //每页具体的数据
        map.put("rows", banners);
        return map;
    }

    @Override
    public void updateByimg_path(Banner banner) {
        bannerDao.UpdateById(banner);
    }

    @Override
    public void RemoveById(Banner banner) {
        bannerDao.DelectById(banner.getId());
    }

}
