package com.hx.orderservice.service;

import com.hx.orderservice.entity.TblOrder;

import java.util.List;

/**
 * (TblOrder)表服务接口
 *
 * @author makejava
 * @since 2021-08-12 17:42:12
 */
public interface TblOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    TblOrder queryById(Integer orderId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TblOrder> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tblOrder 实例对象
     * @return 实例对象
     */
    TblOrder insert(TblOrder tblOrder);

    /**
     * 修改数据
     *
     * @param tblOrder 实例对象
     * @return 实例对象
     */
    TblOrder update(TblOrder tblOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer orderId);

}
