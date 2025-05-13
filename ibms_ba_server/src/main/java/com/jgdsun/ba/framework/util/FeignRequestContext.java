package com.jgdsun.ba.framework.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xh
 * @date 2025-04-08
 * @Description:
 */
public class FeignRequestContext {
    private static final ThreadLocal<Map<String, String>> HEADERS = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<Map<String, String>> PARAMS = ThreadLocal.withInitial(HashMap::new);

    public static Map<String, String> getHeaders() {
        return HEADERS.get();
    }
    public static Map<String, String> getParams() {
        return PARAMS.get();
    }

    public static void addHeader(String key, String value) {
        HEADERS.get().put(key, value);
    }
    public static void addParam(String key, String value) {
        PARAMS.get().put(key, value);
    }

    public static void clear() {
        HEADERS.remove();
        PARAMS.remove();
    }
}
