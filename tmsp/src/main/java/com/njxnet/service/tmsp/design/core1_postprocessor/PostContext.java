package com.njxnet.service.tmsp.design.core1_postprocessor;

import lombok.Getter;

@Getter
public class PostContext<T> {

    private final T t;

    public PostContext(T t) {
        this.t = t;
    }


}
