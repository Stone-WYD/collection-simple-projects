package com.wyd.satokendemospringboot.demos.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyResult<T> implements Serializable {

    private T data;

    private int code;

    private String message;
}
