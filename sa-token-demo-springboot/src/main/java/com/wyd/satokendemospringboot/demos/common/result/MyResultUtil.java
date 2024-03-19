package com.wyd.satokendemospringboot.demos.common.result;

public class MyResultUtil {
    public MyResultUtil() {
    }

    public static <T> MyResult<T> getFalseResult(MyResult<T> MyResult, String message, int code) {
        MyResult.setCode(code);
        MyResult.setMessage(message);
        return MyResult;
    }

    public static <T> MyResult<T> getDefaultFalseResult(MyResult<T> MyResult) {
        MyResult.setCode(MyResultStatusCode.ERROR.getCode());
        MyResult.setMessage(MyResultStatusCode.ERROR.getName());
        return MyResult;
    }

    public static <T> MyResult<T> getTrueResult(MyResult<T> MyResult) {
        MyResult.setCode(MyResultStatusCode.SUCCESS.getCode());
        MyResult.setMessage(MyResultStatusCode.SUCCESS.getName());
        return MyResult;
    }

    public static MyResult getBussinessErrorResult(String message) {
        MyResult myResult = new MyResult();
        myResult.setCode(MyResultStatusCode.BUSSINESS_ERROR.getCode());
        myResult.setMessage(message);
        return myResult;
    }
}
