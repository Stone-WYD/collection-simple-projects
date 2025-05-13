package com.jgdsun.ba.bootstrap.business;

import com.jgdsun.ba.mybatis.entity.TBaParameter;

/**
 * @author xh
 * @date 2025-04-22
 * @Description: 控制程序
 */
public interface BaControlService {

    void control(TBaParameter parameter, String value);

}
