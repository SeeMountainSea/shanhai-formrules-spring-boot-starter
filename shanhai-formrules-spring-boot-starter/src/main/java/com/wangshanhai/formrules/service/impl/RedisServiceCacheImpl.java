package com.wangshanhai.formrules.service.impl;


import com.wangshanhai.formrules.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 原生Redis实现
 * @author Fly.Sky
 */
@Component
@ConditionalOnProperty(
        prefix = "shanhai.sqllock",
        name = "cacheType",
        havingValue = "redis")
public class RedisServiceCacheImpl implements CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long expire(String key, int time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return 1L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public String get(String key) {
        return key == null ? null : String.valueOf(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(String key, String value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
