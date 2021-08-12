package com.hx.orderservice.service.impl;

import com.hx.orderservice.entity.TblOrder;
import com.hx.orderservice.dao.TblOrderDao;
import com.hx.orderservice.service.TblOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TblOrder)表服务实现类
 *
 * @author makejava
 * @since 2021-08-12 17:42:12
 */
@Service("tblOrderService")
public class TblOrderServiceImpl implements TblOrderService {
    @Resource
    private TblOrderDao tblOrderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    @Override
    public TblOrder queryById(Integer orderId) {
        return this.tblOrderDao.queryById(orderId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<TblOrder> queryAllByLimit(int offset, int limit) {
        return this.tblOrderDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tblOrder 实例对象
     * @return 实例对象
     */
    @Override
    public TblOrder insert(TblOrder tblOrder) {
        this.tblOrderDao.insert(tblOrder);
        return tblOrder;
    }

    /**
     * 修改数据
     *
     * @param tblOrder 实例对象
     * @return 实例对象
     */
    @Override
    public TblOrder update(TblOrder tblOrder) {
        this.tblOrderDao.update(tblOrder);
        return this.queryById(tblOrder.getOrderId());
    }

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer orderId) {
        return this.tblOrderDao.deleteById(orderId) > 0;
    }
}
