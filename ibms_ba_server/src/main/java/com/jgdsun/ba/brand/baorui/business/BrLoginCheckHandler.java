package com.jgdsun.ba.brand.baorui.business;

import cn.hutool.core.util.StrUtil;
import com.jgdsun.ba.brand.baorui.client.BrFeignClient;
import com.jgdsun.ba.brand.baorui.model.login.result.BrLoginResult;
import com.jgdsun.ba.framework.logincheck.aspectj.LoginCheckHandler;
import com.jgdsun.ba.framework.util.FeignRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xh
 * @date 2025-04-17
 * @Description:
 */
@Component
public class BrLoginCheckHandler implements LoginCheckHandler {

    @Autowired
    private BrFeignClient feignClient;

    @Value("${feign.baorui.token.password}")
    private String password;
    @Value("${feign.baorui.token.tenantId}")
    private String tenantId;
    @Value("${feign.baorui.token.username}")
    private String userName;
    @Value("${feign.baorui.token.grant-type}")
    private String grantType;

    // 请求头参数
    private String token;
    private String tokenTimeStamp;

    @Override
    public boolean support(String brandName) {
        if (StrUtil.isEmpty(brandName)) {
            return false;
        }
        return brandName.equals("baorui");
    }

    @Override
    public boolean needLogin() {
        if (StrUtil.isEmpty(token) || StrUtil.isEmpty(tokenTimeStamp)) return true;
        // 根据时间戳差值判断是否需要重新登录 todo(根据实际情况调整) 时间戳差值大于 1 小时，则重新登录
        if (System.currentTimeMillis() - Long.parseLong(tokenTimeStamp) > 3500 * 1000) {
            return true;
        } else {
            FeignRequestContext.addHeader("Blade-Auth", "bearer " + token);
            return false;
        }
    }

    @Override
    public void login() {
        // 登录
        synchronized (this) {
            // 双重校验
            if (needLogin()) {
                refreshToken();
            }
        }
        // 填充 token
        FeignRequestContext.addHeader("Blade-Auth", "bearer " + token);
    }

    @Override
    public void refreshToken() {
        try {
            BrLoginResult loginResult = feignClient.login(password, tenantId, userName, grantType);
            token = loginResult.getAccessToken();
            tokenTimeStamp = String.valueOf(System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException("调用保瑞登录接口失败！失败原因：{}" + e.getMessage());
        }
    }

}
