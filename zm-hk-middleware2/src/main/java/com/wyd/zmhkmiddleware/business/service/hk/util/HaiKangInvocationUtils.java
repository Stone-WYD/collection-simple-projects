package com.wyd.zmhkmiddleware.business.service.hk.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.wyd.zmhkmiddleware.business.model.hk.result.HaiKangDataResult;
import com.wyd.zmhkmiddleware.business.model.hk.result.HaiKangDataResultContent;
import com.wyd.zmhkmiddleware.business.model.hk.result.HaiKangResult;
import com.wyd.zmhkmiddleware.common.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
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
        if (((ParameterizedType) typeToken.getType()).getRawType().equals(HaiKangResult.class)) {
            try {
                // T 的类型如果是 HaiKangResult 类型，则判断返回结果是否成功
                HaiKangResult haiKangResult = new Gson().fromJson(result, HaiKangResult.class);
                if (!"0".equals(haiKangResult.getCode())) {
                    log.error("海康接口{}调用失败，失败原因：{}", url, haiKangResult.getMsg());
                    throw new BaseException(ERROR.getCode(), "海康接口调用失败，返回结果为:" + haiKangResult.getMsg());
                }
                if (ObjectUtil.isNotNull(haiKangResult.getData())){
                    HaiKangResult<HaiKangDataResult> dataResult = new Gson().fromJson(result, new TypeToken<HaiKangResult<HaiKangDataResult>>() {
                    }.getType());
                    List<HaiKangDataResultContent> failures = dataResult.getData().getFailures();
                    if (CollectionUtil.isNotEmpty(failures)) {
                        for (HaiKangDataResultContent failure : failures) {
                            log.error("海康接口{}调用失败，失败原因（返回结果data中获取的具体失败原因）：{}", url, failure.getMessage());
                            throw new BaseException(ERROR.getCode(), "海康接口调用失败，失败原因：" + failure.getMessage());
                        }
                    }
                }
            } catch (BaseException baseException) {
                throw baseException;
            } catch (Exception e) {
                log.info("其他未知异常不做处理，但打印一下：{}", e);
                // 不一定能序列化成功，所以这里不做处理
            }
        }
        return new Gson().fromJson(result, typeToken.getType());
    }

    public static void main(String[] args) {
        /*String result = "{\"code\":\"0\",\"msg\":\"success\",\"data\":{\"successes\":[],\"failures\":[{\"clientId\":0,\"code\":\"0x00052102\",\"message\":\"Required Param jobNo Is Null\"}]}}";
        get(result, new TypeToken<HaiKangResult<String>>(){});*/
        try {
            test2();
        } catch (BaseException baseException) {
            System.out.println("捕获到了baseException！");
        }

    }

    public static <T> T get(String result, TypeToken<T> typeToken) {
        // T 的类型如果是 String 类型，则直接返回结果
        if (typeToken.getType().equals(String.class)) {
            return (T) result;
        }
        // T 的类型如果是 HaiKangResult 类型，则判断返回结果是否成功
        if (((ParameterizedType) typeToken.getType()).getRawType().equals(HaiKangResult.class)) {
            HaiKangResult haiKangResult = new Gson().fromJson(result, HaiKangResult.class);
            if (!"0".equals(haiKangResult.getCode())) {
                throw new BaseException(ERROR.getCode(), "海康接口调用失败，返回结果为:" + haiKangResult.getMsg());
            }
            try {
                if (ObjectUtil.isNotNull(haiKangResult.getData())){
                    HaiKangResult<HaiKangDataResult> dataResult = new Gson().fromJson(result, new TypeToken<HaiKangResult<HaiKangDataResult>>() {
                    }.getType());
                    List<HaiKangDataResultContent> failures = dataResult.getData().getFailures();
                    if (CollectionUtil.isNotEmpty(failures)) {
                        for (HaiKangDataResultContent failure : failures) {
                            log.error("海康接口调用失败，失败原因（返回结果data中获取的具体失败原因）：{}", haiKangResult.getMsg());
                            throw new BaseException(ERROR.getCode(), "海康接口调用失败，失败原因：" + failure.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                // 不一定能序列化成功，所以这里不做处理
            }
        }
        return new Gson().fromJson(result, typeToken.getType());
    }


    public static void test2() {
        try {
            throw new BaseException(500, "测试异常！");
        } catch (BaseException baseException) {
            throw baseException;
        } catch (Exception e) {
            System.out.println("运行到不打算运行的地方了！");
        }
    }

}
