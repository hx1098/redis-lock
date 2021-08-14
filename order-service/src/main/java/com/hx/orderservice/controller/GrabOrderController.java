package com.hx.orderservice.controller;

import com.hx.orderservice.service.GrabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (TblOrder)表控制层
 *
 * @author makejava
 * @since 2021-08-12 17:42:13
 */
@RestController
@RequestMapping("/grab")
public class GrabOrderController {

    @Autowired
    //@Qualifier("grabNoLockService")
    @Qualifier("grabJvmLockService")
    private GrabService grabService;


    @GetMapping("/do/{orderId}")
    public String selectOne(@PathVariable("orderId") int orderId, int driverId) {
        System.out.println("order: " + orderId + " ,driverId: " + driverId);
        grabService.grabOrder(orderId, driverId);
        return null;
    }

}
