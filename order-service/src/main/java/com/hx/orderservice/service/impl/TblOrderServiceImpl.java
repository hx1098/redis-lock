package com.hx.orderservice.service.impl;

import com.hx.orderservice.dao.TblOrderDao;
import com.hx.orderservice.entity.TblOrder;
import com.hx.orderservice.service.TblOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (TblOrder)表服务实现类
 *
 * @author makejava
 * @since 2021-08-12 17:42:12
 */
@Service("tblOrderService")
public class TblOrderServiceImpl implements TblOrderService {

    @Autowired
    private TblOrderDao tblOrderDao;

    @Override
    public boolean grab(int orderId, int driverId)  {
        //查看司机是否抢单成功,
        //否: 直接返回
        //是: 做其他的业务, 关联上相应的司机,起点, 终点等信息, 返回true
        TblOrder tblOrder = tblOrderDao.queryById(orderId);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /** 省略其他业务*/

        if (tblOrder.getOrderStatus().intValue() == 0) {
            tblOrder.setOrderStatus(1);
            tblOrderDao.update(tblOrder);
            return true;
        }
        return false;
    }
}
