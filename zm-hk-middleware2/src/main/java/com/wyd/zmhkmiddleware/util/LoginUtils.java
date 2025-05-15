package com.wyd.zmhkmiddleware.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.cage.Cage;
import com.github.cage.IGenerator;
import com.github.cage.image.ConstantColorGenerator;
import com.github.cage.image.Painter;
import com.wyd.zmhkmiddleware.business.model.local.dto.CaptchaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Stone
 * @since 2025-05-10
 */
@Component
@Slf4j
public class LoginUtils {

    @Resource
    private CacheManager cacheManager;

    private final static String CAPTCHA_KEY = "captchaKey";
    private final static String LOGIN_TOKEN_KEY = "loginToken";

    private Cache capCache;
    private Cache loginCache;




    @PostConstruct
    public void init() {
        capCache = cacheManager.getCache(CAPTCHA_KEY);
        loginCache = cacheManager.getCache(LOGIN_TOKEN_KEY);
    }

    public String generageLoginToken(String userName) {
        // 随机生成 token 存储到本地缓存中
        String token = RandomUtil.randomString(LOGIN_TOKEN_KEY, 32);
        loginCache.put(token, userName);
        return token;
    }

    public String getUserName() {
        // 获取当前请求对象
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        return (String) loginCache.get(token).get();
    }

    public boolean checkToken(String token) {
        if (StrUtil.isEmpty(token)) {
            return false;
        }
        return ObjectUtil.isNotEmpty(loginCache.get(token));
    }


    public CaptchaDTO generateCaptcha() throws Exception {
        IGenerator<Color> ig = new ConstantColorGenerator(Color.BLACK);
        Painter painter = new Painter(115, 38, null, null, null, null);
        Cage cage = new Cage(painter, null, ig, null, null, null, null);
        //获取验证码字符串
        String text = cage.getTokenGenerator().next().toUpperCase().substring(0, 4);
        //将用户名+时间戳生成唯一标识
        String kapKey = UUID.randomUUID().toString().replace("-", "");
        capCache.put(kapKey, text);
        //生成图片
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            cage.draw(text, outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            CaptchaDTO captchaDTO = new CaptchaDTO();
            captchaDTO.setImg(captchaBase64);
            captchaDTO.setKey(kapKey);
            log.info("请求的验证码为：{}:{}", kapKey, text);
            return captchaDTO;
        } catch (Exception e) {
            log.error("获取验证码异常", e);
            throw e;
        }
    }

    public boolean checkCaptcha(String captchaKey, String captchaValue) {
        if (StrUtil.isEmpty(captchaKey) || StrUtil.isEmpty(captchaValue)) {
            return false;
        }
        if (ObjectUtil.isEmpty(capCache.get(captchaKey))) return false;
        String value = ((String) capCache.get(captchaKey).get());
        // 不区分大小写比较captchaValue和value
        return captchaValue.equalsIgnoreCase(value);
    }
}
