package com.hx.orderservice.lock;/**
 * @author yd
 * @date 2021/8/14 10:04
 * @version 1.0
 */

import com.hx.orderservice.dao.TblOrderLockDao;
import com.hx.orderservice.entity.TblOrderLock;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/14 10:04
 * @description
 * @editUser hx
 * @editTime 2021/8/14 10:04
 * @editDescription
 */


@Data
@Service
public class MysqlLock implements Lock {

    @Autowired
    private TblOrderLockDao mapper;

    private ThreadLocal<TblOrderLock> orderLockThreadLocal;

    @Override
    public void lock() {
        // 1、尝试加锁
        if (tryLock()) {
            System.out.println("尝试加锁");
            return;
        }
        // 2.休眠
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 3.递归再次调用
        lock();
    }

    /**
     * 非阻塞式加锁，成功，就成功，失败就失败。直接返回, 这里的枷锁实际上就是在数据库中加一个按照orderID为主键的数据.
     */
    @Override
    public boolean tryLock() {
        try {
            TblOrderLock tblOrderLock = orderLockThreadLocal.get();
            int i = mapper.insertSelective(tblOrderLock);

            System.out.println("加锁对象：" + orderLockThreadLocal.get());
            return i > 0 ? true : false;
        } catch (Exception e) {
            return false;
        }


    }

    /**
     * 解锁实际就是将加锁的数据直接删除掉
     */
    @Override
    public void unlock() {
        mapper.deleteByPrimaryKey(orderLockThreadLocal.get().getOrderId());
        System.out.println("解锁对象：" + orderLockThreadLocal.get());
        orderLockThreadLocal.remove();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        return null;
    }
}
