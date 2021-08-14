package com.hx.orderservice.service.impl;/**
 * @author yd
 * @date 2021/8/14 8:48
 * @version 1.0
 */

import com.hx.orderservice.service.GrabService;
import com.hx.orderservice.service.TblOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hx
 * @createTime 2021/8/14 8:48
 * @version 1.0.0
 * @description  jvm 锁实现
 * @editUser hx
 * @editTime 2021/8/14 8:48
 * @editDescription
 */
@Slf4j
@Service("grabJvmLockService")
public class Grab_02_JvmLockImpl implements GrabService {


    @Autowired
    private TblOrderService tblOrderService;

    /**
     * jvm 锁实现抢单
     * @param orderId
     * @param driverId
     * @return
     */
    @Override
    public String grabOrder(int orderId, int driverId) {
        String lock = orderId + "";
        synchronized (lock.intern()) {
            try {
                log.info("司机" + driverId + "执行抢单逻辑!");
                boolean b = tblOrderService.grab(orderId, driverId);

                if (b) {
                    log.info("司机" + driverId + "抢单成功!");
                } else {
                    log.info("司机" + driverId + "抢单失败!");
                }
            } finally {

            }
        }
        return null;
    }
}
