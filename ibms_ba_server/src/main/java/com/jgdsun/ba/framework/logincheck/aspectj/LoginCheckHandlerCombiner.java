package com.jgdsun.ba.framework.logincheck.aspectj;

import com.jgdsun.ba.framework.util.ApplicationContextUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xh
 * @date 2025-04-17
 * @Description:
 */
@Component
public class LoginCheckHandlerCombiner implements LoginCheckHandler{

    private LoginCheckHandler loginCheckHandler;

    @Override
    public boolean support(String brandName) {
        List<LoginCheckHandler> loginCheckHandlerList =
                ApplicationContextUtil.getBeansOfType(LoginCheckHandler.class).stream().filter(handler -> handler != this).collect(Collectors.toList());
        for (LoginCheckHandler loginCheckHandler : loginCheckHandlerList) {
            if (loginCheckHandler.support(brandName)) {
                this.loginCheckHandler = loginCheckHandler;
                return true;
            }
        }
        throw new RuntimeException("找不到对应的登录处理器");
    }


    @Override
    public boolean needLogin() {
        return loginCheckHandler.needLogin();
    }

    @Override
    public void login() {
        loginCheckHandler.login();
    }

    @Override
    public void refreshToken() {
        loginCheckHandler.refreshToken();
    }

}
