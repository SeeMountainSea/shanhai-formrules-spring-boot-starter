package com.wangshanhai.formrules.service;

/**
  *缓存服务
  *@author Fly.Sky
  */
public interface CacheService {

    Long expire(String key, int time);

    boolean exists(String key);

    long ttl(String key);

    void del(String key);

    String get(String key);

    void set(String key, String value);

    void set(String key, String value, long time);

}
