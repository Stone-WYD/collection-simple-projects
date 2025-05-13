package com.wyd.zmhkmiddleware.advice;

import com.wyd.zmhkmiddleware.common.BaseException;
import com.wyd.zmhkmiddleware.common.enums.ResultStatusCode;
import com.wyd.zmhkmiddleware.util.LoginUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author Stone
 * @since 2025-05-12
 */
@Aspect
@Component
public class TokenAspect {

    @Resource
    private LoginUtils loginUtils;

    /**
     * 环绕通知：拦截所有标注了 @CheckToken 的方法
     */
    @Around("@annotation(com.wyd.zmhkmiddleware.advice.CheckToken)")
    public Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前请求对象
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 从请求头中获取 token
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            throw new BaseException(ResultStatusCode.TOKEN_NOT_FOUND.getCode(), "缺少 token，请在请求头中添加 Authorization");
        }

        // 示例：验证 token 是否合法
        if (!loginUtils.checkToken(token)) {
            throw new BaseException(ResultStatusCode.TOKEN_NOT_FOUND.getCode(), "token 已过期或者不合法");
        }

        // token 校验通过，继续执行目标方法
        return joinPoint.proceed();
    }
}
