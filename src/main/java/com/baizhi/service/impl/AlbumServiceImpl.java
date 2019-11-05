package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private StarDao starDao;

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Album> list = albumDao.selectByRowBounds(album, rowBounds);
        for (Album a : list) {
            Star star = starDao.selectByPrimaryKey(a.getStarId());
            a.setStar(star);
        }
        int count = albumDao.selectCount(album);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public String add(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCount(0);
        int i = albumDao.insertSelective(album);
        if(i==0){
            throw new RuntimeException("添加专辑失败");
        }
        return album.getId();
    }

    @Override
    public void edit(Album album) {
        int i = albumDao.updateByPrimaryKeySelective(album);
        if(i==0){
            throw new RuntimeException("修改专辑失败");
        }
    }

    @Override
    public Album selectOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }
}
