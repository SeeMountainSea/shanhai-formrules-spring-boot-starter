package com.wangshanhai.formrules.service.impl;


import com.wangshanhai.formrules.annotation.ReqLock;
import com.wangshanhai.formrules.dto.LockInfoDTO;
import com.wangshanhai.formrules.service.ShanhaiReqLock;
import com.wangshanhai.formrules.utils.Logger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redisson的分布式锁实现（RedLock奇数多主模式）
 *
 * @author Fly.Sky
 * @since 2022/12/9 11:09
 */
@Service
@ConditionalOnProperty(
        prefix = "shanhai.reqlock",
        name = "strategy",
        havingValue = "redisson")
public class RedissonRedLock implements ShanhaiReqLock {
    @Autowired
    private RedissonClient redissonClient;
    
    @Override
    public LockInfoDTO lock(ReqLock reqLock, Map<String, Object> extParams,String lockReqFlag) {
        LockInfoDTO lockInfoDTO=new LockInfoDTO();
        String realKey=reqLock.lockName()+":"+lockReqFlag;
        lockInfoDTO.setLockRealKey(realKey);
        RLock redLock = redissonClient.getLock(realKey);
        try{
            lockInfoDTO.setStatus( redLock.tryLock(reqLock.lockTimeOut(), reqLock.lockExpireTime(), TimeUnit.SECONDS));
            lockInfoDTO.setLockObj(redLock);
            Logger.info("[ShanhaiReqLock-redissonClient-lock]-lockReal:[{}],status:{}",lockInfoDTO.getLockRealKey(),lockInfoDTO.getStatus());
        }catch (Exception e){
            lockInfoDTO.setStatus(false);
            e.printStackTrace();
        }
       return lockInfoDTO;
    }

    @Override
    public void unlock(LockInfoDTO lockInfoDTO, ReqLock reqLock, Map<String, Object> extParams) {
        RLock redLock =(RLock)lockInfoDTO.getLockObj();
        if(redLock!=null){
            Logger.info("[ShanhaiReqLock-redissonClient-unlock]-{}",lockInfoDTO.getLockRealKey());
            redLock.unlock();
        }
    }
}
