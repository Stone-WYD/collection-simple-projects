package com.jgdsun.qms.service;

import com.jgdsun.qms.model.ims4.DeviceDetail;
import com.jgdsun.qms.model.ims4.IMS4Result;
import com.jgdsun.qms.model.ims4.LoginData;
import com.jgdsun.qms.model.ims4.dto.DeviceDetailDto;
import com.jgdsun.qms.service.cookie.CookieContain;
import org.noear.nami.Filter;
import org.noear.nami.Invocation;
import org.noear.nami.Result;
import org.noear.nami.annotation.NamiClient;
import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.annotation.*;

import java.util.List;

/**
 * @author Stone
 * @since 2025-10-17
 */
// @NamiClient(url = "http://172.16.102.201")
@NamiClient(url = "http://47.103.216.72:9116")
public interface Ims4Service extends Filter {

    // 登录接口
    @Post
    @Mapping("/ims4/backend/login")
    IMS4Result<LoginData> login(@Param("username") String username,@Param("password") String password);

    @Post
    @Mapping("/ims4/backend/terminal/search-by-condition")
    IMS4Result<DeviceDetail> searchByCondition(@Body DeviceDetailDto dto);


    @Post
    @Mapping("/ims4/backend/terminal/search-ids-by-condition") // 在线、休眠、离线、后台：RUNNING、DORMANT、OFFLINE、BACKGROUD
    IMS4Result<List<Integer>> searchIdsByCondition(@Param("status") String status);


    //自带个过滤器 //要用 default 直接实现代码！！！
    default Result doFilter(Invocation inv) throws Throwable{

        if (inv.url.contains("/login")) {
            Result invoke = inv.invoke();
            // 从登录接口中获取cookie中的SESSIONID
            invoke.headers().forEach(k -> {
                if (k.getKey().equals("Set-Cookie")) {
                    CookieContain.ims4SessionCookie = k.getValue().split(";")[0];
                }
            });
            return invoke;
        } else {
            String userName = Solon.cfg().get("ims4.username");
            String password = Solon.cfg().get("ims4.password");
            IMS4Result<LoginData> login = this.login(userName, password);
            String token = login.getData().getCsrfToken();
            inv.headers.put("X-CSRF-TOKEN", token);
            inv.headers.put("Cookie", CookieContain.ims4SessionCookie);
            return inv.invoke();
        }
    }
}


