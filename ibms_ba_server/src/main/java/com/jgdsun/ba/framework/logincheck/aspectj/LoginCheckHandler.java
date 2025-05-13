package com.jgdsun.ba.framework.logincheck.aspectj;

import org.springframework.stereotype.Component;

/**
 * @author xh
 * @date 2025-04-17
 * @Description:
 */
public interface LoginCheckHandler {


    boolean support(String brandName);

    // 判断 token 是否失效来确定是否需要登录
    boolean needLogin();

    // 登录以获取 token
    void login();

    // 当确定因 token 失效而导致接口调用失败时，刷新 token
    // 可能重新登录，也可能利用刷新机制刷新 token
    void refreshToken();


}
