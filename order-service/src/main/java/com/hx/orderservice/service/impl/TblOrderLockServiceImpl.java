package com.hx.orderservice.service.impl;

import com.hx.orderservice.entity.TblOrderLock;
import com.hx.orderservice.dao.TblOrderLockDao;
import com.hx.orderservice.service.TblOrderLockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TblOrderLock)表服务实现类
 *
 * @author makejava
 * @since 2021-08-12 17:42:15
 */
@Service("tblOrderLockService")
public class TblOrderLockServiceImpl implements TblOrderLockService {
    @Resource
    private TblOrderLockDao tblOrderLockDao;

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    @Override
    public TblOrderLock queryById(Integer orderId) {
        return this.tblOrderLockDao.queryById(orderId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<TblOrderLock> queryAllByLimit(int offset, int limit) {
        return this.tblOrderLockDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tblOrderLock 实例对象
     * @return 实例对象
     */
    @Override
    public TblOrderLock insert(TblOrderLock tblOrderLock) {
        this.tblOrderLockDao.insert(tblOrderLock);
        return tblOrderLock;
    }

    /**
     * 修改数据
     *
     * @param tblOrderLock 实例对象
     * @return 实例对象
     */
    @Override
    public TblOrderLock update(TblOrderLock tblOrderLock) {
        this.tblOrderLockDao.update(tblOrderLock);
        return this.queryById(tblOrderLock.getOrderId());
    }

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer orderId) {
        return this.tblOrderLockDao.deleteById(orderId) > 0;
    }
}
