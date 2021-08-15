package com.hx.orderservice.service;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 7:42
 * @description
 * @editUser hx
 * @editTime 2021/8/15 7:42
 * @editDescription  重新设置redis的过期时间;
 */
public interface RenewGrabLockService {


    public void renewLock(String key,String value,int time);
}
