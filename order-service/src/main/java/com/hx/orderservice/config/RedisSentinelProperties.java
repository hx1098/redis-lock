package com.hx.orderservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author hx
 * @version 1.0.0
 * @createTime 2021/8/15 8:12
 * @description
 * @editUser hx
 * @editTime 2021/8/15 8:12
 * @editDescription
 */
@Component
@ConfigurationProperties(prefix = "sentinel")
@Order(0)
@Data
public class RedisSentinelProperties {
    private String[] address;

    private String masterName;

}
