package com.wyd.satokendemospringboot.demos.controller.qr;

import com.github.hui.quick.plugin.base.DomUtil;
import com.github.hui.quick.plugin.base.constants.MediaType;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions;
import com.google.zxing.WriterException;
import com.wyd.satokendemospringboot.demos.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Api(tags = "扫码登录模块")
@Controller
@CrossOrigin
public class QrLoginRest {

    @Value("${server.port}")
    private String port;

    private final Map<String, SseEmitter> cache = new ConcurrentHashMap<>();

    @ApiOperation(value = "登录")
    @GetMapping(path = "login")
    public String qr(Map<String, Object> data) throws IOException, WriterException{
        String id = UUID.randomUUID().toString();
        // IpUtils 获取本机ip，本机测试，如果使用 127.0.0.1，localhost那么app扫码访问会有问题
        String ip = IpUtils.getLocalIp();

        String pref = "http://" + ip + ":" + port + "/" ;
        // 扫码成功后，登录页面跳转到接口的url
        data.put("redirect", pref + "home");
        // 与登录页面建立 sse 半长连接接口的url
        data.put("subscribe", pref + "subscribe?id=" + id);

        // 二维码中的 url, 用于获取手机扫码展现的内容
        String qrUrl = pref + "scan?id=" + id;
        // 生成二维码
        String qrCode = QrCodeGenWrapper.of(qrUrl).setW(200).setDrawPreColor(Color.GREEN)
                .setDrawStyle(QrCodeOptions.DrawStyle.CIRCLE).asString();
        data.put("qrcode", DomUtil.toDomSrc(qrCode, MediaType.ImageJpg));
        return "login";
    }

    @ApiOperation(value = "建立sse半长连接")
    @GetMapping(path = "subscribe", produces = {org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter subscribe(String id){
        // 设置五分钟超时时间
        SseEmitter sseEmitter = new SseEmitter(5 * 60 * 1000L);
        cache.put(id, sseEmitter);
        sseEmitter.onError((e)->cache.remove(id));
        sseEmitter.onTimeout(()->cache.remove(id));
        return sseEmitter;
    }

    @ApiOperation(value = "手机扫码接口")
    @GetMapping(path = "scan")
    public String scan(Model model, HttpServletRequest request) throws IOException {
        // 扫码后跳转页面
        String id = request.getParameter("id");
        SseEmitter sseEmitter = cache.get(id);
        if (sseEmitter != null) {
            sseEmitter.send("scan");
        }

        String url = "http://" + IpUtils.getLocalIp() + ":" + port + "/accept?id=" + id;
        model.addAttribute("url", url);
        return "scan";
    }

    @ResponseBody
    @ApiOperation(value = "授权接口")
    @GetMapping(path = "accept")
    public String accept(String id, String token) throws IOException, InterruptedException {
        SseEmitter sseEmitter = cache.get(id);
        if (sseEmitter != null) {
            sseEmitter.send("login#qrlogin=" + token);
            Thread.sleep(1000);
            sseEmitter.complete();
            cache.remove(id);
        }
        return "登录成功" + id;
    }

    @ApiOperation(value = "首页")
    @GetMapping(path = {"home", ""})
    @ResponseBody
    public String home(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0 ) {
            return "未登录";
        }

        Optional<Cookie> cookie = Stream.of(cookies).filter(s -> s.getName().equalsIgnoreCase("qrlogin")).findFirst();
        return cookie.map(cookie1 -> "欢迎进入首页：" + cookie1.getValue()).orElse("未登录！");
    }


}
