package com.wyd.satokendemospringboot.demos.constants;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = IEnumJsonSerializer.class)
@JsonDeserialize(using = IEnumJsonDeserializer.class)
public enum TestIEum implements IEnum {
    WYD(1, "王玉东"),
    YXY(2, "叶絮依")
    ;

    private final Integer code;
    private final String desc;

    TestIEum(Integer value, String desc) {
        this.code = value;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return desc;
    }


    @Override
    public String toString() {
        return "TestIEum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
