package com.hx.orderservice.service.impl;

import com.hx.orderservice.service.GrabService;
import com.hx.orderservice.service.TblOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hx
 * @createTime 2021/8/12 20:59
 * @version 1.0.0
 * @description 无锁状态
 * @editUser hx
 * @editTime 2021/8/12 20:59
 * @editDescription
 */
@Service("grabNoLockService")
public class Grab_01_NolocksImpl implements GrabService {

    @Autowired
    TblOrderService tblOrderService;

    /**
     * 无锁状态下的司机抢单!
     * @param orderId
     * @param driverId
     * @return
     */
    @Override
    public String grabOrder(int orderId, int driverId) {
        System.out.println("司机" + driverId + " 开始执行抢单的逻辑......");

        try {
            boolean flag = tblOrderService.grab(orderId, driverId);
            if (flag) {
                System.out.println("司机" + driverId + " 抢单成功! yes! ");
            } else {
                System.out.println("司机" + driverId + " 抢单失败! fuck!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        //    执行相应的业务
        }

        return null;
    }
}
