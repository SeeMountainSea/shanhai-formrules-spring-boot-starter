package com.wangshanhai.formrules.component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.wangshanhai.formrules.annotation.SqlLock;
import com.wangshanhai.formrules.exception.HttpSqlLockException;
import com.wangshanhai.formrules.service.CacheService;
import com.wangshanhai.formrules.utils.Logger;
import com.wangshanhai.formrules.utils.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 基于SQL的业务操作锁
 *
 * @author Fly.Sky
 * @since 2022/12/14 10:18
 */
@Aspect
@Configuration
public class SqlLockComponent {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CacheService cacheService;
    @Pointcut("@annotation(com.wangshanhai.formrules.annotation.SqlLock)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void checkSqlLock(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取变量名列表
        String[] reqParams = signature.getParameterNames();
        //获取全量参数
        Object[] params = joinPoint.getArgs();
        Method method = signature.getMethod();
        SqlLock sqlLock = method.getAnnotation(SqlLock.class);
        String exSource=sqlLock.expression();
        exSource=exSource.replace("{","'{");
        exSource=exSource.replace("}","}'");
        String execSQL=StrUtil.format(exSource,buildSqlLockFlag(reqParams,params,sqlLock).toArray());
        Logger.info("[ShanhaiSqlLock-trace]-{}",execSQL);
        if(sqlLock.cache()){
            if(cacheService.exists(SecureUtil.md5(execSQL))){
               throw new HttpSqlLockException(40001,"该事务已锁定，禁止提交！");
            }
        }
        List t=this.jdbcTemplate.queryForList(execSQL);
        if(t!=null&&t.size()>0){
            if(sqlLock.cache()&& !cacheService.exists(SecureUtil.md5(execSQL))){
                cacheService.set(SecureUtil.md5(execSQL), UUID.randomUUID().toString(),sqlLock.cacheExpire());
            }
            throw new HttpSqlLockException(40002,"该事务已锁定，禁止提交！");
        }

    }

    /**
     * 动态获取SQL锁关键值
     *
     * @param reqParams 变量名列表
     * @param params    全量参数
     * @param sqlLock   Sql锁定义
     * @return
     */
    public List<String> buildSqlLockFlag(String[] reqParams, Object[] params, SqlLock sqlLock) {
        List<String> lockSqlFlag = new ArrayList<>();
        for (String t : sqlLock.expressionValueTargets()) {
            lockSqlFlag.add(buildSingleSqlLockFlag(reqParams, params, t));
        }
        return lockSqlFlag;
    }

    /**
     * 获取目标参数的值
     *
     * @param reqParams 变量名列表
     * @param params    全量参数
     * @param target    目标定义
     * @return
     */
    public String buildSingleSqlLockFlag(String[] reqParams, Object[] params, String target) {
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
            if (targetParam.contains(".")) {
                targetParam = targetParam.substring(0, targetParam.indexOf("."));
            }
            if (targetParam.equals(reqParams[i])) {
                return i;
            }
        }
        return -1;
    }
}
