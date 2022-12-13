package com.wangshanhai.formrules.component;

import com.wangshanhai.formrules.annotation.ReqLock;
import com.wangshanhai.formrules.config.ReqLockConfig;
import com.wangshanhai.formrules.dto.LockInfoDTO;
import com.wangshanhai.formrules.exception.HttpReqLockException;
import com.wangshanhai.formrules.service.ShanhaiReqLock;
import com.wangshanhai.formrules.utils.Logger;
import com.wangshanhai.formrules.utils.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 分布式锁组件
 *
 * @author Fly.Sky
 * @since 2022/12/12 9:44
 */
@Aspect
@Configuration
public class ReqLockComponent {
    @Autowired
    private ReqLockConfig reqLockConfig;
    @Autowired
    private ShanhaiReqLock shanhaiReqLock;

    @Pointcut("@annotation(com.wangshanhai.formrules.annotation.ReqLock)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object lockReq(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取变量名列表
        String[] reqParams = signature.getParameterNames();
        //获取全量参数
        Object[] params = joinPoint.getArgs();
        Method method = signature.getMethod();
        ReqLock reqLock = method.getAnnotation(ReqLock.class);
        String lockReqFlag = buildLockReqFlag(reqParams, params, reqLock);
        if (!StringUtils.isEmpty(lockReqFlag)) {
            LockInfoDTO lockInfoDTO = null;
            Object result = null;
            boolean lockStatus = false;
            try {
                lockInfoDTO = shanhaiReqLock.lock(reqLock, reqLockConfig.getExtParams(), lockReqFlag);
                lockStatus = lockInfoDTO.getStatus();
                if (lockStatus) {
                    result = joinPoint.proceed();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (lockInfoDTO != null && lockStatus) {
                    shanhaiReqLock.unlock(lockInfoDTO, reqLock, reqLockConfig.getExtParams());
                }
            }
            if (!lockStatus) {
                Logger.error("[ShanhaiReqLock-failure]-lockName:{},msg:{}", reqLock.lockName(), "获取分布式锁失败！");
                throw new HttpReqLockException(30001, "分布式锁[" + reqLock.lockName() + "]获取失败，请稍后重试！");
            }
            return result;
        }
        Logger.error("[ShanhaiReqLock-Create-failure]-lockName:{},msg:{}", reqLock.lockName(), "目标参数解析失败，无法创建分布式锁！");
        return joinPoint.proceed();
    }

    /**
     * 构建分布式锁关键字(单目标模式优先级最高)
     *
     * @param reqParams 变量名列表
     * @param params    全量参数
     * @param reqLock   分布式锁定义
     * @return
     */
    public String buildLockReqFlag(String[] reqParams, Object[] params, ReqLock reqLock) {
        if (!StringUtils.isEmpty(reqLock.lockTarget())) {
            return buildSingleLockReqFlag(reqParams, params, reqLock.lockTarget());
        }
        StringBuffer lockReqFlag = new StringBuffer();
        for (String t : reqLock.multiLockTarget()) {
            lockReqFlag.append(buildSingleLockReqFlag(reqParams, params, t)+":");
        }
        if(!StringUtils.isEmpty(lockReqFlag.toString())){
            lockReqFlag.append(reqLock.multiLockTarget().length);
        }
        return lockReqFlag.toString();

    }

    /**
     * 获取目标参数的值
     * @param reqParams 变量名列表
     * @param params  全量参数
     * @param target 目标定义
     * @return
     */
    public String buildSingleLockReqFlag(String[] reqParams, Object[] params, String target) {
        String lockReqFlag = "";
        int checkIndex = getParamsIndex(reqParams, target);
        if (checkIndex > -1) {
            Object obj = params[checkIndex];
            if (target.contains(".")) {
                lockReqFlag = String.valueOf(ObjectUtils.getFieldValueByName(obj, target.substring(target.indexOf(".") + 1)));
            } else {
                lockReqFlag = String.valueOf(obj);
            }
            return lockReqFlag;
        }
        return lockReqFlag;
    }
    /**
     * 获取变量位置
     *
     * @param reqParams   全量参数名
     * @param targetParam 目标参数名
     * @return
     */
    public int getParamsIndex(String[] reqParams, String targetParam) {
        for (int i = 0; i < reqParams.length; i++) {
            if(targetParam.contains(".")){
                targetParam=targetParam.substring(0,targetParam.indexOf("."));
            }
            if (targetParam.equals(reqParams[i])) {
                return i;
            }
        }
        return -1;
    }
}
