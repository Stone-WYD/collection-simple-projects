package com.njxnet.asset_manage.test.mybatis.ocp;

import org.apache.ibatis.annotations.Param;


public interface OcpTestDao {

    OcpTest getOcpTest(@Param("id") long id);

}
