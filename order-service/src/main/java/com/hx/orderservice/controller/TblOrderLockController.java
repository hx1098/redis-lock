package com.hx.orderservice.controller;

import com.hx.orderservice.entity.TblOrderLock;
import com.hx.orderservice.service.TblOrderLockService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TblOrderLock)表控制层
 *
 * @author makejava
 * @since 2021-08-12 17:42:15
 */
@RestController
@RequestMapping("tblOrderLock")
public class TblOrderLockController {
    /**
     * 服务对象
     */
    @Resource
    private TblOrderLockService tblOrderLockService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TblOrderLock selectOne(Integer id) {
        return this.tblOrderLockService.queryById(id);
    }

}
