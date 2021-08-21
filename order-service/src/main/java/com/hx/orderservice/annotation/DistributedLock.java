package com.hx.orderservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 17:07
 * @description
 * @editUser hx
 * @editTime 2021/8/15 17:07
 * @editDescription
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    String value() default "";

    int time() default 30;

}
