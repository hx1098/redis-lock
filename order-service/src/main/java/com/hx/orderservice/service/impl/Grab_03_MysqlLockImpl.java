package com.hx.orderservice.service.impl;/**
 * @author yd
 * @date 2021/8/14 10:28
 * @version 1.0
 */

import com.hx.orderservice.entity.TblOrder;
import com.hx.orderservice.entity.TblOrderLock;
import com.hx.orderservice.lock.MysqlLock;
import com.hx.orderservice.service.GrabService;
import com.hx.orderservice.service.TblOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hx
 * @createTime 2021/8/14 10:28
 * @version 1.0.0
 * @description:  mysql的加锁实际上在用户抢的过程中: 如果用户抢到, 那就走下面的逻辑, 抢不到就抢不到了
 * 加锁实际就是在数据库"事件表"中增加一条以 orderID为主键的数据, 抢到就直接插入数据了, 抢不到就直接返回, 重新抢, 直到抢到或者超时为止
 *
 * @editUser hx
 * @editTime 2021/8/14 10:28
 * @editDescription
 *
 */
@Slf4j
@Service("grabMysqlLockService")
public class Grab_03_MysqlLockImpl implements GrabService {

    @Autowired
    private MysqlLock lock;

    @Autowired
    private TblOrderService tblOrderService;

    ThreadLocal<TblOrderLock> orderLockThreadLocal = new ThreadLocal();

    @Override
    public String grabOrder(int orderId, int driverId) {
        //生成一个锁
        TblOrderLock tblOrderLock = new TblOrderLock();
        tblOrderLock.setOrderId(orderId);
        tblOrderLock.setDriverId(driverId);

        orderLockThreadLocal.set(tblOrderLock);
        lock.setOrderLockThreadLocal(orderLockThreadLocal);

        //先上锁
        lock.lock();

        //执行业务
        try {
            log.info("司机 driverId = [{}], 开始执行抢单逻辑", driverId);
            boolean b = tblOrderService.grab(orderId, driverId);
            if(b) {
                log.info("司机 [{}] 抢单成功!", driverId);
            }else {
                log.info("司机 [{}] 抢单失败!", driverId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        //    释放锁
            lock.unlock();
        }

        //执行各种业务
        return null;
    }
}
