package com.wangshanhai.formrules.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The {@code SqlLockConfig} class
 *
 * @author Fly.Sky
 * @since 2022/12/16 9:53
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "shanhai.sqllock")
public class SqlLockConfig {
    /**
     * SQL锁缓存类型
     */
    private  String cacheType;
}
