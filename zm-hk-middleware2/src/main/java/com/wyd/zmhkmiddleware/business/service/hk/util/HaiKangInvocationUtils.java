package com.wyd.zmhkmiddleware.business.service.hk.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.wyd.zmhkmiddleware.common.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.wyd.zmhkmiddleware.common.enums.ResultStatusCode.ERROR;

/**
 * @author xh
 * @date 2025-03-19
 * @Description:
 */
@Slf4j
@Component
public class HaiKangInvocationUtils {

    @Resource
    private ArtemisConfig artemisConfig;

    @Value("${artemis.config.path}")
    private String artemisPath;

    @Value("${artemis.config.protocol}")
    private String protocol;

    public <T> T post(String url, Object query, TypeToken<T> typeToken) {
        // 确定 url
        String previewURLsApi = artemisPath + url;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put(protocol, previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };
        // 序列请求参数
        String body = query != null ? new Gson().toJson(query) : null;
        // 调用接口
        String result;
        try {
            result = ArtemisHttpUtil.doPostStringArtemis(artemisConfig, path, body, null, null,
                    "application/json", null);// post请求application/json类型参数
            log.info("海康接口{}调用返回结果：{}", url, result);
        } catch (Exception e) {
            log.error("海康接口{}：调用失败，失败原因：{}", url, e.getMessage());
            throw new BaseException(ERROR.getCode(), ERROR.getName());
        }
        // T 的类型如果是 String 类型，则直接返回结果
        if (typeToken.getType().equals(String.class)) {
            return (T) result;
        }
        return new Gson().fromJson(result, typeToken.getType());
    }

}
