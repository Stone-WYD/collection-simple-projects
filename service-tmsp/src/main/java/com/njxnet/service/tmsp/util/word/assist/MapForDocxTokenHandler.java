package com.njxnet.service.tmsp.util.word.assist;

import org.apache.ibatis.parsing.TokenHandler;

import java.util.Map;

public class MapForDocxTokenHandler implements TokenHandler {
    private final Map<String, String> map;

    public MapForDocxTokenHandler(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String handleToken(String key) {
        return map.get(key);
    }
}
