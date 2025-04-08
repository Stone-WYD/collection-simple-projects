package com.wyd.feignsimpletest.kaiheyun.model;

import lombok.Data;

/**
 * @author xh
 * @date 2025-04-08
 * @Description:
 */
@Data
public class KhInvokeHeaders {

    private String userId;

    private String timestamp;

    private String signature;

    private String platform;

    private String version;

    private String faceInfo;

}
