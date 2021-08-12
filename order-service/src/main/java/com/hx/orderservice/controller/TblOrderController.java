package com.hx.orderservice.controller;

import com.hx.orderservice.entity.TblOrder;
import com.hx.orderservice.service.TblOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TblOrder)表控制层
 *
 * @author makejava
 * @since 2021-08-12 17:42:13
 */
@RestController
@RequestMapping("tblOrder")
public class TblOrderController {
    /**
     * 服务对象
     */
    @Resource
    private TblOrderService tblOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TblOrder selectOne(Integer id) {
        return this.tblOrderService.queryById(id);
    }

}
