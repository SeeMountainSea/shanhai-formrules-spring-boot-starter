package com.shanhai.examples.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangshanhai.formrules.annotation.EnableShanHiFormRules;
import com.wangshanhai.formrules.annotation.EnableShanHiReqLock;
import com.wangshanhai.formrules.annotation.EnableShanHiSqlLock;
import com.wangshanhai.formrules.utils.Logger;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableShanHiFormRules
@EnableShanHiSqlLock
@EnableShanHiReqLock
public class RunConfig {

    /**
     * 使用默认分布式锁策略，需要自行初始化RedissonClient对象
     * @return
     */
    @Bean
    public RedissonClient createClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                .setDatabase(2);
        Logger.info(("[RedissonClient]-单实例模式"));
        return  Redisson.create(config);
    };
    /**
     * 使用默认SQL锁缓存策略，需要自行初始化RedisTemplate对象
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
