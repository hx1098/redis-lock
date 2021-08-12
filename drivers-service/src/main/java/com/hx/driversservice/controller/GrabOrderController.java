package com.hx.driversservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author hx
 * @createTime 2021/8/12 20:08
 * @version 1.0.0
 * @description
 * @editUser hx
 * @editTime 2021/8/12 20:08
 * @editDescription
 */
@RestController
@RequestMapping("/grap")
public class GrabOrderController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/do/{orderId}")
    public String grab(@PathVariable("orderId") String orderId,int driverId) {
        String url = "http://service-order" + "/grab/do/"+orderId+"?driverId="+driverId;
        restTemplate.getForEntity(url,String.class);
        return "success!";
    }

}
