package com.shanhai.examples.config;

import com.wangshanhai.formrules.annotation.EnableShanHiFormRules;
import com.wangshanhai.formrules.utils.Logger;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableShanHiFormRules
public class RulesConfig {

    @Bean
    public RedissonClient createClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                .setDatabase(2);
        Logger.info(("[RedissonClient]-单实例模式"));
        return  Redisson.create(config);
    };
}
