package com.wangshanhai.formrules.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * The {@code ReqLockConfig} class
 *
 * @author Fly.Sky
 * @since 2022/12/12 10:33
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "shanhai.reqlock")
public class ReqLockConfig {
    /**
     * 分布式加锁策略
     */
    private  String strategy;

    private Map<String,Object> extParams;
}
