package com.baizhi.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DAO<T> {
    /**
     * 查询所有
     * @return
     */
    List<T> SelectAll();

    /**
     * 增加
     * @param t
     */
    void Insert(T t);

    /**
     * 删除
     * @param id
     */
    void Delete(String id);

    /**
     * 修改
     * @param t
     */
    void Update(T t);

    /**
     * 分页查询
     * @param start
     * @param rows
     * @return
     */
    List<T> SelectByPage(@Param("start")Integer start, @Param("rows") Integer rows);

    /**
     * 查询总条数
     * @return
     */
    Integer SelectBYTotal();
}
