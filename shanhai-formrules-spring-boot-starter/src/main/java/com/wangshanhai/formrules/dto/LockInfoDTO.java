package com.wangshanhai.formrules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用加锁信息
 *
 * @author Fly.Sky
 * @since 2022/12/12 9:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LockInfoDTO {
    /**
     * 锁状态
     */
    private Boolean status;
    /**
     * 获取到的锁
     */
    private Object  lockObj;
    /**
     * 分布式锁的真实Key
     */
    private String  lockRealKey;
}
