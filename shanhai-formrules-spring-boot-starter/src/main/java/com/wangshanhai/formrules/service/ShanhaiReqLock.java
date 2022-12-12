package com.wangshanhai.formrules.service;


import com.wangshanhai.formrules.annotation.ReqLock;
import com.wangshanhai.formrules.dto.LockInfoDTO;

import java.util.Map;

/**
 * 基于RedLock的锁实现
 *
 * @author Fly.Sky
 * @since 2022/12/9 10:49
 */
public interface ShanhaiReqLock {
    /**
     * 获取分布式锁
     * @param reqLock 分布式锁定义
     * @param extParams 分布式锁自定义参数
     * @return
     */
    public LockInfoDTO lock(ReqLock reqLock, Map<String, Object> extParams,String lockReqFlag);

    /**
     *  释放分布式锁
     * @param lockInfoDTO
     * @param reqLock
     * @param extParams
     */
    public void unlock(LockInfoDTO lockInfoDTO,ReqLock reqLock, Map<String,Object> extParams);
}
