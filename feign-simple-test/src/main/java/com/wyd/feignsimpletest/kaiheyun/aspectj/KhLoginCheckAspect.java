package com.wyd.feignsimpletest.kaiheyun.aspectj;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.wyd.feignsimpletest.kaiheyun.annotation.KhLoginCheck;
import com.wyd.feignsimpletest.kaiheyun.client.KhFeignClient;
import com.wyd.feignsimpletest.kaiheyun.model.param.KhLoginParam;
import com.wyd.feignsimpletest.kaiheyun.model.result.CommonResult;
import com.wyd.feignsimpletest.kaiheyun.model.result.login.LoginContent;
import com.wyd.feignsimpletest.kaiheyun.model.result.login.Station;
import com.wyd.feignsimpletest.kaiheyun.util.KhFeignRequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 调用接口前校验是否需要登录，登录后刷新 token 和 一些登录信息
 *
 * @author ruoyi
 */
@Aspect
@Component
public class KhLoginCheckAspect {

    @Autowired
    private KhFeignClient feignClient;

    @Value("${feign.kaihe.login-code}")
    private String code;
    @Value("${feign.kaihe.login-user}")
    private String user;
    @Value("${feign.kaihe.login-pwd}")
    private String pwd;

    // 请求头参数
    private String userId;
    private String token;
    private String tokenTimeStamp;
    // params 参数
    private String stationId;

    @Before("@annotation(loginCheck)")
    public void doBefore(JoinPoint point, KhLoginCheck loginCheck){
        // 检查是否需要重新登录
        if (needLogin()) {
            // 生成新的请求头
            loginToRefresh();
        }
        // 填充请求头
        String timestamp = String.valueOf(System.currentTimeMillis());
        KhFeignRequestContext.addHeader("timestamp", timestamp);
        KhFeignRequestContext.addHeader("userId", userId);
        // 虽说有多个参数要通过登录接口获取，但实际只有 token 可能变化，这里不再加入多线程进行控制
        KhFeignRequestContext.addHeader("signature", DigestUtil.sha1Hex(userId + token + timestamp).toUpperCase());
        // 填充其他参数
        KhFeignRequestContext.addParam("stationId", stationId);
    }


    @After("@annotation(loginCheck)")
    public void doAfter(JoinPoint point, KhLoginCheck loginCheck) {
        // 清空请求头信息
        KhFeignRequestContext.clear();
    }

    private void loginToRefresh() {
        synchronized (this) {
            // 双重校验
            if (needLogin()) {
                KhLoginParam loginParam = new KhLoginParam();
                loginParam.setCode(code);
                loginParam.setUsername(user);
                loginParam.setPassword(pwd);
                try {
                    CommonResult<LoginContent> loginResult = feignClient.login(loginParam);
                    if (ObjectUtil.isNotEmpty(loginResult) && loginResult.getStatus() != 200L ) {
                        throw new RuntimeException("调用凯和云登录接口失败！凯和云返回：" + loginResult.getMessage());
                    }
                    LoginContent content = loginResult.getContent();
                    token = content.getToken();
                    tokenTimeStamp = String.valueOf(System.currentTimeMillis());
                    if (ObjectUtil.isNotEmpty(content.getId())) {
                        userId = String.valueOf(content.getId());
                    }
                    // 获取用户所属的站点信息
                    Station station = content.getStation();
                    Long stationId = ObjectUtil.isNotEmpty(station) ? station.getStationId() : null;
                    if (ObjectUtil.isNotEmpty(stationId)) {
                        this.stationId = String.valueOf(stationId);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("调用凯和云登录接口失败！");
                }
            }
        }
    }

    private boolean needLogin() {
        if (StrUtil.isEmpty(userId) || StrUtil.isEmpty(token) || StrUtil.isEmpty(tokenTimeStamp)) return true;
        // 根据时间戳差值判断是否需要重新登录 todo(根据实际情况调整) 时间戳差值大于 24 小时，则重新登录
        return System.currentTimeMillis() - Long.parseLong(tokenTimeStamp) > 24 * 3600 * 1000;
    }

}
