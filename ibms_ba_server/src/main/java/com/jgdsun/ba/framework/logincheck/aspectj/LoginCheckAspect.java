package com.jgdsun.ba.framework.logincheck.aspectj;


import com.jgdsun.ba.framework.logincheck.annotation.LoginCheck;
import com.jgdsun.ba.framework.logincheck.aspectj.exception.MyLoginException;
import com.jgdsun.ba.framework.util.FeignRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 调用接口前校验是否需要登录，登录后刷新 token 和 一些登录信息
 *
 * @author ruoyi
 */
@Aspect
@Slf4j
@Component
public class LoginCheckAspect {

    @Autowired
    private LoginCheckHandlerCombiner loginCheckHandlerCombiner;

    // 新增环绕通知（用于处理特定异常后重试）
    @Around("@annotation(loginCheck)")
    public Object doAroundWithRetry(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        // 是否有对应支持的登录处理类
        String brandName = loginCheck.value();
        loginCheckHandlerCombiner.support(brandName);
        // 刷新 token
        if (loginCheckHandlerCombiner.needLogin()) {
            loginCheckHandlerCombiner.login();
        }

        // 抛出特定登录异常时重试
        int maxRetries = 3; // 最大重试次数
        int attempts = 0;
        Exception lastException = null;

        while (attempts < maxRetries) {
            try {
                return joinPoint.proceed();
            } catch (MyLoginException ex) {
                lastException = ex;
                attempts++;
                // 刷新 token 后重试
                loginCheckHandlerCombiner.refreshToken();
                log.error("接口调用前登录接口：第 " + attempts + " 次重试，异常原因：", ex);
            }
        }
        FeignRequestContext.clear();
        // 重试次数用尽后抛出异常
        throw new RuntimeException("接口调用前登录接口：重试 " + maxRetries + " 次后仍然失败，不再重试", lastException);
    }

    @After("@annotation(loginCheck)")
    public void doAfter(JoinPoint point, LoginCheck loginCheck) {
        // 清空请求头信息
        FeignRequestContext.clear();
    }
}
